<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/problemManager/saveProblemChap.action" method="post" novalidate="novalidate">
	<input type="hidden" name="problemChap.parentChap.chId" value="${param.chId }"/>
	<input type="hidden" name="problemChap.problemLib.lid" value="${param.lid }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>目录名称：</label>
			<input class="required textInput" type="text"  name="problemChap.chName"/>
		</div>
		<div class="unit">
			<label>访问级别:</label>
			<select id="libVisitSelect" class="required combox" name="problemChap.visit">
				<option value="0">私有</option>
				<option value="1" selected="selected">公开</option>
			</select>
		</div>
		<div class="unit">
			<label>说明:</label>
			<textarea class="required textInput" cols="50" rows="5" name="problemChap.memo"></textarea>
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