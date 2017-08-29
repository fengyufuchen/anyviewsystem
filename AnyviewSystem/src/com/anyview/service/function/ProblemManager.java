package com.anyview.service.function;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemContentVO;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.TeacherTable;

public interface ProblemManager {
	
	/**
	 * 获取可见的题目目录ID and Name
	 * @param params
	 * @return
	 */
	public List<Object[]> getProblemChapIN(Map params);
	
	/**
	 * 根据目录id和题库Id获取题目页面
	 * @param params
	 * @return
	 */
	public Pagination<ProblemTable> getProblemPageByCh(Map params);
	
	/**
	 * 
	 * @Description: TODO(根据访问级别获取题库) 
	 * @param kind,teacher
	 * @return 题库id和name的json串
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月30日 下午3:30:32
	 */
	public String getProblemLibsINByKind(TeacherTable teacher, Integer kind);
	/**
	 * 
	 * @Description: TODO(根据id获取题目信息) 
	 * @param pid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月31日 下午11:38:45
	 */
	public ProblemTable getProblemByPid(Integer pid);
	/**
	 * 
	 * @Description: TODO(根据id批量获取题目) 
	 * @param ids
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月1日 上午11:21:28
	 */
	public List<ProblemTable> getProblemsByPids(Integer[]ids);
	
	/**
	 * 
	 * @Description: TODO(获取题目) 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月20日 上午4:25:31
	 */
	public Pagination<ProblemTable> getProblemPageByChId(Map param);
	/**
	 * 
	 * @Description: TODO(保存题目，生成题目xml再保存) 
	 * @param problem 题目信息
	 * @param problemContent 题目内容信息
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月2日 下午4:37:38
	 */
	public void saveProblem(ProblemTable problem, ProblemContentVO problemContent)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除题目，需要先清空相关学生的答案) 
	 * @param problem
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月4日 上午9:40:50
	 */
	public void deleteProblem(ProblemTable problem)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(更新题目信息) 
	 * @param problem
	 * @param problemContent
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月8日 下午4:07:09
	 */
	public void updateProblem(ProblemTable problem, ProblemContentVO problemContent)throws Exception;
	
	/**
	 * 获取教师能访问的题目
	 * 指定chId
	 * 自己创建题库中所有的题目：
	 * 其他可访问目录中正式状态并且公开的题目
	 * @param chId
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月31日 下午11:01:56
	 */
	public List<ProblemTable> getAccessProblemsByChId(Integer chId, Integer tid);
	/**
	 * 从file中导入题目
	 * 成功则返回导入条数
	 * @param fin 输入流
	 * @param lid ProblemLibTable id
	 * @param chId ProblemChapTable id
	 * @param type 题类型
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月11日 下午8:19:01
	 */
	public int importProblemFromExcel(FileInputStream fin, Integer lid, Integer chId, Integer type)throws Exception;

}
