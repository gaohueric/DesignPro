package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.design.imau.utils.other.Cons;
import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.FileUpLoadBiz;

public class GetFileServlet extends HttpServlet {
	private static final String TAG = GetFileServlet.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(TAG, "doPost", "获得请求");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		// 如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		// 如果who：1获得学生文件列表，who：2获得导师文件列表
		String who = request.getParameter("who");
		String account = (String) session.getAttribute("account");
		// type: 1获得毕设信息，type：2获得文件信息
		String type = request.getParameter("type");
		String filePath = "";

		FileUpLoadBiz fub = new FileUpLoadBiz();
		if ("1".equals(who)) {
			if ("1".equals(type)) {
				JSONObject obj = fub.getSelfDes(account);
				log.debug(TAG, "获得自己毕业设计列表", obj.toString());
				out.print(obj.toString());

			} else if ("2".equals(type)) {
				filePath = getServletContext().getRealPath(
						"/USERS/STUDENTS/" + account + "/FILES");
				String realPath = "/USERS/STUDENTS/" + account + "/FILES";
				log.debug(TAG, "获得学生上传的文件路径", filePath + ",realPath:"
						+ realPath);
				JSONArray array = fub.teaGetFiles(filePath, realPath);

				log.debug(TAG, "获得学生上传的文件路径", array.toString());
				out.print(array.toString());
			}
		} else if ("2".equals(who)) {
			if ("1".equals(type)) {
				JSONArray array = fub.teaGetStdDes(account);
				log.debug(TAG, "获得毕业学生毕业设计列表", array.toString());
				out.print(array.toString());
			} else if ("2".equals(type)) {

				filePath = getServletContext().getRealPath(
						"/USERS/TEACHERS/" + account + "/FILES");
				String realPath = "/USERS/TEACHERS/" + account + "/FILES";
				log.debug(TAG, "获得导师上传的文件路径", filePath + ",realPath:"
						+ realPath);
				JSONArray array = fub.teaGetFiles(filePath, realPath);

				log.debug(TAG, "获得导师上传的文件路径", array.toString());
				out.print(array.toString());
			}
		}
		out.flush();
		out.close();
	}
}
