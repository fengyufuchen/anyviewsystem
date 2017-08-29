<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
var pro = $("#proTab");
</script>


<form id="pagerForm" action="teacher/problemManager/getProblemsByChAjax.action" method="post" >
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	<input type="hidden" name="ids" value="${ids }"/>
	<input type="hidden" name="kind" value="${kind }"/>
</form>
<table id="proTab" class="table" width="99%" layoutH="205" rel="jbsxBox">
	<thead>
		<tr>
			<th width="3%" align="center"><input type="checkbox" /> </th>
			<th width="10%" align="center">题目名称</th>
			<th width="5%" align="center">难度</th>
			<th width="10%" align="center">题目类型</th>
			<th width="20%" align="center">所属目录</th>
			<th width="10%" align="center">所属题库</th>
			<th width="7%" align="center">更新时间</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.content }" var="pro">
		<tr target="tarpid" rel="${pro.pid }">
			<td align="center"><input name="pidChkTwo" type="checkbox" value="${pro.pid }"/></td>
			<td align="center">${pro.pname }</td>
			<td align="center">${pro.degree }</td>
			<td align="center">${pro.kind }</td>
			<td align="center">${pro.problemChap.chName }</td>
			<td align="center">${pro.problemChap.problemLib.lname }</td>
			<td align="center">${pro.updateTime }</td>	
		</tr>
	</c:forEach>
	</tbody>
</table>

<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value})">
			<option value="10" <c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
			<option value="20" <c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
			<option value="30" <c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
			<option value="50" <c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
			<option value="100" <c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
		</select>
		<span> 条，共${page.totalCount }条</span>
	</div>
	<!--分页组件-->
	<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
</div>