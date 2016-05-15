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

public class StdSTinfo extends HttpServlet {

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
		// 从session中获得要查看的tno
		//HttpSession session = request.getSession();
		String tno = request.getParameter("tno");
		// 接口中有tno为空或没有tno参数
		if ("".equals(tno) || tno == null) {
			System.out.println("****************接口中没有tno参数");
			//tno = session.getAttribute("tno").toString();
			// 如果session中存在tno并且不为空
			if (tno != null && !"".equals(tno)) {
				StdDaoImpl sdi = new StdDaoImpl();
				JSONArray info = sdi.getTeaInfo(tno);

				System.out.println("*****************导师信息为：" + info.toString());
				// 回传值
				out.print(info.toString());
			} else {
				out.print(Cons.ERROR);
			}
		} else {
			System.out.println("************接口中有tno参数");
			StdDaoImpl sdi = new StdDaoImpl();
			JSONArray info = sdi.getTeaInfo(tno);
			System.out.println("*****************导师信息为：" + info.toString());
			// 回传值
			out.print(info.toString());
		}
		out.close();
	}

	public void init() throws ServletException {
	}

}
