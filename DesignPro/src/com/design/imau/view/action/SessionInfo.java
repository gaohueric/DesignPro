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

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;

public class SessionInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StdDaoImpl sdi;
	private TeaDaoImpl tdi;

	public SessionInfo() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
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
		
		String who = request.getParameter("who");
		String account = (String) session.getAttribute("account");
		String name = (String) session.getAttribute("name");

		Map<String, Object> map = null;

		// 如果session中有name存在，则不用再次请求数据库
		if (name != null && !name.isEmpty()) {
			System.out
					.println("************has 'name' in session*************");

			map = new HashMap<String, Object>();
			if ("1".equals(who)) {
				map.put("name", name);
				map.put("account", account);
				map.put("major", session.getAttribute("major").toString());
				map.put("imgUrl", session.getAttribute("imgUrl").toString());
				map.put("isSelect", session.getAttribute("isSelect").toString());
				map.put("hasTea", session.getAttribute("hasTea").toString());
				map.put("tno", session.getAttribute("tno").toString());
			}
			if ("2".equals(who)) {
				map.put("name", name);
				map.put("account", account);
				map.put("major", session.getAttribute("major").toString());
				map.put("imgUrl", session.getAttribute("imgUrl").toString());
				map.put("teaStdCount", session.getAttribute("teaStdCount")
						.toString());
			}

			System.out.println("***********" + map.toString() + "************");
		} else {
			System.out.println("************no 'name' in session*************");
			// session 中没有基本信息
			Map<String, Object> temp = null;
			map = new HashMap<String, Object>();
			if ("1".equals(who)) {
				sdi = new StdDaoImpl();
				temp = sdi.baseInfo(account);

				System.out.println("**********temp: " + temp.toString());

				// 将基本信息写入session
				setSession(session, temp.get("STD_NAME").toString(),
						temp.get("STD_MAJOR").toString(), temp.get("STD_HEAD")
								.toString());
				session.setAttribute("isSelect", temp.get("ISSELECT")
						.toString());
				session.setAttribute("hasTea", temp.get("HAS_TEA").toString());
				session.setAttribute("tno", temp.get("TEA_NUM").toString());

				map.put("name", temp.get("STD_NAME").toString());
				map.put("major", temp.get("STD_MAJOR").toString());
				map.put("imgUrl", temp.get("STD_HEAD").toString());
				map.put("isSelect", temp.get("ISSELECT").toString());
				map.put("hasTea", temp.get("HAS_TEA").toString());
				map.put("tno", temp.get("TEA_NUM").toString());
			}
			if ("2".equals(who)) {
				tdi = new TeaDaoImpl();
				temp = tdi.baseInfo(account);

				// 将基本信息写入session
				setSession(session, temp.get("TEA_NAME").toString(),
						temp.get("TEA_MAJOR").toString(), temp.get("TEA_HEAD")
								.toString());
				session.setAttribute("teaStdCount", temp.get("TEA_STDCOUNT")
						.toString());
				System.out.println(session.getAttributeNames());

				map.put("name", temp.get("TEA_NAME").toString());
				map.put("major", temp.get("TEA_MAJOR").toString());
				map.put("imgUrl", temp.get("TEA_HEAD").toString());
				map.put("teaStdCount", temp.get("TEA_STDCOUNT").toString());
			}
			// 添加账户到map中;
			map.put("account", account);
		}
		// 获得最大学生数量
		tdi = new TeaDaoImpl();
		Map<String, Object> maxSCount = tdi.maxSCount();
		if (maxSCount != null) {
			map.put("maxSCount", maxSCount.get("scount").toString());
		} else {
			System.out.println("*******************maxSCount == null");
			map.put("maxSCount", "0");
		}
		// 返回json数据
		JSONObject json = JSONObject.fromObject(map);
		System.out.println("***************json:" + json.toString());
		// 输出结果
		out.print(json.toString());
		out.close();
	}

	/**
	 * 设置session的属性
	 * 
	 * @param session
	 *            session
	 * @param name
	 *            姓名
	 * @param major
	 *            专业
	 * @param imgUrl
	 *            图片路径
	 */
	private void setSession(HttpSession session, String name, String major,
			String imgUrl) {
		session.setAttribute("name", name);
		session.setAttribute("major", major);
		session.setAttribute("imgUrl", imgUrl);
	}

	public void init() throws ServletException {

	}

}
