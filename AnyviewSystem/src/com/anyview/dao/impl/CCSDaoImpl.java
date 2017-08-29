package com.anyview.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.anyview.dao.CCSDao;

@Repository
public class CCSDaoImpl extends BaseDaoImpl implements CCSDao {

	@Override
	public List getCCSByClass(String classId) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
//		Query query=session.createQuery( "from CCSTable as ccs where ccs.classTable.cid =:cid ");
//		query.setInteger("cid", Integer.parseInt(classId));
//		return query.list();
		Query query=session.createSQLQuery("select b.courseId,vname,tname from schemetable a join class_course_schemetable b on a.vid=b.vid join teachertable c on b.tid=c.tid where cid = :cid and b.status=1");
		query.setInteger("cid", Integer.parseInt(classId));
		return query.list();
	}

	@Override
	public List getCCSByCourse(String courseId) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
//		Query query=session.createQuery( "from CCSTable as ccs where ccs.classTable.cid =:cid ");
//		query.setInteger("cid", Integer.parseInt(classId));
//		return query.list();
		Query query=session.createSQLQuery("select b.cId,vname,tname from schemetable a join class_course_schemetable b on a.vid=b.vid join teachertable c on b.tid=c.tid where b.courseId = :courseId and b.status=1");
		query.setInteger("courseId", Integer.parseInt(courseId));
		return query.list();
	}
	
	@Override
	public int settingCCS(String classId,String courseId,String schemeId,String teacherId,String status) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createSQLQuery("select * from class_course_schemetable where cId=:cId and courseId = :courseId");
		query.setInteger("cId", Integer.parseInt(classId));
		query.setInteger("courseId", Integer.parseInt(courseId));
		if(query.list().size()>0)
		{
			query=session.createSQLQuery("update class_course_schemetable set vid=:vid,tid=:tid,status=:status,updateTime=now() where cId=:cId and courseId = :courseId");
			
		}
		else{
			query=session.createSQLQuery("insert into class_course_schemetable values(:cId,:courseId,:vid,:tid,:status,now())");
			
		}
		query.setInteger("cId", Integer.parseInt(classId));
		query.setInteger("courseId", Integer.parseInt(courseId));
		query.setInteger("vid", Integer.parseInt(schemeId));
		query.setInteger("tid", Integer.parseInt(teacherId));
		query.setInteger("status", Integer.parseInt(status));
		return query.executeUpdate();
	}

}
