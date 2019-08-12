package com.hl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;

import com.hl.model.User;

public class UserDao {

	public User userLogin(Connection con, User user) throws SQLException {
		User resUser = null;
		String sql = "select * from user where user_phone=? and user_password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserPhone());
		pstmt.setString(2, user.getUserPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resUser = new User();
			resUser.setUserId(rs.getInt("user_id"));
			resUser.setUserJigou(rs.getNString("user_jigou"));
			resUser.setUserTjName(rs.getNString("user_tjname"));
			resUser.setUserName(rs.getString("user_name"));
			resUser.setUserPassword(rs.getString("user_password"));
			resUser.setUserPhone(rs.getString("user_phone"));
		}
		return resUser;
	}

	public int isExistUser(Connection connection, User user) throws SQLException {
		String sql = "select count(*) from user where user_phone = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getUserPhone());
		ResultSet executeQuery = pstmt.executeQuery();
		while (executeQuery.next()) {
			if (executeQuery.getInt(1) > 0) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	
	public int isExistUser_reg(Connection connection, User user) throws SQLException {
		String sql = "select count(*) from user_reg where user_phone = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, user.getUserPhone());
		ResultSet executeQuery = pstmt.executeQuery();
		while (executeQuery.next()) {
			if (executeQuery.getInt(1) > 0) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	
//	public int addUser(Connection con, User user) throws SQLException {
//		String sql = "insert into user(user_name,user_phone,user_password,user_jigou,user_tjname) values(?,?,?,?,?)";
//		PreparedStatement pstm = con.prepareStatement(sql);
//		pstm.setString(1, user.getUserName());
//		pstm.setString(2, user.getUserPhone());
//		pstm.setString(3, user.getUserPassword());
//		pstm.setString(4, user.getUserJigou());
//		pstm.setString(5, user.getUserTjName());
//		return pstm.executeUpdate();
//	}
	
	//将注册页面的注册信息写入新表中
	public int addUserReg(Connection con, User user) throws SQLException {
		String sql = "insert into user_reg(user_name,user_phone,user_password,user_jigou,user_tjname,user_regtime) values(?,?,?,?,?,?)";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getUserPhone());
		pstm.setString(3, user.getUserPassword());
		pstm.setString(4, user.getUserJigou());
		pstm.setString(5, user.getUserTjName());
		pstm.setTimestamp(6, new Timestamp(new Date().getTime()));	            	//获取中介注册时间戳
		return pstm.executeUpdate();
	}
	
}
