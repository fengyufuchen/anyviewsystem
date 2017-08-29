/**   
* @Title: BaseManagerImpl.java 
* @Package com.anyview.service.commons.impl 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年12月1日 下午5:11:22 
* @version V1.0   
*/
package com.anyview.service.commons.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.anyview.dao.BaseDao;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeTable;
import com.anyview.service.commons.BaseManager;

/** 
 * @ClassName: BaseManagerImpl 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年12月1日 下午5:11:22 
 *  
 */
@Service(value="baseManager")
public class BaseManagerImpl implements BaseManager{
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	/* (non-Javadoc)
	 * @see com.anyview.service.commons.BaseManager#getPage(java.lang.Integer, java.lang.Integer, java.util.List, java.lang.Integer)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pagination getPage(Integer currentPage, Integer numPerPage,
			List content, Integer totalCount) {
		Pagination<SchemeTable> page = new Pagination<SchemeTable>();
		page.setTotalCount(totalCount);
		page.setContent(content);
		page.setCurrentPage(currentPage);
		page.setNumPerPage(numPerPage);
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.commons.BaseManager#getPageByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public <T> Pagination<T> getPageByCriteria(DetachedCriteria criteria, Integer pageNum, Integer pageSize) {
		Pagination<T> page = new Pagination<T>();
		page.setTotalCount(baseDao.getCount(criteria));
		page.setContent((List<T>) baseDao.getList(criteria, pageNum, pageSize));
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		page.calcutePage();
		return page;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public <T> T getEntityById(Class c, Integer id) {
		return baseDao.getEntityById(c, id);
	}

	@Override
	public <T> List<T> getListByHql(Class c, String hql, Object[] params) {
		return baseDao.getListByHql(c, hql, params);
	}
	

}
