<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：updateStudent.jsp-->
<!--描   述：修改学生页面-->
<!--时   间 ：2015年08月29日-->

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminstudentManager/updateStudent.action" method="post" novalidate="novalidate">
	<input type="hidden" name="stu.sid" value="${student.sid }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校：</label>
			<select class="combox" disabled="disabled">
				<option selected="selected">${uname}</option>
			</select>
		</div>
		<div class="unit">
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
			<input class="required digits textInput" type="text"  name="stu.sno" value="${student.sno }"/>
		</div>
		<div class="unit">
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
			<input class="required chinese textInput" type="text" name="stu.sname" value="${student.sname }">
		</div>
		<div class="unit">
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
			<select class="required combox" name="stu.ssex">
				<option value="M" <c:if test="${student.ssex=='M' }">selected="selected"</c:if>>男</option>
				<option value="F" <c:if test="${student.ssex=='F' }">selected="selected"</c:if>>女</option>
			</select>
		</div>
		<div class="unit">
			<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有效状态：</label>
			<select class="required combox" name="stu.enabled">
				<option value="0" <c:if test="${student.enabled == 0 }">selected="selected"</c:if>>停&nbsp;&nbsp;用&nbsp;&nbsp;</option>
				<option value="1" <c:if test="${student.enabled == 1 }">selected="selected"</c:if>>正&nbsp;&nbsp;常&nbsp;&nbsp;</option>
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