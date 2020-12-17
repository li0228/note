package com.lhh;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.security.ec.CurveDB;

import java.net.URL;

/**
 * @author lihonghao
 * @date 2020/12/16 14:58
 */
public class TestClassloader {
	public static void main(String[] args) {
		/*
		 * 获取bootstrapClassLoader能够加载的api路径
		 */
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(URL element:urls){
			System.out.println(element.toExternalForm());
		}
		ClassLoader classLoader = Provider.class.getClassLoader();
		System.out.println(classLoader);

		System.out.println("================扩展类加载器=================");
		String property = System.getProperty("java.ext.dirs");
		for (String path : property.split(";")){
			System.out.println(path);
		}
		ClassLoader classLoader1 = CurveDB.class.getClassLoader();
		System.out.println(classLoader1);

	}
}
