package org.zttc.itat.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.zttc.itat.model.SystemContext;
import org.zttc.itat.model.User;

public class SystemContextFilter implements Filter {
	private int pageSize;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			int tps = pageSize;
			try {
				tps = Integer.parseInt(req.getParameter("pageSize"));
			} catch (NumberFormatException e) {}
			int pageOffset = 0;
			try {
				pageOffset = Integer.parseInt(req.getParameter("pager.offset"));
			} catch (NumberFormatException e) {}
			HttpServletRequest hreq = (HttpServletRequest)req;
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(tps);
			String realPath = hreq.getSession().getServletContext().getRealPath("");
			realPath = "D:\\teach_source\\class2\\j2EE\\document_project\\document01\\WebContent";
			SystemContext.setRealPath(realPath);
			User loginUser = (User)hreq.getSession().getAttribute("loginUser");
			if(loginUser!=null) SystemContext.setLoginUser(loginUser);
			chain.doFilter(req, resp);
		} finally {
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
			SystemContext.removeLoginUser();
			SystemContext.removeRealPath();
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
	}

}
