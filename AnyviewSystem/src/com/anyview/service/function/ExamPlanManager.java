/**   
* @Title: ExamPlanManager.java 
* @Package com.anyview.service.function 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月10日 下午3:35:09 
* @version V1.0   
*/
package com.anyview.service.function;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.anyview.entities.ExamPlanTable;
import com.anyview.entities.Pagination;

public interface ExamPlanManager {
	
	/**
	 * 
	 * @Description: TODO(获取考试编排页面) 
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月10日 下午3:46:39
	 */
	public Pagination<ExamPlanTable> getExamPlanPage(Map params);
	
	/**
	 * 
	 * @Description: TODO(保存考试编排) 
	 * @param examPlan
	 * @throws Exception 同一教师创建的考试名称不唯一则抛出异常
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月13日 下午10:59:25
	 */
	public void saveExamPlan(ExamPlanTable examPlan)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除考试编排) 
	 * @param examPlan
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月14日 上午1:03:48
	 */
	public void deleteExamPlan(ExamPlanTable examPlan)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(获取examPlan) 
	 * @param epId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月14日 上午1:30:14
	 */
	public ExamPlanTable getExamPlanByEpId(Integer epId);
	
	/**
	 * 
	 * @Description: TODO(更新考试编排) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月14日 上午2:21:44
	 */
	public void updateExamPlan(ExamPlanTable examPlan)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(考试准备，将未开始的考试对应的班级设置为做题锁定状态，若考试已经开始，则抛出提示异常) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月16日 下午8:18:38
	 */
	public void prepareExam(ExamPlanTable examPlan)throws Exception;
	/**
	 * 
	 * @Description: TODO(开始考试， 启动监听器计时，自动修改考试状态和班级状态) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月16日 下午9:43:05
	 */
	public void startExam(ExamPlanTable examPlan)throws Exception;
	/**
	 * 
	 * @Description: TODO(考试结束，修改考试状态和班级状态) 
	 * @param epId
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月17日 下午5:07:45
	 */
	public void examOver(Integer epId)throws Exception;
	/**
	 * 
	 * @Description: TODO(取消考试准备，将考试状态恢复为未使用，对应班级状态设置为未锁定) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月17日 下午10:05:39
	 */
	public void canclePrepareExam(ExamPlanTable examPlan)throws Exception;
	/**
	 * 
	 * @Description: TODO(中止考试， 将考试状态设为中止，对应班级状态设为未锁定) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月17日 下午10:12:05
	 */
	public void interruptExam(ExamPlanTable examPlan)throws Exception;
	/**
	 * 
	 * @Description: TODO(启动开始时间在24小时内的自动类型考试) 
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月19日 上午1:46:39
	 */
	public void startAll24HourAutomaticExam();
	/**
	 * 修改班级状态为考试锁定
	 * 修改考试状态为正在考试
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月24日 上午1:28:54
	 */
	public void changeStatusForExamming(ExamPlanTable examPlan);
	/**
	 * 查询所有已经开始的考试
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月24日 上午2:04:38
	 */
	public List<ExamPlanTable> getAllStartedExam();
	/**
	 * 获取考试自动结束任务标识
	 * @param epId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月29日 下午1:34:25
	 */
	public Integer getAutomaticEndFlag(Integer epId);

}
