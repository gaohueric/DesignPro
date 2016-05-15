package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.AccDaoImpl;
import com.design.imau.utils.other.Cons;

public class RegCheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		String ntype = request.getParameter("ntype");
		if (ntype != null && !ntype.isEmpty()) {
			if ("0".equals(ntype)) {
				String account = request.getParameter("acc");
				AccDaoImpl adi = new AccDaoImpl();
				boolean flag = adi.isAccExist(account);
				if (flag) {
					out.print(Cons.DES_ID_ISEXIST);
				} else {
					out.print(Cons.DES_ID_NOT_EXIST);
				}
			}
		} else {
			out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
		}
		out.close();
	}

}
