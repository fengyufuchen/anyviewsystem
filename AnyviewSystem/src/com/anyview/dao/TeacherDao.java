package com.anyview.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.GradeRules;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;

public interface TeacherDao{

	/**
	 * 获取相关教师信息，并排序
	 * @param param
	 * @param admin
	 * @return
	 */
	public List<TeacherTable> getTeachers(Map param, ManagerTable admin);
	
	/**
	 * 根据教师no查找教师信息
	 * @param tno
	 * @return
	 */
	public TeacherTable getTeacherByTnoAndUnId(String tno, Integer unId);
	
	/**
	 * 根据教师id获取身份信息
	 * @param tid
	 * @return
	 */
	public Integer getTeacherTiden(Integer tid);
	
    /**
     * 获取全部教师信息
     * @param pageSize
     * @param pageNum
     * @param admin 
     * @return
     */
	public List<TeacherTable> getTeachers(final Integer pageSize,final Integer pageNum);
	
    /**
     * 获取全部教师信息
     * @return
     */
	public List<TeacherTable> getTeachers();
	
	/**
	 * 获取教师数目
	 * @return
	 */
	public Integer getTeacherCount();
	/**
	 * 更新教师信息
	 * @param tea
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void updateTeacher(TeacherTable tea) throws NoSuchAlgorithmException, UnsupportedEncodingException;
/*
 * 根据管理员身份获得相应的教师列表
 * 
 * /
 */
	public List<TeacherTable> getTeachersByMid(final Integer pageSize,final Integer pageNum,ManagerTable admin);
/*
 * 根据管理员身份获得相应的教师数目
 * 
 * /
 */
	public Integer getTeacherCountByMid(ManagerTable admin);

	/*
	 * 
	 */
	public void addTeacher(TeacherTable tea);

	/**
	 * 根据查询条件获取教师列表
	 * @param param
	 * @return
	 */
	public List<TeacherTable> getTeachersPage(Map param);

	/**
	 * 根据查询条件获取教师的数目
	 * @param param
	 * @return
	 */


	public Integer getTeacherCountBySearch(Map param);
/*
 * 根据教师id获取教师
 * /
 */
	

	public TeacherTable gainTeacherByTid(Integer tid);

	public List<TeacherTable> getAllTeacher();
	/**
	 * 获取teacher的id+name数组
	 * @param courseId
	 * @return
	 */
	public List<Object[]> getTeacherINByCourseId(Integer courseId);
	/**
	 * 直接删除教师
	 * @param tea
	 */

	public void deleteTeacher(TeacherTable tea);
/**
 * 根据班级ID删除班级
 * @param cid
 */
	public void deleteClassByCid(Integer cid);
/**
 * 根据教师ID获取题库并将学校，学院，教师ID置为-1
 * @param tid
 */
	public void deleteProblemLibByTid(Integer tid);
/**
 * 根据教师ID获取作业表并删除
 * @param tid
 */
	public void deleteSchemeByTid(Integer tid);
/***
 * 根据教师ID获取作业教师表并删除
 * @param tea
 */
public void deleteSchemeTeacherByTid(TeacherTable tea);
/***
 * 根据教师ID获取题库教师表并删除
 * @param tea
 */
public void deleteProblemLibTeahcerByTid(TeacherTable tea);

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
 * 
 * @Description： 根据学院ceID在教师表中查询该学院拥有的教师数量
 * @param ceId
 * @return：查询学院的教师数量
 * @author：刘武 
 * @date：2015年9月14日 下午12:28:27
 */
public  Integer gainTeacherCountByCeid(Integer ceId);
/**
 * 根据学校ID获取教师页面
 * @param param
 * @return
 */
public List<TeacherTable> getTeachersPageForSuper(Map param);
/**
 * 根据学校ID获取教师数目
 * @param param
 * @return
 */
public Integer getTeacherCountForSuper(Map param);
/**
 * 根据学院ID去学院教师表中获取信息
 * @param param
 * @return
 */
public List<TeacherTable> getTeachersPageForCollege(Map param);
/**
 * 根据学院ID获取教师数目
 * @param param
 * @return
 */
public Integer getTeacherCountForCollege(Map param);
/**
 * 
 * @Description: 根据学校ID获取学院
 * @param unid
 * @return
 * @author 马楚勋 
 * @date 2015年10月20日 上午11:42:12
 */
List<CollegeTable> getCollegesByUnid(Integer unid);
/**
 * 
 * @Description:将教师与学院关联
 * @param tea
 * @param ceid
 * @author马楚勋
 * @date 2015年10月21日 下午4:49:21
 */
public void linkToCollege(TeacherTable tea, String ceid,Integer enabled);

/**
 * 
 * @Description: TODO(获取同学院的教师id) 
 * @param tid
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年11月12日 下午12:51:00
 */
public List<Integer> getSameCollegeTeacherId(Integer tid);
/**
 * 根据tid获取GradeRules对象
 * 
 * @param tid
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月12日 下午4:38:10
 */
public GradeRules getGradeRulesByTid(Integer tid);
/**
 * 增加一个算分规则
 * 
 * @param rule 其中没有id,因为设置id自增
 * @return 返回id
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月12日 下午4:47:07
 */
public Integer addGraduRule(GradeRules rule);
/**
 * 更新算分规则
 * 
 * @param rule
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月12日 下午7:57:47
 */
public void updateGradeRules(GradeRules rule);
/**
 * 根据条件查询教师
 * 
 * @param pageNum
 * @param pageSize
 * @param criteria
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月21日 下午4:25:29
 */
public List<TeacherTable> getTeacherByCriteria(Integer pageNum,Integer pageSize,DetachedCriteria criteria);
/**
 * 根据条件查询教师数量
 * 
 * @param criteria
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月21日 下午4:26:13
 */
public Integer getTeacherCountByCriteria(DetachedCriteria criteria);

/**
 * 
 * @Description: TODO() 
 * @param teacher
 * @param map
 * @throws Exception
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年3月31日 下午12:31:38
 */
public void updateTeacher(TeacherTable teacher, Map map)throws Exception;
/**
 * 获取教师关联的学院
 * 
 * @param tid
 * @return
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年5月10日 上午11:34:08
 */
public List<CollegeTable> getColleges(Integer tid);

}
