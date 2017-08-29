<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="pageContent">
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="7%" align="center">操作</th>
				<th width="7%" align="center">类型</th>
				<th width="10%" align="center">名称</th>
				<th width="10%" align="center">访问级别</th>
				<th width="20%" align="center">说明(目录)</th>
				<th width="7%" align="center">状态(题)</th>
				<th width="7%" align="center">类型(题)</th>
				<th width="7%" align="center">难度(题)</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${chId!=-1 }">
				<tr>
					<td align="center">
						<a rel="nsLibContentDiv" style="color: blue;" target="ajax" href="teacher/problemManager/libContents.action?problemChap.problemLib.lid=${lid }&problemChap.chId=${parentId}">上一级</a>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:if>
			<c:forEach items="${chaps }" var="chap">
				<tr>
					<td align="center">
						<a rel="nsLibContentDiv" style="color: blue;" target="ajax" href="teacher/problemManager/libContents.action?problemChap.problemLib.lid=${lid }&problemChap.chId=${chap.chId}">下一级</a>
					</td>
					<td align="center"><span class="icon-dir" title="目录"></span></td>
					<td align="center">${chap.chName }</td>
					<td align="center">
						<c:if test="${chap.visit==0 }">私有</c:if>
						<c:if test="${chap.visit==1 }">公开</c:if>
					</td>
					<td align="center">${chap.memo }</td>
					<td align="center">--</td>
					<td align="center">--</td>
					<td align="center">--</td>
				</tr>
			</c:forEach>
			<c:forEach items="${pros }" var="pro">
				<tr>
					<td align="center">
						<a style="color: blue" href="#" onclick="chooseProblemsFromLib(${pro.pid},'${pro.pname }',${pro.kind });">选择</a>
					</td>
					<td align="center"><span class="icon-file" title="题目"></span></td>
					<td align="center">${pro.pname }</td>
					<td align="center">
						<c:if test="${pro.visit==0 }">私有</c:if>
						<c:if test="${pro.visit==1 }">公开</c:if>
					</td>
					<td align="center">--</td>
					<td align="center">
						<c:if test="${pro.status==0 }">停用</c:if>
						<c:if test="${pro.status==1 }">测试</c:if>
						<c:if test="${pro.status==2 }">公开</c:if>
					</td>
					<td align="center">
						<c:if test="${pro.kind==0 }">程序题</c:if>
						<c:if test="${pro.kind==1 }">例题</c:if>
						<c:if test="${pro.kind==2 }">填空题</c:if>
						<c:if test="${pro.kind==3 }">单选题</c:if>
						<c:if test="${pro.kind==4 }">多选题</c:if>
						<c:if test="${pro.kind==5 }">判断题</c:if>
					</td>
					<td align="center">
						${pro.degree }
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
//根据Id后台格式化题目内容
function chooseProblemsFromLib(pid, pname,kind){
	$.ajax({
		type:"POST",
		url:"teacher/problemManager/getProblemContentJson.action",
		data:{'problem.pid':pid},
		cache:"false",
		success:function(data){
			var obj = eval("("+data+")");
			var div = createProblemDiv(obj,pid, pname, kind);
			var $div = $(div);
			var $start = $div.find("span[name='startTime']");
			var $finish = $div.find("span[name='finishTime']");
			var dateopts = {pattern:'yyyy-MM-dd HH:mm'};
			$start.find("input.date").datepicker(dateopts);
			$finish.find("input.date").datepicker(dateopts);
			$("#nsSelectedPro").append($div);
			sortProblem();
			var dragop = {
					cursor: 'move', // selector 的鼠标手势
					sortBoxs: 'div.sortDrag', //拖动排序项父容器
					replace: false, //2个sortBox之间拖动替换
					items: '> div', //拖动排序项选择器
					selector: '', //拖动排序项用于拖动的子元素的选择器，为空时等于item
					zIndex: 1000,
					userFunction:sortProblem
				};
			//双击拖动
			$div.dblclick(function(event){
				DWZ.sortDrag.start($("#nsSelectedPro"), $div, event, dragop);
				event.preventDefault();
			});
		}
	});
}
function createProblemDiv(obj,pid, pname, kind){
	var div = ' <div class="sortDiv problemContentDiv" style="padding:15px;overflow: hidden;" pid='+pid+' kind='+kind+'>';
	div += '<h1> <span name="vchapName"></span>';
	div += '<span name="vpName"><input type="text" placeholder="请输入题目名称" class="required textInput" group="vpname" value=""/></span>';
	switch(kind){
		case(0):break;
		case(1):break;
		case(2):break;
		case(3):{
			div += '（单选题）';
			div += '<span name="score"><input type="text" placeholder="分值" onblur="checkScore(this.value);" class="required textInput" group="score" style="width:80px" value=""/></span>分';
			div += '<span name="startTime" style="margin-left:300"><input type="text" readonly="readonly" class="date" group="startTime" style="width:150px;" value="" name="startTime" dateFmt="yyyy-MM-dd HH:mm"></span>';
			div += ' <span class="limit">-</span> ';
			div += '<span name="finishTime"><input type="text" readonly="readonly" class="date" group="finishTime" style="width:150px;" value="" name="finishTime" dateFmt="yyyy-MM-dd HH:mm"></span>';
			div += '</h1></br>';
			div += '<pre>'+obj.choiceContent+'</pre>';
			var opts = obj.options;
			for(i=0;i<opts.length;i++){
				var op = opts[i];
				if(op.isRight){
					div += '<font size="3" color="red"><pre><b>'+op.sequence+'</b>'+op.optContent+'</pre></font>';
				}else{
					div += '<font"><pre><b>'+op.sequence+'</b>'+op.optContent+'</pre></font>';
				}
			}
			break;
		}
		case(4):{
			break;
		};
		case(5):{
			
			break;
		};
	}
	div += '<div class="button" style="float: right;"><div class="buttonContent"><button onclick="deleteThisProblem(this);">删除</button></div></div></div>';
	return div;
}
</script>