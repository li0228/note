package com.lhh.io.bio.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lihonghao
 * @date 2021/6/30 17:01
 */
public class ServerReadThread extends Thread {
	private Socket socket;

	public ServerReadThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// 从socket去获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg;
			while ((msg = br.readLine())!= null){
				// 推送给在线socket
				sendMsgToAllClient(msg);
			}
		} catch (Exception e) {
			Server.sockets.remove(socket);
			e.printStackTrace();
		}
	}

	/**
	 * 群发
	 * @param msg
	 */
	private void sendMsgToAllClient(String msg) throws IOException {
		for (Socket s : Server.sockets) {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println(msg);
			ps.flush();
		}
	}
}
