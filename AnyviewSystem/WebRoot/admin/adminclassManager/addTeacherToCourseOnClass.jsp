<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/addTeacherToCourseOnClass.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return dialogSearch(this);" action="admin/adminclassManager/addTeacherToCourseOnClass.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="cc.id" value="${claco.id}" />
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
				<a id="addToCourse" class="add" href="#" onclick="addTeacherToCourseOnClass(${claco.id},'addTeacherToCourseOnClassCheckbox', 'atcc_ctcrightCheckBox','dialog', {'cc.id':${claco.id }})">
					<span>添加到课程</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="80" targetType="dialog">
		<thead>
			<tr>
				<th width="5%" align="center"><input type="checkbox" onclick="checkAll('addTeacherToCourseOnClassCheckbox','dialog', this.checked)"/></th>
				<th width="7%" align="center" orderField="teacher.tno" <c:if test="${orderField=='teacher.tno' }">class="${orderDirection}"</c:if> >教师编号*</th>
				<th width="10%" align="center" orderField="teacher.tname" <c:if test="${orderField=='teacher.tname' }">class="${orderDirection}"</c:if> >教师姓名*</th>
				<th width="7%" align="center">性别</th>
				<th width="30%" align="center">班级权限</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="ct">
				<tr target="tar_tid" rel="${ct.teacher.tid}" >
					<td align="center"><input type="checkbox" name="addTeacherToCourseOnClassCheckbox" value="${ct.teacher.tid }"/></td>
					<td align="center">${ct.teacher.tno}</td>
					<td align="center">${ct.teacher.tname }</td>
					<td align="center">${ct.teacher.tsex }</td>
					<td align="center">
						<input type="checkbox" class="floatLeftInput" name="atcc_ctcrightCheckBox" value="1"/>
						<span class="grayspan">批改作业，评分</span>
						<input type="checkbox" class="floatLeftInput" name="atcc_ctcrightCheckBox" value="2"/>
						<span class="grayspan">设置作业计划表</span>
					</td>
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
		<div class="pagination"  targetType="dialog" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>