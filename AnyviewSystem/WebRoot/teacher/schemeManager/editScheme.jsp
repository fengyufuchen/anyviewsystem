<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/schemeManager/updateScheme.action" method="post" novalidate="novalidate">
<input type="hidden" name="scheme.vid" value="${scheme.vid }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>计划表名：</label>
			<input class="required textInput" type="text"  name="scheme.vname" value="${scheme.vname }"/>
		</div>
		<div class="unit">
			<label>课程:</label>
			<select class="required combox" name="scheme.course.courseId">
				<c:forEach items="${courses }" var="c">
					<option value="${c.courseId }" <c:if test="${scheme.course.courseId==c.courseId }">selected="selected"</c:if> >${c.courseName }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>类型:</label>
			<select class="required combox" name="scheme.kind">
				<option value="0" <c:if test="${scheme.kind==0 }">selected="selected"</c:if>>作业题</option>
				<option value="1" <c:if test="${scheme.kind==1 }">selected="selected"</c:if>>考试题</option>
				<option value="2" <c:if test="${scheme.kind==2 }">selected="selected"</c:if>>资源表</option>
			</select>
		</div>
		<div class="unit">
			<label>状态:</label>
			<select class="required combox" name="scheme.status">
				<option value="0" <c:if test="${scheme.status==0 }">selected="selected"</c:if>>停用</option>
				<option value="1" <c:if test="${scheme.status==1 }">selected="selected"</c:if>>测试</option>
				<option value="2" <c:if test="${scheme.status==2 }">selected="selected"</c:if>>公开</option>
			</select>
		</div>
		<div class="unit">
			<label>访问级别:</label>
			<select class="required combox" name="scheme.visit">
				<option value="0" <c:if test="${scheme.visit==0 }">selected="selected"</c:if>>私有</option>
				<option value="1" <c:if test="${scheme.visit==1 }">selected="selected"</c:if>>部分公开</option>
				<option value="2" <c:if test="${scheme.visit==2 }">selected="selected"</c:if>>本学院公开</option>
				<option value="3" <c:if test="${scheme.visit==3 }">selected="selected"</c:if>>本校公开</option>
				<option value="4" <c:if test="${scheme.visit==4 }">selected="selected"</c:if>>完全公开</option>
			</select>
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