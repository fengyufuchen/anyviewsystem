package com.anyview.entities;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="StudentTable",catalog="anyviewdb")
public class StudentTable implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sid;//学生id
	private String sname;//学生姓名
	private String sno;//学生编号
	private String spsw;//密码
	private String ssex;//性别
	private Integer enabled;//有效状态：0 停用 1正常
	private Integer loginStatus;//登录状态（0未登录，1已登录）
	private Timestamp logTime;//最后一次登录/退出时间
	private String logIp;//登录IP,可表示Ipv6
	private Integer logPort;//登录端口
	private Integer saccumTime;//累积做题时间
	private String siniInfo;//初始化信息
	private String sencryptKey;//加密用的密钥
	private String sverification;//WEB使用的验证数据
	private Timestamp createTime;//创建时间
	private Timestamp updateTime;//更新时间
	
	private UniversityTable university;//学校
	
	public StudentTable(){
		
	}
	
	
	public StudentTable(Integer sid, String sname, String sno, String spsw,
			String ssex, Integer enabled, Integer loginStatus,
			Timestamp logTime, String logIp, Integer logPort,
			Integer saccumTime, String siniInfo, String sencryptKey,
			String sverification, Timestamp createTime, Timestamp updateTime,
			UniversityTable university) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.sno = sno;
		this.spsw = spsw;
		this.ssex = ssex;
		this.enabled = enabled;
		this.loginStatus = loginStatus;
		this.logTime = logTime;
		this.logIp = logIp;
		this.logPort = logPort;
		this.saccumTime = saccumTime;
		this.siniInfo = siniInfo;
		this.sencryptKey = sencryptKey;
		this.sverification = sverification;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.university = university;
	}


	@Id
	@Column(name="SID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	@Column(name="SName",length=255)
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UnID",nullable=false)
//	@Cascade(CascadeType.ALL)
	public UniversityTable getUniversity() {
		return university;
	}
	public void setUniversity(UniversityTable university) {
		this.university = university;
	}
	
	@Column(name="Sno",length=255)
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	
	@Column(name="SPsw",length=255)
	public String getSpsw() {
		return spsw;
	}
	public void setSpsw(String spsw) {
		this.spsw = spsw;
	}
	
	@Column(name="Ssex")
	public String getSsex() {
		return ssex;
	}
	public void setSsex(String ssex) {
		this.ssex = ssex;
	}
	
	@Column(name="Enabled")
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="LoginStatus")
	public Integer getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	@Column(name="LogTime")
	public Timestamp getLogTime() {
		return logTime;
	}
	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}
	
	@Column(name="LogIP",length=255)
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	
	@Column(name="LogPort")
	public Integer getLogPort() {
		return logPort;
	}
	public void setLogPort(Integer logPort) {
		this.logPort = logPort;
	}
	
	@Column(name="SaccumTime")
	public Integer getSaccumTime() {
		return saccumTime;
	}
	public void setSaccumTime(Integer saccumTime) {
		this.saccumTime = saccumTime;
	}
	
	@Basic(fetch=FetchType.LAZY)//延迟加载
	@Column(name="SiniInfo")
	public String getSiniInfo() {
		return siniInfo;
	}
	public void setSiniInfo(String siniInfo) {
		this.siniInfo = siniInfo;
	}
	
	@Basic(fetch=FetchType.LAZY)//延迟加载
	@Column(name="SEncryptKey")
	public String getSencryptKey() {
		return sencryptKey;
	}
	public void setSencryptKey(String sencryptKey) {
		this.sencryptKey = sencryptKey;
	}
	
	@Basic(fetch=FetchType.LAZY)//延迟加载
	@Column(name="SVerification",length=255)
	public String getSverification() {
		return sverification;
	}
	public void setSverification(String sverification) {
		this.sverification = sverification;
	}
	
	@Column(name="CreateTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="UpdateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	
}
