package com.design.imau.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.design.imau.utils.jdbc.JdbcManager;

public class AccDaoImpl implements InfoDao {

	private StringBuilder sql;
	private List<Object> mParams;
	private Map<String, Object> result;
	private boolean isExist = false;

	public AccDaoImpl() {
		sql = new StringBuilder();
		mParams = new ArrayList<Object>();
		result = new HashMap<String, Object>();
	}

	/**
	 * 创建账号
	 * 
	 * @param params
	 * @return
	 */
	public boolean create(List<Object> params) {
		boolean flag = false;
		
		sql.delete(0, sql.length());
		sql.append("insert into account(IDS,passwd,who,astatus) values(?,?,?,?)");
		mParams.clear();
		mParams = params;
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("注册账户异常");
			e.printStackTrace();
		}

		mParams.clear();
		return flag;
	}

	/**
	 * 判断账号是否存在
	 * 
	 * @param account
	 *            账号
	 * @return
	 */
	public boolean isAccExist(String account) {

		boolean flag = false;
		sql.delete(0, sql.length());
		sql.append("select ids from account where ids = ?");
		mParams.clear();
		mParams.add(account);
		try {
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("查询账户出错");
			e.printStackTrace();
		}
		isExist = flag = result.isEmpty() ? false : true;
		// 清空
		result.clear();
		return flag;

	}

	/**
	 * 账户登录
	 * 
	 * @param account
	 *            账号
	 * @param passwd
	 *            密码
	 * @param who
	 *            登陆者
	 * @return
	 */
	public boolean login(String account, String passwd, String who) {
		boolean flag = false;

		sql.delete(0, sql.length());
		sql.append("update account set astatus=1 where ids=? and passwd=? and who=?");
		mParams.clear();
		mParams.add(account);
		mParams.add(passwd);
		mParams.add(who);

		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("登陆异常");
			e.printStackTrace();
		}

		mParams.clear();
		return flag;
	}

	/**
	 * 退出系统
	 * 
	 * @param account
	 *            账号
	 * @param who
	 *            登陆者
	 * @return
	 */
	public boolean exit(String account, String who) {
		boolean flag = false;
		String sql = "update account set astatus=0 where ids=? and who=?";
		mParams.clear();
		mParams.add(account);
		mParams.add(who);

		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql, mParams);
		} catch (SQLException e) {
			System.out.println("退出异常");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 表示账户是否存在
	 * 
	 * @return
	 */
	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	@Override
	public boolean delete(String account) {
		return false;
	}

	@Override
	public boolean updateInfo(String account, Object person) {
		return false;
	}

}
