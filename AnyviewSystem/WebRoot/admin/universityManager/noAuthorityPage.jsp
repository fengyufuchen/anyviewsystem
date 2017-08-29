<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="pageFormContent" layoutH="56">
     <div id="content" style="margin-top:65px;margin-left:75px">      
         <div class="icon"  style="float:left">
		      <img src="common/imgs/error/alert.ico">
		 </div>
		 <div class="text"style="font-size:22px;color:#EF1519"  >
		      ${errorMessage }
		 </div>
     </div>
	 <div class="formBar"style="padding-right:265px;margin-top:75px;border:none">
			<ul>
				<li>
					<div class="button"" >
						<div class="buttonContent" >
							<button class="close" type="button">我知道了</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>