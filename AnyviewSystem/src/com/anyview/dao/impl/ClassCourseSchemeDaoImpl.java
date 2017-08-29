package com.anyview.dao.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentSchemeDetailVO;
import com.anyview.entities.StudentTable;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.ClassCourseSchemeDao;


@Repository
public class ClassCourseSchemeDaoImpl extends BaseDaoImpl implements ClassCourseSchemeDao{

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseSchemeDao#getClassCourseCount(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public Integer getClassCourseCount(DetachedCriteria criteria) {
		return super.getCount(criteria);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseSchemeDao#getClassCourseList(java.lang.Integer, java.lang.Integer, org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public List<ClassCourseSchemeTable> getClassCourseList(Integer firstResult,
			Integer maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseSchemeDao#getStudentSchemeDetails(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentSchemeDetailVO> getStudentSchemeDetails(final Integer firstResult, final Integer maxResults, final Integer cid, final Integer vid) {
		final String sql = "select l1.totalNum as totalNum, l1.totalTime as totalTime, l2.passNum as finishedNum, {student.*} "
				+"from   "
				+"(  "
				+"select e.sid as sid1, count(e.pid) as totalNum, sum(e.AccumTime) as totalTime "
				+"from   "
				+"exercisetable e  "
				+"where e.cid = :cid and e.vid = :vid  "
				+"group by e.sid  "
				+") l1  "
				+"left JOIN  "
				+"(  "
				+"select e.sid as sid2, count(e.pid) as passNum  "
				+"from exercisetable e  "
				+"where e.cid = :cid and e.vid = :vid and e.RunResult=1  "
				+"group by e.sid  "
				+") l2  "
				+"on l1.sid1 = l2.sid2  "
				+"left JOIN "
				+"studenttable student "
				+"on student.sid = l1.sid1 ";
		List<StudentSchemeDetailVO> ssd = hibernateTemplate.executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql)
						.addScalar("totalNum", Hibernate.INTEGER)
						.addScalar("totalTime", Hibernate.LONG)
						.addScalar("finishedNum", Hibernate.INTEGER)
						.addEntity("student", StudentTable.class);
				query.setParameter("cid", cid);
				query.setParameter("vid", vid);
				query.setResultTransformer(Transformers.aliasToBean(StudentSchemeDetailVO.class));
				return query.setFirstResult(firstResult).setMaxResults(maxResults).list();
			}
		});
		return ssd;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseSchemeDao#getCCSById(java.lang.Integer)
	 */
	@Override
	public ClassCourseSchemeTable getCCSById(Integer id) {
		return (ClassCourseSchemeTable) hibernateTemplate.get(ClassCourseSchemeTable.class, id);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseSchemeDao#getSchemeScores(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<ScoreTable> getSchemeScores(Integer firstResult,
			Integer maxResults, Integer cid, Integer vid,String orderField,String orderDirection) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ScoreTable.class)
				.createAlias("scheme", "v").createAlias("cla", "c")
				.add(Restrictions.eq("v.vid", vid))
				.add(Restrictions.eq("c.cid", cid));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<Object[]> getSchemeByClaIdandCouId(Integer claid, Integer couid){
		try{
			String hql = "select cc.scheme.vid,cc.scheme.vname from ClassCourseSchemeTable cc where cc.cla.cid=? and cc.course.courseId=?";
			return hibernateTemplate.find(hql, new Object[]{claid, couid});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassCourseSchemeTable> getSchemeByClaIdAndCouId(Integer claid, Integer couid){
		try{
			String hql = "from ClassCourseSchemeTable cc where cc.cla.cid=? and cc.course.courseId=?";
			return (List<ClassCourseSchemeTable>) hibernateTemplate.find(hql, new Object[]{claid, couid});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
