<jsp:include page="../common/default.jsp"></jsp:include>
<%@ page language="java" import="java.util.*, com.anyview.entities.ManagerTable" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>管理员主页</title>
	<script type="text/javascript">	
		$(function() {
			DWZ.init("dwz.frag.xml", {
				loginUrl : "login.jsp",
				loginTitle : "登录",
				statusCode : { ok : 200,error : 300,timeout : 301},
				pageInfo : { pageNum : "pageNum",numPerPage : "numPerPage",orderField : "orderField",orderDirection : "orderDirection"},
				debug : true,
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
							if(session.getAttribute("User") instanceof ManagerTable){
								ManagerTable admin = (ManagerTable)session.getAttribute("User");
							if(admin != null){
						 %>
							<%= ((ManagerTable)session.getAttribute("User")).getMno()%>管理员</a>
						<% } else 
							response.sendRedirect("/AnyviewSystem/welcome.jsp");}
							else 
							response.sendRedirect("/AnyviewSystem/welcome.jsp");
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
										<!-- 超级管理员拥有模块 -->
										<c:if test="${User.miden==-1}">
											<li><a href="admin/universityManager/getUniversityManagerPage.action" target="navTab" fresh="true" rel="universityManager">学校管理</a></li>
										</c:if>
										<!-- 超级管理员，学校管理员拥有模块 -->
										<c:if test="${User.miden==-1 || User.miden==1 }">
											<li><a href="admin/adminManager/getAdminManagerPage.action" target="navTab" fresh="true" rel="adminManager" >管理员管理</a></li>
											<li><a href="admin/collegeManager/getCollegeManagerPage.action" target="navTab" fresh="true" rel="collegeManager">学院管理</a></li>
										</c:if>
										<li><a href="admin/adminclassManager/classManager.action" target="navTab" fresh="true" rel="adminclassManager" >班级管理</a></li>
										<li><a href="admin/teacherManager/searchTeacher.action" target="navTab" fresh="true" rel="teacherManager"  >教师管理</a></li>
<!-- 										<li><a href="admin/classTeacherManager/getClassTeacherManagerPage.action" target="navTab" fresh="true" rel="classTeacherManager" >教师-班级权限管理</a></li> -->
										<li><a href="admin/courseManager/getCoursePage.action" target="navTab" rel="courseManager" >课程管理</a></li>
										<li><a href="admin/adminstudentManager/getAdminStudentManagerPage.action" target="navTab" fresh="true" rel="adminstudentManager" >学生管理</a></li>
										<li><a href="admin/passwordManager/passwordManager.action" target="navTab" fresh="true" rel="passwordManager">修改密码</a></li>
<!-- 										<li><a href="admin/classCourseManager/classCourseManager.action" target="navTab" rel="" >班级课程管理</a></li> -->
<!-- 										<li><a href="admin/semesterManager/getSemesterManagerPage.action" target="navTab" fresh="true" rel="semesterManager" >学期管理</a></li> -->
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
