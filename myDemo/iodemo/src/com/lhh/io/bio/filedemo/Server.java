package com.lhh.io.bio.filedemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收客户端的任意类型文件
 * @author lihonghao
 * @date 2021/6/30 15:35
 */
public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			while (true){
				Socket socket = serverSocket.accept();
				// 交给独立线程来处理
				ServerReadThread serverReadThread = new ServerReadThread(socket);
				serverReadThread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
