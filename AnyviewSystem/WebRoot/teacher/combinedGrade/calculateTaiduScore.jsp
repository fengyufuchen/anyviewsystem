<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
//计算卷面分
function taiduAjaxDone(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
    	var min=new Number(json.min);	//最先完成的的得分
		var max=new Number(json.max);	//最后完成的得分
		var numerator=max-min;	// 分子=max-min
		var student=json.allMsg;
		 for (var key in student) {
			var sum=0;	//总分
			var s='tr[rel="'+student[key].sid+'"]';
			 var info=student[key].scoreArr;	//一个学生的所有题目
		 	for(var o in info){
				//分母=截止时间-最先提交时间，精确到分钟数
				var denominator = Math.floor(Math.abs(new Date(info[o].finishTime)-new Date(info[o].earliestFinishTime))/1000/60);
				//每分钟的分值=分子/分母,精确到百分位
				var scorePerMin=(numerator/denominator);	
				//该学生首次通过时间与截止时间之间相差的分钟数
				var interval=Math.floor(Math.abs(new Date(info[o].finishTime)-new Date(info[o].firstPastTime))/1000/60);
				//该题最低分数
				//var minScore=new Number(info[o].minScore);
				sum=min+scorePerMin*interval;
			} 
			$(s).find("td").eq(5).html(sum); 
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
<form class="pageForm required-validate" onsubmit="return validateCallback(this,taiduAjaxDone)" action="communion/gainScoreFinishtimeFirstpasttime.action" method="post" >
	<div class="pageFormContent" layouth="58" style="height: 150px; overflow: auto;">
		<input name="cid" id="cid" value="" type="hidden"/>
		<input name="vid" id="vid" value="" type="hidden"/>
		<input name="sid" id="sid" value="" type="hidden"/>
		<div class="unit" style="color:red;font-size:15;line-height:20px">说明：态度分是根据学生做题的积极性来评分，越先完成得分越高。
		以最先完成者的时间到题目规定完成时间间隔作为分母（以分钟为单位）。以您确定的分数段作分子，得到期间每分钟的分数值（100分制）。每题
		分数=最低分+每分值分数*离规定完成时间分钟数。然后累加所有习题得到最后分数
		</div>
		<div class="unit">
			<label>最先完成者的得分是：</label>
			<input  class="required textInput" type="text"   size="3" name="max" " value="100"/>
			<label>分（100分制）</label>
		</div>
		<div class="unit">
			<label>最后完成者的得分是：</label>
			<input class="required textInput" type="text"  size="3" name="min" " value="60"/>
			<label>分（100分制）</label>
		</div>
		
		
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