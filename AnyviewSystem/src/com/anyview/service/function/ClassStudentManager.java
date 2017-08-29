/**   
* @Title: ClassStudentManager.java 
* @Package com.anyview.service.function 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月21日 下午4:19:46 
* @version V1.0   
*/
package com.anyview.service.function;

import com.anyview.entities.ClassStudentTable;

public interface ClassStudentManager {
	
	/**
	 * 
	 * @Description: TODO(根据cid和sid获取ClassStudentTable) 
	 * @param cid
	 * @param sid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月21日 下午4:21:57
	 */
	public ClassStudentTable getClassStudentByCidAndSid(Integer cid, Integer sid);

}
