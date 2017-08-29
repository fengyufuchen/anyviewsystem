<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
	<li>
		${problem.pname }--${proDir }--
		<c:if test="${problem.kind==0 }">程序题</c:if>
		<c:if test="${problem.kind==1 }">例题</c:if>
		<c:if test="${problem.kind==2 }">填空题</c:if>
		<c:if test="${problem.kind==3 }">单选题</c:if>
		<c:if test="${problem.kind==4 }">多选题</c:if>
		<c:if test="${problem.kind==5 }">判断题</c:if>
	</li>
	<li>
		<span>备注：</span> ${problem.pmemo }
	</li>
	<li>
		<span>提示：</span>${problem.ptip }
	</li>
	<li>
		<span>内容：</span>${problem.pcontent }
	</li>
</ul>