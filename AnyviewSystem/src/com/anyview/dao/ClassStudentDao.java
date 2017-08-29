/**   
* @Title: ClassStudentDao.java 
* @Package com.anyview.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月21日 下午4:22:58 
* @version V1.0   
*/
package com.anyview.dao;

import com.anyview.entities.ClassStudentTable;

public interface ClassStudentDao {

	/**
	 * 
	 * @Description: TODO(根据cid和sid获取ClassStudentTable对象) 
	 * @param cid
	 * @param sid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月21日 下午4:26:26
	 */
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid);
	/**
	 * 保存班级学生信息
	 * @Description: TODO() 
	 * @param cs
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月22日 上午2:26:15
	 */
	public void saveClassStudent(ClassStudentTable cs);
}
