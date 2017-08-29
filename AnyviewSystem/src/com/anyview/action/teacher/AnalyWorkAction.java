/**   
 * @Title: AnalyWork.java
 * @Package com.stusys.action.anyview
 * @Description: TODO
 * @author xhn 
 * @date 2012-11-2 下午10:57:54
 * @version V1.0   
 */
package com.anyview.action.teacher;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
//import com.stusys.entities.anyview.TeacherTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.SchemeT;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.AnalyWorkService;

/**
 * @ClassName: AnalyWork
 * @Description: 批改作业
 * @author sjt
 * @date 2015-7-28 
 * 
 */
@SuppressWarnings("serial")
@Namespace("/teacher/homeworkCorrecting")
@ParentPackage("json-default")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class AnalyWorkAction extends CommonAction {
	
	@Autowired
	private AnalyWorkService awService;

	private List<?> rows;//返回数据
	private Map<String,Object> map;
	
	private int classId;
	private int cid;
	private int courseId;
	private int schemeId;
	private int problemId;
	private int stuId;
	
	private String jsonStr;
	
	@Action(value="gainHomeworkCorrectPage",results={@Result(name="success",location="/teacher/homeworkCorrecting/homeworkCorrect.jsp")})
	public String gainHomeworkCorrectPage()throws Exception{
		return SUCCESS;
	}
	/**
	 * 
	* @Title: getClassesList
	* @Description: 获取班级列表和课程信息
	* @return
	* @return String
	 */
	@JSON(serialize=false)
	@Action(value="getClassesList",results={@Result(name="success",type="json",params ={"root","rows"})})
	public String getClassesList() {
		System.out.println("Hello World");
		TeacherTable tea = (TeacherTable)session.get("User");
		rows = (List<ClassTable>)awService.getClassListById(tea.getTid(),courseId);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: getScheme
	* @Description: 根据班级id和课程id获取作业表信息
	* @return String
	 */
	@JSON(serialize=false)
	@Action(value="getScheme",results={@Result(name="success",type="json",params ={"root","rows"})})
	public String getScheme(){
		int teaId = ((TeacherTable)session.get("User")).getTid();
		rows = (List<SchemeT>)awService.getScheme(cid, courseId, teaId);
		return SUCCESS;
	}
	/**
	 * 
	* @Title: getData
	* @Description: 获取预加载表格信息
	* @return String
	 */
	@JSON(serialize=false)
	@Action(value="getTempGridData",results={@Result(name="success",type="json",params ={"map","rows"})})
	public String getData(){
		map = awService.getTempDataGrid(cid, schemeId);
		if(map.get("msg") != null){
			return "error";
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: getStuDetail
	* @Description: 获取某题目的详细学生做题信息
	* @return
	* @return String
	 */
	@JSON(serialize=false)
	@Action(value="getStuDetail",results={@Result(name="success",type="json",params ={"rows","rows"})})
	public String getStuDetail(){
		rows = awService.getStuDetail(cid, schemeId, problemId);
		 System.out.println(rows);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: getProDetail
	* @Description: 获取某学生的详细题目信息
	* @return
	* @return String
	 */
	@JSON(serialize=false)
	@Action(value="getProDetail",results={@Result(name="success",type="json",params ={"rows","rows"})})
	public String getProDetail(){
		rows = awService.getPointDetail(schemeId, stuId);
		return SUCCESS;
	}
	
	public void setAwService(AnalyWorkService awService) {
		this.awService = awService;
	}

	public List<?> getRows() {
		return rows;
	}
	
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(int classId) {
		this.classId = cid;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public Map<String,Object> getMap() {
		return map;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	/**
	 * @param stuId the stuId to set
	 */
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	@JSON
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
}
