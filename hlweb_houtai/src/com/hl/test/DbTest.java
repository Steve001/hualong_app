package com.hl.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hl.model.User;
import com.hl.util.DbUtil;
import com.mysql.jdbc.Connection;

public class DbTest {

	public User Login(Connection con)throws Exception {
		User user=null;
		String sql = "select * from user where user_id=1 ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			user=new User();
			user.setUserName(rs.getString("user_name"));
			user.setUserPhone(rs.getString("user_phone"));
			user.setUserPassword(rs.getNString("user_password"));
		}
		return user;
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		Connection con=null;
		DbTest dt=new DbTest();
		try {
			con=(Connection) dbUtil.getCon();
			User user=dt.Login(con);
			System.out.println(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
