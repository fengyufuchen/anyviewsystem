/**   
* @Title: ExaminationManageAction.java 
* @Package com.anyview.action.teacher 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月10日 下午2:57:14 
* @version V1.0   
*/
package com.anyview.action.teacher;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.anyview.entities.ClassTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ExamPlanTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.ExamPlanManager;
import com.anyview.service.function.SchemeManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

/** 
 * @ClassName: ExaminationManageAction 
 * @Description: TODO(考试编排管理action) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月10日 下午2:57:14 
 *  
 */
@SuppressWarnings("serial")
@Namespace("/teacher/examPlanManager")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ExamPlanManageAction extends CommonAction{
	//日志
	private static final Log log = LogFactory.getLog(ProblemLibManageAction.class);
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	private Integer pageNum;//当前页数
	private Integer numPerPage;//页面大小
	private String jsonStr;
	
	@Autowired
	private ExamPlanManager examPlanManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private SchemeManager schemeManager;
	
	private ExamPlanTable examPlan = new ExamPlanTable();
	private ClassTable cla = new ClassTable();
	private CourseTable course = new CourseTable();
	
	@Action(value="gainExamPlanManagerPage",results={@Result(name="examPlanManager",location="/teacher/examPlanManage/examPlanManage.jsp")})
	public String gainExamPlanManagerPage(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentPage", pageNum==null?1:pageNum);
		params.put("numPerPage", numPerPage==null?PAGE_SIZE:numPerPage);
		params.put("teacher", teacher);
		params.put("examPlan", examPlan);
		orderField = (orderField==null || "".equals(orderField))?"updateTime":orderField;//默认按更新时间
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"desc":orderDirection;//默认降序
		params.put("orderField", orderField);
		params.put("orderDirection", orderDirection);
		Pagination<ExamPlanTable> page = examPlanManager.getExamPlanPage(params);
		request.setAttribute("page", page);
		request.setAttribute("teacher", teacher);
		request.setAttribute("criteria", examPlan);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return "examPlanManager";
	}
	
	@Action(value="addExamPlan",results={@Result(name="addExamPlan",location="/teacher/examPlanManage/addExamPlan.jsp")})
	public String addExamPlan(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		List<ClassTable> clas = classManager.getClassFromClassTeacherCourse(teacher.getTid());
		request.setAttribute("clas", clas);
		return "addExamPlan";
	}
	
	@Action(value="gainCoursesAjax")
	public void gainCoursesAjax() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		List<Object[]> courseIN = courseManager.getCourseINFromClassTeacherCourse(teacher.getTid(), cla.getCid());
		courseIN.add(0, new Object[]{-1, "请选择课程"});
		jsonStr = ResponseUtils.getIdAndNameDwzComboxJson(courseIN);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value="gainSchemesAjax")
	public void gainSchemesAjax() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		Integer courseId = Integer.valueOf(request.getParameter("courseId"));
		cla.setCid(cid);
		course.setCourseId(courseId);
		List<Object[]> schemeIN = schemeManager.getSchemeINFromClassCourseScheme(teacher, cla, course);
		schemeIN.add(0, new Object[]{-1, "请选择考试表"});
		jsonStr = ResponseUtils.getIdAndNameDwzComboxJson(schemeIN);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value="saveExamPlan")
	public void saveExamPlan() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		try{
			examPlan.setStatus(0);//新建测验状态默认为未使用
			examPlan.setTeacher(teacher);
			examPlan.setAutomaticEndFlag(ExamPlanTable.ALLOW_AUTOMATIC_END);//新测验允许自动结束
			examPlan.setAutomaticStartFlag(ExamPlanTable.ALLOW_AUTOMATIC_START);//新测验允许自动开始
			if(examPlan.getKind()==0){//手动不保存开始时间
				examPlan.setStartTime(null);
			}
			examPlanManager.saveExamPlan(examPlan);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("examPlanManager").toString();
		} catch (TipException t){
			log.debug("保存考试编排失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("保存考试编排系统错误with : "+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="deleteExamPlan")
	public void deleteExamPlan() throws IOException{
		try{
			examPlanManager.deleteExamPlan(examPlan);
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/examPlanManager/gainExamPlanManagerPage.action").toString();
		} catch (TipException t){
			log.debug("删除考试编排失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("删除考试编排系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="editExamPlan",results={
			@Result(name="editExamPlan",location="/teacher/examPlanManage/editExamPlan.jsp")})
	public String editExamPlan() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		examPlan = examPlanManager.getExamPlanByEpId(examPlan.getEpId());
		List<ClassTable> clas = classManager.getClassFromClassTeacherCourse(teacher.getTid());
		request.setAttribute("clas", clas);
		request.setAttribute("examPlan", examPlan);
		return "editExamPlan";
	}
	
	@Action(value="updateExamPlan")
	public void updateExamPlan() throws IOException{
		try{
			ExamPlanTable e = examPlanManager.getExamPlanByEpId(examPlan.getEpId());
			if(e.getStatus().intValue() != ExamPlanTable.NO_USED)
				throw new TipException("只有未使用的考试才能修改");
			e.setEpName(examPlan.getEpName());
			e.setDuration(examPlan.getDuration());
			e.setKind(examPlan.getKind());
			e.setStartTime(examPlan.getStartTime());
			if(e.getKind()==0){//手动不保存开始时间
				e.setStartTime(null);
			}
			e.setCla(examPlan.getCla());
			e.setCourse(examPlan.getCourse());
			e.setScheme(examPlan.getScheme());
			e.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			examPlanManager.updateExamPlan(e);
			jsonStr = AjaxObject.newOk("修改成功!").setNavTabId("examPlanManager").toString();
		} catch (TipException t){
			log.debug("更新考试编排失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("更新考试编排系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("修改失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 
	 * @Description: TODO(考试准备，将对应班级设置为做题锁定状态) 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月16日 下午8:16:49
	 */
	@Action(value="prepareExam")
	public void prepareExam() throws IOException{
		try{
			TeacherTable teacher = (TeacherTable) session.get("User");
			examPlan.setTeacher(teacher);
			examPlanManager.prepareExam(examPlanManager.getExamPlanByEpId(examPlan.getEpId()));
			jsonStr = AjaxObject.newOk("操作成功!").setNavTabId("examPlanManager").setCallbackType("").toString();
		} catch (TipException t){
			log.debug("准备考试失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("准备考试系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("操作失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="startExam")
	public void startExam() throws IOException{
		try{
			TeacherTable teacher = (TeacherTable) session.get("User");
			examPlan.setTeacher(teacher);
			examPlanManager.startExam(examPlanManager.getExamPlanByEpId(examPlan.getEpId()));
			jsonStr = AjaxObject.newOk("操作成功!").setNavTabId("examPlanManager").setCallbackType("").toString();
		} catch (TipException t){
			log.debug("开始考试失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("开始考试系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("操作失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="canclePrepareExam")
	public void canclePrepareExam() throws IOException{
		try{
			examPlanManager.canclePrepareExam(examPlanManager.getExamPlanByEpId(examPlan.getEpId()));
			jsonStr = AjaxObject.newOk("操作成功!").setNavTabId("examPlanManager").setCallbackType("").toString();
		} catch (TipException t){
			log.debug("取消准备失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("取消准备系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("操作失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="interruptExam")
	public void interruptExam() throws IOException{
		try{
			examPlanManager.interruptExam(examPlanManager.getExamPlanByEpId(examPlan.getEpId()));
			jsonStr = AjaxObject.newOk("操作成功!").setNavTabId("examPlanManager").setCallbackType("").toString();
		} catch (TipException t){
			log.debug("中止考试失败with:"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("中止考试系统错误!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("操作失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	public ExamPlanTable getExamPlan() {
		return examPlan;
	}

	public void setExamPlan(ExamPlanTable examPlan) {
		this.examPlan = examPlan;
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

	public CourseTable getCourse() {
		return course;
	}

	public void setCourse(CourseTable course) {
		this.course = course;
	}
	

}
