<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<form id="editCourse" method="post" action="admin/courseManager/editCourse.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<input type="hidden" id="courseId" name="courseId" value="${course.courseId }" />
	<input type="hidden" id="ceID" name="ceID" value="${course.college.ceID }" />
	<input type="hidden" id="unID" name="unID" value="${course.university.unID }" />
	<input type="hidden" id="oldCourseName" name="oldCourseName" value="${course.courseName }" />
	<input type="hidden" id="category" name="category" value="0" />
	
	<div class="pageFormContent" layoutH="56">
		<div style="padding: 5px">
		<table height="150px">
			
            <tr>
            	<td>所属学校：</td>
				<td>${course.university.unName }</td>
			</tr>
			<tr>
				<td>所属学院：</td>
				<td>${course.college.ceName }</td>
			</tr>
			<tr>
				<td>课程名：</td>
				<td><input id="courseName" name="courseName" value="${course.courseName }"/></td>
			</tr>
			<tr>
            	<td>课程类别：</td>
            	<td>学院级课程</td>
            </tr>
            <tr>
				<td>有效状态：</td>
				<td>
	            	<select id="enabled" name="enabled">
	            		<option id="enabled_1" value="1" <c:if test="${course.enabled==1 }">selected="selected"</c:if> >正常</option>
						<option id="enabled_0" value="0" <c:if test="${course.enabled==0 }">selected="selected"</c:if>>停用</option>
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