package com.hl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.hl.model.Admin;

public class AdminDao {

	public Admin Login(Connection connection,Admin admin) throws SQLException {
		Admin resAdmin=null;
		String sqlString="select * from admin where adminName=? and adminPassword=?";
		PreparedStatement pstmt = connection.prepareStatement(sqlString);
		pstmt.setString(1, admin.getUserName());
		pstmt.setString(2, admin.getPassword());
		ResultSet rSet=pstmt.executeQuery();
		if (rSet.next()) {
			resAdmin=new Admin();
			resAdmin.setAdminId(rSet.getInt("adminId"));
			resAdmin.setUserName(rSet.getString("adminName"));
			resAdmin.setPassword(rSet.getString("adminPassword"));
		}
		return resAdmin;
	}
	
	/* *
	 * 修改admin密码 
	 * */
	public int adminUpdate(Connection con, int adminId, String password)throws Exception {
		String sql = "update admin set adminPassword=? where adminId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, password);
		pstmt.setInt(2, adminId);
		return pstmt.executeUpdate();
	}
}
