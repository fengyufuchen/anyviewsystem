package com.anyview.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CollegeTable",catalog="anyviewdb")
public class CollegeTable implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer ceID;//学院ID,关键字，递增
	private String ceName;//学院名称，可以有重复，UnID+CeName组成唯一索引
	private Integer enabled;//有效状态：0停用 1正常
	private Timestamp createTime;//创建时间
	private Timestamp updateTime;//记录更新时间（默认值GetDate（））
	
	private UniversityTable university;//所在学校
	
	public CollegeTable(){
		
	}
	public CollegeTable(Integer ceID){
		this.ceID = ceID;
	}
	
	@Id
	@Column(name="CEID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCeID() {
		return ceID;
	}

	public void setCeID(Integer ceID) {
		this.ceID = ceID;
	}

	@Column(name="CENAME",length=255)
	public String getCeName() {
		return ceName;
	}

	public void setCeName(String ceName) {
		this.ceName = ceName;
	}
	
	@Column(name="CreateTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="ENABLED")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
  
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UNID")
//	@Cascade(CascadeType.ALL)
	public UniversityTable getUniversity() {
		return university;
	}

	public void setUniversity(UniversityTable university) {
		this.university = university;
	}

}
