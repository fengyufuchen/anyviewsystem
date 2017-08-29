/**   
* @Title: ExamPlanManagerImpl.java 
* @Package com.anyview.service.function.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年11月10日 下午3:36:07 
* @version V1.0   
*/
package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassDao;
import com.anyview.dao.ExamPlanDao;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ExamPlanTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ExamPlanManager;
import com.anyview.utils.TipException;
import com.anyview.utils.task.StartExamTask;
import com.anyview.utils.task.TerminateExamTask;
import com.anyview.utils.task.ScheduledTask;

@Service("examPlanManager")
public class ExamPlanManagerImpl implements ExamPlanManager{
	
	@Autowired
	private ExamPlanDao examPlanDao;
	@Autowired
	private ClassDao classDao;

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#getExamPlanPage(java.util.Map)
	 */
	@Override
	public Pagination<ExamPlanTable> getExamPlanPage(Map params) {
		//组装查询条件DetachedCriteria
		TeacherTable teacher = (TeacherTable) params.get("teacher");
		Integer currentPage = Integer.valueOf(params.get("currentPage").toString());
		Integer numPerPage = Integer.valueOf(params.get("numPerPage").toString());
		ExamPlanTable examPlan = (ExamPlanTable) params.get("examPlan");
		DetachedCriteria criteria = DetachedCriteria.forClass(ExamPlanTable.class)
				.add(Example.create(examPlan).enableLike(MatchMode.ANYWHERE));
		//获取页面内容
		Pagination<ExamPlanTable> page = new Pagination<ExamPlanTable>();
		//注意：查询数量要放在查询数据之前
		page.setTotalCount(examPlanDao.getExamPlanCount(criteria));
		page.setContent(examPlanDao.getExamPlanPage((currentPage-1)*numPerPage, numPerPage, criteria));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#saveExamPlan(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void saveExamPlan(ExamPlanTable examPlan) throws Exception {
		examPlan.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if(!examPlanDao.checkExamPlanName(examPlan.getTeacher().getTid(), examPlan, false))
			throw new TipException("名称重复");
		examPlanDao.saveExamPlan(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#deleteExamPlan(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void deleteExamPlan(ExamPlanTable examPlan)throws Exception {
		examPlan = examPlanDao.getExamPlanByEpId(examPlan.getEpId());//获取最新数据
		int status = examPlan.getStatus().intValue();
		if(!(status == ExamPlanTable.NO_USED || status == ExamPlanTable.FINISH))
			throw new TipException("只有未使用或者已完成的考试才能删除");
		examPlanDao.deleteExamPlan(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#getExamPlanByEpId(java.lang.Integer)
	 */
	@Override
	public ExamPlanTable getExamPlanByEpId(Integer epId) {
		return examPlanDao.getExamPlanByEpId(epId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#updateExamPlan(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void updateExamPlan(ExamPlanTable examPlan) throws Exception {
		if(!examPlanDao.checkExamPlanName(examPlan.getTeacher().getTid(), examPlan, true))
			throw new TipException("名称重复");
		examPlanDao.updateExamPlan(examPlan);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#prepareExam(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void prepareExam(ExamPlanTable examPlan) throws Exception {
		if(examPlan.getKind() == ExamPlanTable.AUTOMATIC)
			throw new TipException("自动类型考试不能设置为准备状态");
		if(examPlan.getStatus()!=ExamPlanTable.NO_USED)
			throw new TipException("只有未使用的考试才能设置为准备状态");
		//修改班级状态为做题锁定
		classDao.changeClassStatus(examPlan.getCla().getCid(), ClassTable.EXERCISE_LOCKED);
		//修改考试编排为正在准备
		examPlanDao.changeExamPlanStatus(examPlan.getEpId(), ExamPlanTable.PREPARE_EXAM);
		examPlanDao.changeUpdateTime(examPlan.getEpId(), new Timestamp(System.currentTimeMillis()));
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#startExam(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void startExam(ExamPlanTable examPlan) throws Exception {
		if(examPlan.getKind() == ExamPlanTable.AUTOMATIC)
			throw new TipException("自动类型考试不能手动开始");
		//只有未开始或者考试准备中，才允许开始考试
		if(!(examPlan.getStatus() == ExamPlanTable.NO_USED || examPlan.getStatus() == ExamPlanTable.PREPARE_EXAM))
			throw new TipException("只有未使用或准备中的考试才能开始");
		this.changeStatusForExamming(examPlan);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		//手动类型考试开始时间为手动开始时间
		examPlanDao.changeStartTime(examPlan.getEpId(), now);
		//延迟考试时长后，启动考试自动结束任务
		examPlan.setStatus(ExamPlanTable.EXAMMING);
		examPlan.getCla().setStatus(ClassTable.EXAM_LOCKED);
		new ScheduledTask().startSchedule(new TerminateExamTask(examPlan, this), examPlan.getDuration(), TimeUnit.MINUTES);
		examPlanDao.changeUpdateTime(examPlan.getEpId(), now);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#examOver(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void examOver(Integer epId) throws Exception{
		//重新查询一遍获取最新数据
		ExamPlanTable examPlan = examPlanDao.getExamPlanByEpId(epId);
		//只有状态为正在考试才能结束，否则抛出异常
		if(examPlan.getStatus().intValue() != ExamPlanTable.EXAMMING)
			throw new TipException("id:"+examPlan.getEpId()+",name:"+examPlan.getEpName()+"的考试未进行，不能结束");
		//修改班级状态为未锁定
		classDao.changeClassStatus(examPlan.getCla().getCid(), ClassTable.NO_LOCKED);
		//修改考试状态为已完成
		examPlanDao.changeExamPlanStatus(examPlan.getEpId(), ExamPlanTable.FINISH);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#canclePrepareExam(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void canclePrepareExam(ExamPlanTable examPlan) throws Exception {
		if(examPlan.getKind() == ExamPlanTable.AUTOMATIC)
			throw new TipException("自动类型考试不能取消准备");
		if(examPlan.getStatus().intValue() != ExamPlanTable.PREPARE_EXAM)
			throw new TipException("只有准备中的考试才能取消准备");
		//修改班级状态为未锁定
		classDao.changeClassStatus(examPlan.getCla().getCid(), ClassTable.NO_LOCKED);
		//修改考试编排为未使用
		examPlanDao.changeExamPlanStatus(examPlan.getEpId(), ExamPlanTable.NO_USED);
		examPlanDao.changeUpdateTime(examPlan.getEpId(), new Timestamp(System.currentTimeMillis()));
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#interruptExam(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void interruptExam(ExamPlanTable examPlan) throws Exception {
		if(examPlan.getStatus().intValue() != ExamPlanTable.EXAMMING)
			throw new TipException("只有正在进行的考试才能中止");
		//取消自动结束任务
		examPlanDao.changeAutomaticEndFlag(examPlan.getEpId(), ExamPlanTable.NOT_ALLOW_AUTOMATIC_END);
		//修改班级状态为未锁定
		classDao.changeClassStatus(examPlan.getCla().getCid(), ClassTable.NO_LOCKED);
		//修改考试编排为未使用
		examPlanDao.changeExamPlanStatus(examPlan.getEpId(), ExamPlanTable.INTERRUPT);
		examPlanDao.changeUpdateTime(examPlan.getEpId(), new Timestamp(System.currentTimeMillis()));
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#startAll24HourAutomaticExam()
	 */
	@Override
	public void startAll24HourAutomaticExam() {
		//获取开始时间在24小时内的自动类型考试
		List<ExamPlanTable> exams = examPlanDao.getAll24HourAutomaticExam(new Timestamp(System.currentTimeMillis()));
		//对每个考试启动定时任务, 延迟时长为开始时间-当前时间，计时单位：秒
		ScheduledTask task = new ScheduledTask();
		for(ExamPlanTable e : exams){
			long delay = e.getStartTime().getTime()-System.currentTimeMillis();
			task.startSchedule(new StartExamTask(e, this), delay/1000, TimeUnit.SECONDS);
		}
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#changeStatusForExamming(com.anyview.entities.ExamPlanTable)
	 */
	@Override
	public void changeStatusForExamming(ExamPlanTable examPlan) {
		//修改班级状态为考试锁定
		classDao.changeClassStatus(examPlan.getCla().getCid(), ClassTable.EXAM_LOCKED);
		//修改考试编排 为正在考试
		examPlanDao.changeExamPlanStatus(examPlan.getEpId(), ExamPlanTable.EXAMMING);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#getAllStartedExam()
	 */
	@Override
	public List<ExamPlanTable> getAllStartedExam() {
		return examPlanDao.getExamByStatus(ExamPlanTable.EXAMMING);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ExamPlanManager#getAutomaticEndFlag(java.lang.Integer)
	 */
	@Override
	public Integer getAutomaticEndFlag(Integer epId) {
		return examPlanDao.getAutomaticEndFlag(epId);
	}

}
