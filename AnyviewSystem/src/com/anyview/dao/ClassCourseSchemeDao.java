package com.anyview.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentSchemeDetailVO;

public interface ClassCourseSchemeDao {
	
	/**
	 * 获取数目
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月1日 下午5:36:11
	 */
	public Integer getClassCourseCount(DetachedCriteria criteria);
	
	/**
	 * 获取list
	 * @param firstResult
	 * @param maxResults
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月1日 下午5:36:26
	 */
	public List<ClassCourseSchemeTable> getClassCourseList(Integer firstResult, Integer maxResults, DetachedCriteria criteria);
	/**
	 * 根据id获取ClassCourseSchemeTable对象
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月5日 下午6:05:33
	 */
	public ClassCourseSchemeTable getCCSById(Integer id);
	
	/**
	 * 获取学生做题信息
	 * @param cid
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月7日 下午10:12:41
	 */
	@Deprecated
	public List<StudentSchemeDetailVO> getStudentSchemeDetails(Integer firstResult, Integer maxResults, Integer cid, Integer vid);
	
	/**
	 * 查询ScoreTable
	 * @param firstResult
	 * @param maxResults
	 * @param cid
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月23日 下午10:56:11
	 */
	public List<ScoreTable> getSchemeScores(Integer firstResult, Integer maxResults, Integer cid, Integer vid, String orderField,String orderDirection);
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	public List<Object[]> getSchemeByClaIdandCouId(Integer claid, Integer couid);
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	public List<ClassCourseSchemeTable> getSchemeByClaIdAndCouId(Integer claid, Integer couid);
	
}