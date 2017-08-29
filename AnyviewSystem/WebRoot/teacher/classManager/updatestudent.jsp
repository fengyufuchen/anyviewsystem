<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/classManager/updateStudent.action" method="post" novalidate="novalidate">
	<input type="hidden" name="cs.id" value="${classStudent.id }"/>
	<input type="hidden" name="cs.student.sid" value="${classStudent.student.sid }"/>
	<input type="hidden" name="cs.cla.cid" value="${classStudent.cla.cid }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>学生账号：</label>
			<input class="required digits textInput" type="text"  name="cs.student.sno" value="${classStudent.student.sno }"/>
		</div>
		<div class="unit">
			<label>学生姓名:</label>
			<input class="required chinese textInput" type="text" name="cs.student.sname" value="${classStudent.student.sname }">
		</div>
		<div class="unit">
			<label>性别:</label>
			<select class="required combox" name="cs.student.ssex">
				<option value="M" <c:if test="${classStudent.student.ssex == 'M' }">selected="selected"</c:if>>男</option>
				<option value="F" <c:if test="${classStudent.student.ssex == 'F' }">selected="selected"</c:if>>女</option>
			</select>
		</div>
		<div class="unit">
			<label>属性:</label>
			<select class="required combox" name="cs.sattr">
				<option value="0" <c:if test="${classStudent.sattr == 0 }">selected="selected"</c:if>>休学</option>
				<option value="1" <c:if test="${classStudent.sattr == 1 }">selected="selected"</c:if>>普通</option>
				<option value="2" <c:if test="${classStudent.sattr == 2 }">selected="selected"</c:if>>班长</option>
				<option value="3" <c:if test="${classStudent.sattr == 3 }">selected="selected"</c:if>>教师专用</option>
				<option value="4" <c:if test="${classStudent.sattr == 4 }">selected="selected"</c:if>>教师专属</option>
			</select>
		</div>
		<div class="unit">
			<label>此班级中有效性:</label>
			<select class="required combox" name="cs.status">
				<option value="0" <c:if test="${classStudent.status == 0 }">selected="selected"</c:if>>无效</option>
				<option value="1" <c:if test="${classStudent.status == 1 }">selected="selected"</c:if>>有效</option>
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