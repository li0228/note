package com.lhh.io.nio.demo;

import com.sun.deploy.panel.ITreeNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 目标：NIO非阻塞通信下的入门案例
 * @author lihonghao
 * @date 2021/7/5 10:26
 */
public class Server {
	public static void main(String[] args) throws IOException {
		// 获取通道
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		// 配置成非阻塞模式
		socketChannel.configureBlocking(false);
		// 监听端口
		socketChannel.bind(new InetSocketAddress(9999));
		// 获取选择器
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT );

		while(selector.select() > 0){
			// 1.获取选择器中的所有注册的通道中已经就绪的事件
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// 开始遍历准备好的事件
			while(iterator.hasNext()){
				SelectionKey sk = iterator.next();
				// 判断事件的类型
				if(sk.isAcceptable()){
					SocketChannel accept = socketChannel.accept();
					accept.configureBlocking(false);
					// 将本客户端事件注册到选择
					accept.register(selector,SelectionKey.OP_READ);
				}else if(sk.isReadable()){
					// 获取读就绪事件
					SocketChannel socketChannel1 = (SocketChannel) sk.channel();
					// 读取数据
					ByteBuffer byf = ByteBuffer.allocate(2014);
					int len = 0;
					while ((len = socketChannel1.read(byf))>0){
						byf.flip();
						System.out.println(new String(byf.array(),0,len));
						byf.clear();
					}
				}
				iterator.remove();
			}
		}
	}


}
