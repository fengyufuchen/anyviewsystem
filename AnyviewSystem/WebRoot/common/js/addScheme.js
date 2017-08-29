$("#newSchemeteacherCoSelect").select2({
	placeholder:"请选择课程",
	allowClear:true,
});
$("#newSchemeUnSelect").select2({
	placeholder:"请选择学校",
	allowClear:true,
});
$("#newSchemeCeSelect").select2({
	placeholder : '请选择学院',
	allowClear : true
});
$("#newSchemeCoSelect").select2({
	placeholder : '请选择课程',
	allowClear : true
});
$("#newSchemeLibSelect").select2({
	placeholder : '请选择课程',
	allowClear : true
});
loadSelect2($("#newSchemeUnSelect"), "communion/gainUniversityBySearchAjax.action", "请选择学校", {q:""},"newSchemeCeSelect");
changeAjaxSelect2($("#newSchemeUnSelect"), $("#newSchemeCeSelect"), "communion/gainCollegeByUnIdAjax.action", "请选择学院", {unId:null});
changeAjaxSelect2($("#newSchemeCeSelect"), $("#newSchemeCoSelect"), "communion/gainCourseByCeIdAjax.action", "请选择课程", {ceId:null});

function searchScheme(){
	unId = $("#newSchemeUnSelect").val();
	ceId = $("#newSchemeCeSelect").val();
	courseId = $("#newSchemeCoSelect").val();
	if(unId == null || unId == ""){
		alertMsg.error("请选择学校");
		return false;
	}
	param = {'unId':unId,'ceId':ceId,'courseId':courseId};
	$("#nsSchemesDiv").loadUrl("teacher/schemeManager/accessSchemes.action",param, null);
}

function deleteThisProblem(btn){
	$(btn).parent("div").parent("div").parent("div").remove();
	sortProblem();
}

function sortProblem(){
	var pdiv = $("#nsSelectedPro");
	var sortBoxs = pdiv.find("div.problemContentDiv");
	for(i=0;i<sortBoxs.length;i++){
		$(sortBoxs[i]).find("span[name='vchapName']").html(i+1);
	}
}

function searchLib(){
	unId = $("#newSchemeUnSelect").val();
	ceId = $("#newSchemeCeSelect").val();
	param = {'unId':unId,'ceId':ceId};
	$("#nsLibsDiv").loadUrl("teacher/problemLibManager/accessLibs.action",param, null);
}

function viewLibContent(){
	var tab = $("#nsLibsDiv").find("table");
	var lid = $(tab).find("tr[class='selected']").attr("rel");
	if(undefined == lid || ''==lid){
		alertMsg.error("请选择一个题库");
		return false;
	}
	param = {'problemChap.problemLib.lid':lid,'problemChap.chId':-1};
	$("#nsLibContentDiv").loadUrl("teacher/problemManager/libContents.action",param, null);
}

function dataReview(){
	data = "";
	if($("#nsvname").val() == ""){
		alertMsg.error("作业表名称不能为空");
	}else if($("#newSchemeteacherCoSelect").val()==""){
		alertMsg.error("课程不能为空");
	}else if($("#nskind").val()==""){
		alertMsg.error("类型不能为空");
	}else{
		data += $("#nsvname").val() + "," + $("#newSchemeteacherCoSelect").val() + "," + $("#nskind").val() + "," + $("#nsfullscore").val() + "|";
		prodivs = $("#nsSelectedPro").find("div[class='sortDiv problemContentDiv']");
		//数组组装格式为：|pid , vpchapname , vpname , score , startTime , finishTime|
		prodivs.each(function(){
			div = $(this);
			kind = div.attr('kind');
			temp = false;
			switch(kind){
			case '3':{
				
			}
			case '4':{
				temp = choiceDataView(div);
				break;
			}
			case '5':{
				temp = judgmentDataView(div);
				break;
			}
			}
			if(temp){
				data += temp + '|';
			}else{
				return false;
			}
		});
	}
	return data;
}

//选择题检查及数据组装
function choiceDataView(div){
	var flag = false;
	var pid = div.attr('pid');
	var vpchapname = div.find("span[name='vchapName']").html();
	var vpname = div.find("input[group='vpname']").val();
	var score = div.find("input[group='score']").val();
	var startTime = div.find("input[group='startTime']").val();
	var finishTime = div.find("input[group='finishTime']").val();
	if(vpname==""){
		alertMsg.error("题目"+vpchapname+"名称不能为空");
	}else if(score==""){
		alertMsg.error("题目"+vpchapname+"分值不能为空");
	}else if(startTime==""){
		alertMsg.error("题目"+vpchapname+"开始时间不能为空");
	}else if(finishTime==""){
		alertMsg.error("题目"+vpchapname+"结束时间不能为空");
	}else{
		flag = true;
	}
	if(flag){
		return pid + "," + vpchapname + "," + vpname + "," + score + "," + startTime + "," + finishTime;
	}else{
		return false;
	}
}
//判断题检查及数据组装
function judgmentDataView(div){
	return false;
}

function saveScheme(obj){
	addScore();
	var data = dataReview();
	if(!data)
		return;
	$("#nsSchemeMsg").val(data);
	$("#nsAddSchemeForm").submit();
}

function addScore(){
	//把所有题目的分值加起来并复制给作业表分值
	ins = $("#nsSelectedPro").find("input[group='score']");
	var totalScore = 0;
	ins.each(function(){
		if(this.value.length == 0 || isNaN(this.value)){
			alertMsg.error("请输入正确的分值");
			return false;
		}
		totalScore+=parseFloat(this.value);
	});
	$("#nsfullscore").val(totalScore);
	return true;
}

function checkScore(score){
	if(score.length==0 || isNaN(score)){
		alertMsg.error("请输入正确的分值");
		return false;
	}
	return true;
}
