package com.anyview.service.function.impl;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.anyview.dao.ExerciseDao;
import com.anyview.dao.ProblemChapDao;
import com.anyview.dao.ProblemDao;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemContentVO;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ProblemManager;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.ExcelToObject;
import com.anyview.utils.TipException;
import com.anyview.utils.XmlUtil;

@Service
public class ProblemManagerImpl implements ProblemManager{

	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ProblemChapDao problemChapDao;
	@Autowired
	private ExerciseDao exerciseDao;

	@Override
	public List<Object[]> getProblemChapIN(Map params) {
		return problemDao.getProblemChapIN(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<ProblemTable> getProblemPageByCh(Map params) {
		Map<Integer,Set<Integer>> libChMap = (Map<Integer, Set<Integer>>) params.get("libChMap");
		List<Integer> lidList = new ArrayList<Integer>();
		List<Integer> chIdList = new ArrayList<Integer>();
		Set<Integer> lidSet = libChMap.keySet();
		for(Integer lid : lidSet){
			Set<Integer> chIdSet = libChMap.get(lid);
			if(chIdSet.contains(-1)){// -1为题库的chId
				lidList.add(lid);
			}else{
				chIdList.addAll(chIdSet);
			}
		}
		Integer visit = (Integer) params.get("visit");
		List<Integer> childrenChap = null;
		//对已ilb查询出其下的所有目录id
		childrenChap = problemChapDao.getAllChapIdByLibIds(lidList,visit==0?false:true);
		chIdList.addAll(childrenChap);
		//对于chap查询出所有子目录id
		childrenChap = problemChapDao.getAllChildrenChapIdByParentChIds(chIdList,visit==0?false:true);
		chIdList.addAll(childrenChap);
		params.put("chIds", chIdList);
		Pagination<ProblemTable> page = new Pagination<ProblemTable>();
		page.setContent(problemDao.getProblemListByCh(params));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.setTotalCount(problemDao.getProblemCountByCh(params));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#getProblemLibsByKind(java.lang.Integer)
	 */
	@Override
	public String getProblemLibsINByKind(TeacherTable teacher, Integer kind) {
		List<Object[]>libIN=null; 
		if(kind==0){
			libIN = problemDao.getTeacherCreateLibs(teacher);
		}else if(kind==1){
			libIN = problemDao.getTeacherAccessableLibs(teacher);
		}else if(kind==2){
			libIN = problemDao.getCollegePublicLibs(teacher);
		}else if(kind==3){
			libIN = problemDao.getUniversityPublicLibs(teacher);
		}else if(kind==4){
			libIN = problemDao.getAllPublicLibs();
		}
		return ResponseUtils.getIdAndNameParentJson(libIN);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#getProblemByPid(java.lang.Integer)
	 */
	@Override
	public ProblemTable getProblemByPid(Integer pid) {
		return problemDao.getProblemByPid(pid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#getProblemsByPids(java.lang.Integer[])
	 */
	@Override
	public List<ProblemTable> getProblemsByPids(Integer[] ids) {
		return problemDao.getProblemsByPids(ids);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#getProblemPageByChId(java.util.Map)
	 */
	@Override
	public Pagination<ProblemTable> getProblemPageByChId(Map params) {
		ProblemTable problem = (ProblemTable) params.get("problem");
		Integer numPerPage = (Integer) params.get("numPerPage");
		Integer currentPage = (Integer) params.get("currentPage");
		String orderField = (String) params.get("orderField");
		String orderDirection = (String) params.get("orderDirection");
		DetachedCriteria criteria = DetachedCriteria.forClass(ProblemTable.class)
				.createAlias("problemChap", "chap")
				.add(Restrictions.eq("chap.chId", problem.getProblemChap().getChId()))
				.add(Example.create(problem).enableLike(MatchMode.ANYWHERE));
		Pagination<ProblemTable> page = new Pagination<ProblemTable>();
		page.setContent(problemDao.getProblems(criteria, (currentPage-1)*numPerPage, numPerPage, orderField, orderDirection));
		page.setTotalCount(problemDao.getProblemsCount(criteria));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#saveProblem(com.anyview.entities.ProblemTable, com.anyview.entities.ProblemContentVO)
	 */
	@Override
	public void saveProblem(ProblemTable problem,ProblemContentVO problemContent) throws Exception {
		//根据题目类型生成xml
		String xmlStr = "";
		switch(problem.getKind()){
		case 0 : xmlStr = XmlUtil.createProgramXML(problemContent).asXML();break;
		case 1 : break;
		case 2 : break;
		case 3 : xmlStr = XmlUtil.createChoiceXML(problemContent, true).asXML();break;
		case 4 : xmlStr = XmlUtil.createChoiceXML(problemContent, false).asXML();break;
		case 5 : xmlStr = XmlUtil.createJudgmentXML(problemContent).asXML();break;
		}
		//题目提示xml
		String tipXml = XmlUtil.createTipXML(problem.getPtip()).asXML();
		problem.setPcontent(xmlStr);
		problem.setPtip(tipXml);
		problem.setCreateTime(new Timestamp(System.currentTimeMillis()));
		problemDao.saveProblem(problem);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#deleteProblem(com.anyview.entities.ProblemTable)
	 */
	@Override
	public void deleteProblem(ProblemTable problem) throws Exception {
		//删除答案
		exerciseDao.deleteExerciseByPid(problem.getPid());
		//删除题目
		problemDao.deleteProblem(problem.getPid());
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ProblemManager#updateProblem(com.anyview.entities.ProblemTable, com.anyview.entities.ProblemContentVO)
	 */
	@Override
	public void updateProblem(ProblemTable problem,
			ProblemContentVO problemContent) throws Exception {
		//根据题目类型生成xml
		String xmlStr = "";
		switch(problem.getKind()){
		case 0 : xmlStr = XmlUtil.createProgramXML(problemContent).asXML();break;
		case 1 : break;
		case 2 : break;
		case 3 : xmlStr = XmlUtil.createChoiceXML(problemContent, true).asXML();break;
		case 4 : xmlStr = XmlUtil.createChoiceXML(problemContent, false).asXML();break;
		case 5 : xmlStr = XmlUtil.createJudgmentXML(problemContent).asXML();break;
		}
		//题目提示xml
		String tipXml = XmlUtil.createTipXML(problem.getPtip()).asXML();
		problem.setPcontent(xmlStr);
		problem.setPtip(tipXml);
		problem.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		problemDao.updateProblem(problem);
	}

	@Override
	public List<ProblemTable> getAccessProblemsByChId(Integer chId, Integer tid){
		if(problemChapDao.getProblemChapByChId(chId).getProblemLib().getTeacher().getTid().intValue() == tid.intValue())
			return problemDao.getOwnLibProblemsByChId(chId);
		return problemDao.getOtherLibProblemsByChId(chId);
	}

	@Override
	public int importProblemFromExcel(FileInputStream fin, Integer lid, Integer chId, Integer type)
			throws Exception {
		List<ProblemTable> list = null;
		//获取只读对象
		Workbook readwb = Workbook.getWorkbook(fin);
		switch(type){
			case 0 : {
				list = ExcelToObject.ProgramToObject(readwb,type, chId, lid);
				break;
			}
			case 1 : break;
			case 2 : break;
			case 3 : ;
			case 4 : {
				list = ExcelToObject.ChooseToObject(readwb,type, chId, lid);
				break;
			}
			case 5 : {
				list = ExcelToObject.JudgmentToObject(readwb,type, chId, lid);
				break;
			}
			default : throw new TipException("不支持此类型的题");
		}
		//查询出当前目录下所有题目的名称，再判断导入的题目名称有无重复
		List<String> existedName = problemDao.getAllNamesByChId(chId);
		List<String> newName = new ArrayList<String>();
		for(ProblemTable p : list){
			newName.add(p.getPname());
		}
		newName.retainAll(existedName);
		if(newName.size() > 0){
			String tip = "";
			for(String s : newName){
				tip+=s+",";
			}
			throw new TipException(tip+"这些名称已经存在于此目录中");
		}
			
		//批量存数据库
		problemDao.saveManyProblems(list);
		return list.size();
	}

}
