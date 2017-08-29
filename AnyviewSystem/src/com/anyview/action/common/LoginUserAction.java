package com.anyview.action.common;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.entities.UniversityTable;
import com.anyview.service.commons.LoginManager;
import com.anyview.service.function.UniversityManager;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")

@Namespace("")
@ParentPackage("kkkjjjj")
@Action(interceptorRefs={@InterceptorRef("systemInterceptor")})
@Results({
	@Result(name="0_index",location = "/admin/adminIndex.jsp"),
	@Result(name="1_index",location = "/teacher/teacherIndex.jsp"),
	@Result(name="2_index",location = "/student/studentIndex.jsp"),
	@Result(name="input",location = "/login.jsp"),		//验证账号密码
	@Result(name=com.opensymphony.xwork2.Action.ERROR,location = "/500.jsp")
})
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "500")})    
public class LoginUserAction extends CommonAction implements ModelDriven<LoginVO>{

	private LoginVO user = new LoginVO();
	
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private UniversityManager universityManager;

	@Action(value = "gainLoginPage",results={@Result(name="gainLoginPage",location = "/login.jsp")})
	public String gainLoginPage()throws Exception {
		List<UniversityTable> uns = universityManager.gainAllUniversities();
		request.setAttribute("universities", uns);
		return "gainLoginPage";
	}
	
	@SuppressWarnings("rawtypes")
	@Action(value = "login")
	public String login() throws Exception {
		System.out.println(user.getUsername());
		Map map = loginManager.login(user.getUsername(),user.getPassword(),
				request.getRemoteAddr(),user.getRank()+"",user.getUnId()+"");
		Object u = map.get("User");
		if(u != null){
			//保存用户信息记录到session 
			session.put("User", u);
			
			if(user.getRank() == 0){//管理员登陆
				
			}else if(user.getRank() == 1){//教师登陆 
				
			}else if(user.getRank() == 2){//学生登录 
				
			}
			
			//记录cookie
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for (Cookie c : cookies) {
					if ((c.getName().equals("name") || c.getName().equals("account")) && c.getValue()!= "") {
						String value = URLDecoder.decode(c.getValue(),"utf-8");
						System.out.println(value);
					}
				}
			}
		}else{
			this.addActionMessage((String)map.get("msg"));
		}
		return user.getRank()+"_index";
	}
	
	@Override
	public void validate() {
		Map map;
		try {
			map = loginManager.login(user.getUsername(),user.getPassword(),
					request.getRemoteAddr(),user.getRank()+"",user.getUnId()+"");
			Object u = map.get("User");
			if(user.getUsername()!=null){
				if(u == null){
					this.addFieldError("usernameError", "用户名或密码输入错误");
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SkipValidation
	@Action(value = "userLoginOut",results={@Result(name="loginOut",location = "gainLoginPage",type="chain")})
	public String userLoginOut ()throws Exception{
		session.clear();
		return "loginOut";
	}
	
	@Override
	public LoginVO getModel() {
		return user;
	}
}
