package com.anyview.service.function;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;

public interface CollegeManager {
	
	/**
	 * get colleges by unID
	 * @param param
	 * @return
	 */
	public Set<CollegeTable> getColleges(Map param);
    
	//通过学校ID获取该学校的下属学院的数量
	public Integer getCollegesByUnid(Integer unId);
	
	/**
	 * 获取学院id和name组成的object[]的list
	 * @param unID
	 * @return
	 * @throws Exception 
	 */
	public List<Object[]> getCollegesINByUnId(Integer unID) throws Exception;
	/**
	 * 
	 * @Description: TODO(根据学校id获取其中的所有可用的学院) 
	 * @param unId
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午11:02:50
	 */
	public List<Object[]> getEnabledCollegesByUnId(Integer unId);
	/**
	 * 
	 * @Description: TODO(根据学校id获取学院) 
	 * @param ids
	 * @return  
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 上午1:48:28
	 */
	public List<CollegeTable> getCollegesByUnIds(Integer []ids);
	
	/**
	 * 
	 * @Description：获取学院管理页面数据 
	 * @param param
	 * @return：Pagination<CollegeTable>
	 * @author：刘武 
	 * @date：2015年9月11日 下午5:38:24
	 */
		public Pagination<CollegeTable> getCollegePage(Map param);
		
		/**
		 * 
		 * @Description： 添加学院信息
		 * @param college,需要添加的学院信息
		 * @return：处理结果false或者true
		 * @author：刘武 
		 * @date：2015年9月12日 上午10:23:56
		 */
		public boolean addCollege(CollegeTable college);
		
		/**
		 * 
		 * @Description： 判断学院是否已经存在于数据库当中
		 * @param unID 
		 * @param ceName,需要判断的学院名称
		 * @return： 处理结果
		 * @author：刘武 
		 * @date：2015年9月12日 上午11:10:31
		 */
		public boolean isCollegeExist(Integer unID, String ceName);
		
		
		/**
		 * 
		 * @Description： 修改学院信息
		 * @param college,需要保存的学院信息集
		 * @throws Exception 
		 * @return：代表处理结果的字符串false或者true
		 * @author：刘武 
		 * @date：2015年9月13日 下午8:29:53
		 */
		public boolean updateCollege(CollegeTable college) throws Exception;
		
		/**
		 * 
		 * @Description： 通过学院编号ceID获取该学院的详细信息
		 * @param ceId
		 * @return：学院编号所代表学院的详细信息
		 * @author：刘武 
		 * @date：2015年9月13日 下午8:55:16
		 */
		public CollegeTable gainCollegeByCeid(Integer ceId);
		
		/**
		 * 
		 * @Description： 删除学院信息
		 * @param ceId，需删除学院的编码
		 * @return：代表处理结果的字符串
		 * @author：刘武 
		 * @date：2015年9月14日 上午10:10:55
		 */
		public boolean deleteCollege(int ceId);

}
