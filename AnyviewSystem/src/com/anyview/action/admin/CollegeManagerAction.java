package com.anyview.action.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.ErrorManager;

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
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CollegeManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.service.function.UniversityManager;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.CourseManager;

@SuppressWarnings("serial")
@Namespace("/admin/collegeManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

/**
 * 文件名：CollegeManagerAction.java
 * 描   述：学院管理控制器类
 * 时   间 ：2015年09月11日
 * @author WuLiu
 * @version 1.0
 */

public class  CollegeManagerAction extends AdminBaseAction {

	public static final Integer PAGE_SIZE = 20;
	private static final String FAIL = null;
	//封装jsp参数
	private CollegeTable college = new CollegeTable();
	private UniversityTable university = new UniversityTable();
	private String jsonStr;		
	private String tcRightStr;
	
	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType ;
	private String navTabId;
	
	private static Integer unId = -2;  //数据库中学校编号从-1开始，大于-2
	
	@Autowired
	private CollegeManager collegeManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private CourseManager courseManager;
	
	private CollegeTable condition = new CollegeTable();//对页面查询条件进行封装
	
	private Timestamp createDateStart;
	private Timestamp createDateEnd;
	private Timestamp updateDateStart; 
	private Timestamp updateDateEnd; 
	
	/**
	 * 
	 * @Description： 获取学院管理页面
	 * @return：代表处理结果的字符串
	 * @throws Exception：
	 * @author：刘武 
	 * @date：2015年9月11日 下午7:43:26
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getCollegeManagerPage",results={
	       @Result(name="superCollegeManager",location="/admin/collegeManager/superCollegeManager.jsp"),
	       @Result(name="universityCollegeManager",location="/admin/collegeManager/ universityCollegeManager.jsp")})
	public String getCollegeManagerPage()throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		Map param = new HashMap();
		param.put("admin", admin);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("condition", condition);
		param.put("createDateStart", createDateStart);
		param.put("createDateEnd", createDateEnd);
		param.put("updateDateStart", updateDateStart);
		param.put("updateDateEnd", updateDateEnd);
		param.put("unID", unId);
		Pagination<CollegeTable> page=new Pagination<CollegeTable>();
		if(admin.getMiden()==1){
			Integer unID = admin.getUniversity().getUnID();
			List<Object[]> colleges = collegeManager.getEnabledCollegesByUnId(unID);
			request.setAttribute("colleges", colleges);
		}else if(admin.getMiden()==-1){
			List<UniversityTable> universities = universityManager.gainAllUniversities();
			request.setAttribute("university", universities);
			if(condition.getUniversity()!=null){
				List<Object[]> colleges = collegeManager.getEnabledCollegesByUnId(condition.getUniversity().getUnID());
				request.setAttribute("colleges", colleges);
			}
		}
		page = collegeManager.getCollegePage(param);
		request.setAttribute("page", page);
		request.setAttribute("condition", condition);
		request.setAttribute("unID", unId);
		request.setAttribute("createDateStart", createDateStart);
		request.setAttribute("createDateEnd", createDateEnd);
		request.setAttribute("updateDateStart", updateDateStart);
		request.setAttribute("updateDateEnd", updateDateEnd);
		return admin.getMiden()==-1?"superCollegeManager":"universityCollegeManager";
	}
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="preAddCollege",results={@Result(name="success", location="/admin/collegeManager/addCollege.jsp")})
	public String preAddCollege()throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
        if(admin.getMiden()==-1){
        	List<UniversityTable> universities =  universityManager.gainAllUniversities();
    		request.setAttribute("universities", universities);
        }		    
//		String unName = university.getUnName();
		request.setAttribute("admin", admin);
		return SUCCESS;
	}
	

	@Action(value="addCollege", results={@Result(name="success",type="json")})
	public String addCollege() throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
		UniversityTable university = new UniversityTable();
		String ceName=college.getCeName();
//		univer = universityManager.gainUniversityByUnid(unId);
//		String unName = university.getUnName();
		Integer unID = new Integer(-2);
		if(admin.getMiden()==-1){
			 String tempUnID = request.getParameter("unID");
			 if(!tempUnID.isEmpty()){
				   unID = Integer.valueOf(request.getParameter("unID"));
				   university = universityManager.gainUniversityByUnid(unID);
			 }
			 
		}else {
			 unID = admin.getUniversity().getUnID();
			university = universityManager.gainUniversityByUnid(unID);
		}
		  Timestamp time = new Timestamp(System.currentTimeMillis());
		  college.setCreateTime(time);
		  college.setUpdateTime(time);
		  college.setUniversity(university);

		if(unID==-2){
			message = "请选择学校";
			statusCode = "300";
		}
		else if(collegeManager.isCollegeExist(unID,college.getCeName())==true){
			message = "该学院已存在，添加失败";
			statusCode = "300";
		}
		else if(collegeManager.addCollege(college)){
			message = "添加成功";
			statusCode = "200";
			navTabId = "collegeManager";
		}
		else{
			message = "添加失败";
			statusCode = "300";
		}
		forwardUrl = "admin/collegeManager/getCollegeManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description： 在跳转到修改学院页面之前进行预处理
	 * @return 代表处理结果的字符串
	 * @throws Exception：
	 * @author：刘武 
	 * @date：2015年9月13日 下午9:00:27
	 */
	@Action(value="preUpdateCollege",results={@Result(name="success", location="/admin/collegeManager/updateCollege.jsp")})
	public String preUpdateCollege()throws Exception{
		Integer ceId = Integer.valueOf(request.getParameter("ceID"));
		CollegeTable college = collegeManager.gainCollegeByCeid(ceId);
		request.setAttribute("college",college);
		UniversityTable university = collegeManager.gainCollegeByCeid(ceId).getUniversity(); 
		String unName = university.getUnName();
		request.setAttribute("unName", unName);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description： 修改学院信息
	 * @return 代表结果的字符串
	 * @throws Exception：
	 * @author：刘武 
	 * @date：2015年9月13日 下午8:38:06
	 */
	@Action(value="updateCollege", results={@Result(name="success",type="json")})
	public String updateCollege() throws Exception{
		Integer ceId = Integer.valueOf(request.getParameter("ceID"));
//		CollegeTable college = new CollegeTable();
		college.setCreateTime(collegeManager.gainCollegeByCeid(ceId).getCreateTime());
		college.setCeID(ceId);
		UniversityTable university = collegeManager.gainCollegeByCeid(ceId).getUniversity();
		college.setUniversity(university);
		if(collegeManager.updateCollege(college)){
			message = "修改成功";
			statusCode = "200";
			navTabId = "collegeManager";
		}else{
				message = "修改失败";
				statusCode = "300";
		}
		forwardUrl = "admin/collegeManager/getCollegeManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description： 在删除学院之前进行预处理
	 * @return 1.所删除学院不存在任何的教师，班级和课程，删除该学院信息
	 *                 2.所删除学院的教师，班级或者课程不全为空，就不能删除该学院
	 * @throws Exception：
	 * @author：刘武 
	 * @date：2015年9月14日 上午9:51:00
	 */
	@Action(value="preDeleteCollege",
			results={@Result(name="Delete",location="/admin/collegeManager/affirmDeleteCollege.jsp"),
					@Result(name="NotDelete",location="/admin/collegeManager/errorMessagePage.jsp")})
			public String preDeleteCollege()throws Exception{
		        Integer ceId = Integer.valueOf(request.getParameter("ceID"));
		        Integer classCount = classManager.gainClassCountByCeid(ceId);
		        Integer teacherCount = teacherManager.gainTeacherCountByCeid(ceId);
		        Integer courseCount = courseManager.gainCourseCountByCeid(ceId);
		        if(classCount == 0 && teacherCount == 0 && courseCount == 0)
				{//该学院不存在任何教师，班级和课程
//		            UniversityTable univer = universityManager.gainUniversityByUnid(unId);
			    
		        	UniversityTable university = collegeManager.gainCollegeByCeid(ceId).getUniversity();
		        	String unName = university.getUnName();
				    request.setAttribute("unName", unName);
					CollegeTable college = collegeManager.gainCollegeByCeid(ceId);
					request.setAttribute("college", college);
		        	return "Delete";  		
				}
				else
				{//该学院存在教师，班级或者课程
					String errorMessage = " 删除失败，该学院存在教师，班级或者课程！";
					request.setAttribute("errorMessage", errorMessage);
					return "NotDelete";
				}
			}	
	
	/**
	 * 
	 * @Description： 删除学院信息
	 * @return  success字符串
	 * @throws IOException：
	 * @author：刘武 
	 * @date：2015年9月14日 上午10:36:23
	 */
	@Action(value="deleteCollege",results={@Result(name="success",type="json")})
	public String deleteCollege() throws IOException
	{	
		Integer ceId = Integer.parseInt(request.getParameter("ceID"));		
		try {
			collegeManager.deleteCollege(ceId);
		    jsonStr = AjaxObject.newOk("成功删除!").setNavTabId("collegeManager").toString();
		} catch(HibernateException e)
		{
			 jsonStr = AjaxObject.newError("删除失败！").toString();
		}
		forwardUrl = "admin/collegeManager/getCollegeManagerPage.action";
		callbackType  = "closeCurrent";
		ResponseUtils.outJson(response, jsonStr);	
		return SUCCESS;	
	}
    
	/**
	 * 
	 * @Description： 根据权限规则判断是否有学院管理的相应的权限
	 * @return     字符串SUCCESS代表登陆者为超级管理员，具有全部的权限; 或者该学校的学校管理员，具有该学校的学院管理的所有权限
	 *                     字符串NoAuthority代表登陆者为学院管理员，不具有权限；或者其他学校的学校管理员，同样不具有该学校的学院管理权限
	 * @throws Exception：
	 * @author：刘武 
	 * @date：2015年9月15日 下午12:28:06
	 */
	@Action(value="authorityCollegeManager",
	results={@Result(name="success", location="/admin/collegeManager/collegeManager.jsp"),
				@Result(name="NoAuthority",location="/admin/collegeManager/errorMessagePage.jsp")})
	public String authorityCollegeManager()throws Exception{
		        String errorMessage = null;
		       //获取登陆者信息
				ManagerTable currentManager = (ManagerTable) session.get("User");
				//获取已选择学校的学校编号unID
				Integer choosedUnId = Integer.valueOf(request.getParameter("unID"));
				//获取登陆者所属学校的学校编号unID
				Integer userUnid = currentManager.getUniversity().getUnID();
		        if((currentManager.getMiden() == -1) || (choosedUnId.equals(userUnid)))
				{// 超级管理员或者选择学校编号和使用者的学校编号一致
		        	String pageNumStr = request.getParameter("pageNum");
		    		String numPerPageStr = request.getParameter("numPerPage");
		    		unId = choosedUnId;//unId未初始化
		    		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		    		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		    		Map param = new HashMap();
		    		param.put("pageSize", numPerPage);
		    		param.put("pageNum", pageNum);
		    		param.put("unID", unId);
		    		Pagination<CollegeTable> page=new Pagination<CollegeTable>();
		    		page = collegeManager.getCollegePage(param);
		    		request.setAttribute("page", page);
		    		request.setAttribute("unID", unId);
		   		  return SUCCESS;
				}
		        else if(currentManager.getMid() == 0){
		        	//学院管理员
					 errorMessage = "操作失败，学院管理员无此权限！";				
		        }
				else
				{// 其他学校的管理员				
					 errorMessage = " 操作失败，你不属于选中学校的管理员";
				}		  
		        request.setAttribute("errorMessage", errorMessage);
		        return "NoAuthority";
		}
	



/**DWZ相关参数获取方法**/
	
	public CollegeTable getCollege() {
		return college;
	}

	public void setCollege(CollegeTable college) {
		this.college = college;
	}
	
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




	public CollegeTable getCondition() {
		return condition;
	}




	public void setCondition(CollegeTable condition) {
		this.condition = condition;
	}




	public Timestamp getCreateDateStart() {
		return createDateStart;
	}




	public void setCreateDateStart(Timestamp createDateStart) {
		this.createDateStart = createDateStart;
	}




	public Timestamp getCreateDateEnd() {
		return createDateEnd;
	}




	public void setCreateDateEnd(Timestamp createDateEnd) {
		this.createDateEnd = createDateEnd;
	}




	public Timestamp getUpdateDateStart() {
		return updateDateStart;
	}




	public void setUpdateDateStart(Timestamp updateDateStart) {
		this.updateDateStart = updateDateStart;
	}




	public Timestamp getUpdateDateEnd() {
		return updateDateEnd;
	}




	public void setUpdateDateEnd(Timestamp updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}




	public UniversityTable getUniversity() {
		return university;
	}




	public void setUniversity(UniversityTable university) {
		this.university = university;
	}




}
