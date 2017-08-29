package com.anyview.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import com.anyview.dao.ProblemDao;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.TeacherTable;

@Component
public class ProblemDaoImpl extends BaseDaoImpl implements ProblemDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> getProblemChapIN(Map params) {
		String hql = "select pc.chId,pc.chName,"
				+ "(select count(npc.chId) from ProblemChapTable npc where npc.parentChap.chId=pc.chId) "
				+ "from ProblemChapTable pc "
				+ "where pc.problemLib.lid=? "
				+ "and pc.parentChap.chId=? "
				+ "and (pc.problemLib.teacher.tid=? or pc.visit=1)";
		return hibernateTemplate.find(hql,new Object[]{
				Integer.valueOf(params.get("lid").toString()),
				Integer.valueOf(params.get("parentId").toString()),
				Integer.valueOf(params.get("tid").toString())});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProblemTable> getProblemListByCh(Map params) {
		Integer visit = (Integer) params.get("visit");
		Integer currentPage = (Integer)params.get("currentPage");
		Integer numPerPage = (Integer)params.get("numPerPage");
		List<Integer> chIds = (List<Integer>) params.get("chIds");
		if(chIds == null || chIds.size()==0)
			return new ArrayList<ProblemTable>();
		Object[] chArr = new Object[chIds.size()];
		for(int i=0;i<chIds.size();i++)
			chArr[i]=chIds.get(i);
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class)
				.createAlias("problemChap", "ch").add(Restrictions.in("ch.chId", chArr));
		if(visit != 0){//非自建题库增加公开正式限制条件
			criteria = criteria.add(Restrictions.eq("visit", 1)).add(Restrictions.eq("status", 2));
		}
		/*criteria=criteria.setProjection(
				Projections.projectionList()
				.add(Projections.property("pid"),"pid")
				.add(Projections.property("pname"),"pname")
				.add(Projections.property("degree"),"degree")
				.add(Projections.property("kind"),"kind")
				.add(Projections.property("status"),"status")
				.add(Projections.property("visit"),"visit")
				.add(Projections.property("cacheSync"),"cacheSync")
				.add(Projections.property("problemChap"),"problemChap")
				.add(Projections.property("updateTime"),"updateTime")
				).setResultTransformer(Transformers.aliasToBean(ProblemTable.class));*/
		return hibernateTemplate.findByCriteria(criteria, (currentPage-1)*numPerPage, numPerPage);
	}

	@Override
	public Integer getProblemCountByCh(Map params) {
		Integer visit = (Integer) params.get("visit");
		List<Integer> chIds = (List<Integer>) params.get("chIds");
		if(chIds == null || chIds.size()==0)
			return 0;
		Object[] chArr = new Object[chIds.size()];
		for(int i=0;i<chIds.size();i++)
			chArr[i]=chIds.get(i);
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class)
				.createAlias("problemChap", "ch").add(Restrictions.in("ch.chId", chArr));
		if(visit != 0){//非自建题库增加公开正式限制条件
			criteria = criteria.add(Restrictions.eq("visit", 1)).add(Restrictions.eq("status", 2));
		}
		criteria = criteria.setProjection(Projections.countDistinct("pid"));
		return (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getTeacherCreateLibs(com.anyview.entities.TeacherTable)
	 */
	@Override
	public List<Object[]> getTeacherCreateLibs(TeacherTable teacher) {
		String hql = "select pl.lid,pl.lname from ProblemLibTable pl where pl.visit=0 and pl.teacher.tid=?";
		return hibernateTemplate.find(hql, teacher.getTid());
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getTeacherAccessableLibs(com.anyview.entities.TeacherTable)
	 */
	@Override
	public List<Object[]> getTeacherAccessableLibs(TeacherTable teacher) {
		String hql = "select plt.problemLib.lid,plt.problemLib.lname from ProblemLibTeacherTable plt where plt.problemLib.visit=1 and plt.teacher.tid=?";
		return hibernateTemplate.find(hql, teacher.getTid());
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getCollegePublicLibs(com.anyview.entities.TeacherTable)
	 */
	@Override
	public List<Object[]> getCollegePublicLibs(TeacherTable teacher) {
		String hql = "select pl.lid,pl.lname from ProblemLibTable pl where pl.visit=2 ";
		return hibernateTemplate.find(hql);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getUniversityPublicLibs(com.anyview.entities.TeacherTable)
	 */
	@Override
	public List<Object[]> getUniversityPublicLibs(TeacherTable teacher) {
		String hql = "select pl.lid,pl.lname from ProblemLibTable pl where pl.visit=3 and pl.university.unID=?";
		return hibernateTemplate.find(hql,teacher.getUniversity().getUnID());
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getAllPublicLibs()
	 */
	@Override
	public List<Object[]> getAllPublicLibs() {
		String hql = "select pl.lid,pl.lname from ProblemLibTable pl where pl.visit=4";
		return hibernateTemplate.find(hql);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getProblemByPid(java.lang.Integer)
	 */
	@Override
	public ProblemTable getProblemByPid(Integer pid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class).add(Restrictions.eq("pid", pid));
//		criteria=criteria.setProjection(
//				Projections.projectionList()
//				.add(Projections.property("pmemo"),"pmemo")
//				.add(Projections.property("ptip"),"ptip")
//				.add(Projections.property("pcontent"),"pcontent")
//				.add(Projections.property("problemChap"),"problemChap")
//				).setResultTransformer(Transformers.aliasToBean(ProblemTable.class));
		return (ProblemTable) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getProblemsByPids(java.lang.Integer[])
	 */
	@Override
	public List<ProblemTable> getProblemsByPids(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class).add(Restrictions.in("pid", ids));
		/*criteria=criteria.setProjection(
				Projections.projectionList()
				.add(Projections.property("pid"),"pid")
				.add(Projections.property("pname"),"pname")
				.add(Projections.property("degree"),"degree")
				.add(Projections.property("kind"),"kind")
				.add(Projections.property("status"),"status")
				.add(Projections.property("visit"),"visit")
				.add(Projections.property("cacheSync"),"cacheSync")
				.add(Projections.property("problemChap"),"problemChap")
				.add(Projections.property("updateTime"),"updateTime")
				).setResultTransformer(Transformers.aliasToBean(ProblemTable.class));*/
		return hibernateTemplate.findByCriteria(criteria);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getProblems(org.hibernate.criterion.DetachedCriteria, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ProblemTable> getProblems(DetachedCriteria criteria,
			Integer firstResult, Integer maxResults, String orderField,
			String orderDirection) {
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#getProblemsCount(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public Integer getProblemsCount(DetachedCriteria criteria) {
		criteria = criteria.setProjection(Projections.countDistinct("pid"));
		return (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#saveProblem(com.anyview.entities.ProblemTable)
	 */
	@Override
	public void saveProblem(ProblemTable problem) throws Exception {
		hibernateTemplate.save(problem);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#deleteProblem(java.lang.Integer)
	 */
	@Override
	public void deleteProblem(Integer pid) throws Exception {
		String hql = "delete from ProblemTable where pid=?";
		hibernateTemplate.bulkUpdate(hql, pid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ProblemDao#updateProblem(com.anyview.entities.ProblemTable)
	 */
	@Override
	public void updateProblem(ProblemTable problem) throws Exception {
		hibernateTemplate.update(problem);
	}

	@Override
	public List<ProblemTable> getOwnLibProblemsByChId(Integer chId) {
		String hql = "from ProblemTable where problemChap.chId = ? order by updateTime";
		return hibernateTemplate.find(hql, chId);
	}

	@Override
	public List<ProblemTable> getOtherLibProblemsByChId(Integer chId) {
		String hql = "from ProblemTable where problemChap.chId=? and status=2 and visit=1 order by updateTime";
		return hibernateTemplate.find(hql, chId);
	}

	@Override
	public void saveManyProblems(List<ProblemTable> list) {
		for(int i=0;i<list.size();i++){
			hibernateTemplate.save(list.get(i));
			// 批插入的对象立即写入数据库并释放内存  
			if(i % 10 == 0){
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
		}
	}

	@Override
	public List<String> getAllNamesByChId(Integer chId) {
		String hql = "select pname from ProblemTable where problemChap.chId = ?";
		return hibernateTemplate.find(hql, chId);
	}

}
