/**   
* @Title: StudentSchemeDetail.java 
* @Package com.anyview.entities 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年12月3日 下午6:55:14 
* @version V1.0   
*/
package com.anyview.entities;

/** 
 * @ClassName: StudentSchemeDetail 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年12月3日 下午6:55:14 
 *  
 */
@Deprecated
public class StudentSchemeDetailVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private StudentTable student;
//	private SchemeTable scheme;
	
	private Integer finishedNum;//完成题数
	private Integer totalNum;//总题数
	private Long totalTime;//总时间
//	private Integer rank;//排名
//	private Long totalScore;//总得分
	
	public StudentSchemeDetailVO(){
		
	}
	
	public StudentSchemeDetailVO(StudentTable st, Integer finishedNum, Integer totalNum, Long totalTime){
		this.student = st;
		this.finishedNum = finishedNum;
		this.totalNum = totalNum;
		this.totalTime = totalTime;
	}
	
	public StudentTable getStudent() {
		return student;
	}
	public void setStudent(StudentTable student) {
		this.student = student;
	}
//	public SchemeTable getScheme() {
//		return scheme;
//	}
//	public void setScheme(SchemeTable scheme) {
//		this.scheme = scheme;
//	}
	public Integer getFinishedNum() {
		return finishedNum;
	}
	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
	public Long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
//	public Integer getRank() {
//		return rank;
//	}
//	public void setRank(Integer rank) {
//		this.rank = rank;
//	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

//	public Long getTotalScore() {
//		return totalScore;
//	}
//
//	public void setTotalScore(Long totalScore) {
//		this.totalScore = totalScore;
//	}
	
	
}
