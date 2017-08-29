package com.anyview.service.function;

import java.util.List;
import java.util.Map;


import java.util.Set;










import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

public interface TeacherManager {
	
/**
 * 获取相关老师身份信息
 * @return
 */
public Integer getTeacherIdenByTid(Integer tid);

/**
 * 获取相关老师信息
 * @return
 */
public List<TeacherTable> getTeachers();

/**
 * 
 * @param param
 * @return
 * @author 何凡
 * 2015-8-07
 */
public Set<TeacherTable> getTeachers(Map param);

/**
 * @author 李泽熊
 * 显示相关的教师页面信息，并排序
 * @param param
 * @param admin
 * @return
 */
public Pagination<TeacherTable> getTeachersPage(Map param, ManagerTable admin);


/**
 * 获取教师页面
 * @param pageSize 一页数目
 * @param pageNum 第几页
 * @param admin 
 * @return
 */
public Pagination<TeacherTable> getTeachersPageByTid(Integer pageSize,
		Integer pageNum);
/*
 * 
 * 根据管理者的身份来获得相应的教师页面
 * /
 */
public Pagination<TeacherTable> getTeachersPageByMid(Integer pageSize,
			Integer pageNum,ManagerTable admin);
/*
 * 
 * 添加教师
 */
public void addTeacher(TeacherTable tea);
/*
 * 根据查询条件获取教师页面
 * @param param 封装查询参数
 * @return
 */
public Pagination<TeacherTable> getTeachersPage(Map param);
/*
 * 
 * 根据教师id查找相应的教师
 * 
 *
 */
public TeacherTable gainTeacherByTid(Integer tid);
/*
 * 更新老师信息
 */
public void updateTeacher(TeacherTable tea) throws Exception;

public List<TeacherTable> getAllTeacher();
/**
 * 获取teacher的id+name数组
 * @param courseId
 * @return
 * @throws TipException 
 */
public List<Object[]> getTeacherINByCourseId(Integer courseId) throws TipException;

public void deleteTeacher(TeacherTable tea);

/**
 * 
 * @Description： 获取学院ceID拥有的教师数量
 * @param ceId,需查询的学院编号
 * @return：所查学院拥有的教师数量值
 * @author：刘武 
 * @date：2015年9月13日 下午10:43:01
 */
public Integer gainTeacherCountByCeid(Integer ceId);
/**
 * 
 * @Description: TODO(根据学院id获取教师id+name) 
 * @param ceId
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年9月14日 上午11:04:26
 */
public List<Object[]> getEnabledTeacherByCeId(Integer ceId);
/**
 * 
 * @Description: TODO() 
 * @param ids
 * @return [0]gid[1]gtext[2]tid[3]tname
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年9月15日 下午1:35:30
 */
public List<Object[]> getEnabledTeachersByCeIds(Integer[] ids);
/**
 * 根据学校ID获取教师页面
 * 
 */
public Pagination<TeacherTable> getTeachersPageForSuper(Map param);
/**
 * 根据学院ID去学院教师表获取教师信息
 * @param param
 * @return
 */
public Pagination<TeacherTable> getTeachersPageForCollege(Map param);
/**
 * 根据学校ID获取学院
 */
public List<CollegeTable> getCollegeByUnid(Integer unid);
/**
 * 
 * @Description: 将教师与学院关联
 * @param tea
 * @param enabled 
 * @param tid
 * @author马楚勋
 * @date 2015年10月21日 下午4:46:13
 */
public void linkToCollege(TeacherTable tea, String cid, Integer enabled);
/**
 * 根据学校和学院获取有效教师信息(未关联到此班级的教师)
 * @param cid 班级Id
 * @param unID
 * @param ceID 可以为Null,若为null表示不指定学院
 * @param pageSize
 * @param pageNum
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月21日 下午3:59:29
 */
public Pagination<TeacherTable> getEnabledTeachersByUniAndCe(Integer cid, Integer unID, Integer ceID, Integer pageSize, Integer pageNum,String orderField,String orderDirection);

public void modifyTeacher(TeacherTable teacher, Map map) throws Exception;
}
