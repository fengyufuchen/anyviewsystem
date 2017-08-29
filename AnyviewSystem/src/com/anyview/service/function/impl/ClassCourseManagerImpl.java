package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.CCDao;
import com.anyview.dao.CCSDao;
import com.anyview.dao.ClassCourseDao;
import com.anyview.dao.ClassDao;
import com.anyview.dao.CourseDao;
import com.anyview.entities.CCTable;
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.Pagination;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.ClassCourseManager;
import com.anyview.service.function.ClassManager;
import com.anyview.utils.TipException;

@Service
public class ClassCourseManagerImpl extends BaseManagerImpl implements ClassCourseManager{
	
	@Autowired
	private CCSDao ccsDao;
	@Autowired
	private ClassCourseDao classCourseDao;

	public List getCCSByClass(String classId) {
		return ccsDao.getCCSByClass(classId);
	}
	
	public List getCCSByCourse(String courseId){
		return ccsDao.getCCSByCourse(courseId);
	}

	@Override
	public int settingCCS(String classId, String courseId, String schemeId,
			String teacherId, String status) {
		return ccsDao.settingCCS(classId, courseId, schemeId, teacherId, status);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#getClassCourses(java.util.Map)
	 */
	@Override
	public Pagination<ClassCourseTable> getClassCourses(Map params) {
		Integer numPerPage = (Integer) params.get("pageSize");
		Integer currentPage = (Integer) params.get("pageNum");
		String orderField = (String) params.get("orderField");
		String orderDirection = (String) params.get("orderDirection");
		ClassCourseTable cc = (ClassCourseTable) params.get("condition");
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassCourseTable.class, "cc")
				.createAlias("course", "course")
//				.createAlias("teacher", "teacher")
				.add(Restrictions.eq("cla.cid", cc.getCla().getCid()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		Pagination<ClassCourseTable> page = new Pagination<ClassCourseTable>();
		page.setTotalCount(classCourseDao.getCoursesCountOnClass(criteria));
		List<ClassCourseTable> cts = classCourseDao.getCoursesOnClass((currentPage-1)*numPerPage,numPerPage,criteria);
		page.setContent(cts);
		page.setCurrentPage((Integer)params.get("pageNum"));
		page.setNumPerPage((Integer)params.get("pageSize"));
		page.calcutePage();
		return page;
	}

	@Override
	public void saveCourseToClass(String coIds, Integer cid) throws TipException {
		String [] ids = coIds.split(",");
		if(ids.length == 0)
			throw new TipException("没有课程被添加");
		for(String s : ids){
			classCourseDao.saveCourseToClass(Integer.valueOf(s), cid);
		}
	}

	@Override
	public void deleteCourseOnClass(Integer ccId) {
		classCourseDao.deleteClassCourseById(ccId);
	}

	@Override
	public ClassCourseTable getClassCourseById(Integer id) {
		return classCourseDao.getClassCourseById(id);
	}

	@Override
	public void updateCourseOnClass(ClassCourseTable cc) {
		cc.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		classCourseDao.updateCourseOnClass(cc);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#getClassTeacherCoursePage(java.util.Map)
	 */
	@Override
	public Pagination<ClassTeacherCourseTable> getClassTeacherCoursePage(
			Map params) {
		Integer pageNum = Integer.valueOf(params.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
		String orderDirection = params.get("orderDirection").toString();
		String orderField = params.get("orderField").toString();
		ClassCourseTable cc = (ClassCourseTable) params.get("cc");
		//主查询
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassTeacherCourseTable.class, "ctc")
				.createAlias("course", "co")
				.createAlias("cla", "cl")
				.createAlias("teacher", "tea")
				.add(Restrictions.eq("co.courseId", cc.getCourse().getCourseId()))
				.add(Restrictions.eq("cl.cid", cc.getCla().getCid()));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return this.getPageByCriteria(criteria, pageNum, pageSize);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#saveTeacherToCourseOnClass(java.util.Map, com.anyview.entities.ClassCourseTable)
	 */
	@Override
	public void saveTeacherToCourseOnClass(Map<String, Integer> map,
			ClassCourseTable cc) {
		Set<String> keys = map.keySet();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		for(String tid:keys){
			classCourseDao.saveTeacherToCourseOnClass(Integer.valueOf(tid), map.get(tid), cc.getCourse().getCourseId(), cc.getCla().getCid(), now);
			
		}
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#deleteTeacherOnCourse(java.lang.Integer)
	 */
	@Override
	public void deleteTeacherOnCourse(Integer ctcId) {
		classCourseDao.deleteTeacherOnCourse(ctcId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#getClassTeacherCourseRightById(java.lang.Integer)
	 */
	@Override
	public Integer getClassTeacherCourseRightById(Integer ctcId) {
		return classCourseDao.getClassTeacherCourseRightById(ctcId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#getClassTeacherCourseById(java.lang.Integer)
	 */
	@Override
	public ClassTeacherCourseTable getClassTeacherCourseById(Integer ctcId) {
		return classCourseDao.getClassTeacherCourseById(ctcId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassCourseManager#updateCtcright(com.anyview.entities.ClassTeacherCourseTable)
	 */
	@Override
	public void updateCtcright(ClassTeacherCourseTable ctc) {
		classCourseDao.updateCtcright(ctc.getId(), ctc.getCtcright(), new Timestamp(System.currentTimeMillis()));
	}

}
