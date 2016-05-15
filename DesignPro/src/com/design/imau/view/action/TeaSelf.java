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

import net.sf.json.JSONObject;

import com.design.imau.bean.Teacher;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaSelf extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TeaDaoImpl tdi;
	private Teacher tea;

	public TeaSelf() {
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
		
		String account = (String) session.getAttribute("account");
		tea = tdi.getTea(account);

		if (tea != null) {
			tea = tdi.getTea(account);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TEA_NAME", tea.getName());
			map.put("TEA_SEX", tea.getSex());
			map.put("TEA_MAJOR", tea.getMajor());
			map.put("TEA_TITLE", tea.getTitle());
			map.put("TEA_PHONE", tea.getPhone());
			map.put("TEA_QQ", tea.getQQ());
			map.put("TEA_WX", tea.getWX());
			map.put("TEA_SELF", tea.getSelf());
			map.put("TEA_HEAD", tea.getImgURL());
			JSONObject o = JSONObject.fromObject(map.toString());
			out.print(o.toString());
		} else {
			out.print(Cons.QUERY_FAILED);
		}
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		tea = new Teacher();
		tdi = new TeaDaoImpl();
	}

}
