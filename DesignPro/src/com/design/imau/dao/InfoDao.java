package com.design.imau.dao;

import java.util.List;

public interface InfoDao {
	
	/**
	 * 新增用户
	 * @param params
	 * @return
	 */
	public boolean create(List<Object> params);
	
	/**
	 * 删除账号信息
	 * @param account
	 * @return
	 */
	public boolean delete(String account);
	
	/**
	 * 更新用户信息
	 * @param account
	 * @return
	 */
	public boolean updateInfo(String account, Object person);
}
