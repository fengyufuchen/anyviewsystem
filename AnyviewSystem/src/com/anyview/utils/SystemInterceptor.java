/**   
* @Title: SystemInterceptor.java
* @Package com.stusys.utils
* @Description: TODO
* @author xhn 
* @date 2012-10-25 下午08:25:19
* @version V1.0   
*/
package com.anyview.utils;

import java.util.Map;

import com.anyview.action.common.LoginUserAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @ClassName: SystemInterceptor
 * @Description: 系统拦截器，拦截没登录用户和系统异常处理
 * @author xhn
 * @date 2012-10-25 下午08:25:19
 * 
 */
@SuppressWarnings("serial")
public class SystemInterceptor extends AbstractInterceptor {

	/* (no  Javadoc)
	 * <p>Title: intercept</p>
	 * <p>Description: </p>
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		System.out.println("--------------------->SystemInterceptor<-------------------");
		//判断是否是登陆页?
		if(LoginUserAction.class == arg0.getAction().getClass()){
			return arg0.invoke();
		}
		
		Map map = arg0.getInvocationContext().getSession();
		
		if(null == map.get("User")){
			return "permission";
		}
		String result = ""; 
		try{
			result = arg0.invoke();
		}catch(Exception e) {
			e.printStackTrace();
			ActionSupport as=(ActionSupport)arg0.getAction();
			as.addActionError("系统数据异常!");
			result = "error";
	    }
		return result;
	}

}
