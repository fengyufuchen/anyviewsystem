<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：addClass.jsp-->
<!--描   述：添加班级页面-->
<!--时   间 ：2015年09月05日-->

<script type="text/javascript">
$(function(){
loadSelect2($("#addClassUnSelect"), "communion/gainUniversityBySearchAjax.action", "请选择学校", {"q":""});
$("#addClassCeSelect").select2({
	placeholder:"请选择学院",
	allowClear:true,
});
//级联
changeAjaxSelect2($("#addClassUnSelect"), $("#addClassCeSelect"), "communion/gainCollegeByUnIdAjax.action", "请选择学院", {unId:null});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/saveClass.action" method="post" novalidate="novalidate">
	<input type="hidden" name="cla.epId" value="0"/>
	<input type="hidden" name="cla.kind" value="0"/>
	
	<div class="pageFormContent" layouth="58">	
		<div class="unit">
			<label style="width:65px">学校:</label>
			<select id="addClassUnSelect" name="unId" class="required" style="width: 150px;" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if>>
						<c:choose>
							<c:when test="${admin.miden!=-1 }">
								<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
							</c:when>
							<c:otherwise>
								<option></option>
								<c:forEach items="${universities }" var="u">
									<option value="${u.unID }" <c:if test="${(!empty condition.college.university) && condition.college.university.unID==u.unID }">selected="selected"</c:if> >${u.unName }</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">学院:</label>
			<select id="addClassCeSelect" name="ceId" class="required" style="width: 150px;" <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>>
				<option></option>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">专业：</label>
			<input style="width:150px" class="required common textInput" type="text"  name="cla.specialty"/>
		</div>
		<div class="unit">
			<label style="width:65px">班名：</label>
			<input style="width:150px" class="required common textInput" type="text" name="cla.cname"/>
		</div>
		<div class="unit">
			<label style="width:65px">年届：</label>
			<input style="width:150px" class="required digits textInput" type="text" name="cla.startYear"/>
		</div>
		<div class="unit">
			<label style="width:65px">类型：</label>
			<select style="width:150px" class="required combox" disabled="disabled">
				<option value="0" selected="selected">普通班级</option>
				<option value="1">教师映射班级</option>
			</select>
		</div>
		<div class="unit">
			<label style="width:65px">有效状态：</label>
			<select style="width:150px" class="required combox" name="cla.enabled">
				<option value="0">停用&nbsp;&nbsp;</option>
				<option value="1" selected="selected">正常&nbsp;&nbsp;</option>
			</select>
		</div>		
		<div class="unit">		
			<label style="width:65px">锁定状态：</label>
			<select style="width:150px" class="required combox" name="cla.status">
				<option value="0">未锁定&nbsp;&nbsp;</option>
				<option value="1">登录锁定&nbsp;&nbsp;</option>
				<option value="2">做题锁定&nbsp;&nbsp;</option>
				<option value="3">考试锁定&nbsp;&nbsp;</option>
				<option value="4">教师专属&nbsp;&nbsp;</option>
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
