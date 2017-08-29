<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<form class="pageForm required-validate" 
 action="admin/teacherManager/linkToCollege.action" onsubmit="return validateCallback(this, dialogAjaxDone)"method="post" novalidate="novalidate">
		<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
						<div class="unit">
							<label>教师姓名:</label>
							<input value=${tname} type="text" disabled="disabled"/>
						</div>
						<div class="unit">
							<label>教师ID:</label>
							<input value=${tid} type="text" disabled="disabled"/>
						</div>
					   <c:if test="${admin.miden!=-1 }"> 
							<div class="unit">
							  <label>学院名称:</label>
								<select  style="width:200px" name="ceID" id="select_ceID" >
											<option id="allCollege"  <c:if test="${empty ceID}"></c:if>  value="" >请选择学院</option>
											<c:forEach items="${colleges }" var="c">
												<option value="${c.ceID }"  > ${c.ceName }</option>
											</c:forEach>
								</select>
							</div>
						</c:if> 
					<div class="unit">
					<label>是否有效:</label>
					<select name="enabled" required>  
                       <option value ="1">有效</option>  
                       <option value ="0">无效</option>  
                     </select>  
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
			<input type="hidden" name="tid" value="${tid }"/>
   </div>
</form>
