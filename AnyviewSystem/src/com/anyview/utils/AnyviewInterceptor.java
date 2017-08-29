/**   
* @Title: AnyviewInterceptor.java
* @Package com.stusys.utils
* @Description: TODO
* @author xhn 
* @date 2012-11-30 下午10:31:08
* @version V1.0   
*/
package com.anyview.utils;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @ClassName: AnyviewInterceptor
 * @Description: TODO
 * @author xhn
 * @date 2012-11-30 下午10:31:08
 * 
 */
public class AnyviewInterceptor extends AbstractInterceptor {

	/* (no  Javadoc)
	 * <p>Title: intercept</p>
	 * <p>Description: </p>
	 * @param arg0
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		
		//判断是否是登陆页?
		Map map = arg0.getInvocationContext().getSession();
		if(null == map.get("anyviewUser")){
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
