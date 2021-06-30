package com.lhh.io.bio.three;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过线程池来处理多个客户端的请求
 * @author lihonghao
 * @date 2021/6/30 14:24
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 1.注册端口
			ServerSocket ss =new ServerSocket(9999);
			// 定义一个死循环不断接收客户端的socket连接请求
			while (true){
				Socket socket = ss.accept();
				// 创建线程来和socket通信
				new ServerThreadReader(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
