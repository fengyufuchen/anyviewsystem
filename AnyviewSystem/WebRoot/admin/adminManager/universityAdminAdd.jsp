<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	$("#universityAdminAddCeSelect").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminManager/saveAdmin.action" method="post" novalidate="novalidate">

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>学院：</label>
		<select id="universityAdminAddCeSelect" name="condition.college.ceID" style="width: 150px;">
			<option></option>
			<c:forEach items="${colleges }" var="c">
				<option value="${c[0] }">${c[1] }</option>
			</c:forEach>
		</select>
	</div>
	<div class="unit">
		<label>管理员编号：</label>
		<input class="required digits textInput" type="text"  name="condition.mno"/>
	</div>
	<div class="unit">
		<label>身份：</label>
		<select name="condition.miden" disabled="disabled">
			<option value="0">学院管理员</option>
		</select>
	</div>
	<div class="unit">
		<label>有效状态：</label>
		<select name="condition.enabled">
			<option value="1">正常</option>
			<option value="0">停用</option>
		</select>
	</div>
	<div class="unit">
		<p style="color: red">注：密码默认为管理员编号</p>
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