package com.anyview.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.CCSTable;
import com.anyview.entities.ClassTable;
import com.anyview.service.function.ClassCourseManager;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.SchemeManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.util.dwz.ResponseUtils;

@SuppressWarnings("serial")
@Namespace("/admin/classCourseManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ClassCourseManageAction extends CommonAction{
	
	@Autowired
	private SchemeManager schemeManager;
	@Autowired
	private ClassCourseManager ccManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private TeacherManager teacherManager;
	
	@Action(value="classCourseManager",results={@Result(name="success",location="/admin/classCourseManager/classCourseManager.jsp")})
	public String classCourseManager(){
		
		return SUCCESS;
	}
	private List courseList;
	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}
	private List classList;
	
	

	public List getClassList() {
		return classList;
	}

	public void setClassList(List classList) {
		this.classList = classList;
	}

	private List teacherList;
	public List getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List teacherList) {
		this.teacherList = teacherList;
	}
	private List schemeList;
	public List getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List schemeList) {
		this.schemeList = schemeList;
	}
	
	private List ccsList;

	public List getCcsList() {
		return ccsList;
	}

	public void setCcsList(List ccsList) {
		this.ccsList = ccsList;
	}
	
	private int settingResult;

	public int getSettingResult() {
		return settingResult;
	}

	public void setSettingResult(int settingResult) {
		this.settingResult = settingResult;
	}

	@Action(value="allInfo",results={@Result(name="success",type="json", params={"classList","classList","courseList","courseList"})})
	public String allInfo(){
		classList = classManager.getAllClasses();
		courseList = courseManager.getAllCourse();
		return SUCCESS;
	}
	
	@Action(value="allScheme",results={@Result(name="success",type="json", params={"schemeList","schemeList"})})
	public String allScheme(){
		schemeList = schemeManager.getAllScheme();
		return SUCCESS;
	}
	
	@Action(value="allTeacher",results={@Result(name="success",type="json", params={"teacherList","teacherList"})})
	public String allTeacher(){
		teacherList = teacherManager.getAllTeacher();
		return SUCCESS;
	}
	
	@Action(value="ccsinfobyclass",results={@Result(name="success",type="json", params={"ccs","ccs"})})
	public String ccsByClass(){
		String classId=request.getParameter("classId");
		ccsList = ccManager.getCCSByClass(classId);
		return SUCCESS;
	}
	
	@Action(value="ccsinfobycourse",results={@Result(name="success",type="json", params={"ccs","ccs"})})
	public String ccsByCourse(){
		String courseId=request.getParameter("courseId");
		ccsList = ccManager.getCCSByCourse(courseId);
		return SUCCESS;
	}
	
	@Action(value="setting",results={@Result(name="success",type="json", params={"settingResult","settingResult"})})
	public String setCcs(){
		String classId=request.getParameter("classId");
		String courseId=request.getParameter("courseId");
		String schemeId=request.getParameter("vid");
		String teacherId=request.getParameter("tid");
		String status=request.getParameter("status");
		settingResult =  ccManager.settingCCS(classId, courseId, schemeId, teacherId, status);
		return SUCCESS;
	}
}
