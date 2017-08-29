<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/addTeacherForClass.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return dialogSearch(this);" action="admin/adminclassManager/addTeacherForClass.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="teacher.university.unID" value="${unID}" />
<input type="hidden" name="cla.cid" value="${cid}" />

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
				<a id="addToClass" class="add" href="#" onclick="addTeacherToClass(${cid},'addTeacherForClassCheckbox', 'atc_tcrightCheckbox','dialog', {'teacher.university.unID':${unID },'cla.cid':${cid }})">
					<span>添加到班级</span>
				</a>
			</li>
		</ul>
	</div>
	<table id="addTeacherForClassTable" class="table" width="100%" layoutH="82" targetType="dialog">
		<thead>
			<tr>
				<th width="5%" align="center"><input type="checkbox" onclick="checkAll('addTeacherForClassCheckbox','dialog', this.checked)"/></th>
				<th width="10%" align="center" orderField="tno" <c:if test="${orderField=='tno' }">class="${orderDirection}"</c:if> >教师编号*</th>
				<th width="10%" align="center" orderField="tname" <c:if test="${orderField=='tname' }">class="${orderDirection}"</c:if> >教师姓名*</th>
				<th width="35%" align="center">从属学院</th>
				<th width="5%" align="center">性别</th>
				<th width="30%" align="center">权限</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="t">
				<tr target="teacher" rel="${t.tid}" >
					<td align="center"><input type="checkbox" name="addTeacherForClassCheckbox" value="${t.tid }"/></td>
					<td align="center">${t.tno}</td>
					<td align="center">${t.tname }</td>
					<td align="center">
						<c:forEach items="${t.colleges }" var="cols">
							<span class="grayspan">${cols.ceName }</span>
						</c:forEach>
					</td>
					<td align="center">
						<c:if test="${t.tsex =='M' }">男</c:if>
						<c:if test="${t.tsex =='F' }">女</c:if>
					</td>
					<td align="center">
						<input class="floatLeftInput" type="checkbox" name="atc_tcrightCheckbox" value="1"/>
						<span class="tag_viewStuState" title="在线查看学生状态"></span>
						<input class="floatLeftInput" type="checkbox" name="atc_tcrightCheckbox" value="2"/>
						<span class="tag_setCLassState" title="设置班级状态（考试用）"></span>
						<input class="floatLeftInput" type="checkbox" name="atc_tcrightCheckbox" value="4"/>
						<span class="tag_resetStuPsw" title="重置学生密码"></span>
						<input class="floatLeftInput" type="checkbox" name="atc_tcrightCheckbox" value="8"/>
						<span class="tag_stuManager" title="学生管理（增删改查）"></span>
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
		<div class="pagination" targetType="dialog" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>