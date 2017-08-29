<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#editTeacher").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
});
</script>

<%-- <script type="text/javascript">
$(function(){
	//学校select
	$("#select_unIDedit").change(function(){
		var url=$("#select_unIDedit").attr("refUrl");
		var unID = $("#select_unIDedit").val();
		if(unID==""){
			var ce = $("#select_unIDedit");
			ce.empty();
			var $ab ;
			ce.append($ab);
		}else{
			$.ajax({
				type:"POST",
				url:url,
				data:{unID:unID},
				cache:"false",
				success:function(data)
				{
					var cols = $.parseJSON(data);
					var ce = $("#select_ceIDedit");
					ce.empty();//先清空下一级select
					var $allCol ;
					ce.append($allCol);
					for(i=0;i<cols.length;i++){
						var $op = $('<option value="'+cols[i].ceID+'">'+cols[i].ceName+'</option>');
						ce.append($op);
					}
				}
			});
		}
	});
	
})
</script>		 --%>
		<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/teacherManager/updateTeacher.action" method="post" novalidate="novalidate">
			<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
				<div class="unit">
				  <label>学校名称：</label>
					<select   style="width: 150px;" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if> id="editTeacher" name="unID" >
						<c:choose>
							<c:when test="${admin.miden==-1 }">
								<c:forEach items="${universities }" var="u">
									<option <c:if test="${u.unID==return_unID }">selected="selected"</c:if>value="${u.unID }">${u.unName }</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="${admin.university.unID }" selected="selected">${admin.university.unName }</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div> 
				<%-- <div class="unit">
				  <label>学院名称:</label>
					<select <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>   style="width:150px" name="ceID" id="select_ceIDedit"  ref="combox_teacher" refUrl="teacher/schemeManager/gainTeacherByCeIdAjax.action">
			           <c:choose>
							<c:when test="${admin.miden==0 }">
								<option value="${admin.college.ceID }" selected="selected">${admin.college.ceName }</option>
							</c:when>
							<c:when test="${admin.miden!=0 }">
								<option id="editCollege" value="${return_ceID }" selected="selected">${return_ceName }</option>
								<c:forEach items="${colleges }" var="c">
									<option value="${c.ceID }">${c.ceName }</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">请选择学校</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div> --%>
				<div class="unit">
					<label>教师账号：</label>
					<input class="required digits textInput" type="text"  name="tea.tno" value="${teacher.tno }"/>
				</div>
				<div class="unit">
					<label>教师姓名:</label>
					<input class="required chinese textInput" type="text" name="tea.tname" value="${teacher.tname }">
				</div>
				<div class="unit">
					<label>密码:</label>
					<input class="required alphanumeric textInput" type="text" name="tea.tpsw" value="${teacher.tpsw }">
				</div>
				<div class="unit">
					<label>性别:</label>
					<select name="tea.tsex" required>  
                      <option value='M' <c:if test="${teacher.tsex=='M'}"> selected="selected"</c:if> >男</option>
                      <option value='F'  <c:if test="${teacher.tsex=='F'}"> selected="selected"</c:if>>女</option>
                    </select>  
				</div>
				<%-- <div class="unit">
					<label>身份:</label>
					<select name="tea.tiden" required>  
                     <option value ="0">普通教师</option>  
                     
                     <option value ="1">学院领导</option>  
                    
                     <c:if test="${admin.miden>=1 }">
                     <option value ="2">学校领导</option>
                     </c:if>  
                     </select>  
				</div> --%>
				<div class="unit">
					<label>是否有效:</label>
					<select name="tea.enabled" required>  
                       <option value ="1">有效</option>  
                       <option value ="0">无效</option>  
                     </select>  
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
			<input type="hidden" name="tea.tid" value="${teacher.tid }"/>
		</form>