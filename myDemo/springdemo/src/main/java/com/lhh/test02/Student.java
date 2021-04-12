package com.lhh.test02;

/**
 * @author lihonghao
 * @date 2021/4/10 10:31
 */
public class Student {
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
