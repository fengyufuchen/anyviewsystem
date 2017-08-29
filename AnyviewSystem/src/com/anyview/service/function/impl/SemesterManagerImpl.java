package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.SemesterDao;
import com.anyview.entities.Pagination;
import com.anyview.entities.SemesterTable;
import com.anyview.service.function.SemesterManager;

/**
 * 文件名：SemesterManager.java
 * 描   述：学期管理相关管理方法实现类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

@Service
public class SemesterManagerImpl implements SemesterManager {

	@Autowired
	private SemesterDao semesterDao;
	
	/**
	 * 函数名：getSemesters
	 * 描   述：获取学期数据
	 */
	@Override
	public List<SemesterTable> getSemesters() {
		List<SemesterTable> semesters = semesterDao.getSemesters();
		return semesters;
	}

	/**
	 * 函数名：deleteSemester
	 * 描   述：根据学期的ID删除学期信息
	 */
	@Override
	public boolean deleteSemester(int sid) {
		return semesterDao.deleteSemester(sid);
	}

	/**
	 * 函数名：getSemesterPage
	 * 描   述：获取学期管理页面数据
	 */
	@Override
	public Pagination<SemesterTable> getSemesterPage(Map param) {
		Pagination<SemesterTable> page = new Pagination<SemesterTable>();
		List<SemesterTable> sems = semesterDao.getSemestersPage(param);
		page.setContent(sems);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(semesterDao.getSemesterCount());
		page.calcutePage();
		return page;
	}

	/**
	 * 函数名：updateSemester
	 * 描   述：修改学期信息
	 */
	@Override
	public boolean updateSemester(int sid, Timestamp startTime, Timestamp endTime) {
		SemesterTable s = semesterDao.selectBySId(sid);
		s.setStartTime(startTime);
		s.setEndTime(endTime);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		s.setUpdateTime(t);
		return semesterDao.updateSemester(s);
	}

	/**
	 * 函数名：isSemesterexist
	 * 描   述：判断学期是否已经存在于数据库当中
	 */
	public boolean isSemesterexist(String sname) {
		List<SemesterTable> semesterList = semesterDao.selectAllSemester();
		for(SemesterTable s : semesterList){
			if(s.getSname().equals(sname))
				return true;
		}
		return false;
	}

	/**
	 * 函数名：saveSemester
	 * 描   述：添加学期信息
	 */
	@Override
	public boolean saveSemester(String sname, Timestamp startTime, Timestamp endTime) {
		if(isSemesterexist(sname) == true)
			return false;
		SemesterTable s = new SemesterTable();
		s.setSname(sname);
		s.setStartTime(startTime);
		s.setEndTime(endTime);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		s.setUpdateTime(t);
		return semesterDao.saveSemester(s);
	}

}	
