package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;

public interface SchemeManager {
	
	/**
	 * 获取作业表页面
	 * @param param
	 * @return
	 */
	public Pagination<SchemeTable> getSchemePage(Map param);
	
	public List<SchemeTable> getAllScheme();
	
	/**
	 * 获取作业表中题目
	 * @param params
	 * @return
	 */
	public Pagination<SchemeContentTable> getSchemeProblemsPage(Map params);
	
	/**
	 * 
	 * @Description: TODO(保存新的作业表) 
	 * @param params
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月4日 下午6:30:02
	 */
	public void saveNewScheme(Map params) throws Exception;
	/**
	 * 
	 * @Description: TODO(根据vid获取schemeTable对象) 
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月5日 下午12:53:49
	 */
	public SchemeTable getSchemeByVid(Integer vid);
	/**
	 * 
	 * @Description: TODO(更新SchemeTable表信息) 
	 * @param params
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月5日 下午1:17:28
	 */
	public void updateScheme(SchemeTable scheme)throws Exception;
	/**
	 * 
	 * @Description: TODO(获取作业表下题目的list) 
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月5日 下午7:44:28
	 */
	public List<ProblemTable> getSchemeProblemList(Integer vid);
	/**
	 * 
	 * @Description: TODO(根据SchemeTable id获取SchemeContent信息) 
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月7日 上午2:39:48
	 */
	public List<SchemeContentTable> getSchemeContentList(Integer vid);
	/**
	 * 
	 * @Description: TODO(更新作业表的题目信息)
	 * @param params
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月8日 下午7:21:27
	 */
	public void updataSchemePros(Map params);
	/**
	 * 
	 * @Description: TODO(根据id获取SchemeContentTable) 
	 * @param scId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月8日 下午8:05:07
	 */
	public SchemeContentTable getSchemeContentById(Integer scId);
	/**
	 * 
	 * @Description: TODO(更新SchemeContent，需要修改总分值) 
	 * @param sc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月8日 下午8:28:58
	 */
	public void updateSchemeContent(SchemeContentTable sc)throws Exception ;
	/**
	 * 
	 * @Description: TODO(删除schemeContent) 
	 * @param sc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午8:48:30
	 */
	public void deleteSchemeContent(SchemeContentTable sc);
	
	/**
	 * 
	 * @Description: TODO(查询出此教师可访问的作业表) 
	 * @param teacher
	 * @param cla
	 * @param course
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 下午11:51:38
	 */
	public List<Object[]> getSchemeINFromClassCourseScheme(TeacherTable teacher, ClassTable cla, CourseTable course);
	/**
	 * 
	 * 
	 * @Description: TODO(根据学校id和学院id和课程id查询) 
	 * @param unId
	 * @param ceId
	 * @param courseId
	 * @return
	 * @author machuxun
	 * @date 2016年4月19日 下午2:05:06
	 */
	public List<SchemeTable> getSchemeById(Integer unId, Integer ceId,
			Integer courseId,TeacherTable teacher);
	/**
	 * 保存新作业表
	 * 
	 * @param scheme
	 * @param scs
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月6日 上午11:20:11
	 */
	public void saveNScheme(SchemeTable scheme, List<SchemeContentTable> scs)throws Exception;
	/**
	 * 
	 * 
	 * @param tid
	 * @param cid
	 * @param courseId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月7日 下午4:38:14
	 */
	public List<SchemeTable> getSchemesByCidCourseIdTid(Integer tid, Integer cid, Integer courseId);
}
