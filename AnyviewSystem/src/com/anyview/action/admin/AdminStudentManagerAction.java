package com.anyview.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminStudentManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;

/**
 * @Description 学生管理控制器类
 * @author DenyunFang
 * @time 2015年8月29日
 * @version 1.0
 */

@SuppressWarnings("serial")
@Namespace("/admin/adminstudentManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

public class AdminStudentManagerAction extends CommonAction{
	public static final Integer PAGE_SIZE = 20;
	
	private StudentTable stu = new StudentTable();
	private ClassStudentTable cs = new ClassStudentTable();
	

	@Autowired
	private UniversityManager universityManager;
	
	private String jsonStr;		
	private String tcRightStr;
	
	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType ;
	private String navTabId;
	
	private static Integer cids = -1;	//临时存储由jsp传来的班级id
	private static Integer sids = -1;	//临时存储由jsp传来的学生id
	
	@Autowired
	private AdminStudentManager adminStudentManager; 
	
	private ClassTable condition = new ClassTable();	//对页面查询条件进行封装
	private StudentTable conditionstu = new StudentTable(); //对页面学生查询条件进行封装


	
	/**
	 * 
	 * @Description: TODO(根据权限获取学生管理页面及检索列表) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:26:28
	 */
	@Action(value="getAdminStudentManagerPage",results={
			@Result(name="superAdminManager",location="/admin/adminstudentManager/superAdminStudent.jsp"),
			@Result(name="universityAdminManager",location="/admin/adminstudentManager/universityAdminStudent.jsp"),
			@Result(name="collegeAdminManager",location="/admin/adminstudentManager/collegeAdminStudent.jsp")})
	public String getAdminStudentManagerPage()throws Exception{
		//根据权限返回检索列表相关内容，包括选择学校、选择学院、选择班级
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);		
		if(currentManager.getMiden() == 1){
			//校级管理员
			List<CollegeTable> colls = adminStudentManager.selectAllCollegeByUnID(currentManager.getUniversity().getUnID());
			if(condition.getCollege() != null){
				if(condition.getCollege().getCeID() != null)
				{//表示已经选择了学院
					List<ClassTable> clas = adminStudentManager.selectAllClassByCeId(condition.getCollege().getCeID());
					request.setAttribute("clas", clas);
				}
			}
			request.setAttribute("colleges", colls);
		}		
		else if(currentManager.getMiden() == 0){
			//院级管理员
			List<ClassTable> clas = adminStudentManager.selectAllClassByCeId(currentManager.getCollege().getCeID());
			request.setAttribute("clas", clas);
		}	
		else if(currentManager.getMiden() == -1){
			//超级管理员
			List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
			if(condition.getCollege() != null)
			{//表示已经选择了学校
				if (condition.getCollege().getUniversity() != null) {
					List<CollegeTable> colls = adminStudentManager.selectAllCollegeByUnID(condition.getCollege().getUniversity().getUnID());
					request.setAttribute("colleges", colls);
				}
				if(condition.getCollege().getCeID() != null)
				{//表示已经选择了学院
					List<ClassTable> clas = adminStudentManager.selectAllClassByCeId(condition.getCollege().getCeID());
					request.setAttribute("clas", clas);
				}
			}	
			request.setAttribute("universities", univers);
		}
		getAdminPage(currentManager);	//获取学生
		if(currentManager.getMiden() == -1)
			return "superAdminManager";
		else if(currentManager.getMiden() == 1)
			return "universityAdminManager";
		else return "collegeAdminManager";
	}
	
	/**
	 * 
	 * @Description: TODO(获取学生，以from表格形式展示) 
	 * @param currentManager
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:26:45
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public void getAdminPage(ManagerTable currentManager){
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		
		orderField = (orderField==null || "".equals(orderField))?"s.sno":orderField;	//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;	//默认升序	
		
		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		param.put("Manager", currentManager);
		param.put("condition", condition);
		param.put("conditionstu", conditionstu);
		
		Pagination<ClassStudentTable> page=new Pagination<ClassStudentTable>();
		page = adminStudentManager.getStudentsPage(param);

		request.setAttribute("page", page);
		request.setAttribute("condition", condition);//查询条件回显
		request.setAttribute("conditionstu", conditionstu);//查询条件回显
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);	
	}
	
	/**
	 * 
	 * @Description: TODO(根据管理员权限返回添加学生页面的选择列表数据) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:29:21
	 */
	@Action(value="addStudent", results={@Result(name="success",location="/admin/adminstudentManager/addStudent.jsp")})
	public String addStudent() throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);
		if(currentManager.getMiden() == -1){
			//超级管理员
			List<UniversityTable> universityList = adminStudentManager.selectAllUniversity();
			request.setAttribute("universities", universityList);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: TODO(根据管理员权限保存学生相关信息，若jsp提交的数据不完整则返回相应不完整内容的提示，若添加的学生其学校id与学号同时存在则返回“学号存在”提示) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:38:15
	 */
	@Action(value="saveStudent", results={@Result(name="success",type="json")})
	public String saveStudent() throws Exception{
		String unIdStr = request.getParameter("unId");
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer unid = (currentManager.getMiden() == -1) ? Integer.valueOf(unIdStr):currentManager.getUniversity().getUnID();	//非超级管理员则从jsp页面获取学校id
		
		if(unid == -2){
			message = "请选择学校！";
			statusCode = "300";
		}
		else {
			UniversityTable uni = new UniversityTable();
			uni.setUnID(unid);
			stu.setUniversity(uni);
			if(adminStudentManager.isStudentexist(stu) == true){
				message = "该学号已经存在,请修改相关信息再提交！";
				statusCode = "300";		
			}else{
				if(adminStudentManager.saveStudent(stu)){
					if(currentManager.getMiden() == 0)
					{
						message = "添加成功！您是院级管理员，请到班级管理关联所添加的学生后才能对该学生进行操作！";
						statusCode = "200";
						navTabId = "adminstudentManager";
					}
					else{
						message = "添加成功！";
						statusCode = "200";
						navTabId = "adminstudentManager";
					}
				}else{
					message = "添加失败！";
					statusCode = "300";
				}
			}
		}
		forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
		callbackType  = "closeCurrent";
		System.out.println("-----------saveStudent中-----------");
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: TODO(根据学生id及jsp提交的数据修改学生信息) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:40:36
	 */
	@Action(value="editStudent", results={@Result(name="success",location="/admin/adminstudentManager/updateStudent.jsp")})
	public String editStudent() throws Exception{
		Integer sid = Integer.valueOf(request.getParameter("sid"));
		StudentTable stu = adminStudentManager.gainStudentBySid(sid);
		request.setAttribute("student", stu);
		
		String uname = stu.getUniversity().getUnName();
		request.setAttribute("uname", uname);
		
		return SUCCESS;
	}

	@Action(value="initPassword", results={@Result(name="success",type="json")})
	public String initPassword() throws Exception{
		Integer sid = Integer.valueOf(request.getParameter("sid"));
		if(adminStudentManager.initPassword(sid) == true)
		{
			message = "初始化密码成功！";
			statusCode = "200";
			navTabId = "adminstudentManager";
			forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
			callbackType = "forward";
			return SUCCESS;
		}
		else{
			statusCode = "300";
			message = "删除失败！";
			forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
			callbackType  = "forward";
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: TODO(更新修改学生后的信息，若修改后的学生学号已存在则返回“已存在”提示) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:41:43
	 */
	@Action(value="updateStudent", results={@Result(name="success",type="json")})
	public String updateStudent() throws Exception{
		if(adminStudentManager.isStudentexistBysid(stu) == false){
			if(adminStudentManager.updateStudent(stu)){
				message = "修改成功！";
				statusCode = "200";
				navTabId = "adminstudentManager";
			}else{
				message = "修改失败！";
				statusCode = "300";
			}
		}else{
			message = "该学号已经存在,请修改相关信息再提交！";
			statusCode = "300";
		}
		forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
		
	}
	
	/**
	 * 
	 * @Description: TODO(根据jsp传来的数据删除选中的学生信息，若数组为空则返回“选择数据”提示) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:26:56
	 */
	@Action(value="deleteStudent", results={@Result(name="success",type="json")})
	public String deleteStudent() throws Exception{
		String[] sids = request.getParameterValues("sids");
		if(sids == null){		
			statusCode = "300";
			message = "请先勾选要删除的学生！";
			forwardUrl = "adminstudentManager/getAdminStudentManagerPage.action";
			callbackType = "forward";
			return SUCCESS;
		}
		else if(sids.length > 0){
		for(int i = 0; i < sids.length; i++)
			if(adminStudentManager.deleteStudentBySid(Integer.valueOf(sids[i])) == false)
				break;
			message = "删除成功！";
			statusCode = "200";
			navTabId = "adminstudentManager";
			forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
			callbackType = "forward";
			return SUCCESS;	
		}
		statusCode = "300";
		message = "删除失败！";
		forwardUrl = "admin/adminstudentManager/getAdminStudentManagerPage.action";
		callbackType  = "forward";
		return SUCCESS;
	}
	
	/**
	 * @Description: TODO(根据jsp传来的数据关联选中的学生到班级，若数组为空则返回“选择数据”提示) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:26:56
	 */
	@Action(value="batAddStudentInClass", results={@Result(name="success",type="json")})
	public String batAddStudentInClass() throws Exception{
		String[] sids = request.getParameterValues("sids");
				
		if(sids == null){		
			statusCode = "300";
			message = "请先勾选要关联的学生！";
			forwardUrl = "adminstudentManager/getListStudentPage.action";
			callbackType = "forward";
			return SUCCESS;
		}
		else if(sids.length > 0){
		for(int i = 0; i < sids.length; i++)
			if(adminStudentManager.batAddStudentInClass(Integer.valueOf(sids[i]), cids) == false)
				break;
			message = "关联成功！";
			statusCode = "200";
			navTabId = "listStudent";
			forwardUrl = "admin/adminstudentManager/getListStudentPage.action";
			callbackType = "forward";
			return SUCCESS;	
		}
		statusCode = "300";
		message = "关联失败！";
		forwardUrl = "admin/adminstudentManager/getListStudentPage.action";
		callbackType  = "forward";
		return SUCCESS;
	}
	
	/**
	 * @Description DWZ相关参数封装获取方法
	 */
	

	public ClassStudentTable getCs() {
		return cs;
	}

	public void setCs(ClassStudentTable cs) {
		this.cs = cs;
	}
	
	public ClassTable getCondition() {
		return condition;
	}

	public void setCondition(ClassTable condition) {
		this.condition = condition;
	}

	public StudentTable getConditionstu() {
		return conditionstu;
	}

	public void setConditionstu(StudentTable conditionstu) {
		this.conditionstu = conditionstu;
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
	
	public StudentTable getStu() {
		return stu;
	}

	public void setStu(StudentTable stu) {
		this.stu = stu;
	}
	
}
