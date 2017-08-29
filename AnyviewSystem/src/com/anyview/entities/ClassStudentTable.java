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
@Table(name="Class_StudentTable",catalog="anyviewdb")

public class ClassStudentTable implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;                 //关键字
	private Integer status;				//状态，0代表无效，1代表有效
	private Timestamp updateTime;		//更新时间
	private Integer sattr;//属性：0休学学生,1普通学生,2班长,3教师专用,4教师专属  .只属于某个教师，不允许修改密码
	
	private ClassTable cla;  //班级
	private StudentTable student;//学生
	
	/**
	 * 无效
	 */
	public static final int INVALID = 0;
	/**
	 * 有效
	 */
	public static final int VALID = 1;
	
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
	
	@Column(name="SAttr")
	public Integer getSattr() {
		return sattr;
	}
	public void setSattr(Integer sattr) {
		this.sattr = sattr;
	}
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
	
	@ManyToOne(targetEntity=StudentTable.class)
	@JoinColumn(name="SID")
	public StudentTable getStudent() {
		return student;
	}
	public void setStudent(StudentTable student) {
		this.student = student;
	}
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
