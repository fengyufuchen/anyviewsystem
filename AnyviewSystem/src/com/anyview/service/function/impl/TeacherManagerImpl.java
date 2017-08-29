package com.anyview.service.function.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.bytecode.analysis.Type;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.CollegeDao;
import com.anyview.dao.TeacherDao;
import com.anyview.dao.UniversityDao;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.TeacherManager;
import com.anyview.utils.TipException;
import com.anyview.utils.encryption.MD5Util;

@Service
public class TeacherManagerImpl implements TeacherManager{
	
	private static final UniversityDao universityDao = null;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private CollegeDao collegeDao;
	@Autowired
	private UniversityDao univeristyDao;

	public List<TeacherTable> getTeachers() {
		// TODO Auto-generated method stub
		List<TeacherTable> teachers = teacherDao.getTeachers();
		return teachers;
	}

	public Integer getTeacherIdenByTid(Integer tid) {
		Integer tiden = teacherDao.getTeacherTiden(tid);
		return tiden;
	}
	
	public Pagination<TeacherTable> getTeachersPageByTid(Integer pageSize,
			Integer pageNum) {
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
	public Pagination<TeacherTable> getTeachersPage(Map param, ManagerTable admin) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> teachers = teacherDao.getTeachers(param,admin);
		page.setContent(teachers);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(teacherDao.getTeacherCountByMid(admin));
		page.calcutePage();
		return page;
	}
	
	@Override
	public Pagination<TeacherTable> getTeachersPageByMid(Integer pageSize,
			Integer pageNum, ManagerTable admin) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> teachers = teacherDao.getTeachersByMid(pageSize,pageNum,admin);
		page.setContent(teachers);
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		page.setTotalCount(teacherDao.getTeacherCountByMid(admin));
		page.calcutePage();
		return page;
	}

	@Override
	public void addTeacher(TeacherTable tea) {
		teacherDao.addTeacher(tea);
		
	}
	
	@Override
	public Set<TeacherTable> getTeachers(Map param) {
		Integer ceID = (Integer) param.get("ceID");
		if(ceID==null) return null;
		CollegeTable col = collegeDao.getCollegeById(ceID);
//		return col.getTeachers();
		return null;

	}

	@Override
	public Pagination<TeacherTable> getTeachersPage(Map param) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> tea = teacherDao.getTeachersPage(param);
		page.setContent(tea);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(teacherDao.getTeacherCountBySearch(param));
		page.calcutePage();
		return page;
	}

	@Override
	public TeacherTable gainTeacherByTid(Integer tid) {
		TeacherTable tea=teacherDao.gainTeacherByTid(tid);
		return tea;
	}

	@Override
	public void updateTeacher(TeacherTable tea) throws Exception {
		teacherDao.updateTeacher(tea);
	}

	@Override
	public List<Object[]> getTeacherINByCourseId(Integer courseId) throws TipException {
		List<Object[]> teas = teacherDao.getTeacherINByCourseId(courseId);
		if(teas == null || teas.isEmpty())
			throw new TipException("此课程暂无任课教师");
		return teas;
	}

	@Override
	public void deleteTeacher(TeacherTable tea) {
		
		 Integer cid=tea.getTcId();
		 teacherDao.deleteClassByCid(cid);
		 teacherDao.deleteSchemeByTid(tea.getTid());
         teacherDao.deleteSchemeTeacherByTid(tea);
         teacherDao.deleteProblemLibTeahcerByTid(tea);
		 teacherDao.deleteTeacher(tea);
		
	}


	public List<TeacherTable> getAllTeacher(){
		return teacherDao.getAllTeacher();
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.TeacherManager#getEnabledTeacherByCeId(java.lang.Integer)
	 */
	@Override
	public List<Object[]> getEnabledTeacherByCeId(Integer ceId) {
		return teacherDao.getEnabledTeacherByCeId(ceId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.TeacherManager#getEnabledTeachersByCeIds(java.lang.Integer[])
	 */
	@Override
	public List<Object[]> getEnabledTeachersByCeIds(Integer[] ids) {
		return teacherDao.getEnabledTeachersByCeIds(ids);
	}
	

	//获取学院ceID拥有的教师数量
	public Integer gainTeacherCountByCeid(Integer ceId){
		Integer count = 0;
        count = teacherDao.gainTeacherCountByCeid(ceId);
		return count;
	}

	@Override
	public Pagination<TeacherTable> getTeachersPageForSuper(Map param) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> tea = teacherDao.getTeachersPageForSuper(param);
		page.setContent(tea);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(teacherDao.getTeacherCountForSuper(param));
		page.calcutePage();
		return page;
	}

	@Override
	public Pagination<TeacherTable> getTeachersPageForCollege(Map param) {
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		List<TeacherTable> tea = teacherDao.getTeachersPageForCollege(param);
		page.setContent(tea);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(teacherDao.getTeacherCountForCollege(param));
		page.calcutePage();
		return page;
	}

	@Override
	public List<CollegeTable> getCollegeByUnid(Integer unid) {
		// TODO Auto-generated method stub
		List<CollegeTable> c=teacherDao.getCollegesByUnid(unid);
		return c; 
	}

	@Override
	public void linkToCollege(TeacherTable tea, String ceid,Integer enabled) {
		teacherDao.linkToCollege(tea,ceid,enabled);
		
	}

	@Override
	public Pagination<TeacherTable> getEnabledTeachersByUniAndCe(Integer cid ,Integer unID,
			Integer ceID, Integer pageSize, Integer pageNum,String orderField,String orderDirection) {
		//子查询
		DetachedCriteria dc = DetachedCriteria.forClass(ClassTeacherTable.class, "ct")
				.add(Restrictions.eq("ct.cla.cid", cid))
				.add(Property.forName("ct.teacher.tid").eqProperty("tea.tid"))
				.setProjection(Property.forName("ct.id"));
		//主查询
		DetachedCriteria criteria = DetachedCriteria.forClass(TeacherTable.class, "tea")
				.createAlias("university", "u")
				.add(Restrictions.eq("u.unID", unID))
				.add(Restrictions.eq("enabled", 1))
				.add(Subqueries.notExists(dc));
		if(ceID != null){//表示指定了学院
			DetachedCriteria collegeSelect = DetachedCriteria.forClass(ClassTeacherTable.class)
					.createAlias("college", "c")
					.createAlias("teacher", "t")
					.add(Restrictions.eq("c.ceID", ceID))
					.setProjection(Property.forName("t.tid"));
			criteria = criteria.add(Property.forName("tid").in(collegeSelect));
		}
		if("asc".equalsIgnoreCase(orderDirection))
			criteria = criteria.addOrder(Order.asc(orderField));
		else
			criteria = criteria.addOrder(Order.desc(orderField));
		Pagination<TeacherTable> page = new Pagination<TeacherTable>();
		page.setTotalCount(teacherDao.getTeacherCountByCriteria(criteria));
		List<TeacherTable> tea = teacherDao.getTeacherByCriteria(pageNum, pageSize, criteria);
		page.setContent(tea);
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		page.calcutePage();
		return page;
	}

	@Override
	public void modifyTeacher(TeacherTable teacher, Map map)throws Exception {
		String oldPwd=(String) map.get("oldPwd");
		String newPwd=(String) map.get("newPwd");
		if(!MD5Util.validPassword(oldPwd, teacher.getTpsw())){
			throw new TipException("原密码错误!");
		}
		newPwd= MD5Util.getEncryptedPwd(newPwd);
		map.put("newPwd", newPwd);
		teacherDao.updateTeacher(teacher,map);
		
	}

	
}
