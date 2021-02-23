package groupChat;

import javax.security.auth.Subject;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author lihonghao
 * @date 2021/2/23 21:38
 */
public class GroupChatClient {
	// 定义相关的属性
	private final String HOST = "127.0.0.1";//服务器的ip
	// 端口
	private final int POST = 6667;// 服务器端口

	private Selector selector;

	private SocketChannel socketChannel;

	private String name;//名称

	public GroupChatClient() throws IOException {
		selector = Selector.open();

		// 连接服务器
		socketChannel = socketChannel.open(new InetSocketAddress("127.0.0.1", POST));

		// 设置非阻塞
		socketChannel.configureBlocking(false);

		// 注册
		socketChannel.register(selector, SelectionKey.OP_READ);

		// 得到userName
		name = socketChannel.getLocalAddress().toString().substring(1);

		System.out.println(name + "is ok!");
	}

	/**
	 * 向服务器发送消息
	 *
	 * @param info
	 */
	public void sendInfo(String info) {
		info = name + "说:" + info;
		try {
			socketChannel.write(ByteBuffer.wrap(info.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 读取服务端回复的消息
	public void readInfo(){
		try{
			int select = selector.select();
			if(select >0){
				// 有可用的通道
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()){
					SelectionKey key = iterator.next();
					if(key.isReadable()){
						// 得到相关的通道
						SocketChannel channel = (SocketChannel) key.channel();
						// 得到一个buffer
						ByteBuffer buffer = ByteBuffer.allocate(1024);

						// 读取
						channel.read(buffer);
						String msg =  new String(buffer.array());
						System.out.println(msg.trim());

					}else{
						System.out.println("没可用的通道。。");
					}
				}
				iterator.remove();
			}
		}catch (Exception e ){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		// 启动
		final GroupChatClient chatClient = new GroupChatClient();

		new Thread(){
			public void run(){
				while (true){
					chatClient.readInfo();
					try{
						Thread.currentThread().sleep(3000);
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()){
			String s = scanner.nextLine();
			chatClient.sendInfo(s);
		}
	}
}

