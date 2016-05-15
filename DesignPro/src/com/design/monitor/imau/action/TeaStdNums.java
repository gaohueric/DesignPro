package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.utils.other.Cons;
import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.FileUpLoadBiz;

public class TeaStdNums extends HttpServlet {
	private static final String TAG = TeaStdNums.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		// 如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}

		String account = (String) session.getAttribute("account");
		FileUpLoadBiz fub = new FileUpLoadBiz();
		JSONArray array = fub.stdNumName(account);

		log.debug(TAG, "doPost", "array:" + array.toString());
		out.print(array.toString());
		out.flush();
		out.close();
	}

}
