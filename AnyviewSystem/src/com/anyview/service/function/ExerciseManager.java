package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;

public interface ExerciseManager {
	public Pagination<ExerciseTable> getExercise(Map param);
	
	public List<ExerciseTable> getExerciseList(Map param);
	
	public List<Object[]>getExerciseScoreAjax(Integer cid, Integer vid, Integer sid);
	
	/**获取一个班级，一个计划表中，一个学生，每道题目的截止时间，分数
	 * @author 杨坚新
	 * @param cid
	 * @param vid
	 * @param sid
	 * @return
	 */
	public List<Object[]>getFinishTimeAndScore(Integer cid, Integer vid,Integer sid);
	
	/**
	 * 获取一道题最低分和最早完成时间
	 * @param pid
	 * @param vid
	 * @param sid
	 * @param cid
	 * @return
	 */
	public List<Object[]> getMinScoreAndEarliestFinishTime(Integer pid,Integer vid,Integer sid,Integer cid);
}
