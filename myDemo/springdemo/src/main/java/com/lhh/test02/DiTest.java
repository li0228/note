package com.lhh.test02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/4/10 10:38
 */
public class DiTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test02/beans01.xml");
		Student student = (Student)context.getBean("student");
		System.out.println(student);
	}
}
