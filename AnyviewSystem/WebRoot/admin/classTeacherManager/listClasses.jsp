<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!-- 分页的form -->
<form id="pagerForm" action="admin/classTeacherManager/getClassesForTeacher.action" method="post">
	<input type="hidden" name="tid" id="tid" value="${tid }"/>
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
				<a class="edit" mask="true" target="dialog" href="admin/classTeacherManager/editClassTeacherRight.jsp?tid=${tid }&cid={cid}">
					<span>编辑相应班级权限</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
				<th width="25%" align="center" orderField="cname" <c:if test="${orderField=='cname' }">class="${orderDirection}"</c:if> >班名*</th>
				<th width="25%" align="center">所在专业</th>
				<th width="25%" align="center">属性</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="cla" >
                <tr target="cid" rel="${cla.cid }">
					<td align="center">${cla.cname}</td>
					<td align="center">${cla.specialty}</td>
<%-- 					<td align="center">${tname }</td> --%>
					<td align="center">
						<c:choose>
							<c:when test="${cla.kind == 0 }">普通班级</c:when>
							<c:when test="${cla.kind == 1 }">教师映射班级</c:when>
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