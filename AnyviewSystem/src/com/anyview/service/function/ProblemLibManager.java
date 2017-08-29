package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.TeacherTable;

public interface ProblemLibManager {

	/**
	 * 根据管理员身份获取题库列表
	 * @param admin
	 * @return
	 */
	public List<ProblemLibTable> getProblemLibByMiden(ManagerTable admin);
	/**
	 * 
	 * @Title: getAccessableProblemLibs 
	 * @Description: TODO(获取教师可访问的题库) 
	 * @List<ProblemLibTable>
	 * @throws 
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月28日 上午3:20:25
	 */
	public List<ProblemLibTable> getAccessableProblemLibs(TeacherTable teacher);
	/**
	 * 
	 * @Description: TODO(获取教师自己创建的题库页面)
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午10:14:24
	 */
	public Pagination<ProblemLibTable> getProblemLibPage(Map params);

	/**
	 * 
	 * @Description: TODO(保存题库) 
	 * @param lib
	 * @param tidstr 可访问教师的id组成的字符串
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 下午8:14:33
	 */
	public void saveProblemLib(ProblemLibTable lib, String tidstr)throws Exception;
	/**
	 * 
	 * @Description: TODO(根据id获取题库) 
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月16日 下午7:00:59
	 */
	public ProblemLibTable getProblemLibByLid(Integer lid);
	/**
	 * 
	 * @Description: TODO(获取题库的可访问教师ProblemLibTeacherTable) 
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月16日 下午7:03:14
	 */
	public List<TeacherTable> getAccessableTeachers(Integer lid);
	
	/**
	 * 
	 * @Description: TODO(更新题库) 
	 * @param lib
	 * @param tidstr
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午3:46:08
	 */
	public void updateProblemLib(ProblemLibTable lib, String tidstr)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除题库，需先判断题库中有无目录，有则不允许删除) 
	 * @param lib
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午4:09:07
	 */
	public void deleteProblemLib(ProblemLibTable lib)throws Exception;
	
	/**
	 * 根据当前教师所选的学校id和学院Id查询出他可以访问的题库
	 * 
	 * @param unId
	 * @param ceId
	 * @param teacher 当前登录教师
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午9:04:06
	 */
	public List<ProblemLibTable> searchAccessLibs(Integer unId, Integer ceId, TeacherTable teacher);
}
