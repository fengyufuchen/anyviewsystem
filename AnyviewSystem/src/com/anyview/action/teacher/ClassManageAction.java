package com.anyview.action.teacher;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.admin.PasswordManagerAction;
import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassTable;
import com.anyview.service.function.ClassCourseManager;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ClassStudentManager;
import com.anyview.service.function.ClassTeacherManager;
import com.anyview.service.function.StudentsManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@SuppressWarnings("serial")
@Namespace("/teacher/classManager")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ClassManageAction extends CommonAction{
	
	private static final Log log = LogFactory.getLog(ClassManageAction.class);
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	//封装jsp参数
	private ClassTable cla = new ClassTable();
	private StudentTable stu = new StudentTable();
	private ClassStudentTable cs = new ClassStudentTable();
	private ClassTeacherTable ct = new ClassTeacherTable();
	private  static Integer secondCtcid;	//用于处理了学生信息之后重新获取班级学生
	
	//传递给Jsp的参数
	private String jsonStr;
	
	private String tcRightStr;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private ClassCourseManager ccManager;
	@Autowired
	private ClassTeacherManager classTeacherManager;
	@Autowired
	private StudentsManager studentsManager;
	@Autowired
	private ClassStudentManager classStudentManager;
	@Autowired
	private ClassCourseManager classCourseManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainClassCoursePage",results={@Result(name="success",location="/teacher/classManager/classManager.jsp")})
	public String gainClassCoursePage()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "cl.cname"));
		param.put("tid", teacher.getTid());
		Pagination<ClassTeacherCourseTable> page = classTeacherManager.getClassTeacherCoursePage(param);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	/**
	 * motified by hefan 2016.03.24
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainClassManagerPage",results={@Result(name="success",location="/teacher/classManager/classManager.jsp")})
	public String gainClassManagerPage()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "cl.cname"));
		param.put("tid", teacher.getTid());
		Pagination<ClassTeacherTable> page = classTeacherManager.getClassesForTeacher(param);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainStudentsForClass",results={@Result(name="success",location="/teacher/classManager/listStudents.jsp")})
	public String gainStudentsForClass()throws Exception{
		Integer ctcid = null;
		Map param = super.gPageParams(request, PAGE_SIZE, "sid");
		if(request.getParameter("ctcid") != null){
		ctcid = Integer.valueOf(request.getParameter("ctcid"));
		}else{
			ctcid = secondCtcid;
		}
//		Integer ctcid = 2;
		session.put("ctcid", ctcid);
		ClassTeacherCourseTable ctc = classCourseManager.getClassTeacherCourseById(ctcid);
		param.put("cid", ctc.getCla().getCid());
		param.put("classStudent", cs);
		Pagination<ClassStudentTable> page=classManager.getStudentsPage(param);
		request.setAttribute("page", page);
		request.setAttribute("criteria", cs);//查询条件回显
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	@Action(value="gainEditClassPage",results={@Result(name="success",location="/teacher/classManager/editClass.jsp")})
	public String gainEditClassPage()throws Exception{
		TeacherTable tea = (TeacherTable) session.get("User");
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		int tcRight = classTeacherManager.getTCRight(tea.getTid(),cid);
		ClassTable cla = classManager.getClassByCid(cid);
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		request.setAttribute("cla", cla);
		request.setAttribute("right", right);
		request.setAttribute("tcRight", tcRight);
		return SUCCESS;
	}
	
	@Action(value="saveClass")
	public void saveClass()throws Exception{
		try{
			TeacherTable tea = (TeacherTable) session.get("User");
			Integer tcRight = classTeacherManager.getTCRight(tea.getTid(), cla.getCid());
			classManager.updateClass(cla, tcRight);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("classManager").toString();
		}catch(TipException tip){
			jsonStr = AjaxObject.newError(tip.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="checkClassAjax",results={@Result(name="jsonStr",type="json")})
	public String checkClassAjax()throws Exception{
		String cidStr = request.getParameter("cid");
		Integer cid = Integer.valueOf(cidStr);
		ClassTable cla = classManager.getClassByCid(cid);
		TeacherTable tea = (TeacherTable) session.get("User");
		int tcRight = classTeacherManager.getTCRight(tea.getTid(),cid);
		StringBuilder rightStr = new StringBuilder();
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
			rightStr.append(right[i] + "");
		}
		tcRightStr = rightStr.toString();
		jsonStr = cla.toJsonStr();
		return "jsonStr";
	}
	
	@Action(value="deleteStudent")
	public void deleteStudent()throws Exception{
		try{
			TeacherTable tea = (TeacherTable) session.get("User");
			Integer tcRight = classTeacherManager.getTCRight(tea.getTid(), cs.getCla().getCid());
			classManager.deleteStudent(cs.getStudent().getSid(), tcRight);
			Integer ctcid = Integer.valueOf(request.getParameter("ctcid"));
			secondCtcid = ctcid;
//			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/classManager/gainStudentsForClass.action?cs.cla.cid="+cs.getCla().getCid()).toString();
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/classManager/gainStudentsForClass.action?").toString();
		}catch(TipException tip){
			jsonStr = AjaxObject.newError(tip.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="editStudent",results={@Result(name="success",location="/teacher/classManager/updatestudent.jsp")})
	public String editStudent(){
		ClassStudentTable cc = classStudentManager.getClassStudentByCidAndSid(cs.getCla().getCid(), cs.getStudent().getSid());
		request.setAttribute("classStudent", cc);
		return SUCCESS;
	}
	
	@Action(value="updateStudent")
	public void updateStudent() throws IOException{
		try{
			TeacherTable tea = (TeacherTable) session.get("User");
			studentsManager.updateStudent(tea, cs);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("studentList").toString();
		}catch(TipException tip){
			jsonStr = AjaxObject.newError(tip.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="saveStudentForTeacher")
	public void saveStudentForTeacher()throws IOException{
		try{
			TeacherTable tea = (TeacherTable) session.get("User");
			studentsManager.saveStudentForClass(tea,cs);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("studentList").toString();
		}catch(TipException tip){
			jsonStr = AjaxObject.newError(tip.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="lookStudent",results={@Result(name="success",location="/teacher/classManager/lookStudent.jsp")})
	public String lookStudent(){
		Integer sid = Integer.valueOf(request.getParameter("sid"));
		StudentTable stu = studentsManager.gainStudentBySid(sid);
		request.setAttribute("student", stu);
		return SUCCESS;
	}
	
	@Action(value="resetStudentPwd")
	public void resetStudentPwd() throws IOException{
		try{
			TeacherTable tea = (TeacherTable) session.get("User");
			studentsManager.resetStudentPwd(tea, cs);
			Integer ctcid = Integer.valueOf(request.getParameter("ctcid"));
			secondCtcid = ctcid;
//			jsonStr = AjaxObject.newOk("重置密码成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/classManager/gainStudentsForClass.action?cs.cla.cid="+cs.getCla().getCid()).toString();
			jsonStr = AjaxObject.newOk("重置密码成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/classManager/gainStudentsForClass.action?").toString();
		}catch(TipException tip){
			jsonStr = AjaxObject.newError(tip.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	public String getTcRightStr() {
		return tcRightStr;
	}

	public void setTcRightStr(String tcRightStr) {
		this.tcRightStr = tcRightStr;
	}

	public ClassTable getCla() {
		return cla;
	}

	public void setCla(ClassTable cla) {
		this.cla = cla;
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

	public ClassStudentTable getCs() {
		return cs;
	}

	public void setCs(ClassStudentTable cs) {
		this.cs = cs;
	}
	
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       