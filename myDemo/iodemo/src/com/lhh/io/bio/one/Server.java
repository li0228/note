package com.lhh.io.bio.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * @author lihonghao
 * @date 2021/6/29 17:22
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 定义一个serverSocket
			ServerSocket serverSocket = new ServerSocket(8899);

			// 监听客户端的socket连接请求
			Socket socket = serverSocket.accept();

			// 或者一个字节流输入流
			InputStream inputStream = socket.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String msg;
			if ((msg = br.readLine()) != null) {
				System.out.println("服务端接收到数据" + msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
