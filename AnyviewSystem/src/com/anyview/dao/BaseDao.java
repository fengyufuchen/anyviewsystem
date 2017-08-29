package com.anyview.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao {

	public SessionFactory getSessionFactory();
	
	/**Spring注入sessionFactory*/
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * 
	 * @Description: TODO(查询数量) 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月8日 下午9:17:30
	 */
	public Integer getCount(final DetachedCriteria criteria);
	
	/**
	 * 分页查询数据
	 * @param criteria
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月23日 上午10:58:01
	 */
	public <T> List<T> getList(DetachedCriteria criteria, Integer pageNum, Integer pageSize);
	/**
	 * 根据id获取实体类
	 * 
	 * @param c
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 下午2:06:12
	 */
	public <T> T getEntityById(Class c, Integer id);
	/**
	 * hql查询
	 * 
	 * @param c
	 * @param hql
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月13日 下午2:37:21
	 */
	public <T> List<T> getListByHql(Class c, String hql, Object[]params);
	
	public boolean saveObject(Object o);
	
	public boolean deleteObject(Object o);
	
	public boolean updateObject(Object o);
	
	public boolean saveOrUpdateObject(Object o);
}
