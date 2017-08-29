<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function(){
	
	//载入原始数据
	$.ajax({
		type:"POST",
		url:"admin/classTeacherManager/editClassTeacherRight.action",
		dataType:"json",
		data:{cid:$("#cid").val(),tid:$("#tid").val()},
		cache:"false",
		success:function(data)
		{
			var obj = eval("("+data.jsonStr+")");
			$("#cname").val(obj.cname);
			$("#clacid").val(obj.cid);
			$("#startYear").val(obj.startYear);
			$("#specialty").val(obj.specialty);
			$("#hhhtid").val($("#tid").val());
			
			if(obj.kind == 0) $("#kind_0").attr("selected","selected")
			else if(obj.kind == 1) $("#kind_1").attr("selected","selected");
		
			var right = data.tcRightStr;
			var tiden = data.tiden;
			var i=0;
			for(;i<4;i++){
				if(tiden == 1 || tiden == 2)
					$("#right"+i).attr("disabled","disabled");
				if(right[i] == 1)
					$("#right"+i).attr("checked","checked");
			}
			if(tiden == 1 || tiden == 2) {
				$("#saveBtn").attr("disabled","disabled");
			}
		}
	});
	
	$("#saveBtn").bind("click",function(){
		$("#editClassTeacherRight").submit();
	});
});
</script>

<form id="editClassTeacherRight" method="post" action="admin/classTeacherManager/saveTCRight.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<input type="hidden" name="hhhtid" id="hhhtid" value=""/>
	<input type="hidden" id="clacid" name="cla.cid" value=""/>
	<div class="pageFormContent" layoutH="56">
		<div style="padding: 2px">
			<table>
				<td>班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td><td><input id="cname" name="cla.cname" value="" disabled="disabled"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;届：</td><td><input id="startYear" name="cla.startYear" value="" disabled="disabled"/></td>
			</tr>
			<tr>
			<td>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</td>
			<td>
            	<select id="kind" name="cla.kind" disabled="disabled">
					<option id="kind_0" value="0">普通班级</option>
					<option id="kind_1" value="1">教师映射班级</option>
				</select>
             </td>
             <td>专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td><td><input id="specialty" name="cla.specialty" value="" disabled="disabled"/></td>
             </tr>
            </table>
			<div style="padding-top: 10px; font-size: small">
				<fieldset>
                <legend>
						<span style="">此教师对该班级拥有的权限：</span>
				</legend>
				<div style="" >
					<div style="width: 50%; float: left">
						<input id="right0" type="checkbox" name="right" value="1"/>
						查看学生信息
						<br/>
						<input id="right1" type="checkbox" name="right" value="2"/>
						设置班级状态
					</div>
					<div style="width: 50%; float: left">
						<input id="right2" type="checkbox" name="right" value="4"/>
						重置学生密码
						<br/>
						<input id="right3" type="checkbox" name="right" value="8"/>
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