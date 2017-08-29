<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
$(function(){
	setFormsName("answerList_singleChoice",["input[type='hidden']","textarea"], "problemContent.singleOptions", ["sequence","optContent"]);
	setFormsName("answerList_multipleChoice",["input[type='hidden']","textarea"], "problemContent.multipleOptions", ["sequence","optContent"]);
	
	$("input[name='problem.kind']",$.pdialog.getCurrent()).bind("change",function(){
		$("div[group='problemKindDiv']",$.pdialog.getCurrent()).hide();
		$("#addProblemKindDiv_"+this.value,$.pdialog.getCurrent()).show();
	});
	
	//单选题添加选项按钮
	$("#addAnswerBtn_singleChoice",$.pdialog.getCurrent()).bind("click", function(){
		var answer = $('<li><input type="radio" name="singleChoiceRadio"/><input type="hidden" name=""/><span class="inline-block" style="width:20px;"></span><textarea style="height: 40px;width: 480px;"></textarea><input type="button" value="删除" onclick="deleteAnswerOpt(this);"/></li>');
		var ul = $("#answerList_singleChoice",$.pdialog.getCurrent());
		ul.prepend(answer);
		var index = parseInt(ul.attr("index"));
		ul.attr("index", index+1);
		sequenceNum("answerList_singleChoice", "radio");
		setFormsName("answerList_singleChoice",["input[type='hidden']","textarea"], "problemContent.singleOptions", ["sequence","optContent"]);
		//滚动条置底
		var h = document.getElementById("problemTabContentDiv").offsetHeight;
		$("#problemTabContentDiv").scrollTop(h);
	});
	//多选题添加选项按钮
	$("#addAnswerBtn_multipleChoice",$.pdialog.getCurrent()).bind("click", function(){
		var answer = $('<li><input type="checkbox" name="rightMultipleOpt"/><input type="hidden" name=""/><span class="inline-block" style="width:20px;"></span><textarea style="height: 40px;width: 480px;"></textarea><input type="button" value="删除" onclick="deleteAnswerOpt(this);"/></li>');
		var ul = $("#answerList_multipleChoice",$.pdialog.getCurrent());
		ul.prepend(answer);
		var index = parseInt(ul.attr("index"));
		ul.attr("index", index+1);
		sequenceNum("answerList_multipleChoice","checkbox");
		setFormsName("answerList_multipleChoice",["input[type='hidden']","textarea"], "problemContent.multipleOptions", ["sequence","optContent"]);
		var h = document.getElementById("problemTabContentDiv").offsetHeight;
		$("#problemTabContentDiv").scrollTop(h);
	});
	//填空题设置空缺
	$("#setBlank",$.pdialog.getCurrent()).click(function(){
		var area = $("#blankFillingArea");
        if(window.getSelection){
        	//非IE内核
        	var range = window.getSelection().getRangeAt(0);
        	var span = document.createElement('span');
//         	span.style.width="50px";
        	span.style.color="red";
        	span.setAttribute("blank","blank");
        	range.surroundContents(span);
        	range.collapse(false);
        }else if(document.selection){
        	//IE内核
        	
        }
	});
	//程序题添加头文件按钮
	$("#addHeadFileBtn",$.pdialog.getCurrent()).click(function(){
		var ul = $("#headFileList_program",$.pdialog.getCurrent());
		var index = parseInt(ul.attr("index"));
		var name = '<label>头文件名:</label><input type="text" class="fileNameInput"/>';
		var content = '<label>头文件内容</label><textarea class="fileContentArea1"></textarea>';
		var btn = '<input type="button" value="删除" onclick="deleteHeadFile(this)"/>';
		var headFile = $('<li><div class="unit">'+name+'</div><div class="unit">'+content+'</div><div class="unit"><label>删除</label>'+btn+'</div></li>');
		ul.prepend(headFile);
		ul.attr("index", index+1);
		setFormsName("headFileList_program",["input","textarea"], "problemContent.headFiles", ["fileName","fileContent"]);
	});
})
</script>


