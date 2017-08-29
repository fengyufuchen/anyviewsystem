/**   
* @Title: ExamPlan.java 
* @Package com.anyview.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月10日 下午3:37:38 
* @version V1.0   
*/
package com.anyview.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ExamPlanTable;

public interface ExamPlanDao {
	
	/**
	 * 
	 * @Description: TODO(获取examplan记录数量) 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月10日 下午6:36:58
	 */
	public Integer getExamPlanCount(DetachedCriteria criteria);
	
	/**
	 * 
	 * @Description: TODO(分页获取考试编排记录) 
	 * @param firseResult
	 * @param maxResults
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月10日 下午6:38:51
	 */
	public List<ExamPlanTable> getExamPlanPage(Integer firstResult, Integer maxResults, DetachedCriteria criteria);
	
	/**
	 * 
	 * @Description: TODO(保存考试编排) 
	 * @param examPlan
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月13日 下午10:59:25
	 */
	public void saveExamPlan(ExamPlanTable examPlan)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(检查考试名称是否重名) 
	 * @param tid
	 * @param examPlan
	 * @param exist 检查的记录是否已经存在(添加时不存在false，更新时存在true)
	 * @return true 不重名； false 重名
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月13日 下午11:52:20
	 */
	public boolean checkExamPlanName(Integer tid, ExamPlanTable examPlan, boolean exist);
	
	/**
	 * 
	 * @Description: TODO(删除考试编排) 
	 * @param examPlan
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月14日 上午1:04:50
	 */
	public void deleteExamPlan(ExamPlanTable examPlan);
	
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
	 * @Description: TODO(更新考试编排信息) 
	 * @param examPlan
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月14日 上午2:48:12
	 */
	public void updateExamPlan(ExamPlanTable examPlan);
	/**
	 * 
	 * @Description: TODO(修改考斯编排状态) 
	 * @param epId
	 * @param status
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月16日 下午8:48:31
	 */
	public void changeExamPlanStatus(Integer epId, Integer status);
	/**
	 * 
	 * @Description: TODO(修改更新时间) 
	 * @param epId
	 * @param time
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月17日 下午9:54:56
	 */
	public void changeUpdateTime(Integer epId, Timestamp time);
	
	/**
	 * 
	 * @Description: TODO(获取开始时间在time后24小时内的考试) 
	 * @param time
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月19日 上午1:54:08
	 */
	public List<ExamPlanTable> getAll24HourAutomaticExam(Timestamp time);
	/**
	 * 修改考试开始时间
	 * @param epId
	 * @param time
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月24日 上午1:51:53
	 */
	public void changeStartTime(Integer epId, Timestamp time);
	/**
	 * 根据状态查询考试
	 * @param status
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月24日 上午2:06:55
	 */
	public List<ExamPlanTable> getExamByStatus(int status);
	
	/**
	 * 获取考试自动结束任务标识
	 * @param epId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月29日 下午1:34:25
	 */
	public Integer getAutomaticEndFlag(Integer epId);
	/**
	 * 修改考试自动结束标识
	 * @param epId
	 * @param status
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月29日 下午8:30:34
	 */
	public void changeAutomaticEndFlag(Integer epId, Integer status);

}
