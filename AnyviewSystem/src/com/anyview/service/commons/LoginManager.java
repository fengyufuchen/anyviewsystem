package com.anyview.service.commons;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: LoginManager
* @Description: 逻辑层登录接口
* @author xhn
* @date 2012-10-20 下午09:00:32
*
 */
public interface LoginManager {

	/**
	 * 
	* @Title: login
	* @Description: 判断登录信息是否正确,验证正确返回用户对象，否则抛出对应异常
	* @param args  0、帐号  1、密码  2、登录IP 3、等级(1管理员,2教师,3学生)...
	* @return Map<String,Object> 返回"User"登录对象、"msg"处理信息、null为出错
	 * @throws Exception 
	 */
	public Map<String,Object> login(String ...args) throws Exception;
	
}
