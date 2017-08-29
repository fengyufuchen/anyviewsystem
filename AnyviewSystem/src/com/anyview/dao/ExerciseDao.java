/**   
* @Title: ExerciseDao.java 
* @Package com.anyview.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月4日 下午10:09:40 
* @version V1.0   
*/
package com.anyview.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ExerciseTable;
import com.anyview.entities.StudentExerciseAnswerVO;

/** 
 * @ClassName: ExerciseDao 
 * @Description: TODO(学生作业答案dao) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月4日 下午10:09:40 
 *  
 */
public interface ExerciseDao {

	/**
	 * 
	 * @Description: TODO(删除所有题目id为pid的答案) 
	 * @param pid
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月4日 下午10:19:31
	 */
	public void deleteExerciseByPid(Integer pid)throws Exception;
	/**
	 * 获取学生答题卷
	 * @param sid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月8日 上午12:36:45
	 */
	public List<StudentExerciseAnswerVO> getStudentExerciseAnswer(Integer sid, Integer vid, Integer cid);
	
	/**
	 * 
	 * @Description: TODO(获取学生答案) 
	 * @param cid
	 * @param vid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午8:08:35
	 */
	public List<ExerciseTable> getExerciseAnswer(Integer cid, Integer ids);
	
	/**
	 * @author 杨坚新
	 * @param firstResult
	 * @param maxResults
	 * @param criteria
	 * @return
	 */
	public List<ExerciseTable> getExerciseList(Integer firstResult, Integer maxResults, DetachedCriteria criteria);
	
	/**获取一个班级，一个计划表中的一个学生的所有题目的分值，得分和通过情况
	 * @author 杨坚新
	 * @param cid
	 * @param vid
	 * @param sid
	 * @return
	 */
	public List<Object[]>getExerciseScoreAjax(Integer cid, Integer vid, Integer sid);
	
	/**获取一个班级，一个计划表中，一个学生，每道题目的eid,cid,finishtime，score
	 * @author 杨坚新
	 * @param cid
	 * @param vid
	 * @param sid
	 * @return 
	 */
	public List<Object[]>getFinishTimeAndScore(Integer cid, Integer vid,Integer sid);
	
	/**获取
	 * @author 杨坚新
	 * @param eid
	 * @return
	 */
	public List<Object[]> getMinScoreAndEarliestFinishTime(Integer pid,Integer vid,Integer sid,Integer cid);
	
}
