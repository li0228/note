package com.lhh.io.bio.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 目标：bio模式下的端口转发
 * @author lihonghao
 * @date 2021/6/30 16:56
 */
public class Server {
	// 在线集合
	public static List<Socket> sockets = new ArrayList<>();

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(9999);
			while(true){
				Socket socket = ss.accept();
				// 把这个socket存入到一个数据结构中
				sockets.add(socket);

				// 分配线程来处理
				new ServerReadThread(socket).start();
			}
		}catch (Exception exception){
			exception.printStackTrace();
		}
	}
}
