<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>题库访问级别:</label>
		<c:choose>
			<c:when test="${problemLib.visit==0 }">私有</c:when>
			<c:when test="${problemLib.visit==1 }">部分公开</c:when>
			<c:when test="${problemLib.visit==2 }">本学院公开</c:when>
			<c:when test="${problemLib.visit==3 }">本学校公开</c:when>
			<c:when test="${problemLib.visit==4 }">完全公开</c:when>
		</c:choose>
	</div>
	<div class="unit">
		<c:choose>
			<c:when test="${problemLib.visit==1 }">
				<label>可访问教师:</label>
				<div class="showAccessTeacherDiv">
					<c:forEach items="${teachers }" var="t">
						<span>${t.university.unName }-->${t.tname }</span><br/>
					</c:forEach>
				</div>
			</c:when>
			<c:when test="${problemLib.visit==0 }">
				<label>可访问教师:</label>
				<span>${problemLib.teacher.tname }</span>
			</c:when>
			<c:when test="${problemLib.visit==2 }">
				<label>可访问学院:</label>
				<span>${problemLib.college.ceName }</span>
			</c:when>
			<c:when test="${problemLib.visit==2 }">
				<label>可访问学校:</label>
				<span>${problemLib.university.unName }</span>
			</c:when>
		</c:choose>						
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button class="close" type="button">关闭</button>
				</div>
			</div>
		</li>
	</ul>
</div>
