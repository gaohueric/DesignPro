package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.impl.dv.util.Base64;

import net.sf.json.JSONObject;

import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.SingleInfoBiz;
import com.design.monitor.imau.request.DataConstant;

public class SingleInfoServlet extends HttpServlet {

	private static final String TAG = SingleInfoServlet.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;
	private SingleInfoBiz sib;

	public void init() throws ServletException {
		sib = new SingleInfoBiz();
	}

	public void destroy() {
		super.destroy();
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

		// type：1表示获得毕设信息，type=2表示获得导师个人信息，type=3表示获得学生个人信息，type=4表示学生通过自己的学号查看导师个人信息，5表示学生通过学号查询自己毕业设计信息
		String type = new String(Base64.decode(request.getParameter("type")),
				"UTF-8");
		if ("1".equals(type)) {
			int desId = Integer.parseInt(new String(Base64.decode(request
					.getParameter("desId")), "UTF-8"));
			desInfo(out, desId);
		} else if ("2".equals(type)) {
			String teaAccount = new String(Base64.decode(request
					.getParameter("teaAccount")), "UTF-8");
			teaInfo(out, teaAccount);
		} else if ("3".equals(type)) {
			String stdAccount = new String(Base64.decode(request
					.getParameter("stdAccount")), "UTF-8");
			stdInfo(out, stdAccount);
		} else if ("4".equals(type)) {
			String stdAccount = new String(Base64.decode(request
					.getParameter("stdAccount")), "UTF-8");
			stdGetTeaInfo(out, stdAccount);
		} else if ("5".equals(type)) {
			String stdAccount = new String(Base64.decode(request
					.getParameter("stdAccount")), "UTF-8");
			stdGetDesInfo(out, stdAccount);
		}
		out.flush();
		out.close();
	}

	private void desInfo(PrintWriter out, int desId) {
		log.debug(TAG, "desInfo", "desId: " + desId);
		JSONObject result = sib.getDes(desId);
		out.println(result.toString());
	}

	private void teaInfo(PrintWriter out, String teaAccount) {
		log.debug(TAG, "teaInfo", "teaAccount: " + teaAccount);
		JSONObject result = sib.getTea(teaAccount);
		out.println(result.toString());
	}

	private void stdInfo(PrintWriter out, String stdAccount) {
		log.debug(TAG, "stdInfo", "stdAccount: " + stdAccount);
		JSONObject result = sib.getStd(stdAccount);
		if (result != null) {
			out.println(result.toString());
			return;
		}
		out.println(DataConstant.GET_INFO_FAILED);
	}

	private void stdGetTeaInfo(PrintWriter out, String stdAccount) {
		log.debug(TAG, "stdGetTeaInfo", "stdAccount: " + stdAccount);
		JSONObject result = sib.stdGetTeaInfo(stdAccount);
		if (result != null) {
			out.println(result.toString());
			return;
		}
		out.println(DataConstant.GET_INFO_FAILED);
	}

	private void stdGetDesInfo(PrintWriter out, String stdAccount) {
		log.debug(TAG, "stdGetTeaInfo", "stdAccount: " + stdAccount);
		JSONObject result = sib.stdGetDesInfo(stdAccount);
		if (result != null) {
			out.println(result.toString());
			return;
		}
		out.println(DataConstant.GET_INFO_FAILED);
	}
}
