/**   
* @Title: ProblemLibManageAction.java 
* @Package com.anyview.action.teacher 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月9日 下午9:59:18 
* @version V1.0   
*/
package com.anyview.action.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.anyview.entities.CollegeTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.ProblemChapManager;
import com.anyview.service.function.ProblemLibManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

/** 
 * @ClassName: ProblemLibManageAction 
 * @Description: TODO(题库管理Action) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年9月9日 下午9:59:18 
 *  
 */
@SuppressWarnings("serial")
@Namespace("/teacher/problemLibManager")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ProblemLibManageAction  extends CommonAction{
	//日志
	private static final Log log = LogFactory.getLog(ProblemLibManageAction.class);
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	private Integer pageNum;//当前页数
	private Integer numPerPage;//页面大小
	
	@Autowired
	private ProblemLibManager problemLibManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CollegeManager collegeManager;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private ProblemChapManager problemChapManager;
	
	private ProblemLibTable problemLib = new ProblemLibTable();
	private Integer unId;
	private Integer ceId;
	private String jsonStr;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="gainProblemLibManagerPage",results={@Result(name="problemLibPage",location="/teacher/problemLibManage/problemLibManager.jsp")})
	public String gainProblemLibManagerPage(){
		//只显示自己创建的题库
		TeacherTable teacher = (TeacherTable) session.get("User");
		Map param = new HashMap();
		param.put("currentPage", pageNum==null?1:pageNum);
		param.put("numPerPage", numPerPage==null?PAGE_SIZE:numPerPage);
		param.put("teacher", teacher);
		param.put("problemLib", problemLib);
		orderField = (orderField==null || "".equals(orderField))?"updateTime":orderField;//默认按更新时间
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"desc":orderDirection;//默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ProblemLibTable> page = problemLibManager.getProblemLibPage(param);
		request.setAttribute("page", page);
		request.setAttribute("teacher", teacher);
		//回显
		request.setAttribute("criteria", problemLib);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return "problemLibPage";
	}
	
	@Action(value="gainTeacherByCeIdAjax")
	public void gainTeacherByCeIdAjax() throws IOException{
		Integer ceId = Integer.valueOf(request.getParameter("ceId"));
		List<Object[]> tes = teacherManager.getEnabledTeacherByCeId(ceId);
		jsonStr = ResponseUtils.getIdAndTextJson(tes);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value="saveProblemLib")
	public void saveProblemLib() throws IOException{
		try{
			TeacherTable teacher = (TeacherTable) session.get("User");
			problemLib.setTeacher(teacher);
			problemLib.setUniversity(teacher.getUniversity());
			String tidstr = request.getParameter("accessTeaIds");
			problemLibManager.saveProblemLib(problemLib, tidstr);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("problemLibManager").toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("保存题库失败!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("保存失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
		
	}
	
	@Action(value="accessTeachers",results={@Result(name="accessTeachers",location="/teacher/problemLibManage/accessTeachers.jsp")})
	public String accessTeachers(){
		Integer lid = Integer.valueOf(request.getParameter("lid"));
		ProblemLibTable lib = problemLibManager.getProblemLibByLid(lid);
		if(lib.getVisit() == 1){
			List<TeacherTable> accTeas = problemLibManager.getAccessableTeachers(lid);
			request.setAttribute("teachers", accTeas);
		}
		request.setAttribute("problemLib", lib);
		return "accessTeachers";
	}
	
	@Action(value="editProblemLib",results={@Result(name="editProblemLib",location="/teacher/problemLibManage/editProblemLib.jsp")})
	public String editProblemLib(){
		ProblemLibTable lib = problemLibManager.getProblemLibByLid(problemLib.getLid());
		request.setAttribute("problemLib", lib);
		return "editProblemLib";
	}
	
	@Action(value="updateProblemLib")
	public void updateProblemLib() throws IOException{
		try{
			String tidstr = request.getParameter("accessTeaIds");
			problemLibManager.updateProblemLib(problemLib, tidstr);
			jsonStr = AjaxObject.newOk("修改成功!").setNavTabId("problemLibManager").toString();
		} catch (Exception e){
			e.printStackTrace();
			log.error("更新题库失败!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("更新失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="deleteProblemLib")
	public void deleteProblemLib() throws IOException{
		try{
			problemLibManager.deleteProblemLib(problemLib);
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("teacher/problemLibManager/gainProblemLibManagerPage.action").toString();
		}catch (TipException t){
			log.error("删除失败!with : "+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除失败!with : "+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 查询教师可访问的题库
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午7:37:26
	 */
	@Action(value="accessLibs",results={@Result(name="accessLibs",location="/teacher/schemeManager/accessLibs.jsp")})
	public String accessLibs(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		List<ProblemLibTable> libs = problemLibManager.searchAccessLibs(unId, ceId, teacher);
		Set<ProblemLibTable> set = new HashSet<ProblemLibTable>();
		set.addAll(libs);
		request.setAttribute("libs", set);
		return "accessLibs";
	}
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public ProblemLibTable getProblemLib() {
		return problemLib;
	}
	public void setProblemLib(ProblemLibTable problemLib) {
		this.problemLib = problemLib;
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
}
