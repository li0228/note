package com.lhh.io.bio.four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author lihonghao
 * @date 2021/6/30 15:15
 */
public class ServerRunnable implements Runnable{
	Socket socket;

	public ServerRunnable() {
	}

	public ServerRunnable(Socket socket) {
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
