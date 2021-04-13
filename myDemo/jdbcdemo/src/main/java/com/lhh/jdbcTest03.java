package com.lhh;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author lihonghao
 * @date 2021/4/13 10:47
 */
public class jdbcTest03 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try{

			Locale locale;
			ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
			// 注册驱动
			String driver = resourceBundle.getString("driver");
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");

			// 注册驱动
			Class.forName(driver);
			// 获取连接对象
			Connection connection = DriverManager.getConnection(url, user, password);
			// 获取数据库操作对象
			// 执行sql语句
			String sql = "select * from user ";
			ps= connection.prepareStatement(sql);


			ResultSet resultSet = ps.executeQuery();


			while (resultSet.next()){
				int anInt = resultSet.getInt(1);
				String string = resultSet.getString(2);
				int anInt1 = resultSet.getInt(3);
				System.out.println(anInt + "  "+string+"  "+anInt1);
			}

		}catch(SQLException | ClassNotFoundException e){
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
