<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="common/js/schemeManager/commonSettings.js" type="text/javascript"></script>
<script src="common/js/schemeManager/addSchemeProblems.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$.ajax({
		type:"POST",
		url:"teacher/schemeManager/getSchemeProblemsAjax.action",
		data:{vid:$("#vid_2").val()},
		cache:"false",
		success:function(data)
		{
			var pros = $.parseJSON(data);
			var choosedTab = $("#choosedTabTwo");
			for(var i=0;i<pros.length;i++){
				var tr = 
					'<tr class="">'+
						'<td align="center"><input type="radio" name="chooseRadioTwo" value="'+pros[i].pid+'"/></td>'+
						'<td align="center">'+pros[i].pname+'</td>'+
						'<td align="center">'+pros[i].vpName+'</td>'+
						'<td align="center">'+pros[i].degree+'</td>'+
						'<td align="center">'+pros[i].kind+'</td>'+
						'<td align="center">'+pros[i].pdir+'</td>'+
						'<td align="center">'+pros[i].vchapName+'</td>'+
						'<td align="center">'+pros[i].lib+'</td>'+
						'<td align="center">'+pros[i].startTime+'</td>'+
						'<td align="center">'+pros[i].finishTime+'</td>'+
						'<td align="center">'+pros[i].score+'</td>'+
					'</tr>';
				choosedTab.append(tr);
			}
		}
	});
});

</script>
<form id="addSchemeFormTwo" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone)" action="teacher/schemeManager/updateSchemePros.action" method="post" novalidate="novalidate">
<input type="hidden" id="vid_2" name="scheme.vid" value="${scheme.vid }">
<input type="hidden" id="schemeProMsg_2" name="schemeProMsg" value=""/>
<input type="hidden" id="fullScoreValue_2" name="scheme.fullScore">
<div class="pageContent" style="padding:5px">
	<div class="panel" defH="30">
		<h1>基本信息</h1>
		<div>
			<select class="combox" style="width:150px;margin-right:20px;" disabled="disabled">
				<option value="">${scheme.course.courseName }</option>
			</select>
			<select class="combox" style="width:100px;margin-right:20px;" disabled="disabled">
				<option value="0" <c:if test="${scheme.kind==0 }">selected="selected"</c:if>>作业题</option>
				<option value="1" <c:if test="${scheme.kind==1 }">selected="selected"</c:if>>考试题</option>
				<option value="2" <c:if test="${scheme.kind==2 }">selected="selected"</c:if>>资源表</option>
			</select>
			<select class="combox" style="width:100px;margin-right:20px;" disabled="disabled">
				<option value="0" <c:if test="${scheme.visit==0 }">selected="selected"</c:if>>私有</option>
				<option value="1" <c:if test="${scheme.visit==1 }">selected="selected"</c:if>>部分公开</option>
				<option value="2" <c:if test="${scheme.visit==2 }">selected="selected"</c:if>>本学院公开</option>
				<option value="3" <c:if test="${scheme.visit==3 }">selected="selected"</c:if>>本校公开</option>
				<option value="4" <c:if test="${scheme.visit==4 }">selected="selected"</c:if>>完全公开</option>
			</select>
			<select class="combox" style="width:100px;margin-right:20px;" disabled="disabled">
				<option value="0" <c:if test="${scheme.status==0 }">selected="selected"</c:if>>停用</option>
				<option value="1" <c:if test="${scheme.status==1 }">selected="selected"</c:if>>测试</option>
				<option value="2" <c:if test="${scheme.status==2 }">selected="selected"</c:if>>正式</option>
			</select>
			<div class="pages" style="float:left;">
				<label>计划名称:</label>
				<input class="required textInput" type="text" value="${scheme.vname }" disabled="disabled"/>
			</div>
			<div class="pages" style="float:left;">
				<label>满分值:</label>
				<input class="required textInput" id="fullScoreTwo" type="text" disabled="disabled" value="${scheme.fullScore }"/>
			</div>
			<div class="buttonActive" style="float:right;">
				<div class="buttonContent">
					<button type="button" onclick="submitAddSchemeForm_2();">提交</button>
				</div>
			</div>
		</div>
		
	</div>
	<div class="divider"></div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>选择题目</span></a></li>
					<li><a href="javascript:;"><span>已选题目</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<!-- 第一块选项卡 -->
			<div>
				<!-- 左边树形菜单 -->
				<div layoutH="130" style="float:left; display:block; overflow:auto; width:213px; border:solid 1px #CCC; line-height:21px; background:#fff">
					<ul id="problemChapZtreeTwo" class="ztree"></ul>
				</div>
				<!-- 右边题目表格 -->
				<div class="unitBox" style="margin-left:220px;border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
					<div class="panelBar">
						<ul class="toolBar">
							<li>
								<a class="edit" href="#" onclick="getLidsUrl_2(this,'problemChapZtreeTwo');" target="ajax" rel="jbsxBoxTwo">
									<span>查询题目</span>
								</a>
							</li>
							<li>
								<a class="edit" fresh="true" warn="请选择题目" rel="lookProblem" target="dialog" href="teacher/problemManager/lookProblem.action?pid={tarpid}">
									<span>查看题目</span>
								</a>
							</li>
							<li>
								<a class="edit" href="#" onclick="choosePros('chooseRadioTwo','pidChkTwo','choosedTabTwo');">
									<span>选定题目</span>
								</a>
							</li>
						</ul>
					</div>
					<div id="jbsxBoxTwo">
						
					</div>
				</div>
			</div>
			<!-- 第二块选项卡 -->
			<div>
				<div class="panelBar">
					<ul class="toolBar">
					<li>
						<a class="edit" href="teacher/schemeManager/modifyProNameAndVDipTwo.jsp" target="dialog" rel="modifyProNameAndVDipTwo" fresh="false">
							<span>设置题目</span>
						</a>
					</li>
					<li>
						<a class="delete" href="#" onclick="deleteChoosedPro('chooseRadioTwo');">
							<span>删除</span>
						</a>
					</li>
					<li>
						<a class="icon" href="#" onclick="lookProMsg('chooseRadioTwo','proContentDivTwo','proMemoDivTwo','proTipDivTwo');">
							<span>查看内容</span>
						</a>
					</li>
					</ul>
				</div>
				<div>
					<div id="choosedProBoxTwo" style="width:70%;float:left;" layoutH="180">
						<table class="list">
							<thead>
								<tr>
									<th width="2%" align="center"></th>
									<th width="10%" align="center">原题目名称</th>
									<th width="10%" align="center">新题目名称</th>
									<th width="4%" align="center">难度</th>
									<th width="10%" align="center">题目类型</th>
									<th width="20%" align="center">原所属目录</th>
									<th width="10%" align="center">新虚拟目录</th>
									<th width="10%" align="center">所属题库</th>
									<th width="10%" align="center">允许开始时间</th>
									<th width="10%" align="center">要求完成时间</th>
									<th width="4%" align="center">分值</th>
								</tr>
							</thead>
							<tbody id="choosedTabTwo">
							</tbody>
						</table>
					</div>
					
					<div class="accordion" style="width:29%;float:left;">
						<div class="accordionHeader">
							<h2><span>icon</span>题目内容</h2>
						</div>
						<div id="proContentDivTwo" class="accordionContent" style="height:260px">
							
						</div>
						<div class="accordionHeader">
							<h2><span>icon</span>提示</h2>
						</div>
						<div id="proMemoDivTwo" class="accordionContent">
							
						</div>
						<div class="accordionHeader">
							<h2><span>icon</span>备注,题目简介</h2>
						</div>
						<div id="proTipDivTwo" class="accordionContent">
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>