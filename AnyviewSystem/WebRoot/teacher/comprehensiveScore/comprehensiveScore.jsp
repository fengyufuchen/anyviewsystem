<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--分页的form-->
<form id="pagerForm" action="teacher/comprehensiveScore/gainCCSchemePage.action" method="post" >	
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
</form>	

<div class="pageHeader">
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a class="icon" fresh="false" rel="studentScores" mask="true" target="navTab" href="teacher/comprehensiveScore/gainStudentScoresPage.action?ccs.id={tar_ccsId}">
					<span>查看做题情况</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
				<th width="20%" align="center">班名</th>
				<th width="6%" align="center">年届</th>
				<th width="20%" align="center">学院</th>
				<th width="10%" align="center">课程</th>
				<th width="10%" align="center">作业表</th>
				<th width="10%" align="center">表类型</th>
				<th width="10%" align="center">布置教师</th>
				<th width="15%" align="center">布置时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="ccs">
				<tr target="tar_ccsId" rel="${ccs.id }">
					<td align="center">${ccs.cla.cname }</td>
					<td align="center">${ccs.cla.startYear }</td>
					<td align="center">${ccs.cla.college.ceName }</td>
					<td align="center">${ccs.course.courseName }</td>
					<td align="center">${ccs.scheme.vname }</td>
					<td align="center">
						<c:if test="${ccs.scheme.kind==0 }">作业题</c:if>
						<c:if test="${ccs.scheme.kind==1 }">考试题</c:if>
						<c:if test="${ccs.scheme.kind==2 }">资源表</c:if>
					</td>
					<td align="center">${ccs.teacher.tname }</td>
					<td align="center">${ccs.updateTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="10">10</option>
					<option value="20" selected="selected">20</option>
					<option value="30">30</option>
					<option value="50">50</option>
					<option value="100">100</option>
			</select>
			<span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>