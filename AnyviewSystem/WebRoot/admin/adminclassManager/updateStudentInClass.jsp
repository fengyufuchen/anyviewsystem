<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：updateStudentInClass.jsp-->
<!--描   述：编辑班级学生关联关系页面-->
<!--时   间 ：2015年08月29日-->
<script type="text/javascript">
</script>

<form class="pageForm required-validate" id="updateStudentInClass" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/updateStudentInClass.action" method="post" novalidate="novalidate">
	<input type="hidden" name="cs.id" value="${classStudent.id }"/>
	<input type="hidden" name="cs.student.sid" value="${classStudent.student.sid }"/>
	<input type="hidden" name="cs.cla.cid" value="${classStudent.cla.cid }"/>
	
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
						
		<div class="unit">
			<label>属&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性：</label>
			<select class="required combox" name="cs.sattr">
				<option value="0" <c:if test="${classStudent.sattr == 0 }">selected="selected"</c:if>>休&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;</option>
				<option value="1" <c:if test="${classStudent.sattr == 1 }">selected="selected"</c:if>>普&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通&nbsp;&nbsp;</option>
				<option value="2" <c:if test="${classStudent.sattr == 2 }">selected="selected"</c:if>>班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;长&nbsp;&nbsp;</option>
				<option value="3" <c:if test="${classStudent.sattr == 3 }">selected="selected"</c:if>>教师专用&nbsp;&nbsp;</option>
				<option value="4" <c:if test="${classStudent.sattr == 4 }">selected="selected"</c:if>>教师专属&nbsp;&nbsp;</option>
			</select>
		</div>
		
		<div class="unit">
			<label>是否有效：</label>
			<select class="required combox" name="cs.status">
				<option value="0" <c:if test="${classStudent.status == 0 }">selected="selected"</c:if>>无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;效&nbsp;&nbsp;</option>
				<option value="1" <c:if test="${classStudent.status == 1 }">selected="selected"</c:if>>有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;效&nbsp;&nbsp;</option>
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