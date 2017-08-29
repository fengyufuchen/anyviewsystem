package com.anyview.service.function.impl;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassCourseSchemeDao;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassCourseSchemeManager;

@Service
public class ClassCourseSchemeManagerImpl implements ClassCourseSchemeManager {
	@Autowired
	private ClassCourseSchemeDao classCourseSchemeDao;

	@Override
	public Pagination<ClassCourseSchemeTable> getCombinedGradePage(Map param) {
		Integer numPerPage = (Integer) param.get("numPerPage");
		Integer currentPage = (Integer) param.get("currentPage");
		String orderField = (String) param.get("orderField");
		TeacherTable teacher = (TeacherTable) param.get("teacher");
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassCourseSchemeTable.class, "ccs")
				.add(Restrictions.eq("ccs.teacher.tid", teacher.getTid()));
		Pagination<ClassCourseSchemeTable> page = new Pagination<>();
		page.setContent(classCourseSchemeDao.getClassCourseList((currentPage - 1) * numPerPage, numPerPage, criteria));
		page.setCurrentPage(currentPage);
		page.setNumPerPage(numPerPage);
		page.setTotalCount(classCourseSchemeDao.getClassCourseCount(criteria));
		page.calcutePage();
		return page;
	}
}
