<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table" style="overflow: auto;">
	<thead>
		<tr>
			<th width="199" align="center">作业表</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${schemes }" var="s">
			<tr target="as_vid" rel="${s.vid}">
				<td>
					<a rel="nsSchemeContentDiv" style="color: blue;" target="ajax" href="teacher/schemeManager/schemeContents.action?vid=${s.vid}">${s.vname }</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>