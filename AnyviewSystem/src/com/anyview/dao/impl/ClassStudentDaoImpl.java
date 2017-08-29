/**   
* @Title: ClassStudentDaoImpl.java 
* @Package com.anyview.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月21日 下午4:23:20 
* @version V1.0   
*/
package com.anyview.dao.impl;

import org.springframework.stereotype.Repository;

import com.anyview.dao.ClassStudentDao;
import com.anyview.entities.ClassStudentTable;

@Repository
public class ClassStudentDaoImpl extends BaseDaoImpl implements ClassStudentDao{

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassStudentDao#getClassStudentByCidAndSid(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid) {
		String hql = "from ClassStudentTable where cla.cid=? and student.sid=?";
		return (ClassStudentTable) hibernateTemplate.find(hql, new Object[]{cid, sid}).get(0);//唯一性用数据库索引约束
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.ClassStudentDao#saveClassStudent(com.anyview.entities.ClassStudentTable)
	 */
	@Override
	public void saveClassStudent(ClassStudentTable cs) {
		hibernateTemplate.save(cs);
	}

}
