<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#unID_course").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
	$("#ceID_course").select2({
		placeholder : '请选择学院',
		allowClear : true
	});
	//级联
	changeAjaxSelect2($("#unID_course"), $("#ceID_course"), "communion/gainCollegeByUnIdAjax.action", "请选择学院", {unId:null});
});
</script>
<!-- 分页的form -->
<form id="pagerForm" action="admin/courseManager/getCoursePage.action" method="post"></form>

<div class="pageHeader">
<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/courseManager/getCoursePage.action" method="post" >
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	<input type="hidden" name="orderField" value="${orderField}" />  
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
	<div class="searchBar">
	  <table>
	   <tbody>
	    <tr>
	      <td>
	        <div class="pages">
	           <label>学校名称：</label>
	           <select  style="width: 150px;"  id="unID_course" name="unID" refUrl="admin/courseManager/getCollegeByUnIDAjax.action">
	              <c:choose>
					<c:when test="${admin.miden==-1 }">
						    <option id="University" <c:if test="${empty unid}"></c:if> value="">请选择学校</option>
						<c:forEach items="${universities}" var="u">
							<option <c:if test='${unid==u.unID }' >selected="selected" </c:if>  value="${u.unID }" >${u.unName } </option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
					</c:otherwise>
				</c:choose>          
	           </select>
	        </div>
	      </td>
	      <td>
	        <div class="pages">
			  <label>学院名称:</label>
				<select <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>  style="width: 150px;"name="ceID" id="ceID_course" >
					<c:choose>
						<c:when test="${admin.miden==0 }">
							<option value="${admin.college.ceID }" selected="selected"> ${admin.college.ceName }</option>
						</c:when>
						<c:otherwise>
							<option id="allCollege"  <c:if test="${empty ceid}"></c:if>  value="" >请选择学院</option>
							<c:forEach items="${colleges }" var="c">
								<option  value="${c.ceID }" >${c.ceName }</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
			</div>
	      </td>
	      <td>
	       <div class="pages">
	        <label>课程名称：</label>
	         <input class="chinese textInput" type="text" name="coursetable.coursename" maxlength="20" alt="请输入课程名称（可选）" value="${creteria.coursename }">
	       </div>
	       </td>
	       <td>
	       <div class="pages">
	       <label>课程分类：</label>
	       <select id="category" name="coursetable.category">
	            <option <c:if test="${empty criteria.category }">selected="selected"</c:if> value="">请选择课程分类(可选)</option>
          		<option id="category_0" value="0" <c:if test="${course.category == '学院级课程' }">selected="selected"</c:if>>学院级课程</option>
				<option id="category_1" value="1" <c:if test="${course.category == '校级课程' }">selected="selected"</c:if>>校级课程</option>
				<option id="category_2" value="2" <c:if test="${course.category == '校内共享课程' }">selected="selected"</c:if>>校内共享课程</option>
				<option id="category_3" value="3" <c:if test="${course.category == '公共课程' }">selected="selected"</c:if>>公共课程</option>
		   </select>
	       </div>
	       </td>
	      </tr>
	      <tr>
	      <td class="dateRange" colspan="2">
			创建时间:
			<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateStart" dateFmt="yyyy-MM-dd HH:mm:ss">
			<span class="limit">-</span>
			<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss">
		</td>
		<td class="dateRange" colspan="2">
			更新时间:
			<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateStart" dateFmt="yyyy-MM-dd HH:mm:ss">
			<span class="limit">-</span>
			<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="updateDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss">
		</td>
	    <td>
			<div class="pages">
				<label>有效性:</label>
				<select name="coursetable.enabled">
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
			<li >
				<a class="add" mask="true" target="dialog" href="admin/courseManager/getCurrentCourseForCeManagerPage.action">
					<span>添加课程</span>
				</a>
				<a class="edit" mask="true" target="dialog" href="admin/courseManager/editCourseForDiverseManagerPage.action?courseId={courseId}" warn="请选择一门课程!">
					<span>修改课程</span>
				</a>
				<a class="delete" mask="true" target="dialog" href="admin/courseManager/deleteCoursePage.action?courseId={courseId}" warn="请选择一门课程!">
					<span>删除课程</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="74">
		<thead>
			<tr>
				<th width="10%" align="center">*课程名<s:property value="total"/></th>
				<th width="10%" align="center">所属学院</th>
				<th width="10%" align="center">所属学校</th>
				<th width="10%" align="center">分类名称</th>
				<th width="10%" align="center">有效状态</th>
				<th width="20%" align="center">创建时间</th>
				<th width="20%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="course">
				<tr target="courseId" rel="${course.courseId }">
					<td align="center">${course.courseName }</td>
					<td align="center">${course.college.ceName }</td>
					<td align="center">${course.university.unName }</td>
					<td align="center">${course.category }</td>
					<td align="center">
						<c:choose>
							<c:when test="${course.enabled == 0 }">停用</c:when>
							<c:when test="${course.enabled == 1 }">正常</c:when>
						</c:choose>
					</td>
					<td align="center"> ${course.createTime }</td>
					<td align="center"> ${course.updateTime }</td>
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