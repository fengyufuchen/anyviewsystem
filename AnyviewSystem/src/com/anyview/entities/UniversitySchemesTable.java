/**   
* @Title: UniversitySchemesTable.java 
* @Package com.anyview.entities 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年9月20日 下午1:22:53 
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
@Table(name="University_SchemesTable",catalog="anyviewdb")
public class UniversitySchemesTable implements java.io.Serializable{
	
	/** 
	* @Fields serialVersionUID : TODO() 
	*/ 
	private static final long serialVersionUID = 1L;
	private Integer id;//主键
	private Timestamp validTime;//有效时间
	private Timestamp updateTime;
	
	private UniversityTable university;//学校
	private SchemeTable scheme;//作业表
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="validTime")
	public Timestamp getValidTime() {
		return validTime;
	}
	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}
	
	@Column(name="updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=UniversityTable.class)
	@JoinColumn(name="UnID")
	public UniversityTable getUniversity() {
		return university;
	}
	public void setUniversity(UniversityTable university) {
		this.university = university;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID")
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	
	

}
