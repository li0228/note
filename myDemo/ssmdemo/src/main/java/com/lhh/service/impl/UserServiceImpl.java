package com.lhh.service.impl;

import com.lhh.dao.UserDao;
import com.lhh.pojo.User;
import com.lhh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/12 19:41
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}
}
