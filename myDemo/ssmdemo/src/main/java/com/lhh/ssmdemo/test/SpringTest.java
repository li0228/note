package com.lhh.ssmdemo.test;

import com.lhh.ssmdemo.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihonghao
 * @date 2021/5/24 14:53
 */
public class SpringTest {
	@Test
	public void test1() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService userService = (UserService) ac.getBean("userServiceImpl");
		userService.findAll();
	}

}
