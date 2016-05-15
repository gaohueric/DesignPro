package com.design.imau.view.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.design.imau.dao.StdDaoImpl;
import com.design.imau.dao.TeaDaoImpl;
import com.design.imau.utils.other.Cons;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class HeadUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StdDaoImpl std;
	private TeaDaoImpl tea;

	public HeadUpload() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("接收到请求");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		String who = request.getParameter("who");
		// 获得账号
		String account = session.getAttribute("account").toString();
		/*
		 * getServletContext().getRealPath("/") 获得工程的绝对路径
		 * D:\DesignProject\.metadata\.me_tcat\webapps\DesignProject
		 */

		String filepath = "";

		// 创建上传文件夹，绝对路径
		if ("1".equals(who)) {
			filepath = getServletContext().getRealPath(
					"/USERS/STUDENTS/" + account + "/images/");
		} else if ("2".equals(who)) {
			filepath = getServletContext().getRealPath(
					"/USERS/TEACHERS/" + account + "/images/");
		}

		File file = new File(filepath);
		// 如果路径不存在，则创建路径
		if (!file.exists()) {
			file.mkdirs();
		}
		// 创建smartupload
		SmartUpload su = new SmartUpload();
		// 初始化smartupload
		su.initialize(getServletConfig(), request, response);
		// 设置上传图片大小1024KB * 1024KB = 1MB 最大1MB
		su.setMaxFileSize(1024 * 1024);
		// 设置允许上传文件类型
		su.setAllowedFilesList("jpg,gif,png");

		// 上传文件
		try {
			su.upload();
			// 返回保存了多少个文件
			int save_count = su.save(filepath);

			System.out.println("**********上传成功了" + save_count
					+ "个文件***********");

			System.out.println("**********The path is: " + filepath
					+ "**********");
			System.out.println("**********su.getFiles().getCount():"
					+ su.getFiles().getCount());

			// 获得上传头像名称
			com.jspsmart.upload.File tempFile = su.getFiles().getFile(0);
			String headName = tempFile.getFileName();

			boolean flag = false;
			// 获得图片的相对路径,数据库中保存图片的相对路径,注意写法
			String path = "";
			// 如果上传成功，将信息保存到数据库中
			if ("1".equals(who)) {
				path = "./USERS/STUDENTS/" + account + "/images/";
				std = new StdDaoImpl();
				flag = std.updateHead(path, headName, account);
				if (flag) {
					// 更新session中的图片信息
					session.setAttribute("imgUrl", path + headName);
					System.out.println("*****STD_HEAD:"
							+ session.getAttribute("imgUrl"));
					response.sendRedirect("../stdPerInfo.html");
				}
			} else if ("2".equals(who)) {
				path = "./USERS/TEACHERS/" + account + "/images/";
				tea = new TeaDaoImpl();
				flag = tea.updateHead(path, headName, account);
				if(flag){					
					// 更新session中的图片信息
					session.setAttribute("imgUrl", path + headName);
					System.out.println("*****TEA_HEAD:"
							+ session.getAttribute("imgUrl"));
					response.sendRedirect("../teaPerInfo.html");
				}
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
	}
}
