package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.TeacherTable;

public interface CourseDao{

	/**
	 * 向CourseTable中增加一个元组
	 * @param courseName：课程名，类型：String
	 * @param ceID：学院ID，类型：Integer
	 * @param unID：学校ID，类型：Integer
	 * @param category：课程类型，类型：String
	 * @param enabled：有效性，类型：Integer
	 */
	public void addCourse(String courseName, Integer ceID, 
			Integer unID, String category, Integer enabled);
	
	/**
	 * 在CourseTable中通过课程ID修改一个元组
	 * @param courseId：课程ID，类型：Integer
	 * @param courseName：课程名，类型：String
	 * @param ceID：学院ID，类型：Integer
	 * @param unID：学校ID，类型：Integer
	 * @param category：课程类型，类型：String
	 * @param enabled：有效性，类型：Integer
	 */
	public void editCourseByCourseID(Integer courseId, String courseName, Integer ceID, 
					Integer unID, String category, Integer enabled);
    
	/**
     * 根据当前管理员权限，获取全部课程信息
     */
	public List<CourseTable> getCourses(Map param);
	
	/**
	 * 检查是否有重复的课程
	 */
	public boolean checkDuplicateCourse(String courseName, Integer ceID, Integer unID);
	
	/**
	 * 通过学校ID查询对应的学院(学院表:CollegeTable)
	 * @param unID:学校ID
	 * @return List<CollegeTable>
	 */
	public List<CollegeTable> getCollegeByUnID(Integer unID);
	
	/**
	 * 通过课程ID删除课程
	 * @param courseId：课程ID
	 */
	public void deleteCourseByCourseID(Integer courseId) throws HibernateException;
	
	/**
	 * 通过课程ID得到这个课程的所有信息
	 * @param courseId：课程ID
	 * @return List<CollegeTable>
	 */
	public List<CourseTable> getCourseByCourseID(Integer courseId);
	
	public List<CourseTable> getAllCourse();
	
	/**
	 * 查询学院下的课程列表(有效)
	 * @param param
	 * @return
	 */
	public List<CourseTable> getCourseByceID(Map param);
	
	/**
	 * 获取课程id+name的json数组
	 * @param ceID
	 * @return
	 */
	public List<Object[]> getCourseINByCeId(Integer ceID);
    /**
	 * 
	 * @Description: TODO(获取该教师所有具有right的课程) 
	 * @param teacher
	 * @param right 01位：批改作业，10位修改作业表，对应二进制位上为一则拥有相应权限
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月4日 上午2:25:23
	 */
	public List<CourseTable> getCourseByTidAndRight(TeacherTable teacher,Integer right);
	/**
	 * 
	 * @Description: TODO(通过id获取courseTable对象) 
	 * @param courseId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月4日 下午8:01:55
	 */
	public CourseTable getCourseByCourseId(Integer courseId);
	/**
	 * 
	 * @Description: TODO(根据管理员身份获取课程数量) 
	 * @param admin
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月10日 下午4:52:55
	 */
	public Integer getCourseCount(Map param);
	
	/**
	 * 
	 * @Description： 根据学院ceID在课程表中查询该学院拥有的课程数量
	 * @param ceId
	 * @return：查询学院的课程数量
	 * @author：刘武 
	 * @date：2015年9月14日 下午12:28:27
	 */
	public  Integer gainCourseCountByCeid(Integer ceId);
	
	/**
	 * 
	 * @Description: TODO(根据教师id和班级id查询出课程的id和name) 
	 * @param tid
	 * @param cid
	 * @return List<Object[2]> object[0]=id, object[1]=name
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 上午2:11:17
	 */
	public List<Object[]> getCourseINFromClassTeacherCourse(Integer tid, Integer cid);
	
}
