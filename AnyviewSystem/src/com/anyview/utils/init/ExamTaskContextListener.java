/**   
* @Title: ExamTaskContextListener.java 
* @Package com.anyview.utils.init 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月19日 上午1:29:48 
* @version V1.0   
*/
package com.anyview.utils.init;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.anyview.entities.ExamPlanTable;
import com.anyview.service.function.ExamPlanManager;
import com.anyview.utils.springcontext.SpringContext;
import com.anyview.utils.task.ScheduledTask;
import com.anyview.utils.task.TerminateExamTask;

/** 
 * @ClassName: ExamTaskContextListener 
 * @Description: TODO(Context监听器，服务器启动时查询已经开始的考试，并启动结束任务) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月19日 上午1:29:48 
 *  
 */
public class ExamTaskContextListener implements ServletContextListener{
	
	private static final Log log = LogFactory.getLog(ExamTaskContextListener.class);
	
	private ExamPlanManager examPlanManager;

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent context) {
		log.debug("ExamTaskContextListener initialized");
		examPlanManager = (ExamPlanManager) SpringContext.getContext().getBean("examPlanManager");
		List<ExamPlanTable> startedExams = examPlanManager.getAllStartedExam();
		ScheduledTask sche = new ScheduledTask();
		for(ExamPlanTable e:startedExams){
			//延迟时间为：持续时间-（当前时间-开始时间），单位：秒
			long delay = e.getDuration() * 60 - (System.currentTimeMillis()-e.getStartTime().getTime())/1000;
			sche.startSchedule(new TerminateExamTask(e, examPlanManager), delay, TimeUnit.SECONDS);
		}
	}

}
