<%@page import="com.sun.corba.se.impl.oa.poa.AOMEntry"%>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.anyview.entities.*,java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--分页的form-->
<form id="pagerForm"
	action="teacher/combinedGrade/gainStudentsForClass.action"
	method="post"></form>
<div class="pageHeader"></div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" mask="true" target="navTab" fresh="true"
				rel="listStudentExerciseDetail"
				href="teacher/combinedGrade/listStudentExerciseDetail.action?vid={vid}"
				warn="请选择一个学生"> <span>查看计划表个人详情</span>
			</a></li>
		</ul>
	</div>


	<table class="table" width="100%" layoutH="82">
		<thead>
			<tr>
				<th width="10%" align="center" orderField="scs.sid"
					<c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>学号*</th>
				<th width="10%" align="center" orderField="scs.cname"
					<c:if test="${orderField=='cl.college.university.unName' }">class="${orderDirection}"</c:if>>姓名*</th>
				<th width="10%" align="center" orderField="scs.completedCount"
					<c:if test="${orderField=='cl.cname' }">class="${orderDirection}"</c:if>>完成题数*</th>
				<th width="10%" align="center" orderField="scs.totalTime"
					<c:if test="${orderField=='cl.specialty' }">class="${orderDirection}"</c:if>>累计时间*</th>
				<th width="7%" align="center" orderField="scs.totalScore"
					<c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>平均得分*</th>

			</tr>
		</thead>
		<tbody>

			<%!String sname;//该学生的名字
	float totalScore = 0f; //该学生该计划表的总得分
	int completedNum = 0; //在该计划表中，该学生已完成的习题数
	int totalNum = 0; //在该计划表中，该学生的总习题数
	int oldSid;//
	int newSid;
	int totalTime = 0;
	String sno;
	int vid;%>
			<%
				Pagination<ExerciseTable> page1 = (Pagination<ExerciseTable>) request.getAttribute("page");
				List<ExerciseTable> content = page1.getContent();
				ExerciseTable firstExercise = content.get(0);
				oldSid = firstExercise.getStudent().getSid();
				sno = firstExercise.getStudent().getSno();
				totalScore = firstExercise.getScore();
				vid = firstExercise.getScheme().getVid();
				totalNum = 1;
				totalTime = firstExercise.getAccumTime();
				completedNum = firstExercise.getRunResult() == 1 ? 1 : 0;
				sname = firstExercise.getStudent().getSname();
				for (int i = 1; i < content.size(); i++) {
					ExerciseTable e = content.get(i);
					newSid = e.getStudent().getSid();
					if (newSid != oldSid) { //新的学生,将上一个学生的统计数据显示出来，重新统计新的下一位学生
			%>
			<tr target="vid" rel="<%=vid%>">
				<td align="center"><%=sno%></td>
				<td align="center"><%=sname%></td>
				<td align="center"><%=completedNum + "/" + totalNum%></td>
				<td align="center"><%=totalTime%>分钟</td>
				<td align="center"><%=totalScore / totalNum%>分</td>
			</tr>
			<%
				oldSid = newSid;
						sno = e.getStudent().getSno();
						sname = e.getStudent().getSname();
						totalNum = 1;
						vid = e.getScheme().getVid();
						completedNum = e.getRunResult() == 1 ? 1 : 0;
						totalScore = e.getScore();
						totalTime = e.getAccumTime();
					} else {
						totalNum += 1;
						completedNum += e.getRunResult() == 1 ? 1 : 0;
						totalScore += e.getScore();
						totalTime += e.getAccumTime();
					}
				}
			%>
			<!--最后一个学生的信息因为循环退出，没有打印出来  -->
			<tr target="vid" rel="<%=vid%>">
				<td align="center"><%=sno%></td>
				<td align="center"><%=sname%></td>
				<td align="center"><%=completedNum + "/" + totalNum%></td>
				<td align="center"><%=totalTime%>分钟</td>
				<td align="center"><%=totalScore / totalNum%>分</td>
			</tr>
		</tbody>




	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numperPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"
					<c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
				<option value="20"
					<c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
				<option value="30"
					<c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
				<option value="50"
					<c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
				<option value="100"
					<c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select> <span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab"
			totalCount="${page.totalCount }" numPerPage="${page.numPerPage }"
			pageNumShown="${page.totalPageNum }"
			currentPage="${page.currentPage }"></div>
	</div>
</div>