package com.lhh.cglib;

/**
 * @author lihonghao
 * @date 2020/12/9 17:20
 */
public class TestProxy {
	public static void main(String[] args) {
		//目标对象
		UserDao target = new UserDao();
		System.out.println(target.getClass());
		//代理对象
		UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
		System.out.println(proxy.getClass());
		//执行代理对象方法
		proxy.save();
	}
}
