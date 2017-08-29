package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.CollegeDao;
import com.anyview.dao.ProblemChapDao;
import com.anyview.dao.ProblemLibDao;
import com.anyview.dao.TeacherDao;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ProblemLibManager;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@Service
public class ProblemLibManagerImpl implements ProblemLibManager{
	
	@Autowired
	private ProblemLibDao problemLibDao;
	@Autowired
	private CollegeDao collegeDao;
	@Autowired
	private TeacherDao teacherDao;

	@Override
	public List<ProblemLibTable> getProblemLibByMiden(ManagerTable admin) {
		if(admin.getMiden()==-1){
			return problemLibDao.getAllProblemLibs();
		}else if(admin.getMiden()==1){
			return problemLibDao.getUProblemLibs(admin);
		}else if(admin.getMiden()==0){
			return problemLibDao.getCProblemLibs(admin);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#getAccessableProblemLibs(com.anyview.entities.TeacherTable)
	 */
	@Override
	public List<ProblemLibTable> getAccessableProblemLibs(TeacherTable teacher) {
		return problemLibDao.getAccessableProblemLibs(teacher);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#getProblemLibPage(java.util.Map)
	 */
	@Override
	public Pagination<ProblemLibTable> getProblemLibPage(Map params) {
		ProblemLibTable problemLib = (ProblemLibTable) params.get("problemLib");
		TeacherTable teacher = (TeacherTable) params.get("teacher");
		Integer numPerPage = (Integer) params.get("numPerPage");
		Integer currentPage = (Integer) params.get("currentPage");
		String orderField = (String) params.get("orderField");
		String orderDirection = (String) params.get("orderDirection");
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemLibTable.class)
				.createAlias("teacher", "t").add(Restrictions.eq("t.tid", teacher.getTid()))
				.add(Example.create(problemLib).enableLike(MatchMode.ANYWHERE));
		Pagination<ProblemLibTable> page = new Pagination<ProblemLibTable>();
		page.setContent(problemLibDao.getTeacherCreateLibs(criteria, currentPage, numPerPage, orderField, orderDirection));
		page.setTotalCount(problemLibDao.getTeacherCreateLibCount(criteria));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#saveProblemLib(com.anyview.entities.ProblemLibTable, java.lang.String)
	 */
	@Override
	public void saveProblemLib(ProblemLibTable lib, String tidstr)
			throws Exception {
		//保存problemLib获取返回的id
		lib.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Integer lid = problemLibDao.saveProblemLib(lib);
		if(lib.getVisit()==1 && tidstr!=null && !"".equals(tidstr)){
			//visit==1(部分公开)时保存可访问教师Id
			Integer []ids = ResponseUtils.transformIntegerArr(tidstr.split(","));
			problemLibDao.saveProblemLibTeachers(lid, ids);
		}
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#getProblemLibByLid(java.lang.Integer)
	 */
	@Override
	public ProblemLibTable getProblemLibByLid(Integer lid) {
		return problemLibDao.getProblemLibByLid(lid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#getAccessableTeachers(java.lang.Integer)
	 */
	@Override
	public List<TeacherTable> getAccessableTeachers(Integer lid) {
		return problemLibDao.getAccessableTeachers(lid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#updateProblemLib(com.anyview.entities.ProblemLibTable, java.lang.String)
	 */
	@Override
	public void updateProblemLib(ProblemLibTable lib, String tidstr)
			throws Exception {
		//更新problemLib
		lib.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		problemLibDao.updateProblemLib(lib);
		//删除旧的题库-教师关联
		problemLibDao.deleteAllAccessTeachers(lib.getLid());
		//保存新的关联
		if(lib.getVisit()==1 && tidstr!=null && !"".equals(tidstr)){
			//visit==1(部分公开)时保存可访问教师Id
			Integer []ids = ResponseUtils.transformIntegerArr(tidstr.split(","));
			problemLibDao.saveProblemLibTeachers(lib.getLid(), ids);
		}
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemLibManager#deleteProblemLib(com.anyview.entities.ProblemLibTable)
	 */
	@Override
	public void deleteProblemLib(ProblemLibTable lib) throws Exception {
		ProblemLibTable problemLib = problemLibDao.getProblemLibByLid(lib.getLid());
		if(problemLib==null)
			throw new TipException("题库不存在!");
		if(problemLibDao.getProblemChapByLid(problemLib.getLid())>0){
			throw new TipException("题库中还存在题目目录，请先删除所有目录再删除题库!");
		}
		//部分访问级别的还要删除ProblemLibTeacherTable
		if(problemLib.getVisit()==1)
			problemLibDao.deleteAllAccessTeachers(problemLib.getLid());
		problemLibDao.deleteProblemLib(problemLib);
	}

	@Override
	public List<ProblemLibTable> searchAccessLibs(Integer unId, Integer ceId,
			TeacherTable teacher) {
		List<ProblemLibTable> result = new ArrayList<ProblemLibTable>();
		//教师自己创建的题库
		result.addAll(problemLibDao.getLibsCreateByTeacher(teacher.getTid()));
		//在题库-教师表中对应的题库
		result.addAll(problemLibDao.getLibsInLibTeacherTable(teacher.getTid()));
		if(ceId!=null){
			//选择的学院是否是当前登录教师所属学院标志
			boolean ceflag = false;
			//选择的学院是否是当前登录教师所属学校中的学院标志
			boolean unflag = false;
			List<CollegeTable> ces = teacherDao.getColleges(teacher.getTid());
			for(CollegeTable c:ces){
				if(c.getCeID().intValue() == ceId.intValue()){
					ceflag = true;
					break;
				}
			}
			CollegeTable scoll = collegeDao.getCollegeById(ceId);
			unflag = scoll.getUniversity().getUnID().intValue() == teacher.getUniversity().getUnID().intValue()?true:false;
			if(ceflag){
				//ceId学院中教师创建的本院公开的题库
				result.addAll(problemLibDao.getCLibsInCollege(ceId));
			}
			if(unflag){
				//ceId学院中教师创建的本校公开的题库
				result.addAll(problemLibDao.getULibsInCollege(ceId));
			}
			//ceId学院中教师创建的完全公开题库
			result.addAll(problemLibDao.getAllPublishLibsInCollege(ceId));
		}else if(unId != null){
			boolean unflag = teacher.getUniversity().getUnID().intValue() == unId.intValue()?true:false;
			if(unflag){
				//unId学校中本校公开的题库
				result.addAll(problemLibDao.getULibsInUniv(unId));
			}
			//unId学校中完全公开题库
			result.addAll(problemLibDao.getAllPublishLibsInUniv(unId));
		}
		return result;
	}

}
