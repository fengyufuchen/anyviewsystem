package com.anyview.entities;

import java.sql.Timestamp;

public class CCTable implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tId;//
	private int courseID;//
	private int cId;//
	private int cTCRight;//
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getcTCRight() {
		return cTCRight;
	}
	public void setcTCRight(int cTCRight) {
		this.cTCRight = cTCRight;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
		
}
