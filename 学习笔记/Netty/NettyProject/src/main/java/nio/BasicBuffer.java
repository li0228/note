package nio;

import java.nio.IntBuffer;

/**
 * @author lihonghao
 * @date 2021/2/13 15:52
 */
public class BasicBuffer {
	public static void main(String[] args) {
		// 可以存放5个int的buffer
		IntBuffer allocate = IntBuffer.allocate(5);

		allocate.put(1);
		allocate.put(2);
		allocate.put(4);
		allocate.put(3);
		allocate.put(5);
		allocate.flip();
		while (allocate.hasRemaining()){
			System.out.println(allocate.get());
		}
	}
}
