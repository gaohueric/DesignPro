package com.design.monitor.imau.biz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Transaction;
import org.junit.Test;

import com.design.imau.bean.MyFiles;
import com.design.imau.bean.Student;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.MDLog;
import com.design.imau.utils.other.PushMessage;
import com.design.monitor.imau.dao.Dinfo;
import com.design.monitor.imau.dao.DinfoDAO;
import com.design.monitor.imau.dao.Notification;
import com.design.monitor.imau.dao.NotificationDAO;

public class FileUpLoadBiz {
	private static final String TAG = FileUpLoadBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private StdDaoImpl sdi;
	private TeaDaoImpl tdi;
	private DinfoDAO dDao;
	private Transaction t;
	private NotificationDAO nDAO;

	public FileUpLoadBiz() {
		sdi = new StdDaoImpl();
		tdi = new TeaDaoImpl();
		dDao = new DinfoDAO();
		nDAO = new NotificationDAO();
	}

	public Dinfo getDinfoByStdId(int stdId) {
		Dinfo dinfo = null;
		try {
			List<?> list = dDao.findByStdId(stdId);
			if (list != null && !list.isEmpty()) {
				dinfo = (Dinfo) list.get(0);
			}
		} catch (Exception e) {
			log.error(TAG, "getDinfoByStdId", e);
		} finally {
			dDao.getSession().close();
		}
		return dinfo;
	}

	public Dinfo getDinfoByTeaId(int teaId) {
		Dinfo dinfo = null;
		try {
			dinfo = (Dinfo) dDao.findByTeaId(teaId).get(0);
		} catch (Exception e) {
			log.error(TAG, "getDinfoByTeaId", e);
		} finally {
			dDao.getSession().close();
		}
		return dinfo;
	}

	public boolean stdUpdateInfo(String sid, String tid, String dname,
			String path, int upFlag) {
		// 获得导师id
		String tnum = getTid(sid);

		// 提交信息
		Dinfo info = getDinfoByStdId(Integer.parseInt(sid));
		int supcount = 0;
		int tupcount = 0;
		if (info == null) {
			info = new Dinfo();
		} else {
			supcount = info.getSupcount();
			tupcount = info.getTupcount();
		}
		//
		if (upFlag == 1) {
			info.setStdId(Integer.parseInt(sid));
			info.setTeaId(Integer.parseInt(tnum));
			info.setSupcount(supcount + 1);
			info.setTupcount(tupcount);
		}
		if (upFlag == 2) {
			info.setStdId(Integer.parseInt(sid));
			info.setTeaId(Integer.parseInt(tid));
			info.setSupcount(supcount);
			info.setTupcount(tupcount + 1);
		}
		info.setDname(dname);

		info.setDpath(path + "/" + dname);
		// 如果sdowncount为空
		Integer sdownCount = info.getSdowncount();
		if (sdownCount == null) {
			info.setSdowncount(0);
		}
		Integer tdownCount = info.getTdowncount();
		if (tdownCount == null) {
			info.setTdowncount(0);
		}

		// 获得当前时间
		Date date = new Date();
		date.getTime();
		info.setUptime(date);

		Date downtime = info.getDowntime();
		if (downtime == null) {
			info.setDowntime(date);
		}

		// 更新数据库
		t = dDao.getSession().beginTransaction();
		try {
			dDao.attachDirty(info);
			t.commit();
		} catch (Exception e) {
			log.debug(TAG, "updateInfo", "新增论文信息异常");
			log.error(TAG, "updateInfo", e);
			return false;
		} finally {
			dDao.getSession().close();
		}

		return true;
	}

	public String getTid(String sid) {
		Student std = sdi.getStd(sid);
		return std.getTeaNum();
	}

	public JSONArray teaGetFiles(String path, String realPath) {
		File dir = new File(path);
		List<MyFiles> fileList = new ArrayList<MyFiles>();
		if (dir.isDirectory()) {
			File next[] = dir.listFiles();
			for (int i = 0; i < next.length; i++) {
				MyFiles file = new MyFiles();
				file.setName(next[i].getName());
				file.setPath("." + realPath + "/" + next[i].getName());
				System.out.println("next[" + i + "]:" + next[i].getPath());
				fileList.add(file);
			}
		}
		JSONArray files = JSONArray.fromObject(fileList);
		return files;
	}

