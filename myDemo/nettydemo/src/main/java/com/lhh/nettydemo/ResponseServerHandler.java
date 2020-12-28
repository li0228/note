package com.lhh.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * @author lihonghao
 * @date 2020/12/28 19:48
 */
public class ResponseServerHandler extends ChannelHandlerAdapter {

	/**
	 * 覆盖channelRead（）事件处理方案
	 * 每当从客户端收到新的数据时
	 * 这个方法会在收到信息是被调用
	 * ChannelHandlerContext对象提供了许多操作
	 * 使你能够触发各种各样的i/o事件和操作
	 * 这里我们调用write(object)方法来逐字地把接受到的消息写入
	 * @param ctx 通道处理的上下文信息
	 * @param msg 接受到的消息
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println(in.toString(CharsetUtil.UTF_8));
		ctx.write(msg);
		//cxt.writeAndFlush(msg)
		//请注意，这里我并不需要显式的释放，因为在进入的时候netty已经自动释放
		// ReferenceCountUtil.release(msg);
	}

	/**
	 * ctx.write(Object)方法不会使消息写入到通道上，
	 * 他被缓冲在了内部，你需要调用ctx.flush()方法来把缓冲区中数据强行输出。
	 * 或者你可以在channelRead方法中用更简洁的cxt.writeAndFlush(msg)以达到同样的目的
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 这个方法会在发生异常时触发
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}
