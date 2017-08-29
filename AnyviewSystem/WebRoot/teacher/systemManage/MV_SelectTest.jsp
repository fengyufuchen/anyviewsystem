<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>

		<link rel="stylesheet" type="text/css" href="../../jQ_Easy/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../jQ_Easy/themes/icon.css">
		<script type="text/javascript" src="../../jQ_Easy/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="../../jQ_Easy/jq_easyui.min.js"></script>
		<script type="text/javascript" src="../../js/sys-manage/selectTest.js"></script>
		
  </head>
  
  <body class="easyui-layout"> 
  
   <div region="center">
		<div class="easyui-layout" fit="true">
			<div region="center" split="true">
			    <table id="problem"></table>
			</div>
			<div region="east"  style="width: 400px;overflow: hidden;">
			    <div id="showProCon" style="margin:20px;"></div>
			</div>
			<div region="south" split="true" style="height: 210px; overflow: hidden;">
				<div class="easyui-layout" fit="true">
				    <div region="west" style="width:250px;">
				        <table id="sheList"></table>
				    </div>
				    <div region="center" >
				        <fieldset style="padding: 8px 20px 8px 30px; margin: 8px;" >
				            <legend>
				                <span>设置</span>
				            </legend>
				            <div>
				                <table>
				                    <tr><td colspan="4">分值：</td></tr>
				                    <tr>
				                        <td colspan="2"><input type="text" id="value"/>%</td>
				                        <td><a id="scoSet" href="#" class="easyui-linkbutton">设置</a></td>
				                        <td><a id="scoAve" href="#" class="easyui-linkbutton">全部平均</a></td>
				                    </tr> 
				                    <tr><td colspan="4"/></tr>
				                  
				                    <tr><td colspan="4">开始时间：</td></tr>
				                    <tr>
				                        <td><input id="beginDate" type="text" class="easyui-datebox"/></td>
				                        <td><input id="beginAdjust" type="text" class="easyui-timespinner"/></td>
				                        <td><a id="beginSet" href="#" class="easyui-linkbutton">设置</a></td>
				                        <td><a id="beginUn" href="#" class="easyui-linkbutton">全部统一</a></td>
				                    </tr>
				                    <tr><td colspan="4"/></tr>
				                   
				                    <tr><td colspan="4">完成时间</td></tr>
				                    <tr>
				                        <td><input id="finishDate" type="text" class="easyui-datebox"/></td>
				                        <td><input id="finishAdjust" type="text" class="easyui-timespinner"/></td>
				                        <td><a id="finishSet" href="#" class="easyui-linkbutton">设置</a></td>
				                        <td><a id="finishUn" href="#" class="easyui-linkbutton">全部统一</a></td>
				                    </tr>
				                </table>
				            </div>
				        </fieldset>
				    </div>
				    <div region="east" style="width:400px;">
				        <div class="easyui-layout" fit="true">
				            <div region="center" style="width:200px;">
				                <fieldset style="padding:12px 40px;margin:10px;">
				                    <legend>
				                        <span>选择</span>
				                    </legend>
				                    <div>
				                        <table>
				                            <tr><td>当前选中题数：</td></tr>
						                    <tr><td/></tr>
				                            <tr><td><span id="proNum"></span></td></tr>
				                            <tr><td/></tr>
						                    <tr><td/></tr>
				                            <tr><td><a id="allSelect" href="#" class="easyui-linkbutton">全选</a></td></tr>
				                            <tr><td/></tr>
						                    <tr><td/></tr>
				                            <tr><td><a id="noSelect" href="#" class="easyui-linkbutton">不选</a></td></tr>
				                            <tr><td/></tr>
						                    <tr><td/></tr>
				                            <tr><td><a id="reverSelect" href="#" class="easyui-linkbutton">反选</a></td></tr>
				                        </table>
				                    </div>
				                </fieldset>
				            </div>
				            <div region="east" style="width:200px;">
				                <div style="padding:40px 50px;">
				                    <table>
				                        <tr><td><a id="refreshText" href="#" class="easyui-linkbutton">刷新题目</a></td></tr>
				                            <tr><td/></tr>
						                    <tr><td/></tr>
						                    <tr><td/></tr>
						                    <tr><td/></tr>
				                        <tr><td><a id="submit" href="#" class="easyui-linkbutton">提交</a></td></tr>
				                            <tr><td/></tr>
						                    <tr><td/></tr>
						                    <tr><td/></tr>
						                    <tr><td/></tr>
				                        <tr><td><a id="exit" href="#" class="easyui-linkbutton">退出</a></td></tr>
				                    </table>
				                </div>
				            </div>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
        
        

  </body>
</html>
