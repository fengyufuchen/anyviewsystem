<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：semesterManager.jsp-->
<!--描   述：学期管理页面-->
<!--时   间 ：2015年08月04日-->

<!--分页的form-->
<form id="pagerForm" action="admin/semesterManager/getSemesterManagerPage.action" method="post" >	
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
</form>		


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a class="add" mask="true" target="dialog" fresh="true" rel="semesterManager" href="admin/semesterManager/addsemester.jsp">
					<span>添加学期</span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true" target="ajaxTodo" title="确定要删除吗?" href="admin/semesterManager/deleteSemester.action?sid={sid}">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/semesterManager/updatesemester.jsp?sid={sid}">
					<span>修改</span>
				</a>
			</li>
			<li >
				<a class="icon" href="admin/semesterManager/getSemesterManagerPage.action" target="navTab" fresh="true" rel="semesterManager" title="学期管理">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th width="80" align="center">学期名</th>
				<th width="90" align="center">起始时间</th>
				<th width="90" align="center">结束时间</th>
				<th width="90" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="sem">
				<tr target="sid" rel="${sem.sid}">
					<td align="center">${sem.sname }</td>
					<td align="center">${sem.startTime }</td>
					<td align="center">${sem.endTime }</td>
					<td align="center">${sem.updateTime }</td>
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