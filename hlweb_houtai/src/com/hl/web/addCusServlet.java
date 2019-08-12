package com.hl.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hl.dao.CusDao;
import com.hl.model.Customer;
import com.hl.model.HttpModel;
import com.hl.util.DbUtil;

public class addCusServlet extends HttpServlet {
	private static String tag = "addCus";
	CusDao cusDao = new CusDao();
	DbUtil dbUtil = new DbUtil();

	private static final long serialVersionUID = 1L;

	public addCusServlet() {
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
		Customer customer = new Customer();
		String cusName = request.getParameter("cusName");
		String cusSex = request.getParameter("cusSex");
		String cusPhone = request.getParameter("cusPhone");
		String cusArea = request.getParameter("cusArea");
		String cusDate = request.getParameter("cusDate");
		String cusUserid = request.getParameter("cusUserid");
//		String cusGuwen = request.getParameter("cusGuwen");					取消指定置业顾问功能
		if (cusName == null || cusSex == null || cusPhone == null || cusArea == null || cusDate == null) {
			httpModel.setStatus(HttpModel.ERROR);
			httpModel.setMessage("数据不完整");
			response.getWriter().println(JSONObject.toJSON(httpModel));
		} else {
			customer.setCusName(cusName);
			customer.setCusSex(cusSex);
			customer.setCusPhone(cusPhone);
//			customer.setCusGuwen(cusGuwen);
			customer.setCusArea(cusArea);
			customer.setCusDate(cusDate);
			customer.setCusUserId(Integer.parseInt(cusUserid));
			try {
				con = dbUtil.getCon();
				// 1.判断该用户是否被推介过
				int existCus = cusDao.isExistCus(con, customer);
				if (existCus == 1) {
					// 用户被推过
					httpModel.setStatus(HttpModel.ERROR);
					httpModel.setMessage("该用户已被推介，推介失败");
				} else {
					int addCus = cusDao.addCus(con, customer);
					if (addCus > 0) {
						httpModel.setStatus(HttpModel.SUCCESS);
						httpModel.setMessage("推介成功");
					} else {
						httpModel.setStatus(HttpModel.ERROR);
						httpModel.setMessage("推介失败");
					}
				}
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