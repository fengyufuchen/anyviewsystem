<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	
});
</script>
<!--分页的form-->
<form id="pagerForm" action="teacher/schemeManager/gainSchemeManagerPage.action" method="post" ></form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/schemeManager/gainSchemeManagerPage.action" method="post">
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
		<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="orderField" value="${orderField}" />  
    	<input type="hidden" name="orderDirection" value="${orderDirection}" />
		<div class="searchBar">
			<table class="searchContent" style="float: left;">
				<tbody>
					<tr>
						<%-- <td>
							<div class="pages">
								<select class="combox" style="width:200px" name="scheme.course.courseId" id="select_course">
									<option value="-1">所有课程</option>
									<c:forEach items="${tcourses }" var="tc">
										<option value="${tc.courseId }">${tc.courseName }</option>
									</c:forEach>
								</select>
							</div>
						</td> --%>
						<td>
							<div class="pages">
								<select class="combox" style="width:200px" name="scheme.visit">
									<option value="" <c:if test="${empty criteria.visit }">selected="selected"</c:if>>所有级别</option>
									<option value="0" <c:if test="${criteria.visit == 0 }">selected="selected"</c:if>>教师私有 </option>
									<option value="1" <c:if test="${criteria.visit == 1 }">selected="selected"</c:if>>部分公开</option>
									<option value="2" <c:if test="${criteria.visit == 2 }">selected="selected"</c:if>>学院公开</option>
									<option value="3" <c:if test="${criteria.visit == 3 }">selected="selected"</c:if>>学校公开</option>
									<option value="4" <c:if test="${criteria.visit == 4 }">selected="selected"</c:if>>完全公开</option>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<select class="combox" style="width:200px" name="scheme.status">
									<option value="" <c:if test="${empty criteria.status }">selected="selected"</c:if>>所有状态</option>
									<option value="0" <c:if test="${criteria.status==0 }">selected="selected"</c:if>>停用 </option>
									<option value="1" <c:if test="${criteria.status==1 }">selected="selected"</c:if>>测试</option>
									<option value="2" <c:if test="${criteria.status==2 }">selected="selected"</c:if>>正式</option>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<select class="combox" style="width:100px" name="scheme.kind">
									<option value="" <c:if test="${empty criteria.kind }">selected="selected"</c:if> >所有类型</option>
									<option value="0" <c:if test="${criteria.kind == 0 }">selected="selected"</c:if> >作业题</option>
									<option value="1" <c:if test="${criteria.kind == 1 }">selected="selected"</c:if>>考试题</option>
									<option value="2" <c:if test="${criteria.kind == 2 }">selected="selected"</c:if>>资源表</option>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>计划表名:</label>
								<input name="scheme.vname" class="textInput" type="text" placeholder="输入计划表名(可选)" value="${criteria.vname }"/>
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
				<a class="icon" title="我的作业表" rel="schemeManager" target="navTab" href="teacher/schemeManager/gainSchemeManagerPage.action">
					<span>刷新</span> 
				</a>
			</li>
			<li>
				<%-- <a class="add" fresh="false" rel="addScheme" title="新建作业表" target="navTab" href="teacher/schemeManager/addScheme.action"> 
					<span>添加</span> 
				</a> --%>
				<a class="add" fresh="false" rel="addScheme" title="新建作业表" target="navTab" href="teacher/schemeManager/newScheme.action"> 
					<span>添加</span> 
				</a>
			</li> 
			<li>
				<a class="edit" fresh="false" warn="请选择一个作业表" rel="editScheme" title="设置作业表" target="dialog" href="teacher/schemeManager/editScheme.action?vid={tarvid}"> 
					<span>设置</span> 
				</a>
			</li> 
			<%-- <li>
				<a class="edit" fresh="false" warn="请选择一个作业表" rel="lookSchemeProblems" target="navTab" href="teacher/schemeManager/lookSchemeProblems.action?vid={tarvid}"> 
					<span>管理题目</span> 
				</a>
			</li> --%>
			<li>
				<!-- <a class="edit" fresh="true" title="查看作业表" warn="请选择一个作业表" rel="lookSchemeProblems" target="navTab" href="teacher/schemeManager/viewSchemeContent.action?vid={tarvid}">  -->
					<a class="edit" fresh="true" title="查看作业表" warn="请选择一个作业表" rel="lookSchemeProblems" target="navTab" href="teacher/schemeManager/lookSchemeProblems.action?vid={tarvid}">
					<span>查看</span> 
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="10%" align="center">划表名</th>
				<th width="10%" align="center">创建教师</th>
				<th width="10%" align="center">创建教师所在学校</th>
				<th width="10%" align="center">课程</th>
				<th width="10%" align="center">课程所属学院</th>
				<th width="7%" align="center">类型</th>
				<th width="7%" align="center">状态</th>
				<th width="10%" align="center">访问级别</th>
				<th width="7%" align="center" orderField="fullScore" <c:if test="${orderField=='fullScore' }">class="${orderDirection}"</c:if>>满分值*</th>
				<th width="10%" align="center" orderField="updateTime" <c:if test="${orderField=='updateTime' }">class="${orderDirection}"</c:if>>更新时间*</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="scheme">
				<tr target="tarvid" rel="${scheme.vid }" >
					<td align="center">${scheme.vname }</td>
					<td align="center">${scheme.teacher.tname }</td>
					<td align="center">${scheme.teacher.university.unName }</td>
					<td align="center">${scheme.course.courseName }</td>
					<td align="center">${scheme.course.college.ceName }</td>
					<td align="center">
						<c:if test="${scheme.kind ==0}">作业题</c:if>
						<c:if test="${scheme.kind ==1}">考试题</c:if>
						<c:if test="${scheme.kind ==2}">资源表</c:if>
					</td>
					<td align="center">
						<c:if test="${scheme.status == 0 }">停用</c:if>
						<c:if test="${scheme.status == 1 }">测试</c:if>
						<c:if test="${scheme.status == 2 }">正式</c:if>
					</td>
					<td align="center">
						<c:if test="${scheme.visit == 0 }">教师私有</c:if>
						<c:if test="${scheme.visit == 1 }">部分公开</c:if>
						<c:if test="${scheme.visit == 2 }">院级公开</c:if>
						<c:if test="${scheme.visit == 3 }">校级公开</c:if>
						<c:if test="${scheme.visit == 4 }">完全公开</c:if>
					</td>
					<td align="center">${scheme.fullScore }</td>
					<td align="center">${scheme.updateTime }</td>
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