package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.anyview.dao.CollegeDao;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;

//@Repository
@Component
public class CollegeDaoImpl extends BaseDaoImpl implements CollegeDao{

	//通过学院id获取该学院的详细信息
	@Override
	public CollegeTable getCollegeById(Integer ceID) {
		CollegeTable col = (CollegeTable) hibernateTemplate.get(CollegeTable.class, ceID);
//		Set<TeacherTable> set = col.getTeachers();
//		System.out.println(set.size());//cannot delete!set is lazy initilaized
		return col;
	}

	//通过学校ID获取该学校的所有下属学院
	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeTable>getCollegesByUnid(Integer unId){
		String hql = "from CollegeTable where UnID=?";
		List<CollegeTable> colleges = hibernateTemplate.find(hql, new Object[]{unId});
		return colleges;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCollegesINByUnId(Integer unID) {
		String hql = "select c.ceID,c.ceName from CollegeTable c where c.university.unID=?";
		return hibernateTemplate.find(hql,unID);
	}	
	
	 //获取学院总数
	public Integer getCollegeCount(){
		String hql="from CollegeTable";
		List<CollegeTable> colleges = getHibernateTemplate().find(hql);
		if(colleges != null && colleges.size() > 0)
				return colleges.size();
		return null;
	}
	
	//保存学院信息
	public boolean saveCollege(CollegeTable college){		
		return  saveObject(college);
	}
	
	 //获取全部的学院信息
  	@SuppressWarnings("unchecked")
	public List<CollegeTable> gainAllColleges(){
  		String hql = "from CollegeTable where CeID<>-1";
		return hibernateTemplate.find(hql);

  	}
  	
  	 //修改学院信息
  	public boolean updateCollege(CollegeTable college) throws Exception{
  		String hql = "update CollegeTable set ceName = ?,enabled = ?,createTime = ?,updateTime = ? ,university = ? where ceID = ?";
   	    int flag = hibernateTemplate.bulkUpdate(hql, new Object[]{
   	    		college.getCeName(),
   	    		college.getEnabled(),
   	    		college.getCreateTime(),
   	    		college.getUpdateTime(),
   	    		college.getUniversity(),
                college.getCeID()});
	 return flag>=1?true:false;
  	}
  	
 
  	
  	 //根据学院ID删除对应的学院信息
  	public boolean deleteCollege(Integer ceId){
  		Session session = getSession();
		setHibernateTemplate();	
		CollegeTable college = (CollegeTable)session.get(CollegeTable.class, ceId);
		session.delete(college);
		return false;
  	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getEnabledCollegesByUnId(Integer unId) {
		String hql = "select c.ceID,c.ceName from CollegeTable c where c.enabled=1 and c.university.unID=?";
		return hibernateTemplate.find(hql,unId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeTable> getCollegesByUnIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CollegeTable.class)
				.createAlias("university", "u").add(Restrictions.in("u.unID", ids));
		return hibernateTemplate.findByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeTable> getCollegesPage(Integer firseResult, Integer maxResults,
			DetachedCriteria criteria){
		return hibernateTemplate.findByCriteria(criteria,firseResult, maxResults);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getCollegeCount(final DetachedCriteria criteria){
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
}
