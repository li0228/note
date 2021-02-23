package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lihonghao
 * @date 2021/2/21 20:35
 */
public class NIOfileChannel03 {
	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("1.txt");

		FileChannel channel1 = fileInputStream.getChannel();

		FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
		FileChannel channel2 = fileOutputStream.getChannel();

		ByteBuffer b = ByteBuffer.allocate(512);
		while (true){
			b.clear();
			int read = channel1.read(b);
			if(read == -1){
				break;
			}
			// buffer -> channel02
			b.flip();
			channel2.write(b);
		}
		fileInputStream.close();
		fileOutputStream.close();
	}
}
