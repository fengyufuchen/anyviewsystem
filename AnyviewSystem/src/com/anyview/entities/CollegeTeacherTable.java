/**   
* @Title: CollegeTeacherTable.java 
* @Package com.anyview.entities 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月20日 下午12:57:11 
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
@Table(name="College_TeacherTable",catalog="anyviewdb")
public class CollegeTeacherTable implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;//关键字
	private Integer status;//状态：0无效1有效
	private Timestamp updateTime;
	
	@Deprecated//弃用
	private Integer tiden;//身份：0普通教师：普通权限1学院领导：可以管理和查看全学院教师和班级2学校领导：可以管理和查看全校教师和班级
	
	private CollegeTable college;//学院
	private TeacherTable teacher;//教师 
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name="TIDEN")
	public Integer getTiden() {
		return tiden;
	}
	public void setTiden(Integer tiden) {
		this.tiden = tiden;
	}
	@ManyToOne(targetEntity=CollegeTable.class)
	@JoinColumn(name="CeID")
	public CollegeTable getCollege() {
		return college;
	}
	public void setCollege(CollegeTable college) {
		this.college = college;
	}
	
	@ManyToOne(targetEntity=TeacherTable.class)
	@JoinColumn(name="TID")
	public TeacherTable getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherTable teacher) {
		this.teacher = teacher;
	}
	
	
}
