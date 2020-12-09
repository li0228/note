package com.lhh.invocation;

/**
 * @author lihonghao
 * @date 2020/12/9 14:38
 */
public class UserDao extends AbsUserDao implements IUserDao{
	@Override public void save() {
		System.out.println("保存数据");
	}
}
