<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<br/><br/><br/><br/><br/><body>
<!-- <p><a href="setup.exe">下载Anyview客户端(最新版本:1.6.5)</a></p> -->
<p><input type="button" value="下载最新版Anyview客户端" onclick="window.location.href='common/download/setup.exe'" />
</p>
<br/><br/><br/><br/><br/>
<p>
<input type="button" value="启动本地Anyview客户端" onclick="exec('C:\\Anyview\\AnyviewCe.exe')" />

<script type="text/javascript">    
 function exec(command)    {
        window.oldOnError = window.onerror;
        window._command = command;
        window.onerror = function (err) {
         if (err.indexOf('utomation') != -1){
            alert('请更改你的IE的安全级别：Internet选项->安全->自定义级别->对没有标记为安全的ActiveX控件进行初始化和脚本运行->启用。命令：'+ window._command); 
            return true;
        }
        else
            return false;
        };
        var regEdit = new RegEdit();  
        var x = regEdit.regRead("HKEY_CURRENT_USER\\SOFTWARE\\Anyview\\Version");  
          
        if(x == ""){
           alert("请先下载安装Anyview!");  
           //return true;  
        }
        else if(x != "1.6.5"){  
           alert("请更新Anyview的版本!");  
           //return true;  
        }
        else {
	    	var wsh = new ActiveXObject('WScript.Shell');
	        if (wsh) wsh.Run(command);
	        wsh = null;
	        window.onerror = window.oldOnError;
        }
        
     }
	////////////////注册表编辑类start//////////////////////  
	 /** 
	  * 注册表编辑器，封装对注册表的操作 
	  */  
	 function RegEdit(){  
	  this.shell = new ActiveXObject("WScript.Shell");  
	  this.regRead = regRead;  
	  this.regWrite = regWrite;  
	  this.regDelete = regDelete;  
	 }  
	   
	 /** 返回名为 strName 的注册键或值。 
	  * @param strName 要读取的键或值。如果 strName 以反斜线 (\) 结束，本方法将返回键，而不是值 
	  * @return 名为 strName 的注册键或值 
	  */  
	 function regRead(strName){  
	  var val = null;  
	  try {  
	   val = this.shell.regRead(strName);  
	  } catch (e) {  
	   alert(e.message);  
	  }  
	  return val;  
	 }  
	   
	 /** 设置 strName 指定的注册键或值 
	  * @param strName 要写的键或值的名称.如果 strName 以反斜线 (\) 结束，本方法将返回键，而不是值 
	  * @param anyValue 要写入键或注册表值中的值 
	  * @param strType 可选项。要保存到注册表中的值的数据类型REG_SZ、REG_EXPAND_SZ、REG_DWORD、REG_BINARY 
	  */  
	 function regWrite(strName,anyValue,strType){  
	  if(strType == null)  
	   strType = "REG_SZ";  
	  this.shell.regWrite(strName,anyValue,strType);  
	 }  
	   
	 /** 从注册表中删除 strName 指定的键或值。 
	  * @param strName 要删除的键或值的名字。如果 strName 以反斜线 (\) 结束，本方法将删除键，而不是值 
	  */  
	 function regDelete(strName){  
	  this.shell.regDelete(strName);  
	 }  
	 ////////////////注册表编辑类end//////////////////////
</script></p>
</body>