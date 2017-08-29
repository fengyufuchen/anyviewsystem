<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
//计算卷面分
function zongheAjaxDone(json){
    DWZ.ajaxDone(json);
    if (json.statusCode == DWZ.statusCode.ok){
    	var mode= $(":radio:checked ").val();	//选中的计算模式的radio的value
    	var mode2Arr=$(":checkbox:checked");	//选中的及格或满分要求的checkbox的组件集合
    	var fullFlag;	//通过一定题数是否及格的标识
    	var passFlag;	//通过一定题数是否满分的标识
    	var fullNum=new Number($("#fullNum").val());	//获得满分最少通过的题数
    	var passNum=new Number($("#passNum").val());	//获得及格的最少通过题数
    	var passScore=new Number($("#passScore").val());	//及格的分数
    	
     	$('.data').each(function() {	//遍历所有学生
     		var sum;
    		var juanmian=new Number($(this).find("td").eq(4).html()); // 卷面分
    		var taidu=new Number($(this).find("td").eq(5).html());	//态度分
    		var finishNum=new Number($(this).find("td").eq(2).html().replace("<div>","").replace("</div>","").split("/")[0]);	//完成题数
    		switch(mode){
        	case '1':
        	 sum=juanmian;
        	  break;
        	case '2':
        	 sum=taidu;
        	  break;
        	case '3':
          	  sum=taidu*juanmian;
          	  break;
        	case '4':
          	  sum=(taidu+juanmian)*0.5;
        	}
    		
    	 	mode2Arr.each(function(){	//遍历checkbox的所有值
        		if($(this).val()=='1'){
        			passFlag=1;
        		}else if($(this).val()=='2'){
        			fullFlag=1;
        		}
        		
        	});
    		if(sum<60&&passFlag==1&&finishNum>passNum){	//如果该学生总分没有及格，但是通过题目数量达到及格要求
        		sum=passScore;
        	}else if(sum<100&&fullFlag==1&&finishNum>fullNum){	//如果该学生总分没有满分，但通过题目数量达到满分要求
        		sum=100;
        	}
    		$(this).find("td").eq(6).html(sum);   
    	});  
    	$.pdialog.closeCurrent();
    }
}
</script>
<form class="pageForm required-validate" onsubmit="return validateCallback(this,zongheAjaxDone)" action="communion/submitSuccess.action" method="post" >
	<div >
		<div class="unit">
			<label><input type="radio" name="mode" value="1"/>只使用卷面分</label>
			<label><input type="radio" name="mode" value="2"/>只使用态度分</label>
			<label><input type="radio" name="mode" value="3"/>使用卷面分x态度分</label>
		</div>
		</br>
		<div class="unit">
			<label><input type="radio" name="mode" value="4" checked="checked"/>按比例使用卷面分和态度分(默认卷面分和态度分各占50%)</label>
		</div>
		</br>
		<div class="unit">
			<label>卷&nbsp面&nbsp分<input type="text" name="juanmianRatio" value="50" size="3"/></label>
			<label>态&nbsp度&nbsp分<input type="text" name="taiduRatio" value="50" size="3"/></label>
		</div>
		</br>
		</br>
		<div class="unit">
			<label><input type="checkbox" name="mode2" value="1" size="3"/>通过题目数量达到某个要求即可及格(非100分制自动换算)</label>
		</div>
		</br>
		<div class="unit">
			<label>通过题数<input id="passNum" type="text" name="passNum" value="" size="3"/></label>
			<label>及格分数<input id="passScore" type="text" name="passScore" value="60" size="3"/>(100分制)</label>
		</div>
		</br>
		<div class="unit">
			<label><input type="checkbox"  name="mode2" value="2" size="3"/>通过题目数量达到某个要求即可满分(非100分制自动换算)</label>
		</div>
		</br>
		<div class="unit">
		<label>通过题数	<input id="fullNum" type="text" name="fullNum" value="" size="3"/></label>
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