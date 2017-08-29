package com.anyview.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import com.anyview.dao.AdminClassDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

/**
 * @Description 班级管理数据访问接口实现类
 * @author DenyunFang
 * @time 2015年09月05日
 * @version 1.0
 */

@Component
public class AdminClassDaoImpl extends BaseDaoImpl implements AdminClassDao{

	/**
	 * 
	 * @Description: 根据学院ID获取所有班级
	 * @param ceID
	 * @return List<ClassTable> (返回该学院的所有班级列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月4日 下午4:01:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassTable> selectAllClassByCeId(Integer ceID) {
		try{
			String hql = "from ClassTable where college.ceID=?";
			return hibernateTemplate.find(hql,ceID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 
	 * @Description: 将Map集合中的班级信息进行封装
	 * @param param
	 * @param miden
	 * @return List<ClassTable> (封装了班级信息与页面信息的列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月4日 下午3:40:20
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ClassTable> getClassPage(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		ClassTable condition = (ClassTable) param.get("condition");
		Example example = Example.create(condition).setPropertySelector(new PropertySelector() {  
		    private static final long serialVersionUID = 1L;  
            
		    @Override  
		    public boolean include(Object propertyValue, String propertyName, Type type) {  
		        if (propertyValue == null) return false;  
		        if (propertyValue instanceof String)  
		            if (((String)propertyValue).length() == 0) return false;  
		        return true;  
		    }

			
		});
	
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTable.class)
				.createAlias("college", "c").add(example);
		if(condition.getCollege() != null && condition.getCollege().getCeID() != null){
			criteria = criteria.add(Restrictions.eq("c.ceID", condition.getCollege().getCeID()));
		}
		if(condition.getCollege().getUniversity() != null && condition.getCollege().getUniversity().getUnID() != null)
			criteria = criteria.add(Restrictions.eq("c.university.unID", condition.getCollege().getUniversity().getUnID()));
		
		
		//获取页面内容
//		if(mana.getMiden() == 1){//校级管理员
//			criteria = criteria.createAlias("college", "c")
//					   .add(Restrictions.eq("c.university.unID", mana.getUniversity().getUnID()));
//			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
//				criteria = criteria.add(Restrictions.eq("c.ceID", condition.getCollege().getCeID()));
//		}
//		else if(mana.getMiden() == 0){//院级管理员
//			criteria = criteria.createAlias("college", "c")
//					   .add(Restrictions.eq("c.ceID", mana.getCollege().getCeID()));	 
//		}	
//		else if(mana.getMiden() == -1){//超级管理员
//			criteria = criteria.createAlias("college", "c");
//			if(condition.getUniversity() != null && condition.getUniversity().getUnID() != null)
//				criteria = criteria.add(Restrictions.eq("c.university.unID", condition.getUniversity().getUnID()));
//			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
//				criteria = criteria.add(Restrictions.eq("c.ceID", condition.getCollege().getCeID()));
//		}
//		criteria.setProjection(Projections.projectionList()
//				.add(Projections.groupProperty("c.university.unID"))
//				.add(Projections.groupProperty("c.ceID"))
//				);
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		List<ClassTable> clas = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return clas;
	}

	/**
	 * 
	 * @Description: 根据管理员类型获取数据库中的班级总数
	 * @param param
	 * @return count(返回数据库中的班级总数)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月4日 下午3:40:39
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getClassCount(Map param) {
		ClassTable condition = (ClassTable) param.get("condition");
		Example example = Example.create(condition);
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTable.class)
				.createAlias("college", "c")
				.add(example);
		if(condition.getCollege() != null && condition.getCollege().getCeID() != null){
			criteria = criteria.add(Restrictions.eq("c.ceID", condition.getCollege().getCeID()));
		}
		if(condition.getCollege().getUniversity() != null && condition.getCollege().getUniversity().getUnID() != null)
			criteria = criteria.add(Restrictions.eq("c.university.unID", condition.getCollege().getUniversity().getUnID()));
		criteria = criteria.setProjection(Projections.countDistinct("cid"));
		return (Integer)hibernateTemplate.findByCriteria(criteria).get(0);
		
	}

	/**
	 * 
	 * @Description: TODO(将Map集合中的学生信息进行封装) 
	 * @param param
	 * @return Pagination<ClassStudentTable> (封装了班级学生信息与页面信息的集合)
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午3:45:40
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ClassStudentTable> getLookStudentPage(Map param){
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		ClassStudentTable cs = (ClassStudentTable) param.get("classStudent");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class)
				.setFetchMode("student", FetchMode.JOIN).setFetchMode("cla", FetchMode.JOIN)
				.createAlias("student", "s").add(Restrictions.eq("cla.cid", cs.getCla().getCid()));
		if(cs.getStudent() != null){
			if(cs.getStudent().getSname()!=null && !cs.getStudent().getSname().isEmpty()) 
				criteria.add(Restrictions.ilike("s.sname", cs.getStudent().getSname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(cs.getStudent().getSno()!=null && !cs.getStudent().getSno().isEmpty()) 
				criteria.add(Restrictions.ilike("s.sno", cs.getStudent().getSno(),MatchMode.ANYWHERE));
			if(cs.getStudent().getSsex()!=null && !cs.getStudent().getSsex().isEmpty()) 
				criteria.add(Restrictions.eq("s.ssex", cs.getStudent().getSsex()));
		}
		if(cs.getSattr()!=null){
			criteria.add(Restrictions.eq("sattr", cs.getSattr()));
		}
		if(cs.getStatus()!=null){
			criteria.add(Restrictions.eq("status", cs.getStatus()));
		}
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc("s."+orderField));
		else
			criteria = criteria.addOrder(Order.desc("s."+orderField));
		List<ClassStudentTable> css = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return css;
	}
	
	/**
	 * 
	 * @Description: TODO(获取数据库中的班级学生总数) 
	 * @param param
	 * @return count(返回数据库中的班级学生总数)
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午3:48:34
	 */
	@SuppressWarnings("rawtypes") 
	public Integer getLookStudentCount(Map param){
		ClassStudentTable cs = (ClassStudentTable) param.get("classStudent");
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class)
				.setFetchMode("student", FetchMode.JOIN).setFetchMode("cla", FetchMode.JOIN)
				.createAlias("student", "s").add(Restrictions.eq("cla.cid", cs.getCla().getCid()));
		if(cs.getStudent() != null){
			if(cs.getStudent().getSname()!=null && !cs.getStudent().getSname().isEmpty()) 
				criteria.add(Restrictions.ilike("s.sname", cs.getStudent().getSname(),MatchMode.ANYWHERE));//模糊匹配-%word%
			if(cs.getStudent().getSno()!=null && !cs.getStudent().getSno().isEmpty()) 
				criteria.add(Restrictions.ilike("s.sno", cs.getStudent().getSno(),MatchMode.ANYWHERE));
			if(cs.getStudent().getSsex()!=null && !cs.getStudent().getSsex().isEmpty()) 
				criteria.add(Restrictions.eq("s.ssex", cs.getStudent().getSsex()));
		}
		if(cs.getSattr()!=null){
			criteria.add(Restrictions.eq("sattr", cs.getSattr()));
		}
		if(cs.getStatus()!=null){
			criteria.add(Restrictions.eq("status", cs.getStatus()));
		}
		criteria = criteria.setProjection(Projections.countDistinct("s.sid"));
		return (Integer)hibernateTemplate.findByCriteria(criteria).get(0);
	}
	
	
	/**
	 * 
	 * @Description: 根据班级ID删除数据库的班级及该班级下的所有学生
	 * @param cid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午2:43:06
	 */
	@Override
	public boolean deleteClassByCid(Integer cid) {
		try {
			String hql = "delete from ClassStudentTable where cla.cid=?";
			hibernateTemplate.bulkUpdate(hql,cid);
			hql = "delete from ClassTable where cid = ?";
			hibernateTemplate.bulkUpdate(hql,cid);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @Description: 根据班级ID获取该班级所有信息
	 * @param cid
	 * @return List<ClassTable> (返回该班级的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午4:10:37
	 */
	@Override
	public ClassTable gainClassByCid(Integer cid) {
		Session session = getSession();
		ClassTable cla = (ClassTable) session.get(ClassTable.class, cid);
		return cla;
	}


	/**
	 * 
	 * @Description: 修改数据库的班级信息
	 * @param clas
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:18:11
	 */
	@Override
	public boolean updateClass(ClassTable clas) {
		Session session = getSession();
		ClassTable ncla = (ClassTable) session.get(ClassTable.class, clas.getCid());
		ncla.setSpecialty(clas.getSpecialty());
		ncla.setCname(clas.getCname());
		ncla.setStartYear(clas.getStartYear());
		ncla.setEnabled(clas.getEnabled());
		ncla.setStatus(clas.getStatus());
		Timestamp t = new Timestamp(System.currentTimeMillis());
		ncla.setUpdateTime(t);
		return saveOrUpdateObject(ncla);
	}
	
	/**
	 * 
	 * @Description: 修改数据库的班级学生信息 
	 * @param cs
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:07:44
	 */
	@Override
	public boolean updateStudentInClass(ClassStudentTable cs){
		String hql = "from ClassStudentTable where cla.cid=? and student.sid=?";
		ClassStudentTable cc = (ClassStudentTable) hibernateTemplate.find(hql, new Object[]{cs.getCla().getCid(), cs.getStudent().getSid()}).get(0);//唯一性用数据库索引约束
		
		cc.setSattr(cs.getSattr());
		cc.setStatus(cs.getStatus());
		Timestamp t = new Timestamp(System.currentTimeMillis());
		cc.setUpdateTime(t);
		return saveOrUpdateObject(cc);
	}
	
	/**
	 * 
	 * @Description: 获取所有学校
	 * @return List<UniversityTable> (返回所有学校列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午6:39:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UniversityTable> selectAllUniversity() {
		try{
			String hql="from UniversityTable";
			List<UniversityTable> un = hibernateTemplate.find(hql);
			return un;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 
	 * @Description: 根据学校ID获取所有学院 
	 * @param UnID
	 * @return List<CollegeTable> (返回该学校的所有学院列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午6:40:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeTable> selectAllCollegeByUnID(Integer UnID) {
		try{
			String hql="from CollegeTable where university.unID=?";
			List<CollegeTable> ce = hibernateTemplate.find(hql, UnID);
			return ce;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: 保存班级信息到数据库中
	 * @param cla
	 * @return boolean (true表示保存成功，false表示保存失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午7:04:28
	 */
	@Override
	public boolean saveClass(ClassTable cla) throws TipException{
		Timestamp t = new Timestamp(System.currentTimeMillis());
		cla.setCreateTime(t);
		cla.setUpdateTime(t);
		return saveObject(cla);
	}


	/**
	 * 
	 * @Description: 根据学院ID获取班级 
	 * @param ceId
	 * @return 班级ID和班级名字
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月18日 下午8:56:23
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getEnabledClassByCeId(Integer ceId) {
		try{
			String hql = "select c.cid,c.cname from ClassTable c where c.enabled=1 and c.college.ceID=?";
			return hibernateTemplate.find(hql,ceId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(根据cid和sid获取ClassStudentTable) 
	 * @param cid
	 * @param sid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:07:57
	 */
	@Override
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid){
		String hql = "from ClassStudentTable where cla.cid=? and student.sid=?";
		return (ClassStudentTable) hibernateTemplate.find(hql, new Object[]{cid, sid}).get(0);//唯一性用数据库索引约束
	}
	
	/**
	 * 
	 * @Description: TODO(根据cid和sid删除班级学生关系) 
	 * @param cid
	 * @param sid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:07:57
	 */
	@Override
	public boolean deleteStudentInClass(Integer cid, Integer sid){
		try {
			String hql = "delete from ClassStudentTable where cla.cid=? and student.sid=?";
			hibernateTemplate.bulkUpdate(hql,new Object[]{cid, sid});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
