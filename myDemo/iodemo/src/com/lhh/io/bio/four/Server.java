package com.lhh.io.bio.four;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现伪异步通信架构
 * @author lihonghao
 * @date 2021/6/30 15:06
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 1.注册端口
			ServerSocket ss = new ServerSocket(9999);
			HandlerSocketServerPool pool = new HandlerSocketServerPool(6, 10);
			// 2.定义一个循环接收客户端的socket请求
			while(true){
				Socket socket = ss.accept();
				Runnable target = new ServerRunnable(socket);
				pool.execute(target);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
