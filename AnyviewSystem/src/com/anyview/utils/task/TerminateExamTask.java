/**   
* @Title: ExamTask.java 
* @Package com.anyview.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月17日 下午4:31:20 
* @version V1.0   
*/
package com.anyview.utils.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.entities.ExamPlanTable;
import com.anyview.service.function.ExamPlanManager;
import com.anyview.utils.TipException;

/** 
 * @ClassName: ExamTask 
 * @Description: TODO(考试结束任务，用于定时自动执行) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月17日 下午4:31:20 
 *  
 */
public class TerminateExamTask implements Runnable{
	
	private static final Log log = LogFactory.getLog(TerminateExamTask.class);
	
	private ExamPlanTable examPlan;
	
	private ExamPlanManager examPlanManager;
	
	public TerminateExamTask(ExamPlanTable examPlan, ExamPlanManager examPlanManager){
		this.examPlan = examPlan;
		this.examPlanManager = examPlanManager;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			Integer flag = examPlanManager.getAutomaticEndFlag(examPlan.getEpId());
			if(flag.intValue() == ExamPlanTable.ALLOW_AUTOMATIC_END){
				log.info("考试id："+examPlan.getEpId()+"开始结束");
				examPlanManager.examOver(examPlan.getEpId());
				log.info("考试id："+examPlan.getEpId()+"结束完毕");
			}else{
				log.debug("考试任务已被终止");
			}
		} catch (TipException tip){
			log.debug("结束考试任务异常："+tip.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("结束考试任务系统错误:"+e.getMessage());
		}
	}

	public ExamPlanTable getExamPlan() {
		return examPlan;
	}

	public void setExamPlan(ExamPlanTable examPlan) {
		this.examPlan = examPlan;
	}
	
	

}
