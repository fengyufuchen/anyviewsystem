package com.anyview.service.function;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.anyview.entities.Pagination;
import com.anyview.entities.SemesterTable;

/**
 * 文件名：SemesterManager.java
 * 描   述：学期管理相关管理方法类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

public interface SemesterManager {
	
	//获取学期数据
	public List<SemesterTable> getSemesters( );
	
	//根据学期的ID删除学期信息 
	public boolean deleteSemester(int sid);
	
	//获取学期管理页面数据
	public Pagination<SemesterTable> getSemesterPage(Map param);
	
	//修改学期信息
	public boolean updateSemester(int sid, Timestamp startTime, Timestamp endTime);
	
	//判断学期是否已经存在于数据库当中
	public boolean isSemesterexist(String sname);
	
	//添加学期信息
	public boolean saveSemester(String sname, Timestamp startTime, Timestamp endTime);
}
