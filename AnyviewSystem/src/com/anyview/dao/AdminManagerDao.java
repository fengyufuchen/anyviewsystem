package com.anyview.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.anyview.entities.ManagerTable;
import com.anyview.entities.UniversityTable;

public interface AdminManagerDao {
	//获取全部的管理员信息
	public List<ManagerTable> gainAllAdmins();
	
	/**
	 * 
	 * @Description: TODO(获取管理员管理页面数据) 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月15日 下午1:33:01
	 */
	public  List<ManagerTable> getAdminsPage(Integer firseResult, Integer maxResults, DetachedCriteria criteria) ;
	
	/**
	 * 
	 * @Description: TODO(获取管理员总数) 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月15日 下午1:33:30
	 */
	public Integer getAdminCount(DetachedCriteria criteria);
	/**
	 * 
	 * @Description: TODO(保存管理员信息) 
	 * @param admin
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午1:37:28
	 */
	public void saveAdmin(ManagerTable admin)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除管理员) 
	 * @param mid
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午9:47:24
	 */
	public void deleteAdmin(Integer mid)throws Exception;
	/**
	 * 
	 * @Description: TODO(根据mid获取ManagerTable) 
	 * @param mid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午10:46:29
	 */
	public ManagerTable getAdminByMid(Integer mid);
	/**
	 * 
	 * @Description: TODO(修改管理员信息) 
	 * @param admin
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 下午3:54:34
	 */
	public void updateAdmin(ManagerTable admin)throws Exception;
	/**
	 * 
	 * @Description: TODO(修改管理员密码) 
	 * @param admin
	 * @param pwd 加密后的字符串
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月18日 上午12:45:35
	 */
	public void modifyPassword(ManagerTable admin, String pwd)throws Exception;

}
