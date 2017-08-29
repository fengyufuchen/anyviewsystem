<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：lookStudent.jsp-->
<!--描   述：查看学生管理页面-->
<!--时   间 ：2015年08月29日-->


<script type="text/javascript">
</script>

<!--分页的form-->
<form id="pagerForm" action="admin/adminclassManager/getLookStudentPage.action" method="post" ></form>

<div class="pageHeader">
				
<!--查询的form-->
<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminclassManager/getLookStudentPage.action" method="post">
	<input type="hidden" name="cs.cla.cid" id="cid" value="${criteria.cla.cid }"/>
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />  
    <input type="hidden" name="orderDirection" value="${orderDirection}" />
			<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>学生学号：
							<input class="textInput" type="text" name="cs.student.sno" alt="输入学生学号(可选)" value="${criteria.student.sno }"/>
						</td>
						<td>学生姓名：
							<input class="textInput" type="text" name="cs.student.sname" alt="输入学生姓名(可选)" value="${criteria.student.sname }"/>
						</td>
						<td>
							<select class="combox" name="cs.student.ssex">
								<option <c:if test="${empty criteria.student.ssex }">selected="selected"</c:if> value="">请选择性别(可选)</option>
								<option value="M" <c:if test="${criteria.student.ssex=='M' }">selected="selected"</c:if>>男</option>
								<option value="F" <c:if test="${criteria.student.ssex=='F' }">selected="selected"</c:if>>女</option>
							</select>
						</td>
						<td>
							<select class="combox" name="cs.sattr" >
								<option <c:if test="${empty criteria.sattr }">selected="selected"</c:if> value="">请选择属性(可选)</option>
								<option value="0" <c:if test="${criteria.sattr==0 }">selected="selected"</c:if> >休学</option>
								<option value="1" <c:if test="${criteria.sattr==1 }">selected="selected"</c:if>>普通</option>
								<option value="2" <c:if test="${criteria.sattr==2 }">selected="selected"</c:if>>班长</option>
								<option value="3" <c:if test="${criteria.sattr==3 }">selected="selected"</c:if>>教师专用</option>
								<option value="4" <c:if test="${criteria.sattr==4 }">selected="selected"</c:if>>教师专属</option>
							</select>
						</td>
						<td>
							<select class="combox" name="cs.status">
								<option <c:if test="${empty criteria.status }">selected="selected"</c:if> value="">请选择有效性(可选)</option>
								<option value="0" <c:if test="${criteria.status==0 }">selected="selected"</c:if>>无效</option>
								<option value="1" <c:if test="${criteria.status==1 }">selected="selected"</c:if>>有效</option>
							</select>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="edit" mask="true" target="dialog" width="320" height="230" href="admin/adminclassManager/editStudentInClass.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}" warn="请选择一个学生"> 
					<span>编辑</span> 
				</a>
			</li>
			<li>
				<a class="delete" mask="true" target="ajaxTodo" href="admin/adminclassManager/deleteStudentInClass.action?cs.cla.cid=${criteria.cla.cid}&cs.student.sid={sid}" title="确定删除吗？" warn="请选择一个学生"> 
					<span>删除关联</span>
				</a>
			</li>
			<li>
				<a class="icon" href="admin/adminclassManager/getLookStudentPage.action?cs.cla.cid=${criteria.cla.cid }" target="navTab" fresh="true" rel="lookStudent" title="查看学生">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table id="listStudentPageContent" class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="18%" align="center">班级</th>
				<th width="15%" align="center" orderField="sname" <c:if test="${orderField=='sname' }">class="${orderDirection}"</c:if> >姓名*</th>
				<th width="15%" align="center" orderField="sno" <c:if test="${orderField=='sno' }">class="${orderDirection}"</c:if>>学号*</th>
				<th width="8%" align="center">性别</th>
				<th width="12%" align="center">属性</th>
				<th width="12%" align="center">此班级中有效性</th>
				<th width="20%" align="center">更新时间</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="cs" >
				<tr target="sid" rel="${cs.student.sid }">
					<td align="center">${cs.cla.cname }</td>
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
						<c:if test="${cs.status==0 }">无效</c:if>
						<c:if test="${cs.status==1 }">有效</c:if>
					</td>
					<td align="center">${cs.updateTime }</td>
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