package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.AdminDao;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.StudentTable;

@Repository
public class AdminDaoImpl  extends BaseDaoImpl implements AdminDao{

	@Override
	public ManagerTable gainAdminById(Integer mid) {
		return (ManagerTable) getSession().get(ManagerTable.class, mid);
	}

	@Override
	public ManagerTable gainAdminByMnoAndUnId(String mno, Integer unId) {
		String hql = "from ManagerTable mt left join fetch mt.university u where mno=? and u.unID=?";
		List list = hibernateTemplate.find(hql, new Object[]{mno,unId});
		if(list.size() == 0)
			return null;
		ManagerTable admin = (ManagerTable) list.get(0);
		return admin;
	}

}
