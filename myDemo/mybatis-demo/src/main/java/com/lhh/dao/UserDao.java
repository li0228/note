package com.lhh.dao;

import com.lhh.pojo.User;
import com.lhh.vo.QueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lihonghao
 * @date 2021/4/13 17:47
 */
public interface UserDao {
	public List<User> getUsers(@Param("userId") int id);

	public List<User> getUserById(QueryParam queryParam);

	public List<User> getUserByIds(List list);
}
