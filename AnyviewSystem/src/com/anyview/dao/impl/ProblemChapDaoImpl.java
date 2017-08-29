package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.anyview.dao.ProblemChapDao;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;

@Component

public class ProblemChapDaoImpl extends BaseDaoImpl implements ProblemChapDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemChapTable> getAllChildrenChapByParentChIds(List<Integer> chIdList) {
		List<ProblemChapTable> childrenChaps = new ArrayList<ProblemChapTable>();
		for(final Integer chId:chIdList){
			List<ProblemChapTable> temp = hibernateTemplate.executeFind(new HibernateCallback() {
				
				@Override
				public Object doInHibernate(Session session) throws HibernateException,
						SQLException {
					String sql = "select * from problemchaptable where FIND_IN_SET(ChID,queryChildrenChap(?))";
					return session.createSQLQuery(sql).addEntity(ProblemChapTable.class).setInteger(0, chId).list();
				}
			});
			childrenChaps.addAll(temp);
		}
		return childrenChaps;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#getAllChildrenChapIdByParentChIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllChildrenChapIdByParentChIds(
			List<Integer> chIdList, final boolean isPublic) {
		List<Integer> childrenChaps = new ArrayList<Integer>();
		for(final Integer chId:chIdList){
			List<Integer> temp = hibernateTemplate.executeFind(new HibernateCallback() {
				
				@Override
				public Object doInHibernate(Session session) throws HibernateException,
						SQLException {
					if(isPublic){
						String sql = "select chId from problemchaptable where visit=1 and FIND_IN_SET(ChID,queryChildrenChap(?))";
						return session.createSQLQuery(sql).setInteger(0, chId).list();
					}else{
						String sql = "select chId from problemchaptable where FIND_IN_SET(ChID,queryChildrenChap(?))";
						return session.createSQLQuery(sql).setInteger(0, chId).list();
					}
				}
			});
			childrenChaps.addAll(temp);
		}
		return childrenChaps;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#getAllChapIdByLibIds(java.util.List, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllChapIdByLibIds(List<Integer> libList,
			final boolean isPublic) {
		List<Integer> chIds = new ArrayList<Integer>();
		for(final Integer lid:libList){
			List<Integer> temp = hibernateTemplate.executeFind(new HibernateCallback() {
				
				@Override
				public Object doInHibernate(Session session) throws HibernateException,
						SQLException {
					if(isPublic){
						String sql = "select chId from problemchaptable where visit=1 and lid=? and FIND_IN_SET(ChID,queryChildrenChap(-1))";
						return session.createSQLQuery(sql).setInteger(0, lid).list();
					}else{
						String sql = "select chId from problemchaptable where lid=? and FIND_IN_SET(ChID,queryChildrenChap(-1))";
						return session.createSQLQuery(sql).setInteger(0, lid).list();
					}
				}
			});
			chIds.addAll(temp);
		}
		chIds.remove((Object)Integer.getInteger("-1"));
		return chIds;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#getProblemChaps(org.hibernate.criterion.DetachedCriteria, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
//	@Override
	public List<ProblemChapTable> getProblemChaps(DetachedCriteria criteria,
			Integer firstResult, Integer maxResults, String orderField,
			String orderDirection) {
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#getProblemChapsCount(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public Integer getProblemChapsCount(DetachedCriteria criteria) {
		criteria = criteria.setProjection(Projections.countDistinct("chId"));
		return (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#getProblemChapByChId(java.lang.Integer)
	 */
	@Override
	public ProblemChapTable getProblemChapByChId(Integer chId) {
		return (ProblemChapTable) hibernateTemplate.get(ProblemChapTable.class, chId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#checkChapName(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public Integer checkChapName(ProblemChapTable chap) {
		String hql = "select count(chap.chId) from ProblemChapTable chap where chap.problemLib.lid=? and chap.parentChap.chId=? and chap.chName=?";
		Object[] params = new Object[]{chap.getProblemLib().getLid(),chap.getParentChap().getChId(),chap.getChName()}; 
		if(chap.getChId() != null){//如果有id，则是更新判断重名
			hql += " and chap.chId!=?";
			params = new Object[]{chap.getProblemLib().getLid(),chap.getParentChap().getChId(),chap.getChName(),chap.getChId()}; 
		}
		return Integer.valueOf(hibernateTemplate.find(hql,params).get(0).toString());
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#saveProblemChap(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public void saveProblemChap(ProblemChapTable chap) throws Exception {
		hibernateTemplate.save(chap);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#deleteProblemChaps(java.util.List)
	 */
	@Override
	public void deleteProblemChaps(final List<Integer> chIds) throws Exception {
		hibernateTemplate.execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				String hql = "delete from ProblemChapTable where chId in (:ids)";
				Query query = arg0.createQuery(hql);
				query.setParameterList("ids", chIds);
				return query.executeUpdate();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemChapDao#updateProblemChap(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public void updateProblemChap(ProblemChapTable chap) throws Exception {
		String hql = "update ProblemChapTable set chName=?,memo=?,visit=?,updateTime=? where chId=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{chap.getChName(),chap.getMemo(), chap.getVisit(), chap.getUpdateTime(), chap.getChId()});
	}

	@Override
	public List<ProblemChapTable> getOwnLibChapsByParentId(Integer parentId) {
		String hql = "from ProblemChapTable where parentChap.chId = ? order by updateTime";
		return hibernateTemplate.find(hql, parentId);
	}

	@Override
	public List<ProblemChapTable> getOtherLibChapsByParentId(Integer parentId) {
		String hql = "from ProblemChapTable where parentChap.chId=? and visit=1 order by updateTime";
		return hibernateTemplate.find(hql, parentId);
	}

	@Override
	public List<ProblemChapTable> getOwnLibFirstChaps(Integer lid) {
		String hql = "from ProblemChapTable where problemLib.lid=? and parentChap.chId=-1 order by updateTime";
		return hibernateTemplate.find(hql, lid);
	}

	@Override
	public List<ProblemChapTable> getOtherLibFirstChaps(Integer lid) {
		String hql = "from ProblemChapTable where problemLib.lid=? and parentChap.chId=-1 and visit = 1 order by updateTime";
		return hibernateTemplate.find(hql, lid);
	}

}
