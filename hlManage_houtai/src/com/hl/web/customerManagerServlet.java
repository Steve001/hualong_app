package com.hl.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hl.dao.CusDao;
import com.hl.model.Customer;
import com.hl.model.PageBean;
import com.hl.model.User;
import com.hl.util.DbUtil;
import com.hl.util.PropertiesUtil;
import com.hl.util.StringUtil;


public class customerManagerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	CusDao cusDao=new CusDao();
	
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
		String s_customerManagerText = request.getParameter("s_customerManagerText");
		String searchType = request.getParameter("searchType");
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		Customer customer = new Customer();
		if("preSave".equals(action)) {
			cusPreSave(request, response);
			return;
		} else if("save".equals(action)){
			cusSave(request, response);
			return;
		} else if("delete".equals(action)){
			customerDelete(request, response);
			return;
		} else 
			if("list".equals(action)) {
			if(StringUtil.isNotEmpty(s_customerManagerText)) {
				if("cusName".equals(searchType)) {
					customer.setCusName(s_customerManagerText);
				}
			}
			session.removeAttribute("s_customerManagerText");
			session.removeAttribute("searchType");
			request.setAttribute("s_customerManagerText", s_customerManagerText);
			request.setAttribute("searchType", searchType);
		} else if("search".equals(action)){
			if (StringUtil.isNotEmpty(s_customerManagerText)) {
				 if ("cusName".equals(searchType)) {
					 customer.setCusName(s_customerManagerText);
				}
				 else if ("cusPhone".equals(searchType)) {						//根据客户手机号进行查找
//					 customerPhoneFind(request,response);
					 customer.setCusPhone(s_customerManagerText);
				}
				 else if ("cusGuwen".equals(searchType)) {						//根据客户指定置业顾问进行查找
//					 customerPhoneFind(request,response);
					 customer.setCusGuwen(s_customerManagerText);
				}
				session.setAttribute("searchType", searchType);
				session.setAttribute("s_customerManagerText", s_customerManagerText);
			} else {
				session.removeAttribute("s_customerManagerText");
				session.removeAttribute("searchType");
			}
		} else {
			if(StringUtil.isNotEmpty(s_customerManagerText)) {
				if("cusName".equals(searchType)) {
					customer.setCusName(s_customerManagerText);
				}
				else if("cusPhone".equals(searchType)) {
//					customerPhoneFind(request,response);
					customer.setCusPhone(s_customerManagerText);
				}
				else if("cusGuwen".equals(searchType)) {
//					customerPhoneFind(request,response);
					customer.setCusGuwen(s_customerManagerText);
				}
				session.setAttribute("searchType", searchType);
				session.setAttribute("s_customerManagerText", s_customerManagerText);
			}
			if(StringUtil.isEmpty(s_customerManagerText)) {
				Object o1 = session.getAttribute("s_customerManagerText");
				Object o2 = session.getAttribute("searchType");
				if(o1!=null) {
					if("cusName".equals((String)o2)) {
						customer.setCusName((String)o1);
					}
					else if("cusPhone".equals((String)o2)) {
						customer.setCusPhone((String)o1);
					}
					else if("cusGuwen".equals((String)o2)) {
						customer.setCusGuwen((String)o1);
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
			List<Customer> cusList = cusDao.getCustomers(con, pageBean,customer);
			int total=cusDao.customerCount(con, customer);
			System.out.println(total);
			String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("cusList", cusList);
			request.setAttribute("mainPage", "admin/cusManager.jsp");
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

	
//	private void customerPhoneFind(HttpServletRequest request,
//			HttpServletResponse response) {
//		String cusPhone = request.getParameter("cusPhone");
//		Connection con = null;
//		try {
//			con = dbUtil.getCon();
//			CusDao.getCustomerByPhone(con, cusPhone);
//			//request.getRequestDispatcher("cusList?action=list").forward(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	private void customerDelete(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("cusId");
		Connection con = null;
		try {
			con = dbUtil.getCon();
			CusDao.customerDel(con, userId);
			request.getRequestDispatcher("cusList?action=list").forward(request, response);
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

	 private void cusSave(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
			
		String cusId = request.getParameter("cusId");
		String cusStat = request.getParameter("cusStat");
		Customer customer1=new Customer();
		customer1.setCusId(Integer.parseInt(cusId));
		customer1.setCusStat(cusStat);
		if(StringUtil.isNotEmpty(request.getParameter("cusId"))) {

		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNum = 0;
			System.err.println(request.getParameter("cusId"));
			if(StringUtil.isNotEmpty(request.getParameter("cusId"))){
				saveNum = cusDao.cusUpdate(con, customer1);
			} 
			System.out.println(customer1);
			System.out.println(saveNum);
			if(saveNum > 0) {
				request.getRequestDispatcher("cusList?action=list").forward(request, response);
			} else {
				request.setAttribute("customer1", customer1);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "admin/cusSave.jsp");
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
		}}
	}

	
	 private void cusPreSave(HttpServletRequest request,
			 HttpServletResponse response)throws ServletException, IOException {
		String cusId = request.getParameter("cusId");
		if(StringUtil.isNotEmpty(cusId)) {
			Connection con = null;
			try {
				con = dbUtil.getCon();
				/*
				 * DormManager dormManager = dormManagerDao.dormManagerShow(con, dormManagerId);
				 */
				Customer customer=CusDao.customerShow(con, cusId);
				request.setAttribute("customer", customer);
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
		request.setAttribute("mainPage", "admin/cusSave.jsp");
		request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
	}

	private String genPagation(int totalNum, int currentPage, int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='cusList?page=1'>首页</a></li>");
		if(currentPage==1) {
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else {
			pageCode.append("<li><a href='cusList?page="+(currentPage-1)+"'>上一页</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++) {
			if(i<1||i>totalPage) {
				continue;
			}
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			} else {
				pageCode.append("<li><a href='cusList?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		} else {
			pageCode.append("<li><a href='cusList?page="+(currentPage+1)+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='cusList?page="+totalPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
	
}
