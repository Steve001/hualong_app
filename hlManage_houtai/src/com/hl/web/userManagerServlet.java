package com.hl.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hl.dao.UserDao;
import com.hl.model.PageBean;
import com.hl.model.User;
import com.hl.util.DbUtil;
import com.hl.util.PropertiesUtil;
import com.hl.util.StringUtil;


public class userManagerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	UserDao userDao=new UserDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String s_userManagerText = request.getParameter("s_userManagerText");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		User user = new User();
		if("preSave".equals(action)) {
			userPreSave(request, response);
			return;
		} else if("save".equals(action)){
			userSave(request, response);
			return;
		} else if("delete".equals(action)){
			userDelete(request, response);
			return;
		} else 
			if("list".equals(action)) {
			if(StringUtil.isNotEmpty(s_userManagerText)) {
				if("userName".equals(searchType)) {
					user.setUserName(s_userManagerText);
				}
			}
			session.removeAttribute("s_userManagerText");
			session.removeAttribute("searchType");
			request.setAttribute("s_userManagerText", s_userManagerText);
			request.setAttribute("searchType", searchType);
		} else if("search".equals(action)){
			if (StringUtil.isNotEmpty(s_userManagerText)) {
				 if ("userName".equals(searchType)) {
					user.setUserName(s_userManagerText);
				}
				 else if ("userPhone".equals(searchType)) {
						user.setUserPhone(s_userManagerText);
					}
				session.setAttribute("searchType", searchType);
				session.setAttribute("s_userManagerText", s_userManagerText);
			} else {
				session.removeAttribute("s_userManagerText");
				session.removeAttribute("searchType");
			}
		} else {
			if(StringUtil.isNotEmpty(s_userManagerText)) {
				if("userName".equals(searchType)) {
					user.setUserName(s_userManagerText);
				}
				else if("userPhone".equals(searchType)) {
					user.setUserPhone(s_userManagerText);
				}
				session.setAttribute("searchType", searchType);
				session.setAttribute("s_userManagerText", s_userManagerText);
			}
			if(StringUtil.isEmpty(s_userManagerText)) {
				Object o1 = session.getAttribute("s_userManagerText");
				Object o2 = session.getAttribute("searchType");
				if(o1!=null) {
					if("userName".equals((String)o2)) {
						user.setUserName((String)o1);
					}
				}
			}
		}
		if(StringUtil.isEmpty(page)) {
			page="1";
		}
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		request.setAttribute("pageSize", pageBean.getPageSize());
		request.setAttribute("page", pageBean.getPage());
		try {
			con=dbUtil.getCon();
			List<User> usersList = userDao.userManageList(con, pageBean, user);
			int total=userDao.userCount(con, user);
			System.out.println(total);
			String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("usersList", usersList);
			request.setAttribute("mainPage", "admin/userManager.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
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

	private void userDelete(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			userDao.userDel(con, userId);
			request.getRequestDispatcher("userList?action=list").forward(request, response);
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

	 private void userSave(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String userPhone = request.getParameter("userPhone");
		String userJigou = request.getParameter("userJigou");
		String userTjname = request.getParameter("userTjname");
		String userId = request.getParameter("userId");
		User user=new User(userName, userPhone, userPassword, userJigou, userTjname);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			if(StringUtil.isNotEmpty(request.getParameter("userId"))){
				user.setUserId(Integer.parseInt(request.getParameter("userId")));
				saveNum = userDao.userUpdate(con, user);
			} else if(userDao.haveUserByName(con, user.getUserName())){
				request.setAttribute("user", user);
				request.setAttribute("error", "姓名已存在");
				request.setAttribute("mainPage", "admin/userSave.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			} else {
				saveNum = userDao.addUser(con, user);
			}
			if(saveNum > 0) {
				request.getRequestDispatcher("userList?action=list").forward(request, response);
			} else {
				request.setAttribute("user", user);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "admin/userSave.jsp");
				request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
			}
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

	
	 private void userPreSave(HttpServletRequest request,
			 HttpServletResponse response)throws ServletException, IOException {
		String userId = request.getParameter("userId");
		if(StringUtil.isNotEmpty(userId)) {
			Connection con = null;
			try {
				con = dbUtil.getCon();
				/*
				 * DormManager dormManager = dormManagerDao.dormManagerShow(con, dormManagerId);
				 */
				User user=userDao.userShow(con, userId);
				request.setAttribute("user", user);
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
		request.setAttribute("mainPage", "admin/userSave.jsp");
		request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
	}

	private String genPagation(int totalNum, int currentPage, int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='userList?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else {
			pageCode.append("<li><a href='userList?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='userList?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		} else {
			pageCode.append("<li><a href='userList?page="+(currentPage+1)+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='userList?page="+totalPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
	
}
