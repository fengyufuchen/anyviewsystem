package com.anyview.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.anyview.dao.ClassTeacherDao;
import com.anyview.dao.TeacherDao;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassTeacherManager;
import com.anyview.utils.hibernate.HibernateUtils;

@Component
public class ClassTeacherDaoImpl extends BaseDaoImpl implements ClassTeacherDao {

	@Override
	public Integer getTCRight(Integer tid, Integer cid) {
		String hql = "select tcRight from ClassTeacherTable where cla.cid=? and teacher.tid=?";
		return (Integer) hibernateTemplate.find(hql, new Object[]{cid, tid}).get(0);
	}
	
	@Override
	public void updateTCRight(Integer tcRight, Integer tid, Integer cid) {
		//sql, not hql
		String sql = "update Class_TeacherTable c set c.tcRight = ? where c.cid= ? and c.tid = ?";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setInteger(0, tcRight);
		query.setInteger(1, cid);
		query.setInteger(2, tid);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * @Description: TODO(根据教师id获取班级) 
	 * @param tid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午7:33:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<ClassTeacherTable> getClassByTId(Integer tid){
		try{
			String hql = "from ClassTeacherTable where teacher.tid=?";
			return (List<ClassTeacherTable>) hibernateTemplate.find(hql, tid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ClassTeacherTable> getTeachersOnClass(int firstResult,
			int maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	@Override
	public Integer getTeachersCountOnClass(DetachedCriteria criteria) {
		return getCount(criteria);
	}

	@Override
	public void saveTeacherToClass(Integer tid, Integer right, Integer cid,
			Timestamp now) {
		ClassTeacherTable ct = new ClassTeacherTable();
		ct.setTcRight(right);
		ct.setUpdateTime(now);
		ct.setCla(new ClassTable(cid));
		ct.setTeacher(new TeacherTable(tid));
		hibernateTemplate.save(ct);
	}

	@Override
	public void deleteTeacherOnClass(Integer tid, Integer cid) {
		String hql = "delete from ClassTeacherTable where teacher.tid=? and cla.cid = ?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{tid, cid});
	}

	@Override
	public ClassTeacherTable getClassTeacherByCidAndTid(Integer cid, Integer tid) {
		String hql = "from ClassTeacherTable where teacher.tid=? and cla.cid=?";
		return (ClassTeacherTable) hibernateTemplate.find(hql, new Object[]{tid, cid}).get(0);
	}


}
	
	
