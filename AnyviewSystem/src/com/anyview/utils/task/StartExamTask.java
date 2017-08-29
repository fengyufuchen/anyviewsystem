/**   
* @Title: StartExamTask.java 
* @Package com.anyview.utils.task 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月24日 上午1:22:38 
* @version V1.0   
*/
package com.anyview.utils.task;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anyview.entities.ClassTable;
import com.anyview.entities.ExamPlanTable;
import com.anyview.service.function.ExamPlanManager;
import com.anyview.utils.TipException;

/** 
 * @ClassName: StartExamTask 
 * @Description: TODO(开始考试，修改状态，并设置定时结束任务) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月24日 上午1:22:38 
 *  
 */
public class StartExamTask implements Runnable{
	
private static final Log log = LogFactory.getLog(StartExamTask.class);
	
	private ExamPlanTable examPlan;
	
	private ExamPlanManager examPlanManager;
	
	public StartExamTask(ExamPlanTable examPlan, ExamPlanManager examPlanManager){
		this.examPlan = examPlan;
		this.examPlanManager = examPlanManager;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			//查询数据库标志
			
			
			if(!(examPlan.getStatus() == ExamPlanTable.NO_USED))
				throw new TipException("只有未使用的自动类型考试才能自动开始,考试id"+examPlan.getEpId());
			//修改状态
			examPlanManager.changeStatusForExamming(examPlan);
			//延迟考试时长后，启动考试自动结束任务
			examPlan.setStatus(ExamPlanTable.EXAMMING);
			examPlan.getCla().setStatus(ClassTable.EXAM_LOCKED);
			new ScheduledTask().startSchedule(new TerminateExamTask(examPlan, examPlanManager), examPlan.getDuration(), TimeUnit.MINUTES);
		} catch (TipException e) {
			log.error("自动开始考试失败。"+e.getMessage());
		}
		
	}

}
