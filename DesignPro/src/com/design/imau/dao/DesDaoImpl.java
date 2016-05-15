package com.design.imau.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;

import com.design.imau.bean.Design;
import com.design.imau.utils.jdbc.JdbcManager;
import com.design.imau.utils.other.Time;

public class DesDaoImpl implements InfoDao {

	private StringBuilder sql;
	private List<Object> mParams;
	private Map<String, Object> result;

	public DesDaoImpl() {
		sql = new StringBuilder();
		mParams = new ArrayList<Object>();
		result = new HashMap<String, Object>();
	}

	/**
	 * 新增毕设
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public boolean create(List<Object> params) {
		boolean flag = false;
		mParams = params;
		sql.delete(0, sql.length()); // 清空
		if (mParams.size() == 7) {
			sql.append("insert into design(DNAME, TEA_ID, STD_ID, CANCHOICE, ISSELECT, DESCRIPTION, DATE) values(?,?,?,?,?,?,?)");
		} else if (mParams.size() == 6) {
			sql.append("insert into design(DNAME, TEA_ID, CANCHOICE, ISSELECT, DESCRIPTION, DATE) values(?,?,?,?,?,?)");
		} else {
			return false;
		}
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
			System.out.println("*************************createlist");
		} catch (SQLException e) {
			System.out.println("**毕设新增异常");
			e.printStackTrace();
		}
		if (mParams.size() == 7) {
			System.out.println("************ create INSERT design success");
			// 获得账号和学生账号
			String taccount = mParams.get(1).toString();
			String sno = mParams.get(2).toString();
			// 更新学生表
			sql.delete(0, sql.length());
			sql.append("UPDATE stdinfo SET ISSELECT = 1, HAS_TEA = 1, TEA_NUM = ? WHERE STD_ID = ?");
			mParams.clear();
			mParams.add(taccount);
			mParams.add(sno);
			try {
				flag = flag
						&& JdbcManager.getJdbcManager().upDBData(
								sql.toString(), mParams);
			} catch (SQLException e) {
				System.out.println("******************更新毕业设计 * 更新学生信息异常");
				e.printStackTrace();
			}
			// 更新导师表
			if (flag) {
				System.out
						.println("************ updateDes  UPDATE stdinfo, design success");
				sql.delete(0, sql.length());
				sql.append("UPDATE teainfo SET TEA_STDCOUNT = TEA_STDCOUNT + 1 WHERE TEA_ID = ?;");
				mParams.clear();
				mParams.add(taccount);
				try {
					flag = flag
							&& JdbcManager.getJdbcManager().upDBData(
									sql.toString(), mParams);
				} catch (SQLException e) {
					System.out.println("******************导师选择学生*更新毕设信息异常");
					e.printStackTrace();
				}
			}
		}
		if (flag)
			return true;
		else
			return false;
	}

	/**
	 * 获得毕设信息
	 * 
	 * @param desID
	 *            毕设id
	 * @return
	 */
	public Design getDes(int desID) {
		Design des = new Design();

		sql.delete(0, sql.length());
		sql.append("select * from design where id = ?");
		mParams.add(desID);
		try {
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("获得毕设异常");
			e.printStackTrace();
		}

		des.setID(Integer.parseInt(result.get("ID").toString()));
		des.setName(result.get("DNAME").toString());
		des.setTea_id(Integer.parseInt(result.get("TEA_ID").toString()));
		des.setStd_id(result.get("STD_ID").toString());
		des.setIsselect(Integer.parseInt(result.get("ISSELECT").toString()));
		des.setDescription(result.get("DESCRIPTION").toString());
		des.setDate(result.get("DATE").toString());

		mParams.clear();
		result.clear();
		return des;
	}

