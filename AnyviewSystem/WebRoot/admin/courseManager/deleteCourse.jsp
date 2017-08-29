<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<form id="deleteCourse" method="post" action="admin/courseManager/deleteCourse.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<input type="hidden" id="courseId" name="courseId" value="${course.courseId }" >

	<div class="pageFormContent" layoutH="56">
		<div style="padding: 20px">
		<table height="150px">
			<tr>
			<td bgcolor="#FFCC33">请确认删除信息:</td>
			</tr>
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
				<td>${course.courseName }</td>
			</tr>
			<tr>
            	<td>课程类别：</td>
            	<td>${course.category }</td>
            </tr>
            <tr>
				<td>有效状态：</td>
				<td>
					<c:choose>
						<c:when test="${course.enabled == 0 }">停用</c:when>
						<c:when test="${course.enabled == 1 }">正常</c:when>
					</c:choose>
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
								<button type="submit">确认删除</button>
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