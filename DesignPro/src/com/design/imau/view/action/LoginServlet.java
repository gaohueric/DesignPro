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

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应编码格式
		response.setCharacterEncoding("UTF-8");
		// 获得参数
		String ntype = request.getParameter("ntype");
		String account = request.getParameter("acc");
		String passwd = request.getParameter("pass");
		System.out.println("******************who:" + ntype
				+ "******************\naccount:" + account
				+ "******************\npasswd:" + passwd);

		HttpSession session = request.getSession();

		// 进行登陆
		if (!ntype.isEmpty() && ntype != null) {
			PrintWriter pw = response.getWriter();
			AccDaoImpl acc = new AccDaoImpl();
			boolean flag = false;
			flag = acc.login(account, passwd, ntype);
			if (flag) {
				System.out.println("**************" + ntype);
				if ("1".equals(ntype)) {
					pw.print(Cons.STD_LOGIN_SUCCESS);
				} else if ("2".equals(ntype)) {
					pw.print(Cons.TEA_LOGIN_SUCCESS);
				} else if ("3".equals(ntype)) {
					pw.print(Cons.ADM_LOGIN_SUCCESS);
				}

				// 将基本信息存入session
				session.setAttribute("account", account);
				// 有效期1800s
				session.setMaxInactiveInterval(1800);

			} else {
				pw.print(Cons.ERROR); // 登录失败
			}
			pw.close();
		}
	}
}
