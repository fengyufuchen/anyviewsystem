package com.anyview.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.anyview.dao.TeacherDao;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CollegeTeacherTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemLibTeacherTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.SchemeTeacherTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.encryption.MD5Util;
import com.anyview.utils.hibernate.HibernateUtils;

@Component
public class TeacherDaoImpl extends BaseDaoImpl implements TeacherDao {

	@SuppressWarnings("unchecked")
	@Override
	public TeacherTable getTeacherByTnoAndUnId(String tno, Integer unId) {
		String hql = "from TeacherTable tt left join fetch tt.university u where tt.tno=? and u.unID=?";
		List list = hibernateTemplate.find(hql, new Object[]{tno,unId});
		if(list.size()==0)
			return null;
		TeacherTable tea = (TeacherTable) list.get(0);
		return tea;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTable> getTeachers() {
		String hql="from TeacherTable";
		List<TeacherTable> teas = getHibernateTemplate().find(hql);
		if(teas != null && teas.size() > 0)
			return teas;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTable> getTeachers(final Integer pageSize, final Integer pageNum) {
		final String hql = "select distinct t from TeacherTable t";
		List<TeacherTable> teachers = hibernateTemplate.executeFind(new HibernateCallback() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				List list = HibernateUtils.getList(session, hql, pageNum, pageSize);
				return list;
			}
		});
		return teachers;
	}
	
	@Override
	public Integer getTeacherCount() {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		String hql = "select count(t.tid) from TeacherTable t";
		Query query = session.createQuery(hql);
		Long totalNum = (Long) query.list().get(0);
		return totalNum.intValue();
	}

	@Override
	public void updateTeacher(TeacherTable tea) throws NoSuchAlgorithmException, UnsupportedEncodingException, HibernateException {
		Session session = getSession();
		TeacherTable t = (TeacherTable) session.get(TeacherTable.class, tea.getTid());
		t.setTname(tea.getTname());
		t.setEnabled(tea.getEnabled());
		t.setTpsw(MD5Util.getEncryptedPwd(tea.getTpsw()));
		t.setTsex(tea.getTsex());
		t.setTno(tea.getTno());
        t.setUniversity(tea.getUniversity());		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		t.setUpdateTime(time);
		session.flush();	
		
	}

	

