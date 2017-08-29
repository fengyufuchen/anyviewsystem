package com.anyview.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.anyview.dao.StudentsDao;
import com.anyview.dao.UniversityDao;
import com.anyview.entities.ChinaUniversityTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.ProvinceTable;
import com.anyview.entities.SemesterTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;

@Repository
public class UniversityDaoImpl extends BaseDaoImpl implements UniversityDao{

	@Override
	public List<UniversityTable> gainAllUniversities() {
		String hql = "from UniversityTable where UnID<>-1";
		return hibernateTemplate.find(hql);
	}

	@Override
	public UniversityTable getUniversityById(Integer unId) {
		UniversityTable un = (UniversityTable) getSession().get(UniversityTable.class, unId);
		return un;
	}
	//根据学校ID删除对应的学校信息
    public boolean deleteUniversity(Integer unId){
    	Session session = getSession();
		setHibernateTemplate();	
		UniversityTable univer = (UniversityTable)session.get(UniversityTable.class, unId);
		session.delete(univer);
		return false;
    }
    
     //修改学校信息
     public boolean updateUniversity(UniversityTable university){
    	 String hql = "update UniversityTable set unName=?,ip=?,port=?,attr=?,enabled=?,verification=?,updateTime=? where unID=?";
    	 int flag = hibernateTemplate.bulkUpdate(hql, new Object[]{
    			 university.getUnName(),
    			 university.getIp(),
    			 university.getPort(),
    			 university.getAttr(),
    			 university.getEnabled(),
    			 university.getVerification(),
    			 university.getUpdateTime(),
    			 university.getUnID()});
    	 return flag>=1?true:false;
     }
     
   //获取学校管理页面数据
 	public  List<UniversityTable> getUniversitysPage(Map param) {
 		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		Timestamp createDateStart = (Timestamp) param.get("createDateStart");
		Timestamp createDateEnd = (Timestamp) param.get("createDateEnd");
		UniversityTable condition=(UniversityTable) param.get("condition");
		Criteria criteria =getSession().createCriteria(UniversityTable.class)
				 			.setFirstResult((pageNum-1)*pageSize)
				 			.setMaxResults(pageSize);
		if(condition.getUnName()!=null&&!condition.getUnName().isEmpty())
			criteria.add(Restrictions.ilike("unName", condition.getUnName(),MatchMode.ANYWHERE));
		if(condition.getIp()!=null&&!condition.getIp().isEmpty())
			criteria.add(Restrictions.eq("ip", condition.getIp()));
		if(condition.getEnabled()!=null) criteria.add(Restrictions.eq("enabled",condition.getEnabled()));
		if(createDateStart!=null && createDateEnd!=null)
			criteria = criteria.add(Restrictions.between("createTime", createDateStart, createDateEnd));
		List<UniversityTable> univers = criteria.list();
		return univers;
 	}
 	
 	//获取学校总数
 	public Integer getUniversityCount(List<UniversityTable> univers){
 		if(univers!=null)
 			return univers.size();
 		return 0;
 	}
 	
 	//添加学校信息
 	public boolean saveUniversity(UniversityTable university){
 		return saveObject(university);
 	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.UniversityDao#gainAllEnabledUniversities()
	 */
	@Override
	public List<UniversityTable> gainAllEnabledUniversities() {
		String hql = "from UniversityTable where enabled=1 and unID>0";
		return hibernateTemplate.find(hql);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.UniversityDao#searchUniversity(java.lang.String)
	 */
	@Override
	public List<UniversityTable> searchUniversity(String param) {
		//待写
		return null;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.UniversityDao#searchUniversityByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UniversityTable> searchUniversityByName(String param) {
		String hql = "from UniversityTable where enabled=1 and unID<>-1 and unName like ?";
		return hibernateTemplate.find(hql,'%'+param+'%');
	}
	
}
