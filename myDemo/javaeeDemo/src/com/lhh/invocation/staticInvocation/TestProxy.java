package com.lhh.invocation.staticInvocation;

/**
 * @author lihonghao
 * @date 2020/12/9 15:15
 */
public class TestProxy {
	public static void main(String[] args) {
		IUserDao target = new UserDao();
		System.out.println(target.getClass());
		IUserDao proxy = (IUserDao) new UserProxyFactory(target).getProxyInstance();
		System.out.println(proxy.getClass());
		proxy.save();
	}

}
