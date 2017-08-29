<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
var setting = {  
        data: {  
            simpleData: {  
                enable: true  
            }  
        },
        async: {  
            enable: true,  
            url:"problemManager/getNextProblemDirAjax",  
            autoParam:["lid=lid","id=parentId"],  
//             otherParam:{"otherParam":"zTreeAsyncTest"},  
            dataFilter: filter  
        },
        check: {
        	enable: true,//开启复选框
        	chkStyle: "checkbox",
        	chkboxType: {"Y":"ps","N":"ps"},//勾选，取消操作都影响父子结点
        }
    };  
function filter(treeId, parentNode, childNodes) {//ajax返回预处理
    if (!childNodes) return null;  
    for (var i=0, l=childNodes.length; i<l; i++) {  
        childNodes[i].name = childNodes[i].name.replace('','');  
    }  
    return childNodes;  
}  
var zNodes;
//获取第一级菜单（题库）
$(document).ready(function(){  
	$.ajax({
		type:"POST",
		url:"problemManager/getProblemLibsByMidenAjax.action",
		cache:"false",
		success:function(data)
		{
			zNodes = $.parseJSON(data.jsonStr);
			$.fn.zTree.init($("#problemChapZtree"), setting, zNodes);  
		}
	});
   
});  

//获取完全选中状态结点的过滤器
function completelySelectedFilter(node){
	return node.checked && !node.getCheckStatus().half;
}

function chaxun(){
	var ztreeObj = $.fn.zTree.getZTreeObj("problemChapZtree");
	var nodes = ztreeObj.getNodesByFilter(completelySelectedFilter);
	var idstr='';
	for(var i=0;i<nodes.length;i++){
		idstr+=nodes[i].lid+','+nodes[i].id+';';
	}
	$.ajax({
		type:"POST",
		url:"problemManager/getProblemsByChAjax.action",
		data:{ids:idstr},
		cache:"false",
		success:function(data)
		{
			
		}
	});
}

function getLidsUrl(a){
	var aobj = $(a);
	var url = "problemManager/getProblemsByChAjax.action";
	var ztreeObj = $.fn.zTree.getZTreeObj("problemChapZtree");
	var nodes = ztreeObj.getNodesByFilter(completelySelectedFilter);
	var idstr='';
	for(var i=0;i<nodes.length;i++){
		idstr+=nodes[i].lid+','+nodes[i].id+';';
	}
	aobj.attr("href",url+'?ids='+idstr);
}
</script>

<div class="pageContent" style="padding:5px">
	<div class="panel" defH="40">
		<h1></h1>
		<div>
			题目名称：<input type="text" name="" />
			分值:<input type="text" name="" />
			开始时间:<input type="text" name="" />
			完成时间:<input type="text" name="" />
		</div>
	</div>
	<div class="divider"></div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>选择题目</span></a></li>
					<li><a href="javascript:;"><span>已选题目</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<!-- 第一块选项卡 -->
			<div>
				<!-- 左边树形菜单 -->
				<div layoutH="143" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
					<ul id="problemChapZtree" class="ztree"></ul>
				</div>
				<!-- 右边题目表格 -->
				<div class="unitBox" style="margin-left:246px;border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
					<div class="panelBar">
						<ul class="toolBar">
							<li><a class="edit" href="#" onclick="getLidsUrl(this);" target="ajax" rel="jbsxBox"><span>查询题目</span></a></li>
							<li><a href="#" class="edit"><span>查看题目</span></a></li>
						</ul>
					</div>
					<div id="jbsxBox">
						
					</div>
				</div>
			</div>
			<!-- 第二块选项卡 -->
			<div>
				已选题目
			</div>
		</div>
	</div>
</div>