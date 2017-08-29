<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	
})
</script>
<!--分页的form-->
<form id="pagerForm" action="teacher/schemeManager/lookSchemeProblems.action" method="post" ></form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/schemeManager/lookSchemeProblems.action" method="post">
		<input type="hidden" name="vid" value="${vid }" />
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
		<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />  
    	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	</form>
</div>
			
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="icon" title="管理题目" rel="lookSchemeProblems" target="navTab" href="teacher/schemeManager/lookSchemeProblems.action?vid=${vid }"> 
					<span>刷新</span> 
				</a>
			</li>
			<li>
				<a class="add" fresh="true" rel="addSchemeProblems" target="navTab" href="teacher/schemeManager/addProblemForScheme.action?vid=${vid }"> 
					<span>添加题目</span>
				</a>
			</li>
			<li>
				<a class="edit" fresh="false" warn="请选择一个题目" rel="editSchemeContentMsg" target="dialog" href="teacher/schemeManager/editSchemeContent.action?id={tar_id}"> 
					<span>设置题目</span>
				</a>
			</li>
			<li>
				<a class="delete" target="ajaxTodo" href="teacher/schemeManager/deleteSchemeProblem.action?schemeContent.scheme.vid=${vid}&schemeContent.id={tar_id}" title="确定删除吗?" warn="请选择一个题目"> 
					<span>删除题目</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
				<th width="10%" align="center">目录</th>
				<th width="10%" align="center">习题名</th>
				<th width="10%" align="center">类型</th>
				<th width="10%" align="center">难度</th>
				<th width="10%" align="center">分值</th>
				<th width="10%" align="center">开始时间</th>
				<th width="10%" align="center">结束时间</th>
				<th width="10%" align="center" orderField="updateTime" <c:if test="${orderField=='updateTime' }">class="${orderDirection}"</c:if>>更新时间*</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="pro">
				<tr target="tar_id" rel="${pro.id}" >
					<td align="center">${pro.vchapName}</td>
					<td align="center">${pro.vpName}</td>
					<td align="center">
						<c:if test="${pro.problem.kind==0 }">程序题</c:if>
						<c:if test="${pro.problem.kind==1 }">例题</c:if>
						<c:if test="${pro.problem.kind==2 }">填空题</c:if>
						<c:if test="${pro.problem.kind==3 }">单选题</c:if>
						<c:if test="${pro.problem.kind==4 }">多选题</c:if>
						<c:if test="${pro.problem.kind==5 }">判断题</c:if>
					</td>
					<td align="center">${pro.problem.degree }</td>
					<td align="center">${pro.score}</td>
					<td align="center">${pro.startTime }</td>
					<td align="center">${pro.finishTime}</td>
					<td align="center">${pro.updateTime}</td>
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