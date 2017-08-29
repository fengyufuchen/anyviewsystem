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
@Table(name="Class_Teacher_CourseTable",catalog="anyviewdb")
public class ClassTeacherCourseTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer ctcright;
	private Timestamp updateTime;
    
    private TeacherTable teacher;
    private CourseTable course;
    private ClassTable cla;
    
    @Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="CTCRight")
	public Integer getCtcright() {
		return ctcright;
	}
	public void setCtcright(Integer ctcright) {
		this.ctcright = ctcright;
	}
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne(targetEntity=CourseTable.class)
	@JoinColumn(name="CourseID")
	public CourseTable getCourse() {
		return course;
	}
	public void setCourse(CourseTable course) {
		this.course = course;
	}
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
    
    
}