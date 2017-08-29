package com.anyview.dao.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.anyview.dao.CourseDao;
import com.anyview.entities.CourseTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.TeacherTable;


@Component
public class CourseDaoImpl extends BaseDaoImpl implements CourseDao {

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<CourseTable> getCourses(Map param) {
		Integer admin=Integer.valueOf(param.get("admin").toString());
		Timestamp createDateStart = (Timestamp) param.get("createDateStart");
		Timestamp createDateEnd = (Timestamp) param.get("createDateEnd");
		Timestamp updateDateStart = (Timestamp) param.get("updateDateStart");
		Timestamp updateDateEnd = (Timestamp) param.get("updateDateEnd");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		Integer pageNum=(Integer) param.get("pageNum");
		Integer pageSize=(Integer) param.get("pageSize");
		CourseTable course=new CourseTable();
		Criteria criteria=null;
		if(admin == 0)
			return null;
		else
		{
			criteria=getSession().createCriteria(CourseTable.class);
		 if(param.get("unID")!=null)
			{
				criteria=criteria.createAlias("university", "u").add(Restrictions.eq("u.unID", param.get("unID")));
						             
			 if(param.get("ceID")!=null)
			 {
				 criteria=criteria.createAlias("college","c").add(Restrictions.eq("c.ceID", param.get("ceID")));
			 }
			 criteria=criteria.setFirstResult((pageNum-1)*pageSize)
             .setMaxResults(pageSize);	
			}
			
			
		}
		if(course.getCourseName()!=null && !course.getCourseName().isEmpty()) criteria.add(Restrictions.ilike("coursename", course.getCourseName(),MatchMode.ANYWHERE));//模糊匹配-%word%
		if(course.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", course.getEnabled()));
		if(course.getCategory()!=null && !course.getCategory().isEmpty()) criteria.add(Restrictions.ilike("category",course.getCategory()));
		if(createDateStart!=null && createDateEnd!=null)
			criteria = criteria.add(Restrictions.between("createTime", createDateStart, createDateEnd));
		if(updateDateStart!=null && updateDateEnd!=null)
			criteria = criteria.add(Restrictions.between("updateTime", updateDateStart, updateDateEnd));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		
		List courses = criteria.list();
		return courses;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void addCourse(String courseName, Integer ceID, 
			Integer unID, String category, Integer enabled)
	{
		final String sql = "insert into coursetable(CourseName,CeID,UnID,Category,Enabled,UpdateTime) "
							+ "values(?,?,?,?,?,?)";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter(0, courseName);
		query.setParameter(1, ceID);
		query.setParameter(2, unID);
		query.setParameter(3, category);
		query.setParameter(4, enabled);
		query.setParameter(5, new Timestamp(System.currentTimeMillis()));
		query.executeUpdate();
	}
	

	public boolean checkDuplicateCourse(String courseName, Integer ceID, Integer unID)
	{
		String hql = "from CourseTable where CourseName=? and CeID=? and UnID=?";
		List list = hibernateTemplate.find(hql, new Object[]{courseName, ceID, unID});
		if(list.size() == 0)
			return false;
		else
			return true;
		
	}
	
	
	public List<CollegeTable> getCollegeByUnID(Integer unID)
	{
		String hql = "from CollegeTable where UnID=?";
		List<CollegeTable> list = hibernateTemplate.find(hql, new Object[]{unID});
		return list;
	}
	
	public void deleteCourseByCourseID(Integer courseId) throws HibernateException
	{
		String sql = "delete from CourseTable where CourseID=?";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter(0, courseId);
		query.executeUpdate();
		
	}
	

	public void editCourseByCourseID(Integer courseId, String courseName, Integer ceID, 
					Integer unID, String category, Integer enabled)
	{
		final String sql = "update coursetable set CourseName=?,CeID=?,UnID=?,Category=?,Enabled=?,UpdateTime=? "
				+ "where CourseID=?";
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter(0, courseName);
		query.setParameter(1, ceID);
		query.setParameter(2, unID);
		query.setParameter(3, category);
		query.setParameter(4, enabled);
		query.setParameter(5, new Timestamp(System.currentTimeMillis()));
		query.setParameter(6, courseId);
		query.executeUpdate();
	}
	
	
	public List<CourseTable> getCourseByCourseID(Integer courseId)
	{
		String hql = "from CourseTable where courseId=?";
		List<CourseTable> list = hibernateTemplate.find(hql, new Object[]{courseId});
		return list;
	}
	
	
	public List<CourseTable> getAllCourse()
	{
		try{
			final String hql = "select courseName,courseId from CourseTable" ;
			return hibernateTemplate.find(hql);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<CourseTable> getCourseByceID(Map param) {
		String hql = "from CourseTable c where c.enabled=1 and c.college.ceID = ?";
		return hibernateTemplate.find(hql,param.get("ceID"));
	}


	@Override
	public List<Object[]> getCourseINByCeId(Integer ceID) {
		String hql = "select c.courseId,c.courseName from CourseTable c where c.enabled=1 and c.college.ceID=?";
		return hibernateTemplate.find(hql,ceID);
	}


	/* (non-Javadoc)
	 * @see com.anyview.dao.CourseDao#getCourseByTidAndRight(com.anyview.entities.TeacherTable, java.lang.Integer)
	 */
	@Override
	public List<CourseTable> getCourseByTidAndRight(TeacherTable teacher,
			Integer right) {
		String hql = "select ctc.course from ClassTeacherCourseTable ctc where ctc.teacher.tid=? and (ctc.ctcright=3 or ctc.ctcright=?)";
		return hibernateTemplate.find(hql,new Object[]{teacher.getTid(), right});
	}


	/* (non-Javadoc)
	 * @see com.anyview.dao.CourseDao#getCourseByCourseId(java.lang.Integer)
	 */
	@Override
	public CourseTable getCourseByCourseId(Integer courseId) {
		return (CourseTable) hibernateTemplate.get(CourseTable.class, courseId);
	}
	
	//根据学院ceID在课程表中查询该学院拥有的课程数量
	public  Integer gainCourseCountByCeid(Integer ceId){
		try{
			final String hql = "select count(c.courseId) from com.anyview.entities.CourseTable c left join c.college as ce where ce.ceID = ?";
			Long total = (Long) hibernateTemplate.find(hql, ceId).get(0);
			return total.intValue();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}


	/* (non-Javadoc)
	 * @see com.anyview.dao.CourseDao#getCourseCount(com.anyview.entities.ManagerTable)
	 */
	@Override
	public Integer getCourseCount(Map param) {
		Integer admin=Integer.valueOf(param.get("admin").toString());
		Timestamp createDateStart = (Timestamp) param.get("createDateStart");
		Timestamp createDateEnd = (Timestamp) param.get("createDateEnd");
		Timestamp updateDateStart = (Timestamp) param.get("updateDateStart");
		Timestamp updateDateEnd = (Timestamp) param.get("updateDateEnd");
		CourseTable course=new CourseTable();
		Criteria criteria=null;
		if(admin == 0)
			return null;
		else
		{
			criteria=getSession().createCriteria(CourseTable.class);
			 if(param.get("unID")!=null)
			{
				criteria=criteria.createAlias("university", "u").add(Restrictions.eq("u.unID", param.get("ceID")));
						             
			 if(param.get("ceID")!=null)
			 {
				 criteria=criteria.createAlias("college","c").add(Restrictions.eq("c.ceID",param.get("ceID")));
			 }
			}
			
			
		}
		if(course.getCourseName()!=null && !course.getCourseName().isEmpty()) criteria.add(Restrictions.ilike("coursename", course.getCourseName(),MatchMode.ANYWHERE));//模糊匹配-%word%
//		if(tea.getTno()!=null && !tea.getTno().isEmpty()) criteria.add(Restrictions.ilike("tno", tea.getTno(),MatchMode.ANYWHERE));
//		if(tea.getTsex()!=null && !tea.getTsex().isEmpty()) criteria.add(Restrictions.eq("tsex", tea.getTsex()));
		if(course.getEnabled()!=null) criteria.add(Restrictions.eq("enabled", course.getEnabled()));
		if(course.getCategory()!=null && !course.getCategory().isEmpty()) criteria.add(Restrictions.ilike("category",course.getCategory()));
		if(createDateStart!=null && createDateEnd!=null)
			criteria = criteria.add(Restrictions.between("createTime", createDateStart, createDateEnd));
		if(updateDateStart!=null && updateDateEnd!=null)
			criteria = criteria.add(Restrictions.between("updateTime", updateDateStart, updateDateEnd));
		Integer count = (Integer) criteria.setProjection(Projections.countDistinct("courseId")).uniqueResult();
		return count;
}

	/* (non-Javadoc)
	 * @see com.anyview.dao.CourseDao#getCourseINFromClassTeacherCourse(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Object[]> getCourseINFromClassTeacherCourse(Integer tid,
			Integer cid) {
		String hql = "select ctc.course.courseId, ctc.course.courseName from ClassTeacherCourseTable ctc where ctc.teacher.tid=? and ctc.cla.cid=?";
		return hibernateTemplate.find(hql, new Object[]{tid, cid});
	}
	
}
	
	
