package com.anyview.action.admin;

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

import com.anyview.action.common.CommunionAction;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminManager;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;
//struts2
@SuppressWarnings("serial")
@Namespace("/admin/adminManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class AdminManagerAction extends AdminBaseAction{
	private static final Log log = LogFactory.getLog(AdminManagerAction.class);
	public static final Integer PAGE_SIZE = 20; 
	@Autowired//Sring 3 ：对类成员变量、方法和构造函数进行标注，完成自动装配，
	private AdminManager adminManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CollegeManager collegeManager;
	
	private String jsonStr="";
	
	private ManagerTable condition = new ManagerTable();//对页面查询条件进行封装
	private Timestamp createDateStart;
	private Timestamp createDateEnd;
	private Timestamp updateDateStart; 
	private Timestamp updateDateEnd; 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getAdminManagerPage",results={
			@Result(name="superAdminManager",location="/admin/adminManager/superAdminManager.jsp"),
			@Result(name="universityAdminManager",location="/admin/adminManager/universityAdminManager.jsp")})
	public String gainClassadminManagerPage(){
		ManagerTable admin = (ManagerTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		Map param = new HashMap();
		param.put("admin", admin);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("condition", condition);
		param.put("createDateStart", createDateStart);
		param.put("createDateEnd", createDateEnd);
		param.put("updateDateStart", updateDateStart);
		param.put("updateDateEnd", updateDateEnd);
		Pagination<ManagerTable> page = adminManager.getAdminPage(param);
		if(admin.getMiden()==-1){
			List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
			if(condition.getUniversity()!=null && condition.getCollege() != null){//表示已经选择了学校和学院
				List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(condition.getUniversity().getUnID());
				request.setAttribute("colleges", colls);
			}
			request.setAttribute("universities", univers);
		}else if(admin.getMiden()==1){
			List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(admin.getUniversity().getUnID());
			request.setAttribute("colleges", colls);
		}
		request.setAttribute("page", page);//在request对象中加入名为page的属性并附值为page,参数传递，实现跳转到jsp使之获取值（绑定 获取）
		request.setAttribute("condition", condition);//查询条件回显
		request.setAttribute("createDateStart", createDateStart);
		request.setAttribute("createDateEnd", createDateEnd);
		request.setAttribute("updateDateStart", updateDateStart);
		request.setAttribute("updateDateEnd", updateDateEnd);
		return admin.getMiden()==-1?"superAdminManager":"universityAdminManager";
	}

	@Action(value="addAdmin",results={
			@Result(name="superAdminAdd",location="/admin/adminManager/superAdminAdd.jsp"),
			@Result(name="universityAdminAdd",location="/admin/adminManager/universityAdminAdd.jsp")})
	public String addAdmin(){
		ManagerTable admin = (ManagerTable) session.get("User");
		if(admin.getMiden()==-1){
			List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
			request.setAttribute("universities", univers);
		}else if(admin.getMiden()==1){
			List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(admin.getUniversity().getUnID());
			request.setAttribute("colleges", colls);
		}
		return admin.getMiden()==-1?"superAdminAdd":"universityAdminAdd";
	}
	
	@Action(value="saveAdmin")
	public void saveAdmin() throws IOException{
		try {
			ManagerTable admin = (ManagerTable) session.get("User");
			if(admin.getMiden()==1){
				condition.setUniversity(admin.getUniversity());
				condition.setMiden(0);//校级管理员只能添加院级管理员
			}
			if(condition.getMiden()==1){
				condition.setCollege(new CollegeTable(-1));//校级管理员学院ID为-1
			}
			adminManager.saveAdmin(condition);
			jsonStr = AjaxObject.newOk("添加管理员成功!").setNavTabId("adminManager").toString();
		} catch (Exception e) {
			log.error("保存管理员失败--->"+e.getMessage());
			e.printStackTrace();
			jsonStr = AjaxObject.newError("添加管理员失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="deleteAdmin")
	public void deleteAdmin() throws IOException{
		try{
			ManagerTable admin = (ManagerTable) session.get("User");
			if(admin.getMiden()==-1 || admin.getMiden()==1){
				adminManager.deleteAdmin(condition.getMid());
			}else{
				throw new TipException("您没有权限。");
			}
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("admin/adminManager/getAdminManagerPage.action").toString();
		}catch (TipException t){
			log.error("删除管理员失败---> "+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除管理员失败---> "+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="updateAdmin",results={
			@Result(name="superAdminUpdate",location="/admin/adminManager/superAdminUpdate.jsp"),
			@Result(name="universityAdminUpdate",location="/admin/adminManager/universityAdminUpdate.jsp")})
	public String updateAdmin(){
		ManagerTable admin = (ManagerTable) session.get("User");
		ManagerTable updateAdmin = adminManager.getAdminByMid(condition.getMid());
		if(admin.getMiden()==-1){
			List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
			List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(updateAdmin.getUniversity().getUnID());
			request.setAttribute("colleges", colls);
			request.setAttribute("universities", univers);
		}else if(admin.getMiden()==1){
			List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(admin.getUniversity().getUnID());
			request.setAttribute("colleges", colls);
		}
		request.setAttribute("updateAdmin", updateAdmin);
		return admin.getMiden()==-1?"superAdminUpdate":"universityAdminUpdate";
	}
	
	@Action(value="saveUpdateAdmin")
	public void saveUpdateAdmin() throws IOException{
		try {
			ManagerTable admin = (ManagerTable) session.get("User");
			if(admin.getMiden()==1){
				condition.setUniversity(admin.getUniversity());
				condition.setMiden(0);
			}
			adminManager.updateAdmin(condition);
			jsonStr = AjaxObject.newOk("修改管理员成功!").setNavTabId("adminManager").toString();
		} catch (Exception e) {
			log.error("修改管理员失败--->"+e.getMessage());
			e.printStackTrace();
			jsonStr = AjaxObject.newError("修改管理员失败").toString();
		} finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	public ManagerTable getCondition() {
		return condition;
	}

	public void setCondition(ManagerTable condition) {
		this.condition = condition;
	}

	public Timestamp getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Timestamp createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Timestamp getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Timestamp createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Timestamp getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(Timestamp updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public Timestamp getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Timestamp updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}
	
}
