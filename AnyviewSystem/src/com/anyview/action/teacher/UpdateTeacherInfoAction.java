package com.anyview.action.teacher;

import java.io.IOException;
import java.util.HashMap;
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

import com.anyview.action.admin.AdminBaseAction;
import com.anyview.action.common.CommonAction;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.TeacherManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;
import com.anyview.action.common.*;

@SuppressWarnings("serial")
@Namespace("/teacher/updateTeacherInfo")
@ParentPackage("teacherBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class UpdateTeacherInfoAction extends AdminBaseAction{
	private static final Log log = LogFactory.getLog(UpdateTeacherInfoAction.class);
	
	private String jsonStr="";
	@Autowired
	public TeacherManager tea;
	
	@Action(value="getTeacherInfo",results={@Result(name="updateTeacher",location="/teacher/updateTeacher/showTeacherInfo.jsp")})
	public String getTeacherInfo(){
		TeacherTable teacher = (TeacherTable) session.get("User");
		request.setAttribute("teacher", teacher);
		return "updateTeacher";
	}
	
	
	@Action(value="updateTeacher")
	public void updateAdminPwd() throws IOException{
		try{
			TeacherTable teacher = (TeacherTable) session.get("User");
			String oldPwd = request.getParameter("oldPwd");
			if(oldPwd==null){
				oldPwd="";
			}
			String newPwd = request.getParameter("newPwd");
	        String tsex=request.getParameter("tsex");
	        String tname=request.getParameter("tname");
	        Map map=new HashMap();
	        map.put("oldPwd", oldPwd);
	        map.put("newPwd", newPwd);
	        map.put("tsex", tsex);
	        map.put("tname", tname);
	        tea.modifyTeacher(teacher,map);
			jsonStr = AjaxObject.newOk("修改密码成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT).toString();
		}catch(TipException t){
			t.printStackTrace();
			log.debug("修改密码失败--->"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("修改密码错误--->"+e.getMessage());
			jsonStr = AjaxObject.newError("系统错误").toString();
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}
	

}
