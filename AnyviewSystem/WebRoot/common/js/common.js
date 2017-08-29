loadXML = function(xmlString) {
	//跨浏览器，ie和火狐解析xml使用的解析器是不一样的。  
	var xmlStrDoc = null;
	if (window.DOMParser) {// Mozilla Explorer  
		parser = new DOMParser();
		xmlStrDoc = parser.parseFromString(xmlString, "text/xml");
	} else {// Internet Explorer  
		xmlStrDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlStrDoc.async = "false";
		xmlStrDoc.loadXML(xmlString);
	}
	return xmlStrDoc;
}

/**
 * ajax加载select2
 * @param selector jquery对象，是一个select
 * @param url 
 * @param placeholder
 * @param param 参数
 * @param selectedId 默认选择的id
 * added by 何凡 2016.03.19
 */
loadSelect2 = function(selector, url, placeholder, param, selectedId){
	$.ajax({
		type:"POST",
		url:url,
		data:param,
		cache:"false",
		success:function(data)
		{
			selector.select2({
				placeholder:placeholder,
				allowClear:true,
			});
			var datas = $.parseJSON(data);
			selector.append($('<option></option>'));
			for(i=0;i<datas.length;i++){
				var opt = $('<option></option>');
				opt.attr("value",datas[i].id);
				opt.html(datas[i].text);
				selector.append(opt);
			}
			if(selectedId){
				selector.val(selectedId);
				selector.trigger('change');
			}
		},
		error: DWZ.ajaxError
	});
}

changeAjaxSelect2 = function(bindSelector, changeSelector, url, placeholder, param, selectedId){
	bindSelector.on("change",function(e){
		if(this.value==""){
			//如果选择了placeholder提示，则清空下一级select
			changeSelector.html(null);
			changeSelector.val(null);
			changeSelector.select2({
				placeholder : placeholder,
				allowClear : true
			});
			return;
		}
		//只是想将参数的值改变，如果直接放在下面data中则会直接被当成参数名，而不是变量，有更好的方法后修改
		for(p in param)
			param[p] = bindSelector.val();
		$.ajax({
			type:"POST",
			url:url,
			data:param,
			cache:"false",
			success:function(data)
			{
				//先清空原option
				changeSelector.html(null);
				changeSelector.select2({
					placeholder : placeholder,
					allowClear : true
				});
				var datas = $.parseJSON(data);
				changeSelector.append($('<option></option>'));
				for(var i=0; i<datas.length; i++){
					var opt = $('<option></option>');
					opt.attr("value",datas[i].id);
					opt.html(datas[i].text);
					changeSelector.append(opt);
				}
				if(selectedId){
					changeSelector.val(selectedId);
					changeSelector.trigger('change');
				}
			},
			error: DWZ.ajaxError
		});
	});
}

checkAll=function(name, type, value){
	var $context = type=='dialog'?$.pdialog.getCurrent():navTab.getCurrentPanel();
	$("input[name='"+name+"']", $context).each(function(){
		this.checked = value;
	});
}