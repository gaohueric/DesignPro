package com.design.imau.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	private FilterConfig config;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();

		String noFilter = config.getInitParameter("noFilter");
		String[] noFilterArray = noFilter.split(";");
		String charset = config.getInitParameter("charset");
		if ("".equals(charset) || charset == null) {
			charset = "UTF-8";
		}
		request.setCharacterEncoding(charset);

		if (noFilter != null) {
			for (int i = 0; i < noFilterArray.length; i++) {
				if ("".equals(noFilterArray[i]) || noFilterArray[i] == null)
					continue;
				if (request.getRequestURI().indexOf(noFilterArray[i]) != -1) {
					arg2.doFilter(arg0, arg1);
					return;
				}
			}
		}

		if (session.getAttribute("account") != null
				&& ("1").equals(session.getAttribute("status"))) {
			arg2.doFilter(arg0, arg1);
		} else {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		config = arg0;
	}

}
