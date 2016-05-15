package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaStdWithDes extends HttpServlet {

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
		if("2".equals(ntype) && ntype != null){
			String account = session.getAttribute("account").toString();
			List<Map<String, Object>> list = null;
			TeaDaoImpl tdi = new TeaDaoImpl();
			list = tdi.getStdWithDname(account);
			if(list != null){
				JSONArray array = JSONArray.fromObject(list);
				out.print(array.toString());
			}else{
				out.print(Cons.QUERY_FAILED);
			}
		}
		out.close();
	}

}
