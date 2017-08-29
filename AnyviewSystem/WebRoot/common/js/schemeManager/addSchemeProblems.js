//@ sourceURL=addSchemeProblem.js
var curLibs_2=null;
var kind_2=null;
//setting
var setting_2 = {  
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
			beforeClick: this.beforeClick_2,
			onClick: this.onClick_2
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
function beforeClick_2(treeId, node) {
	if(node.level!=0)
		return false;
	else
		curLibs_2 = node;
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
function onClick_2(e, treeId, node) {
	var libUrl="teacher/problemManager/getProblemLibsINByKindAjax.action";
	if(node.id==-3)
		kind_2=0;
	else if(node.id==-4)
		kind_2=2;
	else if(node.id==-5)
		kind_2=3;
	else if(node.id==-6)
		kind_2=4;
	else if(node.id==-7)
		kind_2=1;
	if(node.isParent)
		return;
	$.ajax({
		type:"POST",
		url:libUrl,
		data:{kind:kind_2},
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
function completelySelectedFilter_2(node){
	var flag = false;
	var n=node;
	while(n.level!=0)
		n=n.getParentNode();
	if(n.id===curLibs_2.id)
		flag=true;
	return node.checked && !node.getCheckStatus().half && flag;
}
//获取第一级菜单（题库）
$(document).ready(function(){  
   $.fn.zTree.init($("#problemChapZtreeTwo"), setting_2, zNodes);
});  

//拼装lid和chid的str
function getLidsUrl_2(a,treeId){
	var aobj = $(a);
	var url = "teacher/problemManager/getProblemsByChTwoAjax.action";
	var ztreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = ztreeObj.getNodesByFilter(completelySelectedFilter_2);
	var idstr='';
	for(var i=0;i<nodes.length;i++){
		idstr+=nodes[i].lid+','+nodes[i].id+';';
	}
	aobj.attr("href",url+'?ids='+idstr+'&kind='+kind_2);
}

function confirmMProND_2(){
	var p = $(':radio[name="chooseRadioTwo"]:checked');
	if(p.length>0){
		var tr = p.parent().parent();
		var tds = tr.find("td");
		//修改列表值
		tds[2].innerHTML=$("#newProNameTwo").val();
		tds[6].innerHTML=$("#newProDirTwo").val();
		tds[8].innerHTML=$("#startTimeTwo").val();
		tds[9].innerHTML=$("#finishTimeTwo").val();
		tds[10].innerHTML=$("#proScoreTwo").val();
		//修改满分值
		var total=0;
		$('#choosedTabTwo').find("tr").each(function(){
			total+=parseFloat($(this).find("td")[10].innerHTML);
		});
		$("#fullScoreValue_2").val(total);
		$("#fullScoreTwo").val(total);
		$.pdialog.close("modifyProNameAndVDipTwo");
	}else{
		alertMsg.info("请选择一个题目");
	}
}
//提交整个添加作业表的表单
function submitAddSchemeForm_2(){
	var trs = $('#choosedTabTwo').find("tr");
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
	$("#schemeProMsg_2").val(msg);
	$("#addSchemeFormTwo").submit();
}
