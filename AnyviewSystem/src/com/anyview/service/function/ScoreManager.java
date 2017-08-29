package com.anyview.service.function;

import java.util.List;

import com.anyview.entities.ExerciseSchemeContentVO;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentTable;

public interface ScoreManager {

	/**
	 * 查询学生在某个作业表上的成绩
	 * @param pageNum
	 * @param numPerPage
	 * @param scheme
	 * @param student
	 * @return
	 */
	public Pagination<ScoreTable> getStudentScorePage(int pageNum,int numPerPage,int cid,SchemeTable scheme,StudentTable student);
	
	public ScoreTable getScoreById(int id);
	/**
	 * 查询学生在一个作业表上的答案
	 * 
	 * @param sid
	 * @param vid
	 * @param cid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月13日 下午4:49:24
	 */
	public List<ExerciseSchemeContentVO> getStudentExercises(int sid, int vid, int cid);
	
	/**
	 * 更新某题的分数，修改exercisetable中的score
	 * 
	 * @param eid
	 * @param score
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月31日 下午10:00:26
	 */
	public void updateScore(int eid, float score);
	/**
	 * 修改教师批注
	 * 
	 * @param eid
	 * @param ecomment
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年9月1日 上午12:08:43
	 */
	public void updateEcomment(int eid, String ecomment);
	/**
	 * 修改scoretabletable中的score
	 * @param sid
	 * @param vid
	 * @param cid
	 * @param score
	 */
	public void updateScoreInScoreTable(int sid,int vid,int cid,float score);
}
