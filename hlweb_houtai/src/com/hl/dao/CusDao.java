package com.hl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hl.model.Customer;
import com.hl.util.DateUtil;

public class CusDao {

	DateUtil dateUtil = new DateUtil();

	public int addCus(Connection connection, Customer customer) throws SQLException {

		String sql = "insert into customer values(null,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, customer.getCusName());
		pstmt.setString(2, customer.getCusSex());
		pstmt.setString(3, customer.getCusPhone());
		pstmt.setString(4, customer.getCusArea());
		pstmt.setString(5, customer.getCusDate());
		pstmt.setInt(6, customer.getCusUserId());
		pstmt.setString(7, "null");
//		pstmt.setString(7, customer.getCusGuwen());   
		pstmt.setString(8, "等候审核");								//设置客户默认状态为等候审核
		pstmt.setTimestamp(9, new Timestamp(new Date().getTime()));		//获取推介时间戳
		return pstmt.executeUpdate();
	}

	public int isExistCus(Connection connection, Customer customer) throws SQLException {	//根据手机号判断客户是否被推介
		String sql1 = "select count(*) from customer where cus_phone = ?";
		String sql2 = "select count(*) from customer where cus_phone = ? and cus_stat != '等候审核'";
//		String sql2 = "select cus_stat from customer where cus_phone = ?";
		PreparedStatement pstmt1 = connection.prepareStatement(sql1);
		PreparedStatement pstmt2 = connection.prepareStatement(sql2);
		pstmt1.setString(1, customer.getCusPhone());
		pstmt2.setString(1, customer.getCusPhone());
		ResultSet executeQuery1 = pstmt1.executeQuery();
		ResultSet executeQuery2 = pstmt2.executeQuery();
		System.out.println("判断是否被推介...............");
		System.out.println("客户状态");
		//String str = executeQuery2.getString(1);
		//System.out.println(str);
		while (executeQuery1.next() && executeQuery2.next()) {
//		while (executeQuery2.next()) {	
//			String str = executeQuery2.getString(1);
//			System.out.println(str);
//			if (executeQuery1.getInt(1) > 0 ) {	
			if(executeQuery2.getInt(1)>0) {
//			if (str.equals("等候审核")) {
				System.out.println("审核返回不可被推介");
				return 1;
			} else {
				System.out.println("审核返回可推介");
				return 0;
			}
		}
		return 0;
	}

	public List<Customer> getCustomers(Connection connection, int userId) throws SQLException {
		String sql = "select * from customer where cus_userId = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet executeQuery = pstmt.executeQuery();
		List<Customer> customers = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:SS");
		while (executeQuery.next()) {
			Customer customer = new Customer();
			customer.setCusId(executeQuery.getInt(1));
			customer.setCusName(executeQuery.getString(2));
			customer.setCusPhone(executeQuery.getString(4));
			customer.setCusArea(executeQuery.getString(5));
			customer.setCusDate(executeQuery.getString(6));
			customer.setCusGuwen(executeQuery.getString(8));
			customer.setCusStat(executeQuery.getString(9));
			customer.setCusTjtime(format.format(new Date(executeQuery.getTimestamp(10).getTime())));
			customers.add(customer);
		}
		return customers;
	}

}