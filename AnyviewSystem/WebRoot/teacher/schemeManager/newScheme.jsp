<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="nsAddSchemeForm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)" action="teacher/schemeManager/saveNScheme.action" method="post" novalidate="novalidate">
	<input type="hidden" id="nsSchemeMsg" name="nsSchemeMsg" value=""/>
</form>
<div class="pageContent" style="padding:5px">
	<div id="nsbasediv" class="panel" defH="25">
		<h1>基本信息</h1>
		<div>
			<table class="searchContent" style="float: left;">
				<tr>
					<td style="padding:0 5">
						计划名称:
						<input class="required textInput" type="text" id="nsvname" name="scheme.vname" value=""/>
					</td>
					<td style="padding:0 5">
						课程:
						<select id="newSchemeteacherCoSelect" style="width: 150px;" class="required" name="scheme.course.courseName">
							<c:forEach items="${course }" var="c">
								<option value="${c.courseId }">${c.courseName }</option>
							</c:forEach>
						</select>
					</td>
					<td style="padding:0 5">
						类型：
						<select id="nskind" class="required" name="scheme.kind">
							<option value="0">作业题</option>
							<option value="1">考试题</option>
							<option value="2">资源表</option>
						</select>
					</td>
					<td style="padding:0 5">
						满分值:
						<input class="required textInput" type="text" id="nsfullscore" value="" disabled="disabled" />
					</td>
					<td style="padding:0 5">
						<div class="button"><div class="buttonContent"><button onclick="saveScheme(this);">提交</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="nsselectdiv" class="panel" defH="25" style="display: none;">
		<h1>查询作业表</h1>
		<div>
			<table class="searchContent" style="float: left;">
				<tr>
					<td style="padding:0 5">
						学校:
						<select id="newSchemeUnSelect" style="width: 150px;"></select>
					</td>
					<td style="padding:0 5">
						学院:
						<select id="newSchemeCeSelect" style="width: 150px;"></select>
					</td>
					<td style="padding:0 5" group="scheme">
						课程:
						<select id="newSchemeCoSelect" style="width: 150px;"></select>
					</td>
					<td style="padding:0 5" group="scheme">
						<div class="button"><div class="buttonContent"><button onclick="searchScheme();">查询作业表</button></div></div>
					</td>
					<td style="padding:0 5;display: none;" group="lib">
						<div class="button"><div class="buttonContent"><button onclick="searchLib();">查询题库</button></div></div>
					</td>
					<td style="padding:0 5;display: none;" group="lib">
						<div class="button"><div class="buttonContent"><button onclick="viewLibContent();">查看题库</button></div></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="divider"></div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;" onclick="javascript:$('#nsselectdiv').hide();$('#nsbasediv').show();"><span>已选题目</span></a></li>
					<li><a href="javascript:;" onclick="javascript:$('#nsbasediv').hide();$('#nsselectdiv').show();$('#nsselectdiv').find('td[group=scheme]').show();$('#nsselectdiv').find('td[group=lib]').hide();"><span>从其他作业表选</span></a></li>
					<li><a href="javascript:;" onclick="javascript:$('#nsbasediv').hide();$('#nsselectdiv').show();$('#nsselectdiv').find('td[group=lib]').show();$('#nsselectdiv').find('td[group=scheme]').hide();"><span>从题库选</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:99%">
			<!-- 第一块选项卡 -->
			<div>
				<div id="nsSelectedProDiv" class="borderRadiu10 whiteBgColor" style="width：99%;overflow: auto;" layoutH="125">
					<div id="nsSelectedPro" class="pageContent sortDrag">
						
					</div>
				</div>
			</div>
			<!-- 第二块选项卡 -->
			<div>
				<div class="borderRadiu10 whiteBgColor" style="width:17%;overflow: auto;float: left;" id="nsSchemesDiv" layoutH="125">
					<table class="table" layoutH="160">
						<thead>
							<tr>
								<th width="199" align="center">作业表</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<div id="nsSchemeContentDiv" class="borderRadiu10 whiteBgColor" style="width:82%;overflow: auto; float:right;" layoutH="125">
				</div>
			</div>
			<!-- 第三块选项卡 -->
			<div>
				<div class="borderRadiu10 whiteBgColor" style="width:17%;overflow: auto;float: left;" id="nsLibsDiv" layoutH="125">
					<table class="table" layoutH="160">
						<thead>
							<tr>
								<th width="199" align="center">题库</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<div id="nsLibContentDiv" class="borderRadiu10 whiteBgColor" style="width:82%;overflow: auto; float:right;" layoutH="125">
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>	
<script src="common/js/addScheme.js" type="text/javascript"></script>