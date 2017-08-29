
$(function(){

var urljson = 'getClassList';

//添加或编译添加按钮触发：添加到表格
confirmAdd=function(addRow){
	var data = $('#stuInfo').datagrid("getData");
	var noData = false;
	var cancelAdd = false;
	if(data.total == 0)
		noData = true;
	for(var j = 0;j<addRow.total;j++){
		for(var i = 0;noData || i < data.total;i++){
			if(noData || (data.rows[i].studentNo == addRow.rows[j].studentNo)){
				if(noData || confirm("学号为:"+addRow.rows[j].studentNo+" 的学生已经存在,是否覆盖?")){
					if(!noData){ //删除学号相同的行，下一个if重新赋值
						$('#stuInfo').datagrid("deleteRow",i);
					}
				}
				else cancelAdd = true;
				break;
			}
		}
		if(!cancelAdd)
			$('#stuInfo').datagrid('appendRow',{
					state: "<span style='color:red'>未提交</span>",
					studentNo: addRow.rows[j].studentNo,
					studentName: addRow.rows[j].studentName,
					classId: addRow.rows[j].classId,
					className: addRow.rows[j].className,
					studentSex: addRow.rows[j].studentSex,
					SAttr: addRow.rows[j].SAttr
				});
	}
	$('#stuInfo').datagrid('acceptChanges');
}

//添加或编译，添加按钮
$('#temAdd_1').click(function(){
	var _num = $("input[name='num_1']").val(); //账号
	var claId = $("#class_1").combobox("getValue");
	var cla = $("#class_1").combo('getText');
	var attr = $("#attr_1").combobox("getText");
	var sex = $("#sex_1").combobox("getText");
	var name = $("input[name='name_1']").val();//姓名
	if(_num == ""){
		alert("请填完整学生信息!");
		return;
	}
	
	var num = cla.substring(cla.indexOf("(")+1,cla.indexOf(")"));
	num += _num; //学号(登录账号)

	var rows = [{
		state: "<span style='color:red'>未提交</span>",
		studentNo: num,
		studentName: name,
		className: cla,
		classId: claId,
		studentSex: sex,
		SAttr: attr
	}];
	var total = 1;
	var data = {
		rows: rows,
		total: total
	};
	confirmAdd(data);
});

//按账号创建，添加按钮
$('#temAdd_2').click(function(){
	var rows = [];
	var classId = $("#class_2").combo('getValue');
	var cla = $("#class_2").combo('getText');
//	var classNo =  $("#classNo").val();
	var sex = $("#sex_2").combo("getText");
	var attr = $("#attr_2").combobox("getText");
	var beginNo = $("#beginNo").val();
	var endNo = $("#endNo").val();
	var spinner = $("#spinner").val();
	
	if(beginNo == "" || endNo == ""){
		alert("请填完整学生信息!");
		return false;
	}
	if(eval(beginNo) > eval(endNo)){
		var big = beginNo;
		beginNo = endNo;
		endNo = big;
	}
	
	var count = endNo - beginNo;
	if(count == 0){
		count = 1;
	}
	else {
		count += 1; 
	}
	
	var num = cla.substring(cla.indexOf("(")+1,cla.indexOf(")"));
	
	if(beginNo.length > eval(spinner)){
		spinner = beginNo.length;
		$("#spinner").numberspinner('setValue', spinner);
	}
	
	for(var i = 0;i<count;i++){
		var _beginNo = "" + beginNo;
		while((_beginNo).length < eval(spinner)){
			_beginNo = "0" + _beginNo;
		}
		_num = num + _beginNo;
		var row = {
			state: "<span style='color:red'>未提交</span>",
			studentNo: _num,
//			studentName: name,
			classId: classId,
			className: cla,
			studentSex: sex,
			SAttr: attr
		};
		rows.push(row);
		beginNo++;
	}
	var data = {
		total: count,
		rows: rows
	};
	confirmAdd(data);
});

//从文件导入，添加按钮
$('#tempAdd_3').click(function(){
	
	var text = $('#class_3').combo('getText');
	//text = text.substring(text.indexOf("(")+1,text.indexOf(")"));//班号
	var claId = $('#class_3').combo('getValue');
	
	$('#ff').form('submit',{
		url: 'getFileData.action?class='+text+'&classId='+ claId,
		onSubmit: function(){
//			var claSel = $("#class_1").combobox("getData"); 
			var claSel = $("#class_3").combobox("getData");
			$('input[name="f_cla_IdToName"]').attr("value",JSON.stringify(claSel));
		},
		onLoadError:function(){
			alert("数据加载出错");
		},
		method:"post",
		success: function(data){
//			var json = jQuery.parseJSON(data);
			var json = JSON.parse(data);
			var showdata = {};
			showdata.rows = json.rows;
			showdata.total = json.total;

			if(null != json.msg){
				alert(json.msg);
			}
			
			//$('#stuInfo').datagrid('loadData',showdata);
			confirmAdd(showdata);
		}
	});
});

	
//表格信息
$('#stuInfo').datagrid({  
	
	iconCls:'icon-save',
	width:600,
	height:350,
	nowrap: false,
	striped: true,
	fit: true,
	sortName: 'studentNo',
	sortOrder: 'desc',
	idField:'studentNo',
	frozenColumns:[[
	     {field:'ck',checkbox:true,title:'全选'}
	]],
	columns:[[
        {title:'学生资料',colspan:6},
		{field:'state',title:'状态',width:140,align:'center', rowspan:2,
		formatter:function(value){
		if(value){
			return value;
		}
		else 
			{return "<span style='color:red'>未提交</span>";}
		}
		
		}
	],[
		{field:'studentNo',title:'学号',width:240,align:'center'},
		{field:'className',title:'学校',width:240,align:'center'},
		{field:'studentName',title:'姓名',width:240,align:'center'},
		{field:'studentSex',title:'性别',width:110,align:'center'},
		{field:'SAttr',title:'有效状态',width:180,align:'center'},
		{field:'msg',width:0,hidden:true},
		{field:'classId',width:0,hidden:true},
	]],
	pagination:false,
	rownumbers:true, //显示行号
	fitColumns:true, //宽度适应
	onSelect: function(rowIndex,rowData){

		if(rowData.state == "<span>已提交</span>"){return;}
		
		var cla = rowData.className;
		var num = cla.substring(cla.indexOf("(")+1,cla.indexOf(")"));
		var studentNo = rowData.studentNo;
		
		studentNo = studentNo.slice(num.length);
		
		$("span.tabs-title").eq(0).click();  //触发点击，版面变为第一个“添加或编辑”
		$("#class_1").combobox("select",rowData.classId);
		$("input[name='num_1']").attr("value",studentNo);
		$("#attr_1").combobox("setValue",rowData.SAttr);
		$("input[name='password_1']").attr("value",rowData.SPassword);
		$("#sex_1").combobox("setValue",rowData.studentSex);
		$("input[name='name_1']").attr("value",rowData.studentName);
		
		$("#modify_1").unbind().bind("click",function(){
			var rs = $('#stuInfo').datagrid('getSelections');
			var length = rs.length;
			if(length > 1){confirm("只能修改一个记录");return;}
			
			console.info("modify start");
			
			//修改后是否与当前表格学号冲突
			var tableData = $('#stuInfo').datagrid('getRows');
			var className = $("#class_1").combobox("getText");
			var num = className.substring(className.indexOf("(")+1,className.indexOf(")"));
			var studentNoNew = num + $("input[name='num_1']").val();
			
			for(var i=0;i<tableData.length;i++){
				if(i == rowIndex){
					continue;
				}

				if( studentNoNew == tableData[i].studentNo){
					confirm("学号'"+ studentNoNew +"'已经存在!");
					return;
				}
			}

			var data = {
				studentNo:studentNoNew,
				classId:$("#class_1").combobox("getValue"),
				className:$("#class_1").combobox("getText"),
				studentName:$("input[name='name_1']").val(),
				studentSex:$("#sex_1").combobox("getValue"),
				SAttr:$("#attr_1").combobox("getValue"),
				state:"<span style='color:red'>未提交</span>"
			};
			console.info("modify end")
			modify(rowIndex,data);
		});
	},
	toolbar:[{  //工具栏
		text:'删除',
		iconCls:'icon-cancel',
		handler:function(){
			var rs = $('#stuInfo').datagrid('getSelections');
			var length = rs.length;
			for(var i=0;i<length;i++){    //逐行删除      
				var index = $('#stuInfo').datagrid('getRowIndex', rs[0]);
				$('#stuInfo').datagrid('deleteRow', index);
			}
			$('#stuInfo').datagrid('acceptChanges');
			$("#modify_1").unbind();
		}
		},'-',{
		text:'提交',
		iconCls:'icon-save',
		handler:function(){
			var selNumArray = [];
			var selJSONArray = [];
			var select = $('#stuInfo').datagrid("getSelections");
			if(select.length == 0){
				alert("请选择学生后提交!");
				return;
			}
			for(var j=0;j<select.length;j++){
				if(select[j].state == "<span>已提交</span>"){
					continue;
				}
				if(select[j].SAttr == "正常")
				{
					select[j].SAttr = 1;
				}
				else if(select[j].SAttr == "停用")
				{
					select[j].SAttr = 0;
				}
				if(select[j].studentSex == "男")
				{
					select[j].studentSex = "M";
				}	
				else if(select[j].studentSex == "女")	
				{
					select[j].studentSex = "F";
				}	
				selJSONArray.push(select[j]);
				var index = $('#stuInfo').datagrid('getRowIndex', select[j]);
				selNumArray.push(index);
			}
			if(JSON.stringify(selJSONArray) != "[]"){ 
				if(compareNo()){
					add(selNumArray,selJSONArray);
				}
				else{
					alert("表格中存在重复的学号！");
				}
			}else{
				alert("学生信息已提交");
			}
		}
		},'-',{
		text:'清空',
		iconCls:'icon-remove',
		handler:function(){
			$('#stuInfo').datagrid('loadData', {total:0,rows:[]});
			$('#stuInfo').datagrid('unselectAll');
			$("#modify_1").unbind();
		}
		}],
		
});

function compareNo(){//判断有没重复学号
	
	var flag = 1;
	
	var rows = $('#stuInfo').datagrid('getRows')//获取当前的数据行
	var length = rows.length;
	if(length == 0) return false;

	for (var i = 0; i < length; i++) {
		var noOne= rows[i]['studentNo'];
		for(var j=0;j<length;j++){
			if(j == i)continue;
			var noTwo = rows[j]['studentNo'];
			if(noTwo == noOne){
				flag = 0;
				break;
			}
		}
    }
	return flag==1?true:false;
}

function add(selNumArray,selJSONArray){
	$.post('batAddStudents.action',
           {'selectRows':JSON.stringify(selJSONArray)},
           function(data){
        	   for(var i = 0; i < selNumArray.length; ++i){
        		   $('#stuInfo').datagrid('updateRow',{ 
        			   index:selNumArray[i],
        			   row:data[i]});
//        		   if(data[i].msg != null){
//            		   alert(data[i].msg);
//            	   }
        	   }
        	   
           },'json');
}


function modify(rowIndex,data){
	$('#stuInfo').datagrid('updateRow',{
		index: rowIndex,
		row: data
	});
}

function reExcelData (data){
	var obj = eval("(" + data + ")");
	$(function(){
		confirmAdd(obj);
	});
	if(obj.msg != null){
		alert(obj.msg);
	}
}

});


