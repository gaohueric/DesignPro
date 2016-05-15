package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.utils.other.Cons;

public class SessionTNO extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tno = request.getParameter("tno");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		if (!"".equals(tno) && tno != null) {
			session.setAttribute("showtno", tno);
			System.out.println("****************session: "+session.getAttribute("showtno"));
			out.print(Cons.SUCCESS);
			out.close();
			return;
		}
		out.print(Cons.FAILED);
		out.close();
	}

	public void init() throws ServletException {
	}

}
