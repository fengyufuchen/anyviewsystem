<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 



<!--分页的form-->
<form id="pagerForm" action="admin/collegeManager/getCollegeManagerPage.action" method="post" >	</form>	
<div class="pageHeader">
<!--查询的form-->
<form id="searchUniversity" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/collegeManager/getCollegeManagerPage.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<div class="searchBar">
		<table class="searchContent" style="float: left;">
			<tr>
				<td>
					学院:
                     <input class="college Input" type="text" name="condition.ceName" alt="输入学院名称(可选)" value="${condition.ceName }"/>
				</td>
				<td>
					有效状态:
					<select name="condition.enabled">
						<option value="" <c:if test="${empty condition.enabled }">selected="selected"</c:if>>请选择有效状态</option>
						<option value="0" <c:if test="${condition.enabled==0 }">selected="selected"</c:if> >停用</option>
						<option value="1" <c:if test="${condition.enabled==1 }">selected="selected"</c:if> >正常</option>
					</select>
				</td>
			

				<td class="dateRange" colspan="2">
					创建时间:
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateStart" dateFmt="yyyy-MM-dd HH:mm:ss" value="${createDateStart}">
					<span class="limit">-</span>
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss" value="${createDateEnd}">
				</td>
				<td class="dateRange" colspan="2">
					更新时间:
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateStart" dateFmt="yyyy-MM-dd HH:mm:ss" value="${updateDateStart}">
					<span class="limit">-</span>
					<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss" value="${updateDateEnd}">
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
				<a class="add" mask="true" target="dialog"   fresh="true" rel="admin/collegeManager" href="admin/collegeManager/preAddCollege.action?unID=${college.university.unId}">
					<span>添加学院</span>
				</a>
			</li>
			
			<li >
				<a class="delete" mask="true"  target="dialog"  href="admin/collegeManager/preDeleteCollege.action?ceID={ceID}" warn="请选择一个学院">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/collegeManager/preUpdateCollege.action?ceID={ceID}" warn="请选择一个学院">
					<span>修改</span>
				</a>
			</li>
			<li >		     
				 <a class="icon" mask="true" href="admin/collegeManager/getCollegeManagerPage.action"  target="navTab"  fresh="true"  rel="collegeManager" title="学院管理">
					<span>刷新</span>
				</a>
			</li>
		</ul>
		
	</div>
	
	<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
			    <th width="20%" align="center">学校名称</th>
				<th width="20%" align="center">学院名称</th>
				<th width="20%" align="center">有效状态</th>
		        <th width="20%" align="center">创建时间</th>
				<th width="20%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="college">
				<tr target="ceid" rel="${college.ceID}">
				    <td align="center">${college.university.unName }</td>
					<td align="center">${college.ceName }</td>
					 <td align="center">
					    <c:if test="${college.enabled==0 }">停用</c:if>
					    <c:if test="${college.enabled==1 }">正常</c:if>
					 </td>
					 <td align="center">${college.createTime}</td>
					 <td align="center">${college.updateTime }</td>
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