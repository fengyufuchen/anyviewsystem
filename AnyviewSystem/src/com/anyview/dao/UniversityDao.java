package com.anyview.dao;

import java.util.List;
import java.util.Map;

import com.anyview.entities.UniversityTable;
import com.anyview.entities.ChinaUniversityTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ProvinceTable;

/**
 * 文件名：UniversityDao.java
 * 描   述：学校管理数据访问接口类
 * 时   间 ：2015年08月05日
 * @author WuLiu
 * @version 1.0
 */

public interface UniversityDao {
	//获取全部的学校信息
	public List<UniversityTable> gainAllUniversities();
	
	//根据ID查找对应的学校
	public UniversityTable getUniversityById(Integer unId);	
	
    //根据学校ID删除对应的学校信息
	public boolean deleteUniversity(Integer unId);
	
	//修改学校信息
	public boolean updateUniversity(UniversityTable university);
		
	//获取学校管理页面数据
	public  List<UniversityTable> getUniversitysPage(Map param) ;
		
	//获取学校总数
	public Integer getUniversityCount(List<UniversityTable> univer);
		
	//添加学校信息
	public boolean saveUniversity(UniversityTable university);
	/**
	 * 
	 * @Description: TODO(获取所有未停用的学校列表) 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午7:08:27
	 */
	public List<UniversityTable> gainAllEnabledUniversities();
	/**
	 * 
	 * @Description: TODO(根据名字每个字首字母查询学校) 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午8:03:37
	 */
	public List<UniversityTable> searchUniversity(String param);
	/**
	 * 根据学校名字模糊查询
	 * @Description: TODO() 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午8:21:32
	 */
	public List<UniversityTable> searchUniversityByName(String param);
}
