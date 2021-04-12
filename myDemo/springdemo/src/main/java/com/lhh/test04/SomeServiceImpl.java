package com.lhh.test04;

import org.springframework.stereotype.Service;

/**
 * @author lihonghao
 * @date 2021/4/12 10:11
 */
@Service()
public class SomeServiceImpl implements SomeService{
	@Override
	public void doSome() {
		System.out.println("do some!!");
	}
}
