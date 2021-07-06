package com.lhh.io.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author lihonghao
 * @date 2021/7/5 11:22
 */
public class Client {
	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
		socketChannel.configureBlocking(false);

		 ByteBuffer buffer = ByteBuffer.allocate(1204);
		 Scanner scanner = new Scanner(System.in);

		 while(true){
			 System.out.print("请说：");
			 String msg = scanner.nextLine();
			 buffer.put(("嘻嘻："+msg).getBytes());
			 buffer.flip();
			 socketChannel.write(buffer);
			 buffer.clear();
		 }
	}
}
