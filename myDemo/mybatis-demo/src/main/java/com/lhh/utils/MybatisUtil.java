package com.lhh.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lihonghao
 * @date 2021/4/13 20:54
 */
public class MybatisUtil {
	private static SqlSessionFactory factory = null;
	static {
		String config = "mybatis-conf.xml";
		try {
			InputStream in = Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取sqlsession方法
	public static SqlSession getSqlSession(){
		SqlSession sqlSession = null;
		if(factory != null){
			sqlSession = factory.openSession();// 非自动提交事务
		}
		return sqlSession;
	}
}
