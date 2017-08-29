package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;

public interface ProblemChapManager {
	
	/**
	 * 
	 * @Description: TODO(获取题库下的目录管理页面) 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午2:47:54
	 */
	public Pagination<ProblemChapTable> getProblemChapPageByLid(Map param);
	
	/**
	 * 
	 * @Description: TODO(获取父目录对象，若为根目录则返回自身) 
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月22日 下午5:14:52
	 */
	public ProblemChapTable getParentChap(Integer chId);
	/**
	 * 
	 * @Description: TODO(保存目录) 
	 * @param chap 
	 * @throws Exception 同一题库同一父目录下重名则抛出异常
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月22日 下午6:47:39
	 */
	public void saveProblemChap(ProblemChapTable chap)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除目录，需要先判断该目录及其子目录中有无题目，都没有题目才允许删除，需要先删除其所有子目录) 
	 * @param chap
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月24日 上午10:48:43
	 */
	public void deleteProblemChap(ProblemChapTable chap)throws Exception;
	/**
	 * 
	 * @Description: TODO(根据id获取chap对象) 
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月25日 上午11:09:28
	 */
	public ProblemChapTable getProblemChapById(Integer chId);
	/**
	 * 
	 * @Description: TODO(更新目录,需要判断名字是否重名) 
	 * @param chap
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月25日 上午11:22:02
	 */
	public void updateProblemChap(ProblemChapTable chap)throws Exception;
	
	/**
	 * 查询所有教师能访问的题目目录
	 * 自己创建题库中所有的目录，
	 * 其他可访问题库中访问级别为公开的目录。
	 * 
	 * @param parentId
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月31日 下午11:55:04
	 */
	public List<ProblemChapTable> getAccessChapByParentId(Integer parentId, Integer tid);
	
	/**
	 * 查询出一个题库下的一级目录
	 * 自己创建题库中所有的目录，
	 * 其他可访问题库中访问级别为公开的目录。
	 * 
	 * @param lid
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月1日 上午9:43:01
	 */
	public List<ProblemChapTable> getFirstChapsByLib(Integer lid, Integer tid);

}
