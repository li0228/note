package com.lhh;

import java.sql.*;

/**
 * @author lihonghao
 * @date 2021/4/13 9:48
 */
public class jdbcTest {
	// jdbc

	public static void main(String[] args) throws SQLException {
		// 注册驱动
		Driver driver = new com.mysql.jdbc.Driver();
		DriverManager.registerDriver(driver);

		// 获取连接
		String url = "jdbc:mysql://192.168.16.2:10000/lihonghao";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url,user,password);

		// 获取数据库操作对象
		Statement statement = conn.createStatement();
		// 执行sql语句
		String sql = "insert into user(name,age) value('老王',30)";
		int i = statement.executeUpdate(sql);
		System.out.println(i);
		// 关闭资源
		statement.close();
		conn.close();
	}
}
