<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
//计算卷面分
function juanmianAjaxDone(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
        	  var failScore=0;
			  //题目已通过，但老师未批改的题目的分数
			  var score1;
		      var student=json.allMsg;
			  for(var key in student){
				  var s='tr[rel="'+student[key].sid+'"]';
				  var sum=0;	//该学生该计划表所有题目的总分
				  var ex=student[key].allScore;
				  //遍历一个学生的所有习题
				  for(var o in ex){
					  if(ex[o].runResult==0)	//如果没有通过
							 score1=failScore;
						 else if(ex[o].score1==null)	//如果题目通过，但教师没有评分，默认为得到该题的满分
							 score1=ex[o].score2;
						 else	//题目通过，且教师已评分
							 score1=ex[o].score1;
						//sum+=ex[o].score2*score1;
						sum+=score1;
				  }
				  $(s).find("td").eq(4).html(sum);
			  } 
          $.pdialog.closeCurrent();
    }
}

//载入页面完成之后，将vid，cid，sid等信息装入表单
$(function() {
	var vid=$("#SchemeSelected1").val();
	var cid=$("#ClassSelected1").val();
	var arr="";
	$('.data').each(function() {
		arr=arr+$(this).attr("rel")+',';
		}); 
	$("#vid").val(vid);
	$("#cid").val(cid);
	$("#sid").val(arr);
});

</script>
<form class="pageForm required-validate" onsubmit="return validateCallback(this,juanmianAjaxDone)" action="communion/gainExerciseAjax.action" method="post" >
	<div class="pageFormContent" layouth="58" style="height: 150px; overflow: auto;">
		<input name="cid" id="cid" value="" type="hidden"/>
		<input name="vid" id="vid" value="" type="hidden"/>
		<input name="sid" id="sid" value="" type="hidden"/>
		<div><font color="red" size="40">说明：卷面分=求和（每道习题的得分）</font></div>
		</br>
		<div>如果题目没有通过，按照0分处理</div> 
		</br>
		<div>如果题目已通过，但没有被老师批改，该学生得到该题的满分</div> 
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">计算</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>