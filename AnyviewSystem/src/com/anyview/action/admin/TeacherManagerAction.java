package com.anyview.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.encryption.MD5Util;
	
@SuppressWarnings("serial")
@Namespace("/admin/teacherManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class TeacherManagerAction extends CommonAction{
	//一页显示数目
	public static final Integer PAGE_SIZE = 20;
	//封装jsp参数
	private TeacherTable tea=new TeacherTable();
	//传递给Jsp的参数
	private String jsonStr;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private CollegeManager collegeManager;
	
	@Action(value="judgeTeacher",results={@Result(name="super",location="/admin/teacherManager/teacherManagerForSuper.jsp"),
										  @Result(name="college",location="/admin/teacherManager/teacherManagerForCollege.jsp"),
							              @Result(name="university",location="/admin/teacherManager/teacherManagerForUniversity.jsp"),
							              @Result(name="cuowu",location="/500.jsp")
										  })
	public String judgeTeacher()throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
		if(admin.getMiden()==-1) 
			{
			List<UniversityTable> universities = universityManager.gainAllUniversities();
			request.setAttribute("universities", universities);
			return "super";
			}
		if(admin.getMiden()==0)  return "college";
		if(admin.getMiden()==1)  return "university";
		return "cuowu";	
	}
		

	/**
	 * 
	 * 超级管理员获取教师信息
	 */
	@SuppressWarnings("unchecked")
	@Action(value="getTeacherForSuper",results={@Result(name="teacherManager",location="/admin/teacherManager/teacherManagerForSuper.jsp")})
	public String getTeacherForSuper()throws Exception{
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		Integer unid=Integer.valueOf(request.getParameter("unID"));
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"tno":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Map param = new HashMap();
		param.put("unID", unid);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("tea", tea);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<TeacherTable> page= teacherManager.getTeachersPageForSuper(param);
		request.setAttribute("page", page);
		request.setAttribute("unID", unid);//返回的学校ID
		request.setAttribute("criteria",tea);//查询条件回显
		request.setAttribute("param",param);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return "teacherManager";
	}

	/**
	 * 
	 * 学校管理员获取教师信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value="getTeacherForUniversity",results={@Result(name="success",location="/admin/teacherManager/teacherManagerForUniversity.jsp")})
	public String getTeacherForUniversity()throws Exception{
		ManagerTable admin=(ManagerTable)session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		Integer unid=admin.getUniversity().getUnID();
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"tno":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Map param = new HashMap();
		param.put("unID", unid);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("tea", tea);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<TeacherTable> page=new Pagination<TeacherTable>();
		page = teacherManager.getTeachersPageForSuper(param);
		request.setAttribute("page", page);
		request.setAttribute("unID", unid);//返回的学校ID
		request.setAttribute("criteria",tea );//查询条件回显
		request.setAttribute("param",param);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	/**
	 * 
	 * 学院管理员获取教师信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value="getTeacherForCollege",results={@Result(name="success",location="/admin/teacherManager/teacherManagerForCollege.jsp")})
	public String getTeacherForCollege()throws Exception{
		ManagerTable admin=(ManagerTable)session.get("User");
		String pageNumStr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		Integer unid=admin.getUniversity().getUnID();
	    Integer ceid=admin.getCollege().getCeID();
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"tno":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Map param = new HashMap();
		param.put("unID", unid);
		param.put("ceID", ceid);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("tea", tea);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<TeacherTable> page=new Pagination<TeacherTable>();
		page = teacherManager.getTeachersPageForCollege(param);
		request.setAttribute("page", page);
		request.setAttribute("unID", unid);//返回的学校ID
		request.setAttribute("ceID", ceid);
		request.setAttribute("criteria",tea );//查询条件回显
		request.setAttribute("param",param);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	@Action(value="searchTeacher",results={@Result(name="success",location="/admin/teacherManager/teacherManager.jsp")})
	public String searchTeacher()throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
		String pageNumStr = request.getParameter("pageNum");
 		String pageSizeStr = request.getParameter("pageSize");
		Integer manager=admin.getMiden();
		Integer unid=null;
		Integer ceid=null;
		if(request.getParameter("unID")!=null&&!"".equals(request.getParameter("unID")))//根据是否学校ID为空和管理者身份判断jsp界面学校检索栏的内容
		{
			
			unid=Integer.valueOf(request.getParameter("unID"));
			
		}
		
		
		if(request.getParameter("ceID")!=null && !"".equals(request.getParameter("ceID")))//同理
		{
			ceid=Integer.valueOf(request.getParameter("ceID"));
			
		}
	
		if(manager==0)
		{
			unid=admin.getUniversity().getUnID();
			ceid=admin.getCollege().getCeID();
		}
		else if(manager==1)
		{
			unid=admin.getUniversity().getUnID();
			
			
		}
	
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = pageSizeStr == null ? PAGE_SIZE : Integer.valueOf(pageSizeStr);
		orderField = (orderField==null || "".equals(orderField))?"tno":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		Map param = new HashMap();
		param.put("unID", unid);
		param.put("ceID", ceid);
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("tea", tea);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		Pagination<TeacherTable> page=new Pagination<TeacherTable>();
		page = teacherManager.getTeachersPage(param);
		request.setAttribute("page", page);
		if(admin.getMiden()==-1){
			List<UniversityTable> universities = universityManager.gainAllUniversities();
			request.setAttribute("universities", universities);
		}else if(admin.getMiden()==1){
           List<CollegeTable> colleges=teacherManager.getCollegeByUnid(unid);
           request.setAttribute("colleges", colleges);
		}
		request.setAttribute("ceID", ceid);//返回的学院ID
		request.setAttribute("unID", unid);//返回的学校ID
		request.setAttribute("admin", admin);
		request.setAttribute("criteria",tea );//查询条件回显
		request.setAttribute("param",param);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		return SUCCESS;
	}
	
	
//在增加老师前获得一些初始数据
		@SuppressWarnings("unchecked")
		@Action(value="BeforeAddTeacher",results={@Result(name="success",location="/admin/teacherManager/addTeacher.jsp")})
		public String BeforeAddTeacher()throws Exception{
		    ManagerTable admin = (ManagerTable) session.get("User");
		    if(admin.getMiden()==-1){
				List<UniversityTable> universities = universityManager.gainAllUniversities();
				request.setAttribute("universities", universities);
			}
//		    else if(admin.getMiden()==1){
//				Map param2 = new HashMap();
//				param2.put("unID",admin.getUniversity().getUnID());
//				Set set = collegeManager.getColleges(param2);
//				List<CollegeTable> colleges = new ArrayList();
//				colleges.addAll(set);
//				request.setAttribute("colleges", colleges);
//				
//			}
			request.setAttribute("admin", admin);
			return SUCCESS;
		}
		
		
	
	@Action(value="addTeacher",results={@Result(name="jsonStr",type="json")})
	public void addTeacher()throws Exception{
		response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");//防止弹出的信息出现乱码
        PrintWriter out = response.getWriter();
		ManagerTable admin = (ManagerTable) session.get("User");
		TeacherTable tea = new TeacherTable();
		Integer manager=admin.getMiden();
		UniversityTable un=new UniversityTable();
		CollegeTable ce=new CollegeTable();
		if(manager==0||manager==1)
		{
		un.setUnID(admin.getUniversity().getUnID());
//		if(manager==0)
//			{
//			ce.setCeID(admin.getCollege().getCeID());
//			}
//		
//		else
//			{
//			ce.setCeID(Integer.valueOf(request.getParameter("ceID")));
//			
//			}
			
		}
		else if(manager==-1)
		{
		
		un.setUnID(Integer.valueOf(request.getParameter("unID")));
	
		}
		Integer tno=Integer.valueOf(request.getParameter("tno"));
		Integer unID=Integer.valueOf(un.getUnID());
	
		try{
			
			tea.setUniversity(un);
			tea.setEnabled(1);
			tea.setTname(request.getParameter("tname"));
			tea.setTno(request.getParameter("tno"));
			tea.setTpsw(MD5Util.getEncryptedPwd(request.getParameter("tno")));
			tea.setTsex(request.getParameter("tsex"));
			Timestamp time = new Timestamp(System.currentTimeMillis());
			tea.setUpdateTime(time);
			teacherManager.addTeacher (tea);
			jsonStr = AjaxObject.newOk("保存成功!").setNavTabId("teacherManager").toString();
		}catch(Exception e){
			e.printStackTrace();
			
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			
			ResponseUtils.outJson(response, jsonStr);
		}
	}

	@Action(value="editTeacher",results={@Result(name="success",location="/admin/teacherManager/editTeacher.jsp")})
	public String editTeacher(){
		Integer tid=Integer.valueOf(request.getParameter("tid"));
		TeacherTable tea = teacherManager.gainTeacherByTid(tid);
		ManagerTable admin = (ManagerTable) session.get("User");
		request.setAttribute("admin", admin);
		if(admin.getMiden()==-1){
			List<UniversityTable> universities = universityManager.gainAllUniversities();
			request.setAttribute("universities", universities);
		}
//		else if(admin.getMiden()==1){
//			Map param2 = new HashMap();
//			param2.put("unID", admin.getUniversity().getUnID());
//			Set set = collegeManager.getColleges(param2);
//			List<CollegeTable> colleges = new ArrayList();
//			colleges.addAll(set);
//			request.setAttribute("colleges", colleges);
//		}
		
		Integer return_unID=Integer.valueOf(tea.getUniversity().getUnID());
		String return_unName=tea.getUniversity().getUnName();
		request.setAttribute("return_unName", return_unName);
		request.setAttribute("return_unID", return_unID);
		request.setAttribute("teacher", tea);
		return SUCCESS;
	}

	@Action(value="updateTeacher",results={@Result(name="jsonStr",type="json")})
	public void updateTeacher() throws IOException{
		try{
//			CollegeTable college=new CollegeTable();
//			college.setCeID(Integer.valueOf(request.getParameter("ceID")));
			UniversityTable university=new UniversityTable();
			university.setUnID(Integer.valueOf(request.getParameter("unID")));
			tea.setUniversity(university);
			teacherManager.updateTeacher(tea);
			jsonStr = AjaxObject.newOk("修改成功!").setNavTabId("teacherManager").toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="deleteTeacher",results={@Result(name="jsonStr",type="json")})
	public void deleteTeacher() throws IOException{
		try{
			Integer tid=Integer.valueOf(request.getParameter("tid"));
			TeacherTable tea = teacherManager.gainTeacherByTid(tid);
			teacherManager.deleteTeacher(tea);
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("admin/teacherManager/teacherManager.jsp").toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	@Action(value="beforeLink",results={@Result(name="success",location="/admin/teacherManager/linkToCollege.jsp")})
	public String beforeLink(){
		Integer tid=Integer.valueOf(request.getParameter("tid"));
		TeacherTable tea = teacherManager.gainTeacherByTid(tid);
		Integer unid=Integer.valueOf(tea.getUniversity().getUnID());
		List<CollegeTable> colleges=teacherManager.getCollegeByUnid(unid);
		String tname=tea.getTname();
		request.setAttribute("tid", tid);
		request.setAttribute("tname", tname);
        request.setAttribute("colleges", colleges);
		return SUCCESS;
	}
	@Action(value="linkToCollege",results={@Result(name="jsonStr",type="json")})
	public void linkToCollege() throws IOException{
		try{	
	    Integer tid=Integer.valueOf(request.getParameter("tid"));
		TeacherTable tea = teacherManager.gainTeacherByTid(tid);
		String ceid=request.getParameter("ceID");
		Integer enabled=Integer.valueOf(request.getParameter("enabled"));
		teacherManager.linkToCollege(tea,ceid,enabled);
		jsonStr = AjaxObject.newOk("关联成功!").setNavTabId("teacherManager").toString();
		}
		catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}
		finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	public TeacherTable getTea() {
		return tea;
	}

	public void setTea(TeacherTable tea) {
		this.tea = tea;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
}
