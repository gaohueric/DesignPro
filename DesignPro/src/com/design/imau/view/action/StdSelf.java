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

import com.design.imau.bean.Student;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.utils.other.Cons;

public class StdSelf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public StdSelf() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 定义编码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		String account = request.getParameter("account");
		if (!"".equals(account) && account != null) {
			StdDaoImpl stdDao = new StdDaoImpl();
			Student std = stdDao.getStd(account);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("STD_NAME", std.getName());
			map.put("STD_SEX", std.getSex());
			map.put("STD_MAJOR", std.getMajor());
			map.put("STD_GRADE", std.getGrade());
			map.put("STD_CLASS", std.getSclass());
			map.put("STD_PHONE", std.getPhone());
			map.put("STD_QQ", std.getQQ());
			map.put("STD_SELF", std.getSelf());
			map.put("STD_HEAD", std.getImgURL());
			JSONArray array = JSONArray.fromObject(map);
			if (array != null) {
				out.print(array.toString());
			} else {
				out.print(Cons.QUERY_FAILED);
			}
		} else {
			out.print(Cons.ERROR);
		}

		out.close();
	}

	public void init() throws ServletException {
	}

}
