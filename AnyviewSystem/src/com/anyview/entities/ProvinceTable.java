package com.anyview.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProvinceTable",catalog="anyviewdb")
public class ProvinceTable implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer province_id;
	private String privince_name;
	
	public ProvinceTable() {
		super();
	}

	public ProvinceTable(Integer province_id) {
		super();
		this.province_id = province_id;
	}

	public ProvinceTable(Integer province_id, String privince_name) {
		super();
		this.province_id = province_id;
		this.privince_name = privince_name;
	}

	@Id
	@Column(name="PROVINCE_ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}
	
	@Column(name="PROVINCE_NAME",unique = true, nullable = false,length=255)
	public String getPrivince_name() {
		return privince_name;
	}
	
	public void setPrivince_name(String privince_name) {
		this.privince_name = privince_name;
	}
	
	

}
