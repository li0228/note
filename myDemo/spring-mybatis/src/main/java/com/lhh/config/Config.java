package com.lhh.config;

import com.lhh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lihonghao
 * @date 2021/4/14 20:15
 */
@Configurable
@ComponentScan("com.lhh")
public class Config {

	@Autowired
	User user;

	public static void main(String[] args) {

	}

}
