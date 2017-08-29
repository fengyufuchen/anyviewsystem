<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#AdminTeacher").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
});
</script>
<!--分页的form-->

<form id="pagerForm" action="admin/teacherManager/searchTeacher.action" method="post" ></form> 

<div class="pageHeader">
<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/teacherManager/searchTeacher.action" method="post" >
<input type="hidden" name="tid" id="tid" value="${tea.tid }"/>
<input type="hidden" name="pageNum" id="pageNum" value="1" />
<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />  
    	<input type="hidden" name="orderDirection" value="${orderDirection}" />
			<div class="searchsBar">
			<table class="searchContent" style="float: left;">
			
				<tbody>
				<tr>		
				    <td>
 							<div class="pages">
 							  <label>学校名称：</label>
								<select  style="width: 200px;" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if> id="AdminTeacher" name="unID" >
									<c:choose>
										<c:when test="${admin.miden==-1 }">
											    <option id="University" <c:if test="${empty unID}"></c:if> value="">请选择学校</option>
											<c:forEach items="${universities }" var="u">
												<option <c:if test='${unID==u.unID }' >selected="selected" </c:if>  value="${u.unID }" >${u.unName } </option>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
										</c:otherwise>
									</c:choose>
								</select>
							</div> 
						</td>
					<c:if test="${admin.miden!=-1 }"> 
						<td>
							<div class="pages">
							  <label>学院名称:</label>
								<select <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>   style="width:200px" name="ceID" id="select_ceID" >
									<c:choose>
										<c:when test="${admin.miden==0 }">
											<option value="${admin.college.ceID }" selected="selected"> ${admin.college.ceName }</option>
										</c:when>
										<c:otherwise>
											<option id="allCollege"  <c:if test="${empty ceID}"></c:if>  value="" >请选择学院</option>
											<c:forEach items="${colleges }" var="c">
												<option  <c:if test='${ceID==c.ceID}'> selected="selected"</c:if> value="${c.ceID }"  > ${c.ceName }</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
						</td>
					</c:if> 
						<td>
						<div class="pages">
							<label>教师姓名:</label>
							<input class="chinese textInput" type="text" name="tea.tname" maxlength="20" alt="输入教师姓名(可选)" value="${criteria.tname }"/>
						</div>
					</td>
				    <td>
						<div class="pages">
							<label>教师账号:</label>
							<input class="digits textInput" type="text" name="tea.tno" maxlength="20" alt="输入教师账号(可选)" value="${criteria.tno }"/>
						</div>
					</td>
				</tr>
				<tr>		
					<%-- <td>
						<div class="pages">
							<label>教师身份:</label>
							<select  name="tea.tiden" >
								<option <c:if test="${empty criteria.tiden }">selected="selected"</c:if> value="">请选择身份(可选)</option>
								<option value="0" <c:if test="${criteria.tiden==0 }">selected="selected"</c:if> >普通教师</option>
								<option value="1" <c:if test="${criteria.tiden==1 }">selected="selected"</c:if>>学院领导</option>
								<option value="2" <c:if test="${criteria.tiden==2 }">selected="selected"</c:if>>学校领导</option>
							</select>
						</div>
					</td> --%>
					<td>
						<div class="pages">
							<label>性别:</label>
							<select name="tea.tsex">
								<option <c:if test="${empty criteria.tsex }">selected="selected"</c:if> value="">请选择性别(可选)</option>
								<option value="M" <c:if test="${criteria.tsex=='M' }">selected="selected"</c:if>>男</option>
								<option value="F" <c:if test="${criteria.tsex=='F' }">selected="selected"</c:if>>女</option>
							</select>
						</div>
					</td>
					<td>
						<div class="pages">
							<label>有效性:</label>
							<select name="tea.enabled">
								<option <c:if test="${empty criteria.enabled }">selected="selected"</c:if> value="">请选择有效性(可选)</option>
								<option value="0" <c:if test="${criteria.enabled==0 }">selected="selected"</c:if>>停用</option>
								<option value="1" <c:if test="${criteria.enabled==1 }">selected="selected"</c:if>>正常</option>
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
				<a id="reflush" class="edit" fresh="true" rel="teacherManager"target="navTab" href="admin/teacherManager/searchTeacher.action" title="教师管理"> 
					<span>刷新</span> 
				</a>
			</li>
			<li >
				<a class="add" mask="true" target="dialog"  href="admin/teacherManager/BeforeAddTeacher.action">
					<span>增加</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" href="admin/teacherManager/editTeacher.action?tid={teacher}" warn="请选择一位教师">
					<span>编辑</span>
				</a>
			</li>
			<li >
				<a class="delete" mask="true"  target="ajaxTodo" title="确定要删除吗?" href="admin/teacherManager/deleteTeacher.action?tid={teacher}" warn="请选择一位教师">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog"  href="admin/teacherManager/beforeLink.action?tid={teacher}" warn="请选择一位教师">
					<span>关联学院</span>
				</a>
			</li>
		</ul>
	</div>
</div>
	<table id="listTeacherPageContent" class="table" width="100%" layoutH="150">
		<thead>
			<tr>
			<th width="10%" align="center" orderField="tno" <c:if test="${orderField=='tno' }">class="${orderDirection}"</c:if> >教师编号*</th>
			<th width="10%" align="center" orderField="tname" <c:if test="${orderField=='tname' }">class="${orderDirection}"</c:if> >教师姓名*</th>
			<th width="10%" align="center">学校</th>
			<th width="10%" align="center">性别</th>
			<th width="10%" align="center">是否有效</th>
			<th width="20%" align="center">更新时间</th>
			
			</tr>
		</thead>
		<tbody>
			<!--超级管理员的界面  -->
			<c:catch var ="catchException">
					<c:forEach items="${page.content }" var="tea">
					<tr target="teacher" rel="${tea.tid}" >
					<td align="center">${tea.tno}</td>
						<td align="center">${tea.tname }</td>
					<td align="center">
					${tea.university.unName}
					</td>	
					<%-- <td align="center">
					${tea.college.ceName}
					</td>
						<td align="center">
						<c:if test="${tea.tiden == 0 }">普通教师</c:if>
						<c:if test="${tea.tiden == 1 }">学院领导</c:if>
						<c:if test="${tea.tiden == 2 }">学校领导</c:if>
					</td> --%>
					<td align="center">
						<c:if test="${tea.tsex =='M' }">男</c:if>
						<c:if test="${tea.tsex =='F' }">女</c:if>
					</td>
					<td align="center">
					<c:if test="${tea.enabled ==0 }">无</c:if>
					<c:if test="${tea.enabled ==1}">有</c:if>
					</td>
					<td align="center">
					${tea.updateTime}
					</td>
	<%-- 					<td align="center">${tea.tright }</td> --%>
					</tr>
				</c:forEach>
			</c:catch>
			
			<!--普通管理员的界面  -->
			<c:if test = "${catchException != null}">
					<c:forEach items="${page.content }" var="ct">
					<tr target="teacher" rel="${ct.teacher.tid}" >
					<td align="center">${ct.teacher.tno}</td>
						<td align="center">${ct.teacher.tname }</td>
					<td align="center">
					${ct.teacher.university.unName}
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
					${ct.teacher.updateTime}
					</td>
					</tr>
				</c:forEach>			
			</c:if>
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
