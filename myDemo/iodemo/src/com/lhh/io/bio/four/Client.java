package com.lhh.io.bio.four;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author lihonghao
 * @date 2021/6/30 14:33
 */
public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1",9999 );
			// 得到一个打印流
			OutputStream outputStream = socket.getOutputStream();
			PrintStream ps = new PrintStream(outputStream);
			Scanner sc  = new Scanner(System.in);
			while(true){
				System.out.print("请说：");
				String msg = sc.nextLine();
				ps.println(msg);
				ps.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
