package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ProblemChapTable;

public interface ProblemChapDao {

	/**
	 * 查询出所有子目录(递归)
	 * @param chIdList
	 * @return
	 */
	public List<ProblemChapTable> getAllChildrenChapByParentChIds(List<Integer> chIdList);
	/**
	 * 
	 * @Description: TODO(查询出所有子目录id) 
	 * @param chIdList
	 * @param isPublic true查询所有visit为公开的子目录，false查询所有子目录
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月31日 下午7:57:34
	 */
	public List<Integer> getAllChildrenChapIdByParentChIds(List<Integer> chIdList, boolean isPublic);
	/**
	 * 
	 * @Description: TODO(查询出题库下所有的目录id) 
	 * @param libList
	 * @param isPublic true：查询所有公开的目录及子目录，false:查询所有目录及子目录
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月31日 下午8:53:52
	 */
	public List<Integer> getAllChapIdByLibIds(List<Integer> libList, boolean isPublic);
	
	/**
	 * 
	 * @Description: TODO(获取目录) 
	 * @param criteria 查询条件
	 * @param firstResult 第一条记录位置
	 * @param maxResults 最大记录条数
	 * @param orderField 排序字段
	 * @param orderDirection 排序方向
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午2:57:46
	 */
	public List<ProblemChapTable> getProblemChaps(DetachedCriteria criteria, Integer firstResult, Integer maxResults, String orderField, String orderDirection);
	/**
	 * 
	 * @Description: TODO(获取目录数量) 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午3:03:29
	 */
	public Integer getProblemChapsCount(DetachedCriteria criteria);
	
	/**
	 * 
	 * @Description: TODO(根据id获取problemchap对象) 
	 * @param chId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月22日 下午5:19:12
	 */
	public ProblemChapTable getProblemChapByChId(Integer chId);
	/**
	 * 
	 * @Description: TODO(获取同一题库，同一父目录下同名目录数量) 
	 * @param chap
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月22日 下午6:50:59
	 */
	public Integer checkChapName(ProblemChapTable chap);
	/**
	 * 
	 * @Description: TODO(保存目录) 
	 * @param chap
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月23日 上午12:05:43
	 */
	public void saveProblemChap(ProblemChapTable chap)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除id在chIds中的problemChap) 
	 * @param chIds
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月24日 下午12:27:52
	 */
	public void deleteProblemChaps(List<Integer> chIds)throws Exception;
	/**
	 * 
	 * @Description: TODO(更新目录，包括chName,memo,visit,updateTime) 
	 * @param chap
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月25日 上午11:30:59
	 */
	public void updateProblemChap(ProblemChapTable chap)throws Exception;
	
	/**
	 * 获取自建题库中的目录
	 * 指定父目录为parentId
	 * 
	 * @param parentId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月1日 上午12:00:55
	 */
	public List<ProblemChapTable> getOwnLibChapsByParentId(Integer parentId);
	/**
	 * 获取非自建题库中的目录
	 * 指定父目录为parentId
	 * 其他可访问题库中访问级别为公开的目录。
	 * 
	 * @param parentId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月1日 上午12:03:13
	 */
	public List<ProblemChapTable> getOtherLibChapsByParentId(Integer parentId);
	/**
	 * 查询自建题库的一级目录，查询出所有一级目录
	 * 
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月1日 上午9:49:07
	 */
	public List<ProblemChapTable> getOwnLibFirstChaps(Integer lid);
	/**
	 * 查询出非自建题库的一级目录
	 * 公开的一级目录
	 * 
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月1日 上午9:49:57
	 */
	public List<ProblemChapTable> getOtherLibFirstChaps(Integer lid);
	
}
