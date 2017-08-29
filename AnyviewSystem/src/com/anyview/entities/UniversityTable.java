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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="UniversityTable",catalog="anyviewdb")
public class UniversityTable implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer unID;//学校ID,当修改时,通过触发器同步其他冗余
	private String unName;//学校名称，无重复
	private String ip;//独立服务器IP地址
	private Integer port;//独立服务器端口
	private Integer attr;//属性(0 本服务器 1使用独立服务器)
	private Integer enabled;//有效状态：0停用 1正常
	private String verification;//验证码,其他学校将IP地址和端口登记到此处时使用
	private Timestamp CreateTime;//创建时间
	private Timestamp updateTime;
	
//	private Set<CollegeTable> colleges = new HashSet<CollegeTable>();//学校里的学院
	
	
	public UniversityTable() {
		super();
	}
	
	public UniversityTable(int unID) {
		super();
		this.unID = unID;
	}
	
	
	public UniversityTable(Integer unID, String unName, String ip, Integer port,
		Integer attr, Integer enabled, String verification,
		Timestamp createTime, Timestamp updateTime) {
		super();
		this.unID = unID;
		this.unName = unName;
		this.ip = ip;
		this.port = port;
		this.attr = attr;
		this.enabled = enabled;
		this.verification = verification;
		CreateTime = createTime;
		this.updateTime = updateTime;
	}

	@Id
	@Column(name="UNID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getUnID() {
		return unID;
	}
	
	
	public void setUnID(Integer unID) {
		this.unID = unID;
	}
	
	@Column(name="UNNAME",unique = true, nullable = false,length=255)
	public String getUnName() {
		return unName;
	}
	public void setUnName(String unName) {
		this.unName = unName;
	}
	
	@Column(name="IP",length=25)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name="PORT")
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	@Column(name="ATTR")
	public Integer getAttr() {
		return attr;
	}
	public void setAttr(Integer attr) {
		this.attr = attr;
	}
	
	@Column(name="ENABLED")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="VERIFICATION")
	public String getVerification() {
		return verification;
	}
	public void setVerification(String verification) {
		this.verification = verification;
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
	
//	@OneToMany(targetEntity=CollegeTable.class, mappedBy="university")
//	public Set<CollegeTable> getColleges() {
//		return colleges;
//	}
//	public void setColleges(Set<CollegeTable> colleges) {
//		this.colleges = colleges;
//	}
}
