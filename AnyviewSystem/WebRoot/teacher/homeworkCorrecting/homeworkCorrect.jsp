<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<link rel="stylesheet" type="text/css"
	href="../../common/js/jQ_Easy/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../common/js/jQ_Easy/themes/icon.css">
<script type="text/javascript" src="../../common/js/jQ_Easy/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="../../common/js/jQ_Easy/jq_easyui.min.js"></script>
<script type="text/javascript" src="../../common/js/common.js"></script>
<script type="text/javascript" src="../../common/js/correct-homework/analy_work.js"></script>
<script type="text/javascript" src="../../common/js/correct-homework/run_test.js"></script>
		
<!--文件名：universityAdminStudent.jsp-->
<!--描   述：校级管理员的学生管理页面-->
<!--时   间 ：2015年08月29日-->

<script type="text/javascript">

//$(function(){
	
	//$("#sp").select2({
		//placeholder : "请选择",
		//allowClear : true
	//});
	
//});
</script>

<div class="pageHeader">
				
<!--查询的form-->
<form id="searchClass" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/adminstudentManager/getAdminStudentManagerPage.action" method="post">
	 <input type="hidden" name="pageNum" id="pageNum" value="1" />
	 <input type="hidden" name="numPerPage" id="numPerPage" value="${page.numPerPage }" />
	 <input type="hidden" name="orderField" value="${orderField}" />  
     <input type="hidden" name="orderDirection" value="${orderDirection}" />
			<div class="searchBar">
			<div id="vId"
	style="padding: 5px 5px; font-size:12px;overflow: visible">
	课程表：
    <select id="courseCombo" style="width:100px"></select>
	<div id="sp">
		<div style="color:#99BBE8;background:#fafafa;padding:5px;">选择课程</div>
		 <div style="padding:10px">
			<input type="radio" name="lang" value="1"><span>离散数学</span><br/>
		    <input type="radio" name="lang" value="2"><span>C程序设计</span><br/>
		    <input type="radio" name="lang" value="3"><span>数据结构</span><br/>
		    <input type="radio" name="lang" value="4"><span>C语言</span><br/>
	     </div>
    </div>
	班级：
	<select id="classCombo"></select>
	作业表：
	<select id="schemeCombo"></select>
</div>
						
			
		</div>
	</form>
</div>
			

<div class="pageContent">
	<div
					style="float: left; text-align: left; width: 95%; padding: 5px 5px;font-size:12px;">
					<label for="sort_for_ex">
					<input id="sort_for_ex" type="radio" name="sort" checked="true"
						value="exercise" />
						按习题显示
					</label>
					<label for="sort_for_stu">
					<input id="sort_for_stu" type="radio" name="sort" value="student" />
						按学生显示
					</label>
				</div>
			<div style="height: 172px; width: 100%; overflow-y: hidden;">
				<div id="ExSmallGrid" style="width: 500px; height:100%;">
					<table id="datagrid1"></table>
				</div>
				<div id="StuSmallGrid" style="width: 500px;height:100%; ">
					<table id="datagrid3"></table>
				</div>
		    </div>
		
			<div style="height: 200px; width: 100%;overflow-y: hidden;  ">
				<div id="ExBigGrid" style=" width: 100%;">
					<table id="datagrid2"></table>
				</div>
				<div id="StuBigGrid" style=" width: 100%;">
					<table id="datagrid4"></table>
				</div>	
			</div>
			<div align="center">
			     <input type="button" value="批改作业" id="analyWork">
			     <input type="button" value="保存资料" id="saveData">
            </div>
	<div  title="批改作业" id="analy" style="width: 850px;height:500px;top: 1px;float: left;">
						<div  style="padding: 5px; overflow: hidden;float: left;">
							<textarea id="text_TAnswer" title="正确答案" readonly="readonly"
								style="width: 405px; height: 200px"></textarea>
						</div>
						<div 
							style="padding: 5px; overflow: hidden;float: left;">
							<textarea id="text_StuAnswer" title="学生答案"
								style="width: 405px; height: 200px"></textarea>
						</div>
					    <div style="padding: 5px; overflow: hidden;float: left;">
							<textarea id="text_title" title="题目文档" readonly="readonly"
								style="width: 405px; height: 200px"></textarea>
						</div>
						<div  style="padding: 5px;  overflow: hidden;float: left;">
							<textarea id="text_comment" title="教师评语"
								style="width: 405px; height: 200px"></textarea>
							<br>
						</div>
						<div align="center" style="height: 10px; clear:both;">
				                                          习题得分：
				            <input id="exScore" class="easyui-numberbox" min="0" max="100"
					         precision="1" required="true" style="width: 115px" />
			          
			                <input type="button" value="保存批改" id="saveComment">
                       </div>
				</div>
				
		<div title="保存学生的做题资料"
			style="padding-left: 0px; border: 1px solid black; top: 100px;"
			id="saveStuDataDiv" >
			<div  style="padding: 10px 8px;float: left">
				<div class="catagorySaveDiv" style="padding-left: 8px;margin:15px 0px;">
					<fieldset style="width: 300px;">
						<legend>
							<span>分类保存</span>
						</legend>
						<label for="radio1_1">
						<input id="radio1_1" type="radio" name="radio1" value=1
						checked="checked" />
						按习题保存（把每条习题的学生的资料放在一起）
						</label>
						<br>
						<label for="radio1_2">
						<input id="radio1_2" type="radio" name="radio1" value=2 />
						按学生保存（把每个学生的习题的资料放在一起）
						</label>
					</fieldset>
				</div>
				<div style="padding-left: 8px;margin:15px 0px;">
					<fieldset style="width: 300px;">
						<legend>
							<span>保存内容</span>
						</legend>
						<label for="button1">
						<input type="radio" name="radio2" id="button1" value=1 />
						所有学生所有习题
						</label>
						<br>
						<label for="button2">
						<input type="radio" name="radio2" id="button2" value=2
						checked="checked" />
						指定学生和习题
						</label>
					</fieldset>
				</div>
				<div id="button3"
					style="text-align: center; padding-left: 8px; padding-top: 10px;margin:15px 0px;">
					<form action="" method="post">
					<a class="easyui-linkbutton"  id="saveFile">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" id="cancel">退出</a>
					<input type="hidden" name="sort" id="sort"/>
					<input type="hidden" name="proIdArr" id="proIdArr"/>
					<input type="hidden" name="stuIdArr" id="stuIdArr"/>
					<input type="hidden" name="cid" id="cid"/>
					<input type="hidden" name="schemeId" id="schemeId"/>
					<input type="hidden" name="courseId" id="courseId"/>
					</form>
				</div>
			</div>
			<div  style="float: left;" id="hideDiv">
				<div id="ExBigGrid" style="float: left; background: #fff;">
					<table id="data1" style="height: 500px; min-height: 500px;"></table>
				</div>
				<div id="StuBigGrid" style="float: left; background: #fff;">
					<table id="data2" style="height: 500px; min-height: 500px;"></table>
				</div>
			</div>
		</div>
</div>