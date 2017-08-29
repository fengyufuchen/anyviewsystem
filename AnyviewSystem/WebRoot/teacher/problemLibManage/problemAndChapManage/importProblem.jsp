<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="unit">
		<label>请选择题型：</label>
		<input type="radio" name="type" value="0" />程序题
		<input type="radio" name="type" value="1" />例题
		<input type="radio" name="type" value="2" />填空题
		<input type="radio" name="type" value="3" />选择题
		<input type="radio" name="type" value="5" />判断题
	</div>
	<div>
		
		<input id="importFile" type="file" name="importFile" />
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<input type="button" value="开始上传" class="button" onclick="$('#importFile').uploadify('upload', '*');"/>
				</div>
			</div>
		</li>
		<li>
			<div class="button">
				<div class="buttonContent">
					<input type="button" value="取消上传" class="button" onclick="$('#importFile').uploadify('cancel', '*');"/>
				</div>
			</div>
		</li>
	</ul>
</div>

<script type="text/javascript">
$("#importFile").uploadify({
	swf:'common/plugins/uploadify/scripts/uploadify.swf',
	uploader:'teacher/problemManager/importProblems.action',
	formData:{'problemLib.lid':'${param.lid }', 'problemChap.chId':'${param.chId }'},
	buttonImage:'common/plugins/uploadify/img/add.jpg',
	buttonClass:'my-uploadify-button',
	fileObjName : "importFile",
	width:102,
	fileSizeLimit:'2048KB',
	fileTypeDesc:'*.xls;',
	fileTypeExts:'*.xls;',
	multi:false,
	auto:false,
	preventCaching: false,
	onUploadStart : function(file){
		var rs = $("input[name=type]:checked");
		if(rs.length!=1){
			alertMsg.error("请选择要导入的题类型");
			$('#importFile').uploadify('cancel', '*');
		}else{
			var param = {};
			param.type = rs[0].value;
			$('#importFile').uploadify("settings","formData",param, false);
		}
	},
	onUploadSuccess : function(file, data, response){
		dialogAjaxDone(JSON.parse(data));
	}
});
</script>