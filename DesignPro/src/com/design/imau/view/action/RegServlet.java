package com.design.imau.view.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.AccDaoImpl;
import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;
import com.design.imau.utils.other.DataUrl;
import com.design.imau.utils.other.Time;
import com.design.imau.utils.other.Translate;

public class RegServlet extends HttpServlet {

	private AccDaoImpl accDao;
	private StdDaoImpl stdDao;
	private TeaDaoImpl teaDao;

	private static final long serialVersionUID = 1L;

	public RegServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 编码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}

		// 获取导师和学生共有的信息
		String who = request.getParameter("who"); // 1：学生，2：导师
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String major = request.getParameter("major");
		String phone = request.getParameter("phone");

		// 参数列表
		List<Object> params = new ArrayList<Object>();
		// 获得当前注册日期
		String date = Time.getDate();
		if ("1".equals(who)) {
			String grade = request.getParameter("grade");
			String sclass = request.getParameter("sclass");

			System.out.println(who + "\n" + account + "\n" + passwd + "\n"
					+ name + "\n" + Translate.getSex(sex) + "\n"
					+ Translate.getMajor(major) + "\n" + grade + "\n" + sclass
					+ "\n" + phone + "\n" + date);

			// 判断账号是否存在
			if (!accDao.isAccExist(account)) {
				System.out.println("***********学生注册**************");
				// 账号存在
				// 标记学生信息创建成功
				boolean scs = false;
				// 标记学生账号创建成功
				boolean acs = false;

				// 提交数据到account
				params.clear();// 清空数据
				params.add(account);
				params.add(passwd);
				params.add(1); // 表示学生
				params.add(1); // 提交成功后直接登陆跳转到主界面
				acs = accDao.create(params);

				// 创建学生信息
				params.clear();
				params.add(account);
				params.add(name);
				params.add(sex);
				params.add(major);
				params.add(grade);
				params.add(sclass);
				params.add(phone);
				params.add(date);
				scs = stdDao.create(params);

				if (!scs) {
					System.out.println("学生信息创建失败");
				} else if (!acs) {
					System.out.println("学生账号创建失败");
				} else if (scs && acs) {
					// 回传值
					out.print(Cons.SUCCESS); // 学生账号、信息创建成功
					System.out.println(Cons.SUCCESS);

					// session赋值
					setSession(session, account, name, major,
							DataUrl.DEF_IMG_URL);
					session.setAttribute("isSelect", "0");
					session.setAttribute("hasTea", "0");
					session.setAttribute("tno", "0");

					// 创建对应的文件夹
					String filepath = getServletContext().getRealPath(
							"/USERS/STUDENTS/" + account);
					// 创建该学生的文件夹，方便管理
					File folder = new File(filepath);
					if (folder.mkdirs()) {
						System.out.println("*****绝对*******"
								+ folder.getAbsolutePath());
						System.out.println("*****相对*******" + folder.getPath());
						System.out
								.println("**********学生" + account + "文件夹创建成功");
					} else {
						if (folder.exists()) {
							System.out
									.println("******学生" + account + "文件夹已经存在");
						} else {
							System.out.println("********学生" + account + "创建失败");
						}
					}
				}
			} else {
				System.out.println("***********学生注册失败**************");
				out.print(Cons.CREAT_FAILED); // 学生账号、信息创建失败
			}
		} else if ("2".equals(who)) {
			String title = request.getParameter("title");

			// 如果账号不存存在,则创建
			if (!accDao.isAccExist(account)) {
				System.out.println("***********导师注册**************");
				// 导师信息创建
				boolean sts = false;
				// 导师账户创建
				boolean ats = false;

				// 提交数据到account
				params.clear();// 清空数据
				params.add(account);
				params.add(passwd);
				params.add(2); // 表示导师
				params.add(1); // 提交成功后直接登陆跳转到主界面
				ats = accDao.create(params);

				// 创建导师信息
				params.clear();// 清空数据
				params.add(account);
				params.add(name);
				params.add(sex);
				params.add(major);
				params.add(title);
				params.add(phone);
				params.add(date);
				sts = teaDao.create(params);

				if (!sts) {
					System.out.println("导师信息创建失败");
				} else if (!ats) {
					System.out.println("导师账号创建失败");
				} else if (sts && ats) {
					// 回传值
					out.print(Cons.SUCCESS); // 导师账号、信息创建成功
					// session赋值
					setSession(session, account, name, major,
							DataUrl.DEF_IMG_URL);
					session.setAttribute("teaStdCount", "0");

					// 创建导师对应的文件夹
					String filepath = getServletContext().getRealPath(
							"/USERS/TEACHERS/" + account);
					// 创建该导师的所属文件夹，方便管理
					File folder = new File(filepath);
					if (folder.mkdirs()) {
						System.out.println("*****绝对*******"
								+ folder.getAbsolutePath());
						System.out.println("*****路径*******" + folder.getPath());
						System.out
								.println("**********导师" + account + "文件夹创建成功");
					} else {
						if (folder.exists()) {
							System.out
									.println("******导师" + account + "文件夹已经存在");
						} else {
							System.out.println("********导师" + account + "创建失败");
						}
					}
				}
			} else {
				System.out.println("***********导师注册失败**************");
				out.print(Cons.CREAT_FAILED); // 导师账号、信息创建失败
			}
			out.close();
		}
	}

	private void setSession(HttpSession session, String account, String name,
			String major, String imgUrl) {
		session.setAttribute("account", account);
		session.setAttribute("name", name);
		session.setAttribute("major", Translate.getMajor(major));
		session.setAttribute("imgUrl", imgUrl);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		accDao = new AccDaoImpl();
		stdDao = new StdDaoImpl();
		teaDao = new TeaDaoImpl();
	}

}
