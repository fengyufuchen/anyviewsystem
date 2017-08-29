<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/classTeacherCourseManager.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/classTeacherCourseManager.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="cc.id" value="${cc.id}" />
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
				<a class="icon" href="admin/adminclassManager/classTeacherCourseManager.action?cc.id=${cc.id}" target="navTab" fresh="true" rel="classTeacherCourseManager" title="设定教师">
					<span>刷新</span>
				</a>
			</li>
			<li >
				<a class="add" title="添加教师到课程"  mask="true" target="dialog" fresh="true" height=350 width=650 rel="addTeacherToCourseOnClass" href="admin/adminclassManager/addTeacherToCourseOnClass.action?cc.id=${cc.id}">
					<span>添加 </span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true" target="ajaxTodo" title="确定要删除吗?" href="admin/adminclassManager/deleteTeacherOnCourse.action?ctc.id={tar_ctcid}" warn="请选择一个教师！">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminclassManager/setCtcright.action?ctc.id={tar_ctcid}" warn="请选择一个教师！">
					<span>设置</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="80">
		<thead>
			<tr>
				<th width="5%" align="center" orderField="tea.tname" <c:if test="${orderField=='tea.tname' }">class="${orderDirection}"</c:if> >教师名称*</th>
				<th width="10%" align="center" orderField="co.courseName" <c:if test="${orderField=='co.courseName' }">class="${orderDirection}"</c:if> >课程名称*</th>
				<th width="10%" align="center" orderField="cl.cname" <c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>班级名称*</th>
				<th width="20%" align="center">权限</th>
				<th width="10%" align="center" orderField="updateTime" <c:if test="${orderField=='updateTime' }">class="${orderDirection}"</c:if>>更新时间*</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="ctc">
				<tr target="tar_ctcid" rel="${ctc.id}" >
					<td align="center">${ctc.teacher.tname}</td>
					<td align="center">${ctc.course.courseName }</td>
					<td align="center">${ctc.cla.cname }</td>
					<td align="center">
						<c:if test="${ctc.ctcright==1 || ctc.ctcright==3}">
							<span class="grayspan">批改作业，评分</span>
						</c:if>
						<c:if test="${ctc.ctcright==2 || ctc.ctcright==3}">
							<span class="grayspan">设置作业计划表</span>
						</c:if>
					</td>
					<td align="center">${ctc.updateTime }</td>
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
</div>