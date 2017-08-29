/**   
* @Title: ChoiceOptions.java 
* @Package com.anyview.entities 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年10月29日 下午10:21:50 
* @version V1.0   
*/
package com.anyview.entities;

/** 
 * @ClassName: ChoiceOptions 
 * @Description: TODO(封装选择题选项信息) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年10月29日 下午10:21:50 
 *  
 */
public class ChoiceOptionsVO {

	private String sequence;//序号
	private String optContent;//选项内容
    private Boolean isRight;//是否是正确选项
    
    public ChoiceOptionsVO() {
		super();
	}
    
    public ChoiceOptionsVO(String optContent, Boolean isRight) {
    	this.optContent = optContent;
		this.isRight = isRight;
	}
    
	public ChoiceOptionsVO(String sequence, String optContent, Boolean isRight) {
		super();
		this.sequence = sequence;
		this.optContent = optContent;
		this.isRight = isRight;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getOptContent() {
		return optContent;
	}
	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}
	public Boolean getIsRight() {
		return isRight;
	}
	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}
    
    
}
