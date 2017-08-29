//@ sourceURL=commonSetting.js

//zNodes;
var zNodes =[
			{ id:-3, pId:-2, name:"自建题库",nocheck:true},
			{ id:-5, pId:-2, name:"校级公开题库",nocheck:true},
			{ id:-6, pId:-2, name:"完全公开题库",nocheck:true},
			{ id:-7, pId:-2, name:"其他可访问题库",nocheck:true}
		];

function filter(treeId, parentNode, childNodes) {//ajax返回预处理
    if (!childNodes) return null;  
    for (var i=0, l=childNodes.length; i<l; i++) {  
        childNodes[i].name = childNodes[i].name.replace('','');  
    }  
    return childNodes;
}  

//判断obj的value是否在arr的obj的value中
function inArray(obj, arr){
	for(var i=0;i<arr.length;i++){
		if(arr[i].value===obj.value)
			return true;
	}
	return false;
}
//删除表格中选中的radio的那一行
function deleteChoosedPro(radioname){
	var p = $(':radio[name="'+radioname+'"]:checked');
	if(p.length>0)
		alertMsg.confirm("确定删除？",{
			okCall : function(){
				//删除radio的上级tr
				p.parent().parent().remove();
			}
		});
	else
		alertMsg.info("请选择一个题目");
}

/*
 * 选定题目
 * oldRadioName 已选择列表中radio的name
 * pidChkName 选择列表中check的name
 * tbodyId 要追加的table的tbody的id
 */
function choosePros(oldRadioName, pidChkName, tbodyId){
	//先获取已经选择的pid
	var oldIdArr = $(":radio[name='"+oldRadioName+"']");
	var ids="";
	var count=0;
	var cp = $(":checkbox[name='"+pidChkName+"']:checked");
	if(!cp.length>0){
		alertMsg.info('请选择题目');
		return;
	}
	cp.each(function(){
		if(!inArray(this, oldIdArr)){
			ids+=this.value+",";
			count++;
		}
	});
	if(ids.length>0){
		$.ajax({
			type:"POST",
			url:"teacher/problemManager/getProblemsByPidAjax.action",
			data:{ids:ids},
			cache:"false",
			success:function(data)
			{
				var pros = $.parseJSON(data);
				var choosedTab = $("#"+tbodyId);
				for(var i=0;i<pros.length;i++){
					var tr = 
						'<tr class="">'+
							'<td align="center"><input type="radio" name="'+oldRadioName+'" value="'+pros[i].pid+'"/></td>'+
							'<td align="center">'+pros[i].pname+'</td>'+
							'<td align="center"></td>'+
							'<td align="center">'+pros[i].degree+'</td>'+
							'<td align="center">'+pros[i].kind+'</td>'+
							'<td align="center">'+pros[i].pdir+'</td>'+
							'<td align="center"></td>'+
							'<td align="center">'+pros[i].lib+'</td>'+
							'<td align="center"></td>'+
							'<td align="center"></td>'+
							'<td align="center">0</td>'+
						'</tr>';
					choosedTab.append(tr);
				}
				var info=count+'个题目已选择';
				if(cp.length>count)
					info+=(cp.length-count)+'个题目已存在';
				alertMsg.correct(info);
			}
		});
	}else{
		alertMsg.info('你选择的题目已在被选列表中');
	}
	
}

function lookProMsg(radioId,proContentDivId,proMemoDivId,proTipDivId){
	var pid = $(':radio[name="'+radioId+'"]:checked').val();
	$.ajax({
		type:"POST",
		url:"teacher/problemManager/lookProblemAjax.action",
		data:{pid:pid},
		cache:"false",
		success:function(data)
		{
			var a = $.parseJSON(data);
			$("#"+proContentDivId).html(a.proContent);
			$("#"+proMemoDivId).html(a.proMemo);
			$("#"+proTipDivId).html(a.proTip);
		}
	});
}

