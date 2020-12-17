package com.lhh.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lihonghao
 * @date 2020/12/15 9:55
 */
public class TestOutPutStream {
	public static void main(String[] args) throws IOException {
		OutputStream output = new FileOutputStream("out/readme.txt");
		output.write(72); // H
		output.write(101); // e
		output.write(108); // l
		output.write(108); // l
		output.write(111); // o
		output.close();
		StringWriter buffer = new StringWriter();
		try (PrintWriter pw = new PrintWriter(buffer)) {
			pw.println("Hello");
			pw.println(12345);
			pw.println(true);
		}
		System.out.println(buffer);
		byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt"));
		System.out.println(bytes.length);

	}
}
