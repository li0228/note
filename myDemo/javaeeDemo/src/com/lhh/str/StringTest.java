package com.lhh.str;

/**
 * @author lihonghao
 * @date 2021/4/2 9:48
 */
public class StringTest {
	public static void main(String[] args) {
		String str1 = new StringBuilder("hei").append("hei").toString();
		System.out.println(str1);
		System.out.println(str1.intern());
		System.out.println(str1 == str1.intern()); // 1

		System.out.println();
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2);
		System.out.println(str2.intern());
		System.out.println(str2 == str2.intern()); // 1

	}

}
