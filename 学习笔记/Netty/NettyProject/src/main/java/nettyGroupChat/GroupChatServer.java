package nettyGroupChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lihonghao
 * @date 2021/2/28 14:30
 */
public class GroupChatServer {
	// 监听端口
	private int port;

	public GroupChatServer(int port) {
		this.port = port;
	}

	// 编写run方法，处理客户端请求
	public void run() throws InterruptedException {

			// 创建两个线程组
			EventLoopGroup bossGroup = new NioEventLoopGroup() ;
			EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG,128)
					.childOption(ChannelOption.SO_KEEPALIVE,true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel ch) throws Exception {
							// 获取到pipeline
							ChannelPipeline pipeline = ch.pipeline();
							// 向pipeline 加入一个解码器
							pipeline.addLast("decoder",new StringDecoder());
							// 加入一个编码器
							pipeline.addLast("encoder",new StringEncoder());
							// 加入自己的handle
							pipeline.addLast(new GroupChatServerHandle());

						}
					});
			System.out.println("netty 服务器启动");
			ChannelFuture channelFuture = b.bind(port).sync();
			// 监听关闭事件
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		try {
			new GroupChatServer(7000).run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
