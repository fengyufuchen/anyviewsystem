package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ManagerTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.TeacherTable;

public interface ProblemLibDao {
	
	/**
	 * 获取所有题库
	 * @return
	 */
	public List<ProblemLibTable> getAllProblemLibs();
	/**
	 * 获取本校及完全公开题库
	 * @param admin
	 * @return
	 */
	public List<ProblemLibTable> getUProblemLibs(ManagerTable admin);
	/**
	 * 获取本院及完全公开及本校公开题库
	 * @param admin
	 * @return
	 */
	public List<ProblemLibTable> getCProblemLibs(ManagerTable admin);
	/**
	 * 
	 * @Title: getAccessableProblemLibs 
	 * @Description: TODO(获取教师可访问的题库) 
	 * @List<ProblemLibTable>
	 * @throws 
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月28日 上午3:22:22
	 */
	public List<ProblemLibTable> getAccessableProblemLibs(TeacherTable teacher);
	/**
	 * 
	 * @Description: TODO(获取教师自己创建的题库列表) 
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午10:21:45
	 */
	public List<ProblemLibTable> getTeacherCreateLibs(DetachedCriteria criteria, Integer currentPage, Integer numPerPage, String orderField, String orderDirection);
	/**
	 * 
	 * @Description: TODO(获取教师自己创建题库数量) 
	 * @param params
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午10:36:17
	 */
	public Integer getTeacherCreateLibCount(DetachedCriteria criteria);
	/**
	 * 
	 * @Description: TODO(保存lib) 
	 * @param lib
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 下午8:24:33
	 */
	public Integer saveProblemLib(ProblemLibTable lib);
	/**
	 * 
	 * @Description: TODO(保存题库可访问的教师) 
	 * @param tids
	 * @param lid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 下午8:33:24
	 */
	public void saveProblemLibTeachers(Integer lid,Integer [] tids);
	
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
	 * @Description: TODO(查询problemLibTeacherTable where visit=1 and problemlib.lid=lid) 
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月16日 下午7:04:51
	 */
	public List<TeacherTable> getAccessableTeachers(Integer lid);
	
	/**
	 * 
	 * @Description: TODO(更新题库) 
	 * @param lib
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午3:49:23
	 */
	public void updateProblemLib(ProblemLibTable lib);
	/**
	 * 
	 * @Description: TODO(删除题库-教师关联) 
	 * @param lid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午3:55:31
	 */
	public void deleteAllAccessTeachers(Integer lid);
	/**
	 * 
	 * @Description: TODO(获取题库下目录的数目) 
	 * @param lid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午4:13:45
	 */
	public Integer getProblemChapByLid(Integer lid);
	/**
	 * 
	 * @Description: TODO(删除题库) 
	 * @param lib
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月17日 下午4:16:57
	 */
	public void deleteProblemLib(ProblemLibTable lib);
	/**
	 * 查询出教师自己创建的题库
	 * 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午10:45:58
	 */
	public List<ProblemLibTable> getLibsCreateByTeacher(Integer tid);
	/**
	 * 查询出在题库-教师表中对应的题库
	 * 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午10:48:03
	 */
	public List<ProblemLibTable> getLibsInLibTeacherTable(Integer tid);
	/**
	 * 查询ceId学院中教师创建的本院公开的题库
	 * 
	 * @param ceId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午10:52:09
	 */
	public List<ProblemLibTable> getCLibsInCollege(Integer ceId);
	/**
	 * 查询ceId学院中教师创建的本校公开的题库
	 * 
	 * @param ceId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月8日 下午11:06:34
	 */
	public List<ProblemLibTable> getULibsInCollege(Integer ceId);
	/**
	 * 查询ceId学院中教师创建的完全公开题库
	 * 
	 * @param ceId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月10日 上午10:49:18
	 */
	public List<ProblemLibTable> getAllPublishLibsInCollege(Integer ceId);
	/**
	 * 查询unId学校中本校公开的题库
	 * 
	 * @param unId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月10日 上午10:50:59
	 */
	public List<ProblemLibTable> getULibsInUniv(Integer unId);
	/**
	 * 查询unId学校中完全公开题库
	 * 
	 * @param unId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年5月10日 上午11:08:27
	 */
	public List<ProblemLibTable> getAllPublishLibsInUniv(Integer unId);

}
