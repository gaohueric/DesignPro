package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.impl.dv.util.Base64;

import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.LoginBiz;
import com.design.monitor.imau.request.DataConstant;

public class LoginConfirmServlet extends HttpServlet {

	private static final String TAG = AppLoginServlet.class.getSimpleName();
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

		String username = request.getParameter("username");
		String password = request.getParameter("passwd");
		String who = request.getParameter("who");

		if (username != null && password != null && who != null) {

			String u = new String(Base64.decode(username), "UTF-8");
			String p = new String(Base64.decode(password), "UTF-8");
			String w = new String(Base64.decode(who), "UTF-8");

			log.debug(TAG, "doPost", "username: " + u + " ,passwd: " + p
					+ " ,who:" + w);

			LoginBiz lb = new LoginBiz();
			boolean flag = lb.confirmLogin(u, p, w);
			if (flag) {
				if ("1".equals(w)) {
					log.debug(TAG, "doPost", "学生登陆成功");
					out.print(DataConstant.STD_LOGIN_SUCCESS);
				} else if ("2".equals(w)) {
					log.debug(TAG, "doPost", "导师登陆成功");
					out.print(DataConstant.TEA_LOGIN_SUCCESS);
				}
			} else {
				log.debug(TAG, "doPost", "登陆失败");
				out.print(DataConstant.LOGIN_FAILED);
			}
		} else {
			log.debug(TAG, "doPost", "登陆失败");
			out.print(DataConstant.LOGIN_FAILED);
		}
		out.flush();
		out.close();
	}
}
