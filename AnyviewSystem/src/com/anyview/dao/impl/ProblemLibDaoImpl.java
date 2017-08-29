package com.anyview.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.anyview.dao.ProblemLibDao;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemLibTeacherTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;

@Repository
public class ProblemLibDaoImpl extends BaseDaoImpl implements ProblemLibDao{

	@Override
	public List<ProblemLibTable> getAllProblemLibs() {
		String hql = "from ProblemLibTable";
		return hibernateTemplate.find(hql);
	}

	@Override
	public List<ProblemLibTable> getUProblemLibs(ManagerTable admin) {
		String hql = "from ProblemLibTable pl where pl.visit=4 or pl.university.unID=?";
		return hibernateTemplate.find(hql,admin.getUniversity().getUnID());
	}

	@Override
	public List<ProblemLibTable> getCProblemLibs(ManagerTable admin) {
		String hql = "from ProblemLibTable pl where pl.visit=4 or(pl.visit=3 and pl.university.unID=?) or pl.college.ceID=?";
		return hibernateTemplate.find(hql,new Object[]{admin.getUniversity().getUnID(), admin.getCollege().getCeID()});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getAccessableProblemLibs(com.anyview.entities.TeacherTable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemLibTable> getAccessableProblemLibs(TeacherTable teacher) {
		String hql = "from ProblemLibTable pl "
				+ "where pl.teacher.tid=? "
				+ "or (pl.visit=2 and pl.college.ceID=?) "
				+ "or (pl.visit=3 and pl.university.unID=?) "
				+ "or (pl.visit=4) "
				+ "or (pl.lid in (select plt.problemLib.lid from ProblemLibTeacherTable plt where plt.teacher.tid=?))";
		return hibernateTemplate.find(hql, new Object[]{teacher.getTid(), /*teacher.getCollege().getCeID(),*/ teacher.getUniversity().getUnID(), teacher.getTid()});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getTeacherCreateLibs(java.util.Map)
	 */
	@Override
	public List<ProblemLibTable> getTeacherCreateLibs(DetachedCriteria criteria, 
			Integer currentPage, Integer numPerPage, String orderField, String orderDirection) {
		if("asc".equalsIgnoreCase(orderDirection))
			criteria=criteria.addOrder(Order.asc(orderField));
		else
			criteria=criteria.addOrder(Order.desc(orderField));
		List<ProblemLibTable> libs = hibernateTemplate.findByCriteria(criteria, (currentPage-1)*numPerPage, numPerPage);
		return libs;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getTeacherCreateLibCount(java.util.Map)
	 */
	@Override
	public Integer getTeacherCreateLibCount(DetachedCriteria criteria) {
		criteria = criteria.setProjection(Projections.countDistinct("lid"));
		return (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#saveProblemLib(com.anyview.entities.ProblemLibTable)
	 */
	@Override
	public Integer saveProblemLib(ProblemLibTable lib) {
		return (Integer) hibernateTemplate.save(lib);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#saveProblemLibTeachers(java.lang.Integer, java.lang.Integer[])
	 */
	@Override
	public void saveProblemLibTeachers(Integer lid, Integer[] tids) {
		ProblemLibTable lib = new ProblemLibTable(lid);
		//每保存20条数据就清一次session，避免hibernate一级缓存消耗太多导致性能下降
		for(int i=0;i<tids.length;i++){
			TeacherTable tea = new TeacherTable(tids[i]);
			ProblemLibTeacherTable lt = new ProblemLibTeacherTable();
			lt.setProblemLib(lib);
			lt.setTeacher(tea);
			lt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			hibernateTemplate.save(lt);
			if(i%20==0){
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getProblemLibByLid(java.lang.Integer)
	 */
	@Override
	public ProblemLibTable getProblemLibByLid(Integer lid) {
		return (ProblemLibTable) hibernateTemplate.get(ProblemLibTable.class, lid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getAccessableTeachers(java.lang.Integer)
	 */
	@Override
	public List<TeacherTable> getAccessableTeachers(Integer lid) {
		String hql = "select plt.teacher from ProblemLibTeacherTable plt where plt.problemLib.visit=1 and plt.problemLib.lid=?";
		return hibernateTemplate.find(hql, lid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#updateProblemLib(com.anyview.entities.ProblemLibTable)
	 */
	@Override
	public void updateProblemLib(ProblemLibTable lib) {
		String hql = "update ProblemLibTable set lname=?,visit=?,kind=?,updateTime=? where lid=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{lib.getLname(),lib.getVisit(),lib.getKind(),lib.getUpdateTime(),lib.getLid()});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#deleteAllAccessTeachers(java.lang.Integer)
	 */
	@Override
	public void deleteAllAccessTeachers(Integer lid) {
		String hql = "delete from ProblemLibTeacherTable where problemLib.lid=?";
		hibernateTemplate.bulkUpdate(hql,lid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#getProblemChapByLid(java.lang.Integer)
	 */
	@Override
	public Integer getProblemChapByLid(Integer lid) {
		String hql = "select count(chId) from ProblemChapTable where problemLib.lid=?";
		return Integer.valueOf(hibernateTemplate.find(hql,lid).get(0).toString());
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemLibDao#deleteProblemLib(com.anyview.entities.ProblemLibTable)
	 */
	@Override
	public void deleteProblemLib(ProblemLibTable lib) {
		hibernateTemplate.delete(lib);
	}

	@Override
	public List<ProblemLibTable> getLibsCreateByTeacher(Integer tid) {
		String hql = "from ProblemLibTable l where l.teacher.tid=?";
		return hibernateTemplate.find(hql, tid);
	}

	@Override
	public List<ProblemLibTable> getLibsInLibTeacherTable(Integer tid) {
		String hql = "select pl.problemLib from ProblemLibTeacherTable pl where pl.teacher.tid=?";
		return hibernateTemplate.find(hql, tid);
	}

	@Override
	public List<ProblemLibTable> getCLibsInCollege(Integer ceId) {
		String hql = "from ProblemLibTable l where l.visit=2 and EXISTS(select 1 from CollegeTeacherTable ct where ct.teacher.tid=l.teacher.tid and ct.college.ceID=?)";
		return hibernateTemplate.find(hql, ceId);
	}

	@Override
	public List<ProblemLibTable> getULibsInCollege(Integer ceId) {
		String hql = "from ProblemLibTable l where l.visit=3 and EXISTS(select 1 from CollegeTeacherTable ct where ct.teacher.tid=l.teacher.tid and ct.college.ceID=?)";
		return hibernateTemplate.find(hql, ceId);
	}

	@Override
	public List<ProblemLibTable> getAllPublishLibsInCollege(Integer ceId) {
		String hql = "from ProblemLibTable l where l.visit=4 and EXISTS(select 1 from CollegeTeacherTable ct where ct.teacher.tid=l.teacher.tid and ct.college.ceID=?)";
		return hibernateTemplate.find(hql, ceId);
	}

	@Override
	public List<ProblemLibTable> getULibsInUniv(Integer unId) {
		String hql = "from ProblemLibTable l where l.visit=3 and l.university.unID=?";
		return hibernateTemplate.find(hql, unId);
	}

	@Override
	public List<ProblemLibTable> getAllPublishLibsInUniv(Integer unId) {
		String hql = "from ProblemLibTable l where l.visit=4 and l.university.unID=?";
		return hibernateTemplate.find(hql, unId);
	}

}
