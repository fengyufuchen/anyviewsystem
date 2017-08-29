package com.anyview.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import com.anyview.dao.SemesterDao;
import com.anyview.entities.SemesterTable;

/**
 * 文件名：SemesterDaoImpl.java
 * 描   述：学期管理数据访问接口实现类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

@Component
public class SemesterDaoImpl extends BaseDaoImpl implements SemesterDao {
	
	/**
	 * 函数名：getSemesters
	 * 描   述：获取学期数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SemesterTable> getSemesters() {
		String hql="from SemesterTable";
		List<SemesterTable> sems = getHibernateTemplate().find(hql);
		if(sems != null && sems.size() > 0)
			return sems;
		return null;
	}
	
	/**
	 * 函数名：deleteSemester
	 * 描   述：根据学期ID删除对应的学期信息
	 */
	@Override
	public boolean deleteSemester(Integer sid) {		
		Session session = getSession();
		setHibernateTemplate();	
		SemesterTable s = (SemesterTable)session.get(SemesterTable.class, sid);
		session.delete(s);
		return false;
	}
	
	/**
	 * 函数名：updateSemester
	 * 描   述：修改学期
	 */
	@Override
	public boolean updateSemester(SemesterTable semester){	
		return updateObject(semester);
	}
	
	/**
	 * 函数名：getSemestersPage
	 * 描   述：获取学期管理页面数据
	 */
	@SuppressWarnings("unused")
	@Override
	public List<SemesterTable> getSemestersPage(Map param){
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		//SemesterTable sem = (SemesterTable) param.get("sem");
		//if(pageNum>=1)
		//	pageNum-=1;
		Criteria criteria = getSession().createCriteria(SemesterTable.class)
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize);
		List sems = criteria.list();
		return sems;
	}

	/**
	 * 函数名：getSemesterCount
	 * 描   述：获取学期总数
	 */
	@SuppressWarnings("unused")
	@Override
	public Integer getSemesterCount() {
		String hql="from SemesterTable";
		List<SemesterTable> sems = getHibernateTemplate().find(hql);
		if(sems != null && sems.size() > 0)
				return sems.size();
		return null;
	}

	/**
	 * 函数名：selectAllSemester
	 * 描   述：查找全部的学期信息
	 */
	@Override
	public List<SemesterTable> selectAllSemester() {
		String hql="from SemesterTable";
		List<SemesterTable> semesterList = getHibernateTemplate().find(hql);
		return semesterList;
	}

	/**
	 * 函数名：selectBySId
	 * 描   述：根据ID查找对应的学期
	 */
	@Override
	public SemesterTable selectBySId(Integer sid) {	
		return (SemesterTable)getHibernateTemplate().load(SemesterTable.class, sid);
	}

	/**
	 * 函数名：saveSemester
	 * 描   述：添加学期信息
	 */
	@Override
	public boolean saveSemester(SemesterTable semester) {
		return saveObject(semester);
	}		
}
