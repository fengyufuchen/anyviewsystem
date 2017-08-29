package com.anyview.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaJoinWalker;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.AdminManagerDao;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.UniversityTable;

@Repository//用于标注数据访问组件
public class AdminManagerDaoImpl extends BaseDaoImpl implements AdminManagerDao {

	@Override
	public List<ManagerTable> gainAllAdmins() {
		String hql = "from ManagerTable";
		return hibernateTemplate.find(hql);//Hibernate：一种开放源代码的对象关系映射框架,它对JDBC进行了非常轻量级的对象封装
	}
	
	@SuppressWarnings("unchecked")
	@Override
 	public List<ManagerTable> getAdminsPage(Integer firseResult, Integer maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria,firseResult, maxResults);
 	}
 	
	@SuppressWarnings("unchecked")
	@Override
 	public Integer getAdminCount(final DetachedCriteria criteria){
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
	 * @see com.anyview.dao.AdminManagerDao#saveAdmin(com.anyview.entities.ManagerTable)
	 */
	@Override
	public void saveAdmin(ManagerTable admin) throws Exception {
		hibernateTemplate.save(admin);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.AdminManagerDao#deleteAdmin(java.lang.Integer)
	 */
	@Override
	public void deleteAdmin(Integer mid) throws Exception {
		String hql = "delete from ManagerTable where mid = ?";
		hibernateTemplate.bulkUpdate(hql, mid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.AdminManagerDao#getAdminByMid(java.lang.Integer)
	 */
	@Override
	public ManagerTable getAdminByMid(Integer mid) {
		return (ManagerTable) hibernateTemplate.get(ManagerTable.class, mid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.AdminManagerDao#updateAdmin(com.anyview.entities.ManagerTable)
	 */
	@Override
	public void updateAdmin(ManagerTable admin) throws Exception {
		hibernateTemplate.update(admin);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.AdminManagerDao#modifyPassword(com.anyview.entities.ManagerTable, java.lang.String)
	 */
	@Override
	public void modifyPassword(ManagerTable admin, String pwd) throws Exception {
		String hql = "update ManagerTable set mpsw=? where mid=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{pwd, admin.getMid()});
//		hibernateTemplate.bulkUpdate(hql, pwd);
	}

}
