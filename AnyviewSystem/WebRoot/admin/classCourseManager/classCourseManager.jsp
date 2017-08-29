<%@ page contentType="text/html;charset=UTF-8"%>
<title>管理员主页</title>
<script type="text/javascript">
	var isclass = true;
	var calssid =-1;
	var courseid=-1;
	var leftdivclassinit = '<table id="leftTable" class="table" width="100%" targetType="navTab">'
			+ '<thead>'
			+ '<tr>'
			+ '<th width="50%" align="center">班号</th>'
			+ '<th width="50%" align="center">班名</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody id="leftContent">	' + '</tbody>' + '</table>';
	var leftdivcourseinit = '<table id="leftTable" class="table" width="100%" targetType="navTab">'
			+ '<thead>'
			+ '<tr>'
			+ '<th width="50%" align="center">序号</th>'
			+ '<th width="50%" align="center">课程名</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody id="leftContent">	' + '</tbody>' + '</table>';

	var rightdivclassinit = '<table class="table" width="100%" targetType="navTab">'
			+ '<thead>'
			+ '<tr>'
			+ '<th >班号</th>'
			+ '<th >班名</th>'
			+ '<th >作业表</th>'
			+ '<th >任课老师</th>'
			+ '<th >状态</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody id="rightContent">	'
			+ '</tbody>'
			+ '</table>'

	var rightdivcourseinit = '<table class="table" width="100%" targetType="navTab">'
			+ '<thead>'
			+ '<tr>'
			+ '<th >序号</th>'
			+ '<th >课程名</th>'
			+ '<th >作业表</th>'
			+ '<th >任课老师</th>'
			+ '<th >状态</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody id="rightContent">	'
			+ '</tbody>'
			+ '</table>'

	$(function() {
		String.prototype.format = function(args) {
			var result = this;
			if (arguments.length > 0) {
				if (arguments.length == 1 && typeof (args) == "object") {
					for ( var key in args) {
						if (args[key] != undefined) {
							var reg = new RegExp("({" + key + "})", "g");
							result = result.replace(reg, args[key]);
						}
					}
				} else {
					for (var i = 0; i < arguments.length; i++) {
						if (arguments[i] != undefined) {
							var reg = new RegExp("({[" + i + "]})", "g");
							result = result.replace(reg, arguments[i]);
						}
					}
				}
			}
			return result;
		}

		$("#byclass").click(loadClass);
		$("#bycourse").click(loadCourse);
		$("#byclass").trigger("click");
		$("#setting").click(setting);
		loadSchemeList();
		loadTeacherList();
	});

	function leftclick() {
		var id = $(this).attr('id');
		id = id.substr(6, id.length);
		if (isclass == true) {
			classid=id;
			$.post('admin/classCourseManager/ccsinfobyclass.action', {
				"classId" : id
			}, function(data) {

				var ccsdata = data.ccsList;
				for (var i = 0; i < ccsdata.length; i++) {
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(2)';
					$(id).html(ccsdata[i][1]);
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(3)';
					$(id).html(ccsdata[i][2]);
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(4)';
					$(id).html('有效');
				}

			});
		} else {
			courseid=id;
			$.post('admin/classCourseManager/ccsinfobycourse.action', {
				"courseId" : id
			}, function(data) {

				var ccsdata = data.ccsList;
				for (var i = 0; i < ccsdata.length; i++) {
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(2)';
					$(id).html(ccsdata[i][1]);
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(3)';
					$(id).html(ccsdata[i][2]);
					var id = '#rightid' + ccsdata[i][0] + ' td:eq(4)';
					$(id).html('有效');
				}

			});
		}
	}
	
	function rightclick() {
		var id = $(this).attr('id');
		id = id.substr(7, id.length);
		if (isclass == true) {
			courseid=id;
		} else {
			classid=id;
		}
	}

	function setting() {
		if(classid==-1){
			alert("请选择班级")
			return;
		}
		if(courseid==-1){
			alert("请选择课程")
			return;
		}
		var vid=$("#schemelist").val();
		var tid=$("#teacherlist").val();
		var status=$("#status").val();
		$.post('admin/classCourseManager/setting.action',{"classId":classid,"courseId":courseid,"vid":vid,"tid":tid,"status":status}, function(data) {
			var id=isclass?classid:courseid;
			$("#leftid"+id).trigger("click");
		});
	}

	function loadSchemeList() {
		$.post('admin/classCourseManager/allScheme.action', null, function(data) {
			var schemeData = eval(data).schemeList;
			for (var i = 0; i < schemeData.length; i++) {
				var varItem = new Option(schemeData[i][1], schemeData[i][0]);
				$("#schemelist")[0].options.add(varItem);
			}
		});
	}

	function loadTeacherList() {
		$.post('admin/classCourseManager/allTeacher.action', null, function(data) {
			var teacherData = eval(data).teacherList;
			for (var i = 0; i < teacherData.length; i++) {
				var varItem = new Option(teacherData[i][1], teacherData[i][0]);
				$("#teacherlist")[0].options.add(varItem);
			}
		});
	}

	function loadClass() {
		calssid =-1;
		courseid=-1;
		$.post('admin/classCourseManager/allInfo.action', null, function(data) {
			isclass = true;
			$('#leftdiv').html(leftdivclassinit);
			$('#rightdiv').html(rightdivcourseinit);
			var classData = eval(data).classList;
			var classTableString = "";
			var trHead = "<tr id='leftid{0}'>";
			var trEnd = "</tr>";
			var tdHead = "<td align='center'>";
			var tdEnd = "</td>";
			for (var i = 0; i < classData.length; i++) {
				classTableString += (trHead.format(classData[i][1]));

				classTableString += tdHead;
				classTableString += classData[i][0];
				classTableString += tdEnd;

				classTableString += tdHead;
				classTableString += classData[i][0];
				classTableString += tdEnd;

				classTableString += trEnd;
			}
			$('#leftContent').html(classTableString);
			trHead = "<tr id='rightid{0}'>";
			var courseData = eval(data).courseList;
			var courseTableString = "";
			for (var i = 0; i < courseData.length; i++) {
				courseTableString += (trHead.format(courseData[i][1]));

				courseTableString += tdHead;
				courseTableString += courseData[i][1];
				courseTableString += tdEnd;

				courseTableString += tdHead;
				courseTableString += courseData[i][0];
				courseTableString += tdEnd;

				courseTableString += tdHead;
				courseTableString += tdEnd;

				courseTableString += tdHead;
				courseTableString += tdEnd;

				courseTableString += tdHead;
				courseTableString += '无效';
				courseTableString += tdEnd;

				courseTableString += trEnd;
			}
			$('#rightContent').html(courseTableString);

			$("table").jTable();
			$("#leftContent tr").bind("click", leftclick);
			$("#rightContent tr").bind("click", rightclick);
		});
	};

	function loadCourse() {
		calssid =-1;
		courseid=-1;
		$.post('admin/classCourseManager/allInfo.action', null, function(data) {
			isclass = false;
			$('#leftdiv').html(leftdivcourseinit);
			$('#rightdiv').html(rightdivclassinit);
			var courseData = eval(data).courseList;
			var courseTableString = "";
			var trHead = "<tr id='leftid{0}'>";
			var trEnd = "</tr>";
			var tdHead = "<td align='center'>";
			var tdEnd = "</td>";
			for (var i = 0; i < courseData.length; i++) {
				courseTableString += (trHead.format(courseData[i][1]));

				courseTableString += tdHead;
				courseTableString += courseData[i][1];
				courseTableString += tdEnd;

				courseTableString += tdHead;
				courseTableString += courseData[i][0];
				courseTableString += tdEnd;

				courseTableString += trEnd;
			}
			$('#leftContent').html(courseTableString);
			trHead = "<tr id='rightid{0}'>";
			var classData = eval(data).classList;
			var classTableString = "";
			for (var i = 0; i < classData.length; i++) {
				classTableString += (trHead.format(classData[i][1]));

				classTableString += tdHead;
				classTableString += classData[i][0];
				classTableString += tdEnd;

				classTableString += tdHead;
				classTableString += classData[i][0];
				classTableString += tdEnd;

				classTableString += tdHead;
				classTableString += tdEnd;

				classTableString += tdHead;
				classTableString += tdEnd;

				classTableString += tdHead;
				classTableString += '无效';
				classTableString += tdEnd;

				classTableString += trEnd;
			}
			$('#rightContent').html(classTableString);

			$("table").jTable();
			$("#leftContent tr").bind("click", leftclick);
			$("#rightContent tr").bind("click", rightclick);
		});
	};
</script>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><input type="radio" name="type" id="byclass" />按班级设置 <input
				type="radio" name="type" id="bycourse" />按课程设置 
			<li><span>作业表:</span> <select id="schemelist"></select></li>
			<li><span>教师:</span> <select id="teacherlist"></select></li>
			<li><span>状态:</span> <select id="status">
					<option value="1">有效</option>
					<option value="0">无效</option>
			</select></li>
			<li><input id="setting" type="button" value="设置" /></li>
		</ul>
	</div>
	<div id="rightdiv"
		style="width: 69%; float: right; border: 1px solid gray;">
		<table id="rightTable" class="table" width="100%" targetType="navTab">
			<thead>
				<tr>
					<th>序号</th>
					<th>课程名</th>
					<th>作业表</th>
					<th>任课老师</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody id="rightContent">
			</tbody>
		</table>
	</div>
	<div id="leftdiv" style="width: 29%; border: 1px solid gray;">
		<table id="leftTable" class="table" width="100%" targetType="navTab">
			<thead>
				<tr>
					<th width="50%" align="center">班号</th>
					<th width="50%" align="center">班名</th>
				</tr>
			</thead>
			<tbody id="leftContent">

			</tbody>
		</table>
	</div>
</div>