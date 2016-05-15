package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class ChangePass extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String ntype = request.getParameter("ntype");
		
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		
		if (ntype != null && !ntype.isEmpty()) {
			String oldPass = request.getParameter("oldp");
			String newPass = request.getParameter("newp");
			if ((oldPass != null && !oldPass.isEmpty())
					&& (newPass != null && !newPass.isEmpty())) {
				String account = session.getAttribute("account").toString();
				boolean flag = false;
				if ("student".equals(ntype)) {
					StdDaoImpl sdi = new StdDaoImpl();
					flag = sdi.changePass(account, oldPass, newPass);
				} else if ("teacher".equals(ntype)) {
					TeaDaoImpl tdi = new TeaDaoImpl();
					flag = tdi.changePass(account, oldPass, newPass);
				}
				if (flag) {
					out.print(Cons.UPDATE_SUCCESS);
				} else {
					out.print(Cons.UPDATE_FAILED);
				}
			} else {
				out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
			}
		} else {
			out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
		}
		out.close();

	}
}
