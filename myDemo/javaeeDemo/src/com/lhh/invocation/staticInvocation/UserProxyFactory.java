package com.lhh.invocation.staticInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lihonghao
 * @date 2020/12/9 15:08
 */
public class UserProxyFactory {
	/**
	 * 	维护一个目标对象
	 */
	private Object target;

	public UserProxyFactory() {
	}

	public UserProxyFactory(Object target) {
		this.target = target;
	}

	/**
	 * 	为目标对象生成代理对象
	 */
	public Object getProxyInstance(){
		InvocationHandler h = new InvocationHandler() {
			@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("开启事务");
				// 执行目标对象方法
				Object returnValue = method.invoke(target, args);
				System.out.println("提交事务");
				return null;
			}
		};
		Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), h);
		return o;
	}
}
