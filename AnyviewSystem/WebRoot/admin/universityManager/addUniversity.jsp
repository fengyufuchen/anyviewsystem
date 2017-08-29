<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：addUniversity.jsp-->
<!--描   述：添加学校页面-->
<!--时   间 ：2015年08月05日-->
<script type="text/javascript">
$(function(){
	$("input[group='addUniversityAttrRadio']").bind("change",function(){
		if(this.value==0){
			$("#addUniversityIpAndPortDiv").hide();
		}else{
			$("#addUniversityIpAndPortDiv").show();
		}
	});
});

$(function(){
 	$("#university").select2({
		placeholder : "请选择学校",
		allowClear : true
	}); 
	
	$.ajax({
		url:"communion/gainChinaUniversityBySearchAjax.action",
		data:{"q":""}
		}).done(function(resp){
		uns = eval("("+resp+")");
		opts = ""
		uns.forEach(function(e){
			 opts += "<option type='optionschool' value='"+e.text+"'>"+e.text+"</option>"; 
		}); 
		$("#university").html(opts);
	});

});

</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/universityManager/addUniversity.action" method="post" novalidate="novalidate">
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">	
		<div class="unit">
			<label>学校名称：</label>
			<!-- <input class="required chinese textInput" type="text"  name="univer.unName"/> -->
			<select style="width:200px" id="university" name="univer.unName" >
				<c:forEach items="${universities }" var="u">
					<option type="optionschool" value="${u.unID }">${u.unName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>验证码:</label>			
			<input class="textInput" type="text" name="univer.verification">
		</div>
		<div class="unit">
			<label>状态:</label>
			<input type="radio" name="univer.enabled" value="1" checked="checked">正常
			<input type="radio" name="univer.enabled" value="0">停用
		</div>
		<div class="unit">
			<label>属性:</label>
			<input type="radio" group="addUniversityAttrRadio" name="univer.attr" value="0" checked="checked">本地服务器
			<input type="radio" group="addUniversityAttrRadio" name="univer.attr" value="1">独立服务器
		</div>
		<div id="addUniversityIpAndPortDiv" style="display: none;">
			<div class="unit">
				<label>独立Ip:</label>			
				<input class="ip textInput" type="text" name="univer.ip">
			</div>
			<div class="unit">
				<label>独立Port:</label>			
				<input class="textInput" type="text" name="univer.port">
			</div>
		</div>
	</div>
	<div class="formBar" >
		<ul>
			<li>
				<div class="buttonActive" >
					<div class="buttonContent">
						<button type="submit">提交</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button" >
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>