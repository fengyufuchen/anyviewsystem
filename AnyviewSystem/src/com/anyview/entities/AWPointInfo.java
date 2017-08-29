/**   
* @Title: AWPointInfo.java
* @Package com.stusys.entities.anyview.vo
* @Description: TODO
* @author xhn 
* @date 2012-11-19 下午05:06:33
* @version V1.0   
*/
package com.anyview.entities;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.sql.Date;

/**
 * @ClassName: AWPointInfo
 * @Description: 习题信息对象
 * @author xhn
 * @date 2012-11-19 下午05:06:33
 * 
 */
public class AWPointInfo {

	private Integer exId;
	private int schemeId;//作业表ID
	private int problemId;//题目ID
	private String chapName;//章节名
	private String proName;//问题名
	private Float score;//题目分值
	private Float escore;//题目得分
	private Integer finish;//完成题数
	private Integer total;//总题数
	private String pInfo;//xml数据，包含题目内容信息
	
	private Integer runPass;//运行通过次数
	private Integer cmpPass;//编译通过次数
	private Integer cmpError;//编译错误次数
	private Integer runError;//运行错误次数
	private Date firstPass;//第一次通过的时间
	private Date lastSave;//最后保存的时间
	private Date startTime;//题目开始时间
	private Date deadline;//题目结束时间
	private String memo;//题目说明
	private String ansContent;//学生答案内容（XML格式）
	private String commContent;//教师批注（XML格式）
	
	private String topic;//为题的题目文档
	private String type;//题目类型
	private Integer cmpCount;//编译次数
	private Date createTime;//题目创建时间
	private Integer exStatus;
	private Date modifyTime;//题目修改时间
	
	private String answer;//标准答案
	/**
	 * @return the excore
	 */
	public Float getEscore() {
		return escore;
	}
	/**
	 * @param escore the escore to set
	 */
	public void setEscore(Float escore) {
		this.escore = escore;
	}
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * @return the exStatus
	 */
	public Integer getExStatus() {
		return exStatus;
	}
	/**
	 * @param exStatus the exStatus to set
	 */
	public void setExStatus(Integer exStatus) {
		this.exStatus = exStatus;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the cmpCount
	 */
	public Integer getCmpCount() {
		return cmpCount;
	}
	/**
	 * @param cmpCount the cmpCount to set
	 */
	public void setCmpCount(Integer cmpCount) {
		this.cmpCount = cmpCount;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
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
	/**前台解析XML，在pInfo内已有*/
	/**
	 * 题目
	 * @return the topic
	 
	public String getTopic() {
		return pInfo;//需解析xml数据,暂获取pInfo
	}
	*/
	/**
	 * 答案
	 * @return the answer
	 
	public String getAnswer() {
		return pInfo;//需解析xml数据,暂获取pInfo
	}
	*/
	
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
	 * @return the runError
	 */
	public Integer getRunError() {
		return runError;
	}
	/**
	 * @param runError the runError to set
	 */
	public void setRunError(Integer runError) {
		this.runError = runError;
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
	 * @return the ansContent
	 */
	public String getAnsContent() {
		return ansContent;
	}
	/**
	 * @param ansContent the ansContent to set
	 */
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}
	/**
	 * @return the commContent
	 */
	public String getCommContent() {
		return commContent;
	}
	/**
	 * @param commContent the commContent to set
	 */
	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}
	/**
	 * @return the schemeId
	 */
	public int getSchemeId() {
		return schemeId;
	}
	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	/**
	 * @return the problemId
	 */
	public int getProblemId() {
		return problemId;
	}
	/**
	 * @param problemId the problemId to set
	 */
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	/**
	 * @return the chapName
	 */
	public String getChapName() {
		return chapName;
	}
	/**
	 * @param chapName the chapName to set
	 */
	public void setChapName(String chapName) {
		this.chapName = chapName;
	}
	/**
	 * @return the proName
	 */
	public String getProName() {
		return proName;
	}
	/**
	 * @param proName the proName to set
	 */
	public void setProName(String proName) {
		this.proName = proName;
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
	 * @return the finish
	 */
	public Integer getFinish() {
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
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
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
	public void setpInfo(String pInfo) {
		this.pInfo = pInfo;
	}
	public String getpInfo() {
		return pInfo;
	}
	public void setExId(Integer exId) {
		this.exId = exId;
	}
	public Integer getExId() {
		return exId;
	}
	/*
	public String getAnswer() {
		Element element = null;
		DocumentBuilder db = null;
		  DocumentBuilderFactory dbf = null;
		  try {
		   // 返回documentBuilderFactory对象
		   dbf = DocumentBuilderFactory.newInstance();
		   // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
		   db = dbf.newDocumentBuilder();

		   // 得到一个DOM并返回给document对象
		   Document dt = db.parse(pInfo);
		   // 得到一个elment根元素
		   element = dt.getDocumentElement();
		   // 获得根节点
		   System.out.println("根元素：" + element.getNodeName());

		   // 获得根元素下的子节点
		   NodeList childNodes = element.getChildNodes();

		   // 遍历这些子节点
		   for (int i = 0; i < childNodes.getLength(); i++) {
		    // 获得每个对应位置i的结点
		    Node node1 = childNodes.item(i);
		    if ("answer-file".equals(node1.getNodeName())) {
		     // 如果节点的名称为"Account"，则输出Account元素属性type
		     System.out.println("\r\n找到一篇账号. 所属区域: " + node1.getAttributes().getNamedItem("type").getNodeValue() + ". ");
		     // 获得<Accounts>下的节点
		     NodeList nodeDetail = node1.getChildNodes();
		     // 遍历<Accounts>下的节点
		     for (int j = 0; j < nodeDetail.getLength(); j++) {
		      // 获得<Accounts>元素每一个节点
		      Node detail = nodeDetail.item(j);
		      if ("code".equals(detail.getNodeName())) // 输出code
		       System.out.println("卡号: " + detail.getTextContent());
		      else if ("pass".equals(detail.getNodeName())) // 输出pass
		       System.out.println("密码: " + detail.getTextContent());
		      else if ("name".equals(detail.getNodeName())) // 输出name
		       System.out.println("姓名: " + detail.getTextContent());
		      else if ("money".equals(detail.getNodeName())) // 输出money
		       System.out.println("余额: " + detail.getTextContent());
		     }
		    }

		return answer;
	}*/
}
