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

public class addUserServlet extends HttpServlet {
	private static String tag = "regist";
	UserDao userDao = new UserDao();
	DbUtil dbUtil = new DbUtil();

	private static final long serialVersionUID = 1L;

	public addUserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpModel httpModel = new HttpModel(tag);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		Connection con = null;
		User user = new User();
		String userJigou = request.getParameter("jigou");
		String userTjName = request.getParameter("tjname");
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");
		String userPassword = request.getParameter("userPassword");
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserPhone(userPhone);
		user.setUserJigou(userJigou);
		user.setUserTjName(userTjName);
		if (userJigou == null || userName == null || userPhone == null || userPassword == null) {
			httpModel.setStatus(HttpModel.ERROR);
			httpModel.setMessage("数据不完整");
			response.getWriter().println(JSONObject.toJSON(httpModel));
		} else {
			try {
				con = dbUtil.getCon();
				// 1.判断该用户是否被推介过
				int existCus = userDao.isExistUser(con, user);
				int existCus_reg = userDao.isExistUser_reg(con, user);
				if (existCus == 1 || existCus_reg ==1) {
					// 用户被推过
					httpModel.setStatus(HttpModel.ERROR);
					httpModel.setMessage("该用户已存在");
				} 
				else {
					System.out.println("------------------");
					userDao.addUserReg(con, user);
					httpModel.setMessage("注册完成，请等候审核结果");
					httpModel.setStatus(HttpModel.SUCCESS);
				}
				
				
				//取消注册端口功能
//				else {
//					int saveNum = userDao.addUser(con, user);
//					if (saveNum > 0) {
//						httpModel.setMessage("注册成功");
//						httpModel.setStatus(HttpModel.SUCCESS);
//					} else {
//						httpModel.setMessage("注册失败，请联系系统管理员");
//						httpModel.setStatus(HttpModel.ERROR);
//					}
//				}
				response.getWriter().println(JSONObject.toJSON(httpModel));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}