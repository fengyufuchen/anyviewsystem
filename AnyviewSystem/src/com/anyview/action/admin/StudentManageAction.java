package com.anyview.action.admin;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.action.common.CommonAction;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.StudentsManager;
import com.anyview.util.dwz.AjaxObject;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;

@SuppressWarnings("serial")
@Namespace("/admin/studentManager")
@ParentPackage("adminBasePkg")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})
public class StudentManageAction extends CommonAction{
	
	@Autowired
	private StudentsManager studentsManager;
	
	private StudentTable stu;
	
	private String jsonStr;
	
	public StudentTable getStu() {
		return stu;
	}

	public void setStu(StudentTable stu) {
		this.stu = stu;
	}

}
