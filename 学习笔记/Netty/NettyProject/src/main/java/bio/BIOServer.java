package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lihonghao
 * @date 2021/2/13 14:29
 */
public class BIOServer {
	public static void main(String[] args) throws IOException {
		//线程池机制
		ExecutorService executorService = Executors.newCachedThreadPool();

		ServerSocket serverSocket = new ServerSocket(6666);
		while (true) {
			// 监听，等待客户端连接
			final Socket socket = serverSocket.accept();
			System.out.println("连接到一个客户端");

			// 创建线程通信
			executorService.execute(new Runnable() {
				public void run() {
					handle(socket);
				}
			});
		}

	}

	public static void handle(Socket socket) {
		try {
			byte[] bytes = new byte[1024];
			InputStream inputStream = socket.getInputStream();
			System.out.println("线程信息 id = " + Thread.currentThread().getId() + "线程名称：" + Thread.currentThread().getName());
			while (true) {
				int read = inputStream.read(bytes);
				if (read != -1) {
					System.out.println(new String(bytes, 0, read));
				} else {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("关闭连接");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}
}
