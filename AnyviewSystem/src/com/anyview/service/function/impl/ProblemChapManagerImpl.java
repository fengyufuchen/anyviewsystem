package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ProblemChapDao;
import com.anyview.dao.ProblemDao;
import com.anyview.dao.ProblemLibDao;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ProblemChapManager;
import com.anyview.utils.TipException;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class ProblemChapManagerImpl implements ProblemChapManager{

	@Autowired
	private ProblemChapDao problemChapDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ProblemLibDao problemLibDao;
	
	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#getProblemChapPageByLid(java.util.Map)
	 */
	@Override
	public Pagination<ProblemChapTable> getProblemChapPageByLid(Map params) {
		ProblemChapTable chap = (ProblemChapTable) params.get("chap");
		Integer numPerPage = (Integer) params.get("numPerPage");
		Integer currentPage = (Integer) params.get("currentPage");
		String orderField = (String) params.get("orderField");
		String orderDirection = (String) params.get("orderDirection");
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemChapTable.class)
				.createAlias("problemLib", "lib").createAlias("parentChap", "parent")
				.add(Restrictions.eq("lib.lid", chap.getProblemLib().getLid()))
				.add(Restrictions.eq("parent.chId", chap.getParentChap().getChId()))
				.add(Example.create(chap).enableLike(MatchMode.ANYWHERE));
		Pagination<ProblemChapTable> page = new Pagination<ProblemChapTable>();
		page.setContent(problemChapDao.getProblemChaps(criteria, (currentPage-1)*numPerPage, numPerPage, orderField, orderDirection));
		page.setTotalCount(problemChapDao.getProblemChapsCount(criteria));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#getParentChap(java.lang.Integer)
	 */
	@Override
	public ProblemChapTable getParentChap(Integer chId) {
		ProblemChapTable chap = problemChapDao.getProblemChapByChId(chId);
		if(chap.getChId() == -1)
			return chap;
		else
			return chap.getParentChap();
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#saveProblemChap(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public void saveProblemChap(ProblemChapTable chap) throws Exception {
		chap.setCreateTime(new Timestamp(System.currentTimeMillis()));
		Integer count = problemChapDao.checkChapName(chap);
		if(count>0)
			throw new TipException("目录重名");
		problemChapDao.saveProblemChap(chap);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#deleteProblemChap(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public void deleteProblemChap(ProblemChapTable chap) throws Exception {
		//获取所有子目录的id
		List<Integer> chIds = problemChapDao.getAllChildrenChapIdByParentChIds(Arrays.asList(new Integer[]{chap.getChId()}), false);
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class)
				.createAlias("problemChap", "chap").add(Restrictions.in("chap.chId", chIds));
		Integer proCount = problemDao.getProblemsCount(criteria);
		if(proCount>0){
			throw new TipException("该目录（子目录）下仍有题目存在");
		}
		problemChapDao.deleteProblemChaps(chIds);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#getProblemChapById(java.lang.Integer)
	 */
	@Override
	public ProblemChapTable getProblemChapById(Integer chId) {
		return problemChapDao.getProblemChapByChId(chId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemChapManager#updateProblemChap(com.anyview.entities.ProblemChapTable)
	 */
	@Override
	public void updateProblemChap(ProblemChapTable chap) throws Exception {
		chap.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Integer count = problemChapDao.checkChapName(chap);
		if(count>0)
			throw new TipException("目录重名");
		problemChapDao.updateProblemChap(chap);
	}

	@Override
	public List<ProblemChapTable> getAccessChapByParentId(Integer parentId,
			Integer tid) {
		if(problemChapDao.getProblemChapByChId(parentId).getProblemLib().getTeacher().getTid().intValue() == tid.intValue())
			return problemChapDao.getOwnLibChapsByParentId(parentId);
		return problemChapDao.getOtherLibChapsByParentId(parentId);
	}

	@Override
	public List<ProblemChapTable> getFirstChapsByLib(Integer lid, Integer tid) {
		if(problemLibDao.getProblemLibByLid(lid).getTeacher().getTid().intValue() == tid.intValue())
			return problemChapDao.getOwnLibFirstChaps(lid);
		return problemChapDao.getOtherLibFirstChaps(lid);
	}

}
