package com.anyview.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminClassManager;
import com.anyview.service.function.AdminStudentManager;
import com.anyview.service.function.ClassCourseManager;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ClassTeacherManager;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

/**
 * @Description 班级管理控制器类
 * @author DenyunFang
 * @time 2015年09月05日
 * @version 1.0
 */

@SuppressWarnings("serial")
@Namespace("/admin/adminclassManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

public class AdminClassManagerAction extends CommonAction{
	private static final Log log = LogFactory.getLog(AdminClassManagerAction.class);
	public static final Integer PAGE_SIZE = 20;
	private String jsonStr="";
	private String forwardUrl;
	private String statusCode;
	private String message;
	private String callbackType ;
	private String navTabId;
	@Autowired
	private AdminClassManager adminClassManager; 
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private CollegeManager collegeManager;
	@Autowired
	private ClassTeacherManager classTeacherManager;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private ClassManager classManager;
	@Autowired
	private ClassCourseManager classCourseManager;
	@Autowired
	private CourseManager courseManager;
	
	private static Integer cids = -1;	//临时存储由jsp传来的班级id
	private static Integer sids = -1;	//临时存储由jsp传来的学生id
	@Autowired
	private AdminStudentManager adminStudentManager; 
	private StudentTable conditionstu = new StudentTable(); //对页面学生查询条件进行封装
	
	
	//对页面查询条件进行封装
	private ClassTable cla = new ClassTable();
	private ManagerTable condition = new ManagerTable(); 
	private ClassStudentTable cs = new ClassStudentTable();
	private ClassTeacherTable ct = new ClassTeacherTable();
	private TeacherTable teacher = new TeacherTable();
	private ClassCourseTable cc = new ClassCourseTable();
	private ClassTeacherCourseTable ctc = new ClassTeacherCourseTable();
	
	public AdminClassManagerAction(){
		CollegeTable col = new CollegeTable();
		UniversityTable uni = new UniversityTable();
		col.setUniversity(uni);
		cla.setCollege(col);
		ct.setCla(new ClassTable());
		ct.setTeacher(new TeacherTable());
	}
	
	/**
	 * 
	 * @Description: 根据权限获取班级管理页面
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月4日 下午3:19:27
	 * modified by hefan
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	@Action(value="classManager",results={
			@Result(name="classManager",location="/admin/adminclassManager/classManager.jsp")})
	public String classManager()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "cname"));
		param.put("Manager", currentManager);
		if(currentManager.getMiden()==-1){//超级管理员
			List<UniversityTable> univers = universityManager.gainAllEnabledUniversities();
			if(cla.getCollege().getUniversity().getUnID()!=null){//表示已经选择了学校
				List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(cla.getCollege().getUniversity().getUnID());
				request.setAttribute("colleges", colls);
			}
			request.setAttribute("universities", univers);
		}else if(currentManager.getMiden()==1){//校级管理员
			cla.getCollege().setUniversity(currentManager.getUniversity());
			List<Object[]> colls = collegeManager.getEnabledCollegesByUnId(currentManager.getUniversity().getUnID());
			request.setAttribute("colleges", colls);
		}else{
			cla.setCollege(currentManager.getCollege());
		}
		param.put("condition", cla);
		Pagination<ClassTable> page = adminClassManager.getClassPage(param);
		request.setAttribute("admin", currentManager);
		request.setAttribute("page", page);
		request.setAttribute("condition", cla);//查询条件回显
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);	
		return "classManager";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value="getListStudentPage",results={@Result(name="success",location="/admin/adminclassManager/listStudent.jsp")})
	public String getListStudentPage()throws Exception{
		System.out.println("---------cids:" + cids + "----------");
		cids = request.getParameter("cid") == null ? cids : Integer.valueOf(request.getParameter("cid"));
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		
		orderField = (orderField==null || "".equals(orderField))?"sno":orderField;	//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;	//默认升序	
		
		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		param.put("condition", condition);
		param.put("conditionstu", conditionstu);
		System.out.println("---------param cids:" + cids + "----------");
		param.put("cids", cids);
		
		Pagination<StudentTable> page=new Pagination<StudentTable>();
		page = adminStudentManager.getListStudentsPage(param);

		request.setAttribute("page", page);
		request.setAttribute("condition", condition);//查询条件回显
		request.setAttribute("conditionstu", conditionstu);//查询条件回显
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		System.out.println("---------request cids:" + cids + "----------");
		request.setAttribute("cid", cids);
		
		return SUCCESS;	
	}
	/**
	 * 
	 * @Description: TODO(获取关联学生到班级页面) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月20日 上午12:10:07
	 */
	@Action(value="addStudentInClass", results={@Result(name="success",location="/admin/adminclassManager/addStudentInClass.jsp")})
	public String addStudentInClass() throws Exception{
		System.out.println("--adminstudentmanager---addStudentInClass----");
		sids = request.getParameter("sid") == null ? -1 : Integer.valueOf(request.getParameter("sid"));
		return SUCCESS;
	}
	/**
	 * 
	 * @Description: TODO(根据管理员权限保存学生相关信息，若jsp提交的数据不完整则返回相应不完整内容的提示，若添加的学生其学校id与学号同时存在则返回“学号存在”提示) 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:38:15
	 */
	@Action(value="saveStudentInClass")
	public void saveStudentInClass() throws Exception{
		System.out.println("-----------来到了saveStudentInClass");
		if(adminStudentManager.addStudentInClass(sids, cids, cs))
		{
			message = "关联成功！";
			statusCode = "200";
			navTabId = "listStudent";
		}
		else
		{
			message = "关联失败！";
			statusCode = "300";
		}
		
//		forwardUrl = "admin/adminstudentManager/getListStudentPage.action";
		System.out.println("-----------saveStudentInClass cids:" + cids);
		forwardUrl = "admin/adminclassManager/getListStudentPage.action";
		callbackType  = "closeCurrent";
		AjaxObject json=new AjaxObject(Integer.valueOf(statusCode), message, navTabId, forwardUrl, "", callbackType);
		 response.getWriter().write(json.toString());
	}
	
	
	/**
	 * @Description 根据管理员权限返回添加班级页面的选择列表数据
	 */
	@Action(value="addClass", results={@Result(name="success",location="/admin/adminclassManager/addClass.jsp")})
	public String addClass() throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);
		if(currentManager.getMiden() == 1){
			//校级管理员
			List<CollegeTable> collegeList = adminClassManager.selectAllCollegeByUnID(currentManager.getUniversity().getUnID());
			request.setAttribute("colleges", collegeList);
		}
		else if(currentManager.getMiden() == -1){
			//超级管理员
			List<UniversityTable> universityList = adminClassManager.selectAllUniversity();
			request.setAttribute("universities", universityList);
		}
		return SUCCESS;
	}

	/**
	 * @Description 根据管理员权限返回添加班级页面的选择列表数据
	 */
	@Action(value="listStudent", results={@Result(name="success",location="/admin/adminclassManager/listStudent.jsp")})
	public String listStudent() throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		request.setAttribute("admin", currentManager);
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: TODO(获取查看学生页面及数据) 
	 * @return
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午6:25:47
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value="getLookStudentPage", results={@Result(name="success",location="/admin/adminclassManager/lookStudent.jsp")})
	public String gLookStudentPage()throws Exception{
		
		String pageNumStr = request.getParameter("pageNum");
		String numPerPageStr = request.getParameter("numPerPage");
		Integer pageNum = pageNumStr == null ? 1 : Integer.valueOf(pageNumStr);
		Integer numPerPage = numPerPageStr == null ? PAGE_SIZE : Integer.valueOf(numPerPageStr);
		orderField = (orderField==null || "".equals(orderField))?"sno":orderField;//默认按学生编号排序
		orderDirection = (orderDirection==null || "".equals(orderDirection))?"asc":orderDirection;//默认升序
		
		
		Map param = new HashMap();
		param.put("pageSize", numPerPage);
		param.put("pageNum", pageNum);
		param.put("classStudent", cs);
		param.put("orderField", orderField);
		param.put("orderDirection", orderDirection);
		
		Pagination<ClassStudentTable> page = adminClassManager.getLookStudentPage(param);
		
		request.setAttribute("page", page);
		request.setAttribute("criteria", cs);//查询条件回显
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @Description: 根据管理员权限保存班级相关信息，若jsp提交的数据不完整则返回相应不完整内容的提示，若添加的班级班名及其学院id同时存在则返回“班级存在”提示 
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午6:59:17
	 */
	@Action(value="saveClass")
	public void saveClass() throws Exception{
		try{
			ManagerTable currentManager = (ManagerTable) session.get("User");
			String unIdStr = request.getParameter("unId");
			String ceIdStr = request.getParameter("ceId");
			if(unIdStr == null || ceIdStr == null){
				throw new TipException("请选择学院");
			}
			//超级管理员获取页面选择的学校
			//其他则只能添加自身所属的学校
			Integer unid = (currentManager.getMiden() == -1) ? Integer.valueOf(unIdStr):currentManager.getUniversity().getUnID();	
			//学院管理员只能添加自身所属的学院
			//其他可以选择学院
			Integer ceid = (currentManager.getMiden() == 0) ? currentManager.getCollege().getCeID():Integer.valueOf(ceIdStr);	
			CollegeTable col = new CollegeTable(ceid);
			col.setUniversity(new UniversityTable(unid));
			cla.setCollege(col);
			if(adminClassManager.isClassExist(cla)){
				throw new TipException("在同一学校同一学院中已经有名为"+cla.getCname()+"的班级了");
			}
			adminClassManager.saveClass(cla);
			jsonStr = AjaxObject.newOk("添加班级成功!").setNavTabId("adminclassManager").toString();
		}catch(TipException t){
			t.printStackTrace();
			log.debug("保存班级失败--->"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("保存班级错误--->"+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	
	/**
	 * 
	 * @Description: 根据班级id传递要进行修改的班级的参数到修改班级页面
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午4:05:10
	 */
	@Action(value="editClass", results={@Result(name="success",location="/admin/adminclassManager/updateClass.jsp")})
	public String editClass() throws Exception{
		ManagerTable admin = (ManagerTable) session.get("User");
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		ClassTable cla = adminClassManager.gainClassByCid(cid);
		request.setAttribute("admin", admin);
		request.setAttribute("clas", cla);
		return SUCCESS;
	}

	
	/**
	 * 
	 * @Description: 更新修改班级后的信息，若修改后的班级已存在则不更新并返回“已存在”提示
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:01:51
	 */
	@Action(value="updateClass", results={@Result(name="success",type="json")})
	public String updateClass() throws Exception{
		if(adminClassManager.isClassExistByCeid(cla) == false){
			if(adminClassManager.updateClass(cla)){
				message = "修改成功！";
				statusCode = "200";
				navTabId = "adminclassManager";
			}else{
				message = "修改失败！";
				statusCode = "300";
			}
		}else{
			message = "该班级已经存在,请修改相关信息再提交！";
			statusCode = "300";
		}
		forwardUrl = "admin/adminclassManager/getAdminClassManagerPage.action";
		callbackType  = "closeCurrent";
		return SUCCESS;
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取编辑班级学生关联属性) 
	 * @return
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:47:37
	 */
	@Action(value="editStudentInClass", results={@Result(name="success",location="/admin/adminclassManager/updateStudentInClass.jsp")})
	public String editStudentInClass() throws Exception{
		ClassStudentTable cc = adminClassManager.getClassStudentByCidAndSid(cs.getCla().getCid(), cs.getStudent().getSid());
		request.setAttribute("classStudent", cc);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: TODO(更新班级学生关联关系) 
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:47:11
	 */
	@Action(value="updateStudentInClass")
	public void updateStudentInClass() throws Exception{
		try{
			if(adminClassManager.updateStudentInClass(cs))
				jsonStr = AjaxObject.newOk("修改成功!").setNavTabId("lookStudent").toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 
	 * @Description: TODO(删除班级学生关联关系) 
	 * @return
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:14:49
	 */
	@Action(value="deleteStudentInClass")
	public void deleteStudentInClass()throws Exception{
		try{
			if(adminClassManager.deleteStudentInClass(cs.getCla().getCid(), cs.getStudent().getSid()))
				jsonStr = AjaxObject.newOk("删除成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_FORWARD).setForwardUrl("admin/adminclassManager/getLookStudentPage.action?cs.cla.cid="+cs.getCla().getCid()).toString();
		}catch(Exception e){
			e.printStackTrace();
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	
	/**
	 * 
	 * @Description: 删除班级及班级下的所有学生
	 * @return
	 * @throws Exception
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:01:10
	 */
	@Action(value="deleteClass",results={@Result(name="success",type="json")})
	public String deleteClass() throws Exception{
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		if(adminClassManager.deleteClassByCid(cid)){
			statusCode = "200";
			message = "删除成功";
			forwardUrl = "admin/adminclassManager/getAdminClassManagerPage.action";
			callbackType  = "forward";
			return SUCCESS;
		}
		statusCode = "300";
		message = "删除失败";
		return SUCCESS;
	}
	
	/**
	 * 班级-教师管理
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月20日 下午4:58:35
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="classTeacherManager", results={@Result(name="classTeacherManager",location="/admin/adminclassManager/classTeacherManager.jsp")})
	public String classTeacherManager(){
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "teacher.tname"));
		param.put("condition", ct);
		Pagination<ClassTeacherTable> page = classTeacherManager.getClassTeachers(param);
		ClassTable ccc = classManager.getClassByCid(ct.getCla().getCid());
		ct.setCla(ccc);
		request.setAttribute("page", page);
		request.setAttribute("condition", ct);
		return "classTeacherManager";
	}
	
	/**
	 * 为班级添加教师页面
	 * 超级管理员：可以为一个班级添加班级所在学校的任何学院的教师；
	 * 校级管理员：可以为一个班级添加本校任何学院教师；
	 * 院级管理员：可以为一个班级添加本院教师；
	 * 已经关联到此班级的教师不再显示
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月21日 上午12:37:05
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="addTeacherForClass", results={@Result(name="addTeacherForClass",location="/admin/adminclassManager/addTeacherForClass.jsp")})
	public String addTeacherForClass(){
		ManagerTable admin = (ManagerTable) session.get("User");
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, 10, "tname"));
		//超级管理员从页面传回的参数获取unID,其他管理员从自身学校中获取
		Integer unID = admin.getMiden() == -1 ? teacher.getUniversity().getUnID() : admin.getUniversity().getUnID();
		//院级管理员从自身所属的学院中获取ceID，其他管理员为null
		Integer ceID = admin.getMiden() == 0 ? admin.getCollege().getCeID() : null;
		Pagination<TeacherTable> page = teacherManager.getEnabledTeachersByUniAndCe(cla.getCid(), unID, ceID, (Integer)param.get("pageSize"),(Integer)param.get("pageNum"), orderField, orderDirection);
		request.setAttribute("page", page);
		//从参数中获取再返回给页面保存
		request.setAttribute("unID", unID);
		request.setAttribute("cid", cla.getCid());
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);	
		return "addTeacherForClass";
	}
	
	/**
	 * 保存新添加到班级的教师关联关系
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午8:18:05
	 */
	@SuppressWarnings({ "unchecked"})
	@Action(value="saveTeacherForClass")
	public void saveTeacherForClass() throws IOException{
		Map<String, Integer> map = null;
		try{
			String rs = request.getParameter("p");
			Integer cid = Integer.valueOf(request.getParameter("cid").toString());
			JSONObject obj = JSONObject.fromObject(rs);
			map = (Map<String, Integer>)obj;
			classTeacherManager.saveTeachersToClass(map, cid);
			jsonStr = AjaxObject.newOk("添加成功!").setNavTabId("").setCallbackType("").toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("为班级添加教师错误："+map.toString());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 删除班级与教师的关联关系
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午8:18:23
	 */
	@Action(value="deleteTeacherOnClass")
	public void deleteTeacherOnClass() throws IOException{
		try{
			classTeacherManager.deleteTeacherOnClass(teacher.getTid(), cla.getCid());
			log.info("删除教师与班级关联关系成功，tid:"+teacher.getTid()+",cid:"+cla.getCid());
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除教师与班级关联关系失败，tid:"+teacher.getTid()+",cid:"+cla.getCid());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	@Action(value="editTeacherRightOnClass", results={@Result(name="editTeacherRightOnClass",location="/admin/adminclassManager/editTeacherRightOnClass.jsp")})
	public String editTeacherRightOnClass(){
		ClassTeacherTable cc = classTeacherManager.getClassTeacherByCidAndTid(cla.getCid(), teacher.getTid());
		request.setAttribute("cc", cc);
		return "editTeacherRightOnClass";
	}

	/**
	 * 修改教师在班级上的权限
	 * 
	 * @author 何凡 <piaobo749@qq.com>
	 * @throws IOException 
	 * @date 2016年3月22日 下午9:27:32
	 */
	@Action(value="saveTeacherRightOnClass")
	public void saveTeacherRightOnClass() throws IOException{
		try{
			String[] rs = request.getParameterValues("atc_tcrightCheckbox");
			Integer tcRight = 0;
			if(rs.length>0){
				for(String s : rs){
					tcRight+=Integer.valueOf(s);
				}
			}
			classTeacherManager.updateTCRight(tcRight, ct.getTeacher().getTid(),ct.getCla().getCid());
			log.info("修改教师在班级上的权限成功，cid"+cla.getCid()+" tid:"+teacher.getTid());
			jsonStr = AjaxObject.newOk("设置成功!").setNavTabId("").toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("修改教师在班级上的权限错误");
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 获取一个班级上的课程页面
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午12:06:44
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="classCourseManager", results={@Result(name="classCourseManager",location="/admin/adminclassManager/classCourseManager.jsp")})
	public String classCourseManager(){
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "startYear"));
		param.put("condition", cc);
		Pagination<ClassCourseTable> page = classCourseManager.getClassCourses(param);
		request.setAttribute("page", page);
		request.setAttribute("condition", cc);
		return "classCourseManager";
	}
	
	/**
	 * 
	 * 返回一个班级上添加课程关联的页面
	 * 超级管理员和校级管理员可以给一个班级添加班级所在学校任何学院的课程
	 * 院级管理员只能添加班级所在学院的课程
	 * 已经关联到此班级的课程不再显示
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午10:18:09
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="addCourseToClass", results={@Result(name="addCourseToClass",location="/admin/adminclassManager/addCourseToClass.jsp")})
	public String addCourseToClass(){
		ManagerTable admin = (ManagerTable) session.get("User");
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, 10, "courseId"));
		ClassTable currCla = classManager.getClassByCid(cla.getCid());
		param.put("admin", admin);
		param.put("currCla", currCla);
		Pagination<CourseTable> page = admin.getMiden()==0?courseManager.getCollegeCoursesPage(param):courseManager.getUniversityCoursePage(param);
		request.setAttribute("page", page);
		request.setAttribute("cla", currCla);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);	
		return "addCourseToClass";
	}
	
	/**
	 * 保存课程与班级的关联关系
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午6:27:36
	 */
	@Action(value="saveCourseToClass")
	public void saveCourseToClass() throws IOException{
		String coIds = "";
		try{
			coIds = request.getParameter("p");
			classCourseManager.saveCourseToClass(coIds, cla.getCid());
			jsonStr = AjaxObject.newOk("添加成功!").setNavTabId("").setCallbackType("").toString();
		}catch(TipException t){
			t.printStackTrace();
			log.debug(t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("为班级添加课程错误："+coIds);
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 删除课程与班级的关联关系
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:17:58
	 */
	@Action(value="deleteCourseOnClass")
	public void deleteCourseOnClass() throws IOException{
		try{
			classCourseManager.deleteCourseOnClass(cc.getId());
			log.info("删除课程与班级关联关系成功，ClassCourseTable.id:"+cc.getId());
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除教师与班级关联关系失败，ClassCourseTable.id:"+cc.getId());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 返回编辑班级中的课程页面
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:17:58
	 */
	@Action(value="editCourseOnClass", results={@Result(name="editCourseOnClass",location="/admin/adminclassManager/editCourseOnClass.jsp")})
	public String editCourseOnClass(){
		ClassCourseTable claco = classCourseManager.getClassCourseById(cc.getId());
		request.setAttribute("cc", claco);
		return "editCourseOnClass";
	}
	
	/**
	 * 更新班级中课程的信息：status, startYear, updateTime
	 * 
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:38:27
	 */
	@Action(value="updateCourseOnClass")
	public void updateCourseOnClass() throws IOException{
		try{
			ManagerTable admin = (ManagerTable) session.get("User");
			classCourseManager.updateCourseOnClass(cc);
			log.info("更新ClassCourseTable成功，管理员Id:"+admin.getMid()+",更新信息："+cc.toString());
			jsonStr = AjaxObject.newOk("更新成功!").toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("更新ClassCourseTable失败，更新信息："+cc.toString());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 根据指定的class, course获取ClassTeacherCourseTable中对应的教师
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午11:35:45
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="classTeacherCourseManager", results={@Result(name="classTeacherCourseManager",location="/admin/adminclassManager/classTeacherCourseManager.jsp")})
	public String classTeacherCourseManager(){
		ClassCourseTable claco = classCourseManager.getClassCourseById(cc.getId());
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, PAGE_SIZE, "tea.tname"));
		param.put("cc", claco);
		Pagination<ClassTeacherCourseTable> page = classCourseManager.getClassTeacherCoursePage(param);
		request.setAttribute("page", page);
		request.setAttribute("cc", cc);
		return "classTeacherCourseManager";
	}
	
	/**
	 * 为一个班级上的一个课程添加教师
	 * 只能添加已经关联到此班级的教师
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午12:21:01
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="addTeacherToCourseOnClass", results={@Result(name="addTeacherToCourseOnClass",location="/admin/adminclassManager/addTeacherToCourseOnClass.jsp")})
	public String addTeacherToCourseOnClass(){
		ClassCourseTable claco = classCourseManager.getClassCourseById(cc.getId());
		Map param = new HashMap();
		param.putAll(super.gPageParams(request, 10, "teacher.tname"));
		param.put("condition", claco);
		Pagination<ClassTeacherTable> page = classTeacherManager.getClassTeachersForCourse(param);
		request.setAttribute("page", page);
		request.setAttribute("claco", claco);
		return "addTeacherToCourseOnClass";
	}
	
	/**
	 * 保存添加到一个班级中课程上的教师
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:13:16
	 */
	@SuppressWarnings("unchecked")
	@Action(value="saveTeacherToCourseOnClass")
	public void saveTeacherToCourseOnClass() throws IOException{
		Map<String, Integer> map = null;
		try{
			String rs = request.getParameter("p");
			ClassCourseTable claco = classCourseManager.getClassCourseById(cc.getId());
			JSONObject obj = JSONObject.fromObject(rs);
			map = (Map<String, Integer>)obj;
			classCourseManager.saveTeacherToCourseOnClass(map, claco);
			jsonStr = AjaxObject.newOk("添加成功!").setNavTabId("").setCallbackType("").toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("为课程添加教师错误："+map.toString());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * 删除一个班级中关联到课程上的教师
	 * @throws IOException
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:51:45
	 */
	@Action(value="deleteTeacherOnCourse")
	public void deleteTeacherOnCourse() throws IOException{
		try{
			classCourseManager.deleteTeacherOnCourse(ctc.getId());
			log.info("删除班级中课程与教师关联关系成功，ClassTeacherCourse.id:"+ctc.getId());
			jsonStr = AjaxObject.newOk("删除成功!").setCallbackType("").toString();
		}catch (Exception e){
			e.printStackTrace();
			log.error("删除班级中课程与教师关联关系错误，ClassCourseTable.id:"+cc.getId());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	/**
	 * 设置教师在一个课程中的权限
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午2:15:25
	 */
	@Action(value="setCtcright", results={@Result(name="setCtcright",location="/admin/adminclassManager/setCtcright.jsp")})
	public String setCtcright(){
		ctc = classCourseManager.getClassTeacherCourseById(ctc.getId());
		request.setAttribute("ctc", ctc);
		return "setCtcright";
	}
	
	/**
	 * 保存教师在一个课程中的权限
	 * 
	 * @author 何凡 <piaobo749@qq.com>
	 * @throws IOException 
	 * @date 2016年3月24日 上午2:15:47
	 */
	@Action(value="saveCtcright")
	public void saveCtcright() throws IOException{
		try{
			ManagerTable admin = (ManagerTable) session.get("User");
			String[] rs = request.getParameterValues("ctcright");
			Integer right = 0;
			for(String s:rs){
				right += Integer.valueOf(s);
			}
			ctc.setCtcright(right);
			classCourseManager.updateCtcright(ctc);
			log.info("更新ClassCourseTable成功，管理员Id:"+admin.getMid()+",更新信息："+ctc.toString());
			jsonStr = AjaxObject.newOk("更新成功!").toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("为班级添加课程错误："+ctc.toString());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally {
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	
	/**
	 * @Description DWZ相关参数封装获取方法
	 */
	public ClassStudentTable getCs() {
		return cs;
	}

	public void setCs(ClassStudentTable cs) {
		this.cs = cs;
	}

	public ManagerTable getCondition() {
		return condition;
	}

	public void setCondition(ManagerTable condition) {
		this.condition = condition;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}
	
	public void setForwardUrl(String forwardUrl) {  
        this.forwardUrl = forwardUrl;  
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
    
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public ClassTable getCla() {
		return cla;
	}

	public void setCla(ClassTable cla) {
		this.cla = cla;
	}

	public ClassTeacherTable getCt() {
		return ct;
	}

	public void setCt(ClassTeacherTable ct) {
		this.ct = ct;
	}

	public TeacherTable getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}

	public ClassCourseTable getCc() {
		return cc;
	}

	public void setCc(ClassCourseTable cc) {
		this.cc = cc;
	}

	public ClassTeacherCourseTable getCtc() {
		return ctc;
	}

	public void setCtc(ClassTeacherCourseTable ctc) {
		this.ctc = ctc;
	}
	
	

}
