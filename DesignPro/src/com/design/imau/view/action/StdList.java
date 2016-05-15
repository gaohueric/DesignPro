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
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class StdList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StdDaoImpl sdi;

	public StdList() {
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
		if (start >= 0) {
			JSONArray list = sdi.stdList(start);
			if (list != null && !list.isEmpty()) {
				System.out.println("*********** StdList has result *********");
				out.print(list.toString());
			} else {
				System.out.println("*********** StdList no more *********");
				out.print(Cons.NO_MORE);
			}
		} else {
			TeaDaoImpl tdi = new TeaDaoImpl();
			String account = session.getAttribute("account").toString();
			JSONArray array = tdi.myStdList(account);
			if(array != null){
				out.print(array.toString());
			}else{
				out.print(Cons.QUERY_FAILED);
			}
		}
		out.close();
	}

	public void init() throws ServletException {
		sdi = new StdDaoImpl();
	}

}
