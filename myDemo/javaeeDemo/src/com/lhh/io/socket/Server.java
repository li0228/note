package com.lhh.io.socket;

import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author lihonghao
 * @date 2020/12/15 11:49
 */
public class Server {
	private static final ExecutorService cachedThreadPool = new ThreadPoolExecutor(1, 6, 11,TimeUnit.SECONDS ,new LinkedBlockingQueue<Runnable>());
	public static void main(String[] args) throws IOException {
		// 监听制定端口
		ServerSocket serverSocket = new ServerSocket(6666);
		System.out.println("server is running");
		for (;;){
			final Socket socket = serverSocket.accept();
			System.out.println("connected from"+socket.getRemoteSocketAddress());
			final Handler handler = new Handler(socket);
			cachedThreadPool.submit(new Runnable() {
				@Override public void run() {
					handler.run();
				}
			});

		}
	}

}
class Handler{
	Socket sock;

	public Handler(Socket sock) {
		this.sock = sock;
	}

	void run(){
		try (InputStream input = this.sock.getInputStream()) {
			try (OutputStream output = this.sock.getOutputStream()) {
				handle(input, output);
			}
		} catch (Exception e) {
			try {
				this.sock.close();
			} catch (IOException ioe) {
			}
			System.out.println("client disconnected.");
		}
	}
	private void handle(InputStream input,OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		writer.write("hello\n");
		writer.flush();
		for (;;) {
			String s = reader.readLine();
			if (s.equals("bye")) {
				writer.write("bye\n");
				writer.flush();
				break;
			}
			writer.write("ok: " + s + "\n");
			writer.flush();
		}
	}
}

