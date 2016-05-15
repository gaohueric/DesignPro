package com.design.imau.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;

import com.design.imau.bean.Design;
import com.design.imau.bean.Student;
import com.design.imau.bean.Teacher;
import com.design.imau.utils.jdbc.JdbcManager;
import com.design.imau.utils.other.Translate;

public class TeaDaoImpl implements InfoDao, PeoDao {
	private StringBuilder sql;
	private List<Object> mParams;
	private Map<String, Object> result;

	public TeaDaoImpl() {
		sql = new StringBuilder();
		mParams = new ArrayList<Object>();
		result = new HashMap<String, Object>();
	}

	/**
	 * 新增导师信息
	 * 
	 * @param mParams
	 *            顺序为：教工号,姓名,性别,专业,职称,电话,创建日期
	 * @return
	 */
	public boolean create(List<Object> params) {

		boolean flag = false;
		mParams.clear();
		mParams = params;
		sql.delete(0, sql.length()); // 清空
		sql.append("insert into teainfo(TEA_ID,TEA_NAME,TEA_SEX,TEA_MAJOR,TEA_TITLE,TEA_PHONE,TEA_DATE) values(?,?,?,?,?,?,?)");
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("账户新增出错");
			e.printStackTrace();
		}

		mParams.clear();
		return flag;

	}

	/**
	 * 获得导师详细信息
	 * 
	 * @param account
	 *            账号(教工号)
	 * @return
	 */
	public Teacher getTea(String account) {

		sql.delete(0, sql.length()); // 清空
		sql.append("select * from teainfo where tea_id = ?");
		mParams.clear();
		mParams.add(account);
		try {
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("获得导师信息出错");
			e.printStackTrace();
		}
		if (result != null) {
			Teacher tea = new Teacher();
			tea.setId(Integer.parseInt(account));
			tea.setName((String) result.get("TEA_NAME"));
			tea.setSex((String) result.get("TEA_SEX"));
			tea.setMajor((String) result.get("TEA_MAJOR"));
			tea.setTitle((String) result.get("TEA_TITLE"));
			tea.setPhone((String) result.get("TEA_PHONE"));
			tea.setQQ((String) result.get("TEA_QQ"));
			tea.setWX((String) result.get("TEA_WX"));
			tea.setSelf((String) result.get("TEA_SELF"));
			tea.setImgURL((String) result.get("TEA_HEAD"));
			return tea;
		}
		return null;
	}

	/**
	 * 删除导师信息
	 * 
	 * @param account
	 *            账号(教工号)
	 * @return
	 */
	public boolean delete(String account) {
		boolean flag = false;

		sql.delete(0, sql.length()); // 清空
		sql.append("delete from teainfor where account = ?");
		mParams.add(account);
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out
					.println("********************TeaDaoImpl***删除导师信息出错************************");
			e.printStackTrace();
		}
		mParams.clear();
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.design.imau.dao.InfoDao#updateInfo(java.lang.String,
	 * java.lang.Object)
	 */
	public boolean updateInfo(String account, Object person) {
		boolean flag = false;
		Teacher tea = (Teacher) person;
		// 清空
		sql.delete(0, sql.length());
		sql.append("update teainfo set TEA_NAME=?,TEA_SEX=?,TEA_MAJOR=?,TEA_TITLE=?,TEA_PHONE=?,TEA_QQ=?,TEA_WX=?,TEA_SELF=? WHERE TEA_ID="
				+ account);
		mParams.clear();
		mParams.add(tea.getName());
		mParams.add(tea.getSex());
		mParams.add(tea.getMajor());
		mParams.add(tea.getTitle());
		mParams.add(tea.getPhone());
		mParams.add(tea.getQQ());
		mParams.add(tea.getWX());
		mParams.add(tea.getSelf());
		System.out.println(mParams.toString());
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out
					.println("********************TeaDaoImpl***导师修改信息异常************************");
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获得导师列表：姓名，学生数量，头像，自我介绍，
	 * 
	 * @param params
	 * @return
	 */
	public JSONArray teaList(int start) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray array = new JSONArray();
		sql.delete(0, sql.length());
		sql.append("SELECT TEA_ID,TEA_NAME,TEA_MAJOR,TEA_TITLE,TEA_STDCOUNT,TEA_HEAD,TEA_DATE,scount FROM teainfo,a_control LIMIT ?,12;");
		mParams.add(start);
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out
					.println("********************TeaDaoImpl***获取导师列表异常************************");
			e.printStackTrace();
		}
		mParams.clear();
		// 取得结果中的值
		if (list != null && !list.isEmpty()) {
			System.out
					.println("********************TeaDaoImpl***********list != null********************");
			for (int i = 0; i < list.size(); i++) {
				Teacher tea = new Teacher();
				tea.setId(Integer
						.parseInt(list.get(i).get("TEA_ID").toString()));
				tea.setName(list.get(i).get("TEA_NAME").toString());
				// 将专业转换成中文
				tea.setMajor(Translate.getMajor(list.get(i).get("TEA_MAJOR")
						.toString()));
				tea.setTitle(list.get(i).get("TEA_TITLE").toString());
				tea.setImgURL(list.get(i).get("TEA_HEAD").toString());
				tea.setStdCount(Integer.parseInt(list.get(i)
						.get("TEA_STDCOUNT").toString()));
				tea.setDate(list.get(i).get("TEA_DATE").toString());
				// 如果offset>0，则表示导师所拥有的学生名额未满，学生仍然可以选择导师，
				int offset = list.get(i).get("scount").toString()
						.compareTo(list.get(i).get("TEA_STDCOUNT").toString());
				if (offset > 0) {
					tea.setFull(false);
				} else {
					tea.setFull(true);
				}

				array.add(tea);
			}
			return array;
		} else {
			System.out
					.println("********************TeaDaoImpl***********list == null || list.isEmpty********************");
		}
		return null;
	}

	@Override
	public String image(String account) {

		sql.delete(0, sql.length());
		sql.append("select TEA_HEAD from teainfo where TEA_ID = ?");
		String url = "";
		mParams.clear();
		mParams.add(account);

		try {
			result.clear();
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("**************获得导师头像出错***************");
			e.printStackTrace();
		}

		if (!result.isEmpty()) {
			url = result.get("STD_HEAD").toString();
			return url;
		}
		return null;
	}

	@Override
	public Map<String, Object> baseInfo(String account) {

		sql.delete(0, sql.length());
		sql.append("select TEA_NAME,TEA_MAJOR,TEA_HEAD,TEA_STDCOUNT from teainfo where TEA_ID=?");

		mParams.clear();
		mParams.add(account);

		try {
			result.clear();
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("***********获得导师基本信息出错***********");
			e.printStackTrace();
		}

		System.out.println("result: "+result.toString());
		if (result != null) {
			System.out.println("tea_major: "+ result.get("TEA_MAJOR"));
			result.put("TEA_MAJOR",
					Translate.getMajor(result.get("TEA_MAJOR").toString()));
			return result;
		}
		return null;
	}

	@Override
	public boolean updateHead(String path, String imgName, String account) {

		boolean flag = false;
		sql.delete(0, sql.length());
		sql.append("update teainfo set TEA_HEAD=? where TEA_ID=?");

		mParams.clear();
		mParams.add(path + imgName);
		System.out.println("******************" + path + imgName);
		mParams.add(account);

		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("***导师头像更新异常***");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 获得导师自己的毕设列表
	 * 
	 * @param account
	 * @return
	 */
	public JSONArray getTeaDeses(String account) {

		sql.delete(0, sql.length());
		sql.append("SELECT * FROM design WHERE TEA_ID = ?");

		mParams.clear();
		mParams.add(account);

		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("******************getTeaDeses**获得导师列表异常");
			e.printStackTrace();
		}

		if (!list.isEmpty() && list != null) {
			List<Design> dList = new ArrayList<Design>();
			for (int i = 0; i < list.size(); i++) {
				Design design = new Design();
				design.setID(Integer.parseInt(list.get(i).get("ID").toString()));
				design.setName(list.get(i).get("DNAME").toString());
				if (list.get(i).get("STD_ID") != null
						&& !list.get(i).get("STD_ID").toString().isEmpty()) {
					design.setStd_id(list.get(i).get("STD_ID").toString());
				}
				design.setTea_id(Integer.parseInt(list.get(i).get("TEA_ID")
						.toString()));
				design.setCanchoice(Integer.parseInt(list.get(i)
						.get("CANCHOICE").toString()));
				design.setIsselect(Integer.parseInt(list.get(i).get("ISSELECT")
						.toString()));
				design.setDescription(list.get(i).get("DESCRIPTION").toString());
				design.setDate(list.get(i).get("DATE").toString());
				// 数据写入dlist
				dList.add(design);
			}

			JSONArray array = JSONArray.fromObject(dList);
			return array;
		}
		return null;
	}

	/**
	 * 获得学生姓名及其对应的毕业设计题目列表
	 * 
	 * @param taccount
	 *            导师账号
	 * @return 返回的列表集合
	 */
	public List<Map<String, Object>> getStdWithDname(String taccount) {

		sql.delete(0, sql.length());
		sql.append("SELECT design.DNAME,stdinfo.STD_NAME FROM design,stdinfo WHERE design.STD_ID = stdinfo.STD_ID AND design.TEA_ID=?");

		mParams.clear();
		mParams.add(taccount);

		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("****************获得学生姓名及其毕业设计题目异常*************");
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public Map<String, Object> maxSCount() {
		sql.delete(0, sql.length());
		sql.append("SELECT scount FROM a_control WHERE ID = 1;");
		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					null);
		} catch (SQLException e) {
			System.out.println("*************获得最大学生数量异常**************");
			e.printStackTrace();
		}
		if (map != null && !map.isEmpty()) {
			return map;
		}
		return null;
	}

	public JSONArray myStdList(String taccount) {

		sql.delete(0, sql.length());
		sql.append("SELECT stdinfo.STD_ID, STD_NAME, STD_MAJOR, STD_CLASS, STD_DATE, STD_SELF, TEA_NAME, STD_HEAD FROM stdinfo,teainfo,design WHERE stdinfo.STD_ID = design.STD_ID AND design.TEA_ID = teainfo.TEA_ID AND stdinfo.TEA_NUM=?");
		mParams.clear();
		mParams.add(taccount);

		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("***************获得导师学生列表异常****");
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			List<Student> mystdlist = new ArrayList<Student>();
			for (int i = 0; i < list.size(); i++) {
				Student std = new Student();
				std.setId(Integer
						.parseInt(list.get(i).get("STD_ID").toString()));
				std.setName(list.get(i).get("STD_NAME").toString());
				std.setMajor(Translate.getMajor(list.get(i).get("STD_MAJOR")
						.toString()));
				std.setSclass(list.get(i).get("STD_CLASS").toString());
				std.setDate(list.get(i).get("STD_DATE").toString());
				std.setSelf(list.get(i).get("STD_SELF").toString());
				std.setBelong(list.get(i).get("TEA_NAME").toString());
				std.setImgURL(list.get(i).get("STD_HEAD").toString());
				// 添加列表
				mystdlist.add(std);
			}
			return JSONArray.fromObject(mystdlist);
		}

		return null;
	}

	public JSONArray getDesTitleAndID(String taccount) {

		sql.delete(0, sql.length());
		sql.append("SELECT ID, DNAME FROM design WHERE TEA_ID = ? AND ISSELECT = 0");
		mParams.clear();
		mParams.add(taccount);

		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("****************获得毕业设计的id和题目异常");
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			JSONArray array = JSONArray.fromObject(list);
			return array;
		}
		return null;
	}

	/**
	 * 导师选择学生
	 * 
	 * @param taccount
	 *            导师账号
	 * @param sno
	 *            学生账号
	 * @param did
	 *            毕设id
	 * @return 如果操作成功，返回true
	 */
	public boolean selectStd(String taccount, String sno, String did) {
		// 先更新学生信息（确保操作成功）,如果成功，则更新design信息，如果都更新成功，在更新导师信息。
		sql.delete(0, sql.length());
		sql.append("UPDATE stdinfo SET ISSELECT = 1, HAS_TEA = 1, TEA_NUM = ? WHERE STD_ID = ?");
		mParams.clear();
		mParams.add(taccount);
		mParams.add(sno);
		boolean flag = false;
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("******************导师选择学生*更新学生信息异常");
			e.printStackTrace();
		}
		if (flag) {
			System.out.println("************  UPDATE stdinfo success");
			sql.delete(0, sql.length());
			sql.append("UPDATE design SET STD_ID = ?, CANCHOICE = 0, ISSELECT = 1 WHERE ID=? AND TEA_ID=?;");
			mParams.clear();
			mParams.add(sno);
			mParams.add(did);
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
		if (flag) {
			System.out.println("************  UPDATE stdinfo, design success");
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
		// 如果操作全部成功，返回
		if (flag) {
			System.out
					.println("************  UPDATE stdinfo, design, teainfo success");
			return true;
		}
		return false;
	}

	@Override
	public boolean changePass(String account, String oldPass, String newPass) {
		sql.delete(0, sql.length());
		sql.append("UPDATE account SET PASSWD=? WHERE IDS=?AND who='2' AND PASSWD=?");
		mParams.clear();
		mParams.add(newPass);
		mParams.add(account);
		mParams.add(oldPass);

		boolean flag = false;
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("***************学生更新密码异常");
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean exitSystem(String account) {
		sql.delete(0, sql.length());
		sql.append("UPDATE account SET ASTATUS=0 WHERE who='2' AND IDS=?");
		mParams.clear();
		mParams.add(account);
		boolean flag = false;
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("******* 学生退出系统异常");
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获得导师姓名和教工号
	 * 
	 * @return
	 */
	public JSONArray getTeaNN() {
		sql.delete(0, sql.length());
		sql.append("SELECT TEA_NAME, TEA_ID FROM teainfo WHERE TEA_STDCOUNT < 4");
		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					null);
		} catch (SQLException e) {
			System.out.println("*********** getTeaNN 异常");
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			JSONArray array = JSONArray.fromObject(list);
			return array;
		}
		return null;
	}

	@Test
	public void test() {
		System.out.println(getTeaNN().toString());
	}

}
