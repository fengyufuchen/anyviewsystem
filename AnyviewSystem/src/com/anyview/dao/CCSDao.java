package com.anyview.dao;

import java.util.List;

public interface CCSDao {
	
	public List getCCSByClass(String classId);

	public List getCCSByCourse(String courseId);

	int settingCCS(String classId, String courseId, String schemeId,
			String teacherId, String status);

}
