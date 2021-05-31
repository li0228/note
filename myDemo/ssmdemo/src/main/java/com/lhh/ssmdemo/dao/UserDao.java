package com.lhh.ssmdemo.dao;

import com.lhh.ssmdemo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/5/24 14:57
 */
@Repository
public interface UserDao {
	// 查询所有用户
	List<User> findAll();
}
