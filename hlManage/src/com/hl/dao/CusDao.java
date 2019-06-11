package com.hl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hl.model.Customer;
import com.hl.model.PageBean;
import com.hl.util.DateUtil;
import com.hl.util.StringUtil;

public class CusDao {

	DateUtil dateUtil = new DateUtil();


	public int isExistCus(Connection connection, Customer customer) throws SQLException {
		String sql = "select count(*) from customer where cus_name = ? and cus_phone = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, customer.getCusName());
		pstmt.setString(2, customer.getCusPhone());
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
	
	/* 删除用户 */
	public int cusDel(Connection con, String cusId) throws SQLException {
		String sql = "delete from customer where cus_id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, cusId);
		return pstmt.executeUpdate();
	}

	/* 用户 列表 */
	public List<Customer> getCustomers(Connection connection, PageBean pageBean, Customer customer) throws SQLException {
		List<Customer> customers=new ArrayList<Customer>();
		StringBuffer sb =new StringBuffer( "select t1.*,t2.user_name from customer t1,user t2 where t2.user_id=t1.cus_userId order by cus_tjtime desc ");
		if(StringUtil.isNotEmpty(customer.getCusName())) {
			sb.append(" and cus_name= '"+customer.getCusName()+"'");
		}
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = connection.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Customer customer1 = new Customer();
			customer1.setCusId(rs.getInt(1));
			customer1.setCusName(rs.getString(2));
			customer1.setCusSex(rs.getString(3));
			customer1.setCusPhone(rs.getString(4));
			customer1.setCusArea(rs.getInt(5));
			customer1.setCusDate(rs.getString(6));
			customer1.setCusUserId(rs.getInt(7));
			customer1.setCusStat(rs.getString(8));
			customer1.setCusTjTime(rs.getString(9));
			customer1.setCusUser(rs.getString(10));
			customers.add(customer1);
		}
		return customers;
	}

	public static boolean haveCustomerByName(Connection con, String userName) throws Exception {
		String sql = "select * from user t1 where t1.user_name=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	/* 展示客户信息 */
	public static Customer customerShow(Connection con, String cusId) throws SQLException {
		String sql = "select t1.*,t2.user_name from customer t1,user t2 where t1.cus_id=? and t2.user_id=t1.cus_userId ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, cusId);
		ResultSet rs = pstmt.executeQuery();
		Customer customer1 = new Customer();
		while (rs.next()) {
			customer1.setCusId(rs.getInt(1));
			customer1.setCusName(rs.getString(2));
			customer1.setCusSex(rs.getString(3));
			customer1.setCusPhone(rs.getString(4));
			customer1.setCusArea(rs.getInt(5));
			customer1.setCusDate(rs.getString(6));
			customer1.setCusUserId(rs.getInt(7));
			customer1.setCusStat(rs.getString(8));
			customer1.setCusTjTime(rs.getString(9));
			customer1.setCusUser(rs.getString(10));
		}
		return customer1;
	}

	public int customerCount(Connection con, Customer customer) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total  from customer where 1=1");
		if (StringUtil.isNotEmpty(customer.getCusName())) {
			sb.append(" and cus_name= '" + customer.getCusName() + "'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	/* 删除客户信息 */
	public static int customerDel(Connection con, String cusId) throws SQLException {
		String sql = "delete from customer where cus_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		System.out.println(cusId);
		pstmt.setString(1, cusId);
		return pstmt.executeUpdate();
	}

	/*
	public int cusUpdate(Connection con, Customer customer) throws SQLException {
		String sql="update customer set cus_name=?,cus_sex=?,cus_phone=?,cus_area=?,cus_userId=?,cus_stat=?  where cus_id=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1,customer.getCusName() );
		pstm.setString(2, customer.getCusSex());
		pstm.setString(3, customer.getCusPhone());
		pstm.setInt(4, customer.getCusArea());
		pstm.setInt(5, customer.getCusUserId());
		pstm.setString(6, customer.getCusStat());
		pstm.setInt(7, customer.getCusId());
		int a =pstm.executeUpdate();
		System.out.println(a);
		return a;
	}
	*/
	
	public int cusUpdate(Connection con, Customer customer) throws SQLException {
		String sql="update customer set cus_stat=?  where cus_id=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, customer.getCusStat());
		pstm.setInt(2, customer.getCusId());
		int a =pstm.executeUpdate();
		System.out.println(a);
		return a;
	}

}