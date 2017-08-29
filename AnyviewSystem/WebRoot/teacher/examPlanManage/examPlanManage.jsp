<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!--分页的form-->
<form id="pagerForm" action="teacher/examPlanManager/gainExamPlanManagerPage.action" method="post" ></form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/examPlanManager/gainExamPlanManagerPage.action" method="post">
		<div class="searchBar">
			<table class="searchContent" style="float: left;">
				<tr>
					<td>
						名称:
						<input name="examPlan.epName" class="textInput" type="text" placeholder="输入考试编排名称(可选)" value="${criteria.epName }"/>
					</td>
					<td>
						状态:
						<select name="examPlan.status">
							<option value="" <c:if test="${empty criteria.status }">selected="selected"</c:if> >所有状态</option>
							<option value="0" <c:if test="${criteria.status==0 }">selected="selected"</c:if> >未使用</option>
							<option value="1" <c:if test="${criteria.status==1 }">selected="selected"</c:if> >中止</option>
							<option value="2" <c:if test="${criteria.status==2 }">selected="selected"</c:if> >完成</option>
							<option value="3" <c:if test="${criteria.status==3 }">selected="selected"</c:if> >正在考试</option>
							<option value="4" <c:if test="${criteria.status==4 }">selected="selected"</c:if> >考试准备中</option>
						</select>
					</td>
					<td>
						类型:
						<select name="examPlan.kind">
							<option value="" <c:if test="${empty criteria.kind }">selected="selected"</c:if> >所有类型</option>
							<option value="0" <c:if test="${criteria.kind==0 }">selected="selected"</c:if> >手动</option>
							<option value="1" <c:if test="${criteria.kind==1 }">selected="selected"</c:if> >自动</option>
						</select>
					</td>
				</tr>
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
				<a class="icon" title="考试编排" fresh="true" rel="examPlanManager" target="navTab" href="teacher/examPlanManager/gainExamPlanManagerPage.action"> 
					<span>刷新</span> 
				</a>
			</li>
			<li>
				<a class="add" title="添加考试编排" mask="true" target="dialog" fresh="false" rel="addExamPlan" href="teacher/examPlanManager/addExamPlan.action" width="600" height="350">
					<span>添加</span>
				</a>
			</li>
			<li>
				<a class="delete" warn="请选择一个考试" target="ajaxTodo" title="只有未使用或者已完成的考试才能删除,确定删除吗？" href="teacher/examPlanManager/deleteExamPlan.action?examPlan.epId={tar_epId}"> 
					<span>删除</span>
				</a>
			</li>
			<li>
				<a class="edit" warn="请选择一个考试" fresh="false" rel="eidtExamPlan" target="dialog" title="修改考试编排" href="teacher/examPlanManager/editExamPlan.action?examPlan.epId={tar_epId}" width="600" height="350"> 
					<span>修改</span>
				</a>
			</li>
			<li>
				<a class="icon" warn="请选择一个考试" target="ajaxTodo" title="考试准备后学生将不能做题，确定吗？" href="teacher/examPlanManager/prepareExam.action?examPlan.epId={tar_epId}"> 
					<span>准备</span>
				</a>
			</li>
			<li>
				<a class="icon" warn="请选择一个考试" target="ajaxTodo" title="取消准备后学生将可做题，确定吗？" href="teacher/examPlanManager/canclePrepareExam.action?examPlan.epId={tar_epId}"> 
					<span>取消准备</span>
				</a>
			</li>
			<li>
				<a class="icon" warn="请选择一个考试" target="ajaxTodo" title="考试开始后学生将不能查看其它作业表和考试表，确定吗？" href="teacher/examPlanManager/startExam.action?examPlan.epId={tar_epId}"> 
					<span>开始</span>
				</a>
			</li>
			<li>
				<a class="icon" warn="请选择一个考试" target="ajaxTodo" title="只有正在进行的考试才能中止，确定吗？" href="teacher/examPlanManager/interruptExam.action?examPlan.epId={tar_epId}"> 
					<span>中止</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="10%" align="center">名称</th>
				<th width="10%" align="center">持续时长</th>
				<th width="10%" align="center">开始时间</th>
				<th width="7%" align="center">状态</th>
				<th width="7%" align="center">类型</th>
				<th width="10%" align="center">班级</th>
				<th width="10%" align="center">课程</th>
				<th width="10%" align="center">创建教师</th>
				<th width="10%" align="center">考试计划表</th>
				<th width="10%" align="center">更新时间</th>
				<th width="10%" align="center">创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="exam">
				<tr target="tar_epId" rel="${exam.epId }" >
					<td align="center">${exam.epName }</td>
					<td align="center">${exam.duration }</td>
					<td align="center">${exam.startTime }</td>
					<td align="center">
						<c:if test="${exam.status == 0 }">未使用</c:if>
						<c:if test="${exam.status == 1 }">中止</c:if>
						<c:if test="${exam.status == 2 }">完成</c:if>
						<c:if test="${exam.status == 3 }">正在考试</c:if>
						<c:if test="${exam.status == 4 }">考试准备中</c:if>
					</td>
					<td align="center">
						<c:if test="${exam.kind  == 0 }">手动</c:if>
						<c:if test="${exam.kind  == 1 }">自动</c:if>
					</td>
					<td align="center">${exam.cla.cname }</td>
					<td align="center">${exam.course.courseName }</td>
					<td align="center">${exam.teacher.tname }</td>
					<td align="center">${exam.scheme.vname }</td>
					<td align="center">${exam.updateTime }</td>
					<td align="center">${exam.createTime }</td>
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