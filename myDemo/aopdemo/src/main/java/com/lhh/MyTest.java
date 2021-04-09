package com.lhh;

import com.lhh.service.UserService;
import com.lhh.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/4/9 12:18
 */
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		UserService userService = context.getBean("UserService", UserService.class);
		userService.add();
	}
}
