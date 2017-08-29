/**   
* @Title: AuthorityFilter.java 
* @Package com.anyview.utils.init 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年10月23日 上午1:35:00 
* @version V1.0   
*/
package com.anyview.utils.init;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 
 * @ClassName: AuthorityFilter 
 * @Description: TODO(设置request的字符集为utf8,验证用户是否登陆) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年10月23日 上午1:35:00 
 *  
 */
@WebFilter(filterName="authorityFilter",
		urlPatterns={"/*"},
		initParams={
		@WebInitParam(name="js", value=".js"),
		@WebInitParam(name="ajax", value="Ajax.action"),
		@WebInitParam(name="encoding", value="UTF-8"),
		@WebInitParam(name="loginPage", value="/login.jsp"),
		@WebInitParam(name="superAdminLoginPage", value="/superAdminLogin.jsp")})
public class AuthorityFilter implements Filter{
	
	private FilterConfig config;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		this.config = null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String js = config.getInitParameter("js");
		String encoding = config.getInitParameter("encoding");
		String loginPage = config.getInitParameter("loginPage");
		String superAdminLoginPage = config.getInitParameter("superAdminLoginPage");
		request.setCharacterEncoding(encoding);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(true);
		//获取客户请求页面
		String requestPath = req.getServletPath();
		if(session.getAttribute("User")==null && !requestPath.endsWith(superAdminLoginPage) && !requestPath.endsWith(loginPage) && !requestPath.endsWith(js)){
			request.setAttribute("tip", "请登录!");
			request.getRequestDispatcher(loginPage).forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.config = arg0;
	}

}
