package com.lhh.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 日志类
 *
 * @author lihonghao
 * @date 2021/4/9 11:43
 */

public class Log implements MethodBeforeAdvice {
	// 要执行的目标对象的方法
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println(target.getClass().getName()+"的"+method.getName()+"被执行了");
	}
}
