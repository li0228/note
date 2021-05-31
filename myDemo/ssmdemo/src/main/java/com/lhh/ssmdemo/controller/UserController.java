package com.lhh.ssmdemo.controller;

import com.lhh.ssmdemo.entity.User;
import com.lhh.ssmdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/5/24 16:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}
}
