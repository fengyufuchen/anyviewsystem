//排列选项的序号（span和hidden,radio/check）
function sequenceNum(objId, type){
	var inputs = $("#"+objId,$.pdialog.getCurrent()).find("input[type='"+type+"']");
	var spans = $("#"+objId,$.pdialog.getCurrent()).find("span");
	var hiddens = $("#"+objId,$.pdialog.getCurrent()).find("input[type='hidden']");
	var sq = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	for(var i=0;i<spans.length;i++){
		$(inputs[i]).val(sq[i]);
		$(spans[i]).html(sq[i]);
		$(hiddens[i]).val(sq[i]);
	}
}

/*
 * 排列表单名
 * ulId:jsp中ul的id
 * formTypeArray:jsp中表单类型名的数组
 * backstageName:后台属性名的公共部分
 * backstageAttrArray:后台属性名数组
 */
function setFormsName(ulId, formTypeArray, backstageName, backstageAttrArray){
	var ul = $("#"+ulId,$.pdialog.getCurrent());
	var index = parseInt(ul.attr("index"));
	var list = ul.find("li");
	for(var i=0;i<list.length;i++){
		for(var j=0;j<formTypeArray.length;j++){
			var tempFrom = $(list[i]).find(formTypeArray[j]);
			tempFrom.attr("name", backstageName+"["+i+"]."+backstageAttrArray[j]);
		}
	}
}


//删除选择题选项
function deleteAnswerOpt(o){
	var li = $(o,$.pdialog.getCurrent()).parent("li");
	var ul = li.parent("ul");
	li.remove();
	var index = parseInt(ul.attr("index"));
	ul.attr("index", index-1);
	if(ul.attr("id") == "answerList_singleChoice"){
		sequenceNum(ul.attr("id"), "radio");
		setFormsName("answerList_singleChoice",["input[type='hidden']","textarea"], "problemContent.singleOptions", ["sequence","optContent"]);
	}else{
		sequenceNum(ul.attr("id"), "checkbox");
		setFormsName("answerList_multipleChoice",["input[type='hidden']","textarea"], "problemContent.multipleOptions", ["sequence","optContent"]);
	}
}
//删除程序题头文件
function deleteHeadFile(o){
	var li = $(o).parent("div").parent("li");
	var ul = li.parent("ul");
	var index = parseInt(ul.attr("index"));
	li.remove();
	ul.attr("index", index-1);
	setFormsName("headFileList_program",["input","textarea"], "problemContent.headFiles", ["fileName","fileContent"]);
}
