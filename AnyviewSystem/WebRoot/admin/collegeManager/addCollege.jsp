<%@ page language="java" import="java.util.*"   pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--文件名：addCollege.jsp-->
<!--描   述：添加学院页面-->
<!--时   间 ：2015年09月12日-->

<form  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" rel="admin/collegeManager" action="admin/collegeManager/addCollege.action" method="post" novalidate="novalidate">
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>学校名称：</label>
					<select  style="width:150px" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if> id="select_unIDadd" name="unID" >
						<c:choose>
							<c:when test="${admin.miden==-1 }">
								<option value="" selected="selected">所有学校</option>
								<c:forEach items="${universities }" var="u">
									<option value="${u.unID }">${u.unName }</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
							</c:otherwise>
						</c:choose>
					</select>
		</div>
		<div class="unit">
			<label>学院名称：</label>
			<input class="required textInput" type="text"  name="college.ceName"/>
		</div>
		<div class="unit">
			<label>有效性:</label>
			<input type="radio" name="college.enabled" value="1" checked="checked">正常
			<input type="radio" name="college.enabled" value="0">停用
		</div>
	</div>
	<div class="formBar" >
		<ul>
			<li>
				<div class="buttonActive" >
					<div class="buttonContent">
						<button type="submit">提交</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button" 
				>
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>