<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：updateClass.jsp-->
<!--描   述：修改班级页面-->
<!--时   间 ：2015年09月05日-->
<script type="text/javascript">
$(document).ready(function(){
	var unID = $("#unId", $.pdialog.getCurrent()).val();
	var ceID = $("#ceId", $.pdialog.getCurrent()).val();
	//级联
	changeAjaxSelect2($("#updateClassUnSelect"), $("#updateClassCeSelect"), "communion/gainCollegeByUnIdAjax.action", "请选择学院", {unId:null}, ceID);
	//会默认选择unID，触发选择事件
	loadSelect2($("#updateClassUnSelect"), "communion/gainUniversityBySearchAjax.action", "请选择学校", {"q":""}, unID);
});

</script>
<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/updateClass.action" method="post" novalidate="novalidate">
	<input type="hidden" name="cla.cid" value="${clas.cid }"/>
	<input type="hidden" id="unId" value="${clas.college.university.unID }"/>
	<input type="hidden" id="ceId" value="${clas.college.ceID }"/>
	<div class="pageFormContent" layouth="58">	
		<div class="unit">
			<label style="width:65px">学校:</label>
			<select id="updateClassUnSelect" name="cla.college.university.unID" class="required" style="width: 150px;" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if>>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">学院:</label>
			<select id="updateClassCeSelect" name="cla.college.ceID" class="required" style="width: 150px;" <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>>
				<option></option>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">专业：</label>
			<input style="width:150px" class="required common textInput" type="text"  name="cla.specialty" value="${clas.specialty }"/>
		</div>
		<div class="unit">
			<label style="width:65px">班名：</label>
			<input style="width:150px" class="required common textInput" type="text" name="cla.cname" value="${clas.cname }"/>
		</div>
		<div class="unit">
			<label style="width:65px">年届：</label>
			<input style="width:150px" class="required digits textInput" type="text" name="cla.startYear" value="${clas.startYear }"/>
		</div>
		<div class="unit">
			<label style="width:65px">类型：</label>
			<select style="width:150px" class="required combox" disabled="disabled">
				<option value="0" <c:if test="${clas.kind == 0 }">selected="selected"</c:if>>普通班级</option>
				<option value="1" <c:if test="${clas.kind == 1 }">selected="selected"</c:if>>教师映射班级</option>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">有效状态：</label>
			<select style="width:150px" class="required combox" name="cla.enabled">
				<option value="0" <c:if test="${clas.enabled == 0 }">selected="selected"</c:if>>停用&nbsp;&nbsp;</option>
				<option value="1" <c:if test="${clas.enabled == 1 }">selected="selected"</c:if>>正常&nbsp;&nbsp;</option>
			</select>
		</div>		
		<div class="unit">		
			<label style="width:65px">锁定状态：</label>
			<select style="width:150px" class="required combox" name="cla.status">
				<option value="0" <c:if test="${clas.status == 0 }">selected="selected"</c:if>>未锁定&nbsp;&nbsp;</option>
				<option value="1" <c:if test="${clas.status == 1 }">selected="selected"</c:if>>登录锁定&nbsp;&nbsp;</option>
				<option value="2" <c:if test="${clas.status == 2 }">selected="selected"</c:if>>做题锁定&nbsp;&nbsp;</option>
				<option value="3" <c:if test="${clas.status == 3 }">selected="selected"</c:if>>考试锁定&nbsp;&nbsp;</option>
				<option value="4" <c:if test="${clas.status == 4 }">selected="selected"</c:if>>教师专属&nbsp;&nbsp;</option>
			</select>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">提交</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
