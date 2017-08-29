/**   
* @Title: ExerciseDaoImpl.java 
* @Package com.anyview.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月4日 下午10:10:46 
* @version V1.0   
*/
package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.anyview.dao.ExerciseDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.StudentExerciseAnswerVO;

@Component
public class ExerciseDaoImpl extends BaseDaoImpl implements ExerciseDao{

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExerciseDao#deleteExerciseByPid(java.lang.Integer)
	 */
	@Override
	public void deleteExerciseByPid(Integer pid) throws Exception {
		String hql = "delete from ExerciseTable e where e.problem.pid=?";
		hibernateTemplate.bulkUpdate(hql, pid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ExerciseDao#getStudentExerciseAnswer(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentExerciseAnswerVO> getStudentExerciseAnswer(final Integer sid, final Integer vid, final Integer cid) {
		final String sql = "select {problem.*}, {exercise.*} from ProblemTable problem, ExerciseTable exercise, SchemeContentTable sc "
				+ "where problem.pid=sc.pid and sc.vid=:vid and exercise.pid=problem.pid and exercise.cid=:cid and exercise.sid=:sid and exercise.vid=:vid";
		return hibernateTemplate.executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createSQLQuery(sql).addEntity("problem", ProblemTable.class).addEntity("exercise", ExerciseTable.class);
				query.setParameter("sid", sid);
				query.setParameter("vid", vid);
				query.setParameter("cid", cid);
				query.setResultTransformer(Transformers.aliasToBean(StudentExerciseAnswerVO.class));
				return query.list();
			}
		});
	}
	
	/**
	 * 
	 * @Description: TODO(获取学生答案) 
	 * @param cid
	 * @param vid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午8:08:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExerciseTable> getExerciseAnswer(Integer cid, Integer ids){
		SchemeContentTable sc = (SchemeContentTable) hibernateTemplate.get(SchemeContentTable.class, ids);
		
		try{
			String hql = "from ExerciseTable et where et.cla.cid=? and et.scheme.vid=? and et.problem.pid=?";
			return (List<ExerciseTable>) hibernateTemplate.find(hql, new Object[]{cid, sc.getScheme().getVid(), sc.getProblem().getPid()});
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<ExerciseTable> getExerciseList(Integer firstResult, Integer maxResults, DetachedCriteria criteria) {
		return hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}

	
	@Override
	public List<Object[]> getExerciseScoreAjax(Integer cid, Integer vid, Integer sid) {
		String hql="select e.score,e.runResult,s.score from ExerciseTable e,SchemeContentTable s where e.student.sid=? and "
				+ "e.scheme.vid=? and e.cla.cid=? and s.scheme.vid=? and s.problem.pid=e.problem.pid";
		return hibernateTemplate.find(hql, new Object[]{sid,vid,cid,vid});
	}

	@Override
	public List<Object[]> getFinishTimeAndScore(Integer cid, Integer vid, Integer sid) {
		String hql="select e.problem.pid,e.score,s.finishTime,e.firstPastTime from SchemeContentTable s,ExerciseTable e where "
				+ "e.scheme.vid=? and e.cla.cid=? and e.student.sid=? and s.scheme.vid=? and s.problem.pid=e.problem.pid";
		return hibernateTemplate.find(hql, new Object[]{vid,cid,sid,vid});
	}

	@Override
	public List<Object[]> getMinScoreAndEarliestFinishTime(Integer pid,Integer vid,Integer sid,Integer cid) {
		String hql="select min(firstPastTime),min(score) from ExerciseTable where problem.pid=? and scheme.vid=? and student.sid=? and cla.cid=?";
		return hibernateTemplate.find(hql, new Object[]{pid,vid,sid,cid});
	}

}