	@Override
	public Integer getTeacherCountByMid(ManagerTable admin) {
//		String hql = "select count(t.tid) from TeacherTable t ";
		String hql = "";
		List list = null;
		if(admin.getMiden() == 0){
			hql = "select count(t.tid) from TeacherTable t left join t.college c where c.ceID = ?";
			list = hibernateTemplate.find(hql,admin.getCollege().getCeID());
		}else if(admin.getMiden() == 1){
			hql = "select count(t.tid) from TeacherTable t left join t.university u where u.unID = ?";
			list = hibernateTemplate.find(hql,admin.getUniversity().getUnID());
		}else if(admin.getMiden()== -1){
			hql = "select count(t.tid) from TeacherTable t ";
			list = hibernateTemplate.find(hql);
		}
		Long totalNum = (Long) list.get(0);
		return totalNum.intValue();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTable> getTeachers(Map param, ManagerTable admin) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		Criteria criteria = getSession().createCriteria(TeacherTable.class);
		if(admin.getMiden() == 0)
			criteria = criteria.createAlias("college", "c").add(Restrictions.eq("c.ceID", admin.getCollege().getCeID()));
		if(admin.getMiden() == 1)
			criteria = criteria.createAlias("university", "u").add(Restrictions.eq("u.unID", admin.getUniversity().getUnID()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		List list = criteria.setFirstResult((pageNum-1)*pageSize).setMaxResults(pageSize).list();
		return list;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTable> getTeachersByMid(Integer pageSize,
			Integer pageNum, ManagerTable admin ) {
		Criteria criteria = getSession().createCriteria(TeacherTable.class);
		if(admin.getMiden() == 0)
			criteria = criteria.createAlias("college", "c").add(Restrictions.eq("c.ceID", admin.getCollege().getCeID()));
		if(admin.getMiden() == 1)
			criteria = criteria.createAlias("university", "u").add(Restrictions.eq("u.unID", admin.getUniversity().getUnID()));
		
		List list = criteria.setFirstResult((pageNum-1)*pageSize).setMaxResults(pageSize).list();
		return list;
		
		
		
	}

	@Override
	public void addTeacher(TeacherTable tea) {
		Session session = getSession();
		ClassTable cla=new ClassTable();
		cla.setSpecialty(tea.getTname());
		CollegeTable college=new CollegeTable();
		college.setCeID(-1);
		cla.setEnabled(1);
		cla.setCollege(college);
		cla.setKind(1);
		cla.setCname(tea.getTname());
		cla.setEpId(1);
		cla.setSpecialty("教师");
		cla.setStatus(1);
		cla.setStartYear(2015);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		cla.setUpdateTime(time);
		session.save(cla);
		StudentTable stu=new StudentTable();
		stu.setSname(tea.getTname());
		stu.setEnabled(1);
		stu.setUniversity(tea.getUniversity());
		stu.setSno(tea.getTno());
		stu.setSpsw(tea.getTpsw());
		stu.setSsex(tea.getTsex());
		stu.setUpdateTime(time);
		stu.setCreateTime(time);
		stu.setLoginStatus(0);
		UniversityTable university=new UniversityTable();
		university.setUnID(tea.getUniversity().getUnID());
		stu.setUniversity(university);
		session.save(stu);
		tea.setTsId(stu.getSid());//映射学生
		tea.setTcId(cla.getCid()); //映射班级
		session.save(tea);
	}

	@Override
	public List<TeacherTable> getTeachersPage(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Integer unID = (Integer) param.get("unID");
		Integer ceID=(Integer) param.get("ceID");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		TeacherTable tea = (TeacherTable) param.get("tea");
		Criteria criteria = null;
		if(unID!=null&&ceID!=null)  //学院管理员
		{
			criteria = getSession().createCriteria(CollegeTeacherTable.class)
					.createAlias("college", "c").createAlias("teacher", "t").add(Restrictions.eq("c.ceID", ceID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize);	
			if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("teacher.tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("teacher.tno", tea.getTno(),MatchMode.ANYWHERE));
			if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("teacher.tsex", tea.getTsex()));
			if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("teacher.enabled", tea.getEnabled()));
			if("asc".equalsIgnoreCase(orderDirection))
				criteria = criteria.addOrder(Order.asc("t."+orderField));
			else
				criteria = criteria.addOrder(Order.desc("t."+orderField));
		}
		else if(unID==null&&ceID==null) //超级管理员
		{
			criteria = null;
			return new ArrayList();
			
		}
		
		else if(unID!=null&&ceID==null)  //学校管理员
		{
			criteria = getSession().createCriteria(TeacherTable.class)
					.createAlias("university", "u").add(Restrictions.eq("u.unID", unID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize);	
			if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("tno", tea.getTno(),MatchMode.ANYWHERE));
			if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("tsex", tea.getTsex()));
			if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", tea.getEnabled()));
			if("asc".equalsIgnoreCase(orderDirection))
				criteria = criteria.addOrder(Order.asc(orderField));
			else
				criteria = criteria.addOrder(Order.desc(orderField));
			
		}
	    
		

		
		List teas = criteria.list();
		return teas;
	}

	@Override
	public Integer getTeacherCountBySearch(Map param) {
		Integer count=0;
		Integer unID=(Integer) param.get("unID");
		Integer ceID=(Integer)param.get("ceID");
		
		ManagerTable admin=(ManagerTable) param.get("admin");
		TeacherTable tea = (TeacherTable) param.get("tea");
	 
		Criteria criteria = null;
		if(unID!=null&&ceID!=null)  //学院管理员
		{
			criteria = getSession().createCriteria(CollegeTeacherTable.class)
					.createAlias("college", "c").createAlias("teacher", "t").add(Restrictions.eq("c.ceID", ceID));	
			if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("teacher.tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("teacher.tno", tea.getTno(),MatchMode.ANYWHERE));
			if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("teacher.tsex", tea.getTsex()));
			if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("teacher.enabled", tea.getEnabled()));
			count = (Integer) criteria.setProjection(Projections.countDistinct("teacher.tid")).uniqueResult();
		}
		else if(unID==null&&ceID==null) //超级管理员
		{
			criteria = null;	
			return 0;
			
		}
		else if(unID!=null&&ceID==null)  //学校管理员
		{
			criteria = getSession().createCriteria(TeacherTable.class)
					.createAlias("university", "u").add(Restrictions.eq("u.unID", unID));	
			if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("tno", tea.getTno(),MatchMode.ANYWHERE));
			if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("tsex", tea.getTsex()));
			if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", tea.getEnabled()));
			count= (Integer) criteria.setProjection(Projections.countDistinct("tid")).uniqueResult();
		}
	    
