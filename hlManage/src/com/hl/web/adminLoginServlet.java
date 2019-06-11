package com.hl.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hl.dao.AdminDao;
import com.hl.model.Admin;
import com.hl.util.DbUtil;


public class adminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	DbUtil dbUtil = new DbUtil();
	AdminDao adminDao = new AdminDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		Connection connection = null;
		
		try {
			connection=dbUtil.getCon();
			Admin currentAdmin = null;
			Admin admin = new Admin(userName, password);
			currentAdmin=adminDao.Login(connection, admin);
			if(currentAdmin== null) {
				request.setAttribute("admin", admin);
				request.setAttribute("error", "用户名或密码错误！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				if("remember-me".equals(remember)) {
					rememberMe(userName, password,response);
				} else {
					deleteCookie(userName, request, response);
				}
				session.setAttribute("currentUser", currentAdmin);
				request.setAttribute("mainPage", "admin/blank.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void rememberMe(String userName, String password, HttpServletResponse response) {
		Cookie user = new Cookie("dormuser", userName+"-"+password+"-"+"yes");
		user.setMaxAge(1*60*60*24*7);
		response.addCookie(user);
	}
	
	private void deleteCookie(String userName, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies=request.getCookies();
		for(int i=0;cookies!=null && i<cookies.length;i++){
			if(cookies[i].getName().equals("dormuser")){
				if(userName.equals(userName=cookies[i].getValue().split("-")[0])) {
					Cookie cookie = new Cookie(cookies[i].getName(), null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

}
