package com.anyview.dao;

import com.anyview.entities.ManagerTable;


public interface AdminDao {

	/**
	 * 获取管理员
	 * @param mid
	 * @return
	 */
	public ManagerTable gainAdminById(Integer mid);
	
	/**
	 * 根据编号和学校id 获取管理员
	 * @param mno
	 * @return
	 */
	public ManagerTable gainAdminByMnoAndUnId(String mno,Integer unId);
}
