package com.lhh.controller;

import com.lhh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lihonghao
 * @date 2021/4/14 20:14
 */
public class UserController {
	@Autowired
	User user;

	public void test(){
		int age = user.getAge();
	}

}
