package com.anyview.action.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ChoiceOptionsVO;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemContentVO;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ProblemChapManager;
import com.anyview.service.function.ProblemLibManager;
import com.anyview.service.function.ProblemManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.PathTool;
import com.anyview.utils.TipException;
import com.anyview.utils.XmlUtil;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@Namespace("/teacher/problemManager")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProblemManageAction extends CommonAction {

	// 日志
	private static final Log log = LogFactory.getLog(ProblemManageAction.class);
	// 一页显示数目
	public static final Integer PAGE_SIZE = 20;
	private Integer pageNum;// 当前页数
	private Integer numPerPage;// 页面大小

	@Autowired
	private ProblemManager problemManager;
	@Autowired
	private ProblemLibManager problemLibManager;
	@Autowired
	private ProblemChapManager problemChapManager;

	// 封装jsp参数
	private ProblemLibTable problemLib = new ProblemLibTable();
	private ProblemChapTable problemChap = new ProblemChapTable();
	private ProblemTable problem = new ProblemTable();
	// 封装题目信息
	private ProblemContentVO problemContent = new ProblemContentVO();

	private File importFile;
	private String importFileContentType;
	private String importFileFileName;
	// 返回给页面的json串
	private String jsonStr;

	@Action(value = "getProblemLibsAjax", results = {
			@Result(name = "problemLibs", type = "json", params = { "jsonStr", "${jsonStr}" }) })
	public String gainProblemLibsAjax() {
		TeacherTable teacher = (TeacherTable) session.get("User");
		List<ProblemLibTable> pls = problemLibManager.getAccessableProblemLibs(teacher);
		JSONArray arr = new JSONArray();
		for (ProblemLibTable pl : pls) {
			JSONObject json = new JSONObject();
			json.accumulate("id", -1);// 配合题目目录设置题库ztree的id为-1
			json.accumulate("name", pl.getLname());
			json.accumulate("lid", pl.getLid());
			json.accumulate("isParent", true);
			arr.add(json);
		}
		jsonStr = arr.toString();
		return "problemLibs";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "getNextProblemDirAjax", results = {
			@Result(name = "problemChaps", type = "json", params = { "jsonStr", "${jsonStr}" }) })
	public void gainNextProblemDirAjax() throws IOException {
		TeacherTable teacher = (TeacherTable) session.get("User");
		String lid = request.getParameter("lid");
		String parentId = request.getParameter("parentId");
		Map params = new HashMap();
		params.put("tid", teacher.getTid());
		params.put("lid", lid);
		params.put("parentId", parentId);
		List<Object[]> pcs = problemManager.getProblemChapIN(params);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < pcs.size(); i++) {
			JSONObject json = new JSONObject();
			json.accumulate("id", pcs.get(i)[0]);
			json.accumulate("name", pcs.get(i)[1]);
			json.accumulate("lid", lid);
			json.accumulate("isParent", (Integer.valueOf(pcs.get(i)[2].toString())) > 0 ? true : false);
			arr.add(json);
		}
		jsonStr = arr.toString();
		ResponseUtils.outJson(response, jsonStr);
		// return "problemChaps";
	}

	@Action(value = "getProblemsByChAjax", results = {
			@Result(name = "problems", location = "/teacher/schemeManager/problemTable.jsp") })
	public String gainProblemsByChAjax() {
		String idstr = request.getParameter("ids");
		String kindStr = request.getParameter("kind");
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer pageSize = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		String[] idsArr = idstr.split(";");
		String[][] idsAArr = new String[idsArr.length][2];
		for (int i = 0; i < idsArr.length; i++) {
			String[] temp = idsArr[i].split(",");
			idsAArr[i] = temp;
		}
		// 组合成lid-[]chId的map
		Map<Integer, Set<Integer>> libChMap = new TreeMap<Integer, Set<Integer>>();
		for (int i = 0; i < idsArr.length; i++) {
			Set<Integer> chIdSet = libChMap.get(Integer.valueOf(idsAArr[i][0])) == null ? new HashSet<Integer>()
					: libChMap.get(Integer.valueOf(idsAArr[i][0]));
			chIdSet.add(Integer.valueOf(idsAArr[i][1]));
			libChMap.put(Integer.valueOf(idsAArr[i][0]), chIdSet);
		}
		Integer kind = Integer.valueOf(kindStr);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("libChMap", libChMap);
		params.put("currentPage", pageNum);
		params.put("numPerPage", pageSize);
		params.put("visit", kind);
		Pagination<ProblemTable> page = problemManager.getProblemPageByCh(params);
		request.setAttribute("page", page);
		request.setAttribute("ids", idstr);
		request.setAttribute("kind", kindStr);
		return "problems";
	}

	// 应有好的复用方式代替
	@Action(value = "getProblemsByChTwoAjax", results = {
			@Result(name = "problems", location = "/teacher/schemeManager/problemTableTwo.jsp") })
	public String gainProblemsByChTwoAjax() {
		String idstr = request.getParameter("ids");
		String kindStr = request.getParameter("kind");
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer pageSize = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		String[] idsArr = idstr.split(";");
		String[][] idsAArr = new String[idsArr.length][2];
		for (int i = 0; i < idsArr.length; i++) {
			String[] temp = idsArr[i].split(",");
			idsAArr[i] = temp;
		}
		// 组合成lid-[]chId的map
		Map<Integer, Set<Integer>> libChMap = new TreeMap<Integer, Set<Integer>>();
		for (int i = 0; i < idsArr.length; i++) {
			Set<Integer> chIdSet = libChMap.get(Integer.valueOf(idsAArr[i][0])) == null ? new HashSet<Integer>()
					: libChMap.get(Integer.valueOf(idsAArr[i][0]));
			chIdSet.add(Integer.valueOf(idsAArr[i][1]));
			libChMap.put(Integer.valueOf(idsAArr[i][0]), chIdSet);
		}
		Integer kind = Integer.valueOf(kindStr);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("libChMap", libChMap);
		params.put("currentPage", pageNum);
		params.put("numPerPage", pageSize);
		params.put("visit", kind);
		Pagination<ProblemTable> page = problemManager.getProblemPageByCh(params);
		request.setAttribute("page", page);
		request.setAttribute("ids", idstr);
		request.setAttribute("kind", kindStr);
		return "problems";
	}

	@Action(value = "getProblemLibsINByKindAjax")
	public void gainProblemLibsINByKindAjax() throws IOException {
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer kind = Integer.valueOf(request.getParameter("kind"));
		jsonStr = problemManager.getProblemLibsINByKind(teacher, kind);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "lookProblem", results = {
			@Result(name = "problemDetail", location = "/teacher/schemeManager/problemDetail.jsp") })
	public String lookProblem() {
		Integer pid = Integer.valueOf(request.getParameter("pid"));
		ProblemTable pro = problemManager.getProblemByPid(pid);
		String proDir = "";
		ProblemChapTable chap = pro.getProblemChap();
		while (chap.getChId() != -1) {
			proDir += chap.getChName() + "/";
			chap = chap.getParentChap();
		}
		request.setAttribute("problem", pro);
		request.setAttribute("proDir", proDir);
		return "problemDetail";
	}

	@Action(value = "getProblemsByPidAjax")
	public void gainProblemsByPidAjax() throws IOException {
		String idstr = request.getParameter("ids");
		String[] idstrArr = idstr.split(",");
		Integer[] ids = new Integer[idstrArr.length];
		for (int i = 0; i < idstrArr.length; i++) {
			ids[i] = Integer.valueOf(idstrArr[i]);
		}
		List<ProblemTable> pros = problemManager.getProblemsByPids(ids);
		jsonStr = ResponseUtils.getProblemBaseJson(pros);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "getProblemsByPid", results = {
			@Result(name = "choosedProblemTable", location = "/teacher/schemeManager/choosedProblemTable.jsp") })
	public String gainProblemsByPid() throws IOException {
		String idstr = request.getParameter("ids");
		String[] idstrArr = idstr.split(",");
		Integer[] ids = new Integer[idstrArr.length];
		for (int i = 0; i < idstrArr.length; i++) {
			ids[i] = Integer.valueOf(idstrArr[i]);
		}
		List<ProblemTable> pros = problemManager.getProblemsByPids(ids);
		request.setAttribute("choosedPros", pros);
		return "choosedProblemTable";
	}

	@Action(value = "lookProblemAjax", results = { @Result(name = "problemDetailAjax", type = "json") })
	public void lookProblemAjax() throws IOException {
		Integer pid = Integer.valueOf(request.getParameter("pid"));
		ProblemTable pro = problemManager.getProblemByPid(pid);
		JSONObject json = new JSONObject();
		json.accumulate("proContent", pro.getPcontent());
		json.accumulate("proMemo", pro.getPmemo());
		json.accumulate("proTip", pro.getPtip());
		jsonStr = json.toString();
		ResponseUtils.outJson(response, jsonStr);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "problemAndChapManager", results = {
			@Result(name = "problemAndChapManager", location = "/teacher/problemLibManage/problemAndChapManage/problemAndChapManager.jsp") })
	public String problemAndChapManager() {
		String kind = request.getParameter("kind");
		if ("pre".equals(kind)) {
			problemChap.setParentChap(problemChapManager.getParentChap(problemChap.getParentChap().getChId()));
		}
		// 查询出目录列表和题目列表
		Map param = new HashMap();
		param.put("currentPage", 1);
		param.put("numPerPage", PAGE_SIZE);
		param.put("chap", problemChap);
		problem.setProblemChap(problemChap);
		param.put("orderField", "updateTime");
		param.put("orderDirection", "desc");
		Pagination<ProblemChapTable> chapPage = problemChapManager.getProblemChapPageByLid(param);
		problem.setProblemChap(problem.getProblemChap().getParentChap());
		param.put("problem", problem);
		Pagination<ProblemTable> problemPage = problemManager.getProblemPageByChId(param);
		request.setAttribute("chapPage", chapPage);
		request.setAttribute("chap", problemChap);
		request.setAttribute("problemPage", problemPage);
		request.setAttribute("problem", problem);
		return "problemAndChapManager";
	}

	/**
	 * 列出目录和题目，不分页
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月31日 下午10:58:47
	 */
	@Action(value = "listChapAndProblem", results = {
			@Result(name = "listChapAndProblem", location = "/teacher/problemLibManage/problemAndChapManage/listChapAndProblem.jsp") })
	public String listChapAndProblem() {
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer lid = problemChap.getProblemLib().getLid();
		Integer chId = problemChap.getChId();
		ProblemChapTable parentChap = problemChapManager.getParentChap(chId);
		ProblemLibTable currLib = problemLibManager.getProblemLibByLid(lid);
		if (currLib.getTeacher().getTid().intValue() == teacher.getTid().intValue())
			request.setAttribute("isOwn", true);
		else
			request.setAttribute("isOwn", false);
		if (chId.intValue() == -1) {// 根目录
			List<ProblemChapTable> chaps = problemChapManager.getFirstChapsByLib(lid, teacher.getTid());
			request.setAttribute("chaps", chaps);
		} else {// 非根目录
			List<ProblemChapTable> chaps = problemChapManager.getAccessChapByParentId(chId, teacher.getTid());
			List<ProblemTable> problems = problemManager.getAccessProblemsByChId(chId, teacher.getTid());
			request.setAttribute("chaps", chaps);
			request.setAttribute("pros", problems);
		}
		request.setAttribute("lid", lid);
		request.setAttribute("chId", chId);
		request.setAttribute("parentId", parentChap.getChId());
		return "listChapAndProblem";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "problemChapManager", results = {
			@Result(name = "problemChapManager", location = "/teacher/problemLibManage/problemAndChapManage/problemChapManager.jsp") })
	public String problemChapManager() {
		Map param = new HashMap();
		param.put("currentPage", pageNum == null ? 1 : pageNum);
		param.put("numPerPage", numPerPage == null ? PAGE_SIZE : numPerPage);
		param.put("chap", problemChap);
		orderField = (orderField == null || "".equals(orderField)) ? "updateTime" : orderField;// 默认按更新时间
		orderDirection = (orderDirection == null || "".equals(orderDirection)) ? "desc" : orderDirection;// 默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ProblemChapTable> chapPage = problemChapManager.getProblemChapPageByLid(param);
		request.setAttribute("chapPage", chapPage);
		request.setAttribute("chap", problemChap);
		return "problemChapManager";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value = "problemManager", results = {
			@Result(name = "problemManager", location = "/teacher/problemLibManage/problemAndChapManage/problemManager.jsp") })
	public String problemManager() {
		Map param = new HashMap();
		param.put("currentPage", pageNum == null ? 1 : pageNum);
		param.put("numPerPage", numPerPage == null ? PAGE_SIZE : numPerPage);
		param.put("problem", problem);
		orderField = (orderField == null || "".equals(orderField)) ? "updateTime" : orderField;// 默认按更新时间
		orderDirection = (orderDirection == null || "".equals(orderDirection)) ? "desc" : orderDirection;// 默认降序
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ProblemTable> problemPage = problemManager.getProblemPageByChId(param);
		request.setAttribute("problemPage", problemPage);
		request.setAttribute("problem", problem);
		return "problemManager";
	}

	@Action(value = "saveProblemChap")
	public void saveProblemChap() throws IOException {
		try {
			problemChapManager.saveProblemChap(problemChap);
			jsonStr = AjaxObject.newOk("保存成功!").toString();
		} catch (TipException t) {
			log.debug("保存目录失败-->" + t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value = "deleteProblemChap")
	public void deleteProblemChap() throws IOException {
		String update_info = request.getParameter("update_info");
		String[] strArr = update_info.split(" ");
		Integer value = Integer.valueOf(strArr[0]);
		String type = strArr[1];
		//删除题目
		if ("pid".equals(type)) {
			System.out.println("删除题目");
			try {
				problem.setPid(value);
				problemManager.deleteProblem(problem);
				// jsonStr =
				// AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD)
				// .setForwardUrl("teacher/problemManager/problemAndChapManager.action?problemChap.problemLib.lid="+problemChap.getProblemLib().getLid()+"&kind=next&problemChap.parentChap.chId="+problemChap.getParentChap().getChId()).toString();
				jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("删除题目错误：" + e.getMessage());
				jsonStr = AjaxObject.newError("系统错误").toString();
			} finally {
				ResponseUtils.outJson(response, jsonStr);
			}
		}else{	//删除目录
			System.out.println("删除目录");
			problemChap.setChId(value);
			try {
				problemChapManager.deleteProblemChap(problemChap);
				// jsonStr =
				// AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD)
				// .setForwardUrl("teacher/problemManager/problemAndChapManager.action?problemChap.problemLib.lid="+problemChap.getProblemLib().getLid()+"&kind=next&problemChap.parentChap.chId="+problemChap.getParentChap().getChId()).toString();
				jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
			} catch (TipException t) {
				log.debug("删除目录失败-->" + t.getMessage());
				jsonStr = AjaxObject.newError(t.getMessage()).toString();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				jsonStr = AjaxObject.newError("系统错误").toString();
			} finally {
				ResponseUtils.outJson(response, jsonStr);
			}
		}
		
	}

	@Action(value = "editProblemChap", results = {
			@Result(name = "editProblem", location = "/teacher/problemLibManage/problemAndChapManage/editProblem.jsp"),
			@Result(name = "editProblemChap", location = "/teacher/problemLibManage/problemAndChapManage/editProblemChap.jsp")

	})
	public String editProblemChap() {
		String update_info = request.getParameter("update_info");
		String[] strArr = update_info.split(" ");
		Integer value = Integer.valueOf(strArr[0]);
		String type = strArr[1];
		//修改题目
		if ("pid".equals(type)) {
			ProblemTable p = problemManager.getProblemByPid(value);
			ProblemContentVO pc = null;
			try {
				pc = XmlUtil.resoveProblemContent(p.getPcontent(), p.getKind());
				p.setPtip(XmlUtil.resoveProblemTipXML(p.getPtip()));
			} catch (DocumentException e) {
				e.printStackTrace();
				log.error("解析xml出错:" + e.getMessage());
			}
			request.setAttribute("problem", p);
			request.setAttribute("problemContent", pc);
			return "editProblem";
		} else {	//修改目录
			ProblemChapTable chap = problemChapManager.getProblemChapById(value);
			request.setAttribute("problemChap", chap);
			return "editProblemChap";
		}
	}

	@Action(value = "updateProblemChap")
	public void updateProblemChap() throws IOException {
		try {
			problemChapManager.updateProblemChap(problemChap);
			jsonStr = AjaxObject.newOk("修改成功!").setNavTabId("").toString();
		} catch (TipException t) {
			log.debug("更新目录失败-->" + t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新目录异常!with : " + e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value = "saveProblem")
	public void saveProblem() throws IOException {
		try {
			if (problem.getKind() == 3) {// 单选题获取正确选项
				String singleRightOpt = request.getParameter("singleChoiceRadio");
				for (ChoiceOptionsVO c : problemContent.getSingleOptions()) {
					if (singleRightOpt.equals(c.getSequence()))
						c.setIsRight(true);
					else
						c.setIsRight(false);
				}
			} else if (problem.getKind() == 4) {// 多选题获取正确选项
				String[] multipleRightOpt = request.getParameterValues("rightMultipleOpt");
				List<String> optList = Arrays.asList(multipleRightOpt);
				for (ChoiceOptionsVO c : problemContent.getMultipleOptions()) {
					if (optList.contains(c.getSequence()))
						c.setIsRight(true);
					else
						c.setIsRight(false);
				}
			}
			problemManager.saveProblem(problem, problemContent);
			jsonStr = AjaxObject.newOk("保存成功!").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存题目异常!with : " + e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value = "deleteProblem")
	public void deleteProblem() throws IOException {
		try {
			problemManager.deleteProblem(problem);
			// jsonStr =
			// AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD)
			// .setForwardUrl("teacher/problemManager/problemAndChapManager.action?problemChap.problemLib.lid="+problemChap.getProblemLib().getLid()+"&kind=next&problemChap.parentChap.chId="+problemChap.getParentChap().getChId()).toString();
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("删除题目错误：" + e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value = "editProblem", results = {
			@Result(name = "editProblem", location = "/teacher/problemLibManage/problemAndChapManage/editProblem.jsp") })
	public String editProblem() {
		ProblemTable p = problemManager.getProblemByPid(problem.getPid());
		ProblemContentVO pc = null;
		try {
			pc = XmlUtil.resoveProblemContent(p.getPcontent(), p.getKind());
			p.setPtip(XmlUtil.resoveProblemTipXML(p.getPtip()));
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("解析xml出错:" + e.getMessage());
		}
		request.setAttribute("problem", p);
		request.setAttribute("problemContent", pc);
		return "editProblem";
	}

	@Action(value = "updateProblem")
	public void updateProblem() throws IOException {
		try {
			if (problem.getKind() == 3) {// 单选题获取正确选项
				String singleRightOpt = request.getParameter("singleChoiceRadio");
				for (ChoiceOptionsVO c : problemContent.getSingleOptions()) {
					if (singleRightOpt.equals(c.getSequence()))
						c.setIsRight(true);
					else
						c.setIsRight(false);
				}
			} else if (problem.getKind() == 4) {// 多选题获取正确选项
				String[] multipleRightOpt = request.getParameterValues("rightMultipleOpt");
				List<String> optList = Arrays.asList(multipleRightOpt);
				for (ChoiceOptionsVO c : problemContent.getMultipleOptions()) {
					if (optList.contains(c.getSequence()))
						c.setIsRight(true);
					else
						c.setIsRight(false);
				}
			}
			problemManager.updateProblem(problem, problemContent);
			jsonStr = AjaxObject.newOk("更新成功!").toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新题目异常!with : " + e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value = "importProblems")
	public void importProblems() throws IOException {
		FileInputStream fin = null;
		try {
			Integer type = Integer.valueOf(request.getParameter("type"));
			fin = new FileInputStream(importFile);
			int result = problemManager.importProblemFromExcel(fin, problemLib.getLid(), problemChap.getChId(), type);
			if (result > 0)
				jsonStr = AjaxObject.newOk("成功导入" + result + "条数据").setNavTabId("").setCallbackType("").toString();
			else
				jsonStr = AjaxObject.newError("没有数据被导入").toString();
		} catch (TipException t) {
			log.debug(t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		} catch (FileNotFoundException e) {
			log.error("找不到上传的临时文件:" + e.getMessage());
			jsonStr = AjaxObject.newError("导入失败，找不到上传的临时文件").toString();
		} catch (Exception e) {
			log.error("导入文件错误：" + e.getMessage());
			jsonStr = AjaxObject.newError("导入失败，导入文件错误").toString();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				log.error("关闭流IO异常:" + e.getMessage());
			} finally {
				ResponseUtils.outJson(response, jsonStr);
			}
		}
	}

	@Action(value = "libContents", results = {
			@Result(name = "libContents", location = "/teacher/schemeManager/libContents.jsp") })
	public String libContents() {
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer lid = problemChap.getProblemLib().getLid();
		Integer chId = problemChap.getChId();
		ProblemChapTable parentChap = problemChapManager.getParentChap(chId);
		if (chId.intValue() == -1) {// 根目录
			List<ProblemChapTable> chaps = problemChapManager.getFirstChapsByLib(lid, teacher.getTid());
			request.setAttribute("chaps", chaps);
		} else {// 非根目录
			List<ProblemChapTable> chaps = problemChapManager.getAccessChapByParentId(chId, teacher.getTid());
			List<ProblemTable> problems = problemManager.getAccessProblemsByChId(chId, teacher.getTid());
			request.setAttribute("chaps", chaps);
			request.setAttribute("pros", problems);
		}
		request.setAttribute("lid", lid);
		request.setAttribute("chId", chId);
		request.setAttribute("parentId", parentChap.getChId());
		return "libContents";
	}

	@Action(value = "getProblemContentJson")
	public void getProblemContentJson() throws IOException {
		try {
			ProblemTable sp = problemManager.getProblemByPid(problem.getPid());
			ProblemContentVO pvo = XmlUtil.resoveProblem(sp);
			jsonStr = pvo.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取题目json错误");
			jsonStr = "ERROR";
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@JSON(name = "testJson")
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	@JSON(serialize = false)
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	@JSON(serialize = false)
	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	@JSON(serialize = false)
	public ProblemLibTable getProblemLib() {
		return problemLib;
	}

	public void setProblemLib(ProblemLibTable problemLib) {
		this.problemLib = problemLib;
	}

	@JSON(serialize = false)
	public ProblemChapTable getProblemChap() {
		return problemChap;
	}

	public void setProblemChap(ProblemChapTable problemChap) {
		this.problemChap = problemChap;
	}

	@JSON(serialize = false)
	public ProblemTable getProblem() {
		return problem;
	}

	public void setProblem(ProblemTable problem) {
		this.problem = problem;
	}

	@JSON(serialize = false)
	public ProblemContentVO getProblemContent() {
		return problemContent;
	}

	public void setProblemContent(ProblemContentVO problemContent) {
		this.problemContent = problemContent;
	}

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public String getImportFileContentType() {
		return importFileContentType;
	}

	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}

	public String getImportFileFileName() {
		return importFileFileName;
	}

	public void setImportFileFileName(String importFileFileName) {
		this.importFileFileName = importFileFileName;
	}

}
