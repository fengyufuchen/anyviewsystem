/**   
* @Title: ClassCourseDao.java 
* @Package com.anyview.dao 
* @author 何凡 <piaobo749@qq.com>   
* @date 2016年3月23日 上午12:18:03 
* @version V1.0   
*/
package com.anyview.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTeacherCourseTable;

/** 
 * 班级课程数据库操作接口
 * @ClassName: ClassCourseDao 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月23日 上午12:18:03 
 *  
 */
public interface ClassCourseDao {
	
	/**
	 * 获取一个班级中的课程列表（分页）
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午12:23:05
	 */
	public List<ClassCourseTable> getCoursesOnClass(Integer firstResult, Integer maxResults, DetachedCriteria criteria);
	/**
	 * 获取一个班级中的课程的数量
	 * @param firstResult
	 * @param maxResults
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午12:24:20
	 */
	public Integer getCoursesCountOnClass(DetachedCriteria criteria);
	
	/**
	 * 根据id删除ClassCourseTable中的记录
	 * 
	 * @param ccId
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午6:55:08
	 */
	public void deleteClassCourseById(Integer ccId);
	/**
	 * 根据id获取ClassCourseTable对象
	 * 
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:05:05
	 */
	public ClassCourseTable getClassCourseById(Integer id);
	/**
	 * 更新ClassCourseTable
	 * 
	 * @param cc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:27:32
	 */
	public void updateCourseOnClass(ClassCourseTable cc);
	
	/**
	 * insert into ClassCourseTable
	 * 
	 * @param courseId
	 * @param cid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午8:08:17
	 */
	public void saveCourseToClass(Integer courseId, Integer cid);
	
	/**
	 * insert into ClassTeacherCourseTable
	 * @param tid 教师id
	 * @param ctcRight 班级教师课程权限
	 * @param courseId 课程id
	 * @param cid 班级id
	 * @param now 当前时间
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:21:16
	 */
	public void saveTeacherToCourseOnClass(Integer tid, Integer ctcRight, Integer courseId, Integer cid, Timestamp now);
	
	/**
	 * 根据id删除ClassTeacherCourseTable
	 * @param ctcId
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:54:24
	 */
	public void deleteTeacherOnCourse(Integer ctcId);
	
	/**
	 * 根据id获取ClassTeacherCourseTable的ctcright
	 * @param ctcId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午2:04:11
	 */
	public Integer getClassTeacherCourseRightById(Integer ctcId);
	/**
	 * 根据id获取ClassTeacherCourseTable
	 * @param ctcId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午2:08:36
	 */
	public ClassTeacherCourseTable getClassTeacherCourseById(Integer ctcId);
	/**
	 * 修改权限
	 * @param id
	 * @param right
	 * @param now
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午2:23:25
	 */
	public void updateCtcright(Integer id, Integer right, Timestamp now);

}
