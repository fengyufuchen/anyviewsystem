<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	$("#universityAdminManagerCeSelect").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
});
</script>

<form id="pagerForm" action="admin/adminManager/getAdminManagerPage.action" method="post"></form>		
<div class="pageHeader">
<!--查询的form-->
<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminManager/getAdminManagerPage.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<div class="searchBar">
		<table class="searchContent" style="float: left;">
			<tr>
				<td>
					学院:
					<select id="universityAdminManagerCeSelect" name="condition.college.ceID" style="width: 150px;">
						<option></option>
						<c:forEach items="${colleges }" var="c">
							<option value="${c[0] }" <c:if test="${condition.college.ceID==c[0] }">selected="selected"</c:if> >${c[1] }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					编号:
					<input type="text" name="condition.mno" value="${condition.mno }"/>
				</td>
				<td>
					有效状态:
					<select name="condition.enabled">
						<option value="" <c:if test="${empty condition.enabled }">selected="selected"</c:if>>请选择有效状态</option>
						<option value="0" <c:if test="${condition.enabled==0 }">selected="selected"</c:if> >停用</option>
						<option value="1" <c:if test="${condition.enabled==1 }">selected="selected"</c:if> >正常</option>
					</select>
				</td>
				<td>
					身份:
					<select name="condition.miden">
						<option value="" <c:if test="${empty condition.miden }">selected="selected"</c:if>>请选择管理员身份</option>
						<option value="-1" <c:if test="${condition.enabled==-1 }">selected="selected"</c:if> >超级管理员</option>
						<option value="1" <c:if test="${condition.enabled==1 }">selected="selected"</c:if> >学校管理员</option>
						<option value="0" <c:if test="${condition.enabled==0 }">selected="selected"</c:if> >学院管理员</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="dateRange" colspan="2">
					创建时间:
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateStart" dateFmt="yyyy-MM-dd HH:mm:ss">
					<span class="limit">-</span>
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss">
				</td>
				<td class="dateRange" colspan="2">
					更新时间:
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateStart" dateFmt="yyyy-MM-dd HH:mm:ss">
					<span class="limit">-</span>
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss">
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
			<li>
				<a class="add" mask="true" target="dialog" fresh="false" rel="addAdmin" href="admin/adminManager/addAdmin.action">
					<span>添加管理员</span>
				</a>
			</li>
			<li>
				<a class="delete"  warn="请选择一个管理员" target="ajaxTodo" title="确定删除吗？"  href="admin/adminManager/deleteAdmin.action?condition.mid={tar_mid}">
					<span>删除</span>
				</a>
			</li>
			<li>
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminManager/updateAdmin.action?condition.mid={tar_mid}" warn="请选择一名管理员">
					<span>修改</span>
				</a>
			</li>
			<li>
				<a class="icon" href="admin/adminManager/getAdminManagerPage.action" target="navTab" fresh="true" rel="adminManager" title="管理员管理">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
				<th width="15%" align="center" orderField="mno" <c:if test="${orderField=='mno' }">class="${orderDirection}"</c:if>>管理员编号（登录名）</th>
				<th width="15%" align="center">学校</th>
				<th width="15%" align="center">学院</th>
				<th width="7%" align="center">身份</th>
				<th width="10%" align="center">有效状态</th>
				<th width="15%" align="center" orderField="createTime" <c:if test="${orderField=='createTime' }">class="${orderDirection}"</c:if>>创建时间</th>
				<th width="15%" align="center" orderField="updateTime" <c:if test="${orderField=='updateTime' }">class="${orderDirection}"</c:if>>更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="admin">
				<tr target="tar_mid" rel="${admin.mid}">
					<td align="center">${admin.mno }</td>
					<td align="center">${admin.university.unName }</td>
					<td align="center">${admin.college.ceName }</td>
					<td align="center">
					    <c:if test="${admin.miden==0 }">学院管理员</c:if>
					    <c:if test="${admin.miden==1 }">学校管理员</c:if>
					    <c:if test="${admin.miden==-1 }">超级管理员</c:if>
					 </td>
					 <td align="center">
					    <c:if test="${admin.enabled==0 }">停用</c:if>
					    <c:if test="${admin.enabled==1 }">正常</c:if>
					 </td>
					 <td align="center">${admin.createTime }</td>
					 <td align="center">${admin.updateTime }</td>
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