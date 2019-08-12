package com.hl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hl.model.PageBean;
import com.hl.model.User;
import com.hl.util.StringUtil;

public class UserDao {


	/* 中介列表 */
	public List<User> userManageList(Connection con, PageBean pageBean, User user) throws SQLException{
		List<User> users=new ArrayList<User>();
		StringBuffer sb=new StringBuffer("select * from user where 1=1");
		if(StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and user_name= '"+user.getUserName()+"'");
		}
		if(StringUtil.isNotEmpty(user.getUserPhone())) {
			sb.append(" and user_phone= '"+user.getUserPhone()+"'");
		}
		if(pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			User resUser=new User();
			resUser.setUserId(rs.getInt("user_id"));
			resUser.setUserJigou(rs.getNString("user_jigou"));
			resUser.setUserName(rs.getString("user_name"));
			resUser.setUserPassword(rs.getString("user_password"));
			resUser.setUserPhone(rs.getString("user_phone"));
			resUser.setUserTjname(rs.getNString("user_tjname"));
			users.add(resUser);
		}
		return users;
	}
	
	/* 添加业务员 */
	public int addUser(Connection con,User user) throws SQLException {
		String sql="insert into user(user_name,user_phone,user_password,user_jigou,user_tjname) values(?,?,?,?,?)";
		PreparedStatement pstm=con.prepareStatement(sql);
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getUserPhone());
		pstm.setString(3, user.getUserPassword());
		pstm.setString(4, user.getUserJigou());
		pstm.setString(5, user.getUserTjname());
		return pstm.executeUpdate();
		
	}
	
	/* 删除业务员 */
	public int userDel(Connection con, String userId) throws SQLException {
		String sql = "delete from user where user_id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userId);
		return pstmt.executeUpdate();
	}
	
	/*展示业务员 */
	public User userShow(Connection con, String userId) throws SQLException {
		String sql = "select * from user t1 where t1.user_id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userId);
		ResultSet rs=pstmt.executeQuery();
		User resUser=new User();
		while (rs.next()) {
			resUser.setUserId(rs.getInt("user_id"));
			resUser.setUserJigou(rs.getNString("user_jigou"));
			resUser.setUserTjname(rs.getNString("user_tjname"));
			resUser.setUserName(rs.getString("user_name"));
			resUser.setUserPassword(rs.getString("user_password"));
			resUser.setUserPhone(rs.getString("user_phone"));
		}
		return resUser;
	}
	
	public int userUpdate(Connection con, User user) throws SQLException {
		String sql="update user set user_name=?,user_phone=?,user_password=?,user_jigou=?,user_tjname=? where user_id=?";
		PreparedStatement pstm=con.prepareStatement(sql);
		
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getUserPhone());
		pstm.setString(3, user.getUserPassword());
		pstm.setString(4, user.getUserJigou());
		pstm.setString(5, user.getUserTjname());
		pstm.setInt(6, user.getUserId());
		/*
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getUserPhone());
		pstm.setString(3, user.getUserPassword());
		pstm.setString(4, user.getUserJigou());
		pstm.setInt(5, user.getUserId());
		pstm.setString(6, user.getUserTjname());
		*/
		return pstm.executeUpdate();
	}
	
	public int userCount(Connection con, User user) throws SQLException {
		StringBuffer sb=new StringBuffer("select count(*) as total  from user where 1=1");
		if(StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and user_name= '"+user.getUserName()+"'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	
	public boolean haveUserByName(Connection con, String userName) throws Exception {
		String sql = "select * from user t1 where t1.user_name=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
}