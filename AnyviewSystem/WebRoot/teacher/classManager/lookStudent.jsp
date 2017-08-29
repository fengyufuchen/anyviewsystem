<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	$("#exitBtn").bind("click",function(){
		$.pdialog.closeCurrent();
	});
	
});
</script>

<div class="pageFormContent" layoutH="56">
	<div id="lookDiv">
	姓名：${student.sname }<br/>
	姓名：${student.sno }<br/>
	姓名：${student.ssex }<br/>
	姓名：${student.sattr }<br/>
	姓名：${student.enabled }<br/>
	姓名：${student.loginStatus }<br/>
	姓名：${student.logTime }<br/>
	姓名：${student.logIp }<br/>
	姓名：${student.logPort }<br/>
	</div>
	<div style="padding-top: 15px; text-align: center" class="formBar">
		<button type="button" id="exitBtn">关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
	</div>

</div>


