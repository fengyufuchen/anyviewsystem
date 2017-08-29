package com.anyview.dao;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;

public interface ClassDao {
	
	/**
	 * 根据教师id查找班级，并根据班级编号排序好相关数据
	 * @param tId
	 * @return
	 */
	public List<ClassTable> getClasses(Map param);
	/**
	 * 根据教师id查找班级
	 * @param tId
	 * @return
	 */
	public List<ClassTable> getClassesByTid(Integer tid);
	/**
	 * 根据教师id和tiden查找班级(分页)
	 * @param 
	 * @return
	 */
	//弃用，待删 --hefan 2016.03.24
	@Deprecated
	public List<ClassTable>getClassesByTea(final Integer pageSize,final Integer pageNum,TeacherTable tea);
	/**
	 * 查询教师对应班级数
	 * @param tId
	 * @return
	 */
	public Integer getClassCountByTid(Integer tid);
	/**
	 * 根据班级ID查询学生（分页）
	 * @param tId
	 * @return
	 */
	public List<StudentTable>getStudentsPageByCid(final Integer pageSize,final Integer pageNum,Integer cid);
	/**
	 * 查询一个班级中学生总数
	 * @param tId
	 * @return
	 */
	public Integer getStudentCountByCid(Integer cid);
	
	/**
	 * 根据班级id获取班级
	 * @param cid
	 * @return
	 */
	public ClassTable getClassByCid(Integer cid);
	
	/**
	 * 更新班级信息
	 * @param cla
	 */
	public void updateClass(ClassTable cla);
	
	/**
	 * 查询所有的班级的班号信息
	 */
	public List<ClassTable> getAllClasses();
	
	/**
	 * 根据学生id删除学生
	 * @param sid
	 */
	public void deleteStudent(Integer sid);
	
	/**
	 * 根据班级id和查询条件获取学生列表
	 * @param param
	 * @return
	 */
	public List<ClassStudentTable>getStudentsPage(Map param); 
	/**
	 * 根据班级Id和查询条件获取学生数目
	 * @param param
	 * @return
	 */
	public Integer getStudentCount(Map param);
	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<StudentTable>getStudents(Map param);
	
	/**
	 * 
	 * @Description： 根据学院ceId查询班级数量
	 * @param ceId
	 * @return：该学院在班级表中拥有的班级数量
	 * @author：刘武 
	 * @date：2015年9月14日 上午11:17:24
	 */
	public Integer gainClassCountByCeid(Integer ceId);
	
	/**
	 * 
	 * @Description: TODO(根据教师id查询在ClassTeacherCourseTable表中的班级) 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 上午1:48:05
	 */
	public List<ClassTable> getClassFromClassTeacherCourse(Integer tid);
	
	/**
	 * 
	 * @Description: TODO(改变班级状态) 
	 * @param cid
	 * @param status
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月16日 下午8:28:43
	 */
	public void changeClassStatus(Integer cid, Integer status);

}
