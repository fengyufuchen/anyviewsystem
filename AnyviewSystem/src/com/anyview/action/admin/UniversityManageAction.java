package com.anyview.action.admin;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProvinceTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.service.function.CollegeManager;

@SuppressWarnings("serial")
@Namespace("/admin/universityManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

/**
 * 文件名：UniversityManagerAction.java
 * 描   述：学校管理控制器类
 * 时   间 ：2015年08月05日
 * @author WuLiu
 * @version 1.0
 */

public class  UniversityManageAction extends AdminBaseAction {

	public static final Integer PAGE_SIZE = 20;
	//封装jsp参数
	private UniversityTable univer = new UniversityTable();
	private String jsonStr;		
	private String tcRightStr;
	
	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType ;
	private String navTabId;
	private Timestamp createDateStart;
	private Timestamp createDateEnd;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CollegeManager collegeManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getUniversityManagerPage",results={@Result(name="success",location="/admin/universityManager/universityManager.jsp")})
	public String getUniversityManagerPage()throws Exception{
		/**
		 * 函数名：getUniversityrManagerPage
		 * 描   述：获取学校管理页面
		 */
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer userUnid = currentManager.getUniversity().getUnID();
		Integer MIden = currentManager.getMiden();
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		if(request.getParameter("createDateStart")!=null&&!request.getParameter("createDateStart").equals("")
				&&request.getParameter("createDateEnd")!=null&&!request.getParameter("createDateEnd").equals(""))
		{
			createDateStart=Timestamp.valueOf(request.getParameter("createDateStart"));
			createDateEnd=Timestamp.valueOf(request.getParameter("createDateEnd"));
		}
		
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		Map param = new HashMap();
		param.put("createDateStart", createDateStart);
		param.put("createDateEnd", createDateEnd);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("condition", univer);
		Pagination<UniversityTable> page = universityManager.getUniversityPage(param);
//		if(MIden != -1){//非超级管理员，学校管理员或学院管理员仅显示本校
//			List<UniversityTable> univers =page.getContent();
//			UniversityTable univer = null;
//			for (UniversityTable u : univers) 
//				if(userUnid.equals(u.getUnID())){
//					univer = u;
//					break;
//				}
//					
//			univers.clear();
//			univers.add(univer);
//			page.setContent(univers);
//			page.setTotalPageNum(Integer.valueOf(1));
//		}
		List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
		request.setAttribute("universities",univers);
		request.setAttribute("page", page);
		request.setAttribute("admin", currentManager);	
		request.setAttribute("condition", univer);
		return SUCCESS;
	}
	
	@Action(value="addUniversity", results={@Result(name="success",type="json")})
	public String addUniversity() throws Exception{
		
		/**
		 * 函数名：addUniversity
		 * 描   述：添加学校信息
		 */
		String unName=univer.getUnName();
		 if (universityManager.isUniversityExist(unName) == true){
			message = "该学校已存在，添加失败";
			statusCode = "300";
		}
		else if(universityManager.saveUniversity(univer)){
			message = "添加成功";
			statusCode = "200";
			navTabId = "universityManager";
		}
		else{
			message = "添加失败";
			statusCode = "300";
		}
		forwardUrl = "admin/universityManager/getUniversityManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	@Action(value="updateUniversity", results={@Result(name="success",type="json")})
	public String updateUniversity() throws Exception{
		/**
		 * 函数名：updateUniversity
		 * 描   述：修改学校信息
		 */
		Integer unId = Integer.valueOf(request.getParameter("unID"));
		univer.setUnID(unId);
		if(universityManager.updateUniversity(univer)){
			message = "修改成功";
			statusCode = "200";
			navTabId = "universityManager";
		}else{
				message = "修改失败";
				statusCode = "300";
		}
		forwardUrl = "admin/universityManager/getUniversityManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	@Action(value="deleteUniversity",results={@Result(name="success",type="json")})
	public String deleteUniversity() throws IOException
	{	
		/**
		 * 函数名：deleteUniversity
		 * 描   述：删除学校信息
		 */
		Integer unId = Integer.parseInt(request.getParameter("unID"));		
		try {
			universityManager.deleteUniversity(unId);
		    jsonStr = AjaxObject.newOk("成功删除!").setNavTabId("universityManager").toString();
		} catch(HibernateException e)
		{
			 jsonStr = AjaxObject.newError("删除失败！").toString();
		}
		forwardUrl = "admin/universityManager/getUniversityManagerPage.action";
		callbackType  = "closeCurrent";
		ResponseUtils.outJson(response, jsonStr);	
		return SUCCESS;
		
	}
	
	/**
	  * 函数名：updateUniversityAuthority
	  * 描   述：修改学校信息的管理员权限判断
	  * 超级管理员：可以修改学校信息
	  * 其他管理员：无权限修改学校信息
	 */
	@Action(value="authorityUpdateUniversity",
	results={@Result(name="success",location="/admin//universityManager/updateUniversity.jsp"),
			@Result(name="NoAuthority",location="/admin/universityManager/noAuthorityPage.jsp")})
	public String authorityUpdateUniversity()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);		
		if(currentManager.getMiden() == -1)
		{// 超级管理员
			Integer unId = Integer.valueOf(request.getParameter("unID"));
			UniversityTable univer = universityManager.gainUniversityByUnid(unId);
			request.setAttribute("university", univer);
			return SUCCESS;
		}
		else
		{// 其他管理员
			String errorMessage = " 权限不足，请使用超级管理员账户登录！";
			request.setAttribute("errorMessage", errorMessage);
			return "NoAuthority";
		}
	}
	
	/**
	  * 函数名：authorityAddUniversity
	  * 描   述：添加学校信息的管理员权限判断
	  * 超级管理员：可以添加学校信息
	  * 其他管理员：无权限添加学校信息
	 */
	@Action(value="authorityAddUniversity",
	results={@Result(name="SuperAdmin",location="/admin/universityManager/addUniversity.jsp"),
			@Result(name="NoAuthority",location="/admin/universityManager/noAuthorityPage.jsp")})
	public String authorityAddUniversity()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);		
		if(currentManager.getMiden() == -1)
		{// 超级管理员
//			List<ProvinceTable> pros = universityManager.searchProvinceByName("");
//			request.setAttribute("provinces", pros);
			return "SuperAdmin";
		}
		else
		{// 其他管理员	
			String errorMessage = " 权限不足，请使用超级管理员账户登录！";
			request.setAttribute("errorMessage", errorMessage);
			return "NoAuthority";
		}
	}
	
	/**
	  * 函数名：authorityDeleteUniversity
	  * 描   述：删除学校信息的管理员权限判断和有无下属学院判断
	  * 超级管理员：可以删除学校信息
	  * 其他管理员：无权限删除学校信息
	  * 有下属学院：无法删除该学校
	  * 无下属学院：可以删除该学校
	  */
	@Action(value="authorityDeleteUniversity",
	results={@Result(name="Delete",location="/admin/universityManager/affirmDeleteUniversity.jsp"),
			@Result(name="NoAuthority",location="/admin/universityManager/noAuthorityPage.jsp"),
			@Result(name="ExistColleges",location="/admin/universityManager/existCollege.jsp")})
	public String authorityDeleteUniversity()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer unId = Integer.valueOf(request.getParameter("unID"));
		Integer collegeCount = collegeManager.getCollegesByUnid(unId);
        if(currentManager.getMiden() == -1 && collegeCount <= 0)
		{// 超级管理员且无下属学院  
			UniversityTable univer = universityManager.gainUniversityByUnid(unId);
			request.setAttribute("univer", univer);
        	return "Delete";  		
		}
        else if(collegeCount >0)
        {//存在下属学院
        	return "ExistColleges";
        }
		else
		{// 其他管理员
			String errorMessage = " 权限不足，请使用超级管理员账户登录！";
			request.setAttribute("errorMessage", errorMessage);
			return "NoAuthority";
		}
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

	public UniversityTable getUniver() {
		return univer;
	}

	public void setUniver(UniversityTable unvier) {
		this.univer = univer;
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
