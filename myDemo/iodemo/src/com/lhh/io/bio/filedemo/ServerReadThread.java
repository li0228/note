package com.lhh.io.bio.filedemo;

import java.lang.Runnable;
import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * @author lihonghao
 * @date 2021/6/30 16:12
 */
public class ServerReadThread extends Thread {
	private Socket socket;

	public ServerReadThread() {
	}

	public ServerReadThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			System.out.println("接收到文件！");
			// 读取类型
			String suffix = dis.readUTF();
			// 定义直接输出管道
			OutputStream os = new FileOutputStream("D:\\server\\" + UUID.randomUUID().toString() + suffix);
			byte[] buffer = new byte[1024];
			int len;
			int i = 0;
			while ((len = dis.read(buffer)) > 0) {
				os.write(buffer, 0, len);
				i++;
			}
			os.close();
			System.out.println("保存成功,大小：" + i + "k");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
