<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
$(function(){
	$("#addExamPlan_course", $.pdialog.getCurrent()).bind("change", function(){
		var refUrl="teacher/examPlanManager/gainSchemesAjax.action?";
		var cid = $("#addExamPlan_class", $.pdialog.getCurrent()).val();
		var courseId = $("#addExamPlan_course", $.pdialog.getCurrent()).val();
		$.ajax({
			type:"POST",
			url:refUrl,
			dataType:"json",
			data:{cid:cid, courseId:courseId},
			cache:"false",
			success:function(json)
			{
				var $ref = $("#addExamPlan_scheme", $.pdialog.getCurrent());
				if (!json) return;
				var html = '';

				$.each(json, function(i){
					if (json[i] && json[i].length > 1){
						html += '<option value="'+json[i][0]+'">' + json[i][1] + '</option>';
					}
				});
				
				var $refCombox = $ref.parents("div.combox:first");
				$ref.html(html).insertAfter($refCombox);
				$refCombox.remove();
				$ref.trigger("change").combox();
			},
			error: DWZ.ajaxError
		});
	});
	
	$("input[name='examPlan.kind']",$.pdialog.getCurrent()).bind("change",function(){
		if(this.value==0){
			$("#startTimeDiv",$.pdialog.getCurrent()).hide();
		}else{
			$("#startTimeDiv",$.pdialog.getCurrent()).show();
		}
	});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/examPlanManager/saveExamPlan.action" method="post" novalidate="novalidate">
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>测验名称：</label>
			<input class="required textInput" type="text"  name="examPlan.epName"/>
		</div>
		<div class="unit">
			<label>测验时长(分钟)：</label>
			<input class="required textInput" type="text"  name="examPlan.duration"/>
		</div>
		<div class="unit">
			<label>类型:</label>
			<input type="radio" name="examPlan.kind" value="0" checked="checked"/>手动
			<input type="radio" name="examPlan.kind" value="1"/>自动
		</div>
		<div class="unit" id="startTimeDiv" style="display: none;">
			<label>开始时间：</label>
			<input type="text" value="" readonly="readonly" class="date" style="width:150px;" name="examPlan.startTime" dateFmt="yyyy-MM-dd HH:mm:ss">
		</div>
		<div class="unit">
			<label>班级:</label>
			<select id="addExamPlan_class" name="examPlan.cla.cid" class="combox" ref="addExamPlan_course" refUrl="teacher/examPlanManager/gainCoursesAjax.action?cla.cid={value}">
				<option value="">请选择班级</option>
				<c:forEach items="${clas }" var="c">
					<option value="${c.cid }">${c.cname }</option>
				</c:forEach>
			</select>
		</div>
		<div class="unit">
			<label>课程:</label>
			<select name="examPlan.course.courseId" class="combox" id="addExamPlan_course">
				<option value="">请选择课程</option>
			</select>
		</div>
		<div class="unit">
			<label>考试表:</label>
			<select name="examPlan.scheme.vid" class="combox" id="addExamPlan_scheme">
				<option value="">请选择考试表</option>
			</select>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">提交</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>