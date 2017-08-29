package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ExamPlanTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.Pagination;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentExerciseAnswerVO;
import com.anyview.entities.StudentSchemeDetailVO;
import com.anyview.entities.TeacherTable;
import com.anyview.service.commons.impl.BaseManagerImpl;
import com.anyview.service.function.ClassManager;
import com.anyview.service.function.ComprehensiveScore;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassCourseSchemeDao;
import com.anyview.dao.ClassDao;
import com.anyview.dao.ExerciseDao;
import com.anyview.dao.TeacherDao;

@Service
public class ComprehensiveScoreImpl extends BaseManagerImpl implements ComprehensiveScore{
	@Autowired
	private ClassCourseSchemeDao classCourseSchemeDao;
	@Autowired
	private ClassDao classDao;
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private TeacherDao teacherDao;

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ComprehensiveScore#getClassCoursePage(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Pagination<ClassCourseSchemeTable> getClassCoursePage(
			Integer currentPage, Integer numPerPage, ClassCourseSchemeTable ccs) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassCourseSchemeTable.class)
				.createAlias("teacher", "t").add(Restrictions.eq("t.tid", ccs.getTeacher().getTid()))
				.createAlias("cla", "cl").createAlias("course", "co")
				.add(Example.create(ccs).enableLike(MatchMode.ANYWHERE))
				.addOrder(Order.asc("cl.cid"))
				.addOrder(Order.asc("co.courseId"));
		
		Integer totalCount = classCourseSchemeDao.getClassCourseCount(criteria);
		List<ClassCourseSchemeTable> content = classCourseSchemeDao.getClassCourseList((currentPage-1)*numPerPage, numPerPage, criteria);
		return getPage(currentPage, numPerPage, content, totalCount);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ComprehensiveScore#getStudentSchemeDetails(java.lang.Integer)
	 */
	@Override
	public Pagination<StudentSchemeDetailVO> getStudentSchemeDetailsPage(Integer currentPage, Integer numPerPage,Integer id) {
		ClassCourseSchemeTable ccs = classCourseSchemeDao.getCCSById(id);
		//数目为班级中学生的数目
		Integer totalCount = classDao.getStudentCountByCid(ccs.getCla().getCid());
		List<StudentSchemeDetailVO> content = classCourseSchemeDao.getStudentSchemeDetails((currentPage-1)*numPerPage, numPerPage, ccs.getCla().getCid(), ccs.getScheme().getVid());
		return getPage(currentPage, numPerPage, content, totalCount);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ComprehensiveScore#getStudentExerciseAnswer(java.lang.Integer)
	 */
	@Override
	public List<StudentExerciseAnswerVO> getStudentExerciseAnswer(Integer sid,Integer vid, Integer cid) {
		return exerciseDao.getStudentExerciseAnswer(sid, vid, cid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ComprehensiveScore#getClassCourseSchemeById(java.lang.Integer)
	 */
	@Override
	public ClassCourseSchemeTable getClassCourseSchemeById(Integer id) {
		return classCourseSchemeDao.getCCSById(id);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ComprehensiveScore#getSchemeScorePage(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Pagination<ScoreTable> getSchemeScorePage(Integer currentPage,
			Integer numPerPage, ClassCourseSchemeTable ccs,String orderField,String orderDirection) {
		//数目为班级中学生的数目
		Integer totalCount = classDao.getStudentCountByCid(ccs.getCla().getCid());
		List<ScoreTable> content = classCourseSchemeDao.getSchemeScores((currentPage-1)*numPerPage, numPerPage, ccs.getCla().getCid(), ccs.getScheme().getVid(),orderField,orderDirection);
		return getPage(currentPage, numPerPage, content, totalCount);
	}

	@Override
	public GradeRules generateGradeRulesByTid(Integer tid) {
		GradeRules rule = teacherDao.getGradeRulesByTid(tid);
		//若数据库中没有此教师的规则，则插入一个
		if(rule==null){
			rule = new GradeRules();
			rule.setProgramFirstPer(100);
			rule.setProgramLastPer(80);
			rule.setProgramWrongPer(0);
			rule.setProgramRightPer(100);
			rule.setProgramGradeDes("默认规则");
			rule.setPaperRate(100);
			rule.setAttitudeRate(0);
			rule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			rule.setTeacher(new TeacherTable(tid));
			int id = teacherDao.addGraduRule(rule);        
			rule.setId(id);
		}
		return rule;
	}

	@Override
	public void updateGradeRules(GradeRules rule) {
		teacherDao.updateGradeRules(rule);
	}

	@Override
	public void grade(GradeRules rule, ClassCourseSchemeTable ccs) {
		
	}

}
