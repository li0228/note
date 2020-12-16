package com.lhh;

/**
 * @author lihonghao
 * @date 2020/12/16 16:48
 */
public class ClassLoaderTest02 {
	public static void main(String[] args) {
		try {
			// 获取classLoader的几种方式
			//1
			ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
			System.out.println(classLoader);

			//2
			ClassLoader classLoader2 = ClassLoader.getSystemClassLoader().getParent();
			System.out.println(classLoader2);

			//3
			ClassLoader classLoader3 = Thread.currentThread().getContextClassLoader();
			System.out.println(classLoader3);

			// classLoader常用方法
			/**
			 * 1. findClass()
			 * 2.
			 */


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
