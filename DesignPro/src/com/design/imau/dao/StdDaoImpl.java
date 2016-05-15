package com.design.imau.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.design.imau.bean.Design;
import com.design.imau.bean.Student;
import com.design.imau.bean.Teacher;
import com.design.imau.utils.jdbc.JdbcManager;
import com.design.imau.utils.other.Translate;

public class StdDaoImpl implements InfoDao, PeoDao {

	private StringBuilder sql;
	private List<Object> mParams;
	private Map<String, Object> result;

	public StdDaoImpl() {
		sql = new StringBuilder();
		mParams = new ArrayList<Object>();
		result = new HashMap<String, Object>();
	}

	/**
	 * 新增学生信息
	 * 
	 * @param mParams
	 *            顺序为：学号,姓名,性别,专业,年级,班级,电话,创建日期
	 * @return
	 */
	public boolean create(List<Object> params) {

		boolean flag = false;

		mParams.clear();
		mParams = params;
		sql.delete(0, sql.length()); // 清空
		sql.append("insert into stdinfo(STD_ID,STD_NAME,STD_SEX,STD_MAJOR,STD_GRADE,STD_CLASS,STD_PHONE,STD_DATE) values(?,?,?,?,?,?,?,?)");
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("账户新增出错");
			e.printStackTrace();
		}

