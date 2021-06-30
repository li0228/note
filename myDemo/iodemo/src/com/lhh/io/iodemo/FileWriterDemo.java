package com.lhh.io.iodemo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author lihonghao
 * @date 2021/6/29 21:15
 */
public class FileWriterDemo {
	public static void main(String[] args) {
		try {
			FileWriter outputStreamWriter = new FileWriter("demo.txt");
			outputStreamWriter.write("hello");
			outputStreamWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
