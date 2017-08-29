package com.anyview.service.function.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.AdminManagerDao;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminManager;
import com.anyview.utils.TipException;
import com.anyview.utils.encryption.MD5Util;

@Service
public class AdminManagerImpl implements AdminManager{
	
	@Autowired
	private AdminManagerDao adminManagerDao;

	@Override//说明被标注方法重载了父类方法
	//获取所有管理员
	public List<ManagerTable> gainAllAdmins() {
		return adminManagerDao.gainAllAdmins();
	}
	//获取管理员管理页面数据
	public Pagination<ManagerTable> getAdminPage(Map param){
		//组装查询条件DetachedCriteria
		ManagerTable admin = (ManagerTable) param.get("admin");
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		ManagerTable condition = (ManagerTable) param.get("condition");
		Timestamp createDateStart = (Timestamp) param.get("createDateStart");
		Timestamp createDateEnd = (Timestamp) param.get("createDateEnd");
		Timestamp updateDateStart = (Timestamp) param.get("updateDateStart");
		Timestamp updateDateEnd = (Timestamp) param.get("updateDateEnd");
		if(condition.getMno() != null && "".equals(condition.getMno().trim())){//如果mno为空串，则置null，使example查询自动排除此属性
			condition.setMno(null);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(ManagerTable.class)
				.add(Example.create(condition).enableLike(MatchMode.ANYWHERE));
		if(condition.getCollege()!=null && condition.getCollege().getCeID() != null)
			criteria = criteria.add(Restrictions.eq("college.ceID", condition.getCollege().getCeID()));
		if(createDateStart!=null && createDateEnd!=null)
			criteria = criteria.add(Restrictions.between("createTime", createDateStart, createDateEnd));
		if(updateDateStart!=null && updateDateEnd!=null)
			criteria = criteria.add(Restrictions.between("updateTime", updateDateStart, updateDateEnd));
		//获取页面内容
		Pagination<ManagerTable> page = new Pagination<ManagerTable>();
		if(admin.getMiden()==-1){//超级管理员查询出校级和院级
			criteria = criteria.add(Restrictions.in("miden", new Integer[]{1,0}));
			if(condition.getUniversity()!=null && condition.getUniversity().getUnID() != null){
				criteria = criteria.add(Restrictions.eq("university.unID", condition.getUniversity().getUnID()));
			}
		}else if(admin.getMiden()==1){//校级管理员查询出院级
			criteria = criteria.add(Restrictions.eq("miden", 0))
					.add(Restrictions.eq("university.unID", admin.getUniversity().getUnID()));
		}
		//注意：查询数量要放在查询数据之前
		page.setTotalCount(adminManagerDao.getAdminCount(criteria));
		page.setContent(adminManagerDao.getAdminsPage((pageNum-1)*pageSize, pageSize, criteria));
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.calcutePage();
		return page;
	}
	/* (non-Javadoc)
	 * @see com.anyview.service.function.AdminManager#saveAdmin(com.anyview.entities.ManagerTable)
	 */
	@Override
	public void saveAdmin(ManagerTable admin) throws Exception {
		admin.setCreateTime(new Timestamp(System.currentTimeMillis()));
		admin.setMpsw(MD5Util.getEncryptedPwd(admin.getMno()));//密码默认为编号
		adminManagerDao.saveAdmin(admin);
	}
	/* (non-Javadoc)
	 * @see com.anyview.service.function.AdminManager#deleteAdmin(java.lang.Integer)
	 */
	@Override
	public void deleteAdmin(Integer mid) throws Exception{
		adminManagerDao.deleteAdmin(mid);
	}
	/* (non-Javadoc)
	 * @see com.anyview.service.function.AdminManager#getAdminByMid(java.lang.Integer)
	 */
	@Override
	public ManagerTable getAdminByMid(Integer mid) {
		return adminManagerDao.getAdminByMid(mid);
	}
	/* (non-Javadoc)
	 * @see com.anyview.service.function.AdminManager#updateAdmin(com.anyview.entities.ManagerTable)
	 */
	@Override
	public void updateAdmin(ManagerTable admin) throws Exception {
		if(admin.getMiden()==1){
			admin.setCollege(new CollegeTable(-1));//校级管理员学院ID为-1
		}
		admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		adminManagerDao.updateAdmin(admin);
	}
	/* (non-Javadoc)
	 * @see com.anyview.service.function.AdminManager#modifyPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyPassword(ManagerTable admin, String oldPwd, String newPwd) throws Exception {
		if(!MD5Util.validPassword(oldPwd, admin.getMpsw())){
			throw new TipException("原密码错误!");
		}
		adminManagerDao.modifyPassword(admin, MD5Util.getEncryptedPwd(newPwd));
	}
//	@Override
//	public void modifyPassword(ManagerTable admin, String oldPwd, String newPwd) throws TipException {
//		try {
//			if(!MD5Util.validPassword(oldPwd, admin.getMpsw())){
//				throw new TipException("原密码错误!");
//			}
//			adminManagerDao.modifyPassword(admin, MD5Util.getEncryptedPwd(newPwd));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
