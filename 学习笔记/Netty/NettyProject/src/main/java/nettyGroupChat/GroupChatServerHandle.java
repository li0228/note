package nettyGroupChat;

import com.sun.javafx.collections.MappingChange;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lihonghao
 * @date 2021/2/28 15:47
 */
public class GroupChatServerHandle extends SimpleChannelInboundHandler<String> {

	// 使用一个hashMap管理
	public static Map<String,Channel> channels = new HashMap<String,Channel>();

	// 定义一个channel组
	// GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 表示连接建立，一旦连接第一个被执行
	// 将当前channel加入到channelGroup
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天室");
		channelGroup.add(channel);

	}

	// 表示channel处于活动状态
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "上线了");
	}

	// 表示channel处于非活动状态
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "离线了");
	}

	// 断开连接触发,将某某客户离开推送给当前在线的客户
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + "离开了\n");
		System.out.println("当前channelgroup的大小" + channelGroup.size());
	}

	// 读取数据
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		Channel channel = ctx.channel();
		// 遍历channelGroup，根据不同情况回送不同消息
		channelGroup.forEach(ch -> {
			if (channel != ch) {
				ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送消息:" + s + "\n");
			} else {
				ch.writeAndFlush("我发送了：" + s + "\n");
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.close();
	}
}
