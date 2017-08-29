package com.anyview.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.ClassDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.utils.hibernate.HibernateUtils;

@Repository
public class ClassDaoImpl extends BaseDaoImpl implements ClassDao{

	@Override
	public List<ClassTable> getClasses(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		String hql = "select ct.cla from Class_TeacherTable ct where ct.teacher.tid = ?";
		if("asc".equalsIgnoreCase(orderDirection))
			hql+=" order by ct.cla."+orderField+" asc";
		else
			hql+=" order by ct.cla."+orderField+" desc";
		Query query = getSession().createQuery(hql);
		query.setInteger(0, (Integer)param.get("tid"));
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	@Override
	public List<ClassTable> getClassesByTid(Integer tid) {
		String hql = "from TeacherTable t left join fetch t.classes where t.tid=?";
		TeacherTable teacher = (TeacherTable)hibernateTemplate.find(hql, tid).get(0);
//		Set<ClassTable> claSet = teacher.getClasses();
//		List<ClassTable> claList = new ArrayList<ClassTable>();
//		for(ClassTable cla : claSet) 
//			claList.add(cla);
//		return claList;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassTable> getClassesByTea(Integer pageSize, Integer pageNum, TeacherTable tea) {
//		return hibernateTemplate.executeFind(new HibernateCallback() {
//			@Override
//			public Object doInHibernate(Session session) throws HibernateException,
//					SQLException {
//				String hql = "select ct.cla from ClassTeacherTable ct where ct.teacher.tid=?";
//				Query query = session.createQuery(hql);
//				query.setInteger(0, tid);
//				query.setFirstResult((pageNum-1)*pageSize);
//				query.setMaxResults(pageSize);
//				return query.list();
//			}
//		});
		
		/*
		 * 查询出教师在某个学院上的身份tiden
		 * 
		 * tiden=0普通教师，查出class_teacherTable中的班级
		 * tiden=1学院领导，查出此学院班级
		 * tiden=2学校领导，查出全校班级
		 */
		String hql = "";
		return null;
	}

	@Override
	public Integer getClassCountByTid(Integer tid) {
		String hql = "select count(ct.cla.cid) from ClassTeacherTable ct where ct.teacher.tid=?";
		return ((Long)hibernateTemplate.find(hql, tid).get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentTable> getStudentsPageByCid(final Integer pageSize,
			final Integer pageNum, Integer cid) {
		final String hql = "select distinct s from StudentTable s inner join s.cla as c where c.cid = " + cid;
		List<StudentTable> stus = hibernateTemplate.executeFind(new HibernateCallback() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				List list = HibernateUtils.getList(session, hql, (pageNum-1)*pageSize, pageSize);
				return list;
			}
		});
		return stus;
	}

	@Override
	public Integer getStudentCountByCid(Integer cid) {
		String hql = "select count(cs.id) from ClassStudentTable cs where cs.status = 1 and cs.cla.cid=?";
		Long total = (Long) hibernateTemplate.find(hql, cid).get(0);
		return total.intValue();
	}

	@Override
	public ClassTable getClassByCid(Integer cid) {
		return (ClassTable) getSession().get(ClassTable.class, cid);
	}

	@Override
	public void updateClass(ClassTable cla) {
		Session session = getSession();
		ClassTable c = (ClassTable) session.get(ClassTable.class, cla.getCid());
		c.setCname(cla.getCname());
		c.setEnabled(cla.getEnabled());
		c.setKind(cla.getKind());
		c.setSpecialty(cla.getSpecialty());
		c.setStartYear(cla.getStartYear());
		c.setStatus(cla.getStatus());
		Timestamp t = new Timestamp(System.currentTimeMillis());
		c.setUpdateTime(t);
		session.flush();
	}
	
	@Override
	public List<ClassTable> getAllClasses(){
		try{
			final String hql = "select cname,cid from ClassTable" ;
			return hibernateTemplate.find(hql);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteStudent(Integer sid) {
		String hql = "delete from StudentTable where sid=?";
		hibernateTemplate.bulkUpdate(hql, sid);
		hibernateTemplate.clear();
		hibernateTemplate.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStudentTable> getStudentsPage(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		Integer cid = Integer.valueOf(param.get("cid").toString());
		ClassStudentTable cs = (ClassStudentTable) param.get("classStudent");
		cs.setCla(new ClassTable(cid));
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class)
//				.createAlias("cla", "cla")
				.setFetchMode("student", FetchMode.JOIN).setFetchMode("cla", FetchMode.JOIN)
//				.createAlias("student", "student")
				.add(Restrictions.eq("cla.cid", cs.getCla().getCid()));
		if(cs.getStudent() != null){
			if(cs.getStudent().getSname()!=null && !cs.getStudent().getSname().isEmpty()) 
				criteria.add(Restrictions.ilike("student.sname", cs.getStudent().getSname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(cs.getStudent().getSno()!=null && !cs.getStudent().getSno().isEmpty()) 
				criteria.add(Restrictions.ilike("student.sno", cs.getStudent().getSno(),MatchMode.ANYWHERE));
			if(cs.getStudent().getSsex()!=null && !cs.getStudent().getSsex().isEmpty()) 
				criteria.add(Restrictions.eq("student.ssex", cs.getStudent().getSsex()));
			if(cs.getStudent().getEnabled()!=null) 
				criteria.add(Restrictions.eq("student.enabled", cs.getStudent().getEnabled()));
			if(cs.getStudent().getLoginStatus()!=null) 
				criteria.add(Restrictions.eq("student.loginStatus", cs.getStudent().getLoginStatus()));
		}
		if(cs.getSattr()!=null){
			criteria.add(Restrictions.eq("sattr", cs.getSattr()));
		}
		if(cs.getStatus()!=null){
			criteria.add(Restrictions.eq("status", cs.getStatus()));
		}
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc("student."+orderField));
		else
			criteria = criteria.addOrder(Order.desc("student."+orderField));
		List<ClassStudentTable> css = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return css;
	}

	@Override
	public Integer getStudentCount(Map param) {
		Integer cid = Integer.valueOf(param.get("cid").toString());
		ClassStudentTable cs = (ClassStudentTable) param.get("classStudent");
		cs.setCla(new ClassTable(cid));
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class)
//				.createAlias("cla", "cla")
				.setFetchMode("student", FetchMode.JOIN).setFetchMode("cla", FetchMode.JOIN)
//				.createAlias("student", "student")
				.add(Restrictions.eq("cla.cid", cs.getCla().getCid()));
		if(cs.getStudent() != null){
			if(cs.getStudent().getSname()!=null && !cs.getStudent().getSname().isEmpty()) 
				criteria.add(Restrictions.ilike("student.sname", cs.getStudent().getSname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(cs.getStudent().getSno()!=null && !cs.getStudent().getSno().isEmpty()) 
				criteria.add(Restrictions.ilike("student.sno", cs.getStudent().getSno(),MatchMode.ANYWHERE));
			if(cs.getStudent().getSsex()!=null && !cs.getStudent().getSsex().isEmpty()) 
				criteria.add(Restrictions.eq("student.ssex", cs.getStudent().getSsex()));
			if(cs.getStudent().getEnabled()!=null) 
				criteria.add(Restrictions.eq("student.enabled", cs.getStudent().getEnabled()));
			if(cs.getStudent().getLoginStatus()!=null) 
				criteria.add(Restrictions.eq("student.loginStatus", cs.getStudent().getLoginStatus()));
		}
		if(cs.getSattr()!=null){
			criteria.add(Restrictions.eq("sattr", cs.getSattr()));
		}
		if(cs.getStatus()!=null){
			criteria.add(Restrictions.eq("status", cs.getStatus()));
		}
		criteria = criteria.setProjection(Projections.countDistinct("student.sid"));
		return (Integer)hibernateTemplate.findByCriteria(criteria).get(0);
	}

	@Override
	public List<StudentTable> getStudents(Map param) {
		Integer cid = (Integer)param.get("cid");
		final String sql = "select s.sid,s.sname from studenttable s where s.sid in (select cs.sid from Class_StudentTable cs where cs.cid = 1)";
		List list = hibernateTemplate.executeFind(new HibernateCallback() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return list;
	}

	//根据学院ceId查询班级数量
	public Integer gainClassCountByCeid(Integer ceId){
		try{
			String hql = "select count(c.cid) from com.anyview.entities.ClassTable c left join c.college as ce where ce.ceID = ?";
			Long total = (Long) hibernateTemplate.find(hql, ceId).get(0);
			return total.intValue();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassDao#getClassFromClassTeacherCourse(java.lang.Integer)
	 */
	@Override
	public List<ClassTable> getClassFromClassTeacherCourse(Integer tid) {
		String hql = "select distinct ctc.cla from ClassTeacherCourseTable ctc where ctc.teacher.tid=?";
		return hibernateTemplate.find(hql, tid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassDao#changeClassStatus(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void changeClassStatus(Integer cid, Integer status) {
		String hql = "update ClassTable set status=? where cid=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{status, cid});
	}
}
