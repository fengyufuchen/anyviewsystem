<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!--文件名：batAdminAddStudent.jsp-->
<!--描   述：批量添加学生页面-->
<!--时   间 ：2015年08月29日-->

 <html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>批量添加学生</title>
		<link rel="stylesheet" type="text/css" href="../../common/js/jQ_Easy/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../common/js/jQ_Easy/themes/icon.css">
		<script type="text/javascript" src="../../common/js/jQ_Easy/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="../../common/js/jQ_Easy/jq_easyui.min.js"></script>
		<script type="text/javascript" src="batAdminAddStudent.js"></script>
	</head>
<body class="easyui-layout">
	<div region="north" split="true" style="height:300px;padding:1px;">
			<table id="stuInfo"></table>
	</div>
		<div region="center" title="批量生成学生资料" style="overflow:auto;">
			<div class="easyui-tabs" fit="true" border="false" id="addstutabs">   <!-- easyui-tabs使下面三个div成为选卡项-->
				<div title="添加或编辑" style="padding:20px;overflow:hidden;" name="editDiv"> 
					<div style="float: left">
					学校：<select class="easyui-combobox" name="class_1" id="class_1" style="width:210px" panelHeight="100" >
							
											<c:forEach items="${uniList }" var="uni">
											<option value="${uni.unID }">${uni.unName }</option>
											</c:forEach>
						</select><br><br>
					学号：<input class="easyui-validatebox" type="text" name="num_1" required=true style="width:210px"/>
					&nbsp;&nbsp;&nbsp;
					</div>
					<div style="float: left">
					性别：
						<select id="sex_1" class="easyui-combobox" name="sex_1" style="width:200px;" editable=false required=true panelHeight="auto"panelWidth="200">
								<option value="男">男</option>
								<option value="女">女</option>
						</select><br><br>
					姓名：<input class="easyui-validatebox" type="text" name="name_1" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					</div>
					<div style="float: left">
					有效状态：
						<select id="attr_1" class="easyui-combobox" name="attr_1" style="width:100px;" required=true panelHeight="auto"panelWidth="100">
								<option value="正常">正常</option>
								<option value="停用">停用</option>
						</select><br><br>
						<div style="float: right;width:450px;text-align:left">
							<a id="modify_1" href="#" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
							<a id="temAdd_1" href="#" class="easyui-linkbutton" iconCls="icon-add">添加</a>
						</div>
					</div>
				</div>
				
				
				<div title="按帐号创建"  style="padding:20px;text-align: center">
					<div style="float: left;text-align: center;width:40%">
						学校：<select class="easyui-combobox" name="class_2" id="class_2" style="width:210px" panelHeight="100" >
							
											<c:forEach items="${uniList }" var="uni">
											<option value="${uni.unID }">${uni.unName }</option>
											</c:forEach>
						</select><br><br>	
						学号：
						<input  id="beginNo" class="easyui-numberbox" min="0"  precision="0" required="true" style="width:50px"/>-
						<input  id="endNo" class="easyui-numberbox" min="0"  precision="0" required="true" style="width:50px"/>&nbsp;占位
						<input id="spinner" class="easyui-numberspinner" value="2" min="1" max="100" required="true" editable=false style="width:40px;"></input>
					</div>
					<div style="float: left;text-align: center;width:25%">
						性别：<select id="sex_2" class="easyui-combobox" name="sex_2" style="width:130px;" editable=false required=true panelHeight="auto"panelWidth="160">
									<option value="男">男</option>
									<option value="女">女</option>
							</select><br><br>
						有效状态：
						<select id="attr_2" class="easyui-combobox" name="attr_2" style="width:100px;" required=true panelHeight="auto"panelWidth="100">
								<option value="正常">正常</option>
								<option value="停用">停用</option>
						</select><br><br>
					</div>
					<div style="float: left;font-size:16px;text-align: right;width:35%;color:blue">
						示例：学号：2015001-2015002 占位：7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>则创建后的学号为：2015001 和 2015002
					</div><br><br><br>
					<div style="float: left;text-align: left;width:10%">
						<a id="temAdd_2" href="#" class="easyui-linkbutton" iconCls="icon-add">添加</a>
					</div>
				</div>
				
				<div title="从文件导入" style="overflow:hidden;padding:20px;">
					<div style="float:left;">
					学校：<select class="easyui-combobox" name="class_3" id="class_3" style="width:210px" panelHeight="100" >
							
											<c:forEach items="${uniList }" var="uni">
											<option value="${uni.unID }">${uni.unName }</option>
											</c:forEach>
						</select><br>
					<form id="ff" method="post" enctype="multipart/form-data" target="hidden">
						<div class="toolbar">
							当前文件：
							<input type="file" name="file"/>
							<br>
							<a id="tempAdd_3" href="#" class="easyui-linkbutton" iconCls="icon-add">添加</a>
						</div>
						<input type="hidden" name="f_cla_IdToName" value=""/>
					</form>
					<iframe name="hidden" style="visibility:hidden;"></iframe>
					</div>
					&nbsp;&nbsp;
					<div name="state" style="float:right;background-color:#E0E0E0;color:blue;">
					<div name="part1" style="float:left;">
						说明：<br>学校名字独立第一行第二个,学校必须已经存在,具体格式如下：<br>
						&nbsp;学校信息：学校   学校名字   占位(注:每个属性占一格)&nbsp;&nbsp;&nbsp;<br>
						&nbsp;学生信息：学号 姓名 性别  (注:每个属性占一格;每个学生占一行;性别可以不写)
					</div>
					<div name="part2" style="float:left">
						例子：<br>
						<table border="1">
						<tr>
							<td>学校</td><td>广东工业大学</td><td></td>
						</tr>
						<tr>
							<td>2015001</td><td> 李明</td><td>男</td>
						</tr>
						<tr>
							<td>2015002</td><td>张三</td><td>男</td>
						</tr>
						</table>
					</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>