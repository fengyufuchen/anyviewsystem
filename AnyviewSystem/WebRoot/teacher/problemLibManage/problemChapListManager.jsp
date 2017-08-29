<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
			
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="icon" title="题库管理" rel="problemLibManager" target="navTab" href="teacher/problemLibManager/gainProblemLibManagerPage.action">
					<span>刷新</span> 
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="10%" align="center">题库名</th>
				<th width="10%" align="center">创建教师</th>
				<th width="20%" align="center">创建教师所在学院</th>
				<th width="20%" align="center">创建教师所在学校</th>
				<th width="7%" align="center">访问级别</th>
				<th width="7%" align="center">类别</th>
				<th width="10%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="lib">
			</c:forEach>
		</tbody>
	</table>
</div>