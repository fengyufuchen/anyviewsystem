<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：universityManager.jsp-->
<!--描   述：学校管理页面-->
<!--时   间 ：2015年08月05日-->

<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#superAdminUnSelect").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
	
});
</script>


<form id="pagerForm" action="admin/universityManager/getUniversityManagerPage.action" method="post" >	</form>


<div class="Header">
<!--分页的form-->
<form id="searchForm" onsubmit="return navTabSearch(this);" action="admin/universityManager/getUniversityManagerPage.action" method="post" >	
	 <input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	 <div class="searchBar">
	 	<table class="searchContent">
	 		<tbody>
	 			<tr>
	 				<td>
		 			       学校：<input type="text" name="univer.unName" alt="请输入学校名称（可选）" value="${condition.unName }">
	 				</td>
	 				<td>
		 				属性：<select name="univer.attr" style="width:150px;">
		 						<option <c:if test="${empty condition.attr }"> selected="selected" </c:if> value="">请选择属性（可选）</option>
		 						<option <c:if test="${condition.attr==0 }">selected="selected"</c:if> value="0">本服务器</option>
		 						<option <c:if test="${condition.attr==1 }">selected="selected"</c:if> value="1">独立服务器</option>
		 					</select>
	 				</td>
	 				<td>
	 		                    有效状态：<select name="univer.enabled" style="width:150px;">
								<option <c:if test="${empty condition.enabled }">selected="selected"</c:if> value="">请选择状态（可选）</option> 
								<option <c:if test="${condition.enabled==0 }">selected="selected"</c:if> value="0">停用</option>
								<option <c:if test="${condition.enabled==1 }">selected="selected"</c:if> value="1">正常</option>
	 					    </select>
	 				</td>
	 				<td>
	 					IP:<input name="univer.ip" type="text" alt="请输入ip（可选）" value="${condition.ip }"/>
	 				</td>
	 			</tr>
	 			<tr>
				 	<td class="dateRange" colspan="2">
						创建时间:
						<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateStart" dateFmt="yyyy-MM-dd HH:mm:ss">
						<span class="limit">-</span>
						<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="createDateEnd" dateFmt="yyyy-MM-dd HH:mm:ss">
				    </td>
				    <td>
				    <div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="searchBtn">
								检索
							</button>
						</div>
					</div>
					</td>
	 			</tr>
	 		</tbody>
	 	</table>
	 </div>
	 
	 
</form>		
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li >
				<a class="icon" mask="true" href="admin/universityManager/getUniversityManagerPage.action" target="navTab" fresh="true" rel="universityManager" title="学校管理">
					<span>刷新</span>
				</a>
			</li>
			<li>
				<a class="add" mask="true" target="dialog"   fresh="true" rel="admin/universityManager" href="admin/universityManager/authorityAddUniversity.action">
					<span>添加学校</span>
				</a>
			</li>
<!-- 			<li > -->
<!-- 				<a class="edit"  id="collegeManager" mask="true" target="navTab" fresh="true" rel="collegeManager" href="admin/collegeManager/authorityCollegeManager.action?unID={unID}"  title="学院管理" warn="请选择一个学校"> -->
<%-- 					<span>学院管理</span> --%>
<!-- 				</a> -->
<!-- 			</li> -->
			<li >
				<a class="delete" mask="true"  target="dialog"  href="admin/universityManager/authorityDeleteUniversity.action?unID={unID}"warn="请选择一个学校">
					<span>删除</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/universityManager/authorityUpdateUniversity.action?unID={unID}"warn="请选择一个学校">
					<span>修改</span>
				</a>
			</li>
			
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th width="20%" align="center">学校名</th>
				<th width="7%" align="center">属性</th>
				<th width="7%" align="center">有效状态</th>
				<th width="10%" align="center">验证码</th>
				<th width="15%" align="center">独立IP</th>
				<th width="10%" align="center">独立Port</th>
				<th width="20%" align="center">创建时间</th>
				<th width="20%" align="center">更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="univer">
				<tr onselect="cl(this);" target="unid" rel="${univer.unID}">
					<td align="center">${univer.unName }</td>
					<td align="center">
					    <c:if test="${univer.attr==0 }">本地服务器</c:if>
					    <c:if test="${univer.attr==1 }">独立服务器</c:if>
					 </td>
					 <td align="center">
					    <c:if test="${univer.enabled==0 }">停用</c:if>
					    <c:if test="${univer.enabled==1 }">正常</c:if>
					 </td>
					 <td align="center">${univer.verification }</td>
					 <td align="center">${univer.ip }</td>
					 <td align="center">${univer.port }</td>
					 <td align="center">${univer.createTime }</td>
					 <td align="center">${univer.updateTime }</td>
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