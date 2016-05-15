package com.design.monitor.imau.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.design.imau.utils.other.Cons;
import com.design.imau.utils.other.MDLog;
import com.design.imau.utils.other.PushMessage;
import com.design.monitor.imau.biz.FileUpLoadBiz;

/**
 * 功能：学生上传文件
 * 
 * @author WenC
 * 
 */
public class StdFileUpLoadServlet extends HttpServlet {
	private static final String TAG = StdFileUpLoadServlet.class
			.getSimpleName();
	private static MDLog log = MDLog.getLog();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug(TAG, "doPost", "获得请求");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		// 如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}

		// type ： 1 表示上传毕业设计， 2 : 表示上传相关文件
		String type = request.getParameter("type");

		// 获得上传的参数
		String who = "STUDENTS"; // teacher? or student?
		String account = (String) session.getAttribute("account"); // 上传账户的文件夹名
		String folder = "";
		if ("1".equals(type)) {
			folder = "THESIS"; // 获得上传的文件夹名
		} else {
			folder = "FILES";
		}

		// 准备上传
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploadFile = new ServletFileUpload(factory);

		uploadFile.setFileSizeMax(1024 * 1024 * 15); // 单个文件最多15M
		uploadFile.setSizeMax(1024 * 1024 * 30); // 一次上传最多30M
		uploadFile.setHeaderEncoding("UTF-8");

		boolean isMulti = ServletFileUpload.isMultipartContent(request);
		if (isMulti) { // 如果是multipart/form-data

			try {
				List<?> files = uploadFile.parseRequest(request);
				List<FileItem> items = new ArrayList<FileItem>();
				log.debug(TAG, "files length", "" + files.size());
				// 转化类型
				for (int i = 0; i < files.size(); i++) {
					items.add((FileItem) files.get(i));
				}
				// 开始上传文件
				for (FileItem item : items) {

					if (item.isFormField()) { // 如果提交的是表单里的内容

					} else { // 如果提交的是文本内容

						// 判断后缀是否符合要求
						String fullName = item.getName();
						// 如果后缀正确则可以上传
						if (canUpload(fullName)) {
							// 上传到对应的文件夹
							File file = new File(this.getServletContext()
									.getRealPath(
											"USERS/" + who + "/" + account
													+ "/" + folder), fullName);
							String filePath = "USERS/" + who + "/" + account
									+ "/" + folder + "/" + fullName;

							log.debug(TAG, "路径", filePath);

							file.getParentFile().mkdirs(); // 创建全部父级文件夹
							file.createNewFile(); // 创建文件夹

							InputStream is = item.getInputStream();
							OutputStream os = new FileOutputStream(file);

							int len = 0;
							byte[] buffer = new byte[1024];
							while ((len = is.read(buffer)) > -1) {
								os.write(buffer, 0, len);
							}
							// 关闭输入输出
							os.close();
							is.close();
							log.debug(TAG, "doPost", "上传成功");

							// 只有上传论文的时候才更新数据库
							if ("1".equals(type)) {
								// 上传成功更新数据库
								FileUpLoadBiz fub = new FileUpLoadBiz();
								boolean flag = fub.stdUpdateInfo(account, null,
										fullName, filePath, 1);
								if (flag) {
									log.debug(TAG, "doPost", "上传文件写入数据库成功");
									PushMessage msg = new PushMessage();
									msg.setTitle("学生操作提醒");
									msg.setDescription("您的学生上传了他的毕业设计论文，论文名："
											+ fullName + "，请下载查看");
									fub.stdUplocationNotifi(account, msg);
								} else {
									log.debug(TAG, "doPost", "上传文件写入数据库失败");
								}
							}
							out.print(Cons.FILE_UPLOAD_SUCCESS);
						} else {
							log.debug(TAG, "doPost", "上传异常，文件可上传格式为：doc,docx");
							out.print(Cons.FILE_UPLOAD_FAILED_FORMAT_ERROR);
						}
					}
				}
				response.sendRedirect("stdFileConfig.html");
			} catch (FileUploadException e) {
				out.print(Cons.FILE_UPLOAD_EXCEPTION);
				log.debug(TAG, "doPost", "FileUploadException异常了");
				e.printStackTrace();
				response.sendRedirect("stdFileConfig.html");
			}

		} else {
			log.debug(TAG, "doPost", "content 不是 multipart/form-data");
			out.print(Cons.FILE_UPLOAD_FAILED_CONTENT_ERROR);
			// 刷新页面
			response.sendRedirect("stdFileConfig.html");
		}
	}

	/**
	 * 判断是否可以上传
	 * 
	 * @param fullName
	 * @param fileName
	 * @return
	 */
	private boolean canUpload(String fullName) {
		log.debug(TAG, "canUpload", fullName);
		// 判断后缀
		String[] last = fullName.split("doc");
		if (last.length > 0) {
			return true;
		}
		last = fullName.split("docx");
		if (last.length > 0) {
			return true;
		}
		return false;
	}
}
