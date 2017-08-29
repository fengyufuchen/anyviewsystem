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

@Entity
@Table(name="ProblemTable",catalog="anyviewdb")
public class ProblemTable implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pid;//题目ID,关键字
	private String pname;//题目名称,可以有重复,但（ChID+PName）唯一
	private Float degree;//难度
	private Integer kind;//题目类型: 0：程序题  1：例题 2：填空题 3：单项选择题 4：多项选择题 5：判断题
	private Integer status;//状态：（0停用 1测试 2正式）注：在题库和题目目录公开的情况下，只有状态为正式的题目才能被其他老师访问
	private Integer visit;//访问级别：0 私有 1公开
	private String pmemo;//备注，题目简介
	private String ptip;//提示 （采用XML格式保存数据）
	/*
	 * 题目内容(采用XML格式保存数据)
		数据结构题目包括：
		1.	主文件名和主文件内容
		2.	答案文件名和答案文件内容
		3.	用户文件名和用户文件头
		4.	零个或多个（头文件名+头文件内容）
		5.	题目内容和文档
		6.	题目初始化设置
	 */
	private String pcontent;
	private Integer cacheSync;//缓存同步状态：0未同步 1已同步
	private String cache;//题目缓存，非XML格式，用于加快应用程序速度，由应用程序生成。
	private Timestamp updateTime;
	private Timestamp createTime;
	
	private ProblemChapTable problemChap;//所在目录NoAction
	
	public ProblemTable(){
		
	}
	
	public ProblemTable(int pid){
		this.pid = pid;
	}
	
	@Id
	@Column(name="PID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	@Column(name="PName",length=255)
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	@Column(name="Degree")
	public Float getDegree() {
		return degree;
	}
	public void setDegree(Float degree) {
		this.degree = degree;
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
	@Column(name="PMemo")
	public String getPmemo() {
		return pmemo;
	}
	public void setPmemo(String pmemo) {
		this.pmemo = pmemo;
	}
	@Column(name="PTip")
	public String getPtip() {
		return ptip;
	}
	public void setPtip(String ptip) {
		this.ptip = ptip;
	}
	@Column(name="PContent")
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	@Column(name="CacheSync")
	public Integer getCacheSync() {
		return cacheSync;
	}
	public void setCacheSync(Integer cacheSync) {
		this.cacheSync = cacheSync;
	}
	@Column(name="Cache")
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="createTime", updatable=false)//此属性不包含在update生成的sql中
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@ManyToOne(targetEntity=ProblemChapTable.class)
	@JoinColumn(name="ChID",updatable=false)
	public ProblemChapTable getProblemChap() {
		return problemChap;
	}
	public void setProblemChap(ProblemChapTable problemChap) {
		this.problemChap = problemChap;
	}
	
}
