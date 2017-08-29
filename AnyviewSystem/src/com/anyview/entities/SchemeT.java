package com.anyview.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SchemeTable",catalog="anyviewdb")
public class SchemeT {

	private Integer VID;
	private String VName;

	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param vID
	* @param vName
	*/
	
	@Id
	@Column(name="VID",length=11)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getVID() {
		return VID;
	}

	public void setVID(Integer vID) {
		VID = vID;
	}

	@Column(name="VName",length=255)
	public String getVName() {
		return VName;
	}

	public void setVName(String vName) {
		VName = vName;
	}
}
