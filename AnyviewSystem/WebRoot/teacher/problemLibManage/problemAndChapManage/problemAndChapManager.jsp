<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pageContent">
	<div id="problemChapBox" class="unitBox" style="display:block; overflow:auto;" layoutH="450">
		<jsp:include page="problemChapManager.jsp"></jsp:include>
	</div>
	
	<div id="problemBox" class="unitBox" style="display:block; overflow:auto;" layoutH="387">
		<jsp:include page="problemManager.jsp"></jsp:include>
	</div> 
</div>