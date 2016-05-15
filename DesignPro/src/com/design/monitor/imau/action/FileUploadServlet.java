package com.design.monitor.imau.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
 * 功能：导师文件上传
 * 
 * @author WenC
 * 
 */
public class FileUploadServlet extends HttpServlet {
	private static final String TAG = FileUploadServlet.class.getSimpleName();
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

		// 1：上传学生论文，2：上传资料
		String type = request.getParameter("type");

		// 获得上传的参数
		String who = ""; // teacher? or student?
		if ("1".equals(type)) {
			who = "STUDENTS";
		} else if ("2".equals(type)) {
			who = "TEACHERS";
		}
		String account = (String) session.getAttribute("account"); // 上传账户的文件夹名
		String folder = ""; // 获得上传的文件夹名
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
		uploadFile.setHeaderEncoding("UTF-8"); // 设置文件编码，防止名字出现部分乱码

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
						log.debug(TAG, "导师", "上传文件");

						// 判断后缀是否符合要求
						String fullName = item.getName();

						// 确定上传路径
						String filePath = "USERS/" + who + "/";
						if ("1".equals(type)) {
							String fileName = item.getFieldName();
							filePath += fileName + "/" + folder;
						} else if ("2".equals(type)) {
							filePath += account + "/" + folder;
						}

						// 如果后缀正确则可以上传
						if (canUpload(fullName)) {

							String postfix = fullName.split("\\.")[1];
							Date time = new Date();
							long name = time.getTime();

							// 上传到对应的文件夹
							File file = new File(this.getServletContext()
									.getRealPath(filePath), name + "."
									+ postfix);

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

							if ("1".equals(type)) {
								FileUpLoadBiz fub = new FileUpLoadBiz();
								boolean flag = fub.stdUpdateInfo(
										item.getFieldName(), account, fullName,
										filePath, 2);

								if (flag) {
									log.debug(TAG, "doPost", "上传文件写入数据库成功");
									PushMessage msg = new PushMessage();
									msg.setTitle("导师操作提醒");
									msg.setDescription("您的导师上传了你的毕业设计论文，论文名："
											+ fullName + "，请下载查看");
									fub.teaUplocationNotifi(
											item.getFieldName(), msg);
								} else {
									log.debug(TAG, "doPost", "上传文件写入数据库失败");
								}
							}
							log.debug(TAG, "doPost", "上传成功");
							out.print(Cons.FILE_UPLOAD_SUCCESS);

						} else {
							log.debug(TAG, "doPost", "文件上传格式doc,docx");
							out.print(Cons.FILE_UPLOAD_FAILED_FORMAT_ERROR);

						}
					}
				}
				response.sendRedirect("teaFileConfig.html");
			} catch (FileUploadException e) {
				out.print(Cons.FILE_UPLOAD_EXCEPTION);
				log.debug(TAG, "doPost", "FileUploadException异常了");
				e.printStackTrace();
				response.sendRedirect("teaFileConfig.html");
			}

		} else {
			log.debug(TAG, "doPost", "content 不是 multipart/form-data");
			out.print(Cons.FILE_UPLOAD_FAILED_CONTENT_ERROR);
			response.sendRedirect("teaFileConfig.html");
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
