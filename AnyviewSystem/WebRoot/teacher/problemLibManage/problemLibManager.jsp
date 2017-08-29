<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--分页的form-->
<form id="pagerForm"
	action="teacher/problemLibManager/gainProblemLibManagerPage.action"
	method="post"></form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);"
		action="teacher/problemLibManager/gainProblemLibManagerPage.action"
		method="post">
		<div class="searchBar">
			<table class="searchContent" style="float: left;">
				<tr>
					<td>题库名: <input name="problemLib.lname" class="textInput"
						type="text" placeholder="输入计划表名(可选)" value="${criteria.lname }" />
					</td>
					<td>类别: <input class="textInput" name="problemLib.kind"
						type="text" value="${criteria.kind }" placeholder="输入题库类别(可选)" />
					</td>
					<td><select class="combox" style="width: 150px"
						name="problemLib.visit">
							<option value=""
								<c:if test="${empty criteria.visit }">selected="selected"</c:if>>所有级别</option>
							<option value="0"
								<c:if test="${criteria.visit == 0 }">selected="selected"</c:if>>教师私有
							</option>
							<option value="1"
								<c:if test="${criteria.visit == 1 }">selected="selected"</c:if>>部分公开</option>
							<option value="2"
								<c:if test="${criteria.visit == 2 }">selected="selected"</c:if>>学院公开</option>
							<option value="3"
								<c:if test="${criteria.visit == 3 }">selected="selected"</c:if>>学校公开</option>
							<option value="4"
								<c:if test="${criteria.visit == 4 }">selected="selected"</c:if>>完全公开</option>
					</select></td>
				</tr>
			</table>

			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
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
			<li><a class="icon" title="题库" rel="problemLibManager"
				target="navTab"
				href="teacher/problemLibManager/gainProblemLibManagerPage.action">
					<span>刷新</span>
			</a></li>
			<%-- <li>
				<a class="icon" fresh="false" title="查看可访问人" rel="accessTeachers" target="dialog" href="teacher/problemLibManager/accessTeachers.action?lid={tar_lid}" warn="请选择一个题库">
					<span>查看访问信息</span> 
				</a>
			</li> --%>
			<li><a class="add" fresh="false" rel="addProblemLib"
				title="创建题库" target="dialog"
				href="teacher/problemLibManage/addProblemLib.jsp"> <span>添加题库</span>
			</a></li>
			<li><a class="edit" fresh="false" warn="请选择一个题库"
				rel="editProblemLib" title="设置题库" target="dialog"
				href="teacher/problemLibManager/editProblemLib.action?problemLib.lid={tar_lid}">
					<span>设置</span>
			</a></li>
			<li><a class="delete" warn="请选择一个题库" target="ajaxTodo"
				title="需清空题库下所有的题目目录才能删除！确定删除吗？"
				href="teacher/problemLibManager/deleteProblemLib.action?problemLib.lid={tar_lid}">
					<span>删除题库</span>
			</a></li>
		</ul>
	</div>

	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="10%" align="center">题库名</th>
				<th width="10%" align="center">创建教师</th>
				<th width="20%" align="center">创建教师所在学校</th>
				<th width="7%" align="center">访问级别</th>
				<th width="7%" align="center">类别</th>
				<th width="10%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="lib">
				<tr target="tar_lid" rel="${lib.lid }">
					<td align="center"><a class="icon" warn="请选择一个题库"
						rel="problemAndChapManager" title="题目管理" target="navTab"
						href="teacher/problemManager/listChapAndProblem.action?problemChap.problemLib.lid=${lib.lid }&problemChap.chId=-1">
							<span>${lib.lname }</span>
					</a></td>
					<td align="center">${lib.teacher.tname }</td>
					<td align="center">${lib.university.unName }</td>
					<td align="center"><c:if test="${lib.visit == 0 }">私有</c:if> <c:if
							test="${lib.visit == 1 }">部分公开</c:if> <c:if
							test="${lib.visit == 2 }">本学院公开</c:if> <c:if
							test="${lib.visit == 3 }">本校公开</c:if> <c:if
							test="${lib.visit == 4 }">完全公开</c:if></td>
					<td align="center">${lib.kind }</td>
					<td align="center">${lib.updateTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numperPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"
					<c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
				<option value="20"
					<c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
				<option value="30"
					<c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
				<option value="50"
					<c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
				<option value="100"
					<c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select> <span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab"
			totalCount="${page.totalCount }" numPerPage="${page.numPerPage }"
			pageNumShown="${page.totalPageNum }"
			currentPage="${page.currentPage }"></div>
	</div>
</div>