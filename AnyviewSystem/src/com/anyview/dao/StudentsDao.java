package com.anyview.dao;

import java.util.List;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.StudentTable;

public interface StudentsDao {

	/**
	 * 获取学生
	 * @param sid
	 * @return
	 */
	public StudentTable gainStudentBySid(Integer sid);
	
	/**
	 * 更新班级学生信息
	 * @param cs
	 */
	public void updateClassStudent(ClassStudentTable cs);
	
	/**
	 * 根据学生编号和学校id获取
	 * @param sno
	 * @return
	 */
	public StudentTable gainStudentBySnoAndUnid(String sno, Integer unId);
	
	/**
	 * 保存学生
	 * @param stu
	 * @return主键值
	 */
	public Integer saveStudent(StudentTable stu);
	
	/**
	 * 更新学生
	 * @Description: TODO() 
	 * @param stu
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月22日 上午3:18:53
	 */
	public void updateStudent(StudentTable stu);
	/**
	 * 根据班级id查询所有学生,对sname模糊查询
	 * 
	 * @param cid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月8日 下午9:50:54
	 */
	public List<StudentTable> getStudentsByCidSname(int cid, String sname);
}
