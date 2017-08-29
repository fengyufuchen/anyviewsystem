<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：universityAdminStudent.jsp-->
<!--描   述：校级管理员的学生管理页面-->
<!--时   间 ：2015年08月29日-->

<script type="text/javascript">

$(function(){
	
	$("#superAdminManagerCeSelected").select2({
		placeholder : "请选择学院",
		allowClear : true
	});
	$("#superAdminManagerClaSelected").select2({
		placeholder : "请选择班级",
		allowClear : true
	});
	
	//级联2
	$("#superAdminManagerCeSelected").on("change",function(e){
		if(this.value==""){
			$("#superAdminManagerClaSelected").html(null);
			$("#superAdminManagerClaSelected").val(null);
			$("#superAdminManagerClaSelected").select2({
				placeholder : "请选择班级",
				allowClear : true
			});
			return;
		}
		$.ajax({
			type:"POST",
			url:"communion/gainClassByCeIdAjax.action",
			data:{ceId:$("#superAdminManagerCeSelected").val()},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				$("#superAdminManagerClaSelected").html(null);
				$("#superAdminManagerClaSelected").html(null);
				$("#superAdminManagerClaSelected").select2({
					placeholder : "请选择班级",
					allowClear : true
				});
				var clas = $.parseJSON(data);
				$("#superAdminManagerClaSelected").append($('<option></option>'));
				for(var i=0; i<clas.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",clas[i].id);
					opt.html(clas[i].text);
					$("#superAdminManagerClaSelected").append(opt);
				}
			}
		});
	});
	
});

//全选与反选
function checkselectall(selectall){
	if(selectall.checked)
		$("input[name=sids]:checkbox").attr("checked",true);
	else
		$("input[name=sids]:checkbox").attr("checked",false);
}

//批删除提交
function delectstudent(){
	var objs=document.getElementsByName('sids');
	var isSel=false;//判断是否有选中项，默认为无
	for(var i=0;i<objs.length;i++)
	{
	  if(objs[i].checked==true)
	   {
	    isSel=true;
	    break;
	   }
	}
	if(isSel==false)
	{
		 alertMsg.error("请选择要删除的数据！"); 
	}else
	{
		alertMsg.confirm("确定要删除选中项么？",{
			okCall:function(){
				$("#deletestudnet").submit();
			},
			cancelCall:function(){
			}
		});
	}
}
</script>

<!--分页的form-->
<form id="pagerForm" action="admin/adminstudentManager/getAdminStudentManagerPage.action" method="post" >
</form>		

<div class="pageHeader">
				
<!--查询的form-->
<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminstudentManager/getAdminStudentManagerPage.action" method="post">
	 <input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	 <input type="hidden" name="orderField" value="${orderField}" />  
     <input type="hidden" name="orderDirection" value="${orderDirection}" />
			<div class="searchBar">
			<table class="searchContent">
			<tbody>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;院：
					<select id="superAdminManagerCeSelected" name="condition.college.ceID" style="width: 150px;">
						<option></option>
						<c:forEach items="${colleges }" var="ce">
							<option value="${ce.ceID }" <c:if test="${(!empty condition.college) && condition.college.ceID==ce.ceID }">selected="selected"</c:if> >${ce.ceName }</option>
						</c:forEach>
					</select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;班&nbsp;&nbsp;&nbsp;&nbsp;级：
					<select id="superAdminManagerClaSelected" name="condition.cid" style="width: 150px;">
						<option></option>
						<c:forEach items="${clas }" var="cla">
							<option value="${cla.cid }" <c:if test="${(!empty condition.cid) && condition.cid==cla.cid }">selected="selected"</c:if> >${cla.cname }</option>
						</c:forEach>
						
					</select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;号：
					<input class="textInput" type="text" name="conditionstu.sno" alt="输入学生账号(可选)" value="${conditionstu.sno }"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;名：
					<input class="textInput" type="text" name="conditionstu.sname" alt="输入学生姓名(可选)" value="${conditionstu.sname }"/>
				</td>
			</tr>
			</tbody>
			</table>
						
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" id="searchBtn">
									检索
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
			

<div class="pageContent">
	<form id="deletestudnet" rel="pagerForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="admin/adminstudentManager/deleteStudent.action" method="post">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" mask="true" target="dialog" fresh="true" rel="adminstudentManager" href="admin/adminstudentManager/addStudent.action">
					<span>添加学生</span>
				</a>
			</li>
			<li>
				<a class="add" mask="true" target="navTab" fresh="true" rel="" href="admin/adminstudentManager/getBatAdminAddStudentPage.action" external="true" title="批处理">
					<span>批处理</span>
				</a>
			</li>
			<li >
				<a class="edit" mask="true" target="dialog" fresh="true" href="admin/adminstudentManager/editStudent.action?sid={sid}" warn="请选择一个学生">
					<span>修改</span>
				</a>
			</li>
			<li>
				<a class="edit" mask="true" target="ajaxTodo" title="确定要初始化密码为该学生的学号吗?" fresh="true" href="admin/adminstudentManager/initPassword.action?sid={sid}" warn="请选择一个学生">
					<span>初始化密码</span>
				</a>
			</li>
			<li>
				<a class="delete" id="deleteall" onclick="delectstudent();">
					<span>删除选择</span>
				</a>
			</li>
			<li >
				<a class="icon" href="admin/adminstudentManager/getAdminStudentManagerPage.action" target="navTab" fresh="true" rel="adminstudentManager" title="学生管理">
					<span>刷新</span>
				</a>
			</li>
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="4%" align="center"><input type="checkbox" id="selectall" onclick="checkselectall(this);"></th>
				<th width="15%" align="center">学校</th>
				<th width="15%" align="center" orderField="sno" <c:if test="${orderField=='sno' }">class="${orderDirection}"</c:if> >学号*</th>
				<th width="15%" align="center" orderField="sname" <c:if test="${orderField=='sname' }">class="${orderDirection}"</c:if> >姓名*</th>
				<th width="15%" align="center" >学院</th>
				<th width="15%" align="center" >班级</th>
				<th width="6%" align="center">性别</th>
				<th width="10%" align="center">有效状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="cs">
				<tr target="sid" rel="${cs.student.sid}">
					<td align="center"><input name="sids"  type="checkbox" value="${cs.student.sid}"></td>
					<td align="center">${cs.student.university.unName }</td>
					<td align="center">${cs.student.sno }</td>
					<td align="center">${cs.student.sname }</td>
					<td align="center">${cs.cla.college.ceName }</td>
					<td align="center">${cs.cla.cname }</td>
					<td align="center">
						<c:choose>
							<c:when test="${cs.student.ssex == 'M' }">男</c:when>
							<c:otherwise>女</c:otherwise>
						</c:choose>
					</td>
					<td align="center">
						<c:choose>
							<c:when test="${cs.student.enabled == 0 }">停用</c:when>
							<c:when test="${cs.student.enabled == 1 }">正常</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numperPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="10" <c:if test="${page.numPerPage==10}">selected="selected"</c:if>>10</option>
					<option value="20" <c:if test="${page.numPerPage==20}">selected="selected"</c:if>>20</option>
					<option value="30" <c:if test="${page.numPerPage==30}">selected="selected"</c:if>>30</option>
					<option value="50" <c:if test="${page.numPerPage==50}">selected="selected"</c:if>>50</option>
					<option value="100" <c:if test="${page.numPerPage==100}">selected="selected"</c:if>>100</option>
			</select>
			<span> 条，共${page.totalCount }条</span>
		</div>
		<!--分页组件-->
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="${page.totalPageNum }" currentPage="${page.currentPage }"></div>
	</div>
</div>