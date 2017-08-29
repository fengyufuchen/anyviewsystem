package com.anyview.action.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ClassTeacherManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@SuppressWarnings("serial")
@Namespace("/admin/courseManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

/**
 * 课程管理Action(管理员页面专属)
 * @author 吴汪洋
 *
 */
public class CourseManagerAction extends CommonAction{
	
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	//封装jsp参数
	private CourseTable coursetable;
	//传递给Jsp的参数
	private String jsonStr;
 
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CourseManager courseManager;
	
	private Timestamp createDateStart;
	private Timestamp createDateEnd;
	private Timestamp updateDateStart; 
	private Timestamp updateDateEnd; 
	
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();//存放内容
	private int total;//rows的行数
	
	/**
	 * @author 吴汪洋
	 * @Description
	 * 课程类别，类型:String[](以下标区分)
	 * 下标0: 学院级课程
	 * 下标1: 校级课程
	 * 下标2: 校内共享课程
	 * 下标3: 公共课程
	 */
	private static String categoryList[] = new String[]{"学院级课程", "校级课程", "校内共享课程", "公共课程"};
	
	
	/**
	 * @author 吴汪洋
	 * @Description
	 * 获取课程页面
	 * 根据当前登录的管理员的权限(查询表ManagerTable)
	 * 读取表CourseTable中相应的数据
	 * 院级管理员:能查看 (1)当前学校当前学院的课程 (2)当前学校的共享课程 (3)所有学校的共享课程(公共课程)
	 * 校级管理员:能查看 (1)当前学校所有学院的课程 (2)当前学校的共享课程 (3)所有学校的共享课程(公共课程)
	 * 超级管理员:能查看所有学校的所有课程
	 * @return courseManager显示课程页面
	 * @throws Exception
	 */
	@Action(value="getCoursePage",results={@Result(name="success",location="/admin/courseManager/courseManager.jsp")})
	public String getCoursePage()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer manager=currentManager.getMiden();
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		orderField = (orderField==null || "".equals(orderField))?"courseName":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Integer unid=null;
		Integer ceid=null;
		if(request.getParameter("unID")!=null&&!"".equals(request.getParameter("unID")))//根据是否学校ID为空和管理者身份判断jsp界面学校检索栏的内容
		{
			
			unid=Integer.valueOf(request.getParameter("unID"));
			
		}
		
		
		if(request.getParameter("ceID")!=null && !"".equals(request.getParameter("ceID")))//同理
		{
			ceid=Integer.valueOf(request.getParameter("ceID"));
			
		}
	
		if(manager==0)
		{
			unid=currentManager.getUniversity().getUnID();
			ceid=currentManager.getCollege().getCeID();
		}
		else if(manager==1)
		{
			unid=currentManager.getUniversity().getUnID();
			
			
		}
		if(request.getParameter("createDateStart")!=null&&!request.getParameter("createDateStart").equals("")
				&&request.getParameter("createDateEnd")!=null&&!request.getParameter("createDateEnd").equals("")){
			createDateStart=Timestamp.valueOf(request.getParameter("createDateStart"));
			createDateEnd=Timestamp.valueOf(request.getParameter("createDateEnd"));
		}
		if(request.getParameter("updateDateStart")!=null&&!request.getParameter("updateDateStart").equals("")
				&&request.getParameter("updateDateEnd")!=null&&!request.getParameter("updateDateEnd").equals("")){
			updateDateStart=Timestamp.valueOf(request.getParameter("updateDateStart"));
			updateDateEnd=Timestamp.valueOf(request.getParameter("updateDateEnd"));
		}
	    Map param=new HashMap();
	    param.put("unID", unid);
	    param.put("ceID",ceid);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("admin", manager);
		param.put("course", coursetable);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		param.put("createDateStart", createDateStart);
		param.put("createDateEnd", createDateEnd);
		param.put("updateDateStart",updateDateStart);
		param.put("updateDateEnd", updateDateEnd);
		Pagination<CourseTable> page=courseManager.getCourses(param);
		if(currentManager.getMiden()==-1){
			List<UniversityTable> universities = universityManager.gainAllUniversities();
			request.setAttribute("universities", universities);
		}
		request.setAttribute("page", page);
		request.setAttribute("unid", unid);
		request.setAttribute("admin", currentManager);
		request.setAttribute("ceid", ceid);
		request.setAttribute("criteria", coursetable);
		request.setAttribute("param",param);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	/**
	 * @author 吴汪洋
	 * @Description
	 * 增加课程页面
	 * 根据当前登录的管理员的权限(查询表ManagerTable)
	 * 院级管理员:能增加 (1)当前学校当前学院的课程
	 * 校级管理员:能增加 (1)当前学校所有学院的课程 (2)当前学校的共享课程
	 * 超级管理员:能增加任何学校的任何课程
	 * @return 不同权限的管理员返回不同的界面
	 * @throws Exception
	 */
	@Action(value="getCurrentCourseForCeManagerPage",
			results={@Result(name="CeAdmin",location="/admin/courseManager/addCourseForCeAdmin.jsp"),
					 @Result(name="UnAdmin",location="/admin/courseManager/addCourseForUnAdmin.jsp"),
					 @Result(name="SuperAdmin",location="/admin/courseManager/addCourseForSuperAdmin.jsp")})
	public String getCurrentCourseForCeManagerPage()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer unID = currentManager.getUniversity().getUnID();
		request.setAttribute("admin", currentManager);		
		if(currentManager.getMiden() == 0)
		{// 院级管理员
			return "CeAdmin";
		}
		else if(currentManager.getMiden() == 1)
		{// 校级管理员
			// 获取当前学校的所有学院列表
			List<CollegeTable> collegeList = courseManager.getCollegeByUnID(unID);
			request.setAttribute("college", collegeList);
			return "UnAdmin";
		}
		else
		{// 超级管理员
			// 获取所有学校的列表，相应的页面上将联动查询选中学校的所有学院列表
			List<UniversityTable> unList = courseManager.getAllUniversity();
			List<CollegeTable> collegeList = courseManager.getCollegeByUnID(unID);
			request.setAttribute("university", unList);
			request.setAttribute("college", collegeList);
			return "SuperAdmin";
		}
	}
	
	/**
	 * @author 吴汪洋
	 * @Description
	 * 修改课程页面
	 * 根据当前登录的管理员的权限(查询表ManagerTable)
	 * 院级管理员:能修改 (1)当前学校当前学院的课程
	 * 校级管理员:能修改 (1)当前学校所有学院的课程 (2)当前学校的共享课程
	 * 超级管理员:能修改任何学校的任何课程
	 * @return 不同权限的管理员返回不同的界面
	 * @throws Exception
	 */
	@Action(value="editCourseForDiverseManagerPage",
			results={@Result(name="CeAdmin",location="/admin/courseManager/editCourseForCeAdmin.jsp"),
					 @Result(name="UnAdmin",location="/admin/courseManager/editCourseForUnAdmin.jsp"),
					 @Result(name="SuperAdmin",location="/admin/courseManager/editCourseForSuperAdmin.jsp"),
					 @Result(name="Error",location="/admin/courseManager/editErrorPage.jsp")})
	public String editCourseForDiverseManagerPage()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		
		Integer courseId = Integer.parseInt(request.getParameter("courseId"));
		
		List<CourseTable> courseList = courseManager.getCourseByCourseID(courseId);
		Integer ceID = courseList.get(0).getCollege().getCeID();
		Integer unID = courseList.get(0).getUniversity().getUnID();
		String category = courseList.get(0).getCategory();
		
		request.setAttribute("admin", currentManager);
		request.setAttribute("course", courseList.get(0));
		
		if(currentManager.getMiden() == 0)
		{// 院级管理员
			
			if(ceID == -1 || unID == -1 || category.equals("校级课程"))
			{// 院级管理员没有权限修改共享课程 和 当前学校当前学院的校级课程
				return "Error";
			}
			else
				return "CeAdmin";
		}
		else if(currentManager.getMiden() == 1)
		{// 校级管理员
			if(unID == -1)
			// 校级管理员没有权限修改所有学校的共享课程
				return "Error";
			else
			{
				List<CollegeTable> collegeList = courseManager.getCollegeByUnID(unID);
				request.setAttribute("college", collegeList);
				return "UnAdmin";				
			}

		}
		else
		{//超级管理员
			List<UniversityTable> unList = courseManager.getAllUniversity();
			List<CollegeTable> collegeList = courseManager.getCollegeByUnID(unID);
			request.setAttribute("university", unList);
			request.setAttribute("college", collegeList);
			return "SuperAdmin";
		}

	}
	
	/**
	 * @author 吴汪洋
	 * @Description
	 * 删除课程页面
	 * 根据当前登录的管理员的权限(查询表ManagerTable)
	 * 院级管理员:能删除 (1)当前学校当前学院的课程
	 * 校级管理员:能删除 (1)当前学校所有学院的课程 (2)当前学校的共享课程
	 * 超级管理员:能删除任何学校的任何课程
	 * @return 不同权限的管理员返回不同的界面
	 * @throws Exception
	 */	
	@Action(value="deleteCoursePage",
			results={@Result(name="Delete",location="/admin/courseManager/deleteCourse.jsp"),
					 @Result(name="Error",location="/admin/courseManager/editErrorPage.jsp")})
	public String deleteCoursePage()
	{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		
		Integer courseId = Integer.valueOf(request.getParameter("courseId"));
		
		List<CourseTable> courseList = courseManager.getCourseByCourseID(courseId);
		Integer ceID = courseList.get(0).getCollege().getCeID();
		Integer unID = courseList.get(0).getUniversity().getUnID();
		String category = courseList.get(0).getCategory();
		request.setAttribute("course",courseList.get(0));
		
		if(currentManager.getMiden() == 0)
		{// 院级管理员
			
			if(ceID == -1 || unID == -1 || category.equals("校级课程"))
			{// 院级管理员没有权限修改共享课程 和 当前学校当前学院的校级课程
				return "Error";
			}
			else
				return "Delete";
		}
		else if(currentManager.getMiden() == 1)
		{// 校级管理员
			if(unID == -1)
			// 校级管理员没有权限修改所有学校的共享课程
				return "Error";
			else
			{
				return "Delete";				
			}
		}
		else
		{//超级管理员
			return "Delete";
		}
	}
	
	/**
	 * 增加界面提交表单后，对数据库的操作，以及对错误的处理
	 * @throws Exception
	 */
	@Action(value="addCourse",results={@Result(name="jsonStr",type="json")})
	public void addCourse()throws Exception{
		String courseName = (String) request.getParameter("courseName");
		Integer ceID = Integer.parseInt(request.getParameter("ceID"));
		Integer unID = Integer.parseInt(request.getParameter("unID"));
		Integer categoryIndex = Integer.parseInt(request.getParameter("category"));
		String category = categoryList[categoryIndex];
		Integer enabled = Integer.valueOf(request.getParameter("enabled"));
		
		if(categoryIndex == 3)
		{
			ceID = -1;
			unID = -1;
		}
		else if(categoryIndex == 2)
		{
			ceID = -1;
		}
		
		if(courseName.trim().equals(""))
		{
			jsonStr = AjaxObject.newError("课程名称不能为空!").toString();
			ResponseUtils.outJson(response, jsonStr);
		}
		else if(true == courseManager.isDuplicateCourseName(courseName, ceID, unID))
		{
			jsonStr = AjaxObject.newError("课程名称重复!请重试!").toString();
			ResponseUtils.outJson(response, jsonStr);
		}
		else
		{
			courseManager.addCourses(courseName, ceID, unID, category, enabled);
			jsonStr = AjaxObject.newOk("成功添加课程!").setNavTabId("courseManager").toString();
			ResponseUtils.outJson(response, jsonStr);
		}		
	}
	
	/**
	 * 修改界面提交表单后，对数据库的操作，以及对错误的处理
	 * @throws Exception
	 */
	@Action(value="editCourse",results={@Result(name="jsonStr",type="json")})
	public void editCourse()throws Exception{
		Integer courseId = Integer.parseInt(request.getParameter("courseId"));
		String oldCourseName = (String) request.getParameter("oldCourseName");
		String courseName = (String) request.getParameter("courseName");
		Integer ceID = Integer.parseInt(request.getParameter("ceID"));
		Integer unID = Integer.parseInt(request.getParameter("unID"));
		Integer categoryIndex = Integer.parseInt(request.getParameter("category"));
		String category = categoryList[categoryIndex];
		Integer enabled = Integer.valueOf(request.getParameter("enabled"));
		
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Integer miden= currentManager.getMiden();
		
		if(categoryIndex == 3)
		{
			ceID = -1;
			unID = -1;
		}
		else if(categoryIndex == 2)
		{
			ceID = -1;
		}
		
		if(courseName.trim().equals(""))
		{
			jsonStr = AjaxObject.newError("课程名称不能为空!").toString();
			ResponseUtils.outJson(response, jsonStr);
		}
		else if(false == oldCourseName.trim().equals(courseName) &&
				true == courseManager.isDuplicateCourseName(courseName, ceID, unID))
		{
			jsonStr = AjaxObject.newError("课程名称重复!请重试!").toString();
			ResponseUtils.outJson(response, jsonStr);
		}
		else
		{
			courseManager.editCourseByCourseID(courseId, courseName, ceID, unID, category, enabled);
			jsonStr = AjaxObject.newOk("成功修改课程!").setNavTabId("courseManager").toString();
			ResponseUtils.outJson(response, jsonStr);
		}

		
	}
	
	/**
	 * 删除界面提交表单后，对数据库的操作，以及对错误的处理
	 * @throws Exception
	 */
	@Action(value="deleteCourse",results={@Result(name="jsonStr",type="json")})
	public void deleteCourse() throws IOException
	{		
		Integer courseId = Integer.parseInt(request.getParameter("courseId"));
		
		try {
			courseManager.deleteCourseByCourseID(courseId);
			jsonStr = AjaxObject.newOk("成功删除!").setNavTabId("courseManager").toString();
		} catch(HibernateException e)
		{
			jsonStr = AjaxObject.newError("该课程有下属作业表或学生自定义项目，不可删除！").toString();
		}

		ResponseUtils.outJson(response, jsonStr);	
	}
	
	
	/**
	 * 下拉框二级联动，根据学校ID查询对应学校的所有学院
	 * @throws Exception
	 */
	@Action(value="getCollegeByUnIDAjax",results={@Result(type="json")})
	public void getCollegeByUnIDAjax() throws Exception
	{
		Integer unID = Integer.parseInt(request.getParameter("unID"));
		List<CollegeTable> collegeList = courseManager.getCollegeByUnID(unID);		
		String jsonRe = ResponseUtils.getCollegeIdAndNameJson(collegeList);		
		ResponseUtils.outJson(response, jsonRe);
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
}

