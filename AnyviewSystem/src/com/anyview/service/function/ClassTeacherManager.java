package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;

public interface ClassTeacherManager {
	/**
	 * 获取相关老师信息
	 * @return
	 */
	public List<TeacherTable> getTeachers( );
	
	/**
	 * 获取班级页面
	 * @param pageSize 一页数目
	 * @param pageNum 第几页
	 * @param tId 教师ID
	 * @return
	 */
	public Pagination<TeacherTable> getTeacherPage(Integer pageSize, Integer pageNum);
	
	/**
	 * 获取教师在一个班级上的权限等级
	 * @param tid
	 * @param cid
	 * @return
	 */
	public Integer getTCRight(Integer tid, Integer cid);
	
	/**
	 * 更新相关权限信息
	 * 二进制
	 * 0001 查看学生状态
	 * 0010设置班级状态
	 * 0100重置学生密码
	 * 1000管理学生（增删改查）
	 * 以上可组合
	 * @param cla
	 * @param tcRight
	 */
	public void updateTCRight (Integer tcRight, Integer tid, Integer cid)throws Exception;
	/**
	 * 获取班级中教师的页面
	 * 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月20日 下午5:11:00
	 */
	public Pagination<ClassTeacherTable> getClassTeachers(Map param);
	/**
	 * 
	 * 
	 * @param map包含教师Id和对应的权限
	 * @param cid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午2:35:38
	 */
	public void saveTeachersToClass(Map<String, Integer>map, Integer cid);
	/**
	 * 删除班级与教师的关联关系
	 * 
	 * @param tid
	 * @param cid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午7:22:54
	 */
	public void deleteTeacherOnClass(Integer tid, Integer cid);
	/**
	 * 根据cid和tid获取ClassTeacherTable对象
	 * 
	 * @param cid
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午8:30:30
	 */
	public ClassTeacherTable getClassTeacherByCidAndTid(Integer cid, Integer tid);
	/**
	 * 获取待添加到课程的教师页面
	 * 不包含已经添加到课程的教师
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 上午1:32:35
	 */
	public Pagination<ClassTeacherTable> getClassTeachersForCourse(Map param);
	/**
	 * 获取教师对应的班级
	 * 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 下午1:11:20
	 */
	public Pagination<ClassTeacherTable> getClassesForTeacher(Map param);
	/**
	 * 根据id获取ClassTeacherTable
	 * 
	 * @param id
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月24日 下午2:03:01
	 */
	public ClassTeacherTable getClassTeacherTableById(Integer id);
	/**
	 * 分页查询 “班级教室课程”
	 * 
	 * @param param
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年8月7日 下午2:07:16
	 */
	public Pagination<ClassTeacherCourseTable> getClassTeacherCoursePage(Map param);
}
