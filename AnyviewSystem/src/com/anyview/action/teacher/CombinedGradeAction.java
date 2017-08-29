package com.anyview.action.teacher;

import java.io.IOException;
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
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassCourseSchemeManager;
import com.anyview.service.function.ExerciseManager;
import com.anyview.service.function.ScoreManager;
import com.anyview.service.function.SimilarityDetection;
import com.anyview.util.dwz.ResponseUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description: TODO(综合测评action)
 * @author young
 *
 */
@SuppressWarnings("serial")
@Namespace("/teacher/combinedGrade")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CombinedGradeAction extends CommonAction {
	// 一页显示数目
	public static final Integer PAGE_SIZE = 20;
	private Integer pageNum;// 当前页数
	private Integer numPerPage;// 页面大小
	private String jsonStr;

	// Action的实例变量不仅封装request参数，而且也封装response参数
	private ClassTeacherTable conditioncla = new ClassTeacherTable();
	private ClassTeacherCourseTable conditioncou = new ClassTeacherCourseTable();
	private ClassCourseSchemeTable conditionsch = new ClassCourseSchemeTable();
	private ExerciseTable exercise = new ExerciseTable();
	@Autowired
	private ClassCourseSchemeManager classCourseSchemeManager;
	@Autowired
	private ExerciseManager exerciseManager;
	@Autowired
	private SimilarityDetection similarityDetection;
	@Autowired
	private ScoreManager scoreManager;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "gainCombinedGradeManagerPage", results = {
			@Result(name = "success", location = "/teacher/combinedGrade/test1.jsp") })
	public String gainCombinedGradeManagerPage() throws Exception {
		Map param = new HashMap();
		// 获取相应的教师
		TeacherTable teacher = (TeacherTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		// 获取教师的所有班级
		List<ClassTeacherTable> clt = similarityDetection.getClassByTId(teacher.getTid());
		// 如果combinedGrade.jsp文件中选择了班级
		if (conditioncla.getCla() != null) {
			// 此处的conditioncla.getCla().getCid()对应combinedGrade.jsp文件中的conditioncla.cla.cid
			List<ClassTeacherCourseTable> ctc = similarityDetection.getCourseByTIdAndClaId(teacher.getTid(),
					conditioncla.getCla().getCid());
			request.setAttribute("courses", ctc);
			param.put("cid", conditioncla.getCla().getCid());
			// 如果选择了课程
			if (conditioncou.getCourse() != null) {
				// 此处的conditioncou.getCourse().getCourseId()对应combinedGrade.jsp文件中的conditioncou.course.courseId
				List<ClassCourseSchemeTable> ccs = similarityDetection.getSchemeByClaIdAndCouId(
						conditioncla.getCla().getCid(), conditioncou.getCourse().getCourseId());
				request.setAttribute("schemes", ccs);
				param.put("vid", conditionsch.getScheme().getVid());
			}
		}
		request.setAttribute("clas", clt);
		param.put("numPerPage", numPerPage);
		param.put("currentPage", pageNum);
		param.put("exercise", exercise);

		Pagination<ExerciseTable> page = new Pagination<ExerciseTable>();
		page = exerciseManager.getExercise(param);

		request.setAttribute("page", page);
		request.setAttribute("conditioncla", conditioncla);// 查询条件回显
		request.setAttribute("conditioncou", conditioncou);// 查询条件回显
		request.setAttribute("conditionsch", conditionsch);// 查询条件回显
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "gainStudentExerciseForScheme", results = {
			@Result(name = "success", location = "/teacher/combinedGrade/gainStudentExerciseForScheme.jsp") })
	public String gainStudentExerciseForScheme() throws Exception {
		TeacherTable teacher = (TeacherTable) session.get("User");
		List<ClassTeacherTable> clt = similarityDetection.getClassByTId(teacher.getTid());
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		// 先写死
		Integer cid = 1;
		Map<String, Object> param = new HashMap<>();
		param.put("vid", vid);
		param.put("cid", cid);
		param.put("currentPage", pageNum == null ? 1 : pageNum);
		param.put("numPerPage", numPerPage == null ? PAGE_SIZE : numPerPage);
		orderField = (orderField == null || "".equals(orderField)) ? "sno" : orderField;// 默认按更新时间
		orderDirection = (orderDirection == null || "".equals(orderDirection)) ? "desc" : orderDirection;// 默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ExerciseTable> page = exerciseManager.getExercise(param);
		request.setAttribute("page", page);
		request.setAttribute("clas", clt);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "listStudentExerciseDetail", results = {
			@Result(name = "success", location = "/teacher/combinedGrade/listStudentExerciseDetail.jsp") })
	public String listStudentExerciseDetail() throws Exception {
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		// 先写死
		Integer cid = 1;
		Integer sid = 2;
		Map<String, Object> param = new HashMap<>();
		param.put("vid", vid);
		param.put("cid", cid);
		param.put("sid", sid);
		param.put("currentPage", pageNum == null ? 1 : pageNum);
		param.put("numPerPage", numPerPage == null ? PAGE_SIZE : numPerPage);
		orderField = (orderField == null || "".equals(orderField)) ? "eid" : orderField;// 默认按更新时间
		orderDirection = (orderDirection == null || "".equals(orderDirection)) ? "desc" : orderDirection;// 默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ExerciseTable> page = exerciseManager.getExercise(param);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//计算卷面分的弹出窗口
	@Action(value="calculateJuanmianScore",results={@Result(name="calculateJuanmianScore",location="/teacher/combinedGrade/calculateJuanmianScore.jsp")})
	public String calculateJuanmianScore(){
		return "calculateJuanmianScore";
	}
	
	//计算态度分
	@Action(value="calculateTaiduScore",results={@Result(name="calculateTaiduScore",location="/teacher/combinedGrade/calculateTaiduScore.jsp")})
	public String calculateTaiduScore(){
		System.out.println("cid:"+request.getParameter("cid"));
		return "calculateTaiduScore";
	}
	
	//计算综合分
	@Action(value="calculateZongheScore",results={@Result(name="calculateZongheScore",location="/teacher/combinedGrade/calculateZongheScore.jsp")})
	public String calculateZongheScore(){
		return "calculateZongheScore";
	}
	
	/**
	 * 保存学生成绩到服务器
	 * @throws IOException
	 */
	@Action(value = "saveScore")
	public void gainScoreFinishtimeFirstpasttime() throws IOException{
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		//String[] sidArr = request.getParameter("sid").split(",");
		System.out.println("cid:"+cid);
		System.out.println("vid:"+vid);
		System.out.println("sid:"+request.getParameter("sid"));
		//数组中的每个元素为"sid:score"这种格式
		String[] sidAndScore=request.getParameter("sid").split(",");
		int flag=1;	//是否插入成功
		for(String s:sidAndScore){
			String[] arr=s.split(":");
			int sid=Integer.valueOf(arr[0]);
			float score=Float.valueOf(arr[1]);
			System.out.println(sid);
			System.out.println(score);
			scoreManager.updateScoreInScoreTable(sid, vid, cid, score);
		}
		JSONObject result=new JSONObject();//最终的JSONArray
		result.accumulate("result", flag);
		response.getWriter().write(result.toString());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "calculatejuanmian", results = {
			@Result(name = "success", location = "/teacher/combinedGrade/calculatejuanmian.jsp") })
	public String  calcutejuanmian() throws Exception{
		return SUCCESS;
	}

	public ClassTeacherTable getConditioncla() {
		return conditioncla;
	}

	public void setConditioncla(ClassTeacherTable conditioncla) {
		this.conditioncla = conditioncla;
	}

	public ClassTeacherCourseTable getConditioncou() {
		return conditioncou;
	}

	public void setConditioncou(ClassTeacherCourseTable conditioncou) {
		this.conditioncou = conditioncou;
	}

	public ClassCourseSchemeTable getConditionsch() {
		return conditionsch;
	}

	public void setConditionsch(ClassCourseSchemeTable conditionsch) {
		this.conditionsch = conditionsch;
	}

}
