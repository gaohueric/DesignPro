package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaSelectStd extends HttpServlet {

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
		if ("2".equals(ntype)) {
			String account = session.getAttribute("account").toString();
			String stdID = request.getParameter("stdID");
			String desID = request.getParameter("desID");
			if (!stdID.isEmpty() && stdID != null && !desID.isEmpty()
					&& desID != null) {
				TeaDaoImpl tdi = new TeaDaoImpl();
				boolean flag = false;
				flag = tdi.selectStd(account, stdID, desID);
				System.out.println("************account : "+account +"**** stdID : "+stdID +"**** desID :"+ desID);
				if (flag) {
					System.out.println("****************update success");
					out.print(Cons.UPDATE_SUCCESS);
				} else {
					System.out.println("****************update failed");
					out.print(Cons.UPDATE_FAILED);
				}
			}else{
				out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
			}
		} else {
			out.print(Cons.REQUEST_FAILED);
		}
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
