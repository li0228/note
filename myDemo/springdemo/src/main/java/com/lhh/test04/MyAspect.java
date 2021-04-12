package com.lhh.test04;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 切面类
 * @date 2021/4/12 10:13
 */
@Component
@Aspect
public class MyAspect {
	/**
	 * 定义方法，方法是实现切面功能的
	 */
	@Before(value = "execution(public void com.lhh.test04.SomeServiceImpl.doSome())")
	public void myBefore(JoinPoint jp){
		// 获取方法的完整定义
		System.out.println("方法定义 = "+jp.getSignature());

		// 切面执行的功能代码
		System.out.println("前置通知"+ new Date());
	}

	/**
	 * 后置通知
	 */
	@After(value = "execution(* com.lhh.test04.*.*())")
	public void myAfter(){
		System.out.println("后置通知");
	}

	/**
	 * 有返回值的后置通知
	 * @param res
	 */
	@AfterReturning(value = "execution(* com.lhh.test04.*.*())" , returning = "res")
	public void myAfterRes(Object res){
		System.out.println("后置返回值通知");
		System.out.println(res);
	}

	/**
	 * 环绕通知,必须有一个返回值，要有一个参数.经常用来做事务，方法前开启事务，方法后提交事务
	 */
	@Around(value = "myPt()")
	public Object myhuanrao(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Object result = null;
		System.out.println("环绕通知前");

		result = proceedingJoinPoint.proceed();
		System.out.println("环绕通知后");
		return result;
	}

	@Pointcut(value = "execution(* com.lhh.test04.*.*())")
	public void myPt(){

	}
}
