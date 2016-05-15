package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.design.imau.utils.other.Cons;
import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.TeaSetBiz;
import com.design.monitor.imau.dao.TControl;
import com.design.monitor.imau.request.DataConstant;

public class TeaSetServlet extends HttpServlet {
	private static final MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		// 如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}

		String account = (String) session.getAttribute("account");

		// type：1表示获得设置信息，type：2表示设置信息
		String type = request.getParameter("type");
		TeaSetBiz tsb = new TeaSetBiz();
		if ("1".equals(type)) {

			TControl tc = tsb.getTCByTid(Integer.parseInt(account));
			if (tc != null) {
				String cycle = "" + tc.getCycle();
				String remind = "" + tc.getReminder();
				Date d = tc.getDeadline();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(d);

				Map<String, String> map = new HashMap<String, String>();
				map.put("cycle", cycle);
				map.put("remind", remind);
				map.put("date", date);

				JSONObject result = JSONObject.fromObject(map);
				log.d(this.getClass(), "type = 1",
						"result: " + result.toString());
				out.print(result.toString());

			}

		} else if ("2".equals(type)) {
			String cycle = request.getParameter("cycle");
			String remind = request.getParameter("remind");
			String deadline = request.getParameter("date");

			// 判断操作是否成功
			boolean flag = tsb.setTCInfo(account, cycle, remind, deadline);
			// 如果成功，则发送推送到手机客户端
			if (flag) {
				log.d(TeaSetServlet.class, "doPost", account + "信息设置成功");
				// 如果导师有学生，则发送推送信息到所指导的学生，
				tsb.pushNotifi(account, deadline);
			} else {
				log.d(this.getClass(), "type = 2", account + "信息设置失败");
				out.print(DataConstant.UPDATE_INFO_FAILED);
			}
		}
		out.flush();
		out.close();
	}
}
