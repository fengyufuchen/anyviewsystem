<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	//载入原始数据
	$.ajax({
		type:"POST",
		url:"teacher/classManager/checkClassAjax.action",
		dataType:"json",
		data:{cid:$("#cid").val()},
		cache:"false",
		success:function(data)
		{
			var obj = eval("("+data.jsonStr+")");
			$("#cname").val(obj.cname);
			$("#clacid").val(obj.cid);
			$("#startYear").val(obj.startYear);
			$("#specialty").val(obj.specialty);
			$("#epId").val(obj.epId);
			$("#updateTime").val(obj.updateTime);
			
			if(obj.kind == 0) $("#kind_0").attr("selected","selected")
			else if(obj.kind == 1) $("#kind_1").attr("selected","selected");
			
			if(obj.enabled == 0) $("#enabled_0").attr("selected","selected")
			else if(obj.enabled == 1) $("#enabled_1").attr("selected","selected");
			
			if(obj.status == 0) $("#status_0").attr("selected","selected")
			else if(obj.status == 1) $("#status_1").attr("selected","selected")
			else if(obj.status == 2) $("#status_2").attr("selected","selected")
			else if(obj.status == 3) $("#status_3").attr("selected","selected");
		
			var right = data.tcRightStr;
			var i=0;
			for(;i<4;i++){
				if(right[i]==1)
					$("#right"+i).attr("checked","checked");
			}
		}
	});
	
	$("#saveBtn").bind("click",function(){
		$("#editClass").submit();
	});
});
</script>

<form id="editClass" method="post" action="teacher/classManager/saveClass.action" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<input type="hidden" id="cid" value="${cid }"/>
	<input type="hidden" id="clacid" name="cla.cid" value=""/>
	<input type="hidden" id="specialty" name="cla.specialty" value="">
	<input type="hidden" id="epId" name="cla.epId" value="">
	<input type="hidden" id="updateTime" name="cla.updateTime" value="">
	<div class="pageFormContent" layoutH="56">
		<div style="padding: 2px">
			<table>
            <tr>
				<td>班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td><td><input id="cname" name="cla.cname" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;届：</td><td><input id="startYear" name="cla.startYear" value=""/></td>
			</tr>
			<tr>
			<td>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
			<td>
            	<select id="kind" name="cla.kind">
					<option id="kind_0" value="0">普通班级</option>
					<option id="kind_1" value="1">教师映射班级</option>
				</select>
             </td>
			 <td>有效状态：</td>
			 <td>
            	<select id="enabled" name="cla.enabled">
					<option id="enabled_0" value="0">停用</option>
					<option id="enabled_1" value="1">正常</option>
				</select>
              </td>
             </tr>
             <tr>
			  <td>锁定状态：</td>
			  <td>
                <select id="status" name="cla.status">
					<option id="status_0" value="0">未锁定</option>
					<option id="status_1" value="1">登录锁定</option>
					<option id="status_2" value="2">做题锁定</option>
					<option id="status_3" value="3">考试锁定</option>
				</select>
			   </td>
              </tr>
            </table>
			<div style="padding-top: 10px; font-size: small">
				<fieldset>
                <legend>
						<span style="">您对该班级拥有的权限(不可设置)</span>
				</legend>
				<div style="" >
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
		<div style="padding-top: 15px; text-align: center" class="formBar">
			<button type="button" id="saveBtn">保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
		</div>
	</div>
</form>