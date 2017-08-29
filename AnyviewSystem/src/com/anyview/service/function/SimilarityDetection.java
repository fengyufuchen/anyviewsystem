package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeContentTable;

public interface SimilarityDetection {
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:19:52
	 */
	public  List<Object[]> getCourseByTIdandClaId(Integer tid, Integer claid);
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	public  List<Object[]> getSchemeByClaIdandCouId(Integer claid, Integer couid);
	
	/**
	 * 
	 * @Description: TODO(根据教师id获取班级) 
	 * @param tid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午7:33:28
	 */
	public List<ClassTeacherTable> getClassByTId(Integer tid);
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午4:34:11
	 */
	public List<ClassTeacherCourseTable> getCourseByTIdAndClaId(Integer tid, Integer claid);
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午4:36:52
	 */
	public  List<ClassCourseSchemeTable> getSchemeByClaIdAndCouId(Integer claid, Integer couid);
	
	/**
	 * 
	 * @Description: TODO() 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午7:34:06
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<SchemeContentTable> getSchemeContentPage(Map param);
	
	/**
	 * 
	 * @Description: TODO(下载学生作业答案) 
	 * @param cid
	 * @param ids
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @param path 
	 * @date 2016年1月23日 下午8:17:30
	 */
	public boolean downloadAnswer(Integer cid, String[] ids, String path);
}
