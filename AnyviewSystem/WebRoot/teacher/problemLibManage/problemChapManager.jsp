<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
var chap_setting = {
	view: {
		addHoverDom: addHoverDom,//鼠标移到结点上时，显示自定义控件
		removeHoverDom: removeHoverDom,//鼠标移到结点上时，隐藏自定义控件
		selectedMulti: false//不允许同时选中多个结点
	},
	edit: {
		enable: true,//设置zTree是否处于编辑状态,改变状态用 zTreeObj.setEditable() 
		editNameSelectAll: true,//初次显示编辑结点名称的input时，设置txt内容是否为全选状态
		showRemoveBtn: showRemoveBtn,//设置是否显示删除按钮
		showRenameBtn: showRenameBtn//设置是否显示编辑名称按钮
	},
	data: {
		simpleData: {
			enable: true//是否使用简单数据模式
		}
	},
	callback: {
		beforeDrag: beforeDrag,//用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
		beforeEditName: beforeEditName,//用于捕获节点编辑按钮的 click 事件，并且根据返回值确定是否允许进入名称编辑状态
		beforeRemove: beforeRemove,//用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
		beforeRename: beforeRename,//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
		onRemove: onRemove,//用于捕获删除节点之后的事件回调函数。如果用户设置了 beforeRemove 回调函数，并返回 false，将无法触发 onRemove 事件回调函数。
		onRename: onRename//用于捕获节点编辑名称结束之后的事件回调函数。节点进入编辑名称状态，并且修改节点名称后触发此回调函数。如果用户设置了 beforeRename 回调函数，并返回 false，将无法触发 onRename 事件回调函数。
	}
};

var zNodes =[
	{ id:1, pId:-1, name:"父节点 1", open:true},
	{ id:11, pId:1, name:"叶子节点 1-1"},
	{ id:12, pId:1, name:"叶子节点 1-2"},
	{ id:13, pId:1, name:"叶子节点 1-3"},
	{ id:2, pId:-1, name:"父节点 2", open:true},
	{ id:21, pId:2, name:"叶子节点 2-1"},
	{ id:22, pId:2, name:"叶子节点 2-2"},
	{ id:23, pId:2, name:"叶子节点 2-3"},
	{ id:3, pId:-1, name:"父节点 3", open:true},
	{ id:31, pId:3, name:"叶子节点 3-1"},
	{ id:32, pId:3, name:"叶子节点 3-2"},
	{ id:33, pId:3, name:"叶子节点 3-3"}
];
var className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("problemChapManagerZtree");
	zTree.selectNode(treeNode);
	return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("problemChapManagerZtree");
	zTree.selectNode(treeNode);
	return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}
function onRemove(e, treeId, treeNode) {
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "":"dark");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("problemChapManagerZtree");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
}
function showRemoveBtn(treeId, treeNode) {
	return true;
// 	return !treeNode.isFirstNode;
}
function showRenameBtn(treeId, treeNode) {
	return true;
// 	return !treeNode.isLastNode;
}
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("problemChapManagerZtree");
		zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

$(document).ready(function(){
	$.fn.zTree.init($("#problemChapManagerZtree"), chap_setting, zNodes);
});
</script>


<form id="editClass" method="post" action="teacher/classManager/saveClass.action" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
<input id="problemChapManagerLid" name="problemLib.lid" type="hidden" value="${param.lid}" />
<div class="pageContent sortDrag" selector="h1" layoutH="42">
	<div class="panel" defH="250">
		<h1>题目目录</h1>
		<div>
			<ul id="problemChapManagerZtree" class="ztree"></ul>
		</div>
	</div>
	<div class="panel" defH="150">
		<h1>ddd</h1>
		<div></div>
	</div>
</div>
</form>