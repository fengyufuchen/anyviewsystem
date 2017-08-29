package com.anyview.service.function.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ExamPlanDao;
import com.anyview.dao.ScoreDao;
import com.anyview.entities.ExerciseSchemeContentVO;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentTable;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.ScoreManager;

@Service("scoreManager")
public class ScoreManagerImpl extends BaseManagerImpl implements ScoreManager {
	
	@Autowired
	private ScoreDao scoreDao;

	@Override
	public Pagination<ScoreTable> getStudentScorePage(int pageNum,
			int numPerPage,int cid, SchemeTable scheme, StudentTable student) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ScoreTable.class)
				.createAlias("student", "s")
				.createAlias("cla", "c")
				.createAlias("scheme", "v")
				.add(Restrictions.eq("c.cid", cid));
		if(student.getSname()!=null && student.getSname().length()>0){
			criteria = criteria.add(Restrictions.like("s.sname", student.getSname(),MatchMode.ANYWHERE));
		}
		if(scheme.getVid() != null){
			criteria = criteria.add(Restrictions.eq("v.vid", scheme.getVid()));
		}
		return this.getPageByCriteria(criteria, pageNum, numPerPage);
	}

	@Override
	public ScoreTable getScoreById(int id) {
		return this.getEntityById(ScoreTable.class, id);
	}

	@Override
	public List<ExerciseSchemeContentVO> getStudentExercises(int sid, int vid, int cid) {
		String hql = "select new com.anyview.entities.ExerciseSchemeContentVO(ex, sc) from ExerciseTable ex, SchemeContentTable sc where ex.scheme.vid=sc.scheme.vid"
				+ " and ex.problem.pid=sc.problem.pid and ex.student.sid=? and ex.scheme.vid=? and ex.cla.cid=? order by sc.vchapName asc ";
		return this.getListByHql(ExerciseSchemeContentVO.class, hql, new Object[]{sid, vid, cid});
	}

	@Override
	public void updateScore(int eid, float score) {
		ExerciseTable exercise = super.getBaseDao().getEntityById(ExerciseTable.class, eid);
		exercise.setScore(score);
		super.getBaseDao().updateObject(exercise);
	}

	
	@Override
	public void updateEcomment(int eid, String ecomment) {
		ExerciseTable exercise = super.getBaseDao().getEntityById(ExerciseTable.class, eid);
		exercise.setEcomment(ecomment);
		super.getBaseDao().updateObject(exercise);
	}

	@Override
	public void updateScoreInScoreTable(int sid, int vid, int cid, float score) {
		scoreDao.updateScoreInScoreTable(sid, vid, cid, score);
	}

}
