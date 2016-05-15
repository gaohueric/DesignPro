package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaStdNameSno extends HttpServlet {

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
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		
		String ntype = request.getParameter("ntype");
		String req = request.getParameter("req");
		if ("2".equals(ntype) && "name_no".equals(req)) {
			System.out.println("********************* 2 && name_no");
			StdDaoImpl sdi = new StdDaoImpl();
			JSONArray array = sdi.snameSno();
			if (array != null) {
				out.print(array.toString());
			} else {
				out.print(Cons.QUERY_FAILED);
			}
		} else {
			out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
		}
		out.close();
	}

}
