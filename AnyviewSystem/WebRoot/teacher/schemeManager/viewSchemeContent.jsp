<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript">
$(function(){
	
})
</script>
			
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<a class="icon" title="管理题目" rel="lookSchemeProblems" target="navTab" href="teacher/schemeManager/lookSchemeProblems.action?vid=${vid }"> 
					<span>刷新</span> 
				</a>
			</li>
			<li>
				<a class="add" fresh="true" rel="addSchemeProblems" target="navTab" href="teacher/schemeManager/addProblemForScheme.action?vid=${vid }"> 
					<span>添加题目</span>
				</a>
			</li>
			<li>
				<a class="edit" fresh="false" warn="请选择一个题目" rel="editSchemeContentMsg" target="dialog" href="teacher/schemeManager/editSchemeContent.action?id={tar_id}"> 
					<span>设置题目</span>
				</a>
			</li>
			<li>
				<a class="delete" target="ajaxTodo" href="teacher/schemeManager/deleteSchemeProblem.action?schemeContent.scheme.vid=${vid}&schemeContent.id={tar_id}" title="确定删除吗?" warn="请选择一个题目"> 
					<span>删除题目</span>
				</a>
			</li>
		</ul>
	</div>
	
	<div class="pageContent sortDrag" layoutH="42">
		<c:forEach items="${list }" var="pc">
			<div class="sortDiv">
				<label>${pc.vchapName }</label>
				<label>${pc.vpName }</label>.
				<span>${pc.problem.pcontent }</span>
				<br/>
			</div>
		</c:forEach>
	</div>
	
</div>