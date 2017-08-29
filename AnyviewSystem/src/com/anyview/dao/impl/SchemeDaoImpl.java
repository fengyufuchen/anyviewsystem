package com.anyview.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.management.relation.RelationService;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.SchemeDao;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;

@Repository
public class SchemeDaoImpl extends BaseDaoImpl implements SchemeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeTable> getSchemeList(Integer firstResult, Integer maxResults, DetachedCriteria criteria) {
		List<SchemeTable> schelist = hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
		return schelist;
	}

	@Override
	public Integer getSchemeCount(DetachedCriteria criteria) {
		return getCount(criteria);
	}
	
	public List<SchemeTable> getAllScheme(){
		try{
			final String hql = "select vid,vname from SchemeTable" ;
			return hibernateTemplate.find(hql);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SchemeContentTable> getSchemeProblemsList(Map params) {
		Integer vid = (Integer)params.get("vid");
		Integer numPerPage = (Integer) params.get("numPerPage");
		Integer currentPage = (Integer) params.get("currentPage");
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeContentTable.class).createAlias("scheme", "s").add(Restrictions.eq("s.vid", vid));
		List<SchemeContentTable> scs = hibernateTemplate.findByCriteria(criteria, (currentPage-1)*numPerPage, numPerPage);
		return scs;
	}

	@Override
	public Integer getSchemeProblemsCount(Map params) {
		Integer vid = (Integer)params.get("vid");
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeContentTable.class).createAlias("scheme", "s")
				.add(Restrictions.eq("s.vid", vid)).setProjection(Projections.countDistinct("id"));
		return (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#saveScheme(com.anyview.entities.SchemeTable)
	 */
	@Override
	public Integer saveScheme(SchemeTable scheme) {
		return (Integer) hibernateTemplate.save(scheme);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#saveSchemeContent(com.anyview.entities.SchemeTable, java.lang.String[][])
	 */
	@Override
	public void saveSchemeContent(SchemeTable scheme, List<SchemeContentTable> stList) {
		//每保存20条数据就清一次session，避免hibernate一级缓存消耗太多导致性能下降
		for(int i=0;i<stList.size();i++){
			hibernateTemplate.save(stList.get(i));
			if(i%20==0){
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getSchemeByVid(java.lang.Integer)
	 */
	@Override
	public SchemeTable getSchemeByVid(Integer vid) {
		return (SchemeTable) hibernateTemplate.get(SchemeTable.class, vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#updateScheme(com.anyview.entities.SchemeTable)
	 */
	@Override
	public void updateScheme(SchemeTable scheme) throws Exception {
		SchemeTable newScheme = (SchemeTable) hibernateTemplate.get(SchemeTable.class, scheme.getVid());
		newScheme.setCourse(scheme.getCourse());
		newScheme.setKind(scheme.getKind());
		newScheme.setStatus(scheme.getStatus());
		newScheme.setVisit(scheme.getVisit());
		newScheme.setVname(scheme.getVname().trim());
		newScheme.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		hibernateTemplate.update(newScheme);
		hibernateTemplate.flush();
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getSchemeProblemList(java.lang.Integer)
	 */
	@Override
	public List<ProblemTable> getSchemeProblemList(Integer vid) {
		String hql = "select sc.problem from SchemeContentTable sc where sc.scheme.vid=?";
		return hibernateTemplate.find(hql, vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getSchemeContentList(java.lang.Integer)
	 */
	@Override
	public List<SchemeContentTable> getSchemeContentList(Integer vid) {
		String hql = "from SchemeContentTable where scheme.vid = ? order by vchapName";
		return hibernateTemplate.find(hql, vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#deleteAllSchemeContentByVid(java.lang.Integer)
	 */
	@Override
	public void deleteAllSchemeContentByVid(Integer vid) {
		String hql = "delete from SchemeContentTable where scheme.vid = ?";
		hibernateTemplate.bulkUpdate(hql, vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getSchemeContentById(java.lang.Integer)
	 */
	@Override
	public SchemeContentTable getSchemeContentById(Integer scId) {
		return (SchemeContentTable) hibernateTemplate.get(SchemeContentTable.class, scId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#updateSchemeContent(com.anyview.entities.SchemeContentTable)
	 */
	@Override
	public void updateSchemeContent(SchemeContentTable sc) {
		SchemeContentTable sccc = (SchemeContentTable) hibernateTemplate.get(SchemeContentTable.class, sc.getId());
		sccc.setVpName(sc.getVpName());
		sccc.setVchapName(sc.getVchapName());
		sccc.setStartTime(sc.getStartTime());
		sccc.setFinishTime(sc.getFinishTime());
		sccc.setScore(sc.getScore());
		sccc.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		hibernateTemplate.flush();
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#updateSchemeFullScore(java.lang.Integer)
	 */
	@Override
	public void updateSchemeFullScore(Integer vid) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String hql = "update SchemeTable s set s.updateTime=?,s.fullScore=(select COALESCE(sum(score),0) from SchemeContentTable sc where sc.scheme.vid=?) where s.vid=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{now, vid,vid});
	}

	/* (non-Javadoc)
	 * @see com.any view.dao.SchemeDao#deleteSchemeContent(java.lang.Integer)
	 */
	@Override
	public void deleteSchemeContent(SchemeContentTable sc) {
		hibernateTemplate.delete(sc);

	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getSchemeINFromClassCourseScheme(java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSchemeINFromClassCourseScheme(final TeacherTable teacher, final ClassTable cla, final CourseTable course, final List<Integer> tids, final List<Integer> vids) {
		return hibernateTemplate.executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql = "select ccs.scheme.vid, ccs.scheme.vname "
						+ "from ClassCourseSchemeTable ccs "
						+ "where "
						+ "ccs.scheme.status = 2 and " //正式
						+ "ccs.status=1 and " 
						+ "ccs.scheme.kind=1 and "
						+ "ccs.cla.cid = :cid and "
						+ "ccs.course.courseId = :courseId and "
						+ "("
						+ "(ccs.scheme.visit=0 and ccs.teacher.tid=:tid) or "
						+ "(ccs.scheme.visit=1 and ccs.scheme.vid in (:vids)) or "
						+ "(ccs.scheme.visit=2 and ccs.teacher.tid in (:tids)) or "
						+ "(ccs.scheme.visit=3 and ccs.teacher.university.unID = :unID) or "
						+ "(ccs.scheme.visit=4)"
						+ ")";
				Query query = session.createQuery(hql);
				query.setInteger("cid", cla.getCid());
				query.setInteger("courseId", course.getCourseId());
				query.setInteger("tid", teacher.getTid());
				query.setParameterList("vids", vids);
				query.setParameterList("tids", tids);
				query.setInteger("unID", teacher.getUniversity().getUnID());
				return query.list();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.SchemeDao#getVidFromSchemeTeacher(java.lang.Integer)
	 */
	@Override
	public List<Integer> getVidFromSchemeTeacher(Integer tid) {
		String hql = "select st.scheme.vid from SchemeTeacherTable st where st.teacher.tid=?";
		return hibernateTemplate.find(hql, tid);
	}
	
	/**
	 * 
	 * @Description: TODO() 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午7:34:06
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SchemeContentTable> getSchemeContentPage(Map param){
		
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		
		ClassCourseSchemeTable conditionsch = (ClassCourseSchemeTable) param.get("conditionsch");
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeContentTable.class); 
		
		//获取页面内容
		criteria = criteria.createAlias("scheme", "s");
		if(conditionsch.getScheme() != null && conditionsch.getScheme().getVid() != null)			
			criteria = criteria.add(Restrictions.eq("s.vid", conditionsch.getScheme().getVid()));
		else
			criteria = criteria.add(Restrictions.eq("s.vid", -2));

		List<SchemeContentTable> ccs = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return ccs;
	}
	
	/**
	 * 
	 * @Description: TODO(获取作业表习题总数) 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午3:09:52
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getListSchemeContentCount(Map param){

		ClassCourseSchemeTable conditionsch = (ClassCourseSchemeTable) param.get("conditionsch");
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeContentTable.class);
		
		//获取页面内容
		criteria = criteria.createAlias("scheme", "s");
		if(conditionsch.getScheme() != null && conditionsch.getScheme().getVid() != null)
			criteria = criteria.add(Restrictions.eq("s.vid", conditionsch.getScheme().getVid()));
		else
			criteria = criteria.add(Restrictions.eq("s.vid", -2));
		criteria =  criteria.setProjection(Projections.countDistinct("id"));

		Integer count = (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
		return count;
	}
	
	/**
	 * 
	 * @Description: TODO(根据vid和pid获取schemecontent) 
	 * @param vid
	 * @param pid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年2月23日 下午5:27:18
	 */
	@SuppressWarnings("unchecked")
	public List<SchemeContentTable> getSchemeContentList(Integer vid, Integer pid){
		String hql = "from SchemeContentTable where scheme.vid = ? and problem.pid = ?";
		return hibernateTemplate.find(hql, new Object[]{vid, pid});
	}
	/**
	 * 根据课程id查询作业表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeTable> getSchemeListByCourseId(Integer courseId,
			TeacherTable teacher) {
		//查询出此教师自己创建的此课程上的所有作业表
		String hql="from SchemeTable where teacher.tid=? and course.courseId= ?";
		List<SchemeTable> list=hibernateTemplate.find(hql,new Object[]{teacher.getTid(),courseId});
		//查询其他教师创建的为学院公开的课程
		hql="from SchemeTable as st where st.teacher.tid!=? and st.course.courseId = ? and st.visit=2"
				+ " and  st.course.college.ceID in (select cct.college.ceID from CollegeTeacherTable as cct where cct.teacher.tid=? )";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),courseId,teacher.getTid()}));
		//查询其他教师创建的为学校公开的课程
		hql="from SchemeTable as st where st.teacher.tid!=? and st.course.courseId=? and st.visit=3 "
			+" and st.course.university.unID = ?";
		list.addAll(hibernateTemplate.find(hql,new Object[]{teacher.getTid(),courseId,teacher.getUniversity().getUnID()}));
		//查询其他教师创建的完全公开的作业表
		hql="from SchemeTable as st where st.teacher.tid!=? and st.course.courseId=? and st.visit=4";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),courseId}));
		//查询教师-权限作业表中的作业表
		hql="select stt.scheme from SchemeTeacherTable as stt where stt.teacher.tid= ? and stt.scheme.vid in(select st.vid from SchemeTable as st where st.course.courseId =?) ";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),courseId}));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeTable> getSchemeListByCeID(Integer ceId, TeacherTable teacher) {
		//查询教师自己创建的且课程所属学院为教师所属学院
		String hql="from SchemeTable where teacher.tid=? and course.college.ceID in (select ct.college.ceID from CollegeTeacherTable as ct where ct.teacher.tid=?)";
		List<SchemeTable> list=hibernateTemplate.find(hql,new Object[]{teacher.getTid(),teacher.getTid()});
	    //查询其他教师创建的学院公开的课程
		hql="from SchemeTable as st where st.teacher.tid!=?  and st.visit=2 and st.course.college.ceID=? "
				+ " and  st.course.college.ceID in (select cct.college.ceID from CollegeTeacherTable as cct where cct.teacher.tid=? )";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),ceId,teacher.getTid()}));
		//查询其他教师创建的学校公开的课程
		hql="from CollegeTable as ct where ceID = ?";
		CollegeTable college=(CollegeTable) hibernateTemplate.find(hql, new Object[]{ceId}).get(0);
		hql="from SchemeTable as st where st.teacher.tid!=?  and st.visit=3 and st.course.college.ceID=? "
				+ " and  st.course.university.unID=?";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),ceId,college.getUniversity().getUnID()}));
		//查询其他教师创建的完全公开的课程
		hql="from SchemeTable as st where st.teacher.tid!=?  and st.visit=4 and st.course.college.ceID=?";
		list.addAll(hibernateTemplate.find(hql, new Object[]{teacher.getTid(),ceId}));
		//查询出教师-权限表中的作业表
		hql="select stt.scheme from SchemeTeacherTable as stt where stt.scheme.course.college.ceID=? ";
		list.addAll(hibernateTemplate.find(hql, new Object[]{ceId}));
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeTable> getSchemeListByUnID(Integer unId, TeacherTable teacher) {
		//查询教师自己创建的所有作业表
		String hql="from SchemeTable as st where st.teacher.tid=? ";
		List<SchemeTable> list=hibernateTemplate.find(hql, new Object[]{teacher.getTid()});
		//查询其他教师创建的本校公开或者完全公开的
		hql="from SchemeTable as st where st.teacher.tid!=? and (st.visit=3 or st.visit=4) and"
				+ " st.teacher.university.unID=?";
		list.addAll(hibernateTemplate.find(hql,new Object[]{teacher.getTid(),teacher.getUniversity().getUnID()}));
		//查询教师-作业权限表中创建教师是本校的
		hql="select stt.scheme from SchemeTeacherTable as stt where stt.teacher.university.unID=?";
		list.addAll(hibernateTemplate.find(hql,new Object[]{teacher.getUniversity().getUnID()}));
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeTable> getSchemeListByOtherUnID(Integer unId,
			TeacherTable teacher) {
		//查询出教师所属学校并且访问级别是完全公开的
		String hql="from SchemeTable as st where st.teacher.tid=? and st.visit=4";
		List<SchemeTable> list=hibernateTemplate.find(hql, new Object[]{unId});
		//查询学校作业表中的作业表
		hql="select ust.scheme from UniversitySchemesTable as ust where ust.university.unID=?";
		list.addAll(hibernateTemplate.find(hql,new Object[]{unId}));
		return list;
	}

	@Override
	public List<SchemeTable> getSchemesByCidCourseIdTid(Integer tid,
			Integer cid, Integer courseId) {
		String hql = "select ccs.scheme from ClassCourseSchemeTable ccs where ccs.teacher.tid=? and ccs.cla.cid=? and ccs.course.courseId=?";
		List<SchemeTable> list=hibernateTemplate.find(hql, new Object[]{tid,cid,courseId});
		return list;
	}
	
}
