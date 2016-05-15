package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.impl.dv.util.Base64;

import net.sf.json.JSONArray;

import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.ListInfoBiz;

/**
 * 功能：获得手机界面的相关列表信息
 * 
 * @author WenC
 * 
 */
public class ListInfoServlet extends HttpServlet {
	// 路径/ListInfoService.do
	private static final long serialVersionUID = 1L;
	private static final String TAG = ListInfoServlet.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private ListInfoBiz lib;

	@Override
	public void init() throws ServletException {
		super.init();
		lib = new ListInfoBiz();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// type = 1: 获得毕设列表， type = 2：获得导师列表， type = 3: 获得学生列表
		String type = new String(Base64.decode(request.getParameter("type")),
				"UTF-8");
		String taccount = request.getParameter("taccount");
		int first = 0;// 如果按导师账号查询，则值为-1
		if (request.getParameter("first") != null) {
			first = Integer.parseInt(new String(Base64.decode(request
					.getParameter("first")), "UTF-8"));
		}
		if ("1".equals(type)) {
			getDesList(taccount, out, first);
		} else if ("2".equals(type)) {
			getTeaList(out, first);
		} else if ("3".equals(type)) {
			getStdList(taccount, out, first);
		}
		out.flush();
		out.close();
	}

	private void getStdList(String taccount, PrintWriter out, int first)
			throws UnsupportedEncodingException {
		// taccount = null 则表示获得全部内容
		JSONArray result = null;
		if (taccount != null) {
			String account = new String(Base64.decode(taccount), "utf-8");
			result = lib.getStdByTeaId(account);
		} else {
			result = lib.getAllStd(first);
		}
		log.debug(TAG, "getStdList", "result: " + result.toString());
		out.println(result.toString());
	}

	private void getTeaList(PrintWriter out, int first) {
		JSONArray result = lib.getTeaList(first);
		log.debug(TAG, "getTeaList", "result: " + result.toString());
		out.println(result.toString());
	}

	private void getDesList(String taccount, PrintWriter out, int first)
			throws UnsupportedEncodingException {
		// taccount = null 则表示获得全部内容
		JSONArray result = null;
		if (taccount != null) {
			String account = new String(Base64.decode(taccount), "utf-8");
			result = lib.getDesByTeaId(account);
		} else {
			result = lib.getAllDes(first);
		}
		log.debug(TAG, "getDesList", "result: " + result.toString());
		out.println(result.toString());
	}
}
