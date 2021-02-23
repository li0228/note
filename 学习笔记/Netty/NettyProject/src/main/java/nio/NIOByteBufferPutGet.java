package nio;

import java.nio.ByteBuffer;

/**
 * @author lihonghao
 * @date 2021/2/21 21:40
 */
public class NIOByteBufferPutGet {
	public static void main(String[] args) {
		// 创建一个buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(64);

		// 类型化方式放入数据
		byteBuffer.putInt(100);
		byteBuffer.putLong(1);
		byteBuffer.putChar('李');
		byteBuffer.putShort((short)4);

		byteBuffer.flip();
		System.out.println(byteBuffer.get());
		System.out.println(byteBuffer.getLong());
		System.out.println(byteBuffer.getChar());
		System.out.println(byteBuffer.getShort());

		ByteBuffer buffer = ByteBuffer.allocate(64);
		for(int i = 0;i<64;i++){
			buffer.put((byte)i);
		}
		buffer.flip();

		ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
		System.out.println(readOnlyBuffer.getClass());

		// 读取
		while(readOnlyBuffer.hasRemaining()){
			System.out.println(readOnlyBuffer.get());
		}

		

	}
}
