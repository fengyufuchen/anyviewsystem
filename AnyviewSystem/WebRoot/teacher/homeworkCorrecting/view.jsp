<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="pageContent" id="exerciseview" layoutH="42">
	<c:forEach items="${esc }" var="sc">
		<div class="sortDiv problemContentDiv" style="padding:15px;overflow: hidden;"  pid="${sc.exercise.problem.pid }" kind="${sc.exercise.problem.kind}">
			<h1>
			<span name="vchapName">${sc.sv.schemeContent.vchapName }</span>  
			<span name="vpName">${sc.sv.schemeContent.vpName }</span>
			
			<c:choose>
				<c:when test="${sc.exercise.problem.kind==0 }">（程序题）</c:when>
				<c:when test="${sc.exercise.problem.kind==1 }">（例题）</c:when>
				<c:when test="${sc.exercise.problem.kind==2 }">（填空题）</c:when>
				<c:when test="${sc.exercise.problem.kind==3 }">（单选题）</c:when>
				<c:when test="${sc.exercise.problem.kind==4 }">（多选题）</c:when>
				<c:when test="${sc.exercise.problem.kind==5 }">（判断题）</c:when>
			</c:choose>
			<span name="score">${sc.sv.schemeContent.score }</span>分
			<span name="startTime" style="margin-left:300"><fmt:formatDate value="${sc.sv.schemeContent.startTime }" pattern="yyyy.MM.dd HH:mm"/></span>
			<span class="limit">-</span>
			<span name="finishTime"><fmt:formatDate value="${sc.sv.schemeContent.finishTime }" pattern="yyyy.MM.dd. HH:mm"/></span>
			</h1>
			
			<br/>
			<c:choose>
				<c:when test="${sc.sv.schemeContent.problem.kind==3 || sc.sv.schemeContent.problem.kind==4}">
					<pre>${sc.sv.problemContentVO.choiceContent }</pre>
					<c:forEach items="${sc.sv.problemContentVO.options }" var="so">
						<font <c:if test="${sc.sv.problemContentVO.isRight }">size="3" color="red"</c:if>><pre><b>${so.sequence }</b> ${so.optContent }</pre></font>
					</c:forEach>
				</c:when>
				<c:when test="${sc.sv.schemeContent.problem.kind==5}">
					<pre>${sc.sv.problemContentVO.judgmentContent }</pre>
					<c:choose>
						<c:when test="${sc.sv.problemContentVO.isRight == true }"><span>√</span></c:when>
						<c:otherwise><span>X</span></c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
			<div class="divider"></div>
			<div style="position: relative; min-height: 50px;">
				<label>学生答案：</label>
				<p>${sc.exercise.econtent }</p>
				<input class="textInput" type="text" value="${sc.exercise.score }" maxlength="5" style="position: absolute;right: 0;top: 0;"/>
				<a class="button" href="#" onclick="commitScore(${sc.exercise.eid },this,${sc.sv.schemeContent.score })" style="position: absolute;right: 70;top: 25;"><span>确定</span></a>
				<a class="button" href="teacher/homeworkCorrecting/commentPage.action?exercise.eid=${sc.exercise.eid }" target="dialog" width="610" height="315" fresh="false" style="position: absolute;right: 0;top: 25;"><span>添加批注</span></a>
			</div>
		</div>
	</c:forEach>
</div>

<script>
function commitScore(eid, o, max){
	var obj = $(o);
	var score = obj.prev("input").val();
	if(score.trim().length==0 || isNaN(score.trim())){
		alertMsg.error("请输入正确的分数");
		return false;
	}
	if(parseInt(score)>max){
		alertMsg.error("本题满分为"+max);
		return false;
	}
	$.ajax({
		url:"teacher/homeworkCorrecting/updateScore.action",
		data:{"exercise.eid":eid,"exercise.score":score},
	}).done(function(response){
		if(response=="success"){
			alertMsg.correct("评分成功");
		}else{
			alertMsg.error("评分失败");
		}
	});
}
</script>