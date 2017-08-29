var JSON_URL = '../../php/file_manager_json.php';

var KE = parent.KindEditor;
location.href.match(/\?oper=([\w@]+)&id=([\w-]+)/i);
var operType = RegExp.$1;//操作类别
var operAct = null;
if(operType.indexOf("@")>0){
	var i = operType.indexOf("@");
	operAct = operType.substring(i+1);
	operType = operType.substring(0,i)
}
var id = RegExp.$2;//编辑器id
var fileManagerJson = (typeof KE.g[id].fileManagerJson == 'undefined') ? JSON_URL : KE.g[id].fileManagerJson;
var lang = KE.lang.plugins.file_manager;
var stack = KE.g[id].dialogStack;
var dialog = stack[stack.length - 1];//dialog句柄
KE.event.ready(function() {
	var moveupImg = KE.$('moveupImg', document);
	var moveupLink = KE.$('moveup', document);
	var viewType = KE.$('viewType', document);
	var orderType = KE.$('orderType', document);
	var listTable = KE.$('listTable', document);
	var viewTable = KE.$('viewTable', document);
	var listDiv = KE.$('listDiv', document);
	var viewDiv = KE.$('viewDiv', document);
	var dirSpan = KE.$('dirSpan',document);
	moveupImg.alt = moveupImg.title = lang.moveup;
	viewType.options[0] = new Option(lang.viewImage, 'VIEW');
	viewType.options[1] = new Option(lang.listImage, 'LIST');
	orderType.options[0] = new Option(lang.fileName, 'NAME');
	orderType.options[1] = new Option(lang.fileSize, 'SIZE');
	orderType.options[2] = new Option(lang.fileType, 'TYPE');
	var changeType = function(type) {//视图类型
		if (type == 'VIEW') {
			listDiv.style.display = 'none';
			viewDiv.style.display = '';
		} else {
			listDiv.style.display = '';
			viewDiv.style.display = 'none';
		}
	};
	var insertLink = function(url) {//插入超链接
		if (stack.length > 1) {
			var parentDialog = stack[stack.length - 2];
			var dialogDoc = KE.util.getIframeDoc(parentDialog.iframe);
			if(operAct=='link'){
				KE.$('hyperLink', dialogDoc).value = url;
			}else{
				KE.$('url', dialogDoc).value = url;
			}
			dialog.hide();
			return true;
		} else {
			return false;
		}
	}
	var insertImage = function(url, title) {//插入图片
		if (!insertLink(url)) {
			KE.util.insertHtml(id, '<img src="' + url + '" alt="' + title + '" border="0" />');
		}
	};
	var insertFile = function(url, title) {//插入文件路径
		if (!insertLink(url)) {
			KE.util.insertHtml(id, '<a href="' + url + '" target="_blank">' + title + '</a>');
		}
	};
	var insertHtml = function(content) {//插入html内容
		var contJson = KE.util.parseJson(content);
		if(contJson.error==1){
			alert(contJson.message);
		}else{
			KE.html(id, contJson.content);
			KE.g[id].curFile = contJson.fileName;
			dialog.hide();
		}
	};
	var saveFile = function(content){
		var result = KE.util.parseJson(content);
		if(result.error==1){
			alert(result.message);
		}else{
			if (viewType.value == 'VIEW') 
				reloadPage(result.current_dir_path, operType, orderType.value, createView);
			else 
				reloadPage(result.current_dir_path, operType, orderType.value, createList);
			alert(result.is_save);
			dialog.fileName.value = "";
			dialog.hide();
		}
	}
	var makeFileTitle = function (filename, filesize, datetime) {//显示文件信息
		var title = filename + ' (' + Math.ceil(filesize / 1024) + 'KB, ' + datetime + ')';
		return title;
	};
	var bindTitle = function (el, data) {
		if (data.is_dir) {
			el.title = data.filename;
		} else {
			el.title = makeFileTitle(data.filename, data.filesize, data.datetime);
		}
	};
	var bindEvent = function (el, result, data, createFunc) {
		var fileUrl = result.current_url + data.filename;
		fileUrl = KE.format.getUrl(fileUrl, 'absolute');
		if (data.is_dir) {//路径
			el.onclick = (function (url, path, title) {
				return function () {
					reloadPage(path, operType, orderType.value, createFunc);
				}
			})(fileUrl, escape(result.current_dir_path + data.filename + '/'), data.filename);
		} else if (data.is_photo) {//图片
			el.onclick = (function (url, title) {
				return function () {
					insertImage(url, title);
				}
			})(fileUrl, data.filename);
		} else if (data.is_html){//html内容
			el.onclick = (function (url, path, title) {
				return function () {
					if(operAct=="save"){
					   if(confirm('\u662f\u5426\u8986\u76d6\u6587\u4ef6\uff1f')){
						   var content = KE.html(id);
						   content = content.replace(/\"/g,"'");
				           content = content.replace(/(\s<br\s*\/>)*$/,"");
				           content = KE.util.trim(content);
						   content = encodeURIComponent(content);
						   reloadPage(path+dialog.fileName.value+"&content="+content, operAct, "cover", saveFile);
					   }
					}else if(operAct=="link"){
						insertFile(fileUrl, data.filename);
					}else{
					    reloadPage(path, operAct, "", insertHtml);
					}
				}
			})(fileUrl,escape(result.current_dir_path + data.filename), data.filename);
		}else{//其他
			el.onclick = (function (url, title) {
				return function () {
					insertFile(url, title);
				}
			})(fileUrl, data.filename);
		}
	};
	var createCommon = function(result, createFunc) {
		dirSpan.innerHTML = "/";
		if (result.current_dir_path) {
			dirSpan.innerHTML += result.current_dir_path;
			moveupLink.onclick = function () {
				reloadPage(result.moveup_dir_path, operType, orderType.value, createFunc);
			};
		} else {
			moveupLink.onclick = null;
		}
		var onchangeFunc = function() {
			changeType(viewType.value);
			if (viewType.value == 'VIEW') 
				reloadPage(result.current_dir_path, operType, orderType.value, createView);
			else 
				reloadPage(result.current_dir_path, operType, orderType.value, createList);
		};
		viewType.onchange = onchangeFunc;
		orderType.onchange = onchangeFunc;
	};
	var createList = function(responseText) {
		listDiv.innerHTML = '';
		var result = KE.util.parseJson(responseText);
		if(result.error!=undefined && result.error==1){
			alert(result.message);
			dialog.hide();
		}
		createCommon(result, createList);
		var table = KE.$$('table', document);
		table.className = 'file-list-table';
		table.cellPadding = 0;
		table.cellSpacing = 2;
		table.border = 0;
		listDiv.appendChild(table);
		var fileList = result.file_list;
		for (var i = 0, len = fileList.length; i < len; i++) {
			var data = fileList[i];
			var row = table.insertRow(i);
			row.onmouseover = function () { this.className = 'selected'; };
			row.onmouseout = function () { this.className = 'noselected'; };
			var cell0 = row.insertCell(0);
			cell0.className = 'name';
			var iconName = data.is_dir ? 'folder-16.gif' : 'file-16.gif';
			var img = KE.$$('img', document);
			img.src = './images/' + iconName;
			img.width = 16;
			img.height = 16;
			img.align = 'absmiddle';
			img.alt = data.filename;
			cell0.appendChild(img);
			cell0.appendChild(document.createTextNode(' ' + data.filename));
			if (!data.is_dir || data.has_file) {
				row.style.cursor = 'pointer';
				img.title = data.filename;
				cell0.title = data.filename;
				bindEvent(cell0, result, data, createList);
			} else {
				img.title = lang.emptyFolder;
				cell0.title = lang.emptyFolder;
			}
			var cell1 = row.insertCell(1);
			cell1.className = 'size';
			cell1.innerHTML = data.is_dir ? '-' : Math.ceil(data.filesize / 1024) + 'KB';
			var cell2 = row.insertCell(2);
			cell2.className = 'datetime';
			cell2.innerHTML = data.datetime;
		}
		if(operAct!=null && operAct=='save'){
			bindYesButton(result.current_dir_path);
	    }
	};
	var createView = function(responseText) {
		viewDiv.innerHTML = '';
		var result = KE.util.parseJson(responseText);
		if(result.error!=undefined && result.error==1){
			alert(result.message);
			dialog.hide();
		}
		createCommon(result, createView);
		var fileList = result.file_list;
		for (var i = 0, len = fileList.length; i < len; i++) {
			var data = fileList[i];
			var div = KE.$$('div', document);
			div.className = 'file-view-area';
			viewDiv.appendChild(div);
			var tableObj = KE.util.createTable(document);
			var table = tableObj.table;
			table.className = 'photo noselected';
			table.onmouseover = function () { this.className = 'photo selected'; };
			table.onmouseout = function () { this.className = 'photo noselected'; };
			var cell = tableObj.cell;
			cell.valign = 'middle';
			cell.align = 'center';
			var fileUrl = result.current_url + data.filename;
			var iconUrl = data.is_dir ? './images/folder-64.gif' : (data.is_photo ? fileUrl : './images/file-64.gif');
			var img = KE.$$('img', document);
			img.src = iconUrl;
			img.width = data.is_dir ? 64 : 80;
			img.height = data.is_dir ? 64 : 80;
			img.alt = data.filename;
			if (!data.is_dir || data.has_file) {
				table.style.cursor = 'pointer';
				bindTitle(img, data);
				bindTitle(table, data);
				bindEvent(table, result, data, createView);
			} else {
				img.title = lang.emptyFolder;
				table.title = lang.emptyFolder;
			}
			cell.appendChild(img);
			div.appendChild(table);
			var titleDiv = KE.$$('div', document);
			titleDiv.className = 'name';
			titleDiv.title = data.filename;
			titleDiv.innerHTML = data.filename;
			div.appendChild(titleDiv);
		}
		if(operAct!=null && operAct=='save'){
			bindYesButton(result.current_dir_path);
	    }
	};
	var createBottom = function(){
		var div = dialog.div;//dialog内的div
		var td = div.getElementsByTagName("td");
		var contentCell;
		for(var i=0; i<td.length;++i){
			if(td[i].className=='ke-mc'){
				contentCell = td[i];
				break;
			};
		}
		var bottomDiv = KE.$$('div');
		bottomDiv.className = 'ke-dialog-bottom';
		//inputFileDiv
		var div = KE.$$('div');
		div.style.width = '60%';
		div.style.styleFloat = 'left';
		div.style.cssFloat = 'left';
		div.style.margin=5;
		//newFileName Label
		var label = KE.$$('label');
		label.innerHTML = '\u65b0\u5efa\u6587\u4ef6\u540d\uff1a';
		//newFileName Input
		var input = KE.$$('input');
		input.id = 'fileName';
		input.name = 'fileName';
		input.type = 'text';
		input.maxlength = '20';
		input.style.width = '70%';
		input.onclick = function(){
			this.focus();
		}
		div.appendChild(label);
		div.appendChild(input);
		//yesButton
		yesButton = KE.$$('input');
		yesButton.className = 'ke-button ke-dialog-yes';
		yesButton.type = 'button';
		yesButton.name = 'yesButton';
		yesButton.value = KE.lang.yes;

		//noButton
		noButton = KE.$$('input');
		noButton.className = 'ke-button ke-dialog-no';
		noButton.type = 'button';
		noButton.name = 'noButton';
		noButton.value = KE.lang.no;
		noButton.onclick = function () {
			dialog.hide();
			//KE.util.select(id);
		};
		bottomDiv.appendChild(div);
		bottomDiv.appendChild(yesButton);
		bottomDiv.appendChild(noButton);
		contentCell.appendChild(bottomDiv);
		
		dialog.fileName = input;
		dialog.yesButton = yesButton;
	}
	var bindYesButton = function(path){
		dialog.yesButton.onclick = function(){
			if(!/^[^\\\/\*\.:<>]+\.(html|htm)/g.test(dialog.fileName.value)){
				alert("\u6587\u4ef6\u540d\u4e0d\u5408\u6cd5\u3002\u8bf7\u8f93\u5165\u540e\u7f00\u4e3a" +
				"html"+"\u6216"+"htm"+"\u7684\u6587\u4ef6\u540d");
			}else{
				var content = KE.html(id);
				content = content.replace(/\"/g,"'");
				content = content.replace(/(\s<br\s*\/>)*$/,"");
				content = KE.util.trim(content);
				content = encodeURIComponent(content); 
				reloadPage(path+dialog.fileName.value+"&content="+content, operAct, "new", saveFile);
			}
		}
	}
	
	var httpRequest = function (param, func) {
		KE.util.showLoadingPage(id);
		var req = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
		var url = fileManagerJson;
		//url += param;
		//url += (url.match(/\?/) ? "&" : "?") + (new Date()).getTime()
		//req.open('GET', url, true);
		req.open('POST', url, true);
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
		req.onreadystatechange = function () {
			if (req.readyState == 4) {
				if(req.status == 200) {
					func(req.responseText);
					KE.util.pluginLang('file_manager', document);
					KE.util.hideLoadingPage(id);
				}
			}
		};
		req.send(param);
	};
	var reloadPage = function (path, oper, order, func) {
		httpRequest('path=' + path + '&oper=' + oper+ '&order=' + order, func);
	};
	changeType('VIEW');
	viewType.value = 'VIEW';
	if(operAct!=null && operAct=='save'){
		createBottom();
	}
	reloadPage('', operType, orderType.value, createView);
}, window, document);