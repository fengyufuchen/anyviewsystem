/**   
* @Title: ClassCourseDaoImpl.java 
* @Package com.anyview.dao.impl 
* @author 何凡 <piaobo749@qq.com>   
* @date 2016年3月23日 上午12:18:35 
* @version V1.0   
*/
package com.anyview.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.anyview.dao.ClassCourseDao;
import com.anyview.entities.ClassCourseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.TeacherTable;

/** 
 * 班级课程数据库操作接口实现类
 * @ClassName: ClassCourseDaoImpl 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月23日 上午12:18:35 
 *  
 */
@Repository
public class ClassCourseDaoImpl extends BaseDaoImpl implements ClassCourseDao{

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#getCoursesOnClass(java.lang.Integer, java.lang.Integer, org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public List<ClassCourseTable> getCoursesOnClass(Integer firstResult,
			Integer maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#getCoursesCountOnClass(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public Integer getCoursesCountOnClass(DetachedCriteria criteria) {
		return getCount(criteria);
	}

	@Override
	public void deleteClassCourseById(Integer ccId) {
		String hql = "delete from ClassCourseTable where id=?";
		hibernateTemplate.bulkUpdate(hql, ccId);
	}

	@Override
	public ClassCourseTable getClassCourseById(Integer id) {
		return (ClassCourseTable) hibernateTemplate.get(ClassCourseTable.class, id);
	}

	@Override
	public void updateCourseOnClass(ClassCourseTable cc) {
		String hql = "update ClassCourseTable set status=?, startYear=? where id=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{cc.getStatus(), cc.getStartYear(), cc.getId()});
	}

	@Override
	public void saveCourseToClass(Integer courseId, Integer cid) {
		ClassCourseTable cc = new ClassCourseTable();
		cc.setCourse(new CourseTable(courseId));
		cc.setCla(new ClassTable(cid));
		cc.setStatus(0);//默认停用
		cc.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		hibernateTemplate.save(cc);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#saveTeacherToCourseOnClass(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public void saveTeacherToCourseOnClass(Integer tid, Integer ctcRight,
			Integer courseId, Integer cid, Timestamp now) {
		ClassTeacherCourseTable ctc = new ClassTeacherCourseTable();
		ctc.setTeacher(new TeacherTable(tid));
		ctc.setCourse(new CourseTable(courseId));
		ctc.setCla(new ClassTable(cid));
		ctc.setCtcright(ctcRight);
		ctc.setUpdateTime(now);
		hibernateTemplate.save(ctc);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#deleteTeacherOnCourse(java.lang.Integer)
	 */
	@Override
	public void deleteTeacherOnCourse(Integer ctcId) {
		String hql = "delete from ClassTeacherCourseTable where id=?";
		hibernateTemplate.bulkUpdate(hql, ctcId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#getClassTeacherCourseRightById(java.lang.Integer)
	 */
	@Override
	public Integer getClassTeacherCourseRightById(Integer ctcId) {
		String hql = "select ctcright from ClassTeacherCourseTable where id=?";
		return (Integer) hibernateTemplate.find(hql, ctcId).get(0);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#getClassTeacherCourseById(java.lang.Integer)
	 */
	@Override
	public ClassTeacherCourseTable getClassTeacherCourseById(Integer ctcId) {
		return (ClassTeacherCourseTable) hibernateTemplate.get(ClassTeacherCourseTable.class, ctcId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassCourseDao#updateCtcright(java.lang.Integer, java.lang.Integer, java.sql.Timestamp)
	 */
	@Override
	public void updateCtcright(Integer id, Integer right, Timestamp now) {
		String hql = "update ClassTeacherCourseTable set ctcright=?, updateTime=? where id=?";
		hibernateTemplate.bulkUpdate(hql, new Object[]{right, now , id});
	}

}
