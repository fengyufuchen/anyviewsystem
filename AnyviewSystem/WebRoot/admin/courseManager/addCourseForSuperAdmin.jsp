<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<%-- <script type="text/javascript">
$(function(){
	//学校select
	$("#unID_addCourse").change(function(){
		var url=$("#unID_addCourse").attr("refUrl");
		var unID = $("#unID_addCourse").val();
		if(unID==""){
			var ce = $("#unID_addCourse");
			ce.empty();
			var $ab ;
			ce.append($ab);
		}else{
			$.ajax({
				type:"POST",
				url:url,
				data:{unID:unID},
				cache:"false",
				success:function(data)
				{
					var cols = $.parseJSON(data);
					var ce = $("#ceID_addCourse");
					ce.empty();//先清空下一级select
					var $allCol ;
					ce.append($allCol);
					for(i=0;i<cols.length;i++){
						var $op = $('<option value="'+cols[i].ceID+'">'+cols[i].ceName+'</option>');
						ce.append($op);
					}
				}
			});
		}
	});
	
})
</script> --%>

<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#unID_addCourse").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
	$("#ceID_addCourse").select2({
		placeholder : '请选择学院',
		allowClear : true
	});
	//级联
	changeAjaxSelect2($("#unID_addCourse"), $("#ceID_addCourse"), "communion/gainCollegeByUnIdAjax.action", "请选择学院", {unId:null});
});
</script>

<form id="addCourse" method="post" action="admin/courseManager/addCourse.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	
	<div class="pageFormContent" layoutH="56">
		<div style="padding: 5px">
		您是超级管理员，请注意：<br><br>
		1.若增加课程的【分类】为“校内共享课程”，则可任意选择学院，系统将自动设置本课程属于所有学院共享；<br>
		2.若增加课程的【分类】为“公共课程”，则可任意选择学校和学院，系统将自动设置本课程属于所有学校（所有学院）共享；<br>
		<table height="150px">			
            <tr>
            	<td>所属学校：</td>
				<td>
					<select class="combox" style="width: 150px;" name="unID" ref="ceID" id="unID_addCourse" refUrl="admin/courseManager/getCollegeByUnIDAjax.action">
						  <option <c:if test="${empty unID }"> selected="selected"</c:if> value=""> 请选择学校</option>
						<c:forEach items="${university }" var="university">
							<option value="${university.unID }">${university.unName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>所属学院：</td>
				<td>
					<select class="combox" style="width: 150px;" id="ceID_addCourse" name="ceID">
						<c:choose>
							<c:when test="${admin.miden==-1 }">
								<c:forEach items="${colleges }" var="c">
									<option value="${c.ceID }" <c:if test="${c.ceID == course.college.ceID }">selected="selected"</c:if>>${c.ceName }</option>
								</c:forEach>
							</c:when>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<td>课程名：</td>
				<td><input id="courseName" name="courseName"/></td>
			</tr>
			<tr>
            	<td>课程类别：</td>
            	<td>
            		<select id="category" name="category">
	            		<option id="category_0" value="0" selected="selected">学院级课程</option>
						<option id="category_1" value="1">校级课程</option>
						<option id="category_2" value="2">校内共享课程</option>
						<option id="category_3" value="3">公共课程</option>
					</select>
            	</td>
            </tr>
            <tr>
				<td>有效状态：</td>
				<td>
	            	<select id="enabled" name="enabled">
	            		<option id="enabled_1" value="1" selected="selected">正常</option>
						<option id="enabled_0" value="0">停用</option>
					</select>
	            </td>
	         </tr>
	        
	    </table>
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