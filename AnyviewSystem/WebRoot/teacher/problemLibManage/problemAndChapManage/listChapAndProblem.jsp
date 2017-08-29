<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
</script>

<!-- 为了自动刷新添加的form -->
<form id="pagerForm"
	action="teacher/problemManager/listChapAndProblem.action" method="post">
	<input type="hidden" name="problemChap.problemLib.lid" value="${lid }" />
	<input type="hidden" name="problemChap.chId" value="${chId }" /> 
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${chId != -1 }">
				<li><a class="icon" rel="problemAndChapManager" title="题目管理"
					target="navTab"
					href="teacher/problemManager/listChapAndProblem.action?problemChap.problemLib.lid=${lid }&problemChap.chId=${parentId}">
						<span>返回</span>
				</a></li>
			</c:if>
			<c:if test="${isOwn }">
				<li><a class="add" fresh="false" rel="addProblemChap"
					title="添加目录" target="dialog"
					href="teacher/problemLibManage/problemAndChapManage/addProblemChap.jsp?chId=${chId }&lid=${lid}">
						<span>添加目录</span>
				</a></li>
				<c:if test="${chId != -1  }">
					<li><a class="add" fresh="false" rel="addProblem" title="添加题目" target="dialog" width="800" height="500" href="teacher/problemLibManage/problemAndChapManage/addProblem.jsp?chId=${problem.problemChap.chId }&lid=${problem.problemChap.problemLib.lid}"> 
						<span>添加题目</span>
					</a>
					</li>
					<li><a class="add" fresh="false" rel="importProblem"
						title="导入题目" target="dialog"
						href="teacher/problemLibManage/problemAndChapManage/importProblem.jsp?chId=${chId }&lid=${lid}">
							<span>导入题目</span>
					</a></li>
				</c:if>
			</c:if>
			<li><a class="add" fresh="false" rel="downloadTemplate"
				title="下载导入模板" target="dialog"
				href="teacher/problemLibManage/problemAndChapManage/downloadTemplate.jsp">
					<span>下载导入模板</span>
			</a></li>
			<li><a class="delete" target="ajaxTodo"
				title="确定删除吗？" warn="请选择一条记录"
				href="teacher/problemManager/deleteProblemChap.action?update_info={update_info}&problemChap.problemLib.lid=${problemChap.problemLib.lid}&problemChap.parentChap.chId=${problemChap.parentChap.chId}">
					<span>删除</span>
			</a></li>
			<li><a class="edit" warn="请选择一条记录" fresh="false"
				id="eidtProblemOrChap" rel="eidtProblemOrChap" target="dialog"
				title="修改"
				href="teacher/problemManager/editProblemChap.action?update_info={update_info}">
					<span>修改</span>
			</a></li>
		</ul>
	</div>

	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th width="7%" align="center">选中</th>
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
			<c:forEach items="${chaps }" var="chap">
				<tr target="update_info" rel="${chap.chId }+chid">
					<td align="center"><input type="checkbox" name="file"
						value="${chap.chId }" /></td>
					<td align="center"><span class="icon-dir" title="目录"></span></td>
					<td align="center"><a class="icon" rel="problemAndChapManager"
						title="题目管理" target="navTab"
						href="teacher/problemManager/listChapAndProblem.action?problemChap.problemLib.lid=${lid }&problemChap.chId=${chap.chId }">
							<span>${chap.chName }</span>
					</a></td>
					<td align="center"><c:if test="${chap.visit==0 }">私有</c:if> <c:if
							test="${chap.visit==1 }">公开</c:if></td>
					<td align="center">${chap.memo }</td>
					<td align="center">--</td>
					<td align="center">--</td>
					<td align="center">--</td>
				</tr>
			</c:forEach>
			<c:forEach items="${pros }" var="pro">
				<tr target="update_info" rel="${pro.pid }+pid">
					<td align="center"><input type="checkbox" name="file"
						value="${pro.pid }" /></td>
					<td align="center"><span class="icon-file" title="题目"></span></td>
					<td align="center">${pro.pname }</td>
					<td align="center"><c:if test="${pro.visit==0 }">私有</c:if> <c:if
							test="${pro.visit==1 }">公开</c:if></td>
					<td align="center">--</td>
					<td align="center"><c:if test="${pro.status==0 }">停用</c:if> <c:if
							test="${pro.status==1 }">测试</c:if> <c:if test="${pro.status==2 }">公开</c:if>
					</td>
					<td align="center"><c:if test="${pro.kind==0 }">程序题</c:if> <c:if
							test="${pro.kind==1 }">例题</c:if> <c:if test="${pro.kind==2 }">填空题</c:if>
						<c:if test="${pro.kind==3 }">单选题</c:if> <c:if
							test="${pro.kind==4 }">多选题</c:if> <c:if test="${pro.kind==5 }">判断题</c:if>
					</td>
					<td align="center">${pro.degree }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>