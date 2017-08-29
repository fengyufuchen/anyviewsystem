<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return divSearch(this, 'problemBox');" action="teacher/problemManager/problemManager.action" method="post">
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${problemPage.numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />  
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="problem.problemChap.chId" value="${problem.problemChap.chId }" />
	<%-- <div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					题目名：<input type="text" name="problem.pname" />
				</td>
				<td>
					访问级别：
					<select name="problem.visit">
						<option>选择访问级别</option>
						<option value="0">私有</option>
						<option value="1">公开</option>
					</select>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div> --%>
	</form>
</div>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="icon" fresh="false" rel="lookProblem" title="查看题目" target="dialog" href="teacher/problemManager/lookProblem.action?pid={tar_pid}"> 
					<span>查看</span> 
				</a>
				<li>
				<a class="add" fresh="false" rel="addProblem" title="添加题目" target="dialog" width="800" height="500" href="teacher/problemLibManage/problemAndChapManage/addProblem.jsp?chId=${problem.problemChap.chId }&lid=${problem.problemChap.problemLib.lid}"> 
					<span>添加</span>
				</a>
				<a class="delete" warn="请选择一个题目" target="ajaxTodo" title="需清空相关的学生作业答案才能删除！确定删除吗？" 
				href="teacher/problemManager/deleteProblem.action?problem.pid={tar_pid}&problemChap.problemLib.lid=${problemChap.problemLib.lid}&problemChap.parentChap.chId=${problemChap.parentChap.chId}"> 
					<span>删除</span>
				</a>
				<a class="edit" warn="请选择一个题目" fresh="false" rel="eidtProblem" target="dialog" width="800" height="500" title="编辑题目" href="teacher/problemManager/editProblem.action?problem.pid={tar_pid}"> 
					<span>修改</span>
				</a>
			</li>
			</li>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="477" rel="problemBox">
		<thead>
			<tr>
				<th width="10%" align="center">题目名</th>
				<th width="10%" align="center">难度</th>
				<th width="10%" align="center">类型</th>
				<th width="10%" align="center">状态</th>
				<th width="10%" align="center">访问级别</th>
				<th width="10%" align="center">简介</th>
				<th width="10%" align="center">创建时间</th>
				<th width="10%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${problemPage.content }" var="pro">
				<tr target="tar_pid" rel="${pro.pid }">
					<td align="center">${pro.pname }</td>
					<td align="center">${pro.degree }</td>
					<td align="center">
						<c:if test="${pro.kind==0 }">程序题</c:if>
						<c:if test="${pro.kind==1 }">例题</c:if>
						<c:if test="${pro.kind==2 }">填空题</c:if>
						<c:if test="${pro.kind==3 }">单选题</c:if>
						<c:if test="${pro.kind==4 }">多选题</c:if>
						<c:if test="${pro.kind==5 }">判断题</c:if>
					</td>
					
					<td align="center">
						<c:if test="${pro.status==0 }">停用</c:if>
						<c:if test="${pro.status==1 }">测试</c:if>
						<c:if test="${pro.status==2 }">公开</c:if>
					</td>
					<td align="center">
						<c:if test="${pro.visit==0 }">私有</c:if>
						<c:if test="${pro.visit==1 }">公开</c:if>
					</td>
					<td align="center">${pro.pmemo }</td>
					<td align="center">${pro.createTime }</td>
					<td align="center">${pro.updateTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value}, 'problemBox')">
				<option value="10" <c:if test="${problemPage.numPerPage==10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${problemPage.numPerPage==20}">selected="selected"</c:if>>20</option>
				<option value="30" <c:if test="${problemPage.numPerPage==30}">selected="selected"</c:if>>30</option>
				<option value="50" <c:if test="${problemPage.numPerPage==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${problemPage.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select>
			<span> 条，共${problemPage.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" rel="problemBox" targetType="navTab" totalCount="${problemPage.totalCount }" numPerPage="${problemPage.numPerPage }" pageNumShown="${problemPage.totalPageNum }" currentPage="${problemPage.currentPage }"></div>
	</div>
</div>