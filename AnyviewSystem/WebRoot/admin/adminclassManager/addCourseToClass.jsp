<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/addCourseToClass.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return dialogSearch(this);" action="admin/adminclassManager/addCourseToClass.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="cla.cid" value="${cla.cid}" />

<div class="searchBar">
	<table class="searchContent">
		<tbody>
		</tbody>
	</table>
</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a id="addToClass" class="add" href="#" onclick="addCourseToClass(${cla.cid},'addCourseToClassCheckbox','dialog', {'cla.cid':${cla.cid }})">
					<span>添加到班级</span>
				</a>
			</li>
		</ul>
	</div>
	<table id="addTeacherForClassTable" class="table" width="100%" layoutH="82" targetType="dialog">
		<thead>
			<tr>
				<th width="5%" align="center"><input type="checkbox" onclick="checkAll('addCourseToClassCheckbox','dialog', this.checked)"/></th>
				<th width="7%" align="center" orderField="co.courseId" <c:if test="${orderField=='co.courseId' }">class="${orderDirection}"</c:if> >课程Id*</th>
				<th width="10%" align="center" orderField="co.courseName" <c:if test="${orderField=='co.courseName' }">class="${orderDirection}"</c:if> >课程名称*</th>
				<th width="10%" align="center" orderField="co.category" <c:if test="${orderField=='co.category' }">class="${orderDirection}"</c:if>>分类*</th>
				<th width="10%" align="center">学校</th>
				<th width="10%" align="center">学院</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="c">
				<tr target="tar_courseId" rel="${c.courseId}" >
					<td align="center"><input type="checkbox" name="addCourseToClassCheckbox" value="${c.courseId }"/></td>
					<td align="center">${c.courseId}</td>
					<td align="center">${c.courseName }</td>
					<td align="center">${c.category }</td>
					<td align="center">${c.university.unName }</td>
					<td align="center">${c.college.ceName }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="dialogPageBreak({numPerPage:this.value})">
					<option value="10" <c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
					<option value="20" <c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
					<option value="30" <c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
					<option value="50" <c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
					<option value="100" <c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select>
			<span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="dialog" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>