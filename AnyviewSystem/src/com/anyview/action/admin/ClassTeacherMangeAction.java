package com.anyview.action.admin;

import java.util.ArrayList;
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
import com.anyview.entities.ClassTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ClassTeacherManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@SuppressWarnings("serial")
@Namespace("/admin/classTeacherManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class ClassTeacherMangeAction extends CommonAction{
	
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	//封装jsp参数
	private ClassTable cla;
	//传递给Jsp的参数
	private String jsonStr;
		
	private String tcRightStr;
	
//	private Integer tiden;
//	
//	private Integer tid;
	
	@Autowired
	private ClassTeacherManager classTeacherManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private TeacherManager teacherManager;
	
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();//存放内容
	private int total;//rows的行数
	
	/**
	 * 获取教师数据
	 * @return
	 * @throws Exception
	 */
	@Action(value="getClassTeacherManagerPage",results={@Result(name="success",location="/admin/classTeacherManager/classTeacherManager.jsp")})
	public String gainClassManagerPage()throws Exception{
//		rows = classTeacherManager.getTeachers();
//		total = rows.size();
//		request.setAttribute("rows", rows);
//		request.setAttribute("total", total);
//		rows = classTeacherManager.getTeachers();
//     	request.setAttribute("rows", rows);
//		return SUCCESS;
	
		ManagerTable admin = (ManagerTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("numPerPage");
		Integer manager=admin.getMiden();
		Integer unid=admin.getUniversity().getUnID();
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer pageSize = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"tno":orderField;                //默认按教师编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Map param = new HashMap();
		param.put("pageSize", pageSize);
		param.put("pageNum", pageNum);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<TeacherTable> page=new Pagination<TeacherTable>();
		page = teacherManager.getTeachersPage(param, admin);
     	request.setAttribute("page", page);
     	request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	/**
	 * 获取该名老师教授班级的信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getClassesForTeacher",results={@Result(name="success",location="/admin/classTeacherManager/listClasses.jsp")})
	public String gainClassesForTeacher()throws Exception{
		Integer tid = Integer.valueOf(request.getParameter("tid"));
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer pageSize = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"cname":orderField;                 //默认按班级名称排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;   //默认升序
		Map param = new HashMap();
		param.put("pageSize", pageSize);
		param.put("pageNum", pageNum);
		param.put("tid", tid);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<ClassTable> page=classManager.getClassesPage(param);
		request.setAttribute("page", page);
		request.setAttribute("tid", tid);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	/**
	 * 编辑相关的权限信息
	 * @return
	 * @throws Exception
	 */
	@Action(value="editClassTeacherRight",results={@Result(name="jsonMsg",type="json")})
	public String editClassTeacherRight()throws Exception{
		Integer tid = Integer.valueOf(request.getParameter("tid"));
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		ClassTable cla = classManager.getClassByCid(cid);
		int tcRight = classTeacherManager.getTCRight(tid,cid);
		Integer tiden = teacherManager.getTeacherIdenByTid(tid);
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
		return "jsonMsg";
	}
	
	/**
	 * 保存相关的权限信息
	 * @throws Exception
	 */
	@Action(value="saveTCRight",results={@Result(name="jsonStr",type="json")})
	public void saveClass()throws Exception{
		try{
			String[] rights = request.getParameterValues("right");
			int tcRight = 0;
			for(int i=0;i < rights.length; i++){
				tcRight = tcRight | Integer.valueOf(rights[i]);
			}
			Integer cid = Integer.valueOf(request.getParameter("cla.cid"));
			Integer tid = Integer.valueOf(request.getParameter("hhhtid"));
			classTeacherManager.updateTCRight (tcRight, tid, cid);
			jsonStr = AjaxObject.newOk("保存成功!").toString();
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

//	public Integer getTid() {
//		return tid;
//	}
//
//	public void setTid(Integer tid) {
//		this.tid = tid;
//	}
//	
//	public Integer getTiden() {
//		return tiden;
//	}
//	
//	public void setTiden(Integer tiden) {
//		this.tiden = tiden;
//	}
}

