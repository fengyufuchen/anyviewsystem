<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<div class="pageContent sortDrag" selector="h1" layoutH="42">
	<c:forEach items="${exerciseAnswers }" var="ea">
		<div class="panel collapse" minH="100" defH="150">
			<h1>
			<c:choose>
				<c:when test="${ea.problem.kind == 0 }">程序题</c:when>
				<c:when test="${ea.problem.kind == 1 }">例题</c:when>
				<c:when test="${ea.problem.kind == 2 }">填空题</c:when>
				<c:when test="${ea.problem.kind == 3 }">单选题</c:when>
				<c:when test="${ea.problem.kind == 4 }">多选题</c:when>
				<c:when test="${ea.problem.kind == 5 }">判断题</c:when>
			</c:choose>
			-${ea.problem.pname } 
				得分：${ea.exercise.score }
			</h1>
			<div>
				<p>题目：${ea.problem.pcontent }</p>
				<br/>
				<p>学生答案：${ea.exercise.econtent }</p>
			</div>
		</div>
	</c:forEach>
</div>