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
@Table(name="Class_CourseTable",catalog="anyviewdb")
public class ClassCourseTable implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO() 
	*/ 
	private static final long serialVersionUID = 1L;
	private Integer id;//关键字
	private Timestamp startYear;//开课时间
	private Integer status;//状态：（0停用，1正在使用）
	private Timestamp updateTime;
	
	private ClassTable cla;//班级
	private CourseTable course;//课程
	private TeacherTable teacher;//开设教师
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="StartYear")
	public Timestamp getStartYear() {
		return this.startYear;
	}
	public void setStartYear(Timestamp startYear) {
		this.startYear = startYear;
	}
	
	@Column(name="status")
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
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	@Override
	public String toString() {
		return "ClassCourseTable [id=" + id + ", startYear=" + startYear
				+ ", status=" + status + ", updateTime=" + updateTime + "]";
	}
	
	

}