package com.lhh.test01;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/4/9 16:23
 */
public class MyTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans01.xml");
		User user = context.getBean("user", User.class);
		System.out.println(user.getName());

	}
}
