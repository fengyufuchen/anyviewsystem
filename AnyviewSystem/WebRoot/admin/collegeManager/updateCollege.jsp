<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：updateCollege.jsp-->
<!--描   述：修改学院页面-->
<!--时   间 ：2015年09月13日-->

<form  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" rel="admin/collegeManager" action="admin/collegeManager/updateCollege.action" method="post" novalidate="novalidate">
	<input type="hidden" name="ceID" id="ceID" value="${college.ceID }" />
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>学校名称：</label>
			<input type="text" disabled="true"  name="unName" value=${unName }>
		</div>
		<div class="unit">
			<label>学院名称：</label>
			<input class="required textInput" type="text"  name="college.ceName" value=${college.ceName }>
		</div>
		<div class="unit">
			<label>有效性:</label>
			<input type="radio" name="college.enabled" value="1" <c:if test="${college.enabled==1}">checked="checked"</c:if> >正常
			<input type="radio" name="college.enabled" value="0"<c:if test="${college.enabled==0}">checked="checked"</c:if> >停用
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
				<div class="button" 
				>
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>