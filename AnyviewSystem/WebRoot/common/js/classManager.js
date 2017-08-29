/*
 * 班级管理js
 * 为班级添加教师
 * cid 班级id
 * idBoxName 教师id的复选框name
 * rightBoxName 教师对应权限复选框name
 * type 当前页面片段类型navTab或dialog
 * params 刷新dialog需要的参数
 */
addTeacherToClass = function(cid,idBoxName, rightBoxName, type, params){
	var $context = type=='dialog'?$.pdialog.getCurrent():navTab.getCurrentPanel();
	var teas = "{";
	$("input[name='"+idBoxName+"']:checked", $context).each(function(){
		var $tr = $(this).parents("tr")[0];
		var right=parseInt(0);
		$($tr).find("input[name='"+rightBoxName+"']:checked").each(function(){
			right+=parseInt(this.value);
		});
		teas+=this.value+":"+right+",";
	});
	//防止未选择提交
	if(teas.length == 1){
		alertMsg.warn("您还没有勾选");
		return;
	}
	//转化为JSON字符串，后台转化为map处理
	var rs = teas.substring(0, teas.length-1)+"}";
	$.ajax({
		type:'POST',
		url:"admin/adminclassManager/saveTeacherForClass.action",
		data:{p:rs,cid:cid},
		cache: false,
		success: function(data){
			var obj = eval("("+data+")");
			//刷新navTab
			navTabAjaxDone(obj);
			//刷新dialog
			var url = "admin/adminclassManager/addTeacherForClass.action";
			var did = "addTeacherForClass";
			$.pdialog.reload(url , {data:params, dialogId:did, callback:null});
		},
		error: DWZ.ajaxError
	});
}

/*
 * 为班级添加课程
 * cid 班级id
 * idBoxName 课程id的复选框name
 * type 当前页面片段类型navTab或dialog
 * params 刷新dialog需要的参数
 */
addCourseToClass = function(cid,idBoxName, type, params){
	var $context = type=='dialog'?$.pdialog.getCurrent():navTab.getCurrentPanel();
	var courses = "";
	//将选中的课程id组装成字符串
	$("input[name='"+idBoxName+"']:checked", $context).each(function(){
		courses+=this.value+",";
	});
	//防止未选择提交
	if(courses.length == 1){
		alertMsg.warn("您还没有勾选");
		return;
	}
	$.ajax({
		type:'POST',
		url:"admin/adminclassManager/saveCourseToClass.action",
		data:{p:courses,'cla.cid':cid},
		cache: false,
		success: function(data){
			var obj = eval("("+data+")");
			//刷新navTab
			navTabAjaxDone(obj);
			//刷新dialog
			var url = "admin/adminclassManager/addCourseToClass.action";
			var did = "addCourseToClass";
			$.pdialog.reload(url , {data:params, dialogId:did, callback:null});
		},
		error: DWZ.ajaxError
	});
}

/*
 * 为课程添加教师
 * id ClassCourseTable的id
 * idBoxName
 * rightBoxName
 * type
 * params 刷新dialog需要的参数
 */
addTeacherToCourseOnClass = function(id,idBoxName, rightBoxName, type, params){
	var $context = type=='dialog'?$.pdialog.getCurrent():navTab.getCurrentPanel();
	var teas = "{";
	$("input[name='"+idBoxName+"']:checked", $context).each(function(){
		var $tr = $(this).parents("tr")[0];
		var right=parseInt(0);
		$($tr).find("input[name='"+rightBoxName+"']:checked").each(function(){
			right+=parseInt(this.value);
		});
		teas+=this.value+":"+right+",";
	});
	//防止未选择提交
	if(teas.length == 1){
		alertMsg.warn("您还没有勾选");
		return;
	}
	//转化为JSON字符串，后台转化为map处理
	var rs = teas.substring(0, teas.length-1)+"}";
	$.ajax({
		type:'POST',
		url:"admin/adminclassManager/saveTeacherToCourseOnClass.action",
		data:{p:rs,'cc.id':id},
		cache: false,
		success: function(data){
			var obj = eval("("+data+")");
			//刷新navTab
			navTabAjaxDone(obj);
			//刷新dialog
			var url = "admin/adminclassManager/addTeacherToCourseOnClass.action";
			var did = "addTeacherToCourseOnClass";
			$.pdialog.reload(url , {data:params, dialogId:did, callback:null});
		},
		error: DWZ.ajaxError
	});
}