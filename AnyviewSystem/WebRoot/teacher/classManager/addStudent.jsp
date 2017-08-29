<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/classManager/saveStudentForTeacher.action" method="post" novalidate="novalidate">
<input type="hidden" name="cs.cla.cid" value="${param.cid }" />
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>学生账号：</label>
			<input class="required digits textInput" type="text"  name="cs.student.sno"/>
		</div>
		<div class="unit">
			<label>学生姓名:</label>
			<input class="required chinese textInput" type="text" name="cs.student.sname">
		</div>
		<!--<div class="unit">
			<label>密码:</label>
			<input class="alphanumeric textInput" type="text" name="stu.spsw">
		</div> -->
		<div class="unit">
			<label>性别:</label>
			<input type="radio" name="cs.student.ssex" value="M" checked="checked">男
			<input type="radio" name="cs.student.ssex" value="F">女
		</div>
		<div class="unit">
			<label>属性:</label>
			<select class="required combox" name="cs.sattr">
				<option value="0">休学</option>
				<option value="1" selected="selected">普通</option>
				<option value="2">班长</option>
				<option value="3">教师专用</option>
				<option value="4">教师专属</option>
			</select>
		</div>
		<div class="unit">
			<label>此班级中有效性:</label>
			<select class="required combox" name="cs.status">
				<option value="0">无效</option>
				<option value="1" selected="selected">有效</option>
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