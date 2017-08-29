<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- actionmessage  -->
<s:if test="hasActionMessages()"> 
<s:iterator value="actionMessages"> 
<script language="JavaScript"> 
alert("<s:property escape="false"/>") 
</script> 
</s:iterator>
<s:if test="clearActionErrors()"></s:if>
<s:if test="clearMessages()"></s:if>
</s:if>

<!-- actionerror  -->
<s:if test="hasActionErrors()"> 
<s:iterator value="actionErrors"> 
<script language="JavaScript"> 
alert("<s:property escape="false"/>") 
</script> 
</s:iterator> 
<s:if test="clearActionErrors()"></s:if>
</s:if>
<html>
	<head>
		<title>错误页面</title>
		<meta http-equiv="Content-Language" content="zh-cn">
	</head>
	<body>
	</body>
</html>
