<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
$(function(){
	var p = $(':radio[name="chooseRadio"][checked]');
	var tr = p.parent().parent();
	var tds = tr.find("td");
	var ins = $("#mpnvBaseDiv").find("input");
	$(ins[0]).val($(tds[2]).html());
	$(ins[1]).val($(tds[6]).html());
	$(ins[2]).val($(tds[8]).html());
	$(ins[3]).val($(tds[9]).html());
	$(ins[4]).val($(tds[10]).html());
});
</script>

<div id="mpnvBaseDiv" class="pageFormContent" layouth="58" style="height: 100px; overflow: auto;">
	<div class="unit">
		<label>新题目名：</label>
		<input id="newProName" class="text" type="text"/>
	</div>
	<div class="unit">
		<label>新虚拟目录名：</label>
		<input id="newProDir" class="text" type="text"/>
	</div>
	<div class="unit">
		<label>允许开始时间：</label>
		<input id="startTime" class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss">
		<a class="inputDateButton" href="javascript:;">选择</a>
	</div>
	<div class="unit">
		<label>要求完成时间：</label>
		<input id="finishTime" class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss">
		<a class="inputDateButton" href="javascript:;">选择</a>
	</div>
	<div class="unit">
		<label>分值：</label>
		<input id="proScore" class="text" type="text"/>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="buttonActive">
				<div class="buttonContent">
					<button onclick="confirmMProND()" type="button">确定</button>
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