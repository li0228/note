package com.lhh.ssmdemo.service.impl;

import com.lhh.ssmdemo.dao.UserDao;
import com.lhh.ssmdemo.entity.User;
import com.lhh.ssmdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/5/24 15:00
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public List<User> findAll() {
		return userDao.findAll();
	}
}
