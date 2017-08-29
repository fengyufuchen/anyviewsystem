<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
$(function(){
	$("input[name='sType']").bind("click", function(){
		if(this.value == 0){
			$("#paperScoreDiv").hide();
			$("#attiScoreDiv").hide();
			$("div[group='attiDiv']").hide();
		}else if(this.value == 1){
			$("#paperScoreDiv").show();
			$("#attiScoreDiv").show();
			$("div[group='attiDiv']").show();
		}
	});
});
</script>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/comprehensiveScore/saveGradeRules.action" method="post" novalidate="novalidate">
<input type="hidden" name="rule.id" value="${rule.id }"/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>总分类型：</label>
			<input id="onlyPaper" type="radio" name="sType" checked="checked" value="0"/>只有卷面分
			<input id="multScore" type="radio" name="sType" value="1"/>使用卷面分和态度分
		</div>
		<div class="unit" id="paperScoreDiv" style="display: none;">
			<label>卷面分比例：</label>
			<input class="required textInput" type="text"  name="rule.paperRate" value="${rule.paperRate }"/>%
		</div>
		<div class="unit" id="attiScoreDiv" style="display: none;">
			<label>态度分比例：</label>
			<input class="required textInput" type="text"  name="rule.attitudeRate" value="${rule.attitudeRate }"/>%
		</div>
		<div style="margin-top:20px">
			<h2 style="margin-top:20px">卷面分要素</h2>
			<hr/>
		</div>
		<div class="unit">
			<label>程序题未通过得分：</label>
			<input class="required textInput" type="text"  name="rule.programWrongPer" value="${rule.programWrongPer }"/>%
		</div>
		<div class="unit">
			<label>程序题通过默认得分：</label>
			<input class="required textInput" type="text"  name="rule.programRightPer" value="${rule.programRightPer }"/>%
		</div>
		<div style="margin-top:20px; display: none;" group="attiDiv">
			<h2>态度分要素</h2>
			<hr/>
		</div>
		
		<div class="unit" group="attiDiv" style="display: none;">
			<label>程序题最先完成得分：</label>
			<input class="required textInput" type="text"  name="rule.programFirstPer" value="${rule.programFirstPer }"/>%
		</div>
		<div class="unit" group="attiDiv" style="display: none;">
			<label>程序题最后完成得分：</label>
			<input class="required textInput" type="text"  name="rule.programLastPer" value="${rule.programLastPer }"/>%
		</div>
		<hr/>
		<div class="unit">
			<label>程序题一般得分规则描述：</label>
			<textarea name="rule.programGradeDes" style="height:70px;width: 350px">${rule.programGradeDes }</textarea>
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