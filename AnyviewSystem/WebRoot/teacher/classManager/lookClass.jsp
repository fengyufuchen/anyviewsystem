<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	$("#exitBtn").bind("click",function(){
		$.pdialog.closeCurrent();
	});
	
	$.ajax({
		type:"POST",
		url:"teacher/classManager/checkClassAjax.action",
		dataType:"json",
		data:{cid:$("#cid").val()},
		cache:"false",
		success:function(data)
		{
			var obj = eval("("+data.jsonStr+")");
			$("#cname").html(obj.cname);
			$("#startYear").html(obj.startYear);
			if(obj.kind == 0) $("#kind").html("普通班级")
			else if(obj.kind == 1) $("#kind").html("教师映射班级");
			if(obj.enabled == 0) $("#enabled").html("停用")
			else if(obj.enabled == 1) $("#enabled").html("正常");
			if(obj.status == 0) $("#status").html("未锁定")
			else if(obj.status == 1) $("#status").html("登录锁定")
			else if(obj.status == 2) $("#status").html("做题锁定")
			else if(obj.status == 3) $("#status").html("考试锁定");
			$("#updateTime").html(obj.updateTime);
			
			var right = data.tcRightStr;
			var i=0;
			for(;i<4;i++){
				if(right[i]==1)
					$("#right"+i).attr("checked","checked");
			}
		}
	});
});
</script>

<div class="pageFormContent" layoutH="56">
<input type="hidden" id="cid" value="${cid }"/>
	<div id="lookDiv">
		<div style="padding: 2px">
			<table class="showMsgTable">
	            <tr>
					<td>班名：<span id="cname"></span></td>
					<td>年届：<span id="startYear"></span></td> 
					<td>更新时间：<span id="updateTime"></span></td>
				</tr>
	            <tr>
					<td>类型：<span id="kind"></span></td> 
					<td>有效状态：<span id="enabled"></span></td>
					<td>锁定状态：<span id="status"></span></td>
				</tr>
            </table>
            
			<div style="padding-top: 10px; font-size: small">
				<fieldset>
					<legend>
						<span style="">您对该班级拥有的权限(不可设置)</span>
					</legend>
					<div style="">
						<div style="width: 50%; float: left">
							<input id="right0" type="checkbox" disabled="disabled"/>
							查看学生信息
							<br/>
							<input id="right1" type="checkbox" disabled="disabled"/>
							设置班级状态
						</div>
						<div style="width: 50%; float: left">
							<input id="right2" type="checkbox" disabled="disabled"/>
							重置学生密码
							<br/>
							<input id="right3" type="checkbox" disabled="disabled"/>
							管理学生
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</div>

</div>


