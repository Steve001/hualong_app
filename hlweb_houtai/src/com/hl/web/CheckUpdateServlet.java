package com.hl.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hl.model.AppInfo;
import com.hl.model.HttpModel;
import com.hl.util.PropertiesUtil;
import com.hl.util.StringUtil;

/**
 * @author lzj
 * @干啥的 检测app更新
 * @date 2019年6月15日
 */
public class CheckUpdateServlet extends HttpServlet {
	private static String tag = "login";

	private static final long serialVersionUID = 1L;

	public CheckUpdateServlet() {
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
		// 需要传递两个参数 version platform
		String version = request.getParameter("version");
		String platform = request.getParameter("platform");
		AppInfo appinfo = new AppInfo();
		if ("Android".equals(platform)) {
			// 1.获取最新安卓版本信息
			String new_version = PropertiesUtil.getValue("app.version", "/common.properties");
			if (StringUtil.isEmpty(version) || version.compareTo(new_version) < 0) {
				// 需要更新
				appinfo.setVersion(new_version);
				appinfo.setApk(PropertiesUtil.getValue("app.apk", "/common.properties"));
			} else {
				appinfo.setVersion("0");
			}
			httpModel.addData(appinfo);
		}
		httpModel.setStatus(HttpModel.SUCCESS);
		response.getWriter().println(JSONObject.toJSON(httpModel));
	}

}
