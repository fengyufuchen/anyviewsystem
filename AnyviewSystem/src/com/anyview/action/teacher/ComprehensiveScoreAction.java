package com.anyview.action.teacher;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentExerciseAnswerVO;
import com.anyview.entities.StudentSchemeDetailVO;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ComprehensiveScore;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;

@SuppressWarnings("serial")
@Namespace("/teacher/comprehensiveScore")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ComprehensiveScoreAction extends CommonAction{
	//日志
	private static final Log log = LogFactory.getLog(ComprehensiveScoreAction.class);
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	private Integer pageNum;//当前页数
	private Integer numPerPage;//页面大小
	private String jsonStr;
	
	@Autowired
	private ComprehensiveScore comprehensiveScore;
	
	private ClassCourseSchemeTable ccs = new ClassCourseSchemeTable();
	private StudentTable student = new StudentTable();
	private SchemeTable scheme = new SchemeTable();
	private ClassTable cla = new ClassTable();
	private GradeRules rule = new GradeRules();
	
	/**
	 * author:李泽熊
	 * 获取该名教师任教班级的作业表信息
	 * @return
	 * @throws Exception
	 */
	@Action(value="gainCCSchemePage",results={@Result(name="success",location="/teacher/comprehensiveScore/comprehensiveScore.jsp")})
	public String gainCCSchemePage()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		ccs.setTeacher(teacher);
		pageNum = pageNum==null?1:pageNum;
		numPerPage = numPerPage==null?PAGE_SIZE:numPerPage;
		Pagination<ClassCourseSchemeTable> page = comprehensiveScore.getClassCoursePage(pageNum, numPerPage, ccs);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	/**
	 * author:李泽熊
	 * 获取该名教师任教班级的作业表的详细学生分数信息
	 * @return
	 * @throws Exception
	 */
	@Action(value="gainStudentScoresPage",results={@Result(name="success",location="/teacher/comprehensiveScore/listStudentScoreDetail.jsp")})
	public String gainStudentScoresPage()throws Exception{
		pageNum = pageNum==null?1:pageNum;
		numPerPage = numPerPage==null?PAGE_SIZE:numPerPage;
//		Pagination<StudentSchemeDetailVO> page = comprehensiveScore.getStudentSchemeDetailsPage(pageNum, numPerPage,ccs.getId());
//		ccs = comprehensiveScore.getClassCourseSchemeById(ccs.getId());
//		request.setAttribute("cla", ccs.getCla());
//		request.setAttribute("scheme", ccs.getScheme());
//		request.setAttribute("page", page);
		orderField = (orderField==null || "".equals(orderField))?"rank":orderField;//默认按排名
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		ccs = comprehensiveScore.getClassCourseSchemeById(ccs.getId());
		Pagination<ScoreTable> page = comprehensiveScore.getSchemeScorePage(pageNum, numPerPage,ccs,orderField,orderDirection);
		request.setAttribute("page", page);
		request.setAttribute("ccs", ccs);
		return SUCCESS;
	}
	
	@Action(value="gainStudentExerciseAnswer",results={@Result(name="studentExerciseAnswer",location="/teacher/comprehensiveScore/studentExerciseAnswer.jsp")})
	public String gainStudentExerciseAnswer(){
		List<StudentExerciseAnswerVO> answers = comprehensiveScore.getStudentExerciseAnswer(student.getSid(), scheme.getVid(), cla.getCid());
		request.setAttribute("exerciseAnswers", answers);
		return "studentExerciseAnswer";
	}
	
	@Action(value="setGradeRules",results={@Result(name="setGradeRules",location="/teacher/comprehensiveScore/setGradeRules.jsp")})
	public String setGradeRules(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		GradeRules r = comprehensiveScore.generateGradeRulesByTid(teacher.getTid());
		request.setAttribute("rule", r);
		return "setGradeRules";
	}
	
	@Action(value="saveGradeRules")
	public void saveGradeRules() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		rule.setTeacher(teacher);
		rule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		comprehensiveScore.updateGradeRules(rule);
		jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("studentScores").toString();
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value="grade")
	public void grade() throws IOException{
		String sType = request.getParameter("sType");
		if("0".equals(sType)){
			rule.setPaperRate(100);
			rule.setAttitudeRate(0);
		}
		ccs = comprehensiveScore.getClassCourseSchemeById(ccs.getId());
		comprehensiveScore.grade(rule,ccs);
		jsonStr = AjaxObject.newOk("计算完成!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/comprehensiveScore/gainCCSchemePage.action").toString();
		ResponseUtils.outJson(response, jsonStr);
	}

	public ClassCourseSchemeTable getCcs() {
		return ccs;
	}

	public void setCcs(ClassCourseSchemeTable ccs) {
		this.ccs = ccs;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public StudentTable getStudent() {
		return student;
	}

	public void setStudent(StudentTable student) {
		this.student = student;
	}

	public SchemeTable getScheme() {
		return scheme;
	}

	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}

	public ClassTable getCla() {
		return cla;
	}

	public void setCla(ClassTable cla) {
		this.cla = cla;
	}

	public GradeRules getRule() {
		return rule;
	}

	public void setRule(GradeRules rule) {
		this.rule = rule;
	}
	
	
}
