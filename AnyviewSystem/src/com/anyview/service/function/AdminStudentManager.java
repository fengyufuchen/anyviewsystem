package com.anyview.service.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

/**
 * @Description 学生管理控制器接口类
 * @author DenyunFang
 * @time 2015年8月29日
 * @version 1.0
 */

public interface AdminStudentManager {	
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return Pagination<StudentTable> (封装了学生信息与页面信息的集合)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:26:18
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<ClassStudentTable> getStudentsPage(Map param);
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return Pagination<StudentTable> (封装了学生信息与页面信息的集合)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:26:18
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<StudentTable> getListStudentsPage(Map param);
	
	/**
	 * 
	 * @Description: 获取所有学校
	 * @return List<UniversityTable> (返回所有学校列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:48:20
	 */
	public List<UniversityTable> selectAllUniversity();
	
	/**
	 * 
	 * @Description: 根据学校ID获取所有学院 
	 * @param unID
	 * @return List<ClassTable> (返回该学校的所有学院列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:50:52
	 */
	public List<CollegeTable> selectAllCollegeByUnID(Integer unID);
	
	/**
	 * 
	 * @Description: 根据学院ID获取所有班级
	 * @param ceID
	 * @return List<ClassTable> (返回该学院的所有班级列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:48:49
	 */
	public List<ClassTable> selectAllClassByCeId(Integer ceID);
	
	/**
	 * 
	 * @Description: TODO(根据学生学号Sno和所在学校判断该学生是否存在于数据库) 
	 * @param stu
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:54:08
	 */
	public boolean isStudentexist(StudentTable stu);

	/**
	 * 
	 * @Description: TODO(保存学生信息到数据库中) 
	 * @param stu
	 * @return boolean (true表示保存成功，false表示保存失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:54:27
	 */
	public boolean saveStudent(StudentTable stu) throws TipException;
	
	/**
	 * 
	 * @Description: TODO(根据学生ID删除数据库的学生信息) 
	 * @param sid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:28:24
	 */
	public boolean deleteStudentBySid(Integer sid);
	
	/**
	 * 
	 * @Description: TODO(根据学生ID、班级ID与相关属性将学生与班级关联) 
	 * @param sid
	 * @param cid
	 * @return boolean (true表示关联成功，false表示关联失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午11:23:27
	 */
	public boolean addStudentInClass(Integer sid, Integer cid, ClassStudentTable cs);
	
	/**
	 * 
	 * @Description: TODO(批量根据学生ID与班级ID将学生与班级关联) 
	 * @param sid
	 * @param cid
	 * @return boolean (true表示关联成功，false表示关联失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午11:23:27
	 */
	public boolean batAddStudentInClass(Integer sid, Integer cid);
	
	/**
	 * 
	 * @Description: TODO(根据学生ID获取该学生所有信息) 
	 * @param sid
	 * @return List<StudentTable> (返回该学生的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:43:01
	 */
	public StudentTable gainStudentBySid(Integer sid);
	
	/**
	 * 
	 * @Description: TODO(修改数据库的学生信息) 
	 * @param stu
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:43:13
	 */
	public boolean updateStudent(StudentTable stu) throws TipException;
	
	/**
	 * 
	 * @Description: TODO(根据学生id、学生学号Sno和所在学校判断该学生是否存在于数据库) 
	 * @param stu
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午5:07:26
	 */
	public boolean isStudentexistBysid(StudentTable stu);
	
	/**
	 * 
	 * @Description: TODO(根据学生id初始化学生密码) 
	 * @param sid
	 * @return boolean (true表示初始化成功，false表示初始化失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午5:34:00
	 */
	public boolean initPassword(Integer sid);
	
	/**
	 * 
	 * @Description: TODO(根据学校ID获取该学校的所有信息列表) 
	 * @param unID
	 * @return List<UniversityTable> (返回该学校的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午7:37:29
	 */
	public List<UniversityTable> getUniversityByUnId(Integer unID);
	
	/**
	 * 
	 * @Description: TODO(批量添加学生) 
	 * @param stuList
	 * @return List<StudentTable> (返回状态值为"已提交"的学生列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午9:01:04
	 */
	@SuppressWarnings("rawtypes")
	public List batAddStudent(List stuList);
	
	/**
	 * 
	 * @Description: TODO(获取和解析excel表内容) 
	 * @param 读取的excel文件
	 * @param theClass
	 * @param claid
	 * @return
	 * @throws InvalidFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午9:16:54
	 */
	@SuppressWarnings("rawtypes")
	public Map getFileData(File file, String theClass, String claid) throws InvalidFormatException, FileNotFoundException, IOException;
	
}
