<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--分页的form-->
<form id="pagerForm" action="admin/classTeacherManager/getClassTeacherManagerPage.action" method="post" >	
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />  
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
</form>	

<div class="pageHeader">
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a class="edit" mask="true" target="navTab" href="admin/classTeacherManager/getClassesForTeacher.action?tid={tid}">
					<span>查看所教授的班级</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
			    <th width="30%" align="center" orderField="tno" <c:if test="${orderField=='tno' }">class="${orderDirection}"</c:if> >教师编号*</th>
				<th width="30%" align="center">教师姓名</th>
				<th width="40%" align="center">属性</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="tea">
				<tr target="tid" rel="${tea.tid }">
					<td align="center">${tea.tid }</td>
					<td align="center">${tea.tname }</td>
					<td align="center">
						<c:choose>
							<c:when test="${tea.tiden == 0 }">普通老师</c:when>
							<c:when test="${tea.tiden == 1 }">学院领导</c:when>
							<c:when test="${tea.tiden == 2 }">学校领导</c:when>
						</c:choose>
					</td>
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