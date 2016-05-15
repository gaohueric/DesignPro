package com.design.monitor.imau.biz;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import com.design.imau.dao.AccDaoImpl;
import com.design.imau.utils.other.MDLog;
import com.design.monitor.imau.dao.Notification;
import com.design.monitor.imau.dao.NotificationDAO;

public class LoginBiz {

	private static final String TAG = LoginBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();

	private AccDaoImpl adi;
	private NotificationDAO nfDAO;
	private Transaction t;

	public LoginBiz() {
		adi = new AccDaoImpl();
		nfDAO = new NotificationDAO();
	}

	/**
	 * 确认登陆
	 * 
	 * @param account
	 * @param passwd
	 * @param who
	 * @return
	 */
	public boolean confirmLogin(String account, String passwd, String who) {
		log.debug(TAG, "confirmLogin", "登陆验证");
		return adi.login(account, passwd, who);
	}

	public boolean registInfo(String account, String channelId, String userId) {

		Notification n = null;
		// 检查是否存在信息
		List<?> list = nfDAO.findByAccount(Integer.parseInt(account));
		List<Notification> nlist = new ArrayList<Notification>();
		// 转换类型
		for (int i = 0; i < list.size(); i++) {
			nlist.add((Notification) list.get(i));
		}
		if (nlist != null && nlist.size() > 0) {
			log.debug(TAG, "registInfo", "有值存在，重新注册");
			n = nlist.get(0);
		} else {
			n = new Notification();
			n.setAccount(Integer.parseInt(account));
		}
		n.setChannelid(channelId);
		n.setUserid(userId);
		nfDAO.getSession().clear();

		// 注册设备信息到数据库中
		t = nfDAO.getSession().beginTransaction();
		try {
			nfDAO.attachDirty(n);
			t.commit();
			log.debug(TAG, "registInfo", "注册成功");
		} catch (Exception e) {
			log.debug(TAG, "registInfo", "注册失败");
			log.error(TAG, "registInfo", e);
			return false;
		} finally {
			nfDAO.getSession().clear();
			nfDAO.getSession().close();
		}
		return true;
	}
}
