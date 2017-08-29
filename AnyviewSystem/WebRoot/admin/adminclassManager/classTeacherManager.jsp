<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/classTeacherManager.action" method="post" ></form>

<div class="pageHeader">
<form id="searchForm" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/classTeacherManager.action" method="post">
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
<input type="hidden" name="orderField" value="${orderField}" />  
<input type="hidden" name="orderDirection" value="${orderDirection}" />
<input type="hidden" name="ct.cla.cid" value="${condition.cla.cid}" />
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
				<a class="icon" href="admin/adminclassManager/classTeacherManager.action?ct.cla.cid=${condition.cla.cid}" target="navTab" fresh="true" rel="classTeacherManager" title="班级-教师管理">
					<span>刷新</span>
				</a>
			</li>
			<li >
				<a class="add" title="添加教师到班级"  mask="true" target="dialog" fresh="true" height=380 width=820 rel="addTeacherForClass" href="admin/adminclassManager/addTeacherForClass.action?teacher.university.unID=${condition.cla.college.university.unID }&cla.cid=${condition.cla.cid}">
					<span>添加 </span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true" target="ajaxTodo" title="确定要删除吗?" href="admin/adminclassManager/deleteTeacherOnClass.action?teacher.tid={tar_tid}&cla.cid=${condition.cla.cid }" warn="请选择一个教师！">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminclassManager/editTeacherRightOnClass.action?cla.cid=${condition.cla.cid }&teacher.tid={tar_tid}" warn="请选择一个教师！">
					<span>设置权限</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="80">
		<thead>
			<tr>
				<th width="10%" align="center" orderField="teacher.tno" <c:if test="${orderField=='teacher.tno' }">class="${orderDirection}"</c:if> >教师编号*</th>
				<th width="10%" align="center" orderField="teacher.tname" <c:if test="${orderField=='teacher.tname' }">class="${orderDirection}"</c:if> >教师姓名*</th>
				<th width="15%" align="center">学校</th>
				<th width="30%" align="center">从属学院</th>
				<th width="7%" align="center">性别</th>
				<th width="7%" align="center">是否有效</th>
				<th width="15%" align="center">班级权限</th>
				<th width="20%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="ct">
				<tr target="tar_tid" rel="${ct.teacher.tid}" >
					<td align="center">${ct.teacher.tno}</td>
					<td align="center">${ct.teacher.tname }</td>
					<td align="center">${ct.teacher.university.unName }</td>
					<td align="center">
						<c:forEach items="${ct.teacher.colleges }" var="cols">
							<span class="grayspan">${cols.ceName }</span>
						</c:forEach>
					</td>
					<td align="center">
						<c:if test="${ct.teacher.tsex =='M' }">男</c:if>
						<c:if test="${ct.teacher.tsex =='F' }">女</c:if>
					</td>
					<td align="center">
						<c:if test="${ct.teacher.enabled ==0 }">无</c:if>
						<c:if test="${ct.teacher.enabled ==1}">有</c:if>
					</td>
					<td align="center">
						<c:if test="${ct.tcRight%2==1}">
							<span class="tag_viewStuState" title="在线查看学生状态"></span>
						</c:if>
						<c:if test="${ct.tcRight==2||ct.tcRight==3||ct.tcRight==6||ct.tcRight==7||ct.tcRight==10||ct.tcRight==11||ct.tcRight==14||ct.tcRight==15}">
							<span class="tag_setCLassState" title="设置班级状态（考试用）"></span>
						</c:if>
						<c:if test="${ct.tcRight==4||ct.tcRight==5||ct.tcRight==6||ct.tcRight==7||ct.tcRight==12||ct.tcRight==13||ct.tcRight==14||ct.tcRight==15}">
							<span class="tag_resetStuPsw" title="重置学生密码"></span>
						</c:if>
						<c:if test="${ct.tcRight>=8}">
							<span class="tag_stuManager" title="学生管理（增删改查）"></span>
						</c:if>
					</td>
					<td align="center">
						${ct.updateTime}
					</td>
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