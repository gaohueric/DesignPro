package com.design.imau.view.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.design.imau.dao.DesDaoImpl;
import com.design.imau.utils.other.Cons;

public class TeaSetDeses extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//如果session == null, 则说明超时
		if (session == null) {
			out.print(Cons.OUT_OF_TIME);
			return;
		}
		
		String dtitle = request.getParameter("dtitle");
		String ntype = request.getParameter("ntype");
		if (dtitle != null && !dtitle.isEmpty()) {
			getDesID(dtitle, out);
		} else {
			if (ntype != null && "2".equals(ntype)) {
				String taccount = session.getAttribute("account").toString();
				System.out
						.println("**************************TeaSetDeses*****ntype != null");
				String dataStr = request.getParameter("dataStr");
				if (dataStr != null) {
					System.out.println("**************************TeaSetDeses:"
							+ dataStr);
					JSONArray jsondata = JSONArray.fromObject(dataStr);

					boolean flag = true;
					String[] dname = new String[jsondata.size()];
					// 将要更新的数据更新到数据库，并且将名称存入dname数组
					DesDaoImpl ddi = new DesDaoImpl();
					for (int i = 0; i < jsondata.size(); i++) {

						@SuppressWarnings("unchecked")
						Map<String, Object> data = (Map<String, Object>) jsondata
								.get(i);
						System.out.println(data.toString());
						dname[i] = data.get("title").toString();
						flag = flag && ddi.updateDes(taccount, data);
					}
					//如果全部更新成功,返回对应的id和毕业设计题目
					if (flag == true) {
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < dname.length; i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							int did = ddi.getDesID(dname[i]);
							map.put("did", did);
							map.put("dname", dname[i]);
							list.add(map);
						}
						System.out.println("****list:" + list);
						JSONArray json = JSONArray.fromObject(list);
						System.out.println("****jsonArray: " + json.toString());
						out.print(json.toString());
					} else {
						System.out.println("****false");
						out.print(Cons.UPDATE_FAILED);
					}
				}
			} else {
				System.out.println("***************false***************");
				out.print(Cons.REQUEST_FAILED_PARAMS_ERROR);
			}
		}
		out.close();
	}

	private void getDesID(String dtitle, PrintWriter out) {
		DesDaoImpl ddi = new DesDaoImpl();
		if (ddi.getDesID(dtitle) > 0) {
			out.print(Cons.DES_ID_ISEXIST);
		} else {
			out.print(Cons.DES_ID_NOT_EXIST);
		}
	}

	public void init() throws ServletException {
	}

}
