package com.anyview.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TeacherTable",catalog="anyviewdb")
public class TeacherTable implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tid;//教师ID
	private String tname;//教师姓名
	private String tno;//教师编号
	private String tpsw;//登录密码，（TID+原密码）的MD5值
	private String tsex;//性别（M-男 F-女）
	private Integer tcId;//映射班级ID (默认-1),为每个教师建立一个测试班
	private Integer tsId;//映射学生ID (默认-1),教师帐号可以直接登录学生端，其班级ID是TCID
	private Integer enabled;//有效状态：0 停用 1正常
	private Timestamp CreateTime;
	private Timestamp updateTime;
	
	private UniversityTable university;//学校  ，单向1-N关联，teacher为N方，university为1方 
	private Set<CollegeTable> colleges = new HashSet<CollegeTable>();//学院，单向N-N关联，连接表为CollegeTeacherTable
	
	public TeacherTable(){
		
	}
	
	public TeacherTable(Integer tid){
		this.tid = tid;
	}
	
	@Id
	@Column(name="TID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	
	@Column(name="TNAME",length=255)
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	@Column(name="TPSW",length=255)
	public String getTpsw() {
		return tpsw;
	}
	public void setTpsw(String tpsw) {
		this.tpsw = tpsw;
	}
	
	@Column(name="TSEX",length=1)
	public String getTsex() {
		return tsex;
	}
	public void setTsex(String tsex) {
		this.tsex = tsex;
	}
	
	@Column(name="TCID")
	public Integer getTcId() {
		return tcId;
	}
	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}
	
	@Column(name="TSID")
	public Integer getTsId() {
		return tsId;
	}
	public void setTsId(Integer tsId) {
		this.tsId = tsId;
	}
	
	@Column(name="ENABLED")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="CreateTime")
	public Timestamp getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}

	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="TNO")
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UNID",nullable=false)
	public UniversityTable getUniversity() {
		return university;
	}
	public void setUniversity(UniversityTable university) {
		this.university = university;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToMany(targetEntity=CollegeTable.class,fetch=FetchType.LAZY)//默认延迟抓取 好像没有效果
	@JoinTable(name="College_TeacherTable", 
		joinColumns=@JoinColumn(name="tid", referencedColumnName="tid"), 
		inverseJoinColumns=@JoinColumn(name="ceid", referencedColumnName="ceid"))
	public Set<CollegeTable> getColleges() {
		return colleges;
	}

	public void setColleges(Set<CollegeTable> colleges) {
		this.colleges = colleges;
	}
	
}
