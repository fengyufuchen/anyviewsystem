package com.anyview.dao;

import java.util.List;

import com.anyview.entities.ClassTeacherCourseTable;

public interface ClassTeacherCourseDao {
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程id) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:30:34
	 */
	public  List<Object[]> getCourseByTIdandClaId(Integer tid, Integer claid);
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程id) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:30:34
	 */
	public  List<ClassTeacherCourseTable> getCourseByTIdAndClaId(Integer tid, Integer claid);
	
}
