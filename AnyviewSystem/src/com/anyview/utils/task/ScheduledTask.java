/**   
* @Title: ScheduledTask.java 
* @Package com.anyview.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月17日 下午6:49:35 
* @version V1.0   
*/
package com.anyview.utils.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anyview.entities.ExamPlanTable;

/** 
 * @ClassName: ScheduledTask 
 * @Description: TODO(定时任务执行器) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月17日 下午6:49:35 
 *  
 */
public class ScheduledTask {

	private static final Log log = LogFactory.getLog(ScheduledTask.class);
	
	/**
	 * 线程数
	 */
	private final static int THREAD_COUNT = 3;
	
	private ScheduledExecutorService scheduExec;
	
	public ScheduledTask(){
		this.scheduExec = Executors.newScheduledThreadPool(THREAD_COUNT);
	}
	
	/**
	 * 
	 * @Description: TODO(启动任务) 
	 * @param task 任务
	 * @param delay 延迟时长
	 * @param unit 计时单位
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月19日 上午3:37:31
	 */
	public void startSchedule(Runnable schedule, long delay, TimeUnit unit){
		log.info("开始任务,延时:"+delay+"，单位："+unit.toString());
		scheduExec.schedule(schedule, delay, unit);
	}
}
