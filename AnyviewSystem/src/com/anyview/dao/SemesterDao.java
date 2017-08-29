package com.anyview.dao;

import java.util.List;
import java.util.Map;

import com.anyview.entities.SemesterTable;

/**
 * 文件名：SemesterDao.java
 * 描   述：学期管理数据访问接口类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

public interface SemesterDao {

	//获取学期数据
	public List<SemesterTable> getSemesters();
	
	//根据学期ID删除对应的学期信息
	public boolean deleteSemester(Integer sid);
	
	//修改学期
	public boolean updateSemester(SemesterTable semester);
	
	//获取学期管理页面数据
	public List<SemesterTable> getSemestersPage(Map param);
	
	//获取学期总数
	public Integer getSemesterCount();
	
	//根据ID查找对应的学期
	public SemesterTable selectBySId(Integer sid);
	
	//查找全部的学期信息
	public List<SemesterTable> selectAllSemester();
	
	//添加学期信息
	public boolean saveSemester(SemesterTable semester);
}
