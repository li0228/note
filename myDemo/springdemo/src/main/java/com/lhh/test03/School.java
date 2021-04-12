package com.lhh.test03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lihonghao
 * @date 2021/4/10 14:19
 */
@Component
public class School {
	@Value("人民大学")
	private String name;
	@Autowired
	private Student student;

	public School() {
	}

	public School(String name, Student student) {
		this.name = name;
		this.student = student;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "School{" + "name='" + name + '\'' + ", student=" + student + '}';
	}
}
