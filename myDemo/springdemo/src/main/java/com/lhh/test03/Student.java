package com.lhh.test03;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lihonghao
 * @date 2021/4/10 10:31
 */
@Component(value = "student")
public class Student {
	@Value("lhh")
	private String name;


	public Student() {
	}

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + '}';
	}
}
