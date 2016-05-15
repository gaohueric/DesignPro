package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.bean.Student;
import com.design.imau.bean.Teacher;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class UpdateInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Student std;
	private Teacher tea;
	private TeaDaoImpl tdi;
	private StdDaoImpl sdi;

	public UpdateInfo() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		
		// 获取参数
		String who = request.getParameter("who");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String major = request.getParameter("major");
		String QQ = request.getParameter("qq");
		String phone = request.getParameter("phone");
		String self = request.getParameter("self");
		if (!who.isEmpty() && who != null) {
			boolean flag = false;
			if ("1".equals(who)) {
				String grade = request.getParameter("grade");
				String sclass = request.getParameter("sclass");
				std.setName(name);
				std.setSex(sex);
				std.setMajor(major);
				std.setQQ(QQ);
				std.setPhone(phone);
				std.setSelf(self);
				std.setGrade(grade);
				std.setSclass(sclass);
				// 更新
				flag = sdi.updateInfo(session.getAttribute("account")
						.toString(), std);
			} else if ("2".equals(who)) {
				String title = request.getParameter("title");
				String wx = request.getParameter("wx");
				tea.setName(name);
				tea.setSex(sex);
				tea.setMajor(major);
				tea.setPhone(phone);
				tea.setQQ(QQ);
				tea.setSelf(self);
				tea.setTitle(title);
				tea.setWX(wx);

				// 更新
				flag = tdi.updateInfo(session.getAttribute("account")
						.toString(), tea);
			}
			// 判断是否更新成功
			if (flag) {
				//更新session
				session.setAttribute("name", name);
				session.setAttribute("major", major);
				
				out.print(Cons.UPDATE_SUCCESS);
			} else {
				out.print(Cons.UPDATE_FAILED);
			}
		} else {
			out.print(Cons.FAILED);
		}
		out.close();
	}

	public void init() throws ServletException {
		std = new Student();
		tea = new Teacher();
		tdi = new TeaDaoImpl();
		sdi = new StdDaoImpl();
	}

}
