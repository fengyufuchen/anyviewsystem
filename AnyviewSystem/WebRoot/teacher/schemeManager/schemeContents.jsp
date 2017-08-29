<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
			
<div class="pageContent">
	<c:forEach items="${contents }" var="sc">
		<div class="sortDiv problemContentDiv" style="padding:15px;overflow: hidden;"  pid="${sc.schemeContent.problem.pid }" kind="${sc.schemeContent.problem.kind}">
			<h1>
			<span name="vchapName">${sc.schemeContent.vchapName }</span>  
			<span name="vpName">${sc.schemeContent.vpName }</span>
			
			<c:choose>
				<c:when test="${sc.schemeContent.problem.kind==0 }">（程序题）</c:when>
				<c:when test="${sc.schemeContent.problem.kind==1 }">（例题）</c:when>
				<c:when test="${sc.schemeContent.problem.kind==2 }">（填空题）</c:when>
				<c:when test="${sc.schemeContent.problem.kind==3 }">（单选题）</c:when>
				<c:when test="${sc.schemeContent.problem.kind==4 }">（多选题）</c:when>
				<c:when test="${sc.schemeContent.problem.kind==5 }">（判断题）</c:when>
			</c:choose>
			<span name="score">${sc.schemeContent.score }</span>分
			<span name="startTime" style="margin-left:300"><fmt:formatDate value="${sc.schemeContent.startTime }" pattern="yyyy.MM.dd HH:mm"/></span>
			<span class="limit">-</span>
			<span name="finishTime"><fmt:formatDate value="${sc.schemeContent.finishTime }" pattern="yyyy.MM.dd. HH:mm"/></span>
			</h1>
			
			<br/>
			<c:choose>
				<c:when test="${sc.schemeContent.problem.kind==3 || sc.schemeContent.problem.kind==4}">
					<pre>${sc.problemContentVO.choiceContent }</pre>
					<c:forEach items="${sc.problemContentVO.options }" var="so">
						<font <c:if test="${so.isRight }">size="3" color="red"</c:if>><pre><b>${so.sequence }</b> ${so.optContent }</pre></font>
					</c:forEach>
				</c:when>
				<c:when test="${sc.schemeContent.problem.kind==5}">
					<pre>${sc.problemContentVO.judgmentContent }</pre>
					<c:choose>
						<c:when test="${sc.problemContentVO.isRight == true }"><span>√</span></c:when>
						<c:otherwise><span>X</span></c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
			<div class="button" style="float: right; ">
				<div class="buttonContent" ><button onclick="selectThisProblem(this);">选择</button>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
function selectThisProblem(btn){
	var $sdiv = $(btn).parent("div").parent("div").parent("div");
	$sdiv.remove();
	$(btn).html("删除");
	$(btn).attr("onclick","deleteThisProblem(this)");
	
	var $vpnameSpan = $sdiv.find("span[name='vpName']");
	var $pscore = $sdiv.find("span[name='score']");
	var $start = $sdiv.find("span[name='startTime']");
	var $finish = $sdiv.find("span[name='finishTime']");
	var vpname = $vpnameSpan.html();
	$vpnameSpan.html('<input type="text" placeholder="请输入题目名称" class="required textInput" group="vpname" value="'+vpname+'"/>');
	$pscore.html('<input type="text" placeholder="分值" onblur="checkScore(this.value);" class="required textInput" group="score" style="width:80px" value="'+$pscore.html()+'"/>');
	$start.html('<input type="text" readonly="readonly" class="date" group="startTime" style="width:150px;" value="'+$start.html()+'" name="startTime" dateFmt="yyyy-MM-dd HH:mm">');
	$finish.html('<input type="text" readonly="readonly" class="date" group="finishTime" style="width:150px;" value="'+$finish.html()+'" name="finishTime" dateFmt="yyyy-MM-dd HH:mm">');
	var dateopts = {pattern:'yyyy-MM-dd HH:mm'};
	$start.find("input.date").datepicker(dateopts);
	$finish.find("input.date").datepicker(dateopts);
	$("#nsSelectedPro").append($sdiv);
	sortProblem();
	var dragop = {
			cursor: 'move', // selector 的鼠标手势
			sortBoxs: 'div.sortDrag', //拖动排序项父容器
			replace: false, //2个sortBox之间拖动替换
			items: '> div', //拖动排序项选择器
			selector: '', //拖动排序项用于拖动的子元素的选择器，为空时等于item
			zIndex: 1000,
			userFunction:sortProblem
		};
	//双击拖动
	$sdiv.dblclick(function(event){
		DWZ.sortDrag.start($("#nsSelectedPro"), $sdiv, event, dragop);
		event.preventDefault();
	});
}
</script>