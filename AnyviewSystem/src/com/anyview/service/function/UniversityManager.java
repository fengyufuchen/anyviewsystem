package com.anyview.service.function;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.anyview.entities.ChinaUniversityTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProvinceTable;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

/**
 * 文件名：UniversityManager.java
 * 描   述：学校管理相关管理方法类
 * 时   间 ：2015年08月05日
 * @author WuLiu
 * @version 1.0
 */
public interface UniversityManager {

	//获取所有学校
	public List<UniversityTable> gainAllUniversities();
	
	//根据学校的ID删除学校信息 
	public boolean deleteUniversity(int unId);
	
	//根据学校的ID获取学校的信息
	public UniversityTable gainUniversityByUnid(int unId);
	
	//获取学校管理页面数据
	public Pagination<UniversityTable> getUniversityPage(Map param);
				
	//判断学校是否已经存在于数据库当中
	public boolean isUniversityExist(String sname);
	
	//修改学校信息
	public boolean updateUniversity(UniversityTable univer);
	
	//添加学校信息
	public boolean saveUniversity(UniversityTable univer);
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
	 * @Description: TODO(根据名字每个字首字母匹配学校) 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午7:58:12
	 */
	public List<UniversityTable> searchUniversity(String param);
	/**
	 * 
	 * @Description: TODO(根据学校名字模糊查询) 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午8:20:35
	 */
	public List<UniversityTable> searchUniversityByName(String param);
}
