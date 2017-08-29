<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<form id="editCourse" method="post" action="admin/courseManager/editCourse.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<input type="hidden" id="courseId" name="courseId" value="${course.courseId }" />
	<input type="hidden" id="unID" name="unID" value="${course.university.unID }" />
	<input type="hidden" id="oldCourseName" name="oldCourseName" value="${course.courseName }" />
	
	<div class="pageFormContent" layoutH="56">
		<div style="padding: 5px">
		您是校级管理员，请注意：<br>
		若修改当前课程的【分类】为“校内共享课程”，则可任意选择学院，系统将自动设置本课程属于所有学院共享；<br>
		<table height="150px">			
            <tr>
            	<td>所属学校：</td>
				<td>${course.university.unName }</td>
			</tr>
			<tr>
				<td>所属学院：</td>
				<td>
					<select id="ceID" name="ceID">
						<c:forEach items="${college }" var="college">
							<option value="${college.ceID }" <c:if test="${college.ceID == course.college.ceID }">selected="selected"</c:if>>${college.ceName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>课程名：</td>
				<td><input id="courseName" name="courseName" value="${course.courseName }"/></td>
			</tr>
			<tr>
            	<td>课程类别：</td>
            	<td>
            		<select id="category" name="category">
            		
	            		<option id="category_0" value="0" <c:if test="${course.category == '学院级课程' }">selected="selected"</c:if>>学院级课程</option>
						<option id="category_1" value="1" <c:if test="${course.category == '校级课程' }">selected="selected"</c:if>>校级课程</option>
						<option id="category_2" value="2" <c:if test="${course.category == '校内共享课程' }">selected="selected"</c:if>>校内共享课程</option>
					</select>
            	</td>
            </tr>
            <tr>
				<td>有效状态：</td>
				<td>
	            	<select id="enabled" name="enabled">
	            		<option id="enabled_1" value="1" <c:if test="${course.enabled == 1 }">selected="selected"</c:if>>正常</option>
						<option id="enabled_0" value="0" <c:if test="${course.enabled == 0 }">selected="selected"</c:if>>停用</option>
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