	/**
	 * 通过学生账号获得毕设信息
	 * 
	 * @param sAccount
	 * @return
	 */
	public Design getDesByStdAccount(String sAccount) {
		Design des = new Design();

		sql.delete(0, sql.length()); // 清空
		sql.append("select * from design where STD_ID = ?");
		mParams.add(sAccount);
		try {
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println(" *** getDesByStdAccount 获得毕设异常");
			e.printStackTrace();
		}

		des.setID(Integer.parseInt(result.get("ID").toString()));
		des.setName(result.get("DNAME").toString());
		des.setTea_id(Integer.parseInt(result.get("TEA_ID").toString()));
		des.setStd_id(result.get("STD_ID").toString());
		des.setIsselect(Integer.parseInt(result.get("ISSELECT").toString()));
		des.setDescription(result.get("DESCRIPTION").toString());
		des.setDate(result.get("DATE").toString());

		mParams.clear();
		result.clear();
		return des;

	}

	/**
	 * 获取全部毕设列表
	 * 
	 * @param start
	 *            起始位置
	 * @return
	 */
	private List<Design> desList(int start) {
		List<Design> dlist = new ArrayList<Design>();

		sql.delete(0, sql.length());
		// 一次回复12条信息
		sql.append("select DNAME,TEA_ID,ID,DESCRIPTION,STD_ID,DATE,ISSELECT from design limit ?, 12");

		mParams.clear();
		mParams.add(start);

		List<Map<String, Object>> list = null;
		// 获取毕设信息
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
			System.out.println("********************list: " + list.toString());
		} catch (SQLException e) {
			System.out.println("获取毕设列表异常");
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			Design design = new Design();
			design.setID(Integer.parseInt(list.get(i).get("ID").toString()));
			design.setName(list.get(i).get("DNAME").toString());
			design.setTea_id(Integer.parseInt(list.get(i).get("TEA_ID")
					.toString()));
			if (list.get(i).get("STD_ID") != null
					&& !list.get(i).get("STD_ID").toString().isEmpty()) {
				design.setStd_id(list.get(i).get("STD_ID").toString());
			}
			design.setIsselect(Integer.parseInt(list.get(i).get("ISSELECT")
					.toString()));
			design.setDescription(list.get(i).get("DESCRIPTION").toString());
			design.setDate(list.get(i).get("DATE").toString());

			dlist.add(design);
		}

