package com.design.monitor.imau.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.Transaction;
import org.junit.Test;

import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.MDLog;
import com.design.imau.utils.other.PushMessage;
import com.design.monitor.imau.dao.Notification;
import com.design.monitor.imau.dao.NotificationDAO;
import com.design.monitor.imau.dao.TControl;
import com.design.monitor.imau.dao.TControlDAO;

public class TeaSetBiz {
	private static final String TAG = TeaSetBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();

	private TControlDAO tcDAO;
	private Transaction t;

	public TeaSetBiz() {
		tcDAO = new TControlDAO();
	}

	/**
	 * 通过导师id获得控制信息
	 * 
	 * @param tid
	 * @return
	 */
	public TControl getTCByTid(int tid) {
		TControl tControl = null;
		try {
			tControl = (TControl) (tcDAO.findByTeaId(tid).get(0));
		} catch (Exception e) {
			log.error(TAG, "getTCByTid", e);
		} finally {
			tcDAO.getSession().close();
		}
		return tControl;
	}

	public boolean setTCInfo(String tid, String cycle, String remind,
			String date) {
		TControl tc = getTCByTid(Integer.parseInt(tid));
		try {
			if (tc == null) {
				tc = new TControl();
			}
			tc.setTeaId(Integer.parseInt(tid));
			tc.setCycle(Integer.parseInt(cycle));
			tc.setReminder(Integer.parseInt(remind));
			// 获得date格式
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
			Date dd = d.parse(date);
			tc.setDeadline(dd);

			t = tcDAO.getSession().beginTransaction();
			tcDAO.attachDirty(tc);
			t.commit();
		} catch (Exception e) {
			log.error(TAG, "setTCInfo", e);
			return false;
		} finally {
			tcDAO.getSession().close();
		}
		log.debug(TAG, "setTCInfo", tid + " : 信息设置成功");
		return true;
	}

	public void pushNotifi(String tid, String date) {

		TeaDaoImpl tdi = new TeaDaoImpl();
		JSONArray array = tdi.myStdList(tid);
		log.d(getClass(), "pushNotifi", "获得的学生列表：" + array.toString());
		PushMessage msg = new PushMessage();
		msg.setTitle("论文提交提醒");
		msg.setDescription("您的论文请在 " + date + " 之前提交");

		for (int i = 0; i < array.size(); i++) {
			int sid = array.getJSONObject(i).getInt("id");
			NotificationBiz notifi = NotificationBiz.getInstance();
			NotificationDAO nDao = new NotificationDAO();
			try {
				List<?> list = nDao.findByAccount(sid);
				if (list != null && list.size() > 0) {
					Notification no = (Notification) list.get(0);
					boolean flag = notifi.sendNotification(
							Long.parseLong(no.getChannelid()), no.getUserid(),
							msg);
					log.d(getClass(), "pushNotifi", sid + "：推送成功？" + flag);
				}
			} catch (Exception e) {
				log.error(TAG, "pushNotifi", e);
			} finally {
				nDao.getSession().close();
			}
		}
	}

	@Test
	public void test() {
		pushNotifi("11111111", "2015-05-05");
	}
}
