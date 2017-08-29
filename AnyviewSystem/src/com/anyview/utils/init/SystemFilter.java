package com.anyview.utils.init;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
/**
 * 
* @ClassName: SystemFilter
* @Description: 系统初始化加载信息
* @author xhn
* @date 2012-10-20 下午09:02:16
*
 */
public class SystemFilter extends StrutsPrepareAndExecuteFilter {
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		super.init(arg0);
	}
	
	/* (no  Javadoc)
	* <p>Title: doFilter</p>
	* <p>Description: </p>
	* @param arg0
	* @param arg1
	* @param arg2
	* @throws IOException
	* @throws ServletException
	* @see org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	*/
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		super.doFilter(arg0, arg1, arg2);
	}
}
