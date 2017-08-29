<jsp:include page="../common/default.jsp"></jsp:include>
<%@ page language="java" import="java.util.*, com.anyview.entities.StudentTable" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>学生主页</title>
	<script type="text/javascript">	
		$(function() {
			DWZ.init("../dwz.frag.xml", {
				loginUrl : "login.jsp",
				loginTitle : "登录",
				statusCode : { ok : 200,error : 300,timeout : 301},
				pageInfo : { pageNum : "pageNum",numPerPage : "numPerPage",orderField : "orderField",orderDirection : "orderDirection"},
				debug : false,
				callback : function() {
					initEnv();
					$("#themeList").theme( { themeBase : "themes/type" });
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
						<li>
						<c:choose>
							<c:when test="${empty visitor}">游客</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						</li>
						</li>
						<li>Anyview</li>
						<li id="switchEnvBox"><a href="javascript:void(0);;"><span id="currentSpan"><s:property value="#session.classCourse.course.cname"/></span></a>
							<ul>
							</ul>
						</li>
						<li><a href="visitorLogin.jsp" >请登录</a></li>
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
  										<li><a href="student/downloadStuSys.jsp" target="navTab" rel="downloadStuSys" >下载学生端</a></li>
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
