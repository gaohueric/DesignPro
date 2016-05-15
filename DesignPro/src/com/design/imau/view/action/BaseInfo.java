package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.design.imau.bean.Student;
import com.design.imau.bean.Teacher;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class BaseInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		// 如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		String ntype = request.getParameter("ntype");
		if (ntype != null) {
			String account = session.getAttribute("account").toString();
			if ("1".equals(ntype)) {
				StdDaoImpl sdi = new StdDaoImpl();
				Student std = sdi.getStd(account);
				if (std != null) {
					JSONObject obj = JSONObject.fromObject(std);
					out.print(obj.toString());
				} else {
					out.print(Cons.QUERY_FAILED);
				}
			} else if ("2".equals(ntype)) {
				TeaDaoImpl tdi = new TeaDaoImpl();
				Teacher tea = tdi.getTea(account);
				if (tea != null) {
					JSONObject obj = JSONObject.fromObject(tea);
					out.print(obj.toString());
				} else {
					out.print(Cons.QUERY_FAILED);
				}
			}
		} else {
			out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
		}
		out.close();
	}
}
