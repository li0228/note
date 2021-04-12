package com.lhh.controller;

import com.lhh.pojo.User;
import com.lhh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/12 19:43
 */

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "list";
	}
}
