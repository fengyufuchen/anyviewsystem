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
@Table(name="ExerciseTable",catalog="anyviewdb")
public class ExerciseTable implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer eid;//关键字
	private String econtent;//答案内容，xml
	private String ecomment;//教师批注，xml
	private Integer accumTime;//完成该题所用的所有时间(分钟)
	private Float score;//得分
	private Integer runResult;//运行结果
	private Integer runErrCount;//运行错误次数
	private Integer cmpCount;//编译次数
	private Integer cmpRightCount;//编译正确次数
	private Integer cmpErrorCount;//编译错误次数
	private Timestamp firstPastTime;//首次通过时间
	private Timestamp lastTime;//最后提交时间
	private Timestamp updateTime;//记录更新时间
	private Timestamp createTime;//创建时间
	
	private StudentTable student;
	private ProblemTable problem;
	private SchemeTable scheme;
	private ClassTable cla;
	
	@Id
	@Column(name="eid",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	
	@Column(name="econtent")
	public String getEcontent() {
		return econtent;
	}
	public void setEcontent(String econtent) {
		this.econtent = econtent;
	}
	
	@Column(name="ecomment")
	public String getEcomment() {
		return ecomment;
	}
	public void setEcomment(String ecomment) {
		this.ecomment = ecomment;
	}
	
	@Column(name="accumTime")
	public Integer getAccumTime() {
		return accumTime;
	}
	public void setAccumTime(Integer accumTime) {
		this.accumTime = accumTime;
	}
	
	@Column(name="score")
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	@Column(name="runResult")
	public Integer getRunResult() {
		return runResult;
	}
	public void setRunResult(Integer runResult) {
		this.runResult = runResult;
	}
	
	@Column(name="runErrCount")
	public Integer getRunErrCount() {
		return runErrCount;
	}
	public void setRunErrCount(Integer runErrCount) {
		this.runErrCount = runErrCount;
	}
	
	@Column(name="cmpCount")
	public Integer getCmpCount() {
		return cmpCount;
	}
	public void setCmpCount(Integer cmpCount) {
		this.cmpCount = cmpCount;
	}
	
	@Column(name="cmpRightCount")
	public Integer getCmpRightCount() {
		return cmpRightCount;
	}
	public void setCmpRightCount(Integer cmpRightCount) {
		this.cmpRightCount = cmpRightCount;
	}
	
	@Column(name="cmpErrorCount")
	public Integer getCmpErrorCount() {
		return cmpErrorCount;
	}
	public void setCmpErrorCount(Integer cmpErrorCount) {
		this.cmpErrorCount = cmpErrorCount;
	}
	
	@Column(name="firstPastTime")
	public Timestamp getFirstPastTime() {
		return firstPastTime;
	}
	public void setFirstPastTime(Timestamp firstPastTime) {
		this.firstPastTime = firstPastTime;
	}
	
	@Column(name="lastTime")
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	
	@Column(name="updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(targetEntity=StudentTable.class)
	@JoinColumn(name="sid")
	public StudentTable getStudent() {
		return student;
	}
	public void setStudent(StudentTable student) {
		this.student = student;
	}
	
	@ManyToOne(targetEntity=ProblemTable.class)
	@JoinColumn(name="pid")
	public ProblemTable getProblem() {
		return problem;
	}
	public void setProblem(ProblemTable problem) {
		this.problem = problem;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="vid")
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="cid")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
	
}
