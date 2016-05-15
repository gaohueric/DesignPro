package com.design.imau.dao;

import java.util.Map;

public interface PeoDao {

	/**
	 * 获得用户头像的url
	 * 
	 * @param account
	 * @return
	 */
	public String image(String account);

	/**
	 * 获得页面基本信息
	 * 
	 * @param account
	 * @return
	 */
	public Map<String, Object> baseInfo(String account);

	/**
	 * 更新头像
	 * 
	 * @param path
	 *            新的路径
	 * @param account
	 *            账号
	 * @return
	 */
	public boolean updateHead(String path, String imgName, String account);
	
	/**
	 * 修改密码
	 * @param account 账号
	 * @param oldPass 旧密码
	 * @param newPass 新密码
	 * @return
	 */
	public boolean changePass(String account, String oldPass, String newPass);
	
	/**
	 * 退出系统
	 * @param account
	 * @return
	 */
	public boolean exitSystem(String account);
}
