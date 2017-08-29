<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table" style="overflow: auto;">
	<thead>
		<tr>
			<th width="199" align="center">题库</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${libs }" var="s">
			<tr target="as_lid" rel="${s.lid}">
				<td>${s.lname }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>