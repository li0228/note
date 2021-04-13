package com.lhh.dao;

import com.lhh.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/12 19:38
 */
@Repository
public interface UserDao {
	@Select("select * from user")
	List<User> getUsers();
}