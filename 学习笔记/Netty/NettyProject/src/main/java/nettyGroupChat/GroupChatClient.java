package nettyGroupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author lihonghao
 * @date 2021/2/28 17:17
 */
public class GroupChatClient {
	private final String HOST;
	private final int port;

	public GroupChatClient(String HOST, int port) {
		this.HOST = HOST;
		this.port = port;
	}

	public void run() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// 得到pipeline
							ChannelPipeline pipeline = ch.pipeline();
							// 加入handle
							pipeline.addLast("decoder",new StringDecoder());
							pipeline.addLast("encoder",new StringEncoder());
							pipeline.addLast(new GroupChatClientHandle());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect(HOST, port).sync();
			// 得到channel
			Channel channel = channelFuture.channel();

			System.out.println("-----"+channel.localAddress()+"------");
			// 客户端输入信息，创建一个扫描器

			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()){
				String s = scanner.nextLine();
				// 通过channel发送到服务器端
				channel.writeAndFlush(s+"\r\n");
			}

		} finally {
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		new GroupChatClient("127.0.0.1",7000).run();
	}
}
