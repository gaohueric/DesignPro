package com.design.monitor.imau.biz;

import org.junit.Test;

import net.sf.json.JSONObject;

import com.design.imau.bean.Design;
import com.design.imau.bean.Student;
import com.design.imau.bean.Teacher;
import com.design.imau.dao.DesDaoImpl;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.MDLog;

public class SingleInfoBiz {

	private static final String TAG = SingleInfoBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();

	private DesDaoImpl ddi;
	private TeaDaoImpl tdi;
	private StdDaoImpl sdi;

	public SingleInfoBiz() {
		ddi = new DesDaoImpl();
		tdi = new TeaDaoImpl();
		sdi = new StdDaoImpl();
	}

	/**
	 * 获得毕业设计信息
	 * 
	 * @param desId
	 * @return
	 */
	public JSONObject getDes(int desId) {
		Design d = ddi.getDes(desId);
		JSONObject result = JSONObject.fromObject(d);
		log.debug(TAG, "getDes", "result: " + result.toString());
		return result;
	}

	/**
	 * 获得导师信息
	 * 
	 * @param tAccount
	 * @return
	 */
	public JSONObject getTea(String tAccount) {
		Teacher t = tdi.getTea(tAccount);
		JSONObject result = JSONObject.fromObject(t);
		log.debug(TAG, "getTea", "result: " + result.toString());
		return result;
	}

	/**
	 * 获得学生信息
	 * 
	 * @param sAccount
	 * @return
	 */
	public JSONObject getStd(String sAccount) {
		Student s = sdi.getStd(sAccount);
		JSONObject result = JSONObject.fromObject(s);
		log.debug(TAG, "getStd", "result: " + result.toString());
		return result;
	}

	public JSONObject stdGetTeaInfo(String sAccount) {

		Student std = sdi.getStd(sAccount);
		String tAccount = std.getTeaNum();
		log.debug(TAG, "stdGetTeaInfo", "tAccount: " + tAccount);
		// 如果账号存在
		if (tAccount != null && !tAccount.isEmpty()) {
			Teacher t = tdi.getTea(tAccount);
			JSONObject result = JSONObject.fromObject(t);
			log.debug(TAG, "stdGetTeaInfo", result.toString());
			return result;
		}
		return null;
	}

	public JSONObject stdGetDesInfo(String sAccount) {
		Design des = ddi.getDesByStdAccount(sAccount);
		if (des != null) {
			JSONObject result = JSONObject.fromObject(des);
			log.debug(TAG, "stdGetDesInfo", result.toString());
			return result;
		}
		return null;
	}

	@Test
	public void test() {
		stdGetDesInfo("111014064");
	}

}
