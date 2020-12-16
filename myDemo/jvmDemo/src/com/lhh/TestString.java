package com.lhh;

/**
 * @author lihonghao
 * @date 2020/12/16 17:00
 */
public class TestString {
	public static void main(String[] args) {
		String str = new String();
		System.out.println("hello");

		TestString test = new TestString();
		System.out.println(test.getClass().getClassLoader());

		Hello hello = new Hello();
		System.out.println();

	}
}
