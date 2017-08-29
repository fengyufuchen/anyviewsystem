$(function(){
	var aa=false;
	var bb=false;
	var schemeFlag=false;
	var aw = {};
	aw.tempData = {};//装载每个状态信息
/**初始化所有窗口对象（主窗口）**/
	aw.iniAllWins= function(){
		//几乎所有窗口的通有属性
		$("#saveStuDataDiv").window({
			resizable:true,
			modal:true,
			closed:true,
			inline:true,
			collapsible:false,
			minimizable:false,
			maximizable:false
		});
		//弹出窗口属性
		$("#saveStuDataDiv").window({
			left:200,
			width:780,
			height:320
		});
		$("#analy").window({
			resizable:true,
			modal:true,
			closed:true,
			inline:true,
			collapsible:false,
			minimizable:false,
			maximizable:false
		});
		$("#analy").window({
			left:100,
			width:850,
			height:500
		});
	};
/**初始化所有窗口对象（主窗口）**/
	
	
/**加载所有组件**/
	aw.loadComponent = function(){
		//加载下拉菜单
		$('#courseCombo').combo({
            required:false,
            editable:false
        });
        $('#sp').appendTo($('#courseCombo').combo('panel'));
        $('#sp input').click(function(){
            var v = $(this).val();
            var s = $(this).next('span').text();
            aa=false;
            bb=false;
            $('#courseCombo').combo('setValue', v).combo('setText', s).combo('hidePanel');
            
			$('#classCombo').combogrid("clear");
			$('#schemeCombo').combogrid("clear");
            aw.loadClass(v);
			//存储临时状态信息
			aw.tempData.courseId = v;
			//清空所有结果
			aw.cleanTextData('text_title','text_TAnswer','text_StuAnswer','text_comment','exScore');
			aw.cleanGridData('datagrid1','datagrid2','datagrid3','datagrid4');
			aw.tempData.exId = null;
			aw.tempData.proId = null;
	
			$('#schemeCombo').combogrid("enable");
			
				
			$('#StuSmallGrid').css('display','none');
			$('#ExSmallGrid').css('display','inline');
			$('#ExBigGrid').css('display','inline');
			$('#StuBigGrid').css('display','none');
				
			$("#sort_for_ex").attr("checked",true);
			$("input[name='sort']").attr("disabled",false);
			$("input[name='radio1']").attr("disabled",false);
			
        });
		
		$('#classCombo').combogrid({//班级菜单
			width:100,
			panelWidth:150,
			idField:'cid',
			textField:'cname', 
			fitColumns:true,
			editable: false,
			singleSelect:true,
			columns:[[ 
				//{field:'cid',hidden:true},
				//{field:'cname',title:'班级',width:140}
				{field:'cid',hidden:true},
				{field:'cname',title:'班级',width:140}
			]],
			onSelect:function(rowIndex, rowData){
				 aa=false;
		         bb=false;
				//存储临时状态信息
				aw.tempData.cid = rowData.cid;
				//清空所有结果
				aw.cleanTextData('text_title','text_TAnswer','text_StuAnswer','text_comment','exScore');
				aw.cleanGridData('datagrid1','datagrid2','datagrid3','datagrid4');
				aw.tempData.exId = null;
				aw.tempData.proId = null;
				$('#schemeCombo').combogrid("clear");
				
				aw.loadScheme(rowData.cid);
				
			},
		});
		
		//加载作业表菜单
		$('#schemeCombo').combogrid({
			width:100,
			panelWidth:112,
			idField:'VID',
			textField:'VName', 
			editable: false,
			singleSelect:true,
			columns:[[ 
				{field:'VID',hidden:true}, 
				{field:'VName',title:'作业名',width:100}
			]],
			onSelect:function(rowIndex, rowData){
				//先清空所有结果
				 aa=false;
		         bb=false;
		         schemeFlag=true;
				aw.cleanTextData('text_title','text_TAnswer','text_StuAnswer','text_comment','exScore');
				aw.cleanGridData('datagrid1','datagrid2','datagrid3','datagrid4');
				aw.tempData.exId = null;
				aw.tempData.proId = null;
				aw.tempData.schemeId = rowData.VID;
				aw.loadTempGridData($('#classCombo').combogrid("getValue"),rowData.VID);
			}
		});
		
		//按习题显示主窗口
		$('#datagrid1').datagrid({
			striped:true,
			singleSelect:true,
			fitColumns:true,
			width:610,
			height:170,
			remoteSort:false,
			columns:[[
				{field:'schemeId',hidden:true},
				{field:'problemId',hidden:true},
				{field:'chapName',title:'章名',align:'center',width:85},
				{field:'proName',title:'习题名',align:'center',width:85},
				{field:'score',title:'分值',align:'center',width:60},
				{field:'scale',title:'通过人数',align:'center',width:80,sortable:true,
					sorter:function(a,b){ //排序  
						a = a.split('/');  
						b = b.split('/');  
						return (parseInt(a[0])>parseInt(b[0])?1:-1);  
					}
				},
				{field:'memo',hidden:true},
				{field:'answer',hidden:true},
				{field:'topic',hidden:true},
				]],
			onDblClickRow:function(rowIndex,rowData){
				$('#text_title').attr("value",rowData.topic);//加载题目文档
				$('#text_TAnswer').attr("value",rowData.answer);//加载答案
				aw.tempData.exIndex = rowIndex;//保存索引
				aw.tempData.pInfo = rowData.pInfo;//保存运行测试信息
				aw.tempData.exInfo = rowData.chapName+rowData.proName;
				//清除数据
				aw.cleanTextData('text_StuAnswer','text_comment','exScore');
				aw.tempData.exId = null;
				
				var cid = $('#classCombo').combogrid("getValue");
				$.post("getStuDetail.action",{cid:cid,schemeId:rowData.schemeId,problemId:rowData.problemId},
				function(data){
					aa=true;
					bb=false;
					$('#datagrid2').datagrid("loadData",data.rows);
					aw.tempData.proId = rowData.problemId;
				}
				);
			},
		});
		//按学生显示主窗口
		$('#datagrid3').datagrid({
			striped:true,
			singleSelect:true,
			fitColumns:true,
			width:610,
			height:170,
			remoteSort:false,
			columns:[[
				{field:'stuId',hidden:true},
				{field:'stuNo',title:'学号',align:'center',width:115},
				{field:'stuName',title:'姓名',align:'center',width:115},
				{field:'scale',title:'通过题数',align:'center',width:80,sortable:true,
					sorter:function(a,b){ //排序  
						a = a.split('/');  
						b = b.split('/');  
						return (parseInt(a[0])>parseInt(b[0])?1:-1);  
					}  
				}
				]],
			onDblClickRow:function(rowIndex,rowData){
				var schemeId = $('#schemeCombo').combogrid("getValue");
				aw.tempData.stuIndex = rowIndex;//保存索引
				$.post("getProDetail.action",{stuId:rowData.stuId,schemeId:schemeId},
					function(data){
					    aa=true;
					    bb=false;
						$('#datagrid4').datagrid("loadData",data.rows);
					}
				);
			}
		});
		
		//按习题显示副窗口
		$('#datagrid2').datagrid({
			striped:true,
			singleSelect:true,
			width:1280,
			height:180,
			fitColumns:true,
			columns:[[
				{field:'exId',hidden:true},
				{field:'stuAnswer',hidden:true},
				{field:'comment',hidden:true},
				{field:'stuNo',title:'学号',align:'center',width:100},
				{field:'stuName',title:'姓名',align:'center',width:80},
				{field:'pass',title:'通过',align:'center',width:50,formatter:function(value,rowData,rowIndex){
					if(rowData.runPass > 0){return '是';}
					else if(rowData.runPass < 0){ return '否';}
					else {return '';}
				}},
				{field:'score',title:'得分',align:'center',width:50},
				{field:'cmpError',title:'编译错误次数',align:'center',width:90},
				{field:'cmpPass',title:'编译通过次数',align:'center',width:90},
				//{field:'runPass',title:'运行正确次数',align:'center',width:100},
				{field:'runError',title:'运行错误次数',align:'center',width:90},
				{field:'firstPass',title:'首次通过时间',align:'center',width:120},
				{field:'lastSave',title:'最后提交时间',align:'center',width:120},
				{field:'startTime',title:'允许开始时间',align:'center',width:120},
				{field:'deadline',title:'要求完成时间',align:'center',width:120},
				{field:'memo',title:'题目说明',align:'center',width:150}
				]],
			onClickRow:function(rowIndex,rowData){
				bb=true;
				//$('#text_StuAnswer').attr("value","");
				//$('#text_comment').attr("value","");
				aw.tempData.exId = rowData.exId;
				aw.tempData.stuAnswer = rowData.stuAnswer;
				$('#datagrid1').datagrid("selectRow",aw.tempData.exIndex);
				$('#text_StuAnswer').attr("value",rowData.stuAnswer);
				$('#text_comment').attr("value",rowData.comment);
				$('#exScore').attr("value",rowData.escore);
				$('#saveComment').attr("name",rowIndex);//保存行标识
			}
		});
		//按学生显示副窗口
		$('#datagrid4').datagrid({
			striped:true,
			singleSelect:true,
			width:1260,
			height:180,
			fitColumns:true,
			columns:[[
				{field:'exId',hidden:true},
				{field:'schemeId',hidden:true},
				{field:'problemId',hidden:true},
				{field:'topic',hidden:true},
				{field:'answer',hidden:true},
				{field:'stuAnswer',hidden:true},
				{field:'comment',hidden:true},
				{field:'chapName',title:'章名',align:'center',width:80},
				{field:'proName',title:'习题名',align:'center',width:80},
				{field:'pass',title:'通过',align:'center',width:50,formatter:function(value,rowData,rowIndex){
					if(rowData.runPass > 0){return '是';}
					else if(rowData.runPass < 0){ return '否';}
					else {return '';}
				}},
				{field:'escore',title:'得分',align:'center',width:50},
				{field:'cmpError',title:'编译错误次数',align:'center',width:90},
				{field:'cmpPass',title:'编译通过次数',align:'center',width:90},
				//{field:'runPass',title:'运行正确次数',align:'center',width:100},
				{field:'runError',title:'运行错误次数',align:'center',width:90},
				{field:'firstPass',title:'首次通过时间',align:'center',width:120},
				{field:'lastSave',title:'最后提交时间',align:'center',width:120},
				{field:'startTime',title:'允许开始时间',align:'center',width:120},
				{field:'deadline',title:'要求完成时间',align:'center',width:120},
				{field:'memo',title:'题目说明',align:'center',width:150}
				]],
			onClickRow:function(rowIndex,rowData){
				bb=true;
				//$('#text_StuAnswer').attr("value","");
				//$('#text_comment').attr("value","");
				aw.tempData.exId = rowData.exId;
				aw.tempData.proId = rowData.problemId;
				aw.tempData.pInfo = rowData.pInfo;//保存运行测试信息
				aw.tempData.stuAnswer = rowData.stuAnswer;
				aw.tempData.exInfo = rowData.chapName+rowData.proName;
				$('#datagrid3').datagrid("selectRow",aw.tempData.stuIndex);//复原小表格
				$('#text_title').attr("value",rowData.topic);
				$('#text_TAnswer').attr("value",rowData.answer);
				$('#text_StuAnswer').attr("value",rowData.stuAnswer);
				$('#text_comment').attr("value",rowData.comment);
				$('#exScore').attr("value",rowData.escore);
				$('#saveComment').attr("name",rowIndex);//保存行标识
			}
		});
		
		//保存资料
		$('#data1').datagrid({
				singleSelect:false,
				width:200,
				height:280,
				fitColumns:true,
				columns:[[
					{field:'ck1',checkbox:true},
					{field:'schemeId',hidden:true},
					{field:'problemId',hidden:true},
					{field:'chapName',title:'章名',align:'left',width:80},
					{field:'proName',title:'习题名',align:'left',width:80},
					]],
				onLoadSuccess:function(data){
   				   	 $('#data1').datagrid('selectAll');
            	} 
			});
			$('#data2').datagrid({
				singleSelect:false,
				width:200,
				height:280,
				fitColumns:true,
				columns:[[
					{field:'ck2',checkbox:true},
					{field:'stuId',hidden:true},
					{field:'stuNo',title:'学号',align:'left',width:110},
					{field:'stuName',title:'姓名',align:'left',width:80},
					]],
				onLoadSuccess:function(data){
   				    $('#data2').datagrid('selectAll');
            	} 
			});
		
		//保存资料窗口中'指定学生和习题'的附加窗口默认隐藏
        $("#button1").click(function(){//所有学生和所有习题
        	$("#saveStuDataDiv").window("resize",{width:370,left:395});
        	$("#hideDiv").hide();
       });
       $("#button2").click(function(){//“指定学生和习题”换切
    	   	$("#saveStuDataDiv").window("resize",{width:780,left:200});
    	   	$("#hideDiv").show();
       });
       //隐藏模块按钮侦听
        
	}
/**加载所有组件end**/
	
	
/**按钮监听**/	
	aw.clickBtn = function(){
		//保存资料
		$('#saveData').click(function(){
			    if(schemeFlag == false){
			    	alert("请选中作业表");
			    	return;
			    }
				//var teaComment = $("#text_comment").val();
				//var score = 0;
				//if($("#exScore").numberbox("isValid")){
					//score = $("#exScore").val();
				//}
				$("#saveStuDataDiv").window('open');
				$("#cancel").click(function(){
			     	window.$("#saveStuDataDiv").window('close');		
				});
				$("#saveFile").click(function(){
			    	aw.downloadExcel();		
				});
		});
		//运行测试
		$('#run').click(function(){
			runTest(aw.tempData.ceId,aw.tempData.schemeId,aw.tempData.proId,aw.tempData.exInfo,
				loadXML(aw.tempData.pInfo),aw.tempData.stuAnswer);
		});
		//批改
		$('#analyWork').click(function(){
			if(aa!=true || bb!=true)alert("请选中学生和习题");
			else $("#analy").window('open');
		});
		//保存评语
		$('#saveComment').click(function(){
			var comment = $('#text_comment').val();
			var escore = $('#exScore').val();
			var exId = aw.tempData.exId;
			$.post("savaData.action",{exId:exId,comment:comment,score:escore},function(data){
				var flag = $("input[name='sort']:checked").val();
				var gripIndex = $('#saveComment').attr("name");
				if(flag == 'exercise'){
					$('#datagrid2').datagrid("updateRow",{index:gripIndex,row:{escore:escore,comment:comment}});
				}else {
					$('#datagrid4').datagrid("updateRow",{index:gripIndex,row:{escore:escore,comment:comment}});
				}
				alert(data.msg);
			});
		});
		$('#signature').click(function(){
			var name = $('#tea', parent.document).text();
			var sign =$('#text_comment').val() + "\n\n" + name + "\n" + aw.currentTime();
			$('#text_comment').attr("value",sign);
		});
	}
/**按钮监听end**/		
	
/**作业表类型改变**/
	aw.changeChk = function(){
		 $('input[name="sort"]').change(function(){//按题目显示
			if($(this).val() == "exercise"){
				aw.switchScheme();
			}
			else {
				aw.switchStu();
			}
		 });
	}
/**作业表类型改变end**/
	
/**外部函数**/
	
	//加载班级
	aw.loadClass = function(courseId){
		$('#classCombo').combogrid({
			url:"getClassesList.action",
			queryParams:{courseId:courseId},
		});
	}
	
	//加载作业表
	aw.loadScheme = function(cid){
		$('#schemeCombo').combogrid({url:"getScheme.action",queryParams:{cid:cid,courseId:aw.tempData.courseId}});
	}
	//加载数据框
	aw.loadTempGridData = function(cid,schemeId){
		$.post(
			"getTempGridData.action",
			{cid:cid,schemeId:schemeId},
			function(data){
				$('#StuSmallGrid').css('display','inline');
				$('#StuBigGrid').css('display','inline');
				aw.loadDataGridData(
					'datagrid1',data.map.scheme,
					'datagrid4',data.map.scheme,
					'datagrid2',data.map.stu,
					'datagrid3',data.map.stu,
					'data1',data.map.scheme,
					'data2',data.map.stu);
			})
	}
	//换切到按题目显示
	aw.switchScheme = function(){
		$('#StuSmallGrid').css('display','none');
		$('#StuBigGrid').css('display','none');
		$('#ExSmallGrid').css('display','inline');
		$('#ExBigGrid').css('display','inline');
		//还原数据
		if(aw.tempData.exIndex){
			$('#datagrid1').datagrid("selectRow",aw.tempData.exIndex);
		}
		var d1 = $('#datagrid1').datagrid("getSelected");
		var d2 = $('#datagrid2').datagrid("getSelected");
		if(d1){
			$("#text_TAnswer").attr("value",d1.answer);
			$("#text_title").attr("value",d1.topic);
			aw.tempData.proId = d1.problemId;
			aw.tempData.pInfo = d1.pInfo;
			aw.tempData.exInfo = d2.chapName+d2.proName;
		}else{
			$("#text_TAnswer").attr("value","");
			$("#text_title").attr("value","");
			aw.tempData.proId = null;
			aw.tempData.pInfo = null;
			aw.tempData.exInfo = null;
		}
		if(d2){
			$("#text_StuAnswer").attr("value",d2.stuAnswer);
			$("#text_comment").attr("value",d2.comment);
			$("#exScore").attr("value",d2.score);
			aw.tempData.exId = d2.exId;
			aw.tempData.stuAnswer = d2.stuAnswer;
		}else{
			$("#text_StuAnswer").attr("value","");
			$("#text_comment").attr("value","");
			$("#exScore").attr("value","");
			aw.tempData.exId = null;
			aw.tempData.stuAnswer = null;
		}
	}
	//换切到按学生显示
	aw.switchStu = function(){
		$('#StuSmallGrid').css('display','inline');
		$('#StuBigGrid').css('display','inline');
		$('#ExSmallGrid').css('display','none');
		$('#ExBigGrid').css('display','none');
		//还原数据
		if(aw.tempData.stuIndex)
			{$('#datagrid3').datagrid("selectRow",aw.tempData.stuIndex);}
		var d2 = $('#datagrid4').datagrid("getSelected");
		if(d2){//已选择
			$("#text_TAnswer").attr("value",d2.answer);
			$("#text_title").attr("value",d2.topic);
			$("#text_StuAnswer").attr("value",d2.stuAnswer);
			$("#text_comment").attr("value",d2.comment);
			$("#exScore").attr("value",d2.score);
			aw.tempData.proId = d2.problemId;
			aw.tempData.exId = d2.exId;
			aw.tempData.pInfo = d2.pInfo;
			aw.tempData.stuAnswer = d2.stuAnswer;
			aw.tempData.exInfo = d2.chapName+d2.proName;
		}else{//未选择，清除数据
			$("textarea").attr("value","");
			$("#exScore").attr("value","");
			aw.tempData.proId = null;
			aw.tempData.exId = null;
			aw.tempData.pInfo = null;
			aw.tempData.stuAnswer = null;
			aw.tempData.exInfo = null;
		}
	}
	//加载DataGrid数据,奇数为id,偶数为数据
	aw.loadDataGridData = function(){
		var numargs = arguments.length;//参数长度
		for(var i = 0;i < numargs; i=i+2){
			$('#'+arguments[i]).datagrid("loadData",arguments[i+1]);
		}
	}
	//清除指定文本域id的数据
	aw.cleanTextData = function(){
		var numargs = arguments.length;//参数长度
		for(var i = 0;i < numargs; i++){
			$('#'+arguments[i]).attr("value","");
		}
	}
	//清除指定表格id的数据
	aw.cleanGridData = function(){
		var numargs = arguments.length;//参数长度
		for(var i = 0;i < numargs; i++){
			$('#'+arguments[i]).datagrid("loadData",{rows:[]});
		}
	}
	
	//获取当前时间(eg:2012-01-01 12:00)
	aw.currentTime = function(){
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth();
		var day = now.getDay();
		var hh = now.getHours();
		var mm = now.getMinutes();
		
		var clock = year + '-';
		if(month < 10)
			clock += '0';
		clock += month + '-';
		if(day < 10)
			clock +='0';
		clock += day + " ";
		if(hh < 10)
			clock += '0';
		clock += hh + ':';
		if(mm < 10)
			clock += '0';
		clock += mm;
		return clock;
	}
	
	aw.downloadExcel = function(){
		//1：按习题|2：按学生
		$("#sort").val($("input[name='radio1']:checked").val());
		//1：所有习题|2：指定习题
		var count = $("input[name='radio2']:checked").val();
		if(count == 1){
			$('#data1').datagrid("selectAll");
			$('#data2').datagrid("selectAll");
		}
		var exArr = $('#data1').datagrid("getSelections");
		var stuArr = $('#data2').datagrid("getSelections");
		if(aw.tempData.courseId == 4){
			var proIdArr = "1,2";
		}else{
			var proIdArr = "";
		}
		var stuIdArr = "";
		var exArrLen = exArr.length;
		var stuArrLen = stuArr.length;
		for(var i = 0;i<exArrLen;i++){
			proIdArr += exArr[i].problemId + ",";
		}
		for(var i = 0;i<stuArrLen;i++){
			stuIdArr += stuArr[i].stuId + ",";
		}
		$("#proIdArr").val(proIdArr);
		$("#stuIdArr").val(stuIdArr);
		$("#cid").val(aw.tempData.cid);
		$("#schemeId").val(aw.tempData.schemeId);
		$("form").attr("action","downloadAWData.action");
		$("form").submit();
	}
	
	//初始化函数
    aw.init = function(){
   		aw.iniAllWins();//加载所有窗口
    	aw.loadComponent();//加载表格
    	aw.clickBtn();//确定按钮监听
    	aw.changeChk();//选项格式改变
   	}
    aw.init();
});