package com.anyview.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件名：SemesterTable.java
 * 描   述：学期管理数据库访问类
 * 时   间 ：2015年08月04日
 * @author DenyunFang
 * @version 1.0
 */

@Entity
@Table(name="SemesterTable",catalog="anyviewdb")
public class SemesterTable implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer sid;			//学期ID,关键字，递增
	private String sname;			//学期名字
	private Timestamp startTime;			//学期开始时间
	private Timestamp endTime;			//学期结束时间
	private Timestamp updateTime;		//学期更新时间
	
	
	@Id
	@Column(name="SID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	@Column(name="SNAME")
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Column(name="STARTTIME")
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="ENDTIME")
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
