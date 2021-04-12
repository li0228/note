package com.lhh.test03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/4/10 10:38
 */
public class DiTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test03/bean.xml");
		School s = (School)context.getBean("school");
		System.out.println(s);
	}
}
