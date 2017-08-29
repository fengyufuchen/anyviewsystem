<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table" width="99%" layoutH="215" rel="choosedProBox">
	<thead>
		<tr>
			<th width="10%" align="center">原题目名称</th>
			<th width="10%" align="center">新题目名称</th>
			<th width="5%" align="center">难度</th>
			<th width="10%" align="center">题目类型</th>
			<th width="20%" align="center">原所属目录</th>
			<th width="20%" align="center">新虚拟目录</th>
			<th width="10%" align="center">所属题库</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${choosedPros }" var="pro">
		<tr target="tarChoosedPid" rel="${pro.pid }">
			<td align="center">${pro.pname }</td>
			<td align="center"></td>
			<td align="center">${pro.degree }</td>
			<td align="center">${pro.kind }</td>
			<td align="center">${pro.problemChap.chName }</td>
			<td align="center">${pro.problemChap.problemLib.lname }</td>
			<td align="center">${pro.updateTime }</td>	
		</tr>
	</c:forEach>
	</tbody>
</table>