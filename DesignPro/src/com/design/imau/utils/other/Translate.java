package com.design.imau.utils.other;


/**
 * 功能：获得中文
 * @author WenC
 *
 */
public class Translate {
	private static String s = "";
	/**
	 * 获得学生专业
	 * @param value
	 * @return 
	 */
	public static String getMajor(String value) {
		if ("ie".equals(value))
			s = "网络工程";
		else if ("se".equals(value))
			s = "软件工程";
		else if ("cst".equals(value))
			s = "计算机科学与技术";
		else if ("imis".equals(value))
			s = "信息管理与信息系统";
		return s;
	}
	
	/**
	 * 获得性别
	 * @param value
	 * @return
	 */
	public static String getSex(String value){
		String s = "";
		if ("male".equals(value))
			s = "男";
		else if ("female".equals(value))
			s = "女";
		return s;
	}
	
	/**
	 * 获得教师职称
	 * @param value
	 * @return
	 */
	public static String getTitle(String value){
		if ("assi".equals(value))
			s = "助教";
		else if ("lect".equals(value))
			s = "讲师";
		else if ("asop".equals(value))
			s = "副教授";
		else if ("prof".equals(value))
			s = "教授";
		return s;
	}

}
