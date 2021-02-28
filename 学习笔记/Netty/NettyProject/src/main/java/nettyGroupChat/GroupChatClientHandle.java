package nettyGroupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lihonghao
 * @date 2021/2/28 17:30
 */
public class GroupChatClientHandle extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
		System.out.println(s.trim());
	}
}
