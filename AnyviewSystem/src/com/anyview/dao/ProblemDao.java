package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ProblemTable;
import com.anyview.entities.TeacherTable;

public interface ProblemDao {

	public List<Object[]> getProblemChapIN(Map params);
	
	/**
	 * 获取题目列表（分页）
	 * @param params
	 * @return
	 */
	public List<ProblemTable> getProblemListByCh(Map params);
	
	/**
	 * 获取题目数量
	 * @param params
	 * @return
	 */
	public Integer getProblemCountByCh(Map params);
	
	/**
	 * 
	 * @Description: TODO(获取教师自建题库) 
	 * @param teacher
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:44:04
	 */
	public List<Object[]> getTeacherCreateLibs(TeacherTable teacher);
	/**
	 *
	 * @Description: TODO( 获取在problemlibTeacherTable表中的题库) 
	 * @param teacher
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:45:02
	 */
	public List<Object[]> getTeacherAccessableLibs(TeacherTable teacher);
	/**
	 * 
	 * @Description: TODO(获取学院公开题库) 
	 * @param teacher
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:45:59
	 */
	public List<Object[]> getCollegePublicLibs(TeacherTable teacher);
	/**
	 * 
	 * @Description: TODO(获取学校公开题库) 
	 * @param teacher
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:46:10
	 */
	public List<Object[]> getUniversityPublicLibs(TeacherTable teacher);
	/**
	 * 
	 * @Description: TODO(获取完全公开题库) 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:46:21
	 */
	public List<Object[]> getAllPublicLibs();
	/**
	 * 
	 * @Description: TODO(根据id获取题目信息) 
	 * @param pid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月31日 下午11:39:50
	 */
	public ProblemTable getProblemByPid(Integer pid);
	/**
	 * 
	 * @Description: TODO(根据id批量获取题目) 
	 * @param ids
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月1日 上午11:22:55
	 */
	public List<ProblemTable> getProblemsByPids(Integer []ids);
	
	/**
	 * 
	 * @Description: TODO(获取题目) 
	 * @param criteria 查询条件
	 * @param firstResult 第一条记录位置
	 * @param maxResults 查询记录条数
	 * @param orderField 排序字段
	 * @param orderDirection 排序方向
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午4:30:42
	 */
	public List<ProblemTable> getProblems(DetachedCriteria criteria, Integer firstResult, Integer maxResults, String orderField, String orderDirection);
	
	/**
	 * 
	 * @Description: TODO(获取题目数量) 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午4:33:49
	 */
	public Integer getProblemsCount(DetachedCriteria criteria);
	/**
	 * 
	 * @Description: TODO(保存题目) 
	 * @param problem
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月3日 上午3:41:15
	 */
	public void saveProblem(ProblemTable problem)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除题目) 
	 * @param pid
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月4日 下午11:11:16
	 */
	public void deleteProblem(Integer pid)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(更新题目) 
	 * @param problem
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月8日 下午4:15:55
	 */
	public void updateProblem(ProblemTable problem)throws Exception;
	/**
	 * 查询自建题库中指定chId的题目 ,不分页
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月31日 下午11:05:18
	 */
	public List<ProblemTable> getOwnLibProblemsByChId(Integer chId);
	/**
	 * 获取非自建题库中的题目
	 * 指定chId
	 * 并且status=2(正式状态)
	 * visit=1(公开级别)
	 * 
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月31日 下午11:38:48
	 */
	public List<ProblemTable> getOtherLibProblemsByChId(Integer chId);
	/**
	 * 批量存Problems
	 * 
	 * @param list
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月12日 上午10:17:21
	 */
	public void saveManyProblems(List<ProblemTable> list);
	/**
	 * 获取此目录下所有题目的名称
	 * 
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月12日 下午2:32:34
	 */
	public List<String> getAllNamesByChId(Integer chId);
	
}
