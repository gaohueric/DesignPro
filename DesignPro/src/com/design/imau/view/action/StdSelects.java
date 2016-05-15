package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.utils.other.Cons;

public class StdSelects extends HttpServlet {

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
		String what = request.getParameter("what");
		String desid = request.getParameter("desid");
		String tno = request.getParameter("tno");
		if (ntype == null || what == null || desid == null || ntype.isEmpty()
				|| what.isEmpty() || desid.isEmpty() || !"1".equals(ntype)) {
			out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
		} else {
			String account = session.getAttribute("account").toString();
			if ("100".equals(what)) {
				System.out.println("************* account: " + account
						+ " *** what : 100");
				StdDaoImpl sdi = new StdDaoImpl();
				if (tno.equals(sdi.hasTea(account))) {
					System.out
							.println("********** sdi.isChoiceTitle(account): "
									+ sdi.isChoiceTitle(account));
					if (!sdi.isChoiceTitle(account)) {
						boolean flag = sdi.selectDes(account, desid, tno);
						if (flag) {
							System.out.println("**************更新成功");
							out.print(Cons.UPDATE_SUCCESS);
						}
					} else {
						out.print(Cons.UPDATE_FAILED);
					}
				} else if (sdi.hasTea(account) != null
						&& !sdi.hasTea(account).isEmpty()) {
					out.print("1857");
				} else {
					out.print("1759");
				}
			} else {
				out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
			}
		}
		out.close();
	}

}
