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

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=20)//hibernate的session大小
@Table(name="SchemeContentTable",catalog="anyviewdb")
public class SchemeContentTable implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String vpName;//新的题目名,VID+目录+题目名唯一
	private String vchapName;//虚拟目录名  ；现用于题号的作用，因此只能存整数类型add by hefan 2016.04.18
	private Float score;//分值
	private Timestamp startTime;//允许开始时间
	private Timestamp finishTime;//要求完成时间
	private Timestamp updateTime;
	private Integer status;//状态 0停用 1启用
	
	private SchemeTable scheme;//作业表
	private ProblemTable problem;//题目 级联
	/*
	 * 停用状态
	 */
	public static int STATUS_DISABLE = 0;
	/*
	 * 启用状态
	 */
	public static int STATUS_ENABLE = 1;
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="VPName",length=255)
	public String getVpName() {
		return vpName;
	}

	public void setVpName(String vpName) {
		this.vpName = vpName;
	}

	@Column(name="VChapName",length=255)
	public String getVchapName() {
		return vchapName;
	}

	public void setVchapName(String vchapName) {
		this.vchapName = vchapName;
	}

	@Column(name="Score")
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name="StartTime")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name="FinishTime")
	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	@Column(name="UpdateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID")
	public SchemeTable getScheme() {
		return scheme;
	}

	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}

	@ManyToOne(targetEntity=ProblemTable.class)
	@JoinColumn(name="PID")
	public ProblemTable getProblem() {
		return problem;
	}

	public void setProblem(ProblemTable problem) {
		this.problem = problem;
	}
	@Column(name="status")
	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
