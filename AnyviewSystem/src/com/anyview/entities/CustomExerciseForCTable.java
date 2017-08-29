package com.anyview.entities;

import java.sql.Timestamp;

public class CustomExerciseForCTable implements java.io.Serializable{

	private Integer eid;
	private Integer sid;
	private String exname;
	private String excontent;
	private String extype;
	private Integer excmpcount;
	private Timestamp createtime;
	private Timestamp modifytime;
	private String exmemo;
	private Integer exstatus;
	private double exscore;
	private String excomment;
	
	
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getExname() {
		return exname;
	}
	public void setExname(String exname) {
		this.exname = exname;
	}
	public String getExcontent() {
		return excontent;
	}
	public void setExcontent(String excontent) {
		this.excontent = excontent;
	}
	public String getExtype() {
		return extype;
	}
	public void setExtype(String extype) {
		this.extype = extype;
	}
	public Integer getExcmpcount() {
		return excmpcount;
	}
	public void setExcmpcount(Integer excmpcount) {
		this.excmpcount = excmpcount;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getModifytime() {
		return modifytime;
	}
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	public String getExmemo() {
		return exmemo;
	}
	public void setExmemo(String exmemo) {
		this.exmemo = exmemo;
	}
	public Integer getExstatus() {
		return exstatus;
	}
	public void setExstatus(Integer exstatus) {
		this.exstatus = exstatus;
	}
	public double getExscore() {
		return exscore;
	}
	public void setExscore(double exscore) {
		this.exscore = exscore;
	}
	public String getExcomment() {
		return excomment;
	}
	public void setExcomment(String excomment) {
		this.excomment = excomment;
	}
	
	
}
