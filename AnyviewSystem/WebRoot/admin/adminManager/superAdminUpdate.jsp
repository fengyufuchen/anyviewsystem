<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	$("#superAdminUpdateMidenSelect").bind("change",function(){
		if(this.value==0){
			$("#superAdminUpdateCeSelect").val(null);
			$("#superAdminUpdateCeSelect").html(null);
			$("#superAdminUpdateCeDiv").show();
		}else{
			$("#superAdminUpdateCeDiv").hide();
		}
	});
	
	//初始化学校，学院select
	$("#superAdminUpdateUnSelect").select2({
		placeholder : "请选择学校",
		allowClear : true
	});
	$("#superAdminUpdateCeSelect").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
	//级联
	$("#superAdminUpdateUnSelect").on("change",function(e){
		if($("#superAdminUpdateMidenSelect").val()==1)//校级管理员不显示学院下拉框也不级联
			return;
		if(this.value==""){
			$("#superAdminUpdateCeSelect").clear();
			return;
		}
		$.ajax({
			type:"POST",
			url:"communion/gainCollegeByUnIdAjax.action",
			data:{unId:$("#superAdminUpdateUnSelect").val()},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				$("#superAdminUpdateCeSelect").html(null);
				var colleges = $.parseJSON(data);
				$("#superAdminUpdateCeSelect").append($('<option></option>'));
				for(var i=0; i<colleges.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",colleges[i].id);
					opt.html(colleges[i].text);
					$("#superAdminUpdateCeSelect").append(opt);
				}
			}
		});
	});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminManager/saveUpdateAdmin.action" method="post" novalidate="novalidate">
<input type="hidden" name="condition.mid" value="${updateAdmin.mid }">

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>学校：</label>
		<select id="superAdminUpdateUnSelect" name="condition.university.unID" style="width: 150px;">
			<option></option>
			<c:forEach items="${universities }" var="u">
				<option value="${u.unID }" <c:if test="${updateAdmin.university.unID==u.unID }">selected="selected"</c:if> >${u.unName }</option>
			</c:forEach>
		</select>
	</div>
	<div class="unit" id="superAdminUpdateCeDiv"  <c:if test="${updateAdmin.miden==1 }">style="display: none;"</c:if> >
		<label>学院：</label>
		<select id="superAdminUpdateCeSelect" name="condition.college.ceID" style="width: 150px;">
			<option></option>
			<c:forEach items="${colleges }" var="c">
				<option value="${c[0] }" <c:if test="${updateAdmin.college.ceID==c[0] }">selected="selected"</c:if> >${c[1] }</option>
			</c:forEach>
		</select>
	</div>
	<div class="unit">
		<label>管理员编号：</label>
		<input class="required digits textInput" type="text"  name="condition.mno" value="${updateAdmin.mno }"/>
	</div>
	<div class="unit">
		<label>身份：</label>
		<select id="superAdminUpdateMidenSelect" name="condition.miden">
			<option value="0" <c:if test="${updateAdmin.miden==0 }">selected="selected"</c:if> >院级管理员</option>
			<option value="1" <c:if test="${updateAdmin.miden==1 }">selected="selected"</c:if> >校级管理员</option>
		</select>
	</div>
	<div class="unit">
		<label>有效状态：</label>
		<select name="condition.enabled">
			<option value="1" <c:if test="${updateAdmin.enabled==1 }">selected="selected"</c:if> >正常</option>
			<option value="0" <c:if test="${updateAdmin.enabled==0 }">selected="selected"</c:if> >停用</option>
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