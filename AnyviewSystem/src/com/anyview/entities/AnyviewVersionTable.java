package com.anyview.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="anyview_version_table",catalog="anyviewdb")
public class AnyviewVersionTable {
	private static final long serialVersionUID = 1L;
	
	private Integer vid;           // 
	private String version;           // 
	private Timestamp vupdateTime;  // 
	
	@Id
	@Column(name="VID", nullable = false)
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	
	@Column(name="VERSION")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name="VUPDATETIME")
	public Timestamp getVupdateTime() {
		return vupdateTime;
	}
	public void setVupdateTime(Timestamp vupdateTime) {
		this.vupdateTime = vupdateTime;
	}
	
	
}
