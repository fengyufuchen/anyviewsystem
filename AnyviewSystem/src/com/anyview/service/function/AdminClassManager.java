package com.anyview.service.function;


import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

/**
 * @Description 班级管理控制器接口类
 * @author DenyunFang
 * @time 2015年09月05日
 * @version 1.0
 */

public interface AdminClassManager {	

	/**
	 * 
	 * @Description: 将Map集合中的班级信息进行封装 
	 * @param param
	 * @return Pagination<ClassTable> (封装了班级信息与页面信息的集合)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月4日 下午3:35:04
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<ClassTable> getClassPage(Map param);
	
	/**
	 * 
	 * @Description: TODO(将Map集合中的学生信息进行封装) 
	 * @param param
	 * @return Pagination<ClassStudentTable> (封装了班级学生信息与页面信息的集合)
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午3:45:40
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<ClassStudentTable> getLookStudentPage(Map param);
	
	
	/**
	 * 
	 * @Description: 根据班级ID删除数据库的班级及该班级下的所有学生
	 * @param cid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午2:43:06
	 */
	public boolean deleteClassByCid(Integer cid);
	
	
	/**
	 * 
	 * @Description: 根据班级ID获取该班级所有信息
	 * @param cid
	 * @return List<ClassTable> (返回该班级的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午4:10:37
	 */
	public ClassTable gainClassByCid(Integer cid);
	
	
	/**
	 * 
	 * @Description: 根据学院ID和班级名称判断该班级是否存在于数据库
	 * @param clas
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:08:18
	 */
	public boolean isClassExistByCeid(ClassTable clas);
	
	/**
	 * 
	 * @Description: 根据学院ID和班级名称判断该班级是否存在于数据库，忽略判断cid
	 * @param clas
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:08:18
	 */
	public boolean isClassExist(ClassTable clas);
	

	/**
	 * 
	 * @Description: 修改数据库的班级信息 
	 * @param clas
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:07:44
	 */
	public boolean updateClass(ClassTable clas) throws TipException;
	
	/**
	 * 
	 * @Description: 修改数据库的班级学生信息 
	 * @param cs
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午5:07:44
	 */
	public boolean updateStudentInClass(ClassStudentTable cs) throws TipException;
	
	/**
	 * 
	 * @Description: 获取所有学校
	 * @return List<UniversityTable> (返回所有学校列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午6:39:50
	 */
	public List<UniversityTable> selectAllUniversity();
	
	
	/**
	 * 
	 * @Description: 根据学校ID获取所有学院 
	 * @param UnID
	 * @return List<CollegeTable> (返回该学校的所有学院列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午6:40:24
	 */
	public List<CollegeTable> selectAllCollegeByUnID(Integer UnID);
	
	
	/**
	 * 
	 * @Description: 保存班级信息到数据库中
	 * @param cla
	 * @return boolean (true表示保存成功，false表示保存失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年9月5日 下午7:04:28
	 */
	public boolean saveClass(ClassTable cla) throws TipException;
	
	/**
	 * 
	 * @Description: 根据学院ID获取班级 
	 * @param ceId
	 * @return 班级ID和班级名字
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月18日 下午8:43:20
	 */
	public List<Object[]> getEnabledClassByCeId(Integer ceId);
	
	/**
	 * 
	 * @Description: TODO(根据cid和sid获取ClassStudentTable) 
	 * @param cid
	 * @param sid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:07:57
	 */
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid);
	
	/**
	 * 
	 * @Description: TODO(根据cid和sid删除班级学生关系) 
	 * @param cid
	 * @param sid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2015年10月23日 下午7:07:57
	 */
	public boolean deleteStudentInClass(Integer cid, Integer sid);
		
}
