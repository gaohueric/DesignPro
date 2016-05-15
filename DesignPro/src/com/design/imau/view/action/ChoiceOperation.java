package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.utils.other.Cons;

public class ChoiceOperation extends HttpServlet {

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
		
		String account = session.getAttribute("account").toString();
		String ntype = request.getParameter("ntype");

		System.out.println("*************" + account + "选择导师: ntype=" + ntype);

		if (ntype != null) {
			StdDaoImpl sdi = new StdDaoImpl();
			// 1000 : 学生选择导师，将导师教工号存入session
			if ("1000".equals(ntype)) {
				String tno = request.getParameter("tno");
				System.out.println("***********tno: " + tno);
				// 更新stdinfo表内容，说明没有导师
				if (sdi.hasTea(account) == null
						|| sdi.hasTea(account).isEmpty()) {
					boolean flag = sdi.selectTea(tno, account);
					if (flag) {
						session.setAttribute("tno", tno);
						out.print(Cons.UPDATE_SUCCESS);
					} else {
						out.print(Cons.UPDATE_FAILED);
					}
				} else {
					out.print("1759");
				}
			}
			// 1001: 学生查看自己的毕业设计信息
			if ("1001".equals(ntype)) {
				JSONObject obj = sdi.getMyDes(account);
				if (obj != null) {
					System.out.println("****************学生毕设obj:"
							+ obj.toString());
					out.print(obj.toString());
				} else {
					out.print(Cons.FAILED);
				}
			}
			if ("-33".equals(ntype)) {
				session.invalidate();
				out.print("1313");
			}
			out.close();
		} else {
			// 返回错误代码1003
			out.print(Cons.ERROR);
		}
	}
}
