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
@Table(name="ManagerTable",catalog="anyviewdb")
public class ManagerTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mid;//管理员ID
	private String mno;//管理员编号（登录名）
	private String mpsw;//登录密码，（MID+原密码）的MD5值*
	private Integer miden;//身份：0学院管理员1学校管理员-1超级管理员
    private Integer enabled;//有效状态：0 停用 1正常
    private Timestamp createTime;
    private Timestamp updateTime;
    
    private CollegeTable college;
    private UniversityTable university;
    
    @Id
	@Column(name="MID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	@Column(name="MNo",length=255)
	public String getMno() {
		return mno;
	}
	public void setMno(String mno) {
		this.mno = mno;
	}
//	@Column(name="MPsw",length=255,updatable=false)//设置此属性不包含在update中
	@Column(name="MPsw",length=255,updatable=true)
	public String getMpsw() {
		return mpsw;
	}
	public void setMpsw(String mpsw) {
		this.mpsw = mpsw;
	}
	@Column(name="MIden")
	public Integer getMiden() {
		return miden;
	}
	public void setMiden(Integer miden) {
		this.miden = miden;
	}
	@Column(name="Enabled")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="createTime",updatable=false)//设置此属性不包含在update中
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
	@JoinColumn(name="CEID",nullable=false)
	public CollegeTable getCollege() {
		return college;
	}
	public void setCollege(CollegeTable college) {
		this.college = college;
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
	@Override
	public String toString() {
		return "ManagerTable [mid=" + mid + ", mno=" + mno + ", mpsw=" + mpsw + ", miden=" + miden + ", enabled="
				+ enabled + ", createTime=" + createTime + ", updateTime=" + updateTime + ", college=" + college
				+ ", university=" + university + "]";
	}
	
}
