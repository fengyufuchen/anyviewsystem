/**   
* @Title: ScoreTable.java 
* @Package com.anyview.entities 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年12月23日 下午10:36:55 
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

/** 
 * @ClassName: ScoreTable 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年12月23日 下午10:36:55 
 *  
 */
@Entity
@Table(name="ScoreTable",catalog="anyviewdb")
public class ScoreTable implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;//主键
	private Integer rank;//排名
	private Float score;//总得分
	private Integer totalTime;//总用时
	private Integer passNum;//通过题数
	private Integer correctFlag;//批改标志   0：未批改 1：已批改
	private Timestamp updateTime;

	private Float paperScore;//卷面分
	private Float attitudeScore;//态度分
	
	private StudentTable student;
	private SchemeTable scheme;
	private ClassTable cla;
	
	
	@Id
	@Column(name="ID",unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="rank")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@Column(name="score")
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	@Column(name="totalTime")
	public Integer getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
	
	@Column(name="passNum")
	public Integer getPassNum() {
		return passNum;
	}
	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}
	
	@Column(name="correctFlag")
	public Integer getCorrectFlag() {
		return correctFlag;
	}
	public void setCorrectFlag(Integer correctFlag) {
		this.correctFlag = correctFlag;
	}
	
	@Column(name="updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@ManyToOne(targetEntity=StudentTable.class)
	@JoinColumn(name="SID")
	public StudentTable getStudent() {
		return student;
	}
	public void setStudent(StudentTable student) {
		this.student = student;
	}
	
	@ManyToOne(targetEntity=SchemeTable.class)
	@JoinColumn(name="VID")
	public SchemeTable getScheme() {
		return scheme;
	}
	public void setScheme(SchemeTable scheme) {
		this.scheme = scheme;
	}
	
	@ManyToOne(targetEntity=ClassTable.class)
	@JoinColumn(name="CID")
	public ClassTable getCla() {
		return cla;
	}
	public void setCla(ClassTable cla) {
		this.cla = cla;
	}
	
	@Column(name="paperScore")
	public Float getPaperScore() {
		return paperScore;
	}
	public void setPaperScore(Float paperScore) {
		this.paperScore = paperScore;
	}
	
	@Column(name="attitudeScore")
	public Float getAttitudeScore() {
		return attitudeScore;
	}
	public void setAttitudeScore(Float attitudeScore) {
		this.attitudeScore = attitudeScore;
	}
	
	

}
