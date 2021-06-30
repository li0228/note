package com.lhh.io.bio.filedemo;

import java.io.*;
import java.net.Socket;

/**
 * 目标：实现客户端上传任意类型的文件数据给服务端保存起来
 *
 * @author lihonghao
 * @date 2021/6/30 15:35
 */
public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1",9999);
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(outputStream);
			// 先发送文件的后缀
			dos.writeUTF(".jpg");
			// 把文件数据发送给服务端
			InputStream is = new FileInputStream("D:\\java.jpg");
			byte[] buffer = new byte[1024];
			int len;
			while((len = is.read(buffer)) > 0){
				dos.write(buffer,0,len);
			}
			dos.flush();
			socket.shutdownOutput();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
