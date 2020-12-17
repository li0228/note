package com.lhh.io;

import java.io.*;

/**
 * @author lihonghao
 * @date 2020/12/14 20:09
 */
public class TestInputStream {
	public static void main(String[] args) throws IOException {
		File file;
		InputStream input = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.txt");
		//创建一个数组长度为4的数组
		byte[] b=new byte[1024];
		//i.read(b)计算读取了多少个元素，如果最后没有元素了，则返回-1
		int c=0;
		while ((c=input.read(b)) != -1){
			//这个String方法  中间是从数组下标为0的开始读取，b是读取4个，c是得出用数组读取了几个元素
			System.out.println(new String(b,0,c));
		}
		input.close(); // 关闭流

	}
}
