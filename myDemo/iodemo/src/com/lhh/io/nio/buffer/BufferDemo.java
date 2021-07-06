package com.lhh.io.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author lihonghao
 * @date 2021/7/1 11:50
 */
public class BufferDemo {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		String s = new String("hello");

		buffer.put(s.getBytes());
		System.out.println(buffer.limit());
		System.out.println(buffer.position());
	}
}
