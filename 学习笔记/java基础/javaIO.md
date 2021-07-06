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

### java NIO 基本介绍 

- non-blocking IO 。1.4引入
- 在java.nio包下
- 三大核心：Channel，Buffer，Selector

### NIO和BIO的比较

- BIO以流的方式处理数据，而NOI以块的方式处理数据，**块I/O**的效率比**流I/O**高很多
- BIO是阻塞的，NIO则是非阻塞的
- BIO基于自己留和字符流操作，而NIO基于CHannel（通道）和Buffer（缓冲区）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。Selector（选择器）用于监听多个通道的事件。（比如：连接请求，数据到达等），因此使用单个线程就可以监听多个客户端通道。

| NIO                      | BIO                  |
| ------------------------ | -------------------- |
| 面向缓冲区（Buffer)      | 面向流（Stream)      |
| 非阻塞（Non Blocking IO) | 阻塞IO（Blocking IO) |
| 选择器（Selectors)       |                      |

### NIO三个核心原理示意图

NIO有三个核心部分：**Channel(通道)、Buffer（缓冲区）、Selector(选择器)**

### NIO核心一：缓冲区（buffer）

缓冲区本质是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该内存。相比较直接对数组的操作，Buffer API更加容易操作和管理。

#### BUffer及其子类

buffer就像是一个数组，可以保存多个相同类型的数据。根据数据类型不同，有以下几种

- ByteBuffer
- CharBuffer
- ShortBuffer
- intBuffer
- LongBuffer
- FloatBuffer
- DoubleBuffer

#### 缓冲区的基本属性

**Buffer中的重要概念**

- 容量（capacity)
- 限制（limit)
- 位置（position)
- 标记（mark)和重置（reset)

**使用Buffer读写数据一般遵循四个步骤：**

1. **写入数据到Buffer**
2. **调用flip（），转换为读数据**
3. **从buffer中读取数据**
4. **调用clear()或者campact()方法清除缓冲区**

### NIO核心二：通道（channel）

java NIO的通道类似流，但又有些不同；既可以从通道读取数据，又可以写数据到通道。但流是单向的。通道可以非阻塞读取和写入通道。可以异步读取 。

Channel在NIO中是一个接口

```java
public interface Channel exends Closeable{}
```

#### 常见的Channel实现类 

- FileChannel:用于读取、写入、映射和操作文件的通道
- DatagramChannel:通过UDP读写网络中的通道
- socketChannel:通过TCP读写网络中的通道
- ServerSocketChannel：可以监听新进来的TCP连接，对每一个新进来的链接都会创建一个SocketChannel。



### NIO核心三：选择器（selector）

Selector是一个java NIO组件，可以能够检测一个或多个NIO通道，并确定哪些通道已经准备好读取或写入。这样，一个单线程可以管理多个channel，从而管理多个网络请求，提高效率。

#### 选择器（Selector)的应用

创建选择器：通过调用Select.open()方法创建一个selector

```java
Selector selector = Selector.open();
```

向选择器注册通道：SelectableChannel.register(Selector sel,int ops)

```java
//1. 获取通道
ServerSocketChannel = ssChannel = ServerSocketChannel.open();
//2. 切换到非阻塞模式
ssChannel.configureBlocking(false);
//3. 获取绑定
ssChannel.bind(new InetSocketAddress(9999));
//4. 获取选择器
Selector selector = Selector.open();
//5. 减通道注册到选择器上，并且制定“监听接收事件”
ssChannel.register(selector,SelectionKey.OP_ACCEPT);
```

### NIO非阻塞式网络通信原理分析

#### Selector示意图和特点说明

Selector可以实现：一个I/O线程可以并发处理N个客户端连接和读写操作，这从根本上解决了传统同步阻塞I/O一连接一线程模型，架构的性能、弹性伸缩能力和可靠性得到了极大的提升

![image-20210630200836366](https://raw.githubusercontent.com/li0228/image/master/image-20210630200836366.png)

- 每个channel都会对应一个Buffer
- 一个线程对应一个selecter，一个Selector对应多个channel
- 程序切换到哪个channel是由事件决定的
- selector会根据不同的时间，在各个通道上切换
- buffer就是一个内存快，底层是一个数组
- 数据的读写是通过buffer完成的，BIO中要么就是输入，要么就是输出，不能双向，但是NIO的Buffer是可以读也可写
- java NIO系统的核心在于：通道和缓冲区，通道表示打开到IO设备的连接，若需要使用NIO系统，需要获取用于连接IO设备的通道以及用于容纳数据的缓冲区，然后操作缓冲区，对数据进行书里。简而言之，channel负责传输，buffer负责存取数据

#### NIO非阻塞式网络通信入门案例

需求：服务端接收客户端连接请求，并接收多个客户端发送过来的事件

**代码案例**

**服务端**

```java
/**
 * 目标：NIO非阻塞通信下的入门案例
 * @author lihonghao
 * @date 2021/7/5 10:26
 */
public class Server {
	public static void main(String[] args) throws IOException {
		// 获取通道
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		// 配置成非阻塞模式
		socketChannel.configureBlocking(false);
		// 监听端口
		socketChannel.bind(new InetSocketAddress(9999));
		// 获取选择器
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT );

		while(selector.select() > 0){
			// 1.获取选择器中的所有注册的通道中已经就绪的事件
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// 开始遍历准备好的事件
			while(iterator.hasNext()){
				SelectionKey sk = iterator.next();
				// 判断事件的类型
				if(sk.isAcceptable()){
					SocketChannel accept = socketChannel.accept();
					accept.configureBlocking(false);
					// 将本客户端事件注册到选择
					accept.register(selector,SelectionKey.OP_READ);
				}else if(sk.isReadable()){
					// 获取读就绪事件
					SocketChannel socketChannel1 = (SocketChannel) sk.channel();
					// 读取数据
					ByteBuffer byf = ByteBuffer.allocate(2014);
					int len = 0;
					while ((len = socketChannel1.read(byf))>0){
						byf.flip();
						System.out.println(new String(byf.array(),0,len));
						byf.clear();
					}
				}
				iterator.remove();
			}
		}
	}


}

```

**客户端**

```java

/**
 * @author lihonghao
 * @date 2021/7/5 11:22
 */
public class Client {
	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
		socketChannel.configureBlocking(false);

		 ByteBuffer buffer = ByteBuffer.allocate(1204);
		 Scanner scanner = new Scanner(System.in);

		 while(true){
			 System.out.print("请说：");
			 String msg = scanner.nextLine();
			 buffer.put(("嘻嘻："+msg).getBytes());
			 buffer.flip();
			 socketChannel.write(buffer);
			 buffer.clear();
		 }
	}
}

```
