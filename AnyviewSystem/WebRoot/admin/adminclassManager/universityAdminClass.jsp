<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：universityAdminClass.jsp-->
<!--描   述：校级管理员的班级管理页面-->
<!--时   间 ：2015年09月05日-->
<script type="text/javascript">
$(function(){
	$("#universityAdminClassManagerCeSelect").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
});
</script>

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/getAdminClassManagerPage.action" method="post" ></form>		

<div class="pageHeader">
<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/getAdminClassManagerPage.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<div class="searchBar">
		<table class="searchContent" style="float: left;">
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;院：
					<select id="universityAdminClassManagerCeSelect" name="condition.college.ceID" style="width: 150px;">
						<option></option>
						<c:forEach items="${colleges }" var="c">
							<option value="${c[0] }" <c:if test="${condition.college.ceID==c[0] }">selected="selected"</c:if> >${c[1] }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="searchBtn">
								检索
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</form>
</div>

<div class="pageContent">
	
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a class="add" mask="true" target="dialog" fresh="true" rel="adminclassManager" href="admin/adminclassManager/addClass.action">
					<span>添加</span>
				</a>
			</li>
			<li >
				<a class="add" mask="true" target="navTab" fresh="true" rel="listStudent" href="admin/adminstudentManager/getListStudentPage.action?cid={cid}" warn="请选择一个班级！">
					<span>关联学生</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminclassManager/editClass.action?cid={cid}" warn="请选择一个班级！">
					<span>修改</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="navTab" fresh="true" rel="lookStudent" href="admin/adminclassManager/getLookStudentPage.action?cs.cla.cid={cid}" warn="请选择一个班级！">
					<span>查看学生</span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true" target="ajaxTodo" title="确定要删除吗?" href="admin/adminclassManager/deleteClass.action?cid={cid}" warn="请选择一个班级！">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="icon" href="admin/adminclassManager/getAdminClassManagerPage.action" target="navTab" fresh="true" rel="adminclassManager" title="班级管理">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="112">
		<thead>
			<tr>
				<th width="11%" align="center">学校</th>
				<th width="11%" align="center">学院</th>
				<th width="11%" align="center">专业</th>
				<th width="11%" align="center" orderField="cname" <c:if test="${orderField=='cname' }">class="${orderDirection}"</c:if> >班级*</th>
				<th width="8%" align="center" orderField="startYear" <c:if test="${orderField=='startYear' }">class="${orderDirection}"</c:if> >年届*</th>
				<th width="8%" align="center">类型</th>
				<th width="8%" align="center">是否有效</th>
				<th width="8%" align="center">班级状态</th>
				<th width="12%" align="center">创建时间</th>
				<th width="12%" align="center">更新时间</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="cla">
				<tr target="cid" rel="${cla.cid}">
					<td align="center">${cla.college.university.unName }</td>
					<td align="center">${cla.college.ceName }</td>
					<td align="center">${cla.specialty }</td>
					<td align="center">${cla.cname }</td>
					<td align="center">${cla.startYear }</td>
					<td align="center">
						<c:if test="${cla.kind == 0 }">普通班级</c:if>
						<c:if test="${cla.kind == 1 }">教室映射班级</c:if>
					</td>
					<td align="center">
						<c:if test="${cla.enabled == 0 }">停用</c:if>
						<c:if test="${cla.enabled == 1 }">正常</c:if>
					</td>
					<td align="center">
						<c:if test="${cla.status == 0 }">未锁定</c:if>
						<c:if test="${cla.status == 1 }">登录锁定</c:if>
						<c:if test="${cla.status == 2 }">做题锁定</c:if>
						<c:if test="${cla.status == 3 }">考试锁定</c:if>
					</td>
					<td align="center">${cla.createTime }</td>
					<td align="center">${cla.updateTime }</td>
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