var curLibs=null;
var kind=null;
//setting
var setting = {  
		view: {
			showLine: true,
			selectedMulti: false,
			dblClickExpand: false
		},
        data: {  
            simpleData: {  
                enable: true  
            }  
        },
        callback: {
			onNodeCreated: this.onNodeCreated,
			beforeClick: this.beforeClick,
			onClick: this.onClick
		},
        async: {  
            enable: true,  
            url:"teacher/problemManager/getNextProblemDirAjax.action",  
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
function beforeClick(treeId, node) {
	if(node.level!=0)
		return false;
	else
		curLibs = node;
	//折叠和禁用其他类型题库
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getNodesByParam("level",0, null);
	for(var i=0;i<nodes.length;i++)
		treeObj.expandNode(nodes[i], false, false, true);
	if(node.isParent){
		treeObj.expandNode(node, true, false, true);
	}
	return true;
}
function onClick(e, treeId, node) {
	var libUrl="teacher/problemManager/getProblemLibsINByKindAjax.action";
	if(node.id==-3)
		kind=0;
	else if(node.id==-4)
		kind=2;
	else if(node.id==-5)
		kind=3;
	else if(node.id==-6)
		kind=4;
	else if(node.id==-7)
		kind=1;
	if(node.isParent)
		return;
	$.ajax({
		type:"POST",
		url:libUrl,
		data:{kind:kind},
		cache:"false",
		success:function(data)
		{
			//生成结点
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var newNodes = $.parseJSON(data);
			newNodes = treeObj.addNodes(node, newNodes);
		}
	});
}
//获取完全选中状态结点的过滤器
function completelySelectedFilter(node){
	var flag = false;
	var n=node;
	while(n.level!=0)
		n=n.getParentNode();
	if(n.id===curLibs.id)
		flag=true;
	return node.checked && !node.getCheckStatus().half && flag;
}
//获取第一级菜单（题库）
$(document).ready(function(){  
// 	$.ajax({
// 		type:"POST",
// 		url:"teacher/problemManager/getProblemLibsAjax.action",
// 		cache:"false",
// 		success:function(data)
// 		{
// 			zNodes = $.parseJSON(data.jsonStr);
// 			$.fn.zTree.init($("#problemChapZtree"), setting, zNodes);  
// 		}
// 	});
   $.fn.zTree.init($("#problemChapZtree"), setting, zNodes);
});  
//拼装lid和chid的str
function getLidsUrl(a, treeId){
	var aobj = $(a);
	var url = "teacher/problemManager/getProblemsByChAjax.action";
	var ztreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = ztreeObj.getNodesByFilter(completelySelectedFilter);
	var idstr='';
	for(var i=0;i<nodes.length;i++){
		idstr+=nodes[i].lid+','+nodes[i].id+';';
	}
	aobj.attr("href",url+'?ids='+idstr+'&kind='+kind);
}

function confirmMProND(){
	var p = $(':radio[name="chooseRadio"][checked]');
	if(p.length>0){
		var tr = p.parent().parent();
		var tds = tr.find("td");
		//修改列表值
		tds[2].innerHTML=$("#newProName").val();
		tds[6].innerHTML=$("#newProDir").val();
		tds[8].innerHTML=$("#startTime").val();
		tds[9].innerHTML=$("#finishTime").val();
		tds[10].innerHTML=$("#proScore").val();
		//修改满分值
		var total=0;
		$('#choosedTab').find("tr").each(function(){
			total+=parseFloat($(this).find("td")[10].innerHTML);
		});
		$("#fullScoreValue").val(total);
		$("#fullScore").val(total);
		$.pdialog.close("modifyProNameAndVDip");
	}else{
		alertMsg.info("请选择一个题目");
	}
}

//提交整个添加作业表的表单
function submitAddSchemeForm(){
	var trs = $('#choosedTab').find("tr");
	var msg = '';
	trs.each(function(){
		var tds = $(this).find("td");
		/*msg+='pid:'+$(tds[0]).find("input").val()
		+',newProName:'+$(tds[2]).html()
		+',newProDir:'+$(tds[6]).html()
		+',startTime:'+$(tds[8]).html()
		+',finishTime:'+$(tds[9]).html()
		+',score:'+$(tds[10]).html()+';';*/
		//id,newpname,newpdir,starttime,finishtime,score
		msg+=$(tds[0]).find("input").val()
		+','+$(tds[2]).html()
		+','+$(tds[6]).html()
		+','+$(tds[8]).html()
		+','+$(tds[9]).html()
		+','+$(tds[10]).html()+';';
	});
	$("#schemeProMsg").val(msg);
	$("#addSchemeForm").submit();
}
