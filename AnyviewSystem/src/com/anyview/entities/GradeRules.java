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

/**
 * 算分规则类
* @ClassName: GradeRules 
* @author 何凡 <piaobo749@qq.com>
* @date 2016年3月12日 下午4:15:47 
*
*/
@Entity
@Table(name="grade_rules",catalog="anyviewdb")
public class GradeRules implements java.io.Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer programWrongPer;//程序题未通过得分百分比
	private Integer programRightPer;//程序题通过默认得分百分比
	private Integer programFirstPer;//程序题最先完成得分百分比
	private Integer programLastPer;//程序题最后完成得分百分比
	private String programGradeDes;//程序题一般算分规则描述
	private Integer paperRate;//卷面分所占比率
	private Integer attitudeRate;//态度分所占比率
	private Timestamp updateTime;
	
	private TeacherTable teacher;
	
	@Id
	@Column(name="id",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="program_wrong_per")
	public Integer getProgramWrongPer() {
		return programWrongPer;
	}
	public void setProgramWrongPer(Integer programWrongPer) {
		this.programWrongPer = programWrongPer;
	}
	
	@Column(name="program_right_per")
	public Integer getProgramRightPer() {
		return programRightPer;
	}
	public void setProgramRightPer(Integer programRightPer) {
		this.programRightPer = programRightPer;
	}
	
	@Column(name="program_first_per")
	public Integer getProgramFirstPer() {
		return programFirstPer;
	}
	public void setProgramFirstPer(Integer programFirstPer) {
		this.programFirstPer = programFirstPer;
	}
	
	@Column(name="program_last_per")
	public Integer getProgramLastPer() {
		return programLastPer;
	}
	public void setProgramLastPer(Integer programLastPer) {
		this.programLastPer = programLastPer;
	}
	
	@Column(name="program_grade_des", length=500)
	public String getProgramGradeDes() {
		return programGradeDes;
	}
	public void setProgramGradeDes(String programGradeDes) {
		this.programGradeDes = programGradeDes;
	}
	
	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="tid",updatable=false)
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	@Column(name="paper_rate")
	public Integer getPaperRate() {
		return paperRate;
	}
	public void setPaperRate(Integer paperRate) {
		this.paperRate = paperRate;
	}
	
	@Column(name="attitude_rate")
	public Integer getAttitudeRate() {
		return attitudeRate;
	}
	public void setAttitudeRate(Integer attitudeRate) {
		this.attitudeRate = attitudeRate;
	}
	
	
}
