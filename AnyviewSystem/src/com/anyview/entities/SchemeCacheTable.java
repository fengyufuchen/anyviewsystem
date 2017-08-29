package com.anyview.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SchemeCacheTable",catalog="anyviewdb")
public class SchemeCacheTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vid;//作业表ID,作为缓存ID，关键字
	private String cache;//缓存内容，非XML格式
	private String cacheXML;//xml格式缓存内容
	private Integer syncFlag;//是否已同步更新：0未同步 1同步
	private Timestamp updateTime;
	@Id
	@Column(name="VID",unique = true, nullable = false)
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	@Column(name="Cache")
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	
	@Column(name="CacheXML")
	public String getCacheXML() {
		return cacheXML;
	}
	public void setCacheXML(String cacheXML) {
		this.cacheXML = cacheXML;
	}
	@Column(name="SyncFlag")
	public Integer getSyncFlag() {
		return syncFlag;
	}
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
	@Column(name="UpdateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
