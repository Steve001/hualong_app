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

		String sql = "insert into customer values(null,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, customer.getCusName());
		pstmt.setString(2, customer.getCusSex());
		pstmt.setString(3, customer.getCusPhone());
		pstmt.setString(4, customer.getCusArea());
		pstmt.setString(5, customer.getCusDate());
		pstmt.setInt(6, customer.getCusUserId());
		pstmt.setString(7, "已推介");
		pstmt.setTimestamp(8, new Timestamp(new Date().getTime()));
		return pstmt.executeUpdate();
	}

	public int isExistCus(Connection connection, Customer customer) throws SQLException {
		String sql = "select count(*) from customer where cus_phone = ?";
		//String sql = "select count(*) from customer where cus_name = ? and cus_phone = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		//pstmt.setString(1, customer.getCusName());
		pstmt.setString(1, customer.getCusPhone());
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
			customer.setCusStat(executeQuery.getString(8));
			customer.setCusTjtime(format.format(new Date(executeQuery.getTimestamp(9).getTime())));
			customers.add(customer);
		}
		return customers;
	}

}