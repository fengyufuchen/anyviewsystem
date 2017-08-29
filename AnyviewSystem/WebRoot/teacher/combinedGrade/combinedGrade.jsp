<%@ page contentType="text/html;charset=UTF-8"
	import="java.util.*,com.anyview.entities.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!--文件名：similarityDetection.jsp-->
<!--描   述：代码相似检测页面-->
<!--时   间 ：2016年01月20日-->

<script type="text/javascript">
	$(function() {
		//初始化学校，学院select
		$("#ClassSelected1").select2({
			placeholder : "请选择班级",
			allowClear : true
		});
		$("#CourseSelected1").select2({
			placeholder : "请选择课程",
			allowClear : true
		});
		$("#SchemeSelected1").select2({
			placeholder : "请选择作业表",
			allowClear : true
		});
		//级联
		$("#ClassSelected1").on("change", function(e) {
			if (this.value == "") {
				$("#CourseSelected1").html(null);
				$("#CourseSelected1").val(null);
				$("#CourseSelected1").select2({
					placeholder : "请选择课程",
					allowClear : true
				});

				$("#SchemeSelected1").html(null);
				$("#SchemeSelected1").val(null);
				$("#SchemeSelected1").select2({
					placeholder : "请选择作业表",
					allowClear : true
				});
				return;
			}
			$.ajax({
				type : "POST",
				url : "communion/gainCourseByTidAndClaidAjax.action",
				data : {
					ClaId : $("#ClassSelected1").val()
				},
				cache : "false",
				success : function(data) {
					//先清空原option
					$("#CourseSelected1").html(null);
					$("#CourseSelected1").select2({
						placeholder : "请选择课程",
						allowClear : true
					});
					$("#SchemeSelected1").html(null);
					$("#SchemeSelected1").select2({
						placeholder : "请选择作业表",
						allowClear : true
					});
					var courses = $.parseJSON(data);
					$("#CourseSelected1").append($('<option></option>'));
					for (var i = 0; i < courses.length; i++) {
						var opt = $('<option></option>');
						opt.attr("value", courses[i].id);
						opt.html(courses[i].text);
						$("#CourseSelected1").append(opt);
					}
				}
			});
		});

		//级联2
		$("#CourseSelected1").on("change", function(e) {
			if (this.value == "") {
				$("#SchemeSelected1").html(null);
				$("#SchemeSelected1").val(null);
				$("#SchemeSelected1").select2({
					placeholder : "请选择作业表",
					allowClear : true
				});
				return;
			}
			$.ajax({
				type : "POST",
				url : "communion/gainSchemeByClaIdAndCouIdAjax.action",
				data : {
					ClaId : $("#ClassSelected1").val(),
					CouId : $("#CourseSelected1").val()
				},
				cache : "false",
				success : function(data) {
					//先清空原option
					$("#SchemeSelected1").html(null);
					$("#SchemeSelected1").html(null);
					$("#SchemeSelected1").select2({
						placeholder : "请选择作业表",
						allowClear : true
					});
					var schemes = $.parseJSON(data);
					$("#SchemeSelected1").append($('<option></option>'));
					for (var i = 0; i < schemes.length; i++) {
						var opt = $('<option></option>');
						opt.attr("value", schemes[i].id);
						opt.html(schemes[i].text);
						$("#SchemeSelected1").append(opt);
					}
				}
			});
		});

	});
	
</script>

<!--分页的form-->
<form id="pagerForm"
	action="teacher/combinedGrade/gainCombinedGradeManagerPage.action"
	method="post"></form>

<div class="pageHeader">

	<!--查询的form-->
	<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/combinedGrade/gainCombinedGradeManagerPage.action" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tbody>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;班级： <select id="ClassSelected1"
							name="conditioncla.cla.cid" style="width: 150px;">
								<option></option>
								<c:forEach items="${clas }" var="claes">
									<option value="${claes.cla.cid }"
										<c:if test="${(!empty conditioncla.cla) && conditioncla.cla.cid==claes.cla.cid }">selected="selected"</c:if>>${claes.cla.cname }</option>
								</c:forEach>
						</select>
						</td>
						<td>课程： <select id="CourseSelected1"
							name="conditioncou.course.courseId" style="width: 150px;">
								<option></option>
								<c:forEach items="${courses }" var="cou">
									<option value="${cou.course.courseId }"
										<c:if test="${(!empty conditioncou.course) && conditioncou.course.courseId==cou.course.courseId }">selected="selected"</c:if>>${cou.course.courseName }</option>
								</c:forEach>
						</select>
						</td>
						<td>作业表： <select id="SchemeSelected1"
							name="conditionsch.scheme.vid" style="width: 150px;">
								<option></option>
								<c:forEach items="${schemes }" var="sch">
									<option value="${sch.scheme.vid }"
										<c:if test="${(!empty conditionsch.scheme) && conditionsch.scheme.vid==sch.scheme.vid }">selected="selected"</c:if>>${sch.scheme.vname }</option>
								</c:forEach>
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
								<button type="submit" id="searchBtn">获取习题</button>
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
			<li><a id="juanmian" class="icon" mask="true" target="navTab" fresh="true"
				warn="请选择一个学生"> <span>计算卷面分</span>
			</a></li>
			<li><a class="icon" mask="true" target="navTab" fresh="true"
				rel="listStudentExerciseDetail"
				href="teacher/combinedGrade/listStudentExerciseDetail.action?vid={vid}"
				warn="请选择一个学生"> <span>计算态度分</span>
			</a></li>
			<li><a class="icon" mask="true" target="navTab" fresh="true"
				rel="listStudentExerciseDetail"
				href="teacher/combinedGrade/listStudentExerciseDetail.action?vid={vid}"
				warn="请选择一个学生"> <span>计算综合分</span>
			</a></li>
			<li><a class="icon" mask="true" target="navTab" fresh="true"
				rel="listStudentExerciseDetail"
				href="teacher/combinedGrade/listStudentExerciseDetail.action?vid={vid}"
				warn="请选择一个学生"> <span>一键计算</span>
			</a></li>
			<li><a class="icon" mask="true" target="navTab" fresh="true"
				rel="listStudentExerciseDetail"
				href="teacher/combinedGrade/listStudentExerciseDetail.action?vid={vid}"
				warn="请选择一个学生"> <span>保存服务器</span>
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
					<c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>卷面分*</th>
				<th width="7%" align="center" orderField="scs.totalScore"
					<c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>态度分*</th>
				<th width="7%" align="center" orderField="scs.totalScore"
					<c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>综合分*</th>
				<th width="7%" align="center" orderField="scs.totalScore"
					<c:if test="${orderField=='cl.startYear' }">class="${orderDirection}"</c:if>>排名*</th>

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
				//仅当content的size大于0，才显示数据
				if (content.size() > 0) {
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
			<tr target="sid" rel="<%=oldSid%>">
				<td width="10%" align="center"><%=sno%></td>
				<td width="10%" align="center"><%=sname%></td>
				<td width="10%" align="center"><%=completedNum + "/" + totalNum%></td>
				<td width="10%" align="center"><%=totalTime%>分钟</td>
				<td width="10%" align="center"><%=totalScore / totalNum%>分</td>
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
			<tr target="vid" rel="<%=oldSid%>">
				<td align="center"><%=sno%></td>
				<td align="center"><%=sname%></td>
				<td align="center"><%=completedNum + "/" + totalNum%></td>
				<td align="center"><%=totalTime%>分钟</td>
				<td align="center"><%=totalScore / totalNum%>分</td>
			</tr>
			<%
				}
			%>
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