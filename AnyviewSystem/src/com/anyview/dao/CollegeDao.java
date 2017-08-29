package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;

/**
 * 
* @ClassName: CollegeDao 
* @Description：学院管理数据访问接口类
* @author：刘武
* @date：2015年9月12日 上午10:15:55 
*
 */
public interface CollegeDao {
	
	/**
	 * get college by ceID
	 * @param ceID
	 * @return
	 */
	public CollegeTable getCollegeById(Integer ceID);
	
	//通过学校ID获取该学校的所有下属学院
    public List<CollegeTable>getCollegesByUnid(Integer unId);
    
    /**
	 * 获取学院id和name组成的object[]的list
	 * @param unID
	 * @return
	 */
    public List<Object[]> getCollegesINByUnId(Integer unID);
  	
  	/**
  	 * 
  	 * @Description： 获取学院总数
  	 * @return：
  	 * @author：刘武 
  	 * @date：2015年9月11日 下午7:30:20
  	 */
  	public Integer getCollegeCount();
  	
  	/**
  	 * 
  	 * @Description： 保存学院信息
  	 * @param college，需要保存的学院
  	 * @return：代表处理结果的字符串false或者true
  	 * @author：刘武 
  	 * @date：2015年9月12日 上午10:18:21
  	 */
  	public boolean saveCollege(CollegeTable college);
  	
  	/**
  	 * 
  	 * @Description： 获取数据库中全部的学院信息
  	 * @return：学院信息列表
  	 * @author：刘武 
  	 * @date：2015年9月12日 上午11:14:02
  	 */
  	public List<CollegeTable> gainAllColleges();
  	
  	/**
  	 * 
  	 * @Description：修改学院信息 
  	 * @param college,需要保存的学院信息集
  	 * @return 
  	 * @throws Exception 
  	 * @return：代表处理结果的字符串false或者true
  	 * @author：刘武 
  	 * @date：2015年9月13日 下午8:35:37
  	 */
  	public boolean updateCollege(CollegeTable college) throws Exception ;
  	
  	/**
  	 * 
  	 * @Description： 根据学院ID删除对应的学院信息
  	 * @param ceId
  	 * @return：代表处理结果的字符串
  	 * @author：刘武 
  	 * @date：2015年9月14日 上午10:14:28
  	 */
  	public boolean deleteCollege(Integer ceId);
  	 /**
     * 
     * @Description: TODO(获取学校中可用的学院的id和name) 
     * @param unId
     * @return
     * @author 何凡 <piaobo749@qq.com>
     * @date 2015年9月13日 下午11:04:12
     */
    public List<Object[]> getEnabledCollegesByUnId(Integer unId);
    /**
     * 
     * @Description: TODO(根据学校Id获取学院) 
     * @param ids
     * @return
     * @author 何凡 <piaobo749@qq.com>
     * @date 2015年9月15日 上午1:49:23
     */
    public List<CollegeTable> getCollegesByUnIds(Integer[] ids);

	public List<CollegeTable> getCollegesPage(Integer firseResult, Integer maxResults,
			DetachedCriteria criteria);

	public Integer getCollegeCount(DetachedCriteria criteria);

}
