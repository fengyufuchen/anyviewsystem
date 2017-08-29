<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
$(function(){
	$("#pwdUpdateSubmitBtn").click(function(){
		if($("#newPwd").val() != $("#repeatPwd").val()){
			alertMsg.error("两次密码不一致!");
		}else{
			$("#pwdUpdateForm").submit();
		}
	});
});
</script>

<form id="pwdUpdateForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)" action="admin/passwordManager/updateAdminPwd.action" method="post" novalidate="novalidate">

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>旧密码：</label>
		<!-- <input type="password" name="oldPwd" class="required textInput"/> -->
		<input type="password" name="oldPwd"/>
	</div>
	<div class="unit">
		<label>新密码：</label>
		<input type="password" id="newPwd" name="newPwd" class="required textInput"/>
	</div>
	<div class="unit">
		<label>重复输入新密码：</label>
		<input type="password" id="repeatPwd" class="required textInput"/>
	</div>
</div>

<div class="formBar">
	<ul>
		<li>
			<div class="buttonActive">
				<div class="buttonContent">
					<button id="pwdUpdateSubmitBtn" type="button">提交</button>
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