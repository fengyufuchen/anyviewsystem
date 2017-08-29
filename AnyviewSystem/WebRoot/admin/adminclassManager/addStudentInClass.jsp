<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：addStudentInClass.jsp-->
<!--描   述：添加班级学生关联关系页面-->
<!--时   间 ：2015年08月29日-->
<script type="text/javascript">
</script>

	<form class="pageForm required-validate" id="addStudentInClass" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/saveStudentInClass.action" method="post" novalidate="novalidate">
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
						
		<div class="unit">
			<label  style="float: center;">&nbsp;&nbsp;状态：</label>
			<select style="float: center;" class="required combox" name="cs.status">
				<option value="0">无&nbsp;&nbsp;效&nbsp;&nbsp;</option>
				<option value="1" selected="selected">有&nbsp;&nbsp;效&nbsp;&nbsp;</option>
			</select>
		</div>
		
		<div class="unit">
			<label  style="float: center;">&nbsp;&nbsp;属性：</label>
			<select style="float: center;" class="required combox" name="cs.sattr">
				<option value="0">休学学生&nbsp;&nbsp;</option>
				<option value="1" selected="selected">普通学生&nbsp;&nbsp;</option>
				<option value="2">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;长&nbsp;&nbsp;</option>
				<option value="3">教师专用&nbsp;&nbsp;</option>
				<option value="4">教师专属&nbsp;&nbsp;</option>
			</select>
		</div>
		
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" id="subBtn">提交</button>
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