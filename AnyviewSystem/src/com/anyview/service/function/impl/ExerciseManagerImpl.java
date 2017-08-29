package com.anyview.service.function.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ExerciseDao;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.ExerciseManager;

@Service
public class ExerciseManagerImpl extends BaseManagerImpl implements ExerciseManager {
	@Autowired
	private ExerciseDao exerciseDao;

	@Override
	public List<ExerciseTable> getExerciseList(Map param) {
		Integer claId = (Integer) param.get("claId");
		Integer couId = (Integer) param.get("couId");
		Integer scheId = (Integer) param.get("scheId");
		Integer numPerPage = (Integer) param.get("numPerPage");
		Integer currentPage = (Integer) param.get("currentPage");
		DetachedCriteria criteria = DetachedCriteria.forClass(ExerciseTable.class, "e").add(Restrictions.eq("e.scheme.vid", scheId))
				.add(Restrictions.eq("e.cla.cid", claId))
				.addOrder(Order.asc("e.student.sid"));
		return exerciseDao.getExerciseList( currentPage*numPerPage, numPerPage,criteria);
	}

	@Override
	public Pagination<ExerciseTable> getExercise(Map param) {

		Integer numPerPage = (Integer) param.get("numPerPage");
		Integer currentPage = (Integer) param.get("currentPage");
		String orderField = (String) param.get("orderField");
		TeacherTable teacher = (TeacherTable) param.get("teacher");
		Integer cid = (Integer) param.get("cid");
		Integer vid = (Integer) param.get("vid");
		DetachedCriteria criteria = DetachedCriteria.forClass(ExerciseTable.class, "e")
				.addOrder(Order.asc("e.student.sid"));
		if (vid != null && cid != null) {
			criteria.add(Restrictions.eq("e.scheme.vid", vid)).add(Restrictions.eq("e.cla.cid", cid));
		} else {
			criteria.add(Restrictions.eq("e.scheme.vid", -2)).add(Restrictions.eq("e.cla.cid", -2));
		}

		Pagination<ExerciseTable> page = new Pagination<>();
		System.out.println("numPerPage:" + numPerPage);
		System.out.println("currentPage:" + currentPage);
		List<ExerciseTable> content = exerciseDao.getExerciseList((currentPage - 1) * numPerPage, numPerPage, criteria);
		page.setContent(content);
		page.setCurrentPage(currentPage);
		page.setNumPerPage(numPerPage);
		page.setTotalCount(content.size());
		page.calcutePage();
		return page;
	}

	@Override
	public List<Object[]> getExerciseScoreAjax(Integer cid, Integer vid, Integer sid) {
		return exerciseDao.getExerciseScoreAjax(cid, vid, sid);
	}

	@Override
	public List<Object[]> getFinishTimeAndScore(Integer cid, Integer vid, Integer sid) {
		return exerciseDao.getFinishTimeAndScore(cid, vid, sid);
	}

	@Override
	public List<Object[]> getMinScoreAndEarliestFinishTime(Integer pid, Integer vid, Integer sid, Integer cid) {
		return exerciseDao.getMinScoreAndEarliestFinishTime(pid, vid, sid, cid);
	}
	

}
