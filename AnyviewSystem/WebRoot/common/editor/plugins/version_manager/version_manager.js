var JSON_URL = '';

var KE = parent.KindEditor;
location.href.match(/\?id=([\w-]+)/i);
var id = RegExp.$1;//编辑器id
var operType = "version";
var fileManagerJson = (typeof KE.g[id].fileManagerJson == 'undefined') ? JSON_URL : KE.g[id].fileManagerJson;
var lang = KE.lang.plugins.file_manager;
var stack = KE.g[id].dialogStack;
var dialog = stack[stack.length - 1];//dialog句柄
var curFile = KE.g[id].curFile;//需要恢复的文件
var selectedFile = null;
var isCtrlKey = false;
KE.event.ready(function() {
	var viewType = KE.$('viewType', document);
	var orderType = KE.$('orderType', document);
	var listTable = KE.$('listTable', document);
	var viewTable = KE.$('viewTable', document);
	var listDiv = KE.$('listDiv', document);
	var viewDiv = KE.$('viewDiv', document);
	var dirSpan = KE.$('dirSpan',document);
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
	var makeFileTitle = function (filename, filesize, datetime) {//显示文件信息
		var title = filename + ' (' + Math.ceil(filesize / 1024) + 'KB, ' + datetime + ')';
		return title;
	};
	var bindTitle = function (el, data) {
		el.title = makeFileTitle(data.filename, data.filesize, data.datetime);
	};
	var bindEvent = function (el, result, data, createFunc) {
		var fileUrl = result.current_url + data.filename;
		fileUrl = KE.format.getUrl(fileUrl, 'absolute');
		el.onclick = (function (url, path, title) {
				return function(){
					if(isCtrlKey){
						if(selectedFile != null){
							removeClass(selectedFile,'click-selected');
						}
						if(selectedFile==this){//取消选择
							selectedFile = null;
						}else{//选择新单元
							addClass(this,'click-selected');
							selectedFile = this;
						}
					}else{
						if(confirm('\u786e\u5b9a\u8981\u8fd8\u539f\uff1f')){
						   var content = KE.html(id);
						   content = content.replace(/\"/g,"'");
				           content = content.replace(/(\s<br\s*\/>)*$/,"");
				           content = KE.util.trim(content);
						   content = encodeURIComponent(content);
						   var backupFile = this.title;
						   backupFile.match(/(.*(html|htm))\s/);
						   backupFile = dirSpan.innerHTML + RegExp.$1;
						   reloadPage(backupFile+'#'+curFile, operType, "backup", insertHtml);
					   }
					}
				}
			})(fileUrl,escape(result.current_dir_path + data.filename), data.filename);
	};
	var createCommon = function(result, createFunc) {
		dirSpan.innerHTML = result.current_dir_path;
		var onchangeFunc = function() {
			changeType(viewType.value);
			if (viewType.value == 'VIEW') 
				reloadPage(curFile, operType, orderType.value, createView);
			else 
				reloadPage(curFile, operType, orderType.value, createList);
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
			var iconName = 'file-16.gif';
			var img = KE.$$('img', document);
			img.src = './images/' + iconName;
			img.width = 16;
			img.height = 16;
			img.align = 'absmiddle';
			img.alt = data.filename;
			cell0.appendChild(img);
			cell0.appendChild(document.createTextNode(' ' + data.filename));
			row.style.cursor = 'pointer';
			img.title = data.filename;
			cell0.title = data.filename;
			bindEvent(cell0, result, data, createList);
			var cell1 = row.insertCell(1);
			cell1.className = 'size';
			cell1.innerHTML = Math.ceil(data.filesize / 1024) + 'KB';
			var cell2 = row.insertCell(2);
			cell2.className = 'datetime';
			cell2.innerHTML = data.datetime;
		}
		bindYesButton();
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
			table.onmouseover = function () { 
				if(!hasClass(this,'click-selected'))
					this.className = 'photo selected'; 
			};
			table.onmouseout = function () { 
				if(!hasClass(this,'click-selected'))
					this.className = 'photo noselected'; 
			};
			var cell = tableObj.cell;
			cell.valign = 'middle';
			cell.align = 'center';
			var fileUrl = result.current_url + data.filename;
			var iconUrl = './images/file-64.gif';
			var img = KE.$$('img', document);
			img.src = iconUrl;
			img.width = 80;
			img.height = 80;
			img.alt = data.filename;
			table.style.cursor = 'pointer';
			bindTitle(img, data);
			bindTitle(table, data);
			bindEvent(table, result, data, createView);
			cell.appendChild(img);
			div.appendChild(table);
			var titleDiv = KE.$$('div', document);
			titleDiv.className = 'name';
			titleDiv.title = data.filename;
			titleDiv.innerHTML = data.filename;
			div.appendChild(titleDiv);
		}
		bindYesButton();
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
		div.style.textAlign = 'left';
		//newFileName Label
		var label_1 = KE.$$('span');
		var label_2 = KE.$$('span');
		label_1.innerHTML = '1'+'\u3001\u5355\u51fb\u6587\u4ef6\u53ef\u8fdb\u884c\u8fd8\u539f\u5386\u53f2\u7248\u672c';
		label_2.innerHTML = '2'+'\u3001\u6309\u7740'+' Ctrl '+'\u952e\u9009\u62e9\u6587\u4ef6\u540e\uff0c\u70b9\u51fb\u6bd4\u8f83\u53ef\u5bf9\u6bd4\u6587\u4ef6\u7684\u5f02\u540c';
		div.appendChild(label_1);
		div.appendChild(KE.$$('br'));
		div.appendChild(label_2);
		//yesButton
		yesButton = KE.$$('input');
		yesButton.className = 'ke-button ke-dialog-yes';
		yesButton.type = 'button';
		yesButton.name = 'yesButton';
		yesButton.value = '\u6bd4\u8f83';
		
		bottomDiv.appendChild(div);
		bottomDiv.appendChild(yesButton);
		contentCell.appendChild(bottomDiv);
		
		dialog.yesButton = yesButton;
	}
	var bindYesButton = function(){
		dialog.yesButton.onclick = function(){
			if(selectedFile==null){
				alert("No Selected File !");
				return;
			}
			var diff = new KE.dialog({
				id : id,
				cmd : 'diff',
				file : 'version_manager/diff.html',
				width : 9* KE.util.getDocumentWidth(parent.document)/10,
				height : 2 * KE.util.getDocumentHeight(parent.document)/3,
				loadingMode : true,
				title : KE.lang.fileManager,
				noButton : KE.lang.no
			});
			
			selectedFile.title.match(/(.*(html|htm))\s/);
			var targetFile = dirSpan.innerHTML + RegExp.$1;
			diff.option = 'path=' + curFile+'#'+targetFile + '&oper=' + operType+ '&order=diff';
			diff.show();
		}
	}
	var hasClass = function (ele,cls) {
        return ele.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)'));
    }
	var addClass = function (ele,cls) {
		if(!hasClass(ele,cls)){
			ele.className += " "+cls;
		}
	}
	var removeClass = function (ele,cls) {
		if(hasClass(ele,cls)){
			var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
			ele.className=ele.className.replace(reg,' ');
		}
	}
	var keyDown = function (e) {
		var curKey=0,e=e||event;
		curKey=e.keyCode||e.which||e.charCode;
		var keyName = String.fromCharCode(curKey);
		if(curKey == 17){
			isCtrlKey= true;
		}
	}
	var keyUp = function (e) {
		isCtrlKey= false;
	}
	document.onkeydown = keyDown;
	document.onkeyup = keyUp;
	var httpRequest = function (param, func) {
		KE.util.showLoadingPage(id);
		var req = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
		var url = fileManagerJson;
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

	if(curFile==undefined || curFile=='/' ){
		alert("当前文件未保存！");
		dialog.hide();
	}else{
		changeType('VIEW');
		viewType.value = 'VIEW';
		createBottom();
		reloadPage(curFile, operType, orderType.value, createView);
	}
}, window, document);