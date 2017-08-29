/**   
* @Title: ExamPlanTable.java 
* @Package com.anyview.entities 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月20日 下午1:29:08 
* @version V1.0   
*/
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
@Table(name="ExamPlanTable",catalog="anyviewdb")
public class ExamPlanTable implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer epId; //主键
	private String epName;//考试名称
	private Integer duration;//考试持续时间（分钟）
	private Timestamp startTime;//考试开始时间，类型为自动，此为开始时间；类型为手动，此为手动开始后填写的时间。
	private Integer status;//状态：0未使用 1中止 2完成 考试 4考试准备中
	private Integer kind;//类型：0手动 1自动
	private Timestamp createTime;//创建时间
	private Timestamp updateTime;
	/**
	 * 考试自动开始标识
	 */
	private Integer automaticStartFlag;
	/**
	 * 考试自动结束标识
	 */
	private Integer automaticEndFlag;
	
	private TeacherTable teacher;//创建教师，只有创建者可以看到该编排记录。当多个考试编排ID发生冲突时，以权限高的为标准，外键（级联）
	private ClassTable cla;//班级
	private CourseTable course;//课程
	private SchemeTable scheme;//考试计划表
	
	
	/**
	 * 未使用(status)
	 */
	public static final int NO_USED = 0;
	/**
	 * 中止(status)
	 */
	public static final int INTERRUPT = 1;
	/**
	 * 完成(status)
	 */
	public static final int FINISH = 2;
	/**
	 * 正在考试(status)
	 */
	public static final int EXAMMING = 3;
	/**
	 * 考试准备(status)
	 */
	public static final int PREPARE_EXAM = 4;
	/**
	 * 手动(kind)
	 */
	public static final int MANUAL = 0;
	/**
	 * 自动(kind)
	 */
	public static final int AUTOMATIC = 1;
	/**
	 * 不允许自动开始任务
	 */
	public static final int NOT_ALLOW_AUTOMATIC_START = 0;
	/**
	 * 允许自动开始任务
	 */
	public static final int ALLOW_AUTOMATIC_START = 1;
	/**
	 * 不允许自动结束任务
	 */
	public static final int NOT_ALLOW_AUTOMATIC_END = 0;
	/***
	 * 允许自动结束任务
	 */
	public static final int ALLOW_AUTOMATIC_END = 1;
	
	@Id
	@Column(name="EPID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getEpId() {
		return epId;
	}
	public void setEpId(Integer epId) {
		this.epId = epId;
	}
	
	@Column(name="EPName")
	public String getEpName() {
		return epName;
	}
	public void setEpName(String epName) {
		this.epName = epName;
	}
	
	@Column(name="Duration")
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Column(name="startTime")
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="status",updatable=false)//不更新
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="kind")
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	
	@Column(name="createTime",updatable=false)//不更新
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
	
	@ManyToOne(targetEntity=CourseTable.class)
	@JoinColumn(name="CourseID")
	public CourseTable getCourse() {
		return course;
	}
	public void setCourse(CourseTable course) {
		this.course = course;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID")
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	
	@Column(name="automaticStartFlag")
	public Integer getAutomaticStartFlag() {
		return automaticStartFlag;
	}
	public void setAutomaticStartFlag(Integer automaticStartFlag) {
		this.automaticStartFlag = automaticStartFlag;
	}
	
	@Column(name="automaticEndFlag")
	public Integer getAutomaticEndFlag() {
		return automaticEndFlag;
	}
	public void setAutomaticEndFlag(Integer automaticEndFlag) {
		this.automaticEndFlag = automaticEndFlag;
	}
	
	
}
