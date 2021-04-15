package com.lhh.dao;

import com.lhh.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/15 10:43
 */
@Repository
public interface UserDao {
	public List<User> getUsers();
}
