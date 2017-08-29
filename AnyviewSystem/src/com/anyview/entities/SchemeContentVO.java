package com.anyview.entities;

/**
 * 用于存放解析之后的题目内容，向页面显示
* @ClassName: SchemeContentVO 
* @author 何凡 <piaobo749@qq.com>
* @date 2016年4月18日 下午8:16:18 
*
 */
public class SchemeContentVO {

	private SchemeContentTable schemeContent;
	private ProblemContentVO problemContentVO;
	
	public SchemeContentTable getSchemeContent() {
		return schemeContent;
	}
	public void setSchemeContent(SchemeContentTable schemeContent) {
		this.schemeContent = schemeContent;
	}
	public ProblemContentVO getProblemContentVO() {
		return problemContentVO;
	}
	public void setProblemContentVO(ProblemContentVO problemContentVO) {
		this.problemContentVO = problemContentVO;
	}
}
