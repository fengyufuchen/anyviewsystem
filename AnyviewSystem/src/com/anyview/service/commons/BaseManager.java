/**   
* @Title: BaseManager.java 
* @Package com.anyview.service.commons 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年12月1日 下午5:05:44 
* @version V1.0   
*/
package com.anyview.service.commons;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.Pagination;

/** 
 * service层基类
 * @ClassName: BaseManager 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年12月1日 下午5:05:44 
 *  
 */
public interface BaseManager {

	public Pagination getPage(Integer currentPage, Integer numPerPage, List content, Integer totalCount);
	
	/**
	 * 根据criteria获取页面的公共方法
	 * @param criteria 条件查询
	 * @param pageNum 
	 * @param pageSize
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午11:04:31
	 */
	public <T> Pagination<T> getPageByCriteria(DetachedCriteria criteria, Integer pageNum, Integer pageSize);
	/**
	 * 根据Id获取实体类
	 * 
	 * @param Class
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 下午2:04:40
	 */
	public <T> T getEntityById(Class c ,Integer id);
	/**
	 * hql查询
	 * 
	 * @param c
	 * @param hql
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月13日 下午2:35:34
	 */
	public <T> List<T> getListByHql(Class c,String hql, Object[]params);
	
}
