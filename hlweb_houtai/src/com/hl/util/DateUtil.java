package com.hl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 取得系统时间
	 */
	public static String getSystemTime() {
		String strTime = "";
		java.text.DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		strTime = df.format(new Date());
		return strTime;
	}
}
