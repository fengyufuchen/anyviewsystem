<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<form id="deleteCollege" method="post" action="admin/collegeManager/deleteCollege.action" 
    class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<input type="hidden" id="ceID" name="ceID" value="${college.ceID }" >

	<div class="pageFormContent" layoutH="56">
		<div style="padding: 20px">
		<table height="150px">
			<tr>
			   <td bgcolor="#FFCC33">请确认删除信息:</td>
			</tr>
            <tr>
            	<td>学校名称：</td>
				<td>${unName }</td>
			</tr>
			<tr>
				<td>学院名称：</td>
				<td>${college.ceName}</td>
			</tr>
			<tr>
            	<td>有效性：</td>
            	<td>
					<c:choose>
						<c:when test="${ college.enabled == 0 }">停用</c:when>
						<c:when test="${college.enabled == 1 }">正常</c:when>
					</c:choose>
				</td>
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