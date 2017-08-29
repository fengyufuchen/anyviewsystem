<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!-- actionmessage  -->
 <s:if test="hasActionMessages()"> 
<s:iterator value="actionMessages"> 
<script language="JavaScript"> 
alert("<s:property escape="false"/>") 
</script> 
</s:iterator> 
</s:if> 

<!-- actionerror  -->
<s:if test="hasActionErrors()"> 
<s:iterator value="actionErrors"> 
<script language="JavaScript"> 
alert("<s:property escape="false"/>")
</script> 
</s:iterator> 
</s:if>

<html> 
<head> 
<title>登录页面</title> 
<style type="text/css"> 
body { 
    margin:0; 
    padding:0; 
    background-color:#E4E8EC; 
} 
.wrap { 
    margin:150px auto; 
    width:380px; 
    overflow:hidden; 
} 
.loginForm { 
    box-shadow: 0 0 2px rgba(0, 0, 0, 0.2), 0 1px 1px rgba(0, 0, 0, 0.2), 0 3px 0 #fff, 0 4px 0 rgba(0, 0, 0, 0.2), 0 6px 0 #fff, 0 7px 0 rgba(0, 0, 0, 0.2); 
    position:absolute; 
    z-index:0; 
    background-color:#FFF; 
    border-radius:4px; 
    height:270px; 
    width:380px; 
    background: -webkit-gradient(linear, left top, left 24, from(#EEE), color-stop(4%, #FFF), to(#EEE)); 
    background: -moz-linear-gradient(top, #EEE, #FFF 1px, #EEE 24px); 
    background: -o-linear-gradient(top, #EEEEEE, #FFFFFF 1px, #EEEEEE 24px); 
} 
.loginForm:before { 
    content:''; 
    position:absolute; 
    z-index:-1; 
    border:1px dashed #CCC; 
    top:5px; 
    bottom:5px; 
    left:5px; 
    right:5px; 
    box-shadow: 0 0 0 1px #FFF; 
} 
.loginForm h1 { 
    text-shadow: 0 1px 0 rgba(255, 255, 255, .7), 0px 2px 0 rgba(0, 0, 0, .5); 
    text-transform:uppercase; 
    text-align:center; 
    color:#666; 
    line-height:3em; 
    margin:16px 0 20px 0; 
    letter-spacing: 4px; 
    font:normal 26px/1 Microsoft YaHei, sans-serif; 
} 
fieldset { 
    border:none; 
    padding:10px 10px 0; 
} 
/* fieldset input[type=text] {  */
/*     background:url(style/default/images/user.png) 4px 5px no-repeat;  */
/* }  */
/* fieldset input[type=password] {  */
/*     background:url(style/default/images/password.png) 4px 5px no-repeat;  */
/* }  */
fieldset input[type=text], fieldset input[type=password] { 
    width:100%; 
    line-height:2em; 
    font-size:12px; 
    height:24px; 
    border:none; 
    padding:3px 4px 3px 2.2em; 
    width:340px; 
} 
fieldset input[type=submit] , fieldset input[type=reset] { 
    text-align:center; 
    padding:2px 20px; 
    line-height:2em; 
    border:1px solid #FF1500; 
    border-radius:5px; 
    background: -webkit-gradient(linear, left top, left 24, from(#FF6900), color-stop(0%, #FF9800), to(#FF6900)); 
    background: -moz-linear-gradient(top, #FF6900, #FF9800 0, #FF6900 24px); 
    background:-o-linear-gradient(top, #FF6900, #FF9800 0, #FF6900 24px); 
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FF9800', endColorstr='#FF6900'); 
    -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#FF9800', endColorstr='#FF6900')"; 
    height:30px; 
    cursor:pointer; 
    letter-spacing: 4px; 
    margin-left:55px; 
    color:#FFF; 
    font-weight:bold; 
} 
fieldset input[type=submit], fieldset input[type=reset] :hover { 
    background: -webkit-gradient(linear, left top, left 24, from(#FF9800), color-stop(0%, #FF6900), to(#FF9800)); 
    background: -moz-linear-gradient(top, #FF9800, #FF6900 0, #FF9800 24px); 
    background:-o-linear-gradient(top, #FF6900, #FF6900 0, #FF9800 24px); 
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FF6900', endColorstr='#FF9800'); 
    -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#FF6900', endColorstr='#FF9800')"; 
} 
.inputWrap { 
    background: -webkit-gradient(linear, left top, left 24, from(#FFFFFF), color-stop(4%, #EEEEEE), to(#FFFFFF)); 
    background: -moz-linear-gradient(top, #FFFFFF, #EEEEEE 1px, #FFFFFF 24px); 
    background: -o-linear-gradient(top, #FFFFFF, #EEEEEE 1px, #FFFFFF 24px); 
    border-radius:3px; 
    border:1px solid #CCC; 
    margin:10px 10px 0; 
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#EEEEEE', endColorstr='#FFFFFF'); 
    -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#EEEEEE', endColorstr='#FFFFFF')"; 
} 
fieldset input[type=radio] { 
    margin-left:40px; 
}
fieldset label[type=school] { 
    margin-left:38px; 
}
fieldset select[type=selectschool] { 
    margin-left:15px;
    width:100%; 
    line-height:21em; 
    font-size:15px; 
    height:24px;
    border-radius:3px; 
    border:1px solid #9bc0dd;
    padding:0.8px 0.5px 0.5px 0.7em; 
    width:230px;
/*     background:url(style/default/images/password.png) 4px 5px no-repeat;  */
}
fieldset option[type=optionschool] { 
    text-indent: 10px;
    opacity:0.8;
    font-size:14px;
    background-color: #EEEEEE;
    border-radius:2px; 
}

fieldset a { 
    color:blue; 
    font-size:14px; 
    margin:6px 0 0 10px; 
    text-decoration:none; 
} 
fieldset a:hover { 
    text-decoration:underline; 
} 
fieldset span { 
    font-size:12px; 
} 
</style> 
<!--为了让IE支持HTML5元素，做如下操作：--> 
<![if IE]> 
<script type="text/javascript"> 
document.createElement("section"); 
document.createElement("header"); 
document.createElement("footer"); 
</script> 
<![endif]> 
 <script src="common/js/jq/jquery-2.1.4.min.js" type="text/javascript"></script> 
<script type="text/javascript"> 
$(document).ready(function(){

	$.ajax({
		url:"communion/gainUniversityBySearchAjax.action",
		data:{"q":""}
		}).done(function(resp){
		uns = eval("("+resp+")");
		opts = ""
		uns.forEach(function(e){
			 opts += "<option type='optionschool' value='"+e.id+"'>"+e.text+"</option>"; 
		}); 
		$("#university").html(opts);
	}); 
	
})

</script>

<link rel="shortcut icon" href="bitbug_favicon.ico" type="image/x-icon" />
</head> 

<s:debug></s:debug>
<body>
<div class="wrap"> 
  <form action="login.action"  method="post">
    <section class="loginForm"> 
      <header> 
        <h1>登录</h1> 
        <h3>超级管理员登录地址：http://ip:port/AnyviewSystem/superAdminLogin.jsp</h3> 
      </header> 
      <div class="loginForm_content">     
        <fieldset> 
			<div class="universityDiv">
			<label type="school">学&nbsp;校</label>
			<select type="selectschool" id="university" name="unId">
			<%-- <select id="university" name="unId" style="width: 150px;"> --%>
			<option></option>
				<c:forEach items="${universities }" var="u">
					<option type="optionschool" value="${u.unID }">${u.unName }</option>
				</c:forEach>			
			</select>
			</div>
          
          <div class="inputWrap"> 
            <input type="text" name="username" placeholder="请输入账号" autofocus required> 
          </div> 
          <div class="inputWrap"> 
            <input type="password" name="password" placeholder="请输入密码"> 
          </div> 
        </fieldset> 
        <fieldset>
			<div class="rankdiv">
				<input type="radio" name="rank" value="2" /> <label>学生</label> 
				<input type="radio" name="rank" value="1" checked="checked" /> <label>教师</label>
				<input type="radio" name="rank" value="0" /> <label>管理员</label>
			</div>
			<div>
				<s:fielderror/> <!-- 显示表单验证的出错信息 -->
			</div>
		</fieldset> 
        <fieldset> 
          <input type="submit" font-size="14px" value="登陆"> 
		  <input type="reset" font-size="14px" value="重置">           
		  <a href="student/visitorIndex.jsp">游客</a> 
        </fieldset>
        <div style="color:red">测试账号 用户名：20160301 密码为空</div>
      </div>
    </section> 
  </form> 
</div> 
</body> 
</html> 