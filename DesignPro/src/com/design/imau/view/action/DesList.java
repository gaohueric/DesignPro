package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.dao.DesDaoImpl;
import com.design.imau.utils.other.Cons;

public class DesList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DesList() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置回传值编码, 这里有顺序问题, 如果顺序颠倒, 则无法编码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		
		// 获得开始num
		int start = Integer.parseInt(request.getParameter("start"));
		System.out.println("************start: " + start);
		DesDaoImpl desDao = new DesDaoImpl();
		JSONArray list = desDao.getDesList(start);
		// 如果不为空，则返回数据
		if (list != null) {
			System.out.println(list.toString());
			out.print(list.toString());
		} else {
			out.print(Cons.QUERY_FAILED);
		}
		out.close();

	}

	public void init() throws ServletException {
	}

}
