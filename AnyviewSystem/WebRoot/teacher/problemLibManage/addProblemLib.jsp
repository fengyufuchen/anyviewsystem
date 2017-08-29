<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
//根据id获取对应的下标,找不到则返回-1
function getGroupArrayIndex(groupArr, id){
	for(var jj=0;jj<groupArr.length;jj++){
		if($(groupArr[jj]).attr("gid")==id)
			return jj;
	}
	return -1;
}
//添加所有的groupArr中的group,    obj为要追加的jquery对象
function appendAllGroup(obj, groupArr){
	for(var nn=0;nn<groupArr.length;nn++){
		obj.append(groupArr[nn]);
	}
}
var sInit = {
		unSelect : $("#unSelect"),
		ceSelect : $("#ceSelect"),
		teSelect : $("#teSelect"),
		init:function(){
			//初始化
			this.unSelect.select2({
				  ajax: {
				    url: "communion/gainUniversityBySearchAjax.action",
				    dataType: 'json',
				    delay: 250,
				    data: function (params) {
						  	return {
						        q: params.term, // search term
						        page: params.page
						    };
				    	  },
				    processResults : function (data, page) {
				    	return {
				        	results : data
				        };
				    },
				    cache: true
				  },
				  placeholder : "选择学校...",
				  allowClear :true,
				  createSearchChoice   : function(term, data) {// 创建搜索结果（使用户可以输入匹配值以外的其它值）
				       return { id: term, text: term };
				   },
				  formatSelection : function (item) { return item.id; }, // 选择结果中的显示
				  formatResult : function (item) { return item.id; },  // 搜索列表中的显示
				  escapeMarkup : function (markup) { return markup; }, //字符转义
				  minimumInputLength: 1//限制输入最小字符数
				  
			});
			this.ceSelect.select2({
				placeholder: "选择学院...",
				allowClear :true,
			});
			this.teSelect.select2({
				placeholder: "选择教师...",
				allowClear :true,
			});
		}
};
$(function(){
	$("#libVisitSelect").bind("change",function(){
		if(this.value==1)
			$("#accessTeacherDiv").show();
		else
			$("#accessTeacherDiv").hide();
	});
	//初始化
	sInit.init();
	//设置ceSelect和teSelect动态事件
	sInit.unSelect.on("change",function(e){
		//组合所有已选择的id
		var objs = sInit.unSelect.select2('data');
		var ids = '';
		for(i=0;i<objs.length;i++){
			ids += objs[i].id+',';
		}
		$.ajax({
			type:"POST",
			url:"communion/gainCollegeByUnIdsAjax.action",
			data:{unIds:ids},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				sInit.ceSelect.html(null);
				var uuu = $.parseJSON(data);
				var groupArr = new Array();//创建一个group数组
				for(var i=0;i<uuu.length;i++){
					var opt = $('<option></option>');
					opt.attr("value",uuu[i].id);//这里的value属性对应select2('data')取到的id属性
					opt.html(uuu[i].text);
					//如果groupArr中没有，则新建，否则取出
					var index = getGroupArrayIndex(groupArr,uuu[i].gid);
					if(index != -1){
						groupArr[index].append(opt);
					}else{
						var group = $('<optgroup/>');
						group.attr('gid',uuu[i].gid);
						group.attr('label',uuu[i].gtext);
						group.append(opt);
						groupArr.push(group);
					}
				}
				appendAllGroup(sInit.ceSelect, groupArr);
			}
		});
	});
	sInit.ceSelect.on("change",function(e){
		var objs = sInit.ceSelect.select2('data');
		var ids = '';
		for(var i=0;i<objs.length;i++){
			ids += objs[i].id+',';
		}
		$.ajax({
			type:"POST",
			url:"communion/gainTeacherByCeIdsAjax.action",
			data:{ceIds:ids},
			cache:"false",
			success:function(data)
			{
				//先清空原option
				sInit.teSelect.html(null);
				var uuu = $.parseJSON(data);
				var groupArr = new Array();//创建一个group数组
				for(var i=0;i<uuu.length;i++){
					var opt = $('<option></option>');
					opt.attr("value",uuu[i].id);//这里的value属性对应select2('data')取到的id属性
					opt.html(uuu[i].text);
					//如果groupArr中没有，则新建，否则取出
					var index = getGroupArrayIndex(groupArr,uuu[i].gid);
					if(index != -1){
						groupArr[index].append(opt);
					}else{
						var group = $('<optgroup/>');
						group.attr('gid',uuu[i].gid);
						group.attr('label',uuu[i].gtext);
						group.append(opt);
						groupArr.push(group);
					}
				}
				appendAllGroup(sInit.teSelect, groupArr);
			}
		});
	});
	
	$("#submitAddLibForm").bind("click",function(){
		var stidObjs = sInit.teSelect.select2('data');
		var teids = '';
		for(var i=0;i<stidObjs.length;i++){
			teids += stidObjs[i].id+',';
		}
		$("#accessTeaIds").val(teids);
		$("#addLibForm").submit();
	});
});
</script>

<form id="addLibForm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/problemLibManager/saveProblemLib.action" method="post" novalidate="novalidate">
<input type="hidden" id="accessTeaIds" name="accessTeaIds" value=""/>
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
		<div class="unit">
			<label>题库名称：</label>
			<input class="required textInput" type="text"  name="problemLib.lname"/>
		</div>
		<div class="unit">
			<label>类别:</label>
			<input class="required textInput" type="text" name="problemLib.kind">
		</div>
		<div class="unit">
			<label>访问级别:</label>
			<select id="libVisitSelect" class="required combox" name="problemLib.visit">
				<option value="0">私有</option>
				<option value="1" >部分公开</option>
				<option value="2">本学院公开</option>
				<option value="3">本校公开</option>
				<option value="4">完全公开</option>
			</select>
		</div>
		<div class="unit" id="accessTeacherDiv" style="display: none;">
			<label>选择可访问教师:</label>
			<div style="float: left;width: 400px;border: thick 2px red;">
				<div style="margin-bottom: 10px;">
					<select id="unSelect" multiple="multiple" class="select2-offscreen" style="width: 100%;"></select>
				</div>
				<div style="margin-bottom: 10px;">
					<select id="ceSelect" multiple="multiple" style="width: 100%;"></select>
				</div>
				<div style="margin-bottom: 10px;">
					<select id="teSelect" multiple="multiple" style="width: 100%;"></select>
				</div>
			</div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="submitAddLibForm">提交</button>
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