package com.anyview.action.teacher;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ExerciseSchemeContentVO;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeContentVO;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentExerciseAnswerVO;
import com.anyview.entities.StudentSchemeDetailVO;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.commons.BaseManager;
import com.anyview.service.function.ClassCourseManager;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ComprehensiveScore;
import com.anyview.service.function.ProblemChapManager;
import com.anyview.service.function.SchemeManager;
import com.anyview.service.function.ScoreManager;
import com.anyview.service.function.StudentsManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;
import com.anyview.utils.XmlUtil;

@SuppressWarnings("serial")
@Namespace("/teacher/homeworkCorrecting")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class HomeworkCorrectingAction extends CommonAction{
	//日志
	private static final Log log = LogFactory.getLog(HomeworkCorrectingAction.class);
	
	@Autowired
	private SchemeManager schemeManager;
	@Autowired
	private ClassCourseManager classCourseManager;
	@Autowired
	private StudentsManager studentsManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private ScoreManager scoreManager;
	@Autowired
	@Qualifier("baseManager")
	private BaseManager baseManager;
	
	private String jsonStr;

	private Integer pageNum=1;//当前页数
	private Integer numPerPage=20;//页面大小
	
	private SchemeTable scheme = new SchemeTable();
	private StudentTable student = new StudentTable();
	private ScoreTable score = new ScoreTable();
	private ExerciseTable exercise = new ExerciseTable();

	/**
	 * 
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月7日 下午4:10:42
	 */
	@Action(value="index",results={@Result(name="index",location="/teacher/homeworkCorrecting/index.jsp")})
	public String index(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer ctcid = Integer.valueOf(request.getParameter("ctcid"));
		ClassTeacherCourseTable ctc = classCourseManager.getClassTeacherCourseById(ctcid);
		//查询出Class_Course_SchemeTable表中对应的scheme
		List<SchemeTable> schemes = schemeManager.getSchemesByCidCourseIdTid(teacher.getTid(), ctc.getCla().getCid(), ctc.getCourse().getCourseId());
		Pagination<ScoreTable> page = null;
		if(scheme.getVid()!=null){
			page = scoreManager.getStudentScorePage(pageNum,numPerPage,ctc.getCla().getCid(),scheme,student);
		}else{
			page = new Pagination<ScoreTable>();
			page.setContent(new ArrayList<ScoreTable>());
			page.setCurrentPage(pageNum);
			page.setTotalCount(0);
			page.setTotalPageNum(0);
			page.setNumPerPage(numPerPage);
		}
		request.setAttribute("ctcid", ctcid);
		request.setAttribute("vid", scheme.getVid());
		request.setAttribute("schemes", schemes);
		request.setAttribute("sname", student.getSname());
		request.setAttribute("page", page);
		return "index";
	}
	
	@Action(value="view",results={@Result(name="view",location="/teacher/homeworkCorrecting/view.jsp")})
	public String view() throws DocumentException, TipException{
		ScoreTable s = baseManager.getEntityById(ScoreTable.class, score.getId());
		List<ExerciseSchemeContentVO> es =  scoreManager.getStudentExercises(s.getStudent().getSid(), s.getScheme().getVid(),s.getCla().getCid());
		for(ExerciseSchemeContentVO e:es){
			SchemeContentTable sc = e.getSchemeContent();
			SchemeContentVO sv = new SchemeContentVO();
			sv.setSchemeContent(sc);
			sv.setProblemContentVO(XmlUtil.resoveProblem(sc.getProblem()));
			e.setSv(sv);
		}
		request.setAttribute("esc", es);
		return "view";
	}
	
	@Action(value="updateScore")
	public void updateScore() throws IOException{
		try{
			scoreManager.updateScore(exercise.getEid(), exercise.getScore());
			jsonStr = "success";
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			jsonStr = "fail";
		}
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value="commentPage",results={@Result(name="comment",location="/teacher/homeworkCorrecting/comment.jsp")})
	public String commentPage(){
		ExerciseTable ex = baseManager.getEntityById(ExerciseTable.class, exercise.getEid());
		request.setAttribute("exercise", ex);
		return "comment";
	}
	
	@Action(value="updateEcomment")
	public void updateEcomment() throws IOException{
		try{
			scoreManager.updateEcomment(exercise.getEid(), exercise.getEcomment());
			jsonStr = "success";
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			jsonStr = "fail";
		}
		ResponseUtils.outJson(response, jsonStr);
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
	public ScoreTable getScore() {
		return score;
	}

	public void setScore(ScoreTable score) {
		this.score = score;
	}

	public ExerciseTable getExercise() {
		return exercise;
	}

	public void setExercise(ExerciseTable exercise) {
		this.exercise = exercise;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
}
