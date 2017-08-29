/**   
 * @Title: LoginManagerImpl.java
 * @Package com.stusys.service.commons.impl
 * @Description: TODO
 * @author xhn 
 * @date 2012-11-12 下午09:26:01
 * @version V1.0   
 */
package com.anyview.service.commons.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;














import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.AdminDao;
import com.anyview.dao.StudentsDao;
import com.anyview.dao.TeacherDao;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.commons.LoginManager;
import com.anyview.utils.encryption.MD5Util;

/**
 * @ClassName: LoginManagerImpl
 * @Description: 登录逻辑处理
 * @author xhn
 * @date 2012-11-12 下午09:26:01
 * 
 */
@Service
public class LoginManagerImpl implements LoginManager {

	@Autowired
	private TeacherDao teaDao;
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private StudentsDao studentsDao;
	
	@Override
	public Map<String, Object> login(String... args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int rank = Integer.valueOf(args[3]);
		if(rank == 0){//管理员登陆
			ManagerTable admin = adminDao.gainAdminByMnoAndUnId(args[0],Integer.valueOf(args[4]));
			if(admin == null){
				map.put("msg", "帐号不存在!");
//			} else if (args[1].equals(admin.getMpsw())) {
			} else if ((args[1].isEmpty() && admin.getMpsw().isEmpty()) || MD5Util.validPassword(args[1], admin.getMpsw())) {//开发阶段空密码也允许
				map.put("User", admin);
			} else {
				map.put("msg", "密码错误!");
			}
		}else if(rank == 1){//教师登陆 
			TeacherTable teacher = teaDao.getTeacherByTnoAndUnId(args[0],Integer.valueOf(args[4]));
			if (teacher == null) {
				map.put("msg", "帐号不存在!");
			} else if ((args[1].isEmpty() && teacher.getTpsw().isEmpty()) || MD5Util.validPassword(args[1], teacher.getTpsw())) {
//			} else if (args[1].equals(teacher.getTpsw())) {
				map.put("User", teacher);
			} else {
				map.put("msg", "密码错误!");
			}
		}else if(rank == 2){//学生登录 
			StudentTable student = studentsDao.gainStudentBySnoAndUnid(args[0],Integer.valueOf(args[4]));
			if(student == null){
				map.put("msg", "帐号不存在!");
//			} else if (args[1].equals(student.getSpsw())) {
			} else if ((args[1].isEmpty() && student.getSpsw().isEmpty()) || MD5Util.validPassword(args[1], student.getSpsw())) {
				map.put("User", student);
			} else {
				map.put("msg", "密码错误!");
			}
		}
		
		
		
		return map;
	}

}
