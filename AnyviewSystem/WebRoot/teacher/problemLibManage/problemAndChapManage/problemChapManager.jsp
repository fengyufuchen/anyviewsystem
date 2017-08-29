<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">

</script>

<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<form id="pagerForm" onsubmit="return divSearch(this, 'problemChapBox');" action="teacher/problemManager/problemChapManager.action" method="post">
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${chapPage.numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />  
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<input type="hidden" name="problemChap.problemLib.lid" value="${chap.problemLib.lid }" />
	<input type="hidden" name="problemChap.parentChap.chId" value="${chap.parentChap.chId }" />
	<%-- <div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					目录名：<input type="text" name="problemChap.chName" />
				</td>
				<td>
					访问级别：
					<select name="problemChap.visit">
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
			<a class="icon" title="kkk" rel="problemAndChapManager" target="navTab" href="teacher/problemManager/problemAndChapManager.action?problemChap.problemLib.lid=${problemChap.problemLib.lid}&kind=pre&problemChap.parentChap.chId=${problemChap.parentChap.chId}"> 
				<span>上一级</span> 
			</a>
			<a class="icon" warn="请选择一个目录" rel="problemAndChapManager" title="kkk" target="navTab" href="teacher/problemManager/problemAndChapManager.action?problemChap.problemLib.lid=${problemChap.problemLib.lid}&kind=next&problemChap.parentChap.chId={tar_chId}"> 
				<span>下一级</span> 
			</a>
			<a class="add" fresh="false" rel="addProblemChap" title="添加目录" target="dialog" href="teacher/problemLibManage/problemAndChapManage/addProblemChap.jsp?chId=${problemChap.parentChap.chId }&lid=${problemChap.problemLib.lid}"> 
				<span>添加</span>
			</a>
			<a class="delete" warn="请选择一个目录" target="ajaxTodo" title="需清空目录下所有的题目才能删除（包括所有子目录中的题目）！确定删除吗？" href="teacher/problemManager/deleteProblemChap.action?problemChap.chId={tar_chId}&problemChap.problemLib.lid=${problemChap.problemLib.lid}&problemChap.parentChap.chId=${problemChap.parentChap.chId}"> 
				<span>删除</span>
			</a>
			<a class="edit" warn="请选择一个目录" fresh="false" rel="eidtProblemChap" target="dialog" title="修改目录" href="teacher/problemManager/editProblemChap.action?problemChap.chId={tar_chId}"> 
				<span>修改</span>
			</a>
		</ul>
	</div>
	<table class="table" width="99%" layoutH="540" rel="problemChapBox">
		<thead>
			<tr>
				<th width="10%" align="center">目录名</th>
				<th width="10%" align="center">访问级别</th>
				<th width="10%" align="center">说明</th>
				<th width="10%" align="center">创建时间</th>
				<th width="10%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${chapPage.content }" var="chap">
				<tr target="tar_chId" rel="${chap.chId }">
					<td align="center">${chap.chName }</td>
					<td align="center">
						<c:if test="${chap.visit==0 }">私有</c:if>
						<c:if test="${chap.visit==1 }">公开</c:if>
					</td>
					<td align="center">${chap.memo}</td>
					<td align="center">${chap.createTime}</td>
					<td align="center">${chap.updateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value}, 'problemChapBox')">
				<option value="10" <c:if test="${chapPage.numPerPage==10}">selected="selected"</c:if>>10</option>
				<option value="20" <c:if test="${chapPage.numPerPage==20}">selected="selected"</c:if>>20</option>
				<option value="30" <c:if test="${chapPage.numPerPage==30}">selected="selected"</c:if>>30</option>
				<option value="50" <c:if test="${chapPage.numPerPage==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${chapPage.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select>
			<span> 条，共${chapPage.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" rel="problemChapBox" targetType="navTab" totalCount="${chapPage.totalCount }" numPerPage="${chapPage.numPerPage }" pageNumShown="${chapPage.totalPageNum }" currentPage="${chapPage.currentPage }"></div>
	</div>
</div>