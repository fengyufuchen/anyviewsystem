<%@page import="com.sun.corba.se.impl.oa.poa.AOMEntry"%>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.anyview.entities.*,java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--分页的form-->
<form id="pagerForm"
	action="teacher/combinedGrade/listStudentExerciseDetail.action"
	method="post"></form>
<div class="pageHeader"></div>

<div class="pageContent">

	<table class="table" width="100%" layoutH="82">
		<thead>
			<tr>
				<th width="10%" align="center" orderField="cl.cname" <c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>所用时间*</th>
				<th width="10%" align="center" orderField="cl.college.university.unName" <c:if test="${orderField=='cl.college.university.unName' }">class="${orderDirection}"</c:if>>得分*</th>
				<th width="10%" align="center" orderField="cl.college.ceName" <c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>运行结果*</th>
				<th width="10%" align="center" orderField="cl.specialty" <c:if test="${orderField=='cl.specialty' }">class="${orderDirection}"</c:if>>运行错误次数*</th>
				<th width="7%" align="center" orderField="cl.startYear" <c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>编译次数*</th>
				<th width="7%" align="center">编译正确次数</th>
				<th width="7%" align="center">编译错误次数</th>
				<th width="7%" align="center">首次通过时间</th>
				<th width="7%" align="center">最后提交时间</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="e">
				<tr target="" rel="" >
					<td align="center">${e.accumTime }分钟</td>
					<td align="center">${e.score }分</td>
					<td align="center">
						<c:if test="${e.runResult == 0 }">通过</c:if>
						<c:if test="${e.runResult == 1 }">未通过</c:if>
					</td>					
					<td align="center">${e.runErrCount }</td>
					<td align="center">${e.cmpCount }</td>
					<td align="center">${e.cmpRightCount}</td>
					<td align="center">${e.cmpErrorCount }</td>
					<td align="center">${e.firstPastTime }</td>
					<td align="center">${e.lastTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numperPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"
					<c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
				<option value="20"
					<c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
				<option value="30"
					<c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
				<option value="50"
					<c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
				<option value="100"
					<c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select> <span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab"
			totalCount="${page.totalCount }" numPerPage="${page.numPerPage }"
			pageNumShown="${page.totalPageNum }"
			currentPage="${page.currentPage }"></div>
	</div>
</div>