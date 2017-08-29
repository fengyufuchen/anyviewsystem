/**   
* @Title: FileMsgVO.java 
* @Package com.anyview.entities 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年10月29日 下午10:01:48 
* @version V1.0   
*/
package com.anyview.entities;

/** 
 * @ClassName: FileMsgVO 
 * @Description: TODO(封装文件名及文件内容) 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年10月29日 下午10:01:48 
 *  
 */
public class FileMsgVO {

	private String fileName;
	private String fileContent;
	
	public FileMsgVO() {
		super();
	}
	
	public FileMsgVO(String fileName, String fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	
}
