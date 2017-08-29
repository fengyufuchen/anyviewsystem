package com.anyview.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.TeacherTable;

public interface ClassTeacherDao {
	
	/**
	 * 获取教师在一个班级上的权限等级
	 * @param tid教师Id
	 * @param cid班级Id
	 * @return
	 */
	public Integer getTCRight(Integer tid, Integer cid);
	
	/**
	 * 更新班级教师班级权限信息
	 * @param ctt
	 */
	public void updateTCRight(Integer tcRight, Integer tid, Integer cid);
	
	/**
	 * 
	 * @Description: TODO(根据教师id获取班级) 
	 * @param tid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午7:33:28
	 */
	public  List<ClassTeacherTable> getClassByTId(Integer tid);
	
	/**
	 * 获取班级中的教师
	 * 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月20日 下午5:29:33
	 */
	public List<ClassTeacherTable> getTeachersOnClass(int firstResult, int maxResults, DetachedCriteria criteria);
	
	/**
	 * 获取班级中教师的数目
	 * 
	 * @param criteria
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月20日 下午5:33:37
	 */
	public Integer getTeachersCountOnClass(DetachedCriteria criteria);
	/**
	 * add ClassTeacherTable
	 * 
	 * @param tid
	 * @param right
	 * @param cid
	 * @param now
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午2:44:52
	 */
	public void saveTeacherToClass(Integer tid, Integer right, Integer cid, Timestamp now);
	/**
	 * 根据tid和cid删除ClassTeacherTable中的记录
	 * 
	 * @param tid
	 * @param cid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午7:25:24
	 */
	public void deleteTeacherOnClass(Integer tid, Integer cid);
	/**
	 * 根据tid和cid获取ClassTeacherTable对象
	 * 
	 * @param cid
	 * @param tid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年3月22日 下午8:31:45
	 */
	public ClassTeacherTable getClassTeacherByCidAndTid(Integer cid, Integer tid);
}
