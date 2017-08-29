package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.service.commons.BaseManager;
import com.anyview.utils.TipException;

/**
 * @author 吴汪洋
 *
 */
public interface CourseManager extends BaseManager{
	/**
	 * 根据当前登录的管理员的权限(查询表ManagerTable)
	 * 读取表CourseTable中相应的数据
	 * @return 课程表(CourseTable)中符合条件的课程列表
	 * 返回值类型：List，泛型：CourseTable
	 */
	public Pagination<CourseTable> getCourses(Map param);
	
	
	/**
	 * 向CourseTable中增加一个元组
	 * @param courseName：课程名，类型：String
	 * @param ceID：学院ID，类型：Integer
	 * @param unID：学校ID，类型：Integer
	 * @param category：课程类型，类型：String
	 * @param enabled：有效性，类型：Integer
	 */
	public void addCourses(String courseName, Integer ceID, 
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
	 * 检查课程名是否重复
	 * courseName + ceID + unID为唯一索引
	 * 是则返回true，否则返回false
	 */
	public boolean isDuplicateCourseName(String courseName, Integer ceID, Integer unID);
	
	/**
	 * 通过学校ID查询对应的学院(学院表:CollegeTable)
	 * @param unID:学校ID
	 * @return List<CollegeTable>
	 */
	public List<CollegeTable> getCollegeByUnID(Integer unID);
	
	/**
	 * 获取所有大学列表
	 * @return List<UniversityTable>
	 */
	public List<UniversityTable> getAllUniversity();
	
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
	 * 获取学院下的课程列表(有效)
	 * @param param
	 * @return
	 */
	public List<CourseTable> getCourseByceID(Map param);
	
	/**
	 * 获取课程id+name的json数组
	 * @param ceID
	 * @return
	 * @throws TipException 
	 */
	public List<Object[]> getCourseINByCeId(Integer ceID) throws TipException;
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
	 * @Description： 获取学院ceID拥有的课程数量
	 * @param ceId,需查询的学院编号
	 * @return：获取学院ceID拥有的课程数量值
	 * @author：刘武 
	 * @date：2015年9月13日 下午10:49:03
	 */
	public Integer gainCourseCountByCeid(Integer ceId);
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
	
	/**
	 * 获取一个学校中所有有效的课程
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午10:39:30
	 */
	public Pagination<CourseTable> getUniversityCoursePage(Map params);
	
	/**
	 * 获取一个学院中所有有效的课程
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午10:40:06
	 */
	public Pagination<CourseTable> getCollegeCoursesPage(Map params);
}
