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
@Table(name="ProblemLibTable",catalog="anyviewdb")
public class ProblemLibTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer lid;//题库ID,关键字
	private String lname;//题库名称，可以重复，但（TID +LName）唯一
	private Integer visit;//访问级别：(0私有 1部分公开 2本学院公开 3 本校公开 4 完全公开)注:1部分公开 见”题库-教师访问权限表”
	private String kind;//类别
	private Timestamp updateTime;
	private Timestamp createTime;//创建时间
	
	private TeacherTable teacher;//创建教师
	private UniversityTable university;//创建教师所在学校ID,冗余,加快查询,通过触发器同步，索引
	
	public ProblemLibTable(){
		
	}
	
	public ProblemLibTable(Integer lid){
		this.lid = lid;
	}
	
	@Id
	@Column(name="LID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	@Column(name="LName",length=255)
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	@Column(name="Visit")
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	@Column(name="Kind")
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
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
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UnID")
	public UniversityTable getUniversity() {
		return university;
	}
	public void setUniversity(UniversityTable university) {
		this.university = university;
	}
	
	
}
