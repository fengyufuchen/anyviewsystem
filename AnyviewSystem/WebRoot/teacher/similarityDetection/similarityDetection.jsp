<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：similarityDetection.jsp-->
<!--描   述：代码相似检测页面-->
<!--时   间 ：2016年01月20日-->

<script type="text/javascript">

$(function(){
	//初始化学校，学院select
	$("#ClassSelected").select2({
		placeholder : "请选择班级",
		allowClear : true
	});
	$("#CourseSelected").select2({
		placeholder : "请选择课程",
		allowClear : true
	});
	$("#SchemeSelected").select2({
		placeholder : "请选择作业表",
		allowClear : true
	});
	//级联
	$("#ClassSelected").on("change",function(e){
		if(this.value==""){
			$("#CourseSelected").html(null);
			$("#CourseSelected").val(null);
			$("#CourseSelected").select2({
				placeholder : "请选择课程",
				allowClear : true
			});
			
			$("#SchemeSelected").html(null);
			$("#SchemeSelected").val(null);
			$("#SchemeSelected").select2({
				placeholder : "请选择作业表",
				allowClear : true
			});
			return;
		}
		$.ajax({
			type:"POST",
			url:"communion/gainCourseByTidAndClaidAjax.action",
			data:{ClaId:$("#ClassSelected").val()},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				$("#CourseSelected").html(null);
				$("#CourseSelected").select2({
					placeholder : "请选择课程",
					allowClear : true
				});
				$("#SchemeSelected").html(null);
				$("#SchemeSelected").select2({
					placeholder : "请选择作业表",
					allowClear : true
				});
				var courses = $.parseJSON(data);
				$("#CourseSelected").append($('<option></option>'));
				for(var i=0; i<courses.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",courses[i].id);
					opt.html(courses[i].text);
					$("#CourseSelected").append(opt);
				}
			}
		});
	});
	
	//级联2
	$("#CourseSelected").on("change",function(e){
		if(this.value==""){
			$("#SchemeSelected").html(null);
			$("#SchemeSelected").val(null);
			$("#SchemeSelected").select2({
				placeholder : "请选择作业表",
				allowClear : true
			});
			return;
		}
		$.ajax({
			type:"POST",
			url:"communion/gainSchemeByClaIdAndCouIdAjax.action",
			data:{ClaId:$("#ClassSelected").val(),CouId:$("#CourseSelected").val()},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				$("#SchemeSelected").html(null);
				$("#SchemeSelected").html(null);
				$("#SchemeSelected").select2({
					placeholder : "请选择作业表",
					allowClear : true
				});
				var schemes = $.parseJSON(data);
				$("#SchemeSelected").append($('<option></option>'));
				for(var i=0; i<schemes.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",schemes[i].id);
					opt.html(schemes[i].text);
					$("#SchemeSelected").append(opt);
				}
			}
		});
	});
	
});

//全选与反选
function checkselectall(selectall){
	if(selectall.checked)
		$("input[name=ids]:checkbox").attr("checked",true);
	else
		$("input[name=ids]:checkbox").attr("checked",false);
}

//批下载提交
function downloadanswer(){
	var objs=document.getElementsByName('ids');
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
		 alertMsg.error("请选择要打包的习题答案！"); 
	}else
	{
		alertMsg.confirm("确定要打包选中的习题答案么？",{
			okCall:function(){
				$("#downloadanswer").submit();
				//var url="/sd";
				//location.href="../../teacher/similarityDetection/sd";
				//document.getElementById("ifile").src=url;
				setTimeout("SecondStep()", 6000);
				
			},
			cancelCall:function(){
			}
		});
	}
}

function SecondStep(){
	window.location.href='teacher/similarityDetection/DownLoad.zip';
}

</script>

<!--分页的form-->
<form id="pagerForm" action="teacher/similarityDetection/gainSimilarityDetectionPage.action" method="post" >
</form>		

<div class="pageHeader">
				
<!--查询的form-->
<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="teacher/similarityDetection/gainSimilarityDetectionPage.action" method="post">
	 <input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
			<div class="searchBar">
			<table class="searchContent">
			<tbody>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;班级：
					<select id="ClassSelected" name="conditioncla.cla.cid" style="width: 150px;">
						<option></option>
						<c:forEach items="${clas }" var="claes">
							<option value="${claes.cla.cid }" <c:if test="${(!empty conditioncla.cla) && conditioncla.cla.cid==claes.cla.cid }">selected="selected"</c:if> >${claes.cla.cname }</option>
						</c:forEach>
					</select>
				</td>
				<td>课程：
					<select id="CourseSelected" name="conditioncou.course.courseId" style="width: 150px;">
						<option></option>
						<c:forEach items="${courses }" var="cou">
							<option value="${cou.course.courseId }" <c:if test="${(!empty conditioncou.course) && conditioncou.course.courseId==cou.course.courseId }">selected="selected"</c:if> >${cou.course.courseName }</option>
						</c:forEach>
					</select>
				</td>
				<td>作业表：
					<select id="SchemeSelected" name="conditionsch.scheme.vid" style="width: 150px;">
						<option></option>
						<c:forEach items="${schemes }" var="sch">
							<option value="${sch.scheme.vid }" <c:if test="${(!empty conditionsch.scheme) && conditionsch.scheme.vid==sch.scheme.vid }">selected="selected"</c:if> >${sch.scheme.vname }</option>
						</c:forEach>
					</select>
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
									获取习题
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
	<form id="downloadanswer" rel="pagerForm" onsubmit="return validateCallback(this, dialogAjaxDone);" action="teacher/similarityDetection/downloadAnswer.action" method="post">
	<input type="hidden" name="cides" id="cides" value="${cides }" />
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="add" id="downloadall" onclick="downloadanswer();">
					<span>下载答案</span>
				</a>
			</li>
<%-- 			<li>
				<a class="add" href="teacher/similarityDetection/DownLoad.zip" target="dwzExport" rel="downloadzip" targetType="navTab"  title="确定要下载选中的答案吗?（提示：先打包才能下载！）">
					<span>下载答案</span>
				</a>
			</li> --%>
			<li >
				<a class="edit" mask="true" target="navTab" fresh="true" rel="onlinetesting" href="teacher/similarityDetection/onlineTesting.action">
					<span>在线检测</span>
				</a>
			</li>
			<li >
				<a class="icon" href="teacher/similarityDetection/gainSimilarityDetectionPage.action" target="navTab" fresh="true" rel="similarityDetection" title="代码检测">
					<span>刷新</span>
				</a>
			</li>	
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="5%" align="center"><input type="checkbox" id="selectall" onclick="checkselectall(this);"></th>
				<th width="19%" align="center">章名</th>
				<th width="19%" align="center">习题名</th>
				<th width="19%" align="center">分值</th>
				<th width="19%" align="center">开始时间</th>
				<th width="19%" align="center">结束时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content}" var="sst">
				<tr target="id" rel="${sst.id}">
					<td align="center"><input name="ids"  type="checkbox" value="${sst.id}"></td>
					<td align="center">${sst.vchapName }</td>
					<td align="center">${sst.vpName }</td>
					<td align="center">${sst.score }</td>
					<td align="center">${sst.startTime }</td>
					<td align="center">${sst.finishTime }</td>
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