<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
// 	$("#reflush").bind("click", function(){
// 		var url = $("#pagerForm").attr("action");
// 		var data = {cid:$("#cid").val(),pageNum:$("#pageNum").val(),pageSize:$("#pageSize").val()};
// 		$("#listStudentPageContent").loadUrl(url,data,null);
// 	});
	
// 	$("#reflush").bind("click", function(){
// 		location.reload();
// 	});
});

</script>
<!--分页的form-->
<form id="pagerForm" action="teacher/classManager/gainStudentsForClass.action" method="post" ></form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/classManager/gainStudentsForClass.action" method="post">
		<input type="hidden" name="cs.cla.cid" id="cid" value="${criteria.cla.cid }"/>
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
		<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />  
    	<input type="hidden" name="orderDirection" value="${orderDirection}" />
		<div class="searchBar">
			<table class="searchContent" style="float: left;">
				<tbody>
					<tr>
						<td>
							<div class="pages">
								<label>学生姓名:</label>
								<input class="textInput" type="text" name="cs.student.sname" alt="输入学生姓名(可选)" value="${criteria.student.sname }"/>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>学生账号:</label>
								<input class="textInput" type="text" name="cs.student.sno" alt="输入学生账号(可选)" value="${criteria.student.sno }"/>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>属性:</label>
								<select class="combox" name="cs.sattr" >
									<option <c:if test="${empty criteria.sattr }">selected="selected"</c:if> value="">请选择属性(可选)</option>
									<option value="0" <c:if test="${criteria.sattr==0 }">selected="selected"</c:if> >休学</option>
									<option value="1" <c:if test="${criteria.sattr==1 }">selected="selected"</c:if>>普通</option>
									<option value="2" <c:if test="${criteria.sattr==2 }">selected="selected"</c:if>>班长</option>
									<option value="3" <c:if test="${criteria.sattr==3 }">selected="selected"</c:if>>教师专用</option>
									<option value="4" <c:if test="${criteria.sattr==4 }">selected="selected"</c:if>>教师专属</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="pages">
								<label>性别:</label>
								<select class="combox" name="cs.student.ssex">
									<option <c:if test="${empty criteria.student.ssex }">selected="selected"</c:if> value="">请选择性别(可选)</option>
									<option value="M" <c:if test="${criteria.student.ssex=='M' }">selected="selected"</c:if>>男</option>
									<option value="F" <c:if test="${criteria.student.ssex=='F' }">selected="selected"</c:if>>女</option>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>此班级中有效性:</label>
								<select class="combox" name="cs.status">
									<option <c:if test="${empty criteria.status }">selected="selected"</c:if> value="">请选择有效性(可选)</option>
									<option value="0" <c:if test="${criteria.status==0 }">selected="selected"</c:if>>停用</option>
									<option value="1" <c:if test="${criteria.status==1 }">selected="selected"</c:if>>正常</option>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>状态:</label>
								<select class="combox" name="cs.student.loginStatus">
									<option <c:if test="${empty criteria.student.loginStatus }">selected="selected"</c:if> value="">请选择状态(可选)</option>
									<option value="1" <c:if test="${criteria.student.loginStatus==1 }">selected="selected"</c:if>>已登录</option>
									<option value="0" <c:if test="${criteria.student.loginStatus==0 }">selected="selected"</c:if>>未登录</option>
								</select>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
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
			<li>
				<a id="reflush" class="icon" fresh="true" rel="studentList" target="navTab" href="teacher/classManager/gainStudentsForClass.action?ctcid=${ctcid }" title="学生管理"> 
					<span>刷新</span> 
				</a>
			</li>
			<li>
				<a class="edit" mask="true" target="dialog" href="teacher/classManager/editStudent.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}" warn="请选择一个学生"> 
					<span>编辑</span> 
				</a>
			</li>
			<li>
				<a class="add" mask="true" target="dialog" fresh="true" rel="addStudentForTeacher"  href="teacher/classManager/addStudent.jsp?cid=${criteria.cla.cid }"> 
					<span>添加</span> 
				</a>
			</li>
			<li>
				<a class="delete" mask="true" target="ajaxTodo" href="teacher/classManager/deleteStudent.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}&ctcid=${ctcid }" title="确定删除吗？" warn="请选择一个学生"> 
					<span>删除</span>
				</a>
			</li>
			<li>
				<%-- <a class="delete" mask="true" target="ajaxTodo" href="teacher/classManager/resetStudentPwd.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}" title="确定重置学生密码为学号吗？" warn="请选择一个学生"> --%>
					<a class="delete" mask="true" target="ajaxTodo" href="teacher/classManager/resetStudentPwd.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}&ctcid=${ctcid }" title="确定重置学生密码为学号吗？" warn="请选择一个学生">
					<span>重置学生密码</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table id="listStudentPageContent" class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10%" align="center" orderField="sname" <c:if test="${orderField=='sname' }">class="${orderDirection}"</c:if> >姓名*</th>
				<th width="15%" align="center" orderField="sno" <c:if test="${orderField=='sno' }">class="${orderDirection}"</c:if>>学号*</th>
				<th width="7%" align="center">性别</th>
				<th width="7%" align="center">属性</th>
				<th width="7%" align="center">此班级中有效性</th>
				<th width="7%" align="center">状态</th>
				<th width="7%" align="center" orderField="saccumTime" <c:if test="${orderField=='saccumTime' }">class="${orderDirection}"</c:if>>累计做题时间*</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="cs" >
				<tr target="sid" rel="${cs.student.sid }">
					<td align="center">${cs.student.sname }</td>
					<td align="center">${cs.student.sno }</td>
				<td align="center">
						<c:choose>
							<c:when test="${cs.student.ssex == 'M' }">男</c:when>
							<c:otherwise>女</c:otherwise>
						</c:choose>
					</td>
					<td align="center">
						<c:if test="${cs.sattr==0 }">休学</c:if>
						<c:if test="${cs.sattr==1 }">普通</c:if>
						<c:if test="${cs.sattr==2 }">班长</c:if>
						<c:if test="${cs.sattr==3 }">教师专用</c:if>
						<c:if test="${cs.sattr==4 }">教师专属 </c:if>
					</td>
					<td align="center">
						<c:if test="${cs.status==0 }">停用</c:if>
						<c:if test="${cs.status==1 }">正常</c:if>
					</td>
					<td align="center">
						<c:if test="${cs.student.loginStatus==0}">未登录</c:if>
						<c:if test="${cs.student.loginStatus==1}">已登录</c:if>
					</td>
					<td align="center">${cs.student.saccumTime}</td>
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