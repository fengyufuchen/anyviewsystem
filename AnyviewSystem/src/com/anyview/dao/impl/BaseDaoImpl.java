package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;









import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.anyview.dao.BaseDao;

/*
 * 在applicationContext.xml中配置注入
 */
@Repository(value="baseDao")
public class BaseDaoImpl implements BaseDao{
	
    @Autowired
	HibernateTemplate hibernateTemplate;
	
	@Autowired
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**Spring注入sessionFactory*/
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**Spring注入hibernateTemplate*/
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**返回HibernateTemplate*/
	protected HibernateTemplate getHibernateTemplate()
	{
		this.setHibernateTemplate();
		return hibernateTemplate;
	}
	
	/**如果用HibernateTemplate以外的其他查询，可使用session**/
	protected Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	/**初始化HibernateTemplate*/
	protected void setHibernateTemplate() {
		if(hibernateTemplate == null){
			hibernateTemplate = new HibernateTemplate(sessionFactory);
		}
	}
	
	/**保存对象*/
	public boolean saveObject(Object o){
		try {
			this.getHibernateTemplate().save(o);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}
	
	/**删除对象*/
	public boolean deleteObject(Object o)
	{
		try{
			this.getHibernateTemplate().delete(o);
			return true;
		}catch(DataAccessException e)
		{
			return false;
		}
	}
	
	
	/**更新对象*/
	public boolean updateObject(Object o){
		try {
			this.getHibernateTemplate().update(o);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}
	
	/**更新或保存对象*/
	public boolean saveOrUpdateObject(Object o){
		try {
			this.getHibernateTemplate().saveOrUpdate(o);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}
	
	/**
	 *  去掉List中的重复值
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		 List newList = new ArrayList();
		 for(Iterator it = list.iterator(); it.hasNext();){
			 Object element = it.next();
			 if(set.add(element))
				 newList.add(element);
		 }
		 return newList;
	}
	
	public Integer getCount(final DetachedCriteria criteria){
		Integer c = (Integer) hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria cri = criteria.getExecutableCriteria(session);
				CriteriaImpl criImpl = (CriteriaImpl) cri;
				Projection projection = criImpl.getProjection();
				Integer count = (Integer) cri.setProjection(Projections.rowCount()).uniqueResult();
				cri.setProjection(projection);//清空projection
				if(projection==null){
					cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				}
				return count;
			}
		});
		return c;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.BaseDao#getList(org.hibernate.criterion.DetachedCriteria, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public <T> List<T> getList(DetachedCriteria criteria, Integer pageNum,
			Integer pageSize) {
		return hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
	}

	@Override
	public <T> T getEntityById(Class c, Integer id) {
		return (T) hibernateTemplate.get(c, id);
	}

	@Override
	public <T> List<T> getListByHql(Class c, String hql, Object[] params) {
		return hibernateTemplate.find(hql, params);
	}

	
}
