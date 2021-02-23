package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lihonghao
 * @date 2021/2/21 20:23
 */
public class NIOfileChannel02 {

	public static void main(String[] args) throws IOException {
		// 创建文件输入流
		File file = new File("d:\\file01.txt");

		FileInputStream fileInputStream = new FileInputStream(file);

		FileChannel channel = fileInputStream.getChannel();

		ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

		channel.read(byteBuffer);

		System.out.println(new String(byteBuffer.array()));

		fileInputStream.close();

	}


}
