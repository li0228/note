package com.lhh.io.bio.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author lihonghao
 * @date 2021/6/30 14:28
 */
public class ServerThreadReader extends Thread {
	private Socket socket;

	public ServerThreadReader(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {
		try {
			// 从socket获取到一个直接输入流
			InputStream inputStream = socket.getInputStream();
			// 拿到数据
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String msg;
			while ((msg = bufferedReader.readLine()) != null){
				System.out.println(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
