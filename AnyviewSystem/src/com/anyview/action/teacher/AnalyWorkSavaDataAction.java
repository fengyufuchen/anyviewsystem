package com.anyview.action.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.anyview.action.common.CommonAction;
import com.anyview.entities.TeacherTable;
//import com.stusys.entities.anyview.TeacherTable;
import com.anyview.service.function.AnalyWorkService;

/**
 * 
 * @ClassName: AnalyWorkSavaData 
 * @Description: 批改作业保存资料
 * @date 2015-8-1 下午12:51:36
 */
@SuppressWarnings("serial")
@Namespace("/teacher/homeworkCorrecting")
@ParentPackage("json-default")
@ExceptionMappings({@ExceptionMapping(exception = "java.lange.RuntimeException", result = "error")})

public class AnalyWorkSavaDataAction extends CommonAction {
	//注入
	@Autowired
	private AnalyWorkService awService;
	//前台数据
	private int exId;
	private String comment;
	private float score;
	
	private int sort;
	private int classId;
	private int cid;

	private int schemeId;
	private Set<Integer> proIdSet;
	private Set<Integer> stuIdSet;

	//返回数据
	private String msg;
	
	//下载文件路径
	private String fileName;
	private String directory;//只有set方法，保证文件路径不被访问到
	
	@Action(value="savaData",results={@Result(name="success",type="json")})
	public String saveData(){
		if(awService.updateExercise(exId, comment, score))
			msg = "保存成功!";
		else
			msg = "保存失败";
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	public InputStream getInputStream() throws MalformedURLException, FileNotFoundException{
		String dir = directory + fileName;//dir是相对路径
		String path = ServletActionContext.getServletContext().getRealPath("/");
		return new FileInputStream(new File(path+dir));
	}
	@Action(value="downloadAWData",results={@Result(name="success",type="stream",params={"contentType", "application/octet-stream", "inputName","inputStream","contentDisposition","attachment;filename=\"${fileName}\"","bufferSize","4096"})})

	public String execute(){
		TeacherTable teaUser = (TeacherTable)ActionContext.getContext().getSession().get("User");
		fileName = teaUser.getTid()+"-"+teaUser.getTno()+".xls";
		setDirectory("WEB-INF\\fileTemp\\");
		String absolutePath = ServletActionContext.getServletContext().getRealPath("/");
		absolutePath += directory + fileName;
		//生成文件
		try{
			awService.createFile(absolutePath,sort, cid, schemeId, proIdSet, stuIdSet);
		}catch(Exception e){
			e.printStackTrace();
			addActionError("数据发生错误,生成文件失败!");
			return "error";
		}
		return SUCCESS;
	}
	
	//各依赖注入属性方法
	public void setExId(int exId) {
		this.exId = exId;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public void setAwService(AnalyWorkService awService) {
		this.awService = awService;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public void setProIdArr(String arr) {
		proIdSet = new HashSet<Integer>();
		String[] temp = arr.split(",");
		try{
			for(String s : temp){
				proIdSet.add(Integer.valueOf(s));
			}
		}catch(NumberFormatException e){
			this.addActionError("数据发生错误!");
		}
	}

	public void setStuIdArr(String arr) {
		stuIdSet = new HashSet<Integer>();
		String[] temp = arr.split(",");
		try{
			for(String s : temp){
				stuIdSet.add(Integer.valueOf(s));
			}
		}catch(NumberFormatException e){
			this.addActionError("数据发生错误!");
		}
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	//返回参数
	public String getMsg() {
		return msg;
	}
	@JSON
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
