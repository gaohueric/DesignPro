package com.design.monitor.imau.biz;

import org.junit.Test;

import net.sf.json.JSONArray;

import com.design.imau.dao.DesDaoImpl;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.MDLog;

public class ListInfoBiz {
	private static final String TAG = ListInfoBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();

	private DesDaoImpl ddi;
	private TeaDaoImpl tdi;
	private StdDaoImpl sdi;

	public ListInfoBiz() {
		ddi = new DesDaoImpl();
		tdi = new TeaDaoImpl();
		sdi = new StdDaoImpl();
	}

	/**
	 * 分页获得全部毕业设计
	 * 
	 * @param first
	 * @return
	 */
	public JSONArray getAllDes(int first) {
		return ddi.getDesList(first);
	}

	/**
	 * 按照导师账号获得毕设信息
	 * 
	 * @param taccount
	 * @return
	 */
	public JSONArray getDesByTeaId(String taccount) {
		return tdi.getTeaDeses(taccount);
	}

	/**
	 * 获得导师对应的学生列表
	 * 
	 * @param taccount
	 * @return
	 */
	public JSONArray getStdByTeaId(String taccount) {
		return tdi.myStdList(taccount);
	}

	/**
	 * 分页获得学生信息
	 * 
	 * @param first
	 * @return
	 */
	public JSONArray getAllStd(int first) {
		return sdi.stdList(first);
	}

	/**
	 * 分页获得导师列表
	 * 
	 * @param first
	 * @return
	 */
	public JSONArray getTeaList(int first) {
		return tdi.teaList(first);
	}

	@Test
	public void test() {
		log.debug(TAG, "test", "result: " + getAllStd(1));
	}

}
