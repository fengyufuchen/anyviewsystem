<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="common/js/schemeManager/commonSettings.js" type="text/javascript"></script>
<script src="common/js/schemeManager/addScheme.js" type="text/javascript"></script>
<form id="addSchemeForm" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone)" action="teacher/schemeManager/saveNewScheme.action" method="post" novalidate="novalidate">
<input type="hidden" id="schemeProMsg" name="schemeProMsg" value=""/>
<input type="hidden" id="fullScoreValue" name="scheme.fullScore">
<div class="pageContent" style="padding:5px">
	<div class="panel" defH="30">
		<h1>基本信息</h1>
		<div>
			<select class="combox" name="scheme.course.courseId" style="width:150px;margin-right:20px;">
				<option value="">课程</option>
				<c:forEach items="${course }" var="c">
					<option value="${c.courseId }">${c.courseName }</option>
				</c:forEach>
			</select>
			<select class="combox" name="scheme.kind" style="width:100px;margin-right:20px;">
				<option value="0">作业题</option>
				<option value="1">考试题</option>
				<option value="2">资源表</option>
			</select>
			<select class="combox" name="scheme.visit" style="width:100px;margin-right:20px;">
				<option value="0">私有</option>
				<option value="1">部分公开</option>
				<option value="2">本学院公开</option>
				<option value="3">本校公开</option>
				<option value="4">完全公开</option>
			</select>
			<select class="combox" name="scheme.status" style="width:100px;margin-right:20px;">
				<option value="0">停用</option>
				<option value="1">测试</option>
				<option value="2">正式</option>
			</select>
			<div class="pages" style="float:left;">
				<label>计划名称:</label>
				<input class="required textInput" type="text" name="scheme.vname" />
			</div>
			<div class="pages" style="float:left;">
				<label>满分值:</label>
				<input class="required textInput" id="fullScore" type="text" disabled="disabled" value="0"/>
			</div>
			<div class="buttonActive" style="float:right;">
				<div class="buttonContent">
					<button type="button" onclick="submitAddSchemeForm();">提交</button>
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
					<ul id="problemChapZtree" class="ztree"></ul>
				</div>
				<!-- 右边题目表格 -->
				<div class="unitBox" style="margin-left:220px;border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
					<div class="panelBar">
						<ul class="toolBar">
							<li>
								<a class="edit" href="#" onclick="getLidsUrl(this,'problemChapZtree');" target="ajax" rel="jbsxBox">
									<span>查询题目</span>
								</a>
							</li>
							<li>
								<a class="edit" fresh="true" warn="请选择题目" rel="lookProblem" target="dialog" href="teacher/problemManager/lookProblem.action?pid={tarpid}">
									<span>查看题目</span>
								</a>
							</li>
							<li>
								<a class="edit" href="#" onclick="choosePros('chooseRadio','pidChk','choosedTab');">
									<span>选定题目</span>
								</a>
							</li>
						</ul>
					</div>
					<div id="jbsxBox">
						
					</div>
				</div>
			</div>
			<!-- 第二块选项卡 -->
			<div>
				<div class="panelBar">
					<ul class="toolBar">
					<li>
						<a class="edit" href="teacher/schemeManager/modifyProNameAndVDip.jsp" target="dialog" rel="modifyProNameAndVDip" fresh="false">
							<span>设置题目</span>
						</a>
					</li>
					<li>
						<a class="delete" href="#" onclick="deleteChoosedPro('chooseRadio');">
							<span>删除</span>
						</a>
					</li>
					<li>
						<a class="icon" href="#" onclick="lookProMsg('chooseRadio','proContentDiv','proMemoDiv','proTipDiv');">
							<span>查看内容</span>
						</a>
					</li>
					</ul>
				</div>
				<div>
					<div id="choosedProBox" style="width:70%;float:left;" layoutH="180">
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
							<tbody id="choosedTab">
							</tbody>
						</table>
					</div>
					
					<div class="accordion" style="width:29%;float:left;">
						<div class="accordionHeader">
							<h2><span>icon</span>题目内容</h2>
						</div>
						<div id="proContentDiv" class="accordionContent" style="height:260px">
							
						</div>
						<div class="accordionHeader">
							<h2><span>icon</span>提示</h2>
						</div>
						<div id="proMemoDiv" class="accordionContent">
							
						</div>
						<div class="accordionHeader">
							<h2><span>icon</span>备注,题目简介</h2>
						</div>
						<div id="proTipDiv" class="accordionContent">
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>