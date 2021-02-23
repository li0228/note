package nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lihonghao
 * @date 2021/2/21 19:59
 */
public class NIOFileChannel01 {
	public static void main(String[] args) throws IOException {
		String str = "hello,word";

		// 创建一个输出流
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");


		FileChannel channel = fileOutputStream.getChannel();

		// 创建一个缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

		byteBuffer.put(str.getBytes());

		byteBuffer.flip();

		channel.write(byteBuffer);
		fileOutputStream.close();

	}
}
