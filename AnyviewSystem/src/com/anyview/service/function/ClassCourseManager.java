package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.CCTable;
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.Pagination;
import com.anyview.utils.TipException;

public interface ClassCourseManager {
	
	public List getCCSByClass(String classId);

	public List getCCSByCourse(String courseId);
	
	public int settingCCS(String classId,String courseId,String schemeId,String teacherId,String status) ;
	
	/**
	 * 获取一个班级上课程的页面
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午12:02:11
	 */
	public Pagination<ClassCourseTable> getClassCourses(Map params);
	
	/**
	 * 保存课程与班级的关联关系
	 * 
	 * @param coIds 多个课程id组成的字符串，用,分割
	 * @param cid 班级id
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午6:31:03
	 */
	public void saveCourseToClass(String coIds, Integer cid) throws TipException;
	
	/**
	 * 根据id删除ClassCourseTable中的记录
	 * 
	 * @param ccId
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午6:53:38
	 */
	public void deleteCourseOnClass(Integer ccId);
	
	/**
	 * 根据id获取CLassCourseTable
	 * 
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:04:09
	 */
	public ClassCourseTable getClassCourseById(Integer id);
	/**
	 * 修改ClassCourseTable
	 * 更改字段：status, startYear
	 * 
	 * @param cc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午7:24:27
	 */
	public void updateCourseOnClass(ClassCourseTable cc);
	/**
	 * 根据course和class获取teacher页面
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 下午11:32:42
	 */
	public Pagination<ClassTeacherCourseTable> getClassTeacherCoursePage(Map params);
	
	/**
	 * 保存添加到课程上的教师
	 * @param map
	 * @param cc 包含Class和Course
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:16:16
	 */
	public void saveTeacherToCourseOnClass(Map<String, Integer> map, ClassCourseTable cc);
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
	 * @date 2016年3月24日 上午2:10:12
	 */
	public ClassTeacherCourseTable getClassTeacherCourseById(Integer ctcId);
	/**
	 * 修改权限
	 * @param ctc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午2:21:42
	 */
	public void updateCtcright(ClassTeacherCourseTable ctc);

}
