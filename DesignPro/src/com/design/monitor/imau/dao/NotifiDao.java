package com.design.monitor.imau.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.design.imau.utils.other.MDLog;

public class NotifiDao {

	private static final String TAG = NotifiDao.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private Transaction transaction;
	private NotificationDAO nDao;

	public NotifiDao() {
		nDao = new NotificationDAO();
	}

	/**
	 * 获得单条App绑定信息
	 * 
	 * @param username
	 * @return
	 */
	public Notification getSingleAppInfo(String username) {
		List<Notification> nList = nDao.findByAccount(Integer
				.parseInt(username));
		// 如果结果存在，返回结果
		if (nList != null && !nList.isEmpty()) {
			return nList.get(0);
		}
		return null;
	}

	/**
	 * 注册/修改app信息
	 * 
	 * @param notification
	 * @return
	 */
	public int registAppInfo(Notification notification) {
		transaction = nDao.getSession().beginTransaction();
		try {
			nDao.attachDirty(notification);
			transaction.commit();
		} catch (Exception e) {
			log.debug(TAG, "registAppInfo", "新增/修改，异常，操作失败");
			log.error(TAG, "registAppInfo", e);
			return -1;
		} finally {
			nDao.getSession().close();
		}
		log.debug(TAG, "registAppInfo", "新增/修改，操作成功");
		return 0;
	}

	/**
	 * 删除单条应用信息
	 * 
	 * @param notification
	 * @return
	 */
	public int deleteSingleAppInfo(Notification notification) {
		transaction = nDao.getSession().beginTransaction();
		try {
			nDao.delete(notification);
			transaction.commit();
		} catch (Exception e) {
			log.debug(TAG, "deleteSingleAppInfo", "刪除，操作失败");
			return -1;
		} finally {
			nDao.getSession().close();
		}
		log.debug(TAG, "deleteSingleAppInfo", "刪除，操作成功");
		return 0;
	}

}
