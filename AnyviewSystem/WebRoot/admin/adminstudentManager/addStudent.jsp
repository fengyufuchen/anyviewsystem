<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：addStudent.jsp-->
<!--描   述：添加学生页面-->
<!--时   间 ：2015年08月29日-->
<script type="text/javascript">
</script>

<form class="pageForm required-validate" id="addStudent" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminstudentManager/saveStudent.action" method="post" novalidate="novalidate">
<input type="hidden" name="stu.LoginStatus" value="0"/>

	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label style="float: center;">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;校：</label>
				<select style="float: center;" class="combox" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if> name="unId" id="select_UnID" ref="select_CeID" refUrl="admin/adminstudentManager/getAdminStudentAllCollege.action">
					<c:choose>
						<c:when test="${admin.miden==-1 }">
							<option id="allUniversity" value="-2" selected="selected">请选择学校(可选)&nbsp;&nbsp;&nbsp;&nbsp;</option>
						<c:forEach items="${universities }" var="u">
							<option value="${u.unID }">${u.unName }</option>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
						</c:otherwise>
					</c:choose>
				</select>
				
				
		</div>
							
		<div class="unit">
			<label style="float: center;">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
			<input style="float: center;" class="required digits textInput" type="text"  name="stu.sno"/>
		</div>
		<div class="unit">
			<label style="float: center;">&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
			<input style="float: center;" class="required chinese textInput" type="text" name="stu.sname">
		</div>
		<div class="unit">
			<label  style="float: center;">&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
			<input style="float: center;" type="radio" name="stu.ssex" value="M" checked="checked">男
			<input style="float: center;" type="radio" name="stu.ssex" value="F">女
		</div>
		<div class="unit">
			<label  style="float: center;">&nbsp;&nbsp;有效状态：</label>
			<select style="float: center;" class="required combox" name="stu.enabled">
				<option value="0">停&nbsp;&nbsp;用&nbsp;&nbsp;</option>
				<option value="1" selected="selected">正&nbsp;&nbsp;常&nbsp;&nbsp;</option>
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