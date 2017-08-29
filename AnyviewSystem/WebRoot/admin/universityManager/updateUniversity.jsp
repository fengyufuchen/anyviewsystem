<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：updateUniversity.jsp-->
<!--描   述：修改学校页面-->
<!--时   间 ：2015年08月07日-->
<script type="text/javascript">
$(function(){
	$("input[group='updateUniversityAttrRadio']").bind("change",function(){
		if(this.value==0){
			$("#updateUniversityIpAndPortDiv").hide();
		}else{
			$("#updateUniversityIpAndPortDiv").show();
		}
	});
});
</script>
<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/universityManager/updateUniversity.action" method="post" novalidate="novalidate">
	 <input type="hidden" name="unID" id="unID" value="${university.unID }" />
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>学校名称：</label>
			<input class="required chinese textInput" type="text"  name="univer.unName" value="${university.unName}" />
		</div>
		<div class="unit">
			<label>验证码:</label>			
			<input class="textInput" type="text" name="univer.verification" value="${university.verification }">
		</div>
		<div class="unit">
			<label>状态:</label>
			<input type="radio" name="univer.enabled" value="1" <c:if test="${university.enabled==1}">checked="checked"</c:if> >正常
			<input type="radio" name="univer.enabled" value="0"<c:if test="${university.enabled==0}">checked="checked"</c:if> >停用
		</div>
		<div class="unit">
			<label>属性:</label>
			<input type="radio" group="updateUniversityAttrRadio" name="univer.attr" value="0" <c:if test="${university.attr==0}">checked="checked"</c:if> >本地服务器
			<input type="radio" group="updateUniversityAttrRadio" name="univer.attr" value="1"<c:if test="${university.attr==1}">checked="checked"</c:if> >独立服务器
		</div>
		<div id="updateUniversityIpAndPortDiv" <c:if test="${university.attr==0 }">style="display:none;"</c:if> >
			<div class="unit">
				<label>独立Ip:</label>			
				<input class="ip textInput" type="text" name="univer.ip" value="${university.ip}" >
			</div>
			<div class="unit">
				<label>独立Port:</label>			
				<input class="textInput" type="text" name="univer.port" value="${university.port}">
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
				<div class="buttonCancel" >
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>