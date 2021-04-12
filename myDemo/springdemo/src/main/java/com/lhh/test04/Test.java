package com.lhh.test04;

import com.lhh.test03.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author lihonghao
 * @date 2021/4/12 10:22
 */
public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test04/beans.xml");
		SomeService s = (SomeService)context.getBean("someServiceImpl");
		s.doSome();
	}
}
