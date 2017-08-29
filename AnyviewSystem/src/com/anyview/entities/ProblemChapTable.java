package com.anyview.entities;

import java.sql.Timestamp;
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

import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name="ProblemChapTable",catalog="anyviewdb")
@NamedNativeQuery(name="queryChildrenChapByParentChId",query="{call queryChildrenChap()}",resultClass=ProblemChapTable.class)
public class ProblemChapTable implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer chId;//题目目录ID,关键字，递增
	private String chName;//题目目录名称,可以有重复,但（LID+ParentID+ChName）唯一
	private String memo;//说明
	private Integer visit;//访问级别：0私有 1公开
	private Timestamp updateTime;
	private Timestamp createTime;
	
	private ProblemLibTable problemLib;//所在题库 NoAction
	private ProblemChapTable parentChap;//父目录，最上层ID为-1
	
	public ProblemChapTable(){
		
	}
	
	public ProblemChapTable(Integer chId){
		this.chId = chId;
	}
	
	public ProblemChapTable(Integer chId, ProblemLibTable problemLib){
		this.chId = chId;
		this.problemLib = problemLib;
	}
	
	@Id
	@Column(name="ChID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getChId() {
		return chId;
	}
	public void setChId(Integer chId) {
		this.chId = chId;
	}
	
	@Column(name="ChName",length=255)
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	@Column(name="Memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="Visit")
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	@Column(name="UPDATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@ManyToOne(targetEntity=ProblemLibTable.class)
	@JoinColumn(name="LID")
	public ProblemLibTable getProblemLib() {
		return problemLib;
	}
	public void setProblemLib(ProblemLibTable problemLib) {
		this.problemLib = problemLib;
	}
	@ManyToOne(targetEntity=ProblemChapTable.class)
	@JoinColumn(name="ParentID")
	public ProblemChapTable getParentChap() {
		return parentChap;
	}
	public void setParentChap(ProblemChapTable parentChap) {
		this.parentChap = parentChap;
	}
	
	
}