		// 取得结果中的值
		if (dlist != null && !dlist.isEmpty()) {
			return dlist;
		} else {
			System.out.println("list == null");
			return null;
		}
	}

	/**
	 * 获得毕设列表信息
	 * 
	 * @param start
	 *            开始位置
	 * @return
	 */
	public JSONArray getDesList(int start) {

		List<Design> design = desList(start);
		if (design != null) {
			JSONArray desArray = JSONArray.fromObject(design);
			return desArray;
		}
		return null;
	}

	@Override
	public boolean delete(String account) {
		return false;
	}

	@Override
	public boolean updateInfo(String account, Object person) {
		return false;
	}

	/**
	 * 通过毕设名称，获得毕设的id
	 * 
	 * @param dname
	 *            毕设名称
	 * @return -1 表示没有该毕业设计
	 */
	public int getDesID(String dname) {
		int result = -1;
		sql.delete(0, sql.length());
		sql.append("SELECT ID FROM design WHERE DNAME=?");
		mParams.clear();
		mParams.add(dname);

		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("****************获得毕业设计ID异常");
			e.printStackTrace();
		}
		if (map != null && !map.isEmpty()) {
			result = Integer.parseInt(map.get("ID").toString());
			return result;
		}
		return result;
	}

	/**
	 * 更新毕业设计
	 * 
	 * @param taccount
	 *            导师账号
	 * @param data
	 *            要更新的map
	 * @return
	 */
	public boolean updateDes(String taccount, Map<String, Object> data) {

		boolean flag = false;

		String sno = data.get("sno").toString();
		String dname = data.get("title").toString();
		String oldDname = data.get("oldtitle").toString();
		// 判断该毕业设计是否存在
		if (getDesID(oldDname) != -1) {
			System.out.println("*************getDesID(oldDname):"
					+ getDesID(oldDname));
			// 如果sno不为空，则要更新stdinfo表和design表，已经约束过了
			if (!"".equals(sno)) {

				sql.delete(0, sql.length());
				sql.append("UPDATE stdinfo SET ISSELECT = 1, HAS_TEA = 1, TEA_NUM = ? WHERE STD_ID = ?");
				mParams.clear();
				mParams.add(taccount);
				mParams.add(sno);
				boolean isCT = false;
				try {
					isCT = JdbcManager.getJdbcManager().upDBData(
							sql.toString(), mParams);
				} catch (SQLException e) {
					System.out.println("******************更新毕业设计 * 更新学生信息异常");
					e.printStackTrace();
				}
				if (isCT) {
					System.out
							.println("************ updateDes  UPDATE stdinfo success");
					sql.delete(0, sql.length());
					sql.append("UPDATE design SET DNAME=?,  STD_ID=?, CANCHOICE=?, ISSELECT=?, DESCRIPTION=? WHERE TEA_ID=? AND DNAME=?");
					mParams.clear();
					mParams.add(dname);
					mParams.add(sno);
					if ("uncheck".equals(data.get("check").toString())) {
						mParams.add(1);
					} else {
						mParams.add(0);
					}
					mParams.add(1);
					mParams.add(data.get("des").toString());
					mParams.add(taccount);
					mParams.add(oldDname);
					try {
						isCT = isCT
								&& JdbcManager.getJdbcManager().upDBData(
										sql.toString(), mParams);
					} catch (SQLException e) {
						System.out.println("*******************更新毕业设计信息异常");
						e.printStackTrace();
					}
				}
				if (isCT) {
					System.out
							.println("************ updateDes  UPDATE stdinfo, design success");
					sql.delete(0, sql.length());
					sql.append("UPDATE teainfo SET TEA_STDCOUNT = TEA_STDCOUNT + 1 WHERE TEA_ID = ?;");
					mParams.clear();
					mParams.add(taccount);
					try {
						isCT = isCT
								&& JdbcManager.getJdbcManager().upDBData(
										sql.toString(), mParams);
					} catch (SQLException e) {
						System.out.println("******************导师选择学生*更新毕设信息异常");
						e.printStackTrace();
					}
				}
				if (isCT)
					return true;
				else
					return false;
			} else {// 如果sno为“”的话，则只更新design表中的信息

				sql.delete(0, sql.length());
				sql.append("UPDATE design SET DNAME=?, CANCHOICE=?, ISSELECT=?, DESCRIPTION=? WHERE TEA_ID=? AND DNAME=?");
				mParams.clear();
				mParams.add(dname);
				if ("uncheck".equals(data.get("check").toString())) {
					mParams.add(1);
				} else {
					mParams.add(0);
				}
				mParams.add(0);
				mParams.add(data.get("des").toString());
				mParams.add(taccount);
				mParams.add(oldDname);
				try {
					flag = JdbcManager.getJdbcManager().upDBData(
							sql.toString(), mParams);
				} catch (SQLException e) {
					System.out.println("*******************更新毕业设计信息异常");
					e.printStackTrace();
				}
			}
			return flag;
		} else {
			System.out.println("************getDesID(oldDname) == -1");
			List<Object> params = new ArrayList<Object>();
			params.add(dname);
			params.add(taccount);
			if (!sno.isEmpty())
				params.add(sno);
			if ("uncheck".equals(data.get("check").toString())) {
				params.add(1);
			} else {
				params.add(0);
			}
			if (sno.isEmpty()) {
				params.add(0);
			} else {
				params.add(1);
			}
			params.add(data.get("des").toString());
			params.add(Time.getDate());
			flag = create(params);
			return flag;
		}
	}

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "毕业设计55");
		map.put("des", "描述......555555");
		map.put("check", "check");
		map.put("sno", "");
		map.put("oldtitle", "毕业设计5");
		System.out.println(map.toString());
		System.out
				.println("*********************" + updateDes("11111111", map));
	}

}
