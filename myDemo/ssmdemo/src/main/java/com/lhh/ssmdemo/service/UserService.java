package com.lhh.ssmdemo.service;

import com.lhh.ssmdemo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/5/24 14:59
 */

public interface UserService {
	// 查询所有用户
	List<User> findAll();
}
