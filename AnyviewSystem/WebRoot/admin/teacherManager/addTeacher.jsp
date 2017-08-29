<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(document).ready(function(){
	//初始化学校，学院select
	$("#addTeacher").select2({
		placeholder:"请选择学校",
		allowClear:true,
	});
});
</script>

<%-- <script type="text/javascript">
$(function(){
	//学校select
	$("#select_unIDadd").change(function(){
		var url=$("#select_unIDadd").attr("refUrl");
		var unID = $("#select_unIDadd").val();
		if(unID==""){
			var ce = $("#select_unIDadd");
			ce.empty();
			var $ab = $('<option value="">请选择学校</option>');
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
					var ce = $("#select_ceIDadd");
					ce.empty();//先清空下一级select
					var $allCol = $('<option value="">所有学院</option>');
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
</script>	 --%>	
		<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/teacherManager/addTeacher.action" method="post">
			<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
				<div class="unit">
				  <label>学校名称：</label>
					<select   style="width: 150px;" <c:if test="${admin.miden!=-1 }">disabled="disabled"</c:if> id="addTeacher" name="unID" >
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
				<%-- <div class="unit">
				  <label>学院名称:</label>
					<select <c:if test="${admin.miden==0 }">disabled="disabled"</c:if>   style="width:150px" name="ceID" id="select_ceIDadd"  ref="combox_teacher" refUrl="teacher/schemeManager/gainTeacherByCeIdAjax.action">
			            <c:choose>
							<c:when test="${admin.miden==0 }">
								<option value="${admin.college.ceID }" selected="selected">${admin.college.ceName }</option>
							</c:when>
							<c:when test="${admin.miden!=0 }">
								<option id="Colleges" value="" selected="selected">所有学院</option>
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
					<label>教师姓名：</label>
					<input type="text" name="tname" maxlength="20"   class="required chinese textInput">
				</div>
				<div class="unit">
					<label>教师编号：</label>
					<input type="text" name="tno" maxlength="20" class="required digits textInput">
				</div>
				<div class="unit">
                    <label>教师性别：</label>
                    <input type="radio" name="tsex" value="M" />男
                 	<input type="radio" name="tsex" value="F" /> 女
				</div>
				<%-- <div class="unit">
					<label>教师身份：</label>
					<select name="tiden" required>  
                     <option value ="0">普通教师</option>  
                     
                     <option value ="1">学院领导</option>  
        
                     <c:if test="${admin.miden>=1 }">
                     <option value ="2">学校领导</option>
                     </c:if>  
                     </select>  
				</div> --%>
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
			</div>
		</form>

