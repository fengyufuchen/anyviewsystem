package com.anyview.utils.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;

import com.anyview.entities.ProblemContentVO;

public class HibernateUtils {
	
	public static Log log = LogFactory.getLog(HibernateUtils.class);
	
	public static List<?> getList( Session session , String hql , int offset, int length){
       Query q = session.createQuery(hql);
       q.setFirstResult(offset);
       q.setMaxResults(length);
       log.info(hql);
       List<?> list = q.list();
       log.info("取到的每页的size"+list.size());
       return list;
    }
	
}
