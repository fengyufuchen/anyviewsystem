package com.anyview.action.admin;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminStudentManager;
import net.sf.json.JSONArray;

/**
 * @Description 学生管理批处理控制器类
 * @author DenyunFang
 * @time 2015年8月29日
 * @version 1.0
 */

@SuppressWarnings("serial")
@Namespace("/admin/adminstudentManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

public class BatAdminStudentManagerAction extends CommonAction {
	
	private List<?> rows;//返回数据
	
	private Map<String,Object> map;

	private String msg;
	private File file;
	private int total;
	
	private String jsonStr;
	
	@Autowired
	private AdminStudentManager adminStudentManager;
	
	/**
	 * @Description 根据管理员不同期限返回所能管理的班级列表
	 */
	@Action(value="getBatAdminAddStudentPage",results={@Result(name="success",location="/admin/adminstudentManager/batAdminAddStudent.jsp")})
	public String getBatAdminAddStudentPage()throws Exception{
		ManagerTable currentManager = (ManagerTable) session.get("User");
		if(currentManager.getMiden() == 1){
			//校级管理员
			List<UniversityTable> uniList = adminStudentManager.getUniversityByUnId(currentManager.getUniversity().getUnID());
			request.setAttribute("uniList", uniList);
		}
		else if(currentManager.getMiden() == 0){
			//院级管理员
			List<UniversityTable> uniList = adminStudentManager.getUniversityByUnId(currentManager.getUniversity().getUnID());
			request.setAttribute("uniList", uniList);
		}	
		else if(currentManager.getMiden() == -1){
			//超级管理员
			List<UniversityTable> uniList = adminStudentManager.selectAllUniversity();
			request.setAttribute("uniList", uniList);
		}
		return SUCCESS;
	}
	
	/**
	 * @Description  批量添加学生
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@JSON(serialize=false)
	@Action(value="batAddStudents",results={@Result(name="success",type="json",params ={"root","rows"})})
	public String batAddStudents() throws Exception{
		String selectRows = request.getParameter("selectRows");
		List list = JSONArray.fromObject(selectRows);
		List tempList = adminStudentManager.batAddStudent(list);
		for(int i = 0; i < tempList.size(); i++)
		{
			Map map = (Map)tempList.get(i);	
			if((Integer)map.get("submit") == 1)
			{
				map.put("state", "<span style='color:green'>添加成功</span>");
			}
			else
			{
				map.put("state", "<span style='color:green'>修改成功</span>");
			}
			switch((Integer)map.get("SAttr"))
			{
				case 0:map.put("SAttr", "停用");break;
				case 1:map.put("SAttr", "正常");break;
				default:break;
			}
			String studentsex = (String)map.get("studentSex");
			if(studentsex == null)
				map.put("studentSex", "男");
			else if(studentsex.equals("M"))
				map.put("studentSex", "男");
			else if(studentsex.equals("F"))	
				map.put("studentSex", "女");
			tempList.set(i,map);
		}
		rows = tempList;
		return SUCCESS;
	}
	
	/**
	 * @Description 通过excel表读取学生信息
	 */
	@SuppressWarnings({ "rawtypes"})
	@JSON(serialize = false)
	@Action(value="getFileData",results={@Result(name="success",type="json",params ={"map","rows"})})
	public String getFileData() throws Exception {
		String theClass = request.getParameter("class");
		String claid = request.getParameter("classId");
		//theClass = new String(theClass.getBytes("iso8859-1"),"utf-8");
		Map map = adminStudentManager.getFileData(file, theClass, claid);
		rows = (List) map.get("rows");
		msg = (String) map.get("message");
		total = rows.size();
		return SUCCESS;
	}
	
	/**
	 * @Description DWZ相关参数封装获取方法
	 */
	
	public Map<String,Object> getMap() {
		return map;
	}

	@JSON
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public String getMsg()
	{
		return msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}

}
