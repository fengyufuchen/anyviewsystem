package com.anyview.action.teacher;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.admin.AdminBaseAction;
import com.anyview.action.common.CommonAction;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemContentVO;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeContentVO;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.ProblemLibManager;
import com.anyview.service.function.ProblemManager;
import com.anyview.service.function.SchemeManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;
import com.anyview.utils.XmlUtil;

@SuppressWarnings("serial")
@Namespace("/teacher/schemeManager")
@ParentPackage("teacherBasePkg")
@Action(results={
		@Result(name="paramsErrorPage" ,location="/paramsErrorPage.jsp")
})
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class SchemeManageAction extends AdminBaseAction{
	//日志
	private static final Log log = LogFactory.getLog(SchemeManageAction.class);
	//默认一页显示数目
	public static final Integer PAGE_SIZE = 20;
	public static final Integer AJAX_SUCCESS = 1;//success
	public static final Integer AJAX_ERROR = 2;//error
	public static final Integer AJAX_EXCEPTION = 3;//tipException
	private Integer ajaxStatus;//返回ajax状态码,参照上面定义的常量
	private String errorMsg;//返回错误信息
	
	@Autowired
	private SchemeManager schemeManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CollegeManager collegeManager;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private ProblemLibManager problemLibManager;
	@Autowired
	private ProblemManager problemManager;
	
	private SchemeTable scheme = new SchemeTable();//封装查询参数
	private SchemeContentTable schemeContent = new SchemeContentTable();
	private Integer unId;
	private Integer ceId;
	private Integer courseId;
	private Integer pid;
	private String jsonStr;//传给Jsp的json串
	
	private Integer pageNum;//当前页数
	private Integer numPerPage;//页面大小
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainSchemeManagerPage",results={@Result(name="success",location="/teacher/schemeManager/schemeManager.jsp")})
	public String gainSchemeManagerPage()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		Map param = new HashMap();
		param.put("currentPage", pageNum==null?1:pageNum);
		param.put("numPerPage", numPerPage==null?PAGE_SIZE:numPerPage);
		param.put("teacher", teacher);
		param.put("scheme", scheme);
		orderField = (orderField==null || "".equals(orderField))?"updateTime":orderField;//默认按更新时间
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"desc":orderDirection;//默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<SchemeTable> page = schemeManager.getSchemePage(param);
		request.setAttribute("page", page);
		request.setAttribute("teacher", teacher);
		//回显
		request.setAttribute("criteria", scheme);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainCollegeByUnIdAjax")
	public void gainCollegeByUnIdAjax()throws Exception{
		try{
			String unIDStr = request.getParameter("unID");
			Integer unID = (unIDStr==null||"".equals(unIDStr))?null:Integer.valueOf(unIDStr);
			Map param = new HashMap();
			param.put("unID", unID);
			Set<CollegeTable> cols = collegeManager.getColleges(param);
			jsonStr = ResponseUtils.getCollegeIdAndNameJson(cols);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
//			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainTeacherByCeIdAjax")
	public void gainTeacherByCeIdAjax()throws Exception{
		try{
			String ceIDStr = request.getParameter("ceID");
			Integer ceID = (ceIDStr==null||"".equals(ceIDStr))?null:Integer.valueOf(ceIDStr);
			Map param = new HashMap();
			param.put("ceID", ceID);
			Set<TeacherTable> teas = teacherManager.getTeachers(param);
			jsonStr = ResponseUtils.getTeacherIdAndNameJson(teas);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
//			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="lookSchemeProblems",results={@Result(name="listSchemeProblems",location="/teacher/schemeManager/listSchemeProblems.jsp")})
	public String lookSchemeProblems()throws Exception{
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		Map params = new HashMap();
		params.put("currentPage", pageNum==null?1:pageNum);
		params.put("numPerPage", numPerPage==null?PAGE_SIZE:numPerPage);
		params.put("vid", vid);
		Pagination<SchemeContentTable> page = schemeManager.getSchemeProblemsPage(params);
		request.setAttribute("page", page);
		request.setAttribute("vid", vid);
		return "listSchemeProblems";
	}
	
	@Action(value="viewSchemeContent",results={@Result(name="viewSchemeContent",location="/teacher/schemeManager/viewSchemeContent.jsp")})
	public String viewSchemeContent()throws Exception{
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		List<SchemeContentTable> list = schemeManager.getSchemeContentList(vid);
		request.setAttribute("list", list);
		request.setAttribute("vid", vid);
		return "viewSchemeContent";
	}
	
	@Action(value="addScheme",results={@Result(name="addScheme",location="/teacher/schemeManager/addScheme.jsp")})
	public String addScheme()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		//只有在Class_Teacher_CourseTable中有设置作业表权限的课程才能添加作业表
		List<CourseTable> courses = courseManager.getCourseByTidAndRight(teacher,2);
		request.setAttribute("course", courses);
		return "addScheme";
	}
	
	@Action(value="newScheme",results={@Result(name="newScheme",location="/teacher/schemeManager/newScheme.jsp")})
	public String newScheme()throws Exception{
		TeacherTable teacher = (TeacherTable) session.get("User");
		//只有在Class_Teacher_CourseTable中有设置作业表权限的课程才能添加作业表
		List<CourseTable> courses = courseManager.getCourseByTidAndRight(teacher,2);
		request.setAttribute("course", courses);
		return "newScheme";
	}
	
	@Action(value="gainCollegeINByUnIdAjax",results={@Result(name="json",type="json")})
	public String gainCollegeINByUnIdAjax(){
		try{
			Integer unID = Integer.valueOf(request.getParameter("id"));
			List<Object[]> cols = collegeManager.getCollegesINByUnId(unID);
			jsonStr = ResponseUtils.getIdAndNameJson(cols);
			ajaxStatus = AJAX_SUCCESS;
		}catch(TipException t){
			ajaxStatus = AJAX_EXCEPTION;
			errorMsg = t.getMessage();
			log.debug(t.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			ajaxStatus = AJAX_ERROR;
			log.error(e.getMessage());
		}
		return "json";
	}
	@Action(value="gainCourseINByCeIDAjax",results={@Result(name="json",type="json")})
	public String gainCourseINByCeIDAjax(){
		try{
			Integer ceID = Integer.valueOf(request.getParameter("id"));
			List<Object[]> cous = courseManager.getCourseINByCeId(ceID);
			jsonStr = ResponseUtils.getIdAndNameJson(cous);
			ajaxStatus = AJAX_SUCCESS;
		}catch(TipException t){
			ajaxStatus = AJAX_EXCEPTION;
			errorMsg = t.getMessage();
			log.debug(t.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			ajaxStatus = AJAX_ERROR;
			log.error(e.getMessage());
		}
		return "json";
	}
	@Action(value="gainTeacherINByCourseIdAjax",results={@Result(name="json",type="json")})
	public String gainTeacherINByCourseIdAjax(){
		try{
			Integer courseId = Integer.valueOf(request.getParameter("id"));
			List<Object[]> teas = teacherManager.getTeacherINByCourseId(courseId);
			jsonStr = ResponseUtils.getIdAndNameJson(teas);
			ajaxStatus = AJAX_SUCCESS;
		}catch(TipException t){
			ajaxStatus = AJAX_EXCEPTION;
			errorMsg = t.getMessage();
			log.debug(t.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			ajaxStatus = AJAX_ERROR;
			log.error(e.getMessage());
		}
		return "json";
	}
	
	@Action(value="addProblemForScheme",results={@Result(name="addProblemForScheme",location="/teacher/schemeManager/addSchemeProblems.jsp")})
	public String addProblemForScheme(){
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		SchemeTable scheme = schemeManager.getSchemeByVid(vid);
		request.setAttribute("scheme", scheme);
		return "addProblemForScheme";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="saveNewScheme")
	public void saveNewScheme() throws IOException{
		TeacherTable teacher = (TeacherTable) session.get("User");
		String msg = request.getParameter("schemeProMsg");
		String []proMsgs = msg.split(";");
		//id,newpname,newpdir,starttime,finishtime,score
		String [][] scs = new String[proMsgs.length][];
		for(int i=0;i<proMsgs.length;i++){
			String []temp = proMsgs[i].split(",");
			scs[i]=temp;
		}
		Map params = new HashMap();
		params.put("teacher", teacher);
		params.put("scheme", scheme);
		params.put("scs", scs);
		try {
			schemeManager.saveNewScheme(params);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("schemeManager").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存作业表失败:"+e.getMessage());
			jsonStr = AjaxObject.newError("保存失败").toString();
		} finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="editScheme",results={@Result(name="editScheme",location="/teacher/schemeManager/editScheme.jsp")})
	public String editScheme(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		SchemeTable s = schemeManager.getSchemeByVid(vid);
		List<CourseTable> courses = courseManager.getCourseByTidAndRight(teacher,2);
		request.setAttribute("scheme", s);
		request.setAttribute("courses", courses);
		return "editScheme";
	}
	
	@Action(value="updateScheme")
	public void updateScheme() throws IOException{
		try {
			schemeManager.updateScheme(scheme);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("schemeManager").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新作业表失败:"+e.getMessage());
			jsonStr = AjaxObject.newError("更改失败").toString();
		} finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value="getSchemeProblemsAjax")
	public void getSchemeProblemsAjax() throws IOException{
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		List<SchemeContentTable> scList = schemeManager.getSchemeContentList(vid);
		jsonStr = ResponseUtils.getSchemeContentJson(scList);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="updateSchemePros")
	public void updateSchemePros() throws IOException{
		String msg = request.getParameter("schemeProMsg");
		String []proMsgs = msg.split(";");
		//id,newpname,newpdir,starttime,finishtime,score
		String [][] scs = new String[proMsgs.length][];
		for(int i=0;i<proMsgs.length;i++){
			String []temp = proMsgs[i].split(",");
			scs[i]=temp;
		}
		Map params = new HashMap();
		params.put("scheme", scheme);
		params.put("scs", scs);
		try {
			schemeManager.updataSchemePros(params);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("lookSchemeProblems").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存作业表失败:"+e.getMessage());
			jsonStr = AjaxObject.newError("保存失败").toString();
		} finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="editSchemeContent",results={@Result(name="editSchemeContent",location="/teacher/schemeManager/modifyProNameAndVDipThree.jsp")})
	public String editSchemeContent(){
		Integer Id = Integer.valueOf(request.getParameter("id"));
		SchemeContentTable sc = schemeManager.getSchemeContentById(Id);
		request.setAttribute("sc", sc);
		return "editSchemeContent";
	}
	
	@Action(value="updateSchemeContent")
	public void updateSchemeContent() throws IOException{
		try{
			schemeManager.updateSchemeContent(schemeContent);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("lookSchemeProblems").toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("更新schemeContent失败 with:"+e.getMessage());
			jsonStr = AjaxObject.newError("保存失败").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value="deleteSchemeProblem")
	public void deleteSchemeProblem() throws IOException{
		try{
			schemeManager.deleteSchemeContent(schemeContent);
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/schemeManager/lookSchemeProblems.action?vid="+schemeContent.getScheme().getVid()).toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除schemeContent失败 with:"+e.getMessage());
			jsonStr = AjaxObject.newError("删除失败").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="accessSchemes",results={@Result(name="accessSchemes",location="/teacher/schemeManager/accessSchemes.jsp")})
	public String accessSchemes(){
		if(unId==null)
			return "paramsErrorPage";
		TeacherTable teacher=(TeacherTable) session.get("User");
		List<SchemeTable> schemes = schemeManager.getSchemeById(unId,ceId,courseId,teacher);
		Set<SchemeTable> set = new HashSet<SchemeTable>();
		set.addAll(schemes);
		request.setAttribute("schemes", set);
		return "accessSchemes";
	}
	
	@Action(value="schemeContents",results={@Result(name="schemeContents",location="/teacher/schemeManager/schemeContents.jsp")})
	public String schemeContents(){
		try {
			Integer vid = Integer.valueOf(request.getParameter("vid"));
			if(vid==null)
				throw new TipException("请选择一个作业表");
			List<SchemeContentTable> list = schemeManager.getSchemeContentList(vid);
			Set<SchemeContentVO> contents = XmlUtil.resoveSchemeContents(list);
			request.setAttribute("contents", contents);
		} catch (TipException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (DocumentException e) {
			log.error("解析xml错误"+e.getMessage());
			e.printStackTrace();
		}
		return "schemeContents";
	}
	
	/**
	 * 保存作业表
	 * 
	 * @author 何凡 <piaobo749@qq.com>
	 * @throws IOException 
	 * @date 2016年8月6日 上午10:32:58
	 */
	@Action(value="saveNScheme")
	public void saveNScheme() throws IOException{
		String msg = "";
		try{
			TeacherTable teacher=(TeacherTable) session.get("User");
			String data = request.getParameter("nsSchemeMsg");
			String []params = data.split("\\|");
			if(params.length<=1){
				msg = "请添加题目";
			}else{
				String []schemeargs = params[0].split(",");
				SchemeTable newscheme = new SchemeTable();
				newscheme.setTeacher(teacher);
				newscheme.setVname(schemeargs[0].trim());
				newscheme.setCourse(new CourseTable(Integer.valueOf(schemeargs[1].trim())));
				newscheme.setKind(Integer.valueOf(schemeargs[2].trim()));
				newscheme.setStatus(SchemeTable.STATUS_TEST);//新添加作业表默认测试状态
				newscheme.setFullScore(Float.valueOf(schemeargs[3].trim()));
				newscheme.setCreateTime(new Timestamp(System.currentTimeMillis()));
				newscheme.setVisit(SchemeTable.VISIT_PRIVATE);//默认私有
				List<SchemeContentTable> scs = new ArrayList<SchemeContentTable>();
				for(int i=1;i<params.length;i++){
					String []scargs = params[i].split(",");
					SchemeContentTable tempsc = new SchemeContentTable();
					tempsc.setProblem(new ProblemTable(Integer.valueOf(scargs[0].trim())));
					tempsc.setVpName(scargs[1].trim());
					tempsc.setVpName(scargs[2].trim());
					tempsc.setScore(Float.valueOf(scargs[3].trim()));
					tempsc.setStartTime(Timestamp.valueOf(scargs[4].trim()+":00"));
					tempsc.setFinishTime(Timestamp.valueOf(scargs[5].trim()+":00"));
					tempsc.setStatus(SchemeContentTable.STATUS_ENABLE);//默认启用
				}
				schemeManager.saveNScheme(newscheme, scs);
				msg = "添加成功";
				jsonStr = AjaxObject.newOk(msg).setNavTabId("schemeManager").toString();
			}
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			jsonStr = AjaxObject.newError(msg).toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
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
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	@JSON
	public Integer getAjaxStatus() {
		return ajaxStatus;
	}
	public void setAjaxStatus(Integer ajaxStatus) {
		this.ajaxStatus = ajaxStatus;
	}
	@JSON
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public SchemeContentTable getSchemeContent() {
		return schemeContent;
	}
	public void setSchemeContent(SchemeContentTable schemeContent) {
		this.schemeContent = schemeContent;
	}
	public Integer getUnId() {
		return unId;
	}
	public void setUnId(Integer unId) {
		this.unId = unId;
	}
	public Integer getCeId() {
		return ceId;
	}
	public void setCeId(Integer ceId) {
		this.ceId = ceId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