<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="teacher/problemManager/updateProblem.action" method="post" novalidate="novalidate">
	<input type="hidden" name="problem.pid" value="${problem.pid }"/>
	
	<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>基本信息</span></a></li>
					<li><a href="javascript:;"><span>提示</span></a></li>
					<li><a href="javascript:;"><span>内容</span></a></li>
				</ul>
			</div>
		</div>
		<div id="problemTabContentDiv" class="tabsContent" style="height: 360px;">
			<!-- 基本信息 -->
			<div>
				<div class="unit">
					<label>题目名称：</label>
					<input class="required textInput" type="text"  name="problem.pname" value="${problem.pname }"/>
				</div>
				<div class="unit">
					<label>难度：</label>
					<input class="required textInput number" type="text"  name="problem.degree" value="${problem.degree }"/>
				</div>
				<div class="unit">
					<label>状态:</label>
					<input type="radio" name="problem.status" value="0" <c:if test="${problem.status==0 }">checked="checked"</c:if> />停用
					<input type="radio" name="problem.status" value="1" <c:if test="${problem.status==1 }">checked="checked"</c:if> />测试
					<input type="radio" name="problem.status" value="2" <c:if test="${problem.status==2 }">checked="checked"</c:if>/>正式
				</div>
				<div class="unit">
					<label>访问级别:</label>
					<input type="radio" name="problem.visit" value="0" <c:if test="${problem.visit==0 }">checked="checked"</c:if> />私有
					<input type="radio" name="problem.visit" value="1" <c:if test="${problem.visit==1 }">checked="checked"</c:if>/>公开
				</div>
				<div class="unit">
					<label>备注:</label>
					<textarea class="required textInput" cols="50" rows="5" name="problem.pmemo">${problem.pmemo }</textarea>
				</div>
			</div>
			<!-- 提示 -->
			<div>
				<div class="unit">
					<label>提示:</label>
					<textarea class="fileContentArea2 required textInput" name="problem.ptip">${problem.ptip }</textarea>
				</div>
			</div>
			<!-- 内容 -->
			<div>
				<div class="unit">
					<label>题目类型:</label>
					<input type="radio" name="problem.kind" value="0" <c:if test="${problem.kind==0 }">checked="checked"</c:if> />程序题
					<input type="radio" name="problem.kind" value="1" <c:if test="${problem.kind==1 }">checked="checked"</c:if> />例题
					<input type="radio" name="problem.kind" value="2" <c:if test="${problem.kind==2 }">checked="checked"</c:if> />填空题
					<input type="radio" name="problem.kind" value="3" <c:if test="${problem.kind==3 }">checked="checked"</c:if> />单项选择题
					<input type="radio" name="problem.kind" value="4" <c:if test="${problem.kind==4 }">checked="checked"</c:if> />多项选择题
					<input type="radio" name="problem.kind" value="5" <c:if test="${problem.kind==5 }">checked="checked"</c:if> />判断题
				</div>
				<!-- 程序题 -->
				<div id="addProblemKindDiv_0" group="problemKindDiv" <c:if test="${problem.kind!=0 }">style="display: none;"</c:if> >
					<div class="unit">
						<label>题目信息:</label>
						<div class="tabs" style="width: 600px;display: inline-block;">
							<div class="tabsHeader">
								<div class="tabsHeaderContent">
									<ul>
										<li><a href="javascript:;"><span>主文件</span></a></li>
										<li><a href="javascript:;"><span>用户文件</span></a></li>
										<li><a href="javascript:;"><span>头文件</span></a></li>
										<li><a href="javascript:;"><span>答案文件</span></a></li>
										<li><a href="javascript:;"><span>题目内容</span></a></li>
										<li><a href="javascript:;"><span>题目文档</span></a></li>
									</ul>
								</div>
							</div>
							<div class="tabsContent" style="height: 280px;">
								<!-- 主文件 -->
								<div>
									<div class="unit">
										<label>主文件名:</label>
										<input type="text" name="problemContent.mainFile.fileName" class="fileNameInput" value="${problemContent.mainFile.fileName }"/>
									</div>
									<div class="unit">
										<label>主文件内容:</label>
										<textarea name="problemContent.mainFile.fileContent" class="fileContentArea2">${problemContent.mainFile.fileContent }</textarea>
									</div>
								</div>
								<!-- 用户文件 -->
								<div>
									<div class="unit">
										<label>用户文件名:</label>
										<input name="problemContent.userFile.fileName" type="text" class="fileNameInput" value="${problemContent.userFile.fileName }"/>
									</div>
									<div class="unit">
										<label>用户文件内容:</label>
										<textarea name="problemContent.userFile.fileContent" class="fileContentArea2">${problemContent.userFile.fileContent }</textarea>
									</div>
								</div>
								<!-- 头文件 -->
								<div>
									<ul id="headFileList_program" index="0">
										<c:forEach items="${problemContent.headFiles }" var="h">
											<li>
												<div class="unit">
													<label>头文件名:</label>
													<input type="text" class="fileNameInput" value="${h.fileName }"/>
												</div>
												<div class="unit">
													<label>头文件内容</label>
													<textarea class="fileContentArea1">${h.fileContent }</textarea>
												</div>
												<div class="unit">
													<label>删除</label>
													<input type="button" value="删除" onclick="deleteHeadFile(this)"/>
												</div>
											</li>
										</c:forEach>
									</ul>
									<div class="unit">
										<label>增加头文件</label>
										<input id="addHeadFileBtn" type="button" value="增加头文件"/>
									</div>
								</div>
								<!-- 答案文件 -->
								<div>
									<div class="unit">
										<label>答案文件名:</label>
										<input name="problemContent.answerFile.fileName" type="text" class="fileNameInput" value="${problemContent.answerFile.fileName }"/>
									</div>
									<div class="unit">
										<label>答案文件内容:</label>
										<textarea name="problemContent.answerFile.fileContent" class="fileContentArea2">${problemContent.answerFile.fileContent }</textarea>
									</div>
								</div>
								<!-- 题目内容 -->
								<div>
									<div class="unit">
										<label>题目内容:</label>
										<textarea name="problemContent.programContent" class="fileContentArea2">${problemContent.programContent }</textarea>
									</div>
								</div>
								<!-- 题目文档 -->
								<div>
									<div class="unit">
										<label>题目文档:</label>
										<textarea name="problemContent.document" class="fileContentArea2">${problemContent.document }</textarea>
									</div>
								</div>
							</div>
							<div class="tabsFooter">
								<div class="tabsFooterContent"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 例题 -->
				<div id="addProblemKindDiv_1" group="problemKindDiv" <c:if test="${problem.kind!=1 }">style="display: none;"</c:if>>
					<div class="unit">
						<label>题干:</label>
						<textarea class="fileContentArea2"></textarea>
					</div>
				</div>
				<!-- 填空题 -->
				<div id="addProblemKindDiv_2" group="problemKindDiv" <c:if test="${problem.kind!=2 }">style="display: none;"</c:if>>
					<div class="unit">
						<label>题干:</label>
						<div contenteditable="true" id="blankFillingArea" class="editDiv"></div>
					</div>
					<div class="unit">
						<label>选项:</label>
						<input id="setBlank" type="button" value="设置填空"/>
					</div>
				</div>
				<!--单选题 -->
				<div id="addProblemKindDiv_3" group="problemKindDiv" <c:if test="${problem.kind!=3 }">style="display: none;"</c:if>>
					<div class="unit">
						<label>题干:</label>
						<textarea name="problemContent.singleContent" style="height: 80px;width: 500px;display: block;">${problemContent.singleContent }</textarea>
					</div>
					<div class="unit">
						<label>选项:</label>
						<ul id="answerList_singleChoice" class="choiceOptUl" index="${fn:length(problemContent.singleOptions) }">
							<c:forEach items="${problemContent.singleOptions }" var="s">
								<li>
									<input type="radio" name="singleChoiceRadio" value="${s.sequence }" <c:if test="${s.isRight }">checked="checked"</c:if> />
									<input type="hidden" name=""/>
									<span class="inline-block" style="width:20px;">${s.sequence }</span>
									<textarea style="height: 40px;width: 480px;float: none;">${s.optContent }</textarea>
									<input type="button" value="删除" onclick="deleteAnswerOpt(this);"/>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="unit">
						<label>增加答案选项</label>
						<input id="addAnswerBtn_singleChoice" type="button" value="增加答案选项"/><span style="color:red;">单选框内选择正确答案</span>
					</div>
				</div>
				<!-- 多选题 -->
				<div id="addProblemKindDiv_4" group="problemKindDiv" <c:if test="${problem.kind!=4 }">style="display: none;"</c:if>>
					<div class="unit">
						<label>题干:</label>
						<textarea name="problemContent.multipleContent" style="height: 80px;width: 500px;display: block;"></textarea>
					</div>
					<div class="unit">
						<label>选项:</label>
						<ul id="answerList_multipleChoice" class="inline-block" index="0">
							<c:forEach items="${s.multipleOptions }" var="s">
								<li>
									<input type="checkbox" name="rightMultipleOpt" value="${s.sequence }" <c:if test="${s.isRight }">checked="checked"</c:if> />
									<input type="hidden" name=""/>
									<span class="inline-block" style="width:20px;">${s.sequence }</span>
									<textarea style="height: 40px;width: 480px;">${s.optContent }</textarea>
									<input type="button" value="删除" onclick="deleteAnswerOpt(this);"/>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="unit">
						<label>增加答案选项</label>
						<input id="addAnswerBtn_multipleChoice" type="button" value="增加答案选项"/><span style="color:red;">复选框内选择正确答案</span>
					</div>
				</div>
				<!-- 判断题 -->
				<div id="addProblemKindDiv_5" group="problemKindDiv" <c:if test="${problem.kind!=5 }">style="display: none;"</c:if>>
					<div class="unit">
						<label>题干:</label>
						<textarea name="problemContent.judgmentContent" style="height: 80px;width: 500px;display: block;">${problemContent.judgmentContent }</textarea>
					</div>
					<div class="unit">
						<label>选项:</label>
						<input type="radio" name="problemContent.isRight" value="true" <c:if test="${problemContent.isRight }">checked="checked"</c:if> />正确
						<input type="radio" name="problemContent.isRight" value="false" <c:if test="${!problemContent.isRight }">checked="checked"</c:if> />错误
					</div>
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">提交</button>
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