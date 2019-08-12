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

//import sun.security.timestamp.TSRequest;

public class CusDao {

	DateUtil dateUtil = new DateUtil();


	public int isExistCus(Connection connection, Customer customer) throws SQLException {
		String sql = "select count(*) from customer where cus_name = ? and cus_phone = ?"; //姓名电话判断客户是否存在
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
	
	
//	/* 根据手机号查找客户 */
//	public static List<Customer> getCustomerByPhone(Connection connection, String cusPhone) throws SQLException {
//		List<Customer> customers=new ArrayList<Customer>();
//		
//		//StringBuffer sb1 =new StringBuffer( "select t1.*,t2.user_name,t2.user_jigou,t2.user_tjname from customer t1,user t2 where t2.user_id=t1.cus_userId order by t1.cus_tjtime desc ");
//		StringBuffer sb2 =new StringBuffer( "select * from customer where cus_phone = ?  ");
//		System.out.println(sb2);
//		PreparedStatement pstmt = connection.prepareStatement(sb2.toString());
//		ResultSet rs = pstmt.executeQuery();
//		while (rs.next()) {   
//			Customer customer1 = new Customer();
//			customer1.setCusId(rs.getInt("cus_id"));   					//从数据库中查询信息，需要匹配数据库中相关字段
//			customer1.setCusName(rs.getString("cus_name"));
//			customer1.setCusSex(rs.getString("cus_sex"));
//			customer1.setCusPhone(rs.getString("cus_phone"));
//			customer1.setCusArea(rs.getInt("cus_area"));
//			customer1.setCusDate(rs.getString("cus_date"));
//			customer1.setCusUserId(rs.getInt("cus_userId"));
//			customer1.setCusGuwen(rs.getString("cus_guwen")); 
//			customer1.setCusStat(rs.getString("cus_stat"));
//			customer1.setCusTjTime(rs.getString("cus_tjtime"));
//			customer1.setCusUser(rs.getString("user_name"));
//			customer1.setCusUserJigou(rs.getString("user_jigou")); //获取中介机构
//			customer1.setCusUserTjname(rs.getString("user_tjname"));
//			customers.add(customer1);
//			
//			
//		}
//		System.out.println(customers);
//		return customers;
//	}

	/* 用户 列表 */
	public List<Customer> getCustomers(Connection connection, PageBean pageBean, Customer customer) throws SQLException {
		List<Customer> customers=new ArrayList<Customer>();
		
		//StringBuffer sb1 =new StringBuffer( "select t1.*,t2.user_name,t2.user_jigou,t2.user_tjname from customer t1,user t2 where t2.user_id=t1.cus_userId order by t1.cus_tjtime desc ");
		StringBuffer sb2 =new StringBuffer( "select t1.*,t2.user_name,t2.user_jigou,t2.user_tjname from customer t1,user t2 where t2.user_id=t1.cus_userId  ");
		if(StringUtil.isNotEmpty(customer.getCusName())) {
			sb2.append(" and cus_name= '"+customer.getCusName()+"' order by t1.cus_tjtime desc");
			System.out.println(sb2.toString());
		}
		if(StringUtil.isNotEmpty(customer.getCusPhone())) {
			sb2.append(" and cus_phone= '"+customer.getCusPhone()+"' order by t1.cus_tjtime desc");
			System.out.println(sb2.toString());
		}
		if(StringUtil.isNotEmpty(customer.getCusGuwen())) {
			sb2.append(" and cus_guwen= '"+customer.getCusGuwen()+"' order by t1.cus_tjtime desc");
			System.out.println(sb2.toString());
		}
		if(pageBean != null) {
			sb2.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = connection.prepareStatement(sb2.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {   
			Customer customer1 = new Customer();
			customer1.setCusId(rs.getInt("cus_id"));   					//从数据库中查询信息，需要匹配数据库中相关字段
			customer1.setCusName(rs.getString("cus_name"));
			customer1.setCusSex(rs.getString("cus_sex"));
			customer1.setCusPhone(rs.getString("cus_phone"));
			customer1.setCusArea(rs.getInt("cus_area"));
			customer1.setCusDate(rs.getString("cus_date"));
			customer1.setCusUserId(rs.getInt("cus_userId"));
			customer1.setCusGuwen(rs.getString("cus_guwen")); 
			customer1.setCusStat(rs.getString("cus_stat"));
			customer1.setCusTjTime(rs.getString("cus_tjtime"));
			customer1.setCusUser(rs.getString("user_name"));
			customer1.setCusUserJigou(rs.getString("user_jigou")); //获取中介机构
			customer1.setCusUserTjname(rs.getString("user_tjname"));
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
		String sql = "select t1.*,t2.user_name,t2.user_jigou,t2.user_tjname from customer t1,user t2 where t1.cus_id=? and t2.user_id=t1.cus_userId ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, cusId);
		ResultSet rs = pstmt.executeQuery();
		Customer customer1 = new Customer();
		while (rs.next()) {
			customer1.setCusId(rs.getInt("cus_id"));
			customer1.setCusName(rs.getString("cus_name"));
			customer1.setCusSex(rs.getString("cus_sex"));
			customer1.setCusPhone(rs.getString("cus_phone"));
			customer1.setCusArea(rs.getInt("cus_area"));
			customer1.setCusDate(rs.getString("cus_date"));
			customer1.setCusUserId(rs.getInt("cus_userId"));
			customer1.setCusGuwen(rs.getString("cus_guwen"));
			customer1.setCusStat(rs.getString("cus_stat"));
			customer1.setCusTjTime(rs.getString("cus_tjtime"));
			customer1.setCusUser(rs.getString("user_name"));
			customer1.setCusUserJigou(rs.getString("user_jigou"));
			customer1.setCusUserTjname(rs.getString("user_tjname"));
		
			
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