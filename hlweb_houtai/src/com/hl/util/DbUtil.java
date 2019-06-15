package com.hl.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	public Connection getCon() throws Exception {
		Class.forName(PropertiesUtil.getValue("jdbcName", "/hl.properties"));
		Connection con = DriverManager.getConnection(PropertiesUtil.getValue("dbUrl", "/hl.properties"),
				PropertiesUtil.getValue("dbUserName", "/hl.properties"),
				PropertiesUtil.getValue("dbPassword", "/hl.properties"));
		return con;
	}

	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("no");
			e.printStackTrace();
		}
	}
}
