package com.anyview.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anyview.dao.CCDao;
import com.anyview.dao.ClassDao;
import com.anyview.entities.CCTable;
import com.anyview.entities.ClassTable;

@Component
public class CCDaoImpl extends BaseDaoImpl implements CCDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CCTable> getInfo(String tName) {
		String hql = "from CCTable ";
		List<CCTable> cc = getHibernateTemplate().find(hql);
		return cc;
	}

}
