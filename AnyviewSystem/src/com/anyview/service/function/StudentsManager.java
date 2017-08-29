package com.anyview.service.function;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.utils.TipException;

public interface StudentsManager {
	
	/**
	 * 根据学生id获取学生对象
	 * @param sid
	 * @return
	 */
	public StudentTable gainStudentBySid(Integer sid);
	
	/**
	 * 修改学生信息
	 * 需要判断教师是否具有此权限
	 * @param tea
	 * @param stu
	 * @throws TipException 
	 */
	public void updateStudent(TeacherTable tea, ClassStudentTable cs) throws TipException;
	
	/**
	 * 教师管理班级时添加学生
	 * 需要判断教师是否具有此权限
	 * @param tea
	 * @param cs
	 * @throws TipException
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void saveStudentForClass(TeacherTable tea, ClassStudentTable cs) throws TipException, NoSuchAlgorithmException, UnsupportedEncodingException;
	
	/**
	 * 重置学生密码
	 * 需要判断教师是否具有此权限
	 * @Description: TODO() 
	 * @param tea
	 * @param cs
	 * @throws TipException
	 * @author 何凡 <piaobo749@qq.com>
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @date 2015年9月22日 上午3:15:19
	 */
	public void resetStudentPwd(TeacherTable tea, ClassStudentTable cs) throws TipException, NoSuchAlgorithmException, UnsupportedEncodingException;
	/**
	 * 获取班级里的学生
	 * 
	 * @param cid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月8日 下午9:49:57
	 */
	public List<StudentTable> getStudentsByCidSname(int cid, String sname);

}
