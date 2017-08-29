package com.anyview.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ClassTable",catalog="anyviewdb")
public class ClassTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cid;//班级ID,关键字，递增
	private String cname;//班级名称，可以重复，但（CeID +CName）唯一
	private String specialty;//所在专业
	private Integer startYear;//年届
	private Integer kind;//类型 (0 普通班级 1 教师映射班级)
	private Integer enabled;//有效状态：0-停用 1-正常
	private Integer status;//状态：0：未锁定1：登录锁定2：做题锁定3：考试锁定
	private Integer epId;//考试编排ID，考试锁定状态下起作用
	private Timestamp createTime;
	private Timestamp updateTime;
	
	private CollegeTable college;//所在学院
	
	/**
	 * 未锁定
	 */
	public static final int NO_LOCKED = 0;
	/**
	 * 考试锁定
	 */
	public static final int LOGIN_LOCKED = 1;
	/**
	 * 做题锁定
	 */
	public static final int EXERCISE_LOCKED = 2;
	/**
	 * 考试锁定
	 */
	public static final int EXAM_LOCKED = 3;
	
	public ClassTable() {
		
	}
	public ClassTable(Integer cid){
		this.cid = cid;
	}
	public ClassTable(int cid,String className) {
		this.cid = cid;
		this.cname = className;
	}
	@Id
	@Column(name="CID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	@Column(name="CNAME",length=255)
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	@Column(name="SPECIALTY",length=255)
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Column(name="STARTYEAR")
	public Integer getStartYear() {
		return startYear;
	}
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	
	@Column(name="KIND")
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	
	@Column(name="ENABLED")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="EPID")
	public Integer getEpId() {
		return epId;
	}
	public void setEpId(Integer epId) {
		this.epId = epId;
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
	
	public String toJsonStr(){
		return "{'cid':'"+cid+"',"
				+"'cname':'"+cname+"',"
				+"'specialty':'"+specialty+"',"
				+"'startYear':'"+startYear+"',"
				+"'kind':'"+kind+"',"
				+"'enabled':'"+enabled+"',"
				+"'status':'"+status+"',"
				+"'epId':'"+epId+"',"
				+"'createTime':'"+createTime+"',"
				+"'updateTime':'"+updateTime+"'}";
	}
	
	
}
