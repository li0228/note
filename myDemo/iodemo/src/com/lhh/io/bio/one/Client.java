package com.lhh.io.bio.one;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端
 *
 * @author lihonghao
 * @date 2021/6/29 17:22
 */
public class Client {
	public static void main(String[] args) {
		try {
			// 1. 创建socket对象请求服务端连接
			Socket socket = new Socket("127.0.0.1",8899);

			// 2. 从socket对象中获取一个字节输出流
			OutputStream os = socket.getOutputStream();

			PrintStream ps = new PrintStream(os);

			ps.println("hello world! 服务端！");
			ps.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
