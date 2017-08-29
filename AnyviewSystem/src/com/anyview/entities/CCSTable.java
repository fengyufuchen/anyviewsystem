package com.anyview.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Class_Course_SchemeTable",catalog="anyviewdb")
public class CCSTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassTable classTable;//
	private CourseTable courseTable;//
	private SchemeTable schemeTable;//
	private TeacherTable teacherTable;//
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID",nullable=false)
	public ClassTable getClassTable() {
		return classTable;
	}
	public void setClassTable(ClassTable classTable) {
		this.classTable = classTable;
	}
	
	@ManyToOne(targetEntity=CourseTable.class)
	@JoinColumn(name="CourseID",nullable=false)
	public CourseTable getCourseTable() {
		return courseTable;
	}
	public void setCourseTable(CourseTable courseTable) {
		this.courseTable = courseTable;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID",nullable=false)
	public SchemeTable getSchemeTable() {
		return schemeTable;
	}
	public void setSchemeTable(SchemeTable schemeTable) {
		this.schemeTable = schemeTable;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID",nullable=false)
	public TeacherTable getTeacherTable() {
		return teacherTable;
	}
	public void setTeacherTable(TeacherTable teacherTable) {
		this.teacherTable = teacherTable;
	}
	private Integer status;
	private Timestamp UpdateTime;
	
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		UpdateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
