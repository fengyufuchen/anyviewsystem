package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ClassTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeT;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;

public interface SchemeDao {
	
	/**
	 * 获取作业表列表
	 * @param firstResult 第一条记录序号
	 * @param maxResults 查询最大数量
	 * @param criteria 条件
	 * @return
	 */
	public List<SchemeTable> getSchemeList(Integer firstResult, Integer maxResults, DetachedCriteria criteria);
	
	/**
	 * 获取作业表数量
	 * @param criteria
	 * @return
	 */
	public Integer getSchemeCount(DetachedCriteria criteria);
	
	public List<SchemeTable> getAllScheme();
	
	/**
	 * 获取作业表中的题目(分页)
	 * @param params
	 * @return
	 */
	public List<SchemeContentTable> getSchemeProblemsList(Map params);
	/**
	 * 获取作业表中题目数目
	 * @param params
	 * @return
	 */
	public Integer getSchemeProblemsCount(Map params);
	/**
	 * 
	 * @Description: TODO(保存scheme) 
	 * @param scheme
	 * @return 自动生成的id
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月4日 下午8:06:20
	 */
	public Integer saveScheme(SchemeTable scheme);
	/**
	 * 
	 * @Description: TODO(保存作业表内容) 
	 * @param scheme
	 * @param scs
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月4日 下午10:51:05
	 */
	public void saveSchemeContent(SchemeTable scheme, List<SchemeContentTable> stList);
	/**
	 * 
	 * @Description: TODO(根据vid获取scheme) 
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月5日 下午12:55:04
	 */
	public SchemeTable getSchemeByVid(Integer vid);
	/**
	 * 
	 * @Description: TODO(更新schemeTable信息) 
	 * @param scheme
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月5日 下午1:27:33
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
	 * @Description: TODO(根据schemetable id获取schemecontent) 
	 * @param vid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月7日 上午2:43:03
	 */
	public List<SchemeContentTable> getSchemeContentList(Integer vid);
	
	/**
	 * 
	 * @Description: TODO(删除作业表下所有的schemeContent) 
	 * @param vid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月8日 下午7:40:58
	 */
	public void deleteAllSchemeContentByVid(Integer vid);
	/**
	 * 
	 * @Description: TODO(根据id获取schemeContent) 
	 * @param scId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月8日 下午8:06:48
	 */
	public SchemeContentTable getSchemeContentById(Integer scId);
	/**
	 * 
	 * @Description: TODO(更新SchemeContent) 
	 * @param sc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午1:56:01
	 */
	public void updateSchemeContent(SchemeContentTable sc);
	/**
	 * 
	 * @Description: TODO(scheme.fullScore=all schemeContent score) 
	 * @param vid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午2:00:11
	 */
	public void updateSchemeFullScore(Integer vid);
	/**
	 * 
	 * @Description: TODO(删除schemeContent) 
	 * @param sc
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月9日 下午8:51:41
	 */
	public void deleteSchemeContent(SchemeContentTable sc);
	
	/**
	 * 
	 * @Description: TODO(根据班级id和课程id查询ClassCourseScheme, status=1, scheme.kind=1, tid|scheme.visit) 
	 * @param teacher
	 * @param cla
	 * @param course
	 * @param tids 同学院的教师id
	 * @param vids 教师可访问的作业表
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 下午11:51:38
	 */
	public List<Object[]> getSchemeINFromClassCourseScheme(TeacherTable teacher, ClassTable cla, CourseTable course, List<Integer> tids, List<Integer> vids);
	/**
	 * 
	 * @Description: TODO(查询SchemeTeacher表tid对应的vid) 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月12日 上午10:54:08
	 */
	public List<Integer> getVidFromSchemeTeacher(Integer tid);
	
	/**
	 * 
	 * @Description: TODO(获取作业表习题页面) 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午7:34:06
	 */
	@SuppressWarnings("rawtypes")
	public List<SchemeContentTable> getSchemeContentPage(Map param);
	
	/**
	 * 
	 * @Description: TODO(获取作业表习题总数) 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午3:09:52
	 */
	@SuppressWarnings("rawtypes")
	public Integer getListSchemeContentCount(Map param);
	
	/**
	 * 
	 * @Description: TODO(根据vid和pid获取schemecontent) 
	 * @param vid
	 * @param pid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年2月23日 下午5:27:18
	 */
	public List<SchemeContentTable> getSchemeContentList(Integer vid, Integer pid);
	/**
	 * 
	 * @Description: TODO(根据课程id查作业表) 
	 * @param courseId
	 * @param teacher
	 * @return
	 * @author machuxun
	 * @date 2016年4月19日 下午2:32:23
	 */
	public List<SchemeTable> getSchemeListByCourseId(Integer courseId,
			TeacherTable teacher);
    /***
     * 
     * @Description: TODO(根据学院id查询) 
     * @param ceId
     * @param teacher
     * @return
     * @author machuxun
     * @date 2016年4月19日 下午4:28:05
     */
	public List<SchemeTable> getSchemeListByCeID(Integer ceId,
			TeacherTable teacher);
   /**
    * 
    * @Description: TODO(根据学校id查询) 
    * @param unId
    * @param teacher
    * @return
    * @author machuxun
    * @date 2016年4月20日 下午10:30:40
    */
	public List<SchemeTable> getSchemeListByUnID(Integer unId,TeacherTable teacher);

	public List<SchemeTable> getSchemeListByOtherUnID(Integer unId,TeacherTable teacher);
	
	/**
	 * 根据教师id，课程id，班级id查询Class_Course_SchemeTable表
	 * 
	 * @param tid
	 * @param cid
	 * @param courseId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月7日 下午4:42:43
	 */
	public List<SchemeTable> getSchemesByCidCourseIdTid(Integer tid, Integer cid, Integer courseId);
	
}
