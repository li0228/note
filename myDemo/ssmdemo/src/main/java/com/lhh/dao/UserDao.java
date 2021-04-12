package com.lhh.dao;

import com.lhh.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/12 19:38
 */
@Repository
public interface UserDao {
	List<User> getUsers();
}
