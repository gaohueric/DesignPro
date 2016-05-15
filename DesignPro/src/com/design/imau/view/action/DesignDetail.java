package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.bean.Design;
import com.design.imau.dao.DesDaoImpl;
import com.design.imau.utils.other.Cons;

public class DesignDetail extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DesignDetail() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int desID = Integer.parseInt(request.getParameter("desID"));
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		// 设置响应编码
		DesDaoImpl desDao = new DesDaoImpl();
		Design design = desDao.getDes(desID); // 获得毕设详情

		if (design != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", design.getID());
			map.put("NAME", design.getName());
			map.put("TEA_ID", design.getTea_id());
			map.put("STD_ID", design.getStd_id());
			map.put("ISSELECT", design.getIsselect());
			map.put("DESCRIPTION", design.getDescription());
			map.put("DATE", design.getDate());
			JSONArray array = JSONArray.fromObject(map);
			out.print(array); //回传结果
		}else{
			out.print(Cons.QUERY_FAILED);
		}
		out.close();
		
	}

}
