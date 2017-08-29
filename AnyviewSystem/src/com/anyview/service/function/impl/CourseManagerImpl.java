package com.anyview.service.function.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.UniversityDao;
import com.anyview.dao.CourseDao;
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.CourseManager;
import com.anyview.utils.TipException;

@Service
public class CourseManagerImpl extends BaseManagerImpl implements CourseManager{
	
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private UniversityDao universityDao;
	

	public Pagination<CourseTable> getCourses(Map param) {
		Pagination<CourseTable> page = new Pagination<CourseTable>();
		page.setContent(courseDao.getCourses(param));
		page.setCurrentPage(Integer.valueOf( param.get("pageNum").toString()));
		page.setNumPerPage(Integer.valueOf( param.get("pageSize").toString()));
		page.setTotalCount(courseDao.getCourseCount(param));
		page.calcutePage();
		return page;
	}
	
	public void addCourses(String courseName, Integer ceID, 
					Integer unID, String category, Integer enabled)
	{
		courseDao.addCourse(courseName, ceID, unID, category, enabled);
	}
	

	public void editCourseByCourseID(Integer courseId, String courseName, Integer ceID, 
					Integer unID, String category, Integer enabled)
	{
		courseDao.editCourseByCourseID(courseId, courseName, ceID, unID, category, enabled);
	}
	

	public boolean isDuplicateCourseName(String courseName, Integer ceID, Integer unID)
	{
		return courseDao.checkDuplicateCourse(courseName, ceID, unID);
		
	}
	
	
	public List<CollegeTable> getCollegeByUnID(Integer unID)
	{
		return courseDao.getCollegeByUnID(unID);
		
	}
	
	
	public List<UniversityTable> getAllUniversity()
	{
		return universityDao.gainAllUniversities();
	}
	
	
	public List<CourseTable> getCourseByCourseID(Integer courseId)
	{
		List<CourseTable> list = courseDao.getCourseByCourseID(courseId);
		return list;
	}
	
	
	public void deleteCourseByCourseID(Integer courseId) throws HibernateException
	{
		courseDao.deleteCourseByCourseID(courseId);
	}
	
	public List<CourseTable> getAllCourse()
	{
		return courseDao.getAllCourse();
	}

	@Override
	public List<CourseTable> getCourseByceID(Map param) {
		return courseDao.getCourseByceID(param);
	}

	@Override
	public List<Object[]> getCourseINByCeId(Integer ceID) throws TipException {
		List<Object[]> obs = courseDao.getCourseINByCeId(ceID);
		if(obs == null || obs.isEmpty())
			throw new TipException("此学院无下属课程");
		return obs;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CourseManager#getCourseByTidAndRight(com.anyview.entities.TeacherTable, java.lang.Integer)
	 */
	@Override
	public List<CourseTable> getCourseByTidAndRight(TeacherTable teacher,
			Integer right) {
		return courseDao.getCourseByTidAndRight(teacher,right);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CourseManager#getCourseByCourseId(java.lang.Integer)
	 */
	@Override
	public CourseTable getCourseByCourseId(Integer courseId) {
		return courseDao.getCourseByCourseId(courseId);
	}
	
	//获取学院ceID拥有的课程数量
	public Integer gainCourseCountByCeid(Integer ceId){
		Integer count = 0;
        count = courseDao.gainCourseCountByCeid(ceId);
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.anyview.service.function.CourseManager#getCourseINFromClassTeacherCourse(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Object[]> getCourseINFromClassTeacherCourse(Integer tid,
			Integer cid) {
		return courseDao.getCourseINFromClassTeacherCourse(tid, cid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CourseManager#getUniversityCoursePage(java.util.Map)
	 */
	@Override
	public Pagination<CourseTable> getUniversityCoursePage(Map params) {
		Integer pageNum = Integer.valueOf(params.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
		String orderDirection = params.get("orderDirection").toString();
		String orderField = params.get("orderField").toString();
		ClassTable currCla = (ClassTable) params.get("currCla");
		//子查询
		DetachedCriteria dc = DetachedCriteria.forClass(ClassCourseTable.class, "cc")
				.add(Restrictions.eq("cc.cla.cid", currCla.getCid()))
				.add(Property.forName("cc.course.courseId").eqProperty("co.courseId"))
				.setProjection(Property.forName("cc.id"));
		//主查询
		DetachedCriteria criteria = DetachedCriteria.forClass(CourseTable.class, "co")
				.createAlias("university", "u")
				.add(Restrictions.eq("u.unID", currCla.getCollege().getUniversity().getUnID()))
				.add(Restrictions.eq("enabled", 1))
				.add(Subqueries.notExists(dc));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return this.getPageByCriteria(criteria, pageNum, pageSize);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CourseManager#getCollegeCoursesPage(java.util.Map)
	 */
	@Override
	public Pagination<CourseTable> getCollegeCoursesPage(Map params) {
		Integer pageNum = Integer.valueOf(params.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
		String orderDirection = params.get("orderDirection").toString();
		String orderField = params.get("orderField").toString();
		ClassTable currCla = (ClassTable) params.get("currCla");
		//子查询
		DetachedCriteria dc = DetachedCriteria.forClass(ClassCourseTable.class, "cc")
				.add(Restrictions.eq("cc.cla.cid", currCla.getCid()))
				.setProjection(Property.forName("cc.id"));
		//主查询
		DetachedCriteria criteria = DetachedCriteria.forClass(CourseTable.class, "course")
				.createAlias("college", "c")
				.add(Restrictions.eq("c.ceID", currCla.getCollege().getCeID()))
				.add(Restrictions.eq("enabled", 1))
				.add(Subqueries.notExists(dc));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		return this.getPageByCriteria(criteria, pageNum, pageSize);
	}

}
