/**   
* @Title: AWStuInfo.java
* @Package com.stusys.entities.anyview.vo
* @Description: TODO
* @author xhn 
* @date 2012-11-19 下午09:22:57
* @version V1.0   
*/
package com.anyview.entities;

import java.sql.Date;

/**
 * @ClassName: AWStuInfo
 * @Description: 批改作业学生信息对象
 * @author xhn
 * @date 2012-11-19 下午09:22:57
 * 
 */
public class AWStuInfo {

	private int stuId;//学生ID
	private Integer exId;//学生做题ID
	private String stuNo;//学生学号
	private String stuName;//学生姓名
	private Integer finish;//完成题数
	private Integer total;//总题数
	
	private Integer runPass;//运行通过次数累加，">0"作为通过标识
	private Integer runError;//运行错误次数
	private Float score;//题目得分
	private Integer cmpPass;//编译正确次数
	private Integer cmpError;//编译错误次数
	private Date firstPass;//第一次通过的时间
	private Date lastSave;//最后保存时间
	private Date startTime;//做题开始时间
	private Date deadline;//做题结束时间
	private String memo;//题目说明
	private String ansContent;//学生答案内容（XML格式）
	private String commContent;//教师批注（XML格式）
	
	/**
	 * 学生答案，只有get方法，从ansContent中抽取
	 * @return the stuAnswer
	 */
	public String getStuAnswer() {
		return ansContent;//暂取xml内容
	}
	/**
	 * 教师批注，只有get方法，从commContent中抽取
	 * @return the comment
	 */
	public String getComment() {
		return commContent;//暂取xml内容
	}
	/**
	 * 完成题数/总题数,只有get方法
	* @return String
	 */
	public String getScale() {
		int f = 0,t = 0;
		if(finish != null){
			f = finish;
		}
		if(total != null){
			t = total;
		}
		return f+"/"+t;
	}
	/**
	 * @return the runPass
	 */
	public Integer getRunPass() {
		return runPass;
	}
	/**
	 * @param runPass the runPass to set
	 */
	public void setRunPass(Integer runPass) {
		this.runPass = runPass;
	}
	/**
	 * @return the score
	 */
	public Float getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Float score) {
		this.score = score;
	}
	/**
	 * @return the cmpPass
	 */
	public Integer getCmpPass() {
		return cmpPass;
	}
	/**
	 * @param cmpPass the cmpPass to set
	 */
	public void setCmpPass(Integer cmpPass) {
		this.cmpPass = cmpPass;
	}
	/**
	 * @return the cmpError
	 */
	public Integer getCmpError() {
		return cmpError;
	}
	/**
	 * @param cmpError the cmpError to set
	 */
	public void setCmpError(Integer cmpError) {
		this.cmpError = cmpError;
	}
	/**
	 * @return the firstPass
	 */
	public Date getFirstPass() {
		return firstPass;
	}
	/**
	 * @param firstPass the firstPass to set
	 */
	public void setFirstPass(Date firstPass) {
		this.firstPass = firstPass;
	}
	/**
	 * @return the lastSave
	 */
	public Date getLastSave() {
		return lastSave;
	}
	/**
	 * @param lastSave the lastSave to set
	 */
	public void setLastSave(Date lastSave) {
		this.lastSave = lastSave;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the stuId
	 */
	public int getStuId() {
		return stuId;
	}
	/**
	 * @param stuId the stuId to set
	 */
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	/**
	 * @return the stuNo
	 */
	public String getStuNo() {
		return stuNo;
	}
	/**
	 * @param stuNo the stuNo to set
	 */
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	/**
	 * @return the stuName
	 */
	public String getStuName() {
		return stuName;
	}
	/**
	 * @param stuName the stuName to set
	 */
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	/**
	 * @return the finish
	 */
	public Integer getFinish() {
		if(finish == null){
			return 0;
		}
		return finish;
	}
	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		if(total == null){
			return 0;
		}
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}
	public String getAnsContent() {
		return ansContent;
	}
	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}
	public String getCommContent() {
		return commContent;
	}
	public void setRunError(Integer runError) {
		this.runError = runError;
	}
	public Integer getRunError() {
		return runError;
	}
	public void setExId(Integer exId) {
		this.exId = exId;
	}
	public Integer getExId() {
		return exId;
	}
	
}
