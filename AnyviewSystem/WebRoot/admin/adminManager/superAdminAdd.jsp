<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	$("#superAdminAddMidenSelect").bind("change",function(){
		if(this.value==0){
			$("#superAdminAddCeSelect").val(null);
			$("#superAdminAddCeSelect").html(null);
			$("#superAdminAddCeDiv").show();
		}else{
			$("#superAdminAddCeDiv").hide();
		}
	});
	
	//初始化学校，学院select
	$("#superAdminAddUnSelect").select2({
		placeholder : "请选择学校",
		allowClear : true
	});
	$("#superAdminAddCeSelect").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
	//级联
	$("#superAdminAddUnSelect").on("change",function(e){
		if($("#superAdminAddMidenSelect").val()==1)//校级管理员不显示学院下拉框也不级联
			return;
		if(this.value==""){
			$("#superAdminAddCeSelect").clear();
			return;
		}
		$.ajax({
			type:"POST",
			url:"communion/gainCollegeByUnIdAjax.action",
			data:{unId:$("#superAdminAddUnSelect").val()},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				$("#superAdminAddCeSelect").html(null);
				var colleges = $.parseJSON(data);
				$("#superAdminAddCeSelect").append($('<option></option>'));
				for(var i=0; i<colleges.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",colleges[i].id);
					opt.html(colleges[i].text);
					$("#superAdminAddCeSelect").append(opt);
				}
			}
		});
	});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminManager/saveAdmin.action" method="post" novalidate="novalidate">

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>学校：</label>
		<select id="superAdminAddUnSelect" name="condition.university.unID" style="width: 150px;">
			<option></option>
			<c:forEach items="${universities }" var="u">
				<option value="${u.unID }">${u.unName }</option>
			</c:forEach>
		</select>
	</div>
	<div class="unit" id="superAdminAddCeDiv">
		<label>学院：</label>
		<select id="superAdminAddCeSelect" name="condition.college.ceID" style="width: 150px;">
			<option></option>
		</select>
	</div>
	<div class="unit">
		<label>管理员编号：</label>
		<input class="required digits textInput" type="text"  name="condition.mno"/>
	</div>
	<div class="unit">
		<label>身份：</label>
		<select id="superAdminAddMidenSelect" name="condition.miden">
			<option value="0">院级管理员</option>
			<option value="1">校级管理员</option>
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