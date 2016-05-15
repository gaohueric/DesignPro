package com.design.monitor.imau.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.impl.dv.util.Base64;

import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.biz.LoginBiz;
import com.design.monitor.imau.request.DataConstant;

/**
 * 功能：实现移动端的登陆注册,只要是用户通过登陆界面登陆，则都会将信息修改, 路径/AppLoginServlet.do
 * 
 * @author WenC
 * 
 */
public class AppLoginServlet extends HttpServlet {
	private static final String TAG = AppLoginServlet.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		// 获得提交的用户名，密码，登陆人，channelId，userId
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String who = request.getParameter("who");

		// 先要验证账号密码是否匹配
		if (username != null && passwd != null && who != null) {
			LoginBiz lb = new LoginBiz();
			String u = new String(Base64.decode(username), "UTF-8");
			String p = new String(Base64.decode(passwd), "UTF-8");
			String w = new String(Base64.decode(who), "UTF-8");

			log.debug(TAG, "doPost", "username: " + u + ",passwd: " + p
					+ ",who: " + w);

			boolean flag1 = lb.confirmLogin(u, p, w);
			if (flag1) {
				log.debug(TAG, "doPost", "验证成功，开始注册信息");
				String channelId = request.getParameter("channelId");
				String userId = request.getParameter("userId");
				if (channelId != null && userId != null) {

					String chid = new String(Base64.decode(channelId), "UTF-8");
					String uid = new String(Base64.decode(userId), "UTF-8");

					log.debug(TAG, "doPost", "channelId: " + chid
							+ ", userId: " + uid);
					boolean flag2 = lb.registInfo(u, chid, uid);
					if (flag2) {
						log.debug(TAG, "doPost", "验证成功");
						if ("1".equals(w)) {
							out.print(DataConstant.STD_LOGIN_SUCCESS);
						} else if ("2".equals(w)) {
							out.print(DataConstant.TEA_LOGIN_SUCCESS);
						}

					} else {
						log.debug(TAG, "doPost", "验证成功，注册失败");
						out.print(DataConstant.LOGIN_FAILED);
					}

				} else {

					log.debug(TAG, "doPost", "登陆失败,channelId|userId 存在空值");
					out.print(DataConstant.LOGIN_FAILED);
				}

			} else {
				log.debug(TAG, "doPost", "登陆失败");
				out.print(DataConstant.LOGIN_FAILED);
			}
		} else {
			log.debug(TAG, "doPost", "登陆失败");
			out.print(DataConstant.LOGIN_FAILED);
		}
		out.flush();
		out.close();
	}
}
