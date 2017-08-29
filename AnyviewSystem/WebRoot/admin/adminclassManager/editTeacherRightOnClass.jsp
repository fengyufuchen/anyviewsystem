<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/adminclassManager/saveTeacherRightOnClass.action" method="post" novalidate="novalidate">
	<input type="hidden" name="ct.cla.cid" value="${cc.cla.cid }"/>
	<input type="hidden" name="ct.teacher.tid" value="${cc.teacher.tid }"/>
	
	<div class="pageFormContent" layouth="58">	
		<table id="addTeacherForClassTable" class="table" width="100%" layoutH="82" targetType="dialog">
		<thead>
			<tr>
				<th width="5%" align="center"><input type="checkbox" <c:if test="${cc.tcRight==15 }">checked="checked"</c:if>  onclick="checkAll('atc_tcrightCheckbox','dialog', this.checked)"/></th>
				<th width="5%" align="center">权限图标</th>
				<th width="20%" align="center">权限详情</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td align="center">
					<input type="checkbox" name="atc_tcrightCheckbox" value="1" <c:if test="${cc.tcRight%2==1 }">checked="checked"</c:if> />
				</td>
				<td align="center"><span class="tag_viewStuState" style="float: none;margin: 2 auto;" title="在线查看学生状态"></span></td>
				<td align="center">在线查看学生状态</td>
			</tr>
			<tr>
				<td align="center">
					<input type="checkbox" name="atc_tcrightCheckbox" value="2" <c:if test="${cc.tcRight==2||cc.tcRight==3||cc.tcRight==6||cc.tcRight==7||cc.tcRight==10||cc.tcRight==11||cc.tcRight==14||cc.tcRight==15}">checked="checked"</c:if> />
				</td>
				<td align="center"><span class="tag_setCLassState" style="float: none;margin: 2 auto;" title="设置班级状态（考试用）"></span></td>
				<td align="center">设置班级状态（考试用）</td>
			</tr>
			<tr>
				<td align="center">
					<input type="checkbox" name="atc_tcrightCheckbox" value="4" <c:if test="${cc.tcRight==4||cc.tcRight==5||cc.tcRight==6||cc.tcRight==7||cc.tcRight==12||cc.tcRight==13||cc.tcRight==14||cc.tcRight==15}">checked="checked"</c:if> />
				</td>
				<td align="center"><span class="tag_resetStuPsw" style="float: none;margin: 2 auto;" title="重置学生密码"></span></td>
				<td align="center">重置学生密码</td>
			</tr>
			<tr>
				<td align="center">
					<input type="checkbox" name="atc_tcrightCheckbox" value="8" <c:if test="${cc.tcRight>=8}">checked="checked"</c:if> />
				</td>
				<td align="center"><span class="tag_stuManager" style="float: none;margin: 2 auto;" title="学生管理（增删改查）"></span></td>
				<td align="center">学生管理（增删改查）</td>
			</tr>
		</tbody>
	</table>
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
