package com.lhh.demo.com.lhh.pojo;

import org.springframework.beans.factory.annotation.Value;
/**
 * @author lihonghao
 * @date 2021/4/8 17:09
 */

public class hello {
	@Value("name")
	private String name;

	public hello() {
	}

	public hello(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
