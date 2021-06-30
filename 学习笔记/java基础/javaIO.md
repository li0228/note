## **java的I/O演进之路**

### **I/O模型的基本说明**

I/O模型：就是用什么样的通道或者说通信模式进行数据的传输和接收，很大程度决定了程序通信的性能，java一共支持三种网络编程的I/O模型：**BIO、NIO、AIO**。

实际通信需求下，要根据不同的业务场景和醒来需求来决定不同的I/O模型。

### **I/O模型**

#### **java BIO**

同步并阻塞（传统阻塞型），服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就启动一个线程来进行处理，如果这个连接不做任何事会早餐不必要的线程开销。

![image-20210629162444794](https://raw.githubusercontent.com/li0228/image/master/image-20210629162444794.png)

#### **java NIO**

同步非阻塞，服务器实现模式为一个线程处理多个请求，即客户端发送的链接请求都会注册都多路复用器上，多路复用器轮询到连接有I/O请求就进行处理。

![image-20210629163025189](https://raw.githubusercontent.com/li0228/image/master/image-20210629163025189.png)

#### **java AIO**

异步非阻塞，服务器实现模式为一个有效请求一个线程，客户端的I/O请求都是由OS先完成再通知服务器去启动线程进行处理，一般适用于连接数较多且连接时间较长的应用

## **java BIO深入剖析**

### **BIO基本介绍**

- java BIO 就是传统的java io编程，其相关的类和接口在java.io

### **BIO的工作机制**

通过socket。

### **实例**

**客户端案例**

```java
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
```

#### **服务端案例**

```java
/**
 * 服务端
 * @author lihonghao
 * @date 2021/6/29 17:22
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 定义一个serverSocket
			ServerSocket serverSocket = new ServerSocket(8899);

			// 监听客户端的socket连接请求
			Socket socket = serverSocket.accept();

			// 或者一个字节流输入流
			InputStream inputStream = socket.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String msg;
			if ((msg = br.readLine()) != null) {
				System.out.println("服务端接收到数据" + msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

#### 小结

- 在以上通信中，服务端会一直等待客户端的消息，如果客户端没有进行消息的发送，服务端会一直进入阻塞状态。
- 同事服务端是按照行获取消息的，这以为这客户端也必须按照行来进行消息的发送，否则服务端将进去等待消息的阻塞状态。

### **BIO模式下的多发和多收消息**

上面的例子只能实现客户端发送消息，服务端接收消息，并不能实现反复的受消息和反复的发消息，我们只需要在客户端的案例中，加入按照行发送消息的逻辑即可！案例代码如下：

**客户端代码**

```java
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
			Scanner sc = new Scanner(System.in);
			while(true){
				System.out.print("请说：");
				String msg  = sc.nextLine().trim();
				ps.println(msg);
				ps.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
```

### **BIO模式下接收多个客户端**

上述案例中，一个服务端只能接收一个客户端的通信请求，那么如果服务端需要处理很多个客户端的消息请求应该如何处理呢，此时我们就需要在服务端引入线程了，也就是说客户端每发起一个请求，服务端就创建一个新的线程来处理这个客户端的请求，这样就实现了一个客户端一个线程的模型。

**客户端代码**

```
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

```

**服务端代码**

```java
/**
 * 通过线程池来处理多个客户端的请求
 * @author lihonghao
 * @date 2021/6/30 14:24
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 1.注册端口
			ServerSocket ss =new ServerSocket(9999);
			// 定义一个死循环不断接收客户端的socket连接请求
			while (true){
				Socket socket = ss.accept();
				// 创建线程来和socket通信
				new ServerThreadReader(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



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

```

**小结**

-  每个socket接收到，都会创建一个线程，线程的竞争、切换上下文影响性能。
- 每个线程都会占用栈空间和CPU资源；
- 并不是每个socket都会进行IO操作，无意义的线程处理
- 客户端的并发访问增加时，服务端将呈现1:1的线程开销，访问量越大，系统将发生线程栈溢出，线程创建失败，最后导致经常宕机或者僵死，从而不能对外提供服务

### 伪异步I/O编程

 可以通过线程池和任务队列来实现。通过固定线程数量。

![image-20210630150402192](https://raw.githubusercontent.com/li0228/image/master/image-20210630150402192.png)

**客户端代码**

```java
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
```

**服务端代码**

```java
/**
 * 实现伪异步通信架构
 * @author lihonghao
 * @date 2021/6/30 15:06
 */
public class Server {
	public static void main(String[] args) {
		try {
			// 1.注册端口
			ServerSocket ss = new ServerSocket(9999);
			HandlerSocketServerPool pool = new HandlerSocketServerPool(6, 10);
			// 2.定义一个循环接收客户端的socket请求
			while(true){
				Socket socket = ss.accept();
				Runnable target = new ServerRunnable(socket);
				pool.execute(target);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



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


/**
 * @author lihonghao
 * @date 2021/6/30 15:09
 */
public class HandlerSocketServerPool {
	// 线程池对象
	private ExecutorService executorService;

	public HandlerSocketServerPool(int maxThreadNum,int queueSize) {
		executorService = new ThreadPoolExecutor(3, maxThreadNum, 120, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void execute(Runnable target){
		executorService.execute(target);
	}
}

```

**总结**

- 伪异步io采用了线程池实现，因此避免了为每个请求创建一个独立的线程造成线程资源耗尽的问题，但由于底层依然是采用同步阻塞模型，因此无法从根本解决问题。
- 如果单个消息的处理缓慢，或者服务端线程池中的全部线程都被阻塞，那么后续的socket的i/o消息都将在队列中排队，新的socket请求将被拒绝，客户端会产生大量的连接超时。

### 基于BIO形式下的文件上传

**客户端代码**

```java
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
```

**服务端代码**

```java
/**
 * 接收客户端的任意类型文件
 * @author lihonghao
 * @date 2021/6/30 15:35
 */
public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			while (true){
				Socket socket = serverSocket.accept();
				// 交给独立线程来处理
				ServerReadThread serverReadThread = new ServerReadThread(socket);
				serverReadThread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


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

```

### java BIO模式下的端口转发思想

需求：需要实现一个客户端的消息可以发送给所有的客户端去接收。（群聊）

![
](https://raw.githubusercontent.com/li0228/image/master/image-20210630164558050.png)

**服务端代码**

```java
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
```

## **java NIO深入剖析**

## **java AIO深入剖析**

## **总结**

