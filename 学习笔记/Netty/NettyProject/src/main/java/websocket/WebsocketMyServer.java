package websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lihonghao
 * @date 2021/2/28 20:24
 */
public class WebsocketMyServer {
	public static void main(String[] args) {
		EventLoopGroup boosGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boosGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new LoggingHandler(LogLevel.INFO));
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ctx) throws Exception {
					//因为基于http协议，使用http编码解码器
					ChannelPipeline pipeline = ctx.pipeline();
					pipeline.addLast(new HttpServerCodec());
					pipeline.addLast(new ChunkedWriteHandler());
					pipeline.addLast(new HttpObjectAggregator(8192));
					pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
					pipeline.addLast(new MyTextWebSocketFrameHandler());
				}
			});
			ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
		    channelFuture.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			boosGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
