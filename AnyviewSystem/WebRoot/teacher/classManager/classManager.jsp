<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!--分页的form-->
<!-- <form id="pagerForm" action="teacher/classManager/gainClassManagerPage.action" method="post" > -->
<form id="pagerForm" action="teacher/classManager/gainClassCoursePage.action" method="post" >
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
		<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />  
    	<input type="hidden" name="orderDirection" value="${orderDirection}" />
</form>
<div class="pageHeader">
</div>
			
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<!-- <a id="reflush" class="icon" fresh="true" rel="classManager" target="navTab" href="teacher/classManager/gainClassManagerPage.action" title="我的班级">  -->
				<a id="reflush" class="icon" fresh="true" rel="classManager" target="navTab" href="teacher/classManager/gainClassCoursePage.action" title="我的班级"> 
					<span>刷新</span> 
				</a>
			</li>
<!-- 			<li> -->
<!-- 				<a class="icon" mask="true" target="dialog" href="teacher/classManager/lookClass.jsp?cid={cid}" warn="请选择一个班级"> -->
<%-- 					<span>查看班级</span> --%>
<!-- 				</a> -->
<!-- 			</li> -->
<!-- 			<li > -->
<!-- 				<a class="edit" mask="true" target="dialog" href="teacher/classManager/editClass.jsp?cid={cid}" warn="请选择一个班级"> -->
<%-- 					<span>修改班级</span> --%>
<!-- 				</a> -->
<!-- 			</li> -->
			<li >
				<a class="icon" mask="true" target="navTab" fresh="true" rel="studentList" href="teacher/classManager/gainStudentsForClass.action?ctcid={ctcid}" warn="请选择一个班级">
					<span>学生管理</span>
				</a>
			</li>
			<li >
				<a class="icon" mask="true" target="navTab" fresh="true" rel="homeworkCorrectingIndex" href="teacher/homeworkCorrecting/index.action?ctcid={ctcid}" warn="请选择一个班级">
					<span>查看作业</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="82">
		<thead>
			<tr>
				<th width="10%" align="center" orderField="cl.cname" <c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>班名*</th>
				<%-- <th width="10%" align="center" orderField="cl.college.university.unName" <c:if test="${orderField=='cl.college.university.unName' }">class="${orderDirection}"</c:if>>学校*</th> --%>
				<th width="10%" align="center">学校*</th>
				<%-- <th width="10%" align="center" orderField="cl.college.ceName" <c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>学院*</th> --%>
				<th width="10%" align="center" orderField="cl.college" <c:if test="${orderField=='cl.college' }">class="${orderDirection}"</c:if>>学院*</th>
				<th width="10%" align="center" orderField="cl.specialty" <c:if test="${orderField=='cl.specialty' }">class="${orderDirection}"</c:if>>专业*</th>
				<th width="7%" align="center" orderField="cl.startYear" <c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>年届*</th>
				<th width="7%" align="center">教授课程</th>
<!-- 				<th width="7%" align="center">类型</th> -->
<!-- 				<th width="7%" align="center">是否有效</th> -->
				<th width="7%" align="center">班级状态</th>
				<th width="10%" align="center">考试编排</th>
<!-- 				<th width="7%" align="center">在线人数</th> -->
				<th width="10%" align="center" orderField="cl.createTime" <c:if test="${orderField=='cl.createTime' }">class="${orderDirection}"</c:if>>创建时间*</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${page.content }" var="ct">
				<tr target="ctcid" rel="${ct.id }" >
					<td align="center">${ct.cla.cname }</td>
					<td align="center">${ct.cla.college.university.unName }</td>
					<td align="center">${ct.cla.college.ceName }</td>
					<td align="center">${ct.cla.specialty }</td>
					<td align="center">${ct.cla.startYear }</td>
					<td align="center">${ct.course.courseName }</td>
<!-- 					<td align="center"> -->
<%-- 						<c:if test="${ct.cla.kind == 0 }">普通班级</c:if> --%>
<%-- 						<c:if test="${ct.cla.kind == 1 }">专属班级</c:if> --%>
<!-- 					</td> -->
<!-- 					<td align="center"> -->
<%-- 						<c:if test="${ct.cla.enabled == 0 }">停用</c:if> --%>
<%-- 						<c:if test="${ct.cla.enabled == 1 }">正常</c:if> --%>
<!-- 					</td> -->
					<td align="center">
						<c:if test="${ct.cla.status == 0 }">未锁定</c:if>
						<c:if test="${ct.cla.status == 1 }">登录锁定</c:if>
						<c:if test="${ct.cla.status == 2 }">做题锁定</c:if>
						<c:if test="${ct.cla.status == 3 }">考试锁定</c:if>
					</td>
					<td align="center">${ct.cla.epId }</td>
<!-- 					<td align="center">0/0</td> -->
					<td align="center"><fmt:formatDate value="${ct.cla.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
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