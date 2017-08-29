<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/saveCtcright.action" method="post" novalidate="novalidate">
	<input type="hidden" name="ctc.id" value="${ctc.id }"/>
	
	<div class="pageFormContent" layouth="58">	
		<div class="unit">
			<input type="checkbox" class="floatLeftInput" name="ctcright" value="1" <c:if test="${ctc.ctcright==1 || ctc.ctcright==3 }">checked="checked"</c:if> />
			<span class="grayspan">批改作业，评分</span>
			<input type="checkbox" class="floatLeftInput" name="ctcright" value="2" <c:if test="${ctc.ctcright==2 || ctc.ctcright==3 }">checked="checked"</c:if> />
			<span class="grayspan">设置作业计划表</span>
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