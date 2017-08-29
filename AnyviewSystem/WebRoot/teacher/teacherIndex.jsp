<jsp:include page="../common/default.jsp"></jsp:include>
<%@ page language="java" import="java.util.*, com.anyview.entities.TeacherTable" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>教师主页</title>
	<script type="text/javascript">	
		$(function() {
			DWZ.init("dwz.frag.xml", {
				loginUrl : "login.jsp",
				loginTitle : "登录",
				statusCode : { ok : 200,error : 300,timeout : 301},
				pageInfo : { pageNum : "pageNum",numPerPage : "numPerPage",orderField : "orderField",orderDirection : "orderDirection"},
				debug : false,
				callback : function() {
					initEnv();
					$("#themeList").theme( { themeBase : "common/themes/type" });
				}
			});
		});
	</script>
	</head>
	<body scroll="no">
		<div id="layout">
		
			<div id="header">
				<div class="headerNav">
					<ul class="nav">
						<li><a href="javascript:void(0)">
						<%
							if(session.getAttribute("User") instanceof TeacherTable){
							TeacherTable teacher = (TeacherTable)session.getAttribute("User");
							if(teacher != null){
						 %>
							<%= ((TeacherTable)session.getAttribute("User")).getTname()%>教师</a>
						<% } else 
							response.sendRedirect("/AnyviewSystem/login.jsp");}
							else 
							response.sendRedirect("/AnyviewSystem/login.jsp");
						%>
						</li>
						<li>Anyview</li>
						<li id="switchEnvBox"><a href="javascript:void(0);;"><span id="currentSpan"><s:property value="#session.classCourse.course.cname"/></span></a>
							<ul>
							</ul>
						</li>
						<li><a href="userLoginOut.action" >退出</a></li>
					</ul>
					<ul class="themeList" id="themeList">
						<li theme="default"><div class="selected">蓝色</div></li>
						<li theme="green"><div>绿色</div></li>
						<li theme="purple"><div>紫色</div></li>
						<li theme="silver"><div>银色</div></li>
						<li theme="azure"><div>天蓝</div></li>
					</ul>
				</div>
			</div>
			
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse"><div></div></div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>主菜单</h2>
						<div>收缩</div>
					</div>
					<div class="accordion" fillSpace="sidebar">
						<div class="accordionHeader">
							<h2><span>Folder</span>界面组件</h2>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<li><a href="javascript:void(0);">工作菜单</a>
									<ul>
<!--     									<li><a href="teacher/classManager/gainClassManagerPage.action" target="navTab" title="我的班级" fresh="true" rel="classManager">我的班级</a></li> -->
                                        <li><a href="teacher/classManager/gainClassCoursePage.action" target="navTab" title="我的班级" fresh="true" rel="classManager">我的班级</a></li>
<!--   										<li><a href="teacher/homeworkCorrecting/index.action" target="navTab" title="批改作业" fresh="true" rel="" external="true">批改作业</a></li> -->
										<li><a href="teacher/combinedGrade/gainCombinedGradeManagerPage.action" target="navTab" title="综合评分" fresh="true" rel="combinedGradeIndex" >综合评分</a></li>
										<li><a href="teacher/schemeManager/gainSchemeManagerPage.action" target="navTab" title="我的作业表" fresh="true" rel="schemeManager">我的作业表</a></li>
										<li><a href="teacher/problemLibManager/gainProblemLibManagerPage.action" target="navTab" title="我的题库" fresh="true" rel="problemLibManager">我的题库</a></li>
										<li><a href="teacher/examPlanManager/gainExamPlanManagerPage.action" target="navTab" title="考试编排" fresh="true" rel="examPlanManager" >考试编排</a></li>
										<li><a href="teacher/similarityDetection/gainSimilarityDetectionPage.action" target="navTab" title="代码检测" fresh="true" rel="similarityDetection" >代码检测</a></li>
										<li><a href="teacher/updateTeacherInfo/getTeacherInfo.action" target="navTab" rel="updateTeacherInfo" >个人设置</a></li>
									</ul>
								</li>
							</ul>
						</div>
						<div class="accordionFooter">
						</div>
					</div>
				</div>
			</div>
			
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<ul class="navTab-tab">
								<li tabid="main" class="main"><a href="javascript:void(0);"><span><span class="home_icon">我的主页</span></span></a></li>
							</ul>
						</div>
						
						<div class="tabsLeft">left</div>
						<div class="tabsRight">right</div>
						<div class="tabsMore">more</div>
					</div>
					<ul class="tabsMoreList">
						<li><a href="javascript:;">我的主页</a></li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<div class="page unitBox">
							<iframe frameborder="no" marginheight="0" marginwidth="0" border="0" style="width:100%;height:502px;" src=""></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="footer">
				Copyright &copy; 2010-2013<a href="http://www.cnblogs.com/gdutds/archive/2012/04/26/2471946.html" target="dialog">&nbsp;&nbsp;&nbsp;数据结构与可视计算团队</a>
		</div>
	</body>
</html>
