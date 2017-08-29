package com.anyview.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anyview.dao.ClassTeacherCourseDao;
import com.anyview.entities.ClassTeacherCourseTable;

@Repository
public class ClassTeacherCourseDaoImpl extends BaseDaoImpl implements ClassTeacherCourseDao{
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程id) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:30:34
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCourseByTIdandClaId(Integer tid, Integer claid){
		try{
			String hql = "select ct.course.courseId,ct.course.courseName from ClassTeacherCourseTable ct where ct.teacher.tid=? and ct.cla.cid=?";
			return hibernateTemplate.find(hql, new Object[]{tid, claid});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程id) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:30:34
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<ClassTeacherCourseTable> getCourseByTIdAndClaId(Integer tid, Integer claid){
		try{
			String hql = "from ClassTeacherCourseTable ct where ct.teacher.tid=? and ct.cla.cid=?";
			return (List<ClassTeacherCourseTable>) hibernateTemplate.find(hql, new Object[]{tid, claid});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
