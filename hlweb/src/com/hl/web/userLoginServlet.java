package com.hl.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hl.dao.UserDao;
import com.hl.model.HttpModel;
import com.hl.model.User;
import com.hl.util.DbUtil;

/**
 * ʵ�ֵ�½
 * 
 * */
public class userLoginServlet extends HttpServlet {
	private static String tag = "login";
	UserDao userDao=new UserDao();
	DbUtil dbUtil=new DbUtil();
	
	private static final long serialVersionUID = 1L;
       
    public userLoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpModel httpModel = new HttpModel(tag);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		Connection con =null;
		User user=new User();
		String userPhone=request.getParameter("userPhone");
		String userPassword=request.getParameter("userPassword");
		user.setUserPassword(userPassword);
		user.setUserPhone(userPhone);
		User currUser=null;
		try {
			con=dbUtil.getCon();
			currUser=userDao.userLogin(con, user);
			if(currUser==null) {
				httpModel.setMessage("用户名或密码错误");
				httpModel.setStatus(HttpModel.ERROR);
			}else {
				httpModel.addData(currUser);
				httpModel.setStatus(HttpModel.SUCCESS);
			}
			response.getWriter().println(JSONObject.toJSON(httpModel));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
