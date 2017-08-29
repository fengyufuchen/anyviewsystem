package com.anyview.service.function;

import java.util.List;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.Pagination;
import com.anyview.entities.ScoreTable;
import com.anyview.entities.StudentExerciseAnswerVO;
import com.anyview.entities.StudentSchemeDetailVO;

public interface ComprehensiveScore {
	/**
	 * 获取待评分作业表的班级课程页面
	 * @param currentPage
	 * @param numPerPage
	 * @param ccs
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月1日 下午5:29:12
	 */
	public Pagination<ClassCourseSchemeTable> getClassCoursePage(Integer currentPage, Integer numPerPage, ClassCourseSchemeTable ccs);
	/**
	 * 获取学生做题信息页面
	 * @param currentPage
	 * @param numPerPage
	 * @param id
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月7日 下午10:16:32
	 */
	@Deprecated
	public Pagination<StudentSchemeDetailVO> getStudentSchemeDetailsPage(Integer currentPage, Integer numPerPage,Integer id);
	
	/**
	 * 获取学生答题卷
	 * @param sid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月8日 上午12:34:11
	 */
	public List<StudentExerciseAnswerVO> getStudentExerciseAnswer(Integer sid, Integer vid, Integer cid);
	/**
	 * 获取classCourseScheme对象
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月8日 上午12:46:17
	 */
	public ClassCourseSchemeTable getClassCourseSchemeById(Integer id);
	/**
	 * 获取作业表得分情况页面
	 * @param currentPage
	 * @param numPerPage
	 * @param ccs
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年12月23日 下午10:50:36
	 */
	public Pagination<ScoreTable> getSchemeScorePage(Integer currentPage, Integer numPerPage,ClassCourseSchemeTable ccs,String orderField,String orderDirection);

	/**
	 * 根据教师id获取此教师的算分规则
	 * 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月12日 下午4:35:04
	 */
	public GradeRules generateGradeRulesByTid(Integer tid);
	/**
	 * 更新算分规则
	 * 
	 * @param rule
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月12日 下午7:52:35
	 */
	public void updateGradeRules(GradeRules rule);
	/**
	 * 根据算分规则计算分数
	 * 
	 * @param rule
	 * @param ccs
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月13日 下午3:17:40
	 */
	public void grade(GradeRules rule,ClassCourseSchemeTable ccs);
}
