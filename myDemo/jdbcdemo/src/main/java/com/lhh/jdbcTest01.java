package com.lhh;

import java.sql.*;

/**
 * @author lihonghao
 * @date 2021/4/13 10:31
 */
public class jdbcTest01 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			// 注册驱动（可以用反射）
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);

			// 获取连接（用户信息抽到配置信息里面）
			String url = "jdbc:mysql://192.168.16.2:10000/lihonghao";
			String user = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, user, password);

//			String sql = "delete from user where id = ?";
//			// 获取数据库操作对象
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, 1);
//			// 执行sql语句
//			int i = ps.executeUpdate();

//			String sql = "insert into user(name,age) values(?,?)";
//			ps = conn.prepareStatement(sql);
//			ps.setString(1,"lihonghao");
//			ps.setInt(2,23);
//			int i = ps.executeUpdate();
//			System.out.println(i);
//
//			String sql = "update user set name = ? ,age = ? where id = ?";
//			ps= conn.prepareStatement(sql);
//			ps.setString(1,"laowang");
//			ps.setInt(2,12);
//			ps.setInt(3,4);
//			int i = ps.executeUpdate();
//			System.out.println(i);

			String sql = "delete from user where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,4);
			int i = ps.executeUpdate();
			System.out.println(i);

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(ps != null){
				try {
					ps.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
				try {
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

}
