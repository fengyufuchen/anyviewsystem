<%-- 
    Document   : flash
    Created on : 2011-3-23, 19:31:02
    Author     : CASH
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" import="java.util.*" %>

<html>
	<head>
		<meta charset="utf-8" />
		<title>Flash</title>
		<style type="text/css" rel="stylesheet">
			body {
				margin: 0;
				font:12px/1.5 "sans serif",tahoma,verdana,helvetica;
				background-color:#F0F0EE;
				color:#222222;
				overflow:hidden;
			}
			label {
				cursor:pointer;
			}
			.main {
				margin: 0 10px;
			}
			.table {
				list-style-image:none;
				list-style-position:outside;
				list-style-type:none;
				margin:0;
				padding:0;
				display:block;
			}
			.table li {
				padding:0;
				margin-bottom:10px;
				display:list-item;
				line-height:1.5;
			}
			.table li label {
				font-weight:bold;
			}
			.table li input {
				vertical-align:middle;
			}
			.table li img {
				vertical-align:middle;
			}
		</style>
		<script type="text/javascript">
			var KE = parent.KindEditor;
			location.href.match(/\?id=([\w-]+)/i);
			var id = RegExp.$1;
			KE.event.ready(function() {
				KE.util.pluginLang('flash', document);
				KE.util.hideLoadingPage(id);
			}, window, document);
		</script>
	</head>
        <%
            Hashtable myValues = (Hashtable)session.getAttribute("T130");
            String flashName="";
            if(myValues!=null){
                flashName=(String)myValues.get("flashName");
                flashName="/StudySystem/studyPrepare/flash/"+flashName;
             }
        %>
	<body>
		<div class="main">
			<ul class="table">
                            <li>
                                <form id="form1" name="form1" method="post" action="../servlet/UploadServlet"
                                      ENCTYPE="multipart/form-data">
                                    <input name="upload" type="file" value="浏览"  />
                                    <input type="submit" value="上传"/>
                                    <input type="hidden" name="pageId" id="pageId" value="T130"/>
                                    <input type="hidden" name="actionId" id="actionId" value="uploadFlash"/>
                                    </form>
                            </li>
				<li>
					<label for="url"><span id="lang.url"></span></label>
					<input type="text" id="url" name="url" value="<%=flashName%>" maxlength="255" style="width:90%;" />
				</li>
				<li>
					<label for="width"><span id="lang.width"></span></label>&nbsp;
					<input type="text" id="width" name="width" value="550" maxlength="4" style="width:50px;text-align:right;" /> px
				</li>
				<li>
					<label for="height"><span id="lang.height"></span></label>&nbsp;
					<input type="text" id="height" name="height" value="400" maxlength="4" style="width:50px;text-align:right;" /> px
				</li>

			</ul>
		</div>
	</body>
</html>
