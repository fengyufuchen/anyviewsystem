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
public class ClassCourseSchemeTable implements java.io.Serializable{
	
	/** 
	* @Fields serialVersionUID : TODO() 
	*/ 
	private static final long serialVersionUID = 1L;
	private Integer id;//主键
	private Integer status;    //状态：（0停用 1使用）
	private Timestamp updateTime;
	
	private ClassTable cla;       // 关联班级
	private SchemeTable scheme;   // 关联作业表
	private TeacherTable teacher; // 关联教师
	private CourseTable course;   // 关联课程
	
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
	
	@ManyToOne(targetEntity=CourseTable.class)
	@JoinColumn(name="CourseID")
	public CourseTable getCourse() {
		return course;
	}
	public void setCourse(CourseTable course) {
		this.course = course;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID")
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	
}
