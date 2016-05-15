package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TeaDaoImpl tdi;

	public TeaList() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		int start = Integer.parseInt(request.getParameter("start").toString());
		JSONArray list = tdi.teaList(start);
		if (list != null) {
			System.out.println("*********** TeaList has result *********");
			System.out.println("***********" + list.toString());
			out.print(list.toString());
		} else {
			System.out.println("*********** TeaList no more *********");
			out.print(Cons.NO_MORE);
		}
		out.close();
	}

	public void init() throws ServletException {
		tdi = new TeaDaoImpl();
	}

}
