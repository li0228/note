package groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务器
 *
 * @author lihonghao
 * @date 2021/2/23 19:38
 */
public class GroupChatServer {

	private Selector selector;

	private ServerSocketChannel listenChannel;

	private static final int PORT = 6667;

	/**
	 * 构造器
	 */
	public GroupChatServer() {
		try {
			// 得到选择器
			selector = selector.open();

			// ServerSocketChannel
			listenChannel = ServerSocketChannel.open();

			// 绑定端口
			listenChannel.socket().bind(new InetSocketAddress(PORT));

			// 设置非阻塞模式
			listenChannel.configureBlocking(false);

			// 将该listenChannel注册到selector
			listenChannel.register(selector, SelectionKey.OP_ACCEPT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 监听
	public void listen() {
		try {
			while (true) {
				int count = selector.select();
				if (count > 0) {
					// 遍历所有的key集合
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();

						// 监听到accept
						if (key.isAcceptable()) {
							SocketChannel accept = listenChannel.accept();
							accept.configureBlocking(false);
							accept.register(selector, SelectionKey.OP_READ);
							// 提示上线
							System.out.println(accept.getLocalAddress() + "上线");
						}

						// 可读
						if (key.isReadable()) {
							// 处理读
							readData(key);
						}

						// 当前的key删除
						iterator.remove();
					}
				} else {
					System.out.println("等待。。。");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 读取客户端数据
	 * @param key
	 */
	private void readData(SelectionKey key) {
		// 定义一个sockeyChannel
		SocketChannel channel = null;
		try {
			channel = (SocketChannel) key.channel();
			//創建buffer
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			int read = channel.read(byteBuffer);
			if (read > 0) {
				// 把缓冲区转为字符串
				String s = new String(byteBuffer.array());
				System.out.println("form客户端:" + s);

				// 像其他的客户端转发消息
				sendInfoToOtherClients(s, channel);

			}

		} catch (IOException e) {
			try {
				System.out.println(channel.getLocalAddress() + "离线了。。");
				key.cancel();
				// 关闭通道
				channel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 发消息给其他客户端
	 *
	 * @param s
	 * @param channel
	 */
	private void sendInfoToOtherClients(String s, SocketChannel channel) throws IOException {
		System.out.println("服务器转发消息。。。");
		// 遍历所有注册到selector的socketChannel
		for (SelectionKey key : selector.keys()) {
			// 取出通道
			Channel targetChannel = key.channel();

			// 排除自己
			if (targetChannel instanceof SocketChannel && targetChannel != channel) {
				// 转型
				SocketChannel dest = (SocketChannel) targetChannel;

				// 将消息存到buffer
				ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes());

				// 将buffer的数据写入到通道
				dest.write(byteBuffer);
			}

		}
	}

	public static void main(String[] args) {
		// 创建一个服务器对象
		GroupChatServer groupChatServer = new GroupChatServer();
		groupChatServer.listen();
	}
}
