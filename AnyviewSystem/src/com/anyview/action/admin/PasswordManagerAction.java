/**   
* @Title: PasswordManagerAction.java 
* @Package com.anyview.action.admin 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年10月17日 下午7:04:03 
* @version V1.0   
*/
package com.anyview.action.admin;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.entities.ManagerTable;
import com.anyview.service.function.AdminManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@SuppressWarnings("serial")
@Namespace("/admin/passwordManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class PasswordManagerAction extends AdminBaseAction{
	
	private static final Log log = LogFactory.getLog(PasswordManagerAction.class);
	@Autowired
	public AdminManager adminManager;
	
	private String jsonStr="";
	
	@Action(value="passwordManager",results={@Result(name="passwordManager",location="/admin/passwordManager/passwordManager.jsp")})
	public String passwordManager(){
		ManagerTable admin = (ManagerTable) session.get("User");
		request.setAttribute("admin", admin);
		return "passwordManager";
	}
	
	@Action(value="updateAdminPwd")
	public void updateAdminPwd() throws IOException{
		try{
			ManagerTable admin = (ManagerTable) session.get("User");
			String oldPwd = request.getParameter("oldPwd");
			String newPwd = request.getParameter("newPwd");
			adminManager.modifyPassword(admin,oldPwd, newPwd);
			jsonStr = AjaxObject.newOk("修改密码成功!").setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT).toString();
		}catch(TipException t){
			t.printStackTrace();
			log.debug("旧密码输入错误--->"+t.getMessage());
			jsonStr = AjaxObject.newError(t.getMessage()).toString();
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("修改密码错误--->"+e.getMessage());
			jsonStr = AjaxObject.newError("修改密码错误").toString();                                                                                
		}finally{
			ResponseUtils.outJson(response, jsonStr);
		}
	}

}
