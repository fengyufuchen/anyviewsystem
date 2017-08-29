package com.anyview.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ChinaUniversityTable",catalog="anyviewdb")
public class ChinaUniversityTable implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer school_id;//学校ID,当修改时,通过触发器同步其他冗余
	private String school_name;//学校名称，无重复
	private Integer school_pro_id;
	private Integer school_schooltype_id;
	
	public ChinaUniversityTable() {
		super();
	}
	
	public ChinaUniversityTable(int school_id) {
		super();
		this.school_id = school_id;
	}

	

	public ChinaUniversityTable(Integer school_id, String school_name, Integer school_pro_id,
			Integer school_schooltype_id) {
		super();
		this.school_id = school_id;
		this.school_name = school_name;
		this.school_pro_id = school_pro_id;
		this.school_schooltype_id = school_schooltype_id;
	}
	
	@Id
	@Column(name="SCHOOL_ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}

	@Column(name="SCHOOL_NAME",unique = true, nullable = false,length=255)
	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	@Column(name="SCHOOL_PRO_ID")
	public Integer getSchool_pro_id() {
		return school_pro_id;
	}

	public void setSchool_pro_id(Integer school_pro_id) {
		this.school_pro_id = school_pro_id;
	}

	@Column(name="SCHOOL_SCHOOLTYPE_ID")
	public Integer getSchool_schooltype_id() {
		return school_schooltype_id;
	}

	public void setSchool_schooltype_id(Integer school_schooltype_id) {
		this.school_schooltype_id = school_schooltype_id;
	}
	
	
	
}
