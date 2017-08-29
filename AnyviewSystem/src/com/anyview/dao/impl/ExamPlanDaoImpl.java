/**   
* @Title: ExamPlanDaoImpl.java 
* @Package com.anyview.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月10日 下午3:38:10 
* @version V1.0   
*/
package com.anyview.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.anyview.dao.ExamPlanDao;
import com.anyview.entities.ExamPlanTable;

@Component
public class ExamPlanDaoImpl extends BaseDaoImpl implements ExamPlanDao{

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getExamPlanCount(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public Integer getExamPlanCount(DetachedCriteria criteria) {
		return super.getCount(criteria);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getExamPlanPage(java.lang.Integer, java.lang.Integer, org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public List<ExamPlanTable> getExamPlanPage(Integer firstResult,
			Integer maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#saveExamPlan(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void saveExamPlan(ExamPlanTable examPlan) throws Exception {
		hibernateTemplate.save(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#checkExamPlanName(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean checkExamPlanName(Integer tid, ExamPlanTable examPlan, boolean exist) {
		String hql = "select count(e.epId) from ExamPlanTable e where e.teacher.tid=? and e.epName=?";
		Integer count = 0;
		if(!exist){
			count = ((Long) hibernateTemplate.find(hql, new Object[]{tid, examPlan.getEpName()}).get(0)).intValue();
		}else{
			hql += " and e.epId <> ?";
			count = ((Long) hibernateTemplate.find(hql, new Object[]{tid, examPlan.getEpName(), examPlan.getEpId()}).get(0)).intValue();
		}
		return !(count>0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#deleteExamPlan(java.lang.Integer)
	 */
	@Override
	public void deleteExamPlan(ExamPlanTable examPlan) {
		hibernateTemplate.delete(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getExamPlanByEpId(java.lang.Integer)
	 */
	@Override
	public ExamPlanTable getExamPlanByEpId(Integer epId) {
		return (ExamPlanTable) hibernateTemplate.get(ExamPlanTable.class, epId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#updateExamPlan(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void updateExamPlan(ExamPlanTable examPlan) {
		hibernateTemplate.update(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#changeExamPlanStatus(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void changeExamPlanStatus(Integer epId, Integer status) {
		String hql = "update ExamPlanTable set status=? where epId=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{status, epId});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#changeUpdateTime(java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public void changeUpdateTime(Integer epId, Timestamp time) {
		String hql = "update ExamPlanTable set updateTime=? where epId=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{time, epId});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getAll24HourAutomaticExam(java.sql.Timestamp)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamPlanTable> getAll24HourAutomaticExam(Timestamp time) {
		Criteria criteria = getSession().createCriteria(ExamPlanTable.class)
				.add(Restrictions.eq("kind", 0))
				.add(Restrictions.between("startTime", time, new Timestamp(time.getTime()+24*60*60*1000)));
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#changeStartTime(java.sql.Timestamp)
	 */
	@Override
	public void changeStartTime(Integer epId, Timestamp time) {
		String hql = "update ExamPlanTable set startTime=? where epId=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{time, epId});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getExamByStatus(int)
	 */
	@Override
	public List<ExamPlanTable> getExamByStatus(int status) {
		String hql = "from ExamPlanTable e where e.status = ?";
		return hibernateTemplate.find(hql, status);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#getAutomaticEndFlag(java.lang.Integer)
	 */
	@Override
	public Integer getAutomaticEndFlag(Integer epId) {
		String hql = "select automaticEndFlag from ExamPlanTable where epId=?";
		return (Integer) hibernateTemplate.find(hql, epId).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExamPlanDao#changeAutomaticEndFlag(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void changeAutomaticEndFlag(Integer epId, Integer status) {
		String hql = "update ExamPlanTable set automaticEndFlag = ? where epId = ?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{status, epId});
	}

}
