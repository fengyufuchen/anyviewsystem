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
@Table(name="SchemeTable",catalog="anyviewdb")
public class SchemeTable implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vid;//作业计划ID,关键字
	private String vname;//计划名称,可以有重复,但（TID+VName）唯一
	private Integer kind;//作业计划类型:0：作业题1：考试题2：资源表
	private Integer status;//状态：（0停用 1测试 2正式）
	private Integer visit;//问级别 (0私有 1部分公开 2本学院公开 3 本校公开 4 完全公开)注:1部分公开 见”作业表-教师访问权限表”
	private Float fullScore;//满分值
	private Timestamp updateTime;
	private Timestamp createTime;
	private Integer totalNum;//总题数 added by hefan 2015-12-24 10:51
	
	private TeacherTable teacher;//创建教师
	private CourseTable course;//课程
	
	/*
	 * 停用状态
	 */
	public static int STATUS_STOP = 0;
	/*
	 * 测试状态
	 */
	public static int STATUS_TEST = 1;
	/*
	 * 正式状态
	 */
	public static int STATUS_FORMAL = 2;
	/*
	 * 访问级别-私有
	 */
	public static int VISIT_PRIVATE = 0;
	/*
	 * 访问级别-部分公开
	 */
	public static int VISIT_PART_PUBLIC = 1;
	/*
	 * 访问级别-学院公开
	 */
	public static int VISIT_COLLEGE_PUBLIC = 2;
	/*
	 * 访问级别-学校公开
	 */
	public static int VISIT_UNIVERSITY_PUBLIC = 3;
	/*
	 * 访问级别-完全公开
	 */
	public static int VISIT_ALL_PUBLIC = 4;
	
	public SchemeTable() {
		
	}
	
	public SchemeTable(Integer vid, String vname) {
		super();
		this.vid = vid;
		this.vname = vname;
	}
	@Id
	@Column(name="VID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	
	@Column(name="VName",length=255)
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	
	@Column(name="Kind")
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	
	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="Visit")
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	
	@Column(name="FullScore")
	public Float getFullScore() {
		return fullScore;
	}
	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}
	
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID",nullable=true)
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne(targetEntity=CourseTable.class)
	@JoinColumn(name="CourseID",nullable=true)
	public CourseTable getCourse() {
		return course;
	}
	public void setCourse(CourseTable course) {
		this.course = course;
	}
	
	@Column(name="totalNum")
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;  
        if(this == obj) return true;  
        if(obj instanceof SchemeTable){   
        	SchemeTable s =(SchemeTable)obj;  
            if(s.vid == this.vid) return true; // 只比较id  
        }
        return false;  
	}

	@Override
	public int hashCode() {
		return vid.hashCode();
	}
	
	
}
