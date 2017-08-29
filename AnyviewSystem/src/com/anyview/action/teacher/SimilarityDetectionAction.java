package com.anyview.action.teacher;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.SimilarityDetection;

@SuppressWarnings("serial")
@Namespace("/teacher/similarityDetection")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SimilarityDetectionAction extends CommonAction {

	public static final Integer PAGE_SIZE = 20;

	private String jsonStr;
	private String tcRightStr;

	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType;
	private String navTabId;
	private Integer cides = -2;

	private ClassTeacherTable conditioncla = new ClassTeacherTable();
	private ClassTeacherCourseTable conditioncou = new ClassTeacherCourseTable();
	private ClassCourseSchemeTable conditionsch = new ClassCourseSchemeTable();

	@Autowired
	private SimilarityDetection similarityDetection;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "gainSimilarityDetectionPage", results = {
			@Result(name = "success", location = "/teacher/similarityDetection/similarityDetection.jsp") })
	public String gainSimilarityDetectionPage() throws Exception {
		ArrayList<ClassTeacherTable> clas = (ArrayList<ClassTeacherTable>) request.getAttribute("clas");
		if (clas != null)
			for (ClassTeacherTable c : clas)
				System.out.println(c.getCla().getCname());
		ArrayList<ClassTeacherCourseTable> ctc1 = (ArrayList<ClassTeacherCourseTable>) request.getAttribute("courses");
		if (ctc1 != null)
			for (ClassTeacherCourseTable c : ctc1)
				System.out.println(c.getCourse().getCourseName());
		ArrayList<ClassCourseSchemeTable> ccs1 = (ArrayList<ClassCourseSchemeTable>) request.getAttribute("schemes");
		if (ccs1 != null)
			for (ClassCourseSchemeTable s : ccs1)
				System.out.println(s.getScheme().getVname());

		TeacherTable teacher = (TeacherTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);

		List<ClassTeacherTable> clt = similarityDetection.getClassByTId(teacher.getTid());
		if (conditioncla.getCla() != null) {
			System.out.println("conditioncla.getCla() != null " + conditioncla.getCla().getCname());
			List<ClassTeacherCourseTable> ctc = similarityDetection.getCourseByTIdAndClaId(teacher.getTid(),
					conditioncla.getCla().getCid());
			request.setAttribute("courses", ctc);
			if (conditioncou.getCourse() != null) {
				List<ClassCourseSchemeTable> ccs = similarityDetection.getSchemeByClaIdAndCouId(
						conditioncla.getCla().getCid(), conditioncou.getCourse().getCourseId());
				request.setAttribute("schemes", ccs);
			}
		}
		request.setAttribute("clas", clt);

		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("conditioncla", conditioncla);
		param.put("conditioncou", conditioncou);
		param.put("conditionsch", conditionsch);

		if (conditioncla.getCla() != null)
			cides = conditioncla.getCla().getCid();
		request.setAttribute("cides", cides);

		Pagination<SchemeContentTable> page = new Pagination<SchemeContentTable>();
		page = similarityDetection.getSchemeContentPage(param);

		request.setAttribute("page", page);
		request.setAttribute("conditioncla", conditioncla);// 查询条件回显
		request.setAttribute("conditioncou", conditioncou);// 查询条件回显
		request.setAttribute("conditionsch", conditionsch);// 查询条件回显
		return SUCCESS;
}

	/**
	 * 
	 * @Description: TODO(下载答案)
	 * @return
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午7:33:57
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "downloadAnswer", results = { @Result(name = "success", type = "json") })
	public String downloadAnswer() throws Exception {
		String[] ids = request.getParameterValues("ids");
		String cid = request.getParameter("cides");
		Integer intcid = Integer.valueOf(cid);

		String path = request.getRealPath("/") + "teacher/similarityDetection/" + "DownLoad";
//		String path = request.getRealPath("/") + "teacher/similarityDetection/" + "DownLoadOnline";
		if (ids.length > 0)
			similarityDetection.downloadAnswer(intcid, ids, path);

		statusCode = "200";
		message = "打包成功！";
		navTabId = "downloadzip";
		// forwardUrl =
		// "teacher/similarityDetection/gainSimilarityDetectionPage.action";
		// callbackType = "forward";
		return SUCCESS;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getTcRightStr() {
		return tcRightStr;
	}

	public void setTcRightStr(String tcRightStr) {
		this.tcRightStr = tcRightStr;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public static Integer getPageSize() {
		return PAGE_SIZE;
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
