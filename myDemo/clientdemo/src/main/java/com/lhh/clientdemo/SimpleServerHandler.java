package com.lhh.clientdemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lihonghao
 * @date 2020/12/28 20:14
 */
public class SimpleServerHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("客户端收到消息:" + msg);
	}
}
