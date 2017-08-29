package com.anyview.action.admin;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.Pagination;
import com.anyview.entities.SemesterTable;
import com.anyview.service.function.SemesterManager;

@SuppressWarnings("serial")
@Namespace("/admin/semesterManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

/**
 * 文件名：SemesterManagerAction.java
 * 描   述：学期管理控制器类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

public class SemesterManagerAction extends CommonAction {

	public static final Integer PAGE_SIZE = 20;
	
	private SemesterTable sem;
	private String jsonStr;		
	private String tcRightStr;
	
	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType ;
	private String navTabId;
	
	@Autowired
	private SemesterManager semesterManager;
	
	//@SuppressWarnings("rawtypes")
	//private List rows = new ArrayList();//存放内容
	//private int total;//rows的行数
	
	/**
	 * 函数名：getSemesterManagerPage
	 * 描   述：获取学期管理页面
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getSemesterManagerPage",results={@Result(name="success",location="/admin/semesterManager/semesterManager.jsp")})
	public String getSemesterManagerPage()throws Exception{
//		rows = semesterManager.getSemesters();
//		total = rows.size();
//		request.setAttribute("rows", rows);
//		request.setAttribute("total", total);
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		Pagination<SemesterTable> page=new Pagination<SemesterTable>();
		page = semesterManager.getSemesterPage(param);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	/**
	 * 函数名：addSemester
	 * 描   述：添加学期信息
	 */
	@Action(value="addSemester", results={@Result(name="success",type="json")})
	public String addSemester() throws Exception{
		String sname = request.getParameter("sname");
		Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
		Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
		if(startTime.getTime() > endTime.getTime()){
			message = "开始时间不能大于结束时间";
			statusCode = "300";
		}
		else if (semesterManager.isSemesterexist(sname) == true){
			message = "该学期已存在，添加失败";
			statusCode = "300";
		}
		else if (semesterManager.saveSemester(sname,startTime, endTime)){
			message = "添加成功";
			statusCode = "200";
			navTabId = "semesterManager";
		}
		else{
			message = "添加失败";
			statusCode = "300";
		}
		forwardUrl = "admin/semesterManager/getSemesterManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	/**
	 * 函数名：updateSemester
	 * 描   述：修改学期信息
	 */
	@Action(value="updateSemester", results={@Result(name="success",type="json")})
	public String updateSemester() throws Exception{
		Integer sid = Integer.valueOf(request.getParameter("sid"));
		Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime"));
		Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime"));
		if(sid != 0 && startTime != null && endTime != null && startTime.getTime() < endTime.getTime()){
			if(semesterManager.updateSemester(sid, startTime, endTime)){
				message = "更新成功";
				statusCode = "200";
				navTabId = "semesterManager";
			}else{
				message = "更新失败";
				statusCode = "300";
			}
		}else{
			message = "更新失败，注意开始时间不能大于结束时间,请填写好相关信息再提交";
			statusCode = "300";
		}
		forwardUrl = "admin/semesterManager/getSemesterManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	
	/**
	 * 函数名：deleteSemester
	 * 描   述：删除学期信息
	 */
	@Action(value="deleteSemester",results={@Result(name="success",type="json")})
	public String deleteSemester() throws Exception{
		Integer sid = Integer.valueOf(request.getParameter("sid"));
		if(!semesterManager.deleteSemester(sid)){
			statusCode = "200";
			message = "删除成功";
			forwardUrl = "admin/semesterManager/getSemesterManagerPage.action";
			callbackType  = "forward";
			return SUCCESS;
		}
		//jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("semesterManager/getSemesterManagerPage.action").toString();
		//ResponseUtils.outJson(response, jsonStr);
		statusCode = "300";
		message = "删除失败";
		return SUCCESS;

	}
	
	
/**DWZ相关参数获取方法**/
	
	public String getMessage() {
		return message;
	}

	public String getStatusCode() {
		return statusCode;
	}
	
	public String getTcRightStr() {
		return tcRightStr;
	}

	public void setTcRightStr(String tcRightStr) {
		this.tcRightStr = tcRightStr;
	}

	public SemesterTable getSem() {
		return sem;
	}

	public void setSem(SemesterTable sem) {
		this.sem = sem;
	}
	
	public String getForwardUrl() {
		return forwardUrl;
	}
	
	public void setForwardUrl(String forwardUrl) {  
        this.forwardUrl = forwardUrl;  
    }
	
	public String getCallbackType() {  
        return callbackType;  
    }  
  
    public void setCallbackType(String callbackType) {  
        this.callbackType = callbackType;  
    }
    
    public String getNavTabId() {
		return navTabId;
	}
    
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
}
