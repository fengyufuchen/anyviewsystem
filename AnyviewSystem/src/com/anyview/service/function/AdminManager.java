package com.anyview.service.function;

import java.util.List;
import java.util.Map;

import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.TipException;

public interface AdminManager {
	/**
	 * 
	 * @Description: TODO(获取管理员列表) 
	 * @return
	 * @author 李倩 <1014664490@qq.com> 
	 * @date 2015年9月14日 下午10:21:51
	 */
	//获取所有管理员
	public List<ManagerTable> gainAllAdmins();
	
	//获取管理员管理页面数据
	public Pagination<ManagerTable> getAdminPage(Map param);
	
	/**
	 * 
	 * @Description: TODO(保存管理员信息) 
	 * @param admin
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午1:35:22
	 */
	public void saveAdmin(ManagerTable admin)throws Exception;
	/**
	 * 
	 * @Description: TODO(删除管理员) 
	 * @param mid
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午9:44:00
	 */
	public void deleteAdmin(Integer mid)throws Exception;
	/**
	 * 
	 * @Description: TODO(根据mid获取ManagerTable) 
	 * @param mid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 上午10:45:06
	 */
	public ManagerTable getAdminByMid(Integer mid);
	/**
	 * 
	 * @Description: TODO(修改管理员) 
	 * @param admin
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月17日 下午3:52:12
	 */
	public void updateAdmin(ManagerTable admin)throws Exception;
	/**
	 * 
	 * @Description: TODO(修改自身密码) 
	 * @param admin 登陆的管理员
	 * @param oldPwd
	 * @param newPwd
	 * @throws Exception
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年10月18日 上午12:39:26
	 */
	public void modifyPassword(ManagerTable admin, String oldPwd, String newPwd)throws Exception;
//	public void modifyPassword(ManagerTable admin, String oldPwd, String newPwd);

}
