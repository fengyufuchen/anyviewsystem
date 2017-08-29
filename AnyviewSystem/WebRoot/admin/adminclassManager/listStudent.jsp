<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：listStudent.jsp-->
<!--描   述：关联班级学生管理页面-->
<!--时   间 ：2015年08月29日-->

<script type="text/javascript">

//全选与反选
function checkselectall(selectall){
	if(selectall.checked)
		$("input[name=sids]:checkbox").attr("checked",true);
	else
		$("input[name=sids]:checkbox").attr("checked",false);
}

//批删除提交
function addstudent(){
	alertMsg.confirm("确定要将选中的学生与班级关联么？",{
		okCall:function(){
			$("#addstudnet").submit();
		},
		cancelCall:function(){
		}
	});
}
</script>

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/getListStudentPage.action" method="post" >
</form>		

<div class="pageHeader">
				
<!--查询的form-->
	 <form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/getListStudentPage.action" method="post">
	 <input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	 <input type="hidden" name="orderField" value="${orderField}" />  
     <input type="hidden" name="orderDirection" value="${orderDirection}" />
     <input type="hidden" name="cid" value="${cid }" />
			<div class="searchBar">
			<table class="searchContent">
			<tbody>
			<tr>
				<td>学号：
					<input class="textInput" type="text" name="conditionstu.sno" alt="输入学生账号(可选)" value="${conditionstu.sno }"/>
				</td>
				<td>姓名：
					<input class="textInput" type="text" name="conditionstu.sname" alt="输入学生姓名(可选)" value="${conditionstu.sname }"/>
				</td>
			</tr>
			</tbody>
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
	<form id="addstudnet" rel="pagerForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="admin/adminstudentManager/batAddStudentInClass.action" method="post">
	<!-- <form id="addstudnet" rel="pagerForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="admin/adminclassManager/batAddStudentInClass.action" method="post"> -->
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<!-- <a class="add" mask="true" width="320" height="230" target="dialog" fresh="true" href="admin/adminstudentManager/addStudentInClass.action?sid={sid}" warn="请选择一个学生"> -->
					<a class="add" mask="true" width="320" height="230" target="dialog" fresh="true" href="admin/adminclassManager/addStudentInClass.action?sid={sid}" warn="请选择一个学生">
					<span>超管关联到班级</span>
				</a>
			</li>
			<li>
				<a class="add" id="addall" onclick="addstudent();">
					<span>批量关联到班级</span>
				</a>
			</li>
			<li >
				<a class="icon" href="admin/adminclassManager/getListStudentPage.action" target="navTab" fresh="true" rel="listStudent" title="关联学生">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4%" align="center"><input type="checkbox" id="selectall" onclick="checkselectall(this);"></th>
				<th width="22%" align="center">学校</th>
				<th width="22%" align="center" orderField="sno" <c:if test="${orderField=='sno' }">class="${orderDirection}"</c:if> >学号*</th>
				<th width="22%" align="center" orderField="sname" <c:if test="${orderField=='sname' }">class="${orderDirection}"</c:if> >姓名*</th>
				<th width="14%" align="center">性别</th>
				<th width="16%" align="center">有效状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="stu">
				<tr target="sid" rel="${stu.sid}">
					<td align="center"><input name="sids"  type="checkbox" value="${stu.sid}"></td>
					<td align="center">${stu.university.unName }</td>
					<td align="center">${stu.sno }</td>
					<td align="center">${stu.sname }</td>
					<td align="center">
						<c:choose>
							<c:when test="${stu.ssex == 'M' }">男</c:when>
							<c:otherwise>女</c:otherwise>
						</c:choose>
					</td>
					<td align="center">
						<c:choose>
							<c:when test="${stu.enabled == 0 }">停用</c:when>
							<c:when test="${stu.enabled == 1 }">正常</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
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