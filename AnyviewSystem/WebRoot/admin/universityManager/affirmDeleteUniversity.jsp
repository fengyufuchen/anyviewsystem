<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<form id="deleteUniversity" method="post" action="admin/universityManager/deleteUniversity.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<input type="hidden" id="unID" name="unID" value="${univer.unID }" >

	<div class="pageFormContent" layoutH="56">
		<div style="padding: 20px">
		<table height="150px">
			<tr>
			<td bgcolor="#FFCC33">请确认删除信息:</td>
			</tr>
            <tr>
            	<td>学校名称：</td>
				<td>${univer.unName }</td>
			</tr>
			<tr>
				<td>独立IP：</td>
				<td>${univer.ip }</td>
			</tr>
			<tr>
				<td>独立Port：</td>
				<td>${univer.port }</td>
			</tr>
			<tr>
            	<td>属性：</td>
            	<td>
					<c:choose>
						<c:when test="${univer.attr == 0 }">本地服务器</c:when>
						<c:when test="${univer.attr == 1 }">独立服务器</c:when>
					</c:choose>
				</td>
            </tr>
            <tr>
				<td>状态：</td>
				<td>
					<c:choose>
						<c:when test="${univer.enabled == 0 }">停用</c:when>
						<c:when test="${univer.enabled == 1 }">正常</c:when>
					</c:choose>
				</td>
	         </tr>
	        <tr>
				<td>验证码：</td>
				<td>${univer.verification }</td>
			</tr>
			
	    </table>
		</div>
	</div>
	<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">确认删除</button>
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