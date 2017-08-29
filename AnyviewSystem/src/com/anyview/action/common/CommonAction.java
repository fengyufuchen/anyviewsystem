package com.anyview.action.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 
* @ClassName: CommonAction
* @Description: 所有action的基类，负责封装数据
* @author cjj
* @date 2012-10-20 下午08:42:23
*
 */
@SuppressWarnings("serial")
public class CommonAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware, ServletContextAware
{
	//排序字段
	protected String orderField;
	//排序方向
	protected String orderDirection;
	
	protected Map<String, Object> session;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected ServletContext servletcontext;
	
	protected Map<String,Object> outputData = new HashMap<String,Object>();
	
	public void setSession(Map<String, Object> arg0)
	{
		this.session = arg0;
	}
	
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletcontext = arg0;
	}
	
	
	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	//可以修改传到页面数据的JSON参数名，出于安全性虑
	@JSON(name="Data")
	public Map<String,Object> getOutputData(){
		return outputData;
	}
	
	/**
	 * 获取页面参数方法，包括
	 * pageNum numPerPage orderField orderDirection
	 * 
	 * @return 返回已经封装的map
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月19日 下午3:39:58
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map gPageParams(HttpServletRequest request, int pageSize, String defaultOrderF){
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? pageSize : Integer.valueOf(numPerPageStr);
		orderField = (orderField==null || "".equals(orderField))?defaultOrderF:orderField;	//默认按班级排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;	//默认升序
		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		return param;
	}

	@Override
	public void addActionError(String anErrorMessage) {
		String s=anErrorMessage;
		System.out.println(s);
		super.addActionError(anErrorMessage);
	}

	@Override
	public void addActionMessage(String aMessage) {
		String s=aMessage;
		System.out.println(s);
		super.addActionMessage(aMessage);
	}

	@Override
	public void addFieldError(String fieldName, String errorMessage) {
		String s=errorMessage;
	    String f=fieldName;
	    System.out.println(s);
	    System.out.println(f);
		super.addFieldError(fieldName, errorMessage);
	}
	
	
	
}	
