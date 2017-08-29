package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.impl.CriteriaImpl.Subcriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassDao;
import com.anyview.dao.ClassTeacherDao;
import com.anyview.dao.TeacherDao;
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.ClassTeacherManager;

@Service
public class ClassTeacherManagerImpl extends BaseManagerImpl implements ClassTeacherManager{
	
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private ClassTeacherDao classTeacherDao;
	
	public List<TeacherTable> getTeachers( ) {
		List<TeacherTable> teachers = teacherDao.getTeachers();
		return teachers;
	}

	@Override
	public Pagination<TeacherTable> getTeacherPage(Integer pageSize, Integer pageNum) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> teachers = teacherDao.getTeachers(pageSize,pageNum);
		page.setContent(teachers);
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		page.setTotalCount(teacherDao.getTeacherCount());
		page.calcutePage();
		return page;
	}

	@Override
	public Integer getTCRight(Integer tid, Integer cid) {
		return classTeacherDao.getTCRight(tid,cid);
	}
	
	@Override
	public void updateTCRight (Integer tcRight, Integer tid, Integer cid) {
		classTeacherDao.updateTCRight(tcRight, tid, cid);
	}
	
	@Override
	public Pagination<ClassTeacherTable> getClassTeachers(Map param) {
		Integer numPerPage = (Integer) param.get("pageSize");
		Integer currentPage = (Integer) param.get("pageNum");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		ClassTeacherTable ct = (ClassTeacherTable) param.get("condition");
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTeacherTable.class, "ct")
				.createAlias("teacher", "teacher")
				.add(Restrictions.eq("cla.cid", ct.getCla().getCid()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		Pagination<ClassTeacherTable> page = new Pagination<ClassTeacherTable>();
		page.setTotalCount(classTeacherDao.getTeachersCountOnClass(criteria));
		List<ClassTeacherTable> cts = classTeacherDao.getTeachersOnClass((currentPage-1)*numPerPage,numPerPage,criteria);
		page.setContent(cts);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.calcutePage();
		return page;
	}

	@Override
	public void saveTeachersToClass(Map<String, Integer> map, Integer cid) {
		Set<String> keys = map.keySet();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		for(String tid:keys){
			classTeacherDao.saveTeacherToClass(Integer.valueOf(tid), map.get(tid), cid, now);
		}
	}

	@Override
	public void deleteTeacherOnClass(Integer tid, Integer cid) {
		classTeacherDao.deleteTeacherOnClass(tid, cid);
	}

	@Override
	public ClassTeacherTable getClassTeacherByCidAndTid(Integer cid, Integer tid) {
		return classTeacherDao.getClassTeacherByCidAndTid(cid, tid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassTeacherManager#getClassTeachersForCourse(java.util.Map)
	 */
	@Override
	public Pagination<ClassTeacherTable> getClassTeachersForCourse(Map param) {
		Integer numPerPage = (Integer) param.get("pageSize");
		Integer currentPage = (Integer) param.get("pageNum");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		ClassCourseTable ctc = (ClassCourseTable) param.get("condition");
		DetachedCriteria dc = DetachedCriteria.forClass(ClassTeacherCourseTable.class, "ctc")
				.add(Restrictions.eq("ctc.cla.cid", ctc.getCla().getCid()))
				.add(Restrictions.eq("ctc.course.courseId", ctc.getCourse().getCourseId()))
				.add(Property.forName("ctc.teacher.tid").eqProperty("ct.teacher.tid"))
				.setProjection(Property.forName("ctc.id"));
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTeacherTable.class, "ct")
				.createAlias("teacher", "teacher")
				.add(Restrictions.eq("cla.cid", ctc.getCla().getCid()))
				.add(Subqueries.notExists(dc));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		Pagination<ClassTeacherTable> page = new Pagination<ClassTeacherTable>();
		page.setTotalCount(classTeacherDao.getTeachersCountOnClass(criteria));
		List<ClassTeacherTable> cts = classTeacherDao.getTeachersOnClass((currentPage-1)*numPerPage,numPerPage,criteria);
		page.setContent(cts);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.calcutePage();
		return page;
	}

	@Override
	public Pagination<ClassTeacherTable> getClassesForTeacher(Map params) {
		Integer pageNum = Integer.valueOf(params.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
		String orderDirection = params.get("orderDirection").toString();
		String orderField = params.get("orderField").toString();
		Integer tid = Integer.valueOf(params.get("tid").toString());
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTeacherTable.class, "ct")
				.createAlias("cla", "cl")
				.createAlias("teacher", "tea")
				.add(Restrictions.eq("tea.tid",tid));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return this.getPageByCriteria(criteria, pageNum, pageSize);
	}

	@Override
	public ClassTeacherTable getClassTeacherTableById(Integer id) {
		return super.getEntityById(ClassTeacherTable.class, id);
	}

	@Override
	public Pagination<ClassTeacherCourseTable> getClassTeacherCoursePage(Map params) {
		Integer pageNum = Integer.valueOf(params.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
		String orderDirection = params.get("orderDirection").toString();
		String orderField = params.get("orderField").toString();
		Integer tid = Integer.valueOf(params.get("tid").toString());
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTeacherCourseTable.class, "ctc")
				.createAlias("cla", "cl")
				.createAlias("teacher", "tea")
				.createAlias("course", "course")
				.add(Restrictions.eq("tea.tid",tid));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return this.getPageByCriteria(criteria, pageNum, pageSize);
	}
	

}