	public JSONArray teaGetStdDes(String tid) {

		try {
			List<?> dinfos = dDao.findByTeaId(Integer.parseInt(tid));
			List<MyFiles> fileList = new ArrayList<MyFiles>();
			for (int i = 0; i < dinfos.size(); i++) {
				Dinfo dinfo = (Dinfo) dinfos.get(i);
				// 获得日期
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				String date = f.format(dinfo.getUptime());

				MyFiles files = new MyFiles();
				files.setDate(date);
				files.setName(dinfo.getDname());
				files.setPath("./" + dinfo.getDpath());

				// 学生姓名
				String sname = sdi.getStd("" + dinfo.getStdId()).getName();
				files.setSname(sname);

				fileList.add(files);
			}
			JSONArray array = JSONArray.fromObject(fileList);
			return array;

		} catch (Exception e) {
			log.error(TAG, "teaGetStdDes", e);
		} finally {
			dDao.getSession().close();
		}
		return null;
	}

	public JSONArray stdNumName(String tid) {
		JSONArray array = tdi.myStdList(tid);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < array.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			String name = array.getJSONObject(i).getString("name");
			String num = "" + array.getJSONObject(i).getInt("id");
			map.put("name", name);
			map.put("num", num);
			result.add(map);
		}
		array = JSONArray.fromObject(result);
		return array;
	}

	public JSONObject getSelfDes(String tid) {

		Dinfo dinfo = getDinfoByStdId(Integer.parseInt(tid));
		MyFiles file = new MyFiles();
		file.setName(dinfo.getDname());
		file.setPath("./" + dinfo.getDpath());

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(dinfo.getUptime());
		file.setDate(date);

		JSONObject obj = JSONObject.fromObject(file);
		return obj;
	}

	/**
	 * 学生上传文件，推送到导师手机客户端
	 * 
	 * @param type
	 * @param sid
	 * @param msg
	 * @return
	 */
	public boolean stdUplocationNotifi(String sid, PushMessage msg) {

		String tid = getTid(sid);
		if (tid == null || tid.isEmpty()) {
			log.d(getClass(), "stdUplocationNotifi", "获得导师账号为空");
			return false;
		}
		log.d(getClass(), "stdUplocationNotifi", "tid = " + tid);
		List<?> list = nDAO.findByAccount(Integer.parseInt(tid));
		if (list == null) {
			log.d(getClass(), "stdUplocationNotifi", "导师没有注册手机客户端");
			return false;
		}
		Notification notify = (Notification) list.get(0);
		NotificationBiz nBiz = NotificationBiz.getInstance();
		boolean flag = nBiz.sendNotification(
				Long.parseLong(notify.getChannelid()), notify.getUserid(), msg);

		log.d(getClass(), "stdUplocationNotifi", "是否推送成功:" + flag);
		return flag;
	}

	/**
	 * 导师上传文件，推送到学生客户端
	 * 
	 * @param type
	 * @param sid
	 * @param msg
	 * @return
	 */
	public boolean teaUplocationNotifi(String sid, PushMessage msg) {

		List<?> list = nDAO.findByAccount(Integer.parseInt(sid));
		if (list == null || list.isEmpty()) {
			log.d(getClass(), "teaUplocationNotifi", sid + " :该学生没有注册手机客户端");
			return false;
		}
		Notification notify = (Notification) list.get(0);
		NotificationBiz nBiz = NotificationBiz.getInstance();
		boolean flag = nBiz.sendNotification(
				Long.parseLong(notify.getChannelid()), notify.getUserid(), msg);

		log.d(getClass(), "teaUplocationNotifi", "是否推送成功:" + flag);
		return flag;
	}

	@Test
	public void test() {
		PushMessage msg = new PushMessage();
		msg.setTitle("学生操作提醒");
		msg.setDescription("您的学生上传了他的毕业设计论文，论文名：123.doc，请下载查看");
		stdUplocationNotifi("111014064", msg);
		// System.out.println(teaGetStdDes("11111111").toString());
	}
}
