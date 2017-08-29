<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/schemeManager/updateSchemeContent.action" method="post" novalidate="novalidate">
<input type="hidden" name="schemeContent.id" value="${sc.id }"/>
<input type="hidden" name="schemeContent.scheme.vid" value="${sc.scheme.vid }"/>
<div id="mpnvBaseDivThree" class="pageFormContent" layouth="58" style="height: 100px; overflow: auto;">
	<div class="unit">
		<label>新题目名：</label>
		<input name="schemeContent.vpName" class="required textInput" type="text" value="${sc.vpName }"/>
	</div>
	<div class="unit">
		<label>新虚拟目录名：</label>
		<input name="schemeContent.vchapName" class="required textInput" type="text" value="${sc.vchapName }"/>
	</div>
	<div class="unit">
		<label>允许开始时间：</label>
		<input name="schemeContent.startTime" class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" value="${sc.startTime }">
		<a class="inputDateButton" href="javascript:;">选择</a>
	</div>
	<div class="unit">
		<label>要求完成时间：</label>
		<input name="schemeContent.finishTime" class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" value="${sc.finishTime }">
		<a class="inputDateButton" href="javascript:;">选择</a>
	</div>
	<div class="unit">
		<label>分值：</label>
		<input name="schemeContent.score" class="required textInput" type="text" value="${sc.score }"/>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="buttonActive">
				<div class="buttonContent">
					<button type="submit">确定</button>
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