<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!--分页的form-->
<form id="pagerForm" action="teacher/homeworkCorrecting/index.action" method="post" ></form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/homeworkCorrecting/index.action" method="post">
		<input type="hidden" name="pageNum" id="pageNum" value="1" />
		<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
		<input type="hidden" name="ctcid" value="${ctcid }" />
		<div class="searchBar">
			<table class="searchContent" style="float: left;">
				<tbody>
					<tr>
						<td>
							<div class="pages">
								<label>作业表:</label>
								<select class="combox" style="width:200px" name="scheme.vid">
									<c:forEach items="${schemes }" var="v">
										<option value="${v.vid}" <c:if test="${v.vid==vid }">selected="selected"</c:if> >${v.vname }</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td>
							<div class="pages">
								<label>学生姓名:</label>
								<input class="textInput" type="text" name="student.sname" maxlength="20" value="${sname }">
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
				<a class="icon" title="批改作业" fresh="false" rel="homeworkCorrectingView" target="navTab" href="teacher/homeworkCorrecting/view.action?score.id={tarid}">
					<span>批改作业</span> 
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="10%" align="center">学号</th>
				<th width="10%" align="center">姓名</th>
				<th width="10%" align="center">成绩</th>
				<th width="10%" align="center">是否批改</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="score">
				<tr target="tarid" rel="${score.id }" >
					<td align="center">${score.student.sno }</td>
					<td align="center">${score.student.sname }</td>
					<td align="center">${score.score }</td>
					<td align="center">
						<c:if test="${score.correctFlag == 0}">未批改</c:if>
						<c:if test="${score.correctFlag == 1}">已批改</c:if>
					</td>
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