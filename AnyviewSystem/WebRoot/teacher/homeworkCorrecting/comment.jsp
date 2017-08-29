<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<textarea style="height:92%;width:99%;">
${exercise.ecomment }
</textarea>
<a class="button" style="float:right" href="#" onclick="commitComment(${exercise.eid},this)" ><span>提交</span></a>

<script>
function commitComment(eid,o){
	obj = $(o);
	text = obj.prev("textarea").val();
	$.ajax({
		url:"teacher/homeworkCorrecting/updateEcomment.action",
		data:{'exercise.eid':eid,'exercise.ecomment':text}
	}).done(function(resp){
		if(resp=="success"){
			$.pdialog.closeCurrent();
			alertMsg.correct("修改成功");
		}else{
			alertMsg.error("修改失败");
		}
	});
}
</script>