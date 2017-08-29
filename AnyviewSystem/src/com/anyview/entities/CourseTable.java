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
@Table(name="CourseTable",catalog="anyviewdb")
public class CourseTable implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer courseId;//课程Id
	private String courseName;//课程名称
	private Integer enabled;//有效状态 0停用1正常
	private String category;//分类名称
	private Timestamp createTime;
	private Timestamp updateTime;
	
	private CollegeTable college;//学院
	private UniversityTable university;//学校
	
	public CourseTable(){
		
	}
	
	public CourseTable(Integer courseId){
		this.courseId = courseId;
	}
	
	@Id
	@Column(name="CourseID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	@Column(name="CourseName",length=255)
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Column(name="Enabled")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="Category",length=255)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name="CreateTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=CollegeTable.class)
	@JoinColumn(name="CEID")
	public CollegeTable getCollege() {
		return college;
	}
	public void setCollege(CollegeTable college) {
		this.college = college;
	}
	
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UNID")
	public UniversityTable getUniversity() {
		return university;
	}
	public void setUniversity(UniversityTable university) {
		this.university = university;
	}

}
