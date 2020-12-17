package com.lhh.invocation.proxy;

/**
 * @author lihonghao
 * @date 2020/12/9 14:38
 */
public class UserDaoProxy implements IUserDao {
	// 代理目标对象
	private IUserDao target;

	public UserDaoProxy() {
	}

	public UserDaoProxy(IUserDao target) {
		this.target = target;
	}

	@Override public void save() {
		System.out.println("开启事务");
		target.save();
		System.out.println("提交事务");
	}
}
