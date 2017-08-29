<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--分页的form-->
<form id="pagerForm" action="teacher/comprehensiveScore/gainCCSchemePage.action" method="post" >	
	<input type="hidden" name="pageNum" id="pageNum" value="1" />
	<input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
</form>	

<div class="pageHeader">
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="icon" fresh="false" rel="studentExercise" mask="true" target="navTab"  warn="请选择一个学生"
					href="teacher/comprehensiveScore/gainStudentExerciseAnswer.action?cla.cid=${cla.cid }&scheme.vid=${scheme.vid }&student.sid={tar_sid}">
					<span>查看答卷</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="ajaxTodo" href="teacher/comprehensiveScore/grade.action?ccsId=${ccs.ccsId }">
					<span>计算分数</span>
				</a>
			</li>
			<li>
				<a title="设置算分规则" rel="setGradeRules" class="edit" mask="true" target="dialog" href="teacher/comprehensiveScore/setGradeRules.action" height="450" width="550">
					<span>设置算分规则</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="navTab" href="">
					<span>保存到Excel</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
			    <th width="4%" align="center"><input type="checkbox" id="selectall" onclick="checkselectall(this);"/></th>
				<th width="16%" align="center">学号</th>
				<th width="10%" align="center">姓名</th>
				<th width="10%" align="center">完成题数/总题数</th>
				<th width="20%" align="center">累计时间（分钟）</th>
				<th width="7.5%" align="center">得分</th>
				<th width="7.5%" align="center">排名</th>
				<th width="7.5%" align="center">是否批改</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="s">
				<tr target="tar_sid" rel="${s.student.sid }">
					<td align="center"><input type="checkbox" id="selectall"/></td>
					<td align="center">${s.student.sno }</td>
					<td align="center">${s.student.sname }</td>
					<td align="center">
						<c:choose>
							<c:when test="${empty s.passNum }">0</c:when>
							<c:otherwise>${s.passNum }</c:otherwise>
						</c:choose>
						/${s.scheme.totalNum }
					</td>
					<td align="center">${s.totalTime }</td>
					<td align="center">${s.score }</td>
					<td align="center">${s.rank }</td>
					<td align="center">
						<c:if test="${s.correctFlag == 0 }">未批改</c:if>
						<c:if test="${s.correctFlag == 1 }">已批改</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="10">10</option>
					<option value="20" selected="selected">20</option>
					<option value="30">30</option>
					<option value="50">50</option>
					<option value="100">100</option>
			</select>
			<span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>