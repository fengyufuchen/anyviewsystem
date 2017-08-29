/**   
* @Title: ClassStudentManagerImpl.java 
* @Package com.anyview.service.function.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月21日 下午4:20:26 
* @version V1.0   
*/
package com.anyview.service.function.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassStudentDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.service.function.ClassStudentManager;

@Service
public class ClassStudentManagerImpl implements ClassStudentManager{
	
	@Autowired
	private ClassStudentDao classStudentDao;

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassStudentManager#getClassStudentByCidAndSid(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid) {
		return classStudentDao.getClassStudentByCidAndSid(cid, sid);
	}

}
