<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/updateCourseOnClass.action" method="post" novalidate="novalidate">
	<input type="hidden" name="cc.id" value="${cc.id }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>状态：</label>
			<input type="radio" name="cc.status" value="0" <c:if test="${cc.status==0 }">checked="checked"</c:if> />停用
			<input type="radio" name="cc.status" value="1" <c:if test="${cc.status==1 }">checked="checked"</c:if> />正在使用
		</div>
		<div class="unit">
			<label>开课时间:</label>
			<input type="text" value="${cc.startYear }" readonly="readonly" class="date" style="width:150px;" name="cc.startYear" dateFmt="yyyy-MM-dd HH:mm">
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