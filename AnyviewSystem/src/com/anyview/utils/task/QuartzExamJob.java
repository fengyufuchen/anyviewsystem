/**   
* @Title: QuartzExamJob.java 
* @Package com.anyview.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月23日 下午10:05:29 
* @version V1.0   
*/
package com.anyview.utils.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anyview.service.function.ExamPlanManager;

/** 
 * @ClassName: QuartzExamJob 
 * @Description: TODO(考试任务类) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月23日 下午10:05:29 
 *  
 */
@Component
public class QuartzExamJob {
	
	@Autowired
	private ExamPlanManager examPlanManager;
	
	public void doJobTest(){
		System.out.println("test quartz");
	}
	
	/**
	 * 查询出开始时间在24小时内的自动类型考试，并启动考试任务
	 * 
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月25日 上午12:31:19
	 */
	public void startAutomaticExamTask(){
		examPlanManager.startAll24HourAutomaticExam();
	}

}
