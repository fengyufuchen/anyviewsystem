package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;

public interface ClassManager {
	
	/**
	 * 根据教师id获取班级
	 * @param tId
	 * @return
	 */
	public List<ClassTable> getClassesByTid(Integer tid);
	
	/**
	 * 根据教师编号获取班级页面，并按班级编号排序
	 * @param pageSize 一页数目
	 * @param pageNum 第几页
	 * @param tId 教师ID
	 * @return
	 */
	public Pagination<ClassTable> getClassesPage(Map param);
	
	/**
	 * 根据教师身份,获取班级页面
	 * @param pageSize 一页数目
	 * @param pageNum 第几页
	 * @param tea 教师
	 * @return
	 */
	//弃用，待删 --hefan 2016.03.24
	@Deprecated
	public Pagination<ClassTable> getClassPageByTea(Integer pageSize, Integer pageNum, TeacherTable tea);
	
	/**
	 * 根据班级Id获取学生页面
	 * @param pageSize
	 * @param pageNum
	 * @param tId
	 * @return
	 */
	public Pagination<StudentTable> getStudentsPageByCid(Integer pageSize, Integer pageNum, Integer cid);
	
	/**
	 * 根据班级id和查询条件获取学生页面
	 * @param param 封装查询参数
	 * @return
	 */
	public Pagination<ClassStudentTable> getStudentsPage(Map param);
	
	/**
	 * 根据班级Id获取班级
	 * @param cid
	 * @return
	 */
	public ClassTable getClassByCid(Integer cid);
	
	/**
	 * 更新班级信息
	 * 判断权限
	 * 二进制
	 * 0001 查看学生状态
	 * 0010设置班级状态
	 * 0100重置学生密码
	 * 1000管理学生（增删改查）
	 * 以上可组合
	 * @param cla
	 * @param tcRight
	 */
	public void updateClass (ClassTable cla, Integer tcRight)throws Exception;
	
	/**
	 * 查询所有的班级信息
	 */
	public List<ClassTable> getAllClasses();
	
	/**
	 * 删除教师自己班级下的学生
	 * 需要判断权限
	 * @param sid
	 * @param tcRight
	 * @throws Exception
	 */
	public void deleteStudent(Integer sid, Integer tcRight)throws Exception;
	
	/**
	 * 
	 * @Description： 获取学院ceID拥有的班级数量
	 * @param ceId,查询的学院编号
	 * @return：ceID学院拥有的班级数量值
	 * @author：刘武 
	 * @date：2015年9月13日 下午10:37:30
	 */
	public Integer gainClassCountByCeid(Integer ceId);		
	/**
	 * 
	 * @Description: TODO(根据教师id查询在ClassTeacherCourseTable表中的班级) 
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 上午1:48:05
	 */
	public List<ClassTable> getClassFromClassTeacherCourse(Integer tid);
}
