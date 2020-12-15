package com.lhh.invocation.proxy;

/**
 * @author lihonghao
 * @date 2020/12/9 14:40
 */
public class TestProxy {
	public static void main(String[] args) {
		// 目标对象
		IUserDao target = new UserDao();
		// 代理对象
		UserDaoProxy proxy = new UserDaoProxy(target);

		proxy.save();
	}
}