		return flag;

	}

	/**
	 * 获得学生详细信息
	 * 
	 * @param account
	 *            账号(学号)
	 * @return
	 */
	public Student getStd(String account) {
		Student std = new Student();

		sql.delete(0, sql.length()); // 清空
		sql.append("select * from stdinfo where std_id = ?");
		mParams.clear();
		mParams.add(account);
		try {

			result.clear();
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("获得学生信息出错");
			e.printStackTrace();
		}
		std.setId(Integer.parseInt(account));
		std.setName((String) result.get("STD_NAME"));
		std.setSex((String) result.get("STD_SEX"));
		std.setMajor((String) result.get("STD_MAJOR"));
		std.setGrade((String) result.get("STD_GRADE"));
		std.setSclass((String) result.get("STD_CLASS"));
		std.setPhone((String) result.get("STD_PHONE"));
		std.setTeaNum("" + result.get("TEA_NUM"));
		std.setQQ((String) result.get("STD_QQ"));
		std.setSelf((String) result.get("STD_SELF"));
		std.setImgURL((String) result.get("STD_HEAD"));

		return std;
	}

	/**
	 * 删除学生信息
	 * 
	 * @param account
	 *            账号(学号)
	 * @return
	 */
	public boolean delete(String account) {
		boolean flag = false;

		sql.delete(0, sql.length());// 清空
		sql.append("delete from stdinfor where account = ?");
		mParams.clear();
		mParams.add(account);
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("删除学生信息出错");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 更新信息
	 */
	public boolean updateInfo(String account, Object person) {
		boolean flag = false;
		Student std = (Student) person;
		sql.delete(0, sql.length());
		sql.append("update stdinfo set STD_NAME=?,STD_SEX=?,STD_MAJOR=?,STD_GRADE=?,STD_CLASS=?,STD_PHONE=?,STD_QQ=?,STD_SELF=? WHERE STD_ID=?");
		mParams.clear();
		mParams.add(std.getName());
		mParams.add(std.getSex());
		mParams.add(std.getMajor());
		mParams.add(std.getGrade());
		mParams.add(std.getSclass());
		mParams.add(std.getPhone());
		mParams.add(std.getQQ());
		mParams.add(std.getSelf());
		mParams.add(account);
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("更新学生信息出错");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 获得学生列表，一次12条
	 * 
	 * @param start
	 * @return
	 */
	public JSONArray stdList(int start) {

		List<Map<String, Object>> list = null;
		sql.delete(0, sql.length());
		sql.append("SELECT stdinfo.STD_ID, STD_NAME, STD_MAJOR, STD_CLASS, STD_DATE, stdinfo.ISSELECT,STD_HEAD,teainfo.TEA_NAME FROM stdinfo LEFT JOIN teainfo ON stdinfo.TEA_NUM = teainfo.TEA_ID ORDER BY TEA_NAME DESC LIMIT ?, 12;");

		mParams.clear();
		mParams.add(start);
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					mParams);
			System.out.println("************" + list + "***********");
		} catch (SQLException e) {
			System.out.println("*********** 获得学生列表异常 ***********");
			e.printStackTrace();
		}

		if (!list.isEmpty() && list != null) {
			List<Student> stdlist = new ArrayList<Student>();
			for (int i = 0; i < list.size(); i++) {
				Student std = new Student();
				std.setId(Integer
						.parseInt(list.get(i).get("STD_ID").toString()));
				std.setName(list.get(i).get("STD_NAME").toString());
				std.setMajor(Translate.getMajor(list.get(i).get("STD_MAJOR")
						.toString()));
				std.setSclass(list.get(i).get("STD_CLASS").toString());
				std.setDate(list.get(i).get("STD_DATE").toString());
				std.setSelect(Boolean.parseBoolean(list.get(i).get("ISSELECT")
						.toString()));
				std.setBelong(list.get(i).get("TEA_NAME").toString());
				std.setImgURL(list.get(i).get("STD_HEAD").toString());
				// 添加列表
				stdlist.add(std);
			}
			return JSONArray.fromObject(stdlist);
		} else {
			System.out
					.println("********************stdList****list null**************");
		}
		return null;
	}

	@Override
	public String image(String account) {
		sql.delete(0, sql.length());
		sql.append("select STD_HEAD from stdinfo where STD_ID = ?");
		String url = "";
		mParams.clear();
		mParams.add(account);

		try {
			result.clear();
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out.println("**********获得学生头像出错**************");
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
		sql.append("select STD_NAME,STD_MAJOR,STD_HEAD,ISSELECT,HAS_TEA,TEA_NUM from stdinfo where STD_ID=?");

		mParams.clear();
		mParams.add(account);

		try {
			result.clear();
			result = JdbcManager.getJdbcManager().getSingleResult(
					sql.toString(), mParams);
		} catch (SQLException e) {
			System.out
					.println("*********************获得学生基本信息出错**********************");
			e.printStackTrace();
		}

		if (result != null) {
			result.put("STD_MAJOR",
					Translate.getMajor(result.get("STD_MAJOR").toString()));
			return result;
		}
		return null;
	}

	@Override
	public boolean updateHead(String path, String imgName, String account) {

		boolean flag = false;
		sql.delete(0, sql.length());
		sql.append("update stdinfo set STD_HEAD=? where STD_ID=?");

		mParams.clear();
		mParams.add(path + imgName);
		System.out.println("******************" + path + imgName);
		mParams.add(account);

		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("***学生头像更新异常***");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 选择导师
	 * 
	 * @param teaNum
	 *            教工号
	 * @param account
	 *            学号，即账号
	 * @return true:操作成功,false:操作失败
	 */
	public boolean selectTea(String teaNum, String account) {
		sql.delete(0, sql.length());
		sql.append("UPDATE stdinfo,teainfo SET stdinfo.HAS_TEA = 1, stdinfo.TEA_NUM=?, teainfo.TEA_STDCOUNT=TEA_STDCOUNT+1 WHERE STD_ID=? AND TEA_STDCOUNT<(SELECT scount FROM a_control) AND teainfo.TEA_ID = ?");

		mParams.clear();
		mParams.add(teaNum);
		mParams.add(account);
		mParams.add(teaNum);

		boolean flag = false;
		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out
					.println("**************selectTea：学生选择导师异常******************");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 获得学生查看导师信息
	 * 
	 * @param teano
	 *            教工号
	 * @return
	 */
	public JSONArray getTeaInfo(String teano) {

		sql.delete(0, sql.length());
		sql.append("select * from teainfo where TEA_ID = ?");

		mParams.clear();
		mParams.add(teano);

		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("********************学生查看导师,获得基本信息异常");
			e.printStackTrace();
		}

		// 如果导师信息存在
		if (map != null && !map.isEmpty()) {
			List<Map<String, Object>> list = null;
			try {
				sql.delete(0, sql.length());
				sql.append("select DNAME, DESCRIPTION, STD_ID from design where TEA_ID=?");

				list = JdbcManager.getJdbcManager().getMultiResult(
						sql.toString(), mParams);
			} catch (SQLException e) {
				System.out.println("********************学生查看导师,获得毕设列表");
				e.printStackTrace();
			}
			if (list != null && !list.isEmpty()) {
				Teacher tea = new Teacher();
				tea.setId(Integer.parseInt(map.get("TEA_ID").toString()));
				tea.setName(map.get("TEA_NAME").toString());
				// 将专业转换成中文
				tea.setMajor(Translate
						.getMajor(map.get("TEA_MAJOR").toString()));
				tea.setSex(Translate.getSex(map.get("TEA_SEX").toString()));
				tea.setSelf(map.get("TEA_SELF").toString());
				tea.setTitle(map.get("TEA_TITLE").toString());
				tea.setImgURL(map.get("TEA_HEAD").toString());
				tea.setWX(map.get("TEA_WX").toString());
				tea.setQQ(map.get("TEA_QQ").toString());
				tea.setPhone(map.get("TEA_PHONE").toString());

				List<Design> desList = new ArrayList<Design>();
				for (int i = 0; i < list.size(); i++) {
					Design des = new Design();
					des.setName(list.get(i).get("DNAME").toString());
					des.setDescription(list.get(i).get("DESCRIPTION")
							.toString());
					des.setStd_id(list.get(i).get("STD_ID").toString());
					desList.add(des);
				}
				// 将列表写入teacher对象
				tea.setDesList(desList);

				JSONArray array = new JSONArray();
				array.add(tea);
				return array;
			}
		}

		return null;
	}

	public JSONObject getMyDes(String account) {

		// 清空数据
		sql.delete(0, sql.length());
		sql.append("SELECT DNAME,DESCRIPTION,TEA_NAME,TEA_SELF,TEA_PHONE,TEA_WX,TEA_QQ FROM design,teainfo WHERE design.TEA_ID = teainfo.TEA_ID AND design.STD_ID=?;");

		// 清空参数数据
		mParams.clear();
		mParams.add(account);

		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("*******************学生获取‘我的毕设’异常");
			e.printStackTrace();
		}
		// 如果map有值
		if (map != null && !map.isEmpty()) {
			JSONObject obj = JSONObject.fromObject(map);
			return obj;
		}
		return null;
	}

	/**
	 * 获得学生名字和学号
	 * 
	 * @return JSONArray
	 */
	public JSONArray snameSno() {
		sql.delete(0, sql.length());
		sql.append("SELECT STD_ID, STD_NAME FROM stdinfo WHERE ISSELECT = 0 AND HAS_TEA = 0");
		List<Map<String, Object>> list = null;
		try {
			list = JdbcManager.getJdbcManager().getMultiResult(sql.toString(),
					null);
		} catch (SQLException e) {
			System.out.println("****************snameSno**异常");
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			JSONArray array = JSONArray.fromObject(list);
			return array;
		}
		return null;
	}

	/**
	 * 判断学生是否已经选题
	 * 
	 * @param sno
	 * @return
	 */
	public boolean isChoiceTitle(String sno) {
		sql.delete(0, sql.length());
		sql.append("SELECT ISSELECT from stdinfo WHERE STD_ID = ? ");
		mParams.clear();
		mParams.add(sno);

		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("************** isChoiceTitle * 异常");
			e.printStackTrace();
		}
		if (map != null && !map.isEmpty()) {
			return Boolean.parseBoolean(map.get("ISSELECT").toString());
		}
		return false;
	}

	@Override
	public boolean changePass(String account, String oldPass, String newPass) {
		sql.delete(0, sql.length());
		sql.append("UPDATE account SET PASSWD=? WHERE IDS=?AND who='1' AND PASSWD=?");
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
		sql.append("UPDATE account SET ASTATUS=0 WHERE who='1' AND IDS=?");
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
	 * 学生选择毕设，更新学生表,毕设表,导师表信息
	 * 
	 * @param saccount
	 * @return
	 */
	public boolean selectDes(String saccount, String did, String tno) {
		System.out.println("**** tno: " + tno);
		boolean flag = false;
		// 更新学生表
		sql.delete(0, sql.length());
		sql.append("UPDATE stdinfo SET ISSELECT = 1, HAS_TEA = 1 WHERE STD_ID = ?");
		mParams.clear();
		mParams.add(saccount);

		try {
			flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("*********** selectDes 更新学生信息异常");
			e.printStackTrace();
		}
		if (flag) {
			// 更新毕设表
			System.out.println("************ update stdinfo success");
			sql.delete(0, sql.length());
			sql.append("UPDATE design SET STD_ID=?,CANCHOICE=0, ISSELECT=1 WHERE id=?");
			mParams.clear();
			mParams.add(saccount);
			mParams.add(did);

			try {
				flag = JdbcManager.getJdbcManager().upDBData(sql.toString(),
						mParams);
			} catch (SQLException e) {
				System.out.println("*********** selectDes 更新学生信息异常");
				e.printStackTrace();
			}
		}

		if (flag) {
			System.out.println("************ update stdinfo,design success");
			return true;
		} else
			return false;
	}

	/**
	 * 判断是否以选导师
	 * 
	 * @param saccount
	 * @return
	 */
	public String hasTea(String saccount) {
		// 更新导师表
		sql.delete(0, sql.length());
		sql.append("SELECT HAS_TEA, TEA_NUM FROM stdinfo WHERE STD_ID=?");
		mParams.clear();
		mParams.add(saccount);

		Map<String, Object> map = null;
		try {
			map = JdbcManager.getJdbcManager().getSingleResult(sql.toString(),
					mParams);
		} catch (SQLException e) {
			System.out.println("*********** hasTea 异常");
			e.printStackTrace();
		}
		if (map != null && !map.isEmpty()) {
			if (Boolean.parseBoolean(map.get("HAS_TEA").toString())) {
				return map.get("TEA_NUM").toString();
			}
		}
		return null;
	}

	@Test
	public void test() {
		System.out.println(JSONObject.fromObject(getStd("111014070")));
	}
}