		return count;
		
	}

	@Override
	public TeacherTable gainTeacherByTid(Integer tid) {
		Session session =getSession();
		TeacherTable tea=(TeacherTable)session.get(TeacherTable.class, tid);
		return tea;
	}

	@Override
	public List<Object[]> getTeacherINByCourseId(Integer courseId) {
		String hql = "select ctc.teacher.tid,ctc.teacher.tname from ClassTeacherCourseTable ctc where ctc.course.courseId=?";
		return hibernateTemplate.find(hql,courseId);
	}

	public List<TeacherTable> getAllTeacher(){
		try{
			final String hql = "select tid,tname from TeacherTable" ;
			return hibernateTemplate.find(hql);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void deleteTeacher(TeacherTable tea) {
		Session session=getSession();
		session.delete(tea);
		
	}

	@Override
	public void deleteClassByCid(Integer cid) {
	
		Session session=getSession();
		ClassTable cla=(ClassTable) session.get(ClassTable.class, cid);
		
		session.delete(cla);
		
		
		
	}

	@Override
	public void deleteProblemLibByTid(Integer tid) {
		Session session=getSession();
		Criteria criteria=getSession().createCriteria(ProblemLibTable.class)
				           .createAlias("teacher", "tea").add(Restrictions.eq("tea.tid", tid));
		List problemLibTable=criteria.list();
		
		Iterator<ProblemLibTable> problemLib=problemLibTable.iterator();
	    while(problemLib.hasNext())
	    {
	    	ProblemLibTable it=problemLib.next();
	    	CollegeTable college=new CollegeTable();
	    	college.setCeID(-1);
	        UniversityTable university=new UniversityTable();
	        university.setUnID(-1);
	        it.setUniversity(university);
	        TeacherTable tea=new TeacherTable();
	        tea.setTid(-1);
	        it.setTeacher(tea);
	    	
	       }
		
		
	}

	@Override
	public void deleteSchemeByTid(Integer tid) {
		Session session=getSession();
        Criteria criteria=getSession().createCriteria(SchemeTable.class)
        		           .createAlias("teacher", "tea").add(Restrictions.eq("tea.tid",tid));
        List schemeTable=criteria.list();
        Iterator< SchemeTable> scheme=schemeTable.iterator();
		while(scheme.hasNext())
		{
			SchemeTable it=scheme.next();
			session.delete(it);
			
		}
		
	
}

	@Override
	public void deleteSchemeTeacherByTid(TeacherTable tea) {
		Session session=getSession();
		Criteria criteria=getSession().createCriteria(SchemeTeacherTable.class)
				          .createAlias("teacher", "tea").add(Restrictions.eq("tea.tid", tea.getTid()));
		List schemeTeacherTable=criteria.list();
		Iterator<SchemeTeacherTable> schemeTeacher=schemeTeacherTable.iterator();
		while(schemeTeacher.hasNext())
		{
			SchemeTeacherTable it=schemeTeacher.next();
			tea.setTid(-1);
			it.setTeacher(tea);
			
		}
		
	}

	@Override
	public void deleteProblemLibTeahcerByTid(TeacherTable tea) {
		Session session=getSession();
		Criteria criteria=getSession().createCriteria(ProblemLibTeacherTable.class)
				          .createAlias("teacher", "tea").add(Restrictions.eq("tea.tid", tea.getTid()));
		List problemLibTeacherTable=criteria.list();
		Iterator<ProblemLibTeacherTable> problemLibTeacher=problemLibTeacherTable.iterator();
		while(problemLibTeacher.hasNext())
		{
			ProblemLibTeacherTable it=problemLibTeacher.next();
			tea.setTid(-1);
			it.setTeacher(tea);			
		}		
	}
	
	//根据学院ceID在教师表中查询该学院拥有的教师数量
	public  Integer gainTeacherCountByCeid(Integer ceId){
		try{
			final String hql = "select count(t.tid) from com.anyview.entities.TeacherTable t left join t.college as c where c.ceID = ?";
			Long total = (Long) hibernateTemplate.find(hql, ceId).get(0);
			return total.intValue();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.anyview.dao.TeacherDao#getEnabledTeacherByCeId(java.lang.Integer)
	 */
	@Override
	public List<Object[]> getEnabledTeacherByCeId(Integer ceId) {
		String hql = "select t.tid,t.tname from TeacherTable t where t.enabled=1 and t.college.ceID=?";
		return hibernateTemplate.find(hql,ceId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.TeacherDao#getEnabledTeachersByCeIds(java.lang.Integer[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getEnabledTeachersByCeIds(final Integer[] ids) {
		if(ids ==null || ids.length<=0)
			return null;
		return hibernateTemplate.executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				//有效：TeacherTable.enable=1 and CollegeTeacherTable.status=1
				String hql = "select ct.college.ceID, concat(ct.teacher.university.unName,'->',ct.college.ceName), ct.teacher.tid,ct.teacher.tname "
						+ "from CollegeTeacherTable ct "
						+ "where ct.teacher.enabled=1 and ct.status=1 and ct.college.ceID in (:ids)";
				return arg0.createQuery(hql).setParameterList("ids", Arrays.asList(ids)).list();
			}
		});
	}

	@Override
	public List<TeacherTable> getTeachersPageForSuper(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Integer unID = (Integer) param.get("unID");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		TeacherTable tea = (TeacherTable) param.get("tea");
		Criteria criteria = getSession().createCriteria(TeacherTable.class)
					.createAlias("university", "u").add(Restrictions.eq("u.unID", unID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize);	
		if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
		if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("tno", tea.getTno(),MatchMode.ANYWHERE));
		if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("tsex", tea.getTsex()));
		if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", tea.getEnabled()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		
		List teas = criteria.list();
		return teas;
	}

	@Override
	public Integer getTeacherCountForSuper(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Integer unID = (Integer) param.get("unID");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		TeacherTable tea = (TeacherTable) param.get("tea");
		Criteria criteria = null;
		criteria = getSession().createCriteria(TeacherTable.class)
					.createAlias("university", "u").add(Restrictions.eq("u.unID", unID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize)
					;	
		if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
		if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("tno", tea.getTno(),MatchMode.ANYWHERE));
		if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("tsex", tea.getTsex()));
		if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", tea.getEnabled()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		
		Integer count = (Integer) criteria.setProjection(Projections.countDistinct("tid")).uniqueResult();
		return count;
	}

	@Override
	public List<TeacherTable> getTeachersPageForCollege(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Integer unID = (Integer) param.get("unID");
		Integer ceID =(Integer)param.get("ceID");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		TeacherTable tea = (TeacherTable) param.get("tea");
		Criteria criteria = null;
		criteria = getSession().createCriteria(CollegeTeacherTable.class)
					.createAlias("college", "c").add(Restrictions.eq("c.ceID", ceID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize)
					;	
		if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("teacher.tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
		if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("teacher.tno", tea.getTno(),MatchMode.ANYWHERE));
		if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("teacher.tsex", tea.getTsex()));
		if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("teacher.enabled", tea.getEnabled()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		
		List teas = criteria.list();
		return teas;
	}

	@Override
	public Integer getTeacherCountForCollege(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Integer unID = (Integer) param.get("unID");
		Integer ceID =(Integer)param.get("ceID");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		TeacherTable tea = (TeacherTable) param.get("tea");
		Criteria criteria = null;
		criteria = getSession().createCriteria(CollegeTeacherTable.class)
					.createAlias("college", "c").add(Restrictions.eq("c.ceID", ceID))
					.setFirstResult((pageNum-1)*pageSize)
					.setMaxResults(pageSize);	
		if(tea.getTname()!=null && !tea.getTname().isEmpty()) criteria.add(Restrictions.ilike("teacher.tname", tea.getTname(),MatchMode.ANYWHERE));//模糊匹配-%word%
		if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("teacher.tno", tea.getTno(),MatchMode.ANYWHERE));
		if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("teacher.tsex", tea.getTsex()));
		if(tea.getEnabled()!=null) criteria.add(Restrictions.eq("teacher.enabled", tea.getEnabled()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		
		Integer count = (Integer) criteria.setProjection(Projections.countDistinct("tid")).uniqueResult();
		return count;
	}


	@Override
	public Integer getTeacherTiden(Integer tid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<CollegeTable> getCollegesByUnid(Integer unid)
	{
		Criteria criteria = getSession().createCriteria(CollegeTable.class)
				.createAlias("university", "u").add(Restrictions.eq("u.unID",unid));
		List c = criteria.list();
		return c;
	}


	@Override
	public void linkToCollege(TeacherTable tea, String ceid,Integer enabled) {
		Session session=getSession();
		CollegeTeacherTable collegeTable=new CollegeTeacherTable();
		collegeTable.setTeacher(tea);
		CollegeTable college= new CollegeTable();
		college.setCeID(Integer.valueOf(ceid));
		collegeTable.setCollege(college);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		collegeTable.setUpdateTime(time);
		collegeTable.setStatus(enabled);
		session.save(collegeTable);
		
		
	}


	/* (non-Javadoc)
	 * @see com.anyview.dao.TeacherDao#getSameCollegeTeacherId(java.lang.Integer)
	 */
	@Override
	public List<Integer> getSameCollegeTeacherId(Integer tid) {
		String hql = "select distinct ct.teacher.tid from CollegeTeacherTable ct where ct.status=1 and ct.college.ceID in (select ctt.college.ceID from CollegeTeacherTable ctt where ctt.teacher.tid=?)";
		return hibernateTemplate.find(hql, tid);
	}


	@Override
	public GradeRules getGradeRulesByTid(Integer tid) {
		String hql = "from GradeRules where tid=?";
		List list = hibernateTemplate.find(hql, tid);
		if(list.size() == 0)
			return null;
		return (GradeRules) list.get(0);
	}


	@Override
	public Integer addGraduRule(GradeRules rule) {
		hibernateTemplate.save(rule);
		return rule.getId();
	}


	@Override
	public void updateGradeRules(GradeRules rule) {
		hibernateTemplate.update(rule);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTable> getTeacherByCriteria(Integer pageNum,
			Integer pageSize, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
	}


	@Override
	public Integer getTeacherCountByCriteria(DetachedCriteria criteria) {
		return super.getCount(criteria);
	}


	@Override
	public void updateTeacher(TeacherTable teacher, Map map) throws Exception {
		// TODO Auto-generated method stub
		String newPwd=(String) map.get("newPwd");
		String tname=(String) map.get("tname");
		String tsex=(String) map.get("tsex");
		String hql="update TeacherTable set tpsw=?,tname=?,tsex=? where tno=?";
		hibernateTemplate.bulkUpdate(hql,new Object[]{newPwd,tname,tsex,teacher.getTno()});
		
	}


	@Override
	public List<CollegeTable> getColleges(Integer tid) {
		String hql = "select t.colleges from TeacherTable t where t.tid=?";
		return hibernateTemplate.find(hql, tid);
	}
	
	
}

	
	

	
	
