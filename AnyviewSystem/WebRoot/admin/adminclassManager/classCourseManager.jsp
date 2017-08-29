<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/classCourseManager.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/classCourseManager.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="cc.cla.cid" value="${condition.cla.cid}" />
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
				<a class="icon" href="admin/adminclassManager/classCourseManager.action?cc.cla.cid=${condition.cla.cid}" target="navTab" fresh="true" rel="classCourseManager" title="班级-课程管理">
					<span>刷新</span>
				</a>
			</li>
			<li >
				<a class="add" title="添加课程到班级"  mask="true" target="dialog" fresh="true" rel="addCourseToClass" href="admin/adminclassManager/addCourseToClass.action?cla.cid=${condition.cla.cid}">
					<span>添加 </span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true" target="ajaxTodo" title="确定要删除吗?" href="admin/adminclassManager/deleteCourseOnClass.action?cc.id={tar_ccid}" warn="请选择一个课程！">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminclassManager/editCourseOnClass.action?cc.id={tar_ccid}" warn="请选择一个课程！">
					<span>设置</span>
				</a>
			</li>
			<li>
				<a class="add" mask="true" target="navTab" fresh="true" rel="classTeacherCourseManager" href="admin/adminclassManager/classTeacherCourseManager.action?cc.id={tar_ccid}" warn="请选择一门课程！">
					<span>设定教师</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="80">
		<thead>
			<tr>
				<th width="5%" align="center" orderField="course.courseId" <c:if test="${orderField=='course.courseId' }">class="${orderDirection}"</c:if> >课程id*</th>
				<th width="10%" align="center" orderField="course.courseName" <c:if test="${orderField=='course.courseName' }">class="${orderDirection}"</c:if> >课程名称*</th>
				<th width="10%" align="center" orderField="course.category" <c:if test="${orderField=='course.category' }">class="${orderDirection}"</c:if>>分类*</th>
				<th width="10%" align="center">学校</th>
				<th width="10%" align="center">学院</th>
				<th width="7%" align="center" title="课程是否有效">课程是否有效</th>
				<th width="5%" align="center" title="课程在此班级中的状态">状态</th>
				<th width="10%" align="center" orderField="startYear" <c:if test="${orderField=='startYear' }">class="${orderDirection}"</c:if>>开课时间*</th>
				<th width="10%" align="center" title="开设此门课程的教师">开设教师*</th>
				<th width="10%" align="center" orderField="updateTime" <c:if test="${orderField=='updateTime' }">class="${orderDirection}"</c:if>>更新时间*</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="cc">
				<tr target="tar_ccid" rel="${cc.id}" >
					<td align="center">${cc.course.courseId}</td>
					<td align="center">${cc.course.courseName }</td>
					<td align="center">${cc.course.category}</td>
					<td align="center">${cc.course.university.unName }</td>
					<td align="center">${cc.course.college.ceName }</td>
					<td align="center">
						<c:if test="${cc.course.enabled ==0 }">停用</c:if>
						<c:if test="${cc.course.enabled ==1}">正常</c:if>
					</td>
					<td align="center">
						<c:if test="${cc.status ==0 }">停用</c:if>
						<c:if test="${cc.status ==1}">正在使用</c:if>
					</td>
					<td align="center">${cc.startYear }</td>
					<td align="center">
						<c:choose>
							<c:when test="${empty cc.teacher }">管理员</c:when>
							<c:otherwise>${cc.teacher.tname }</c:otherwise>
						</c:choose>
					</td>
					<td align="center">${cc.updateTime }</td>
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