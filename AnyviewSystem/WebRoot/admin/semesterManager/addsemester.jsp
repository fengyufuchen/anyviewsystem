<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!--文件名：addsemester.jsp-->
<!--描   述：添加学期页面-->
<!--时   间 ：2015年08月04日-->

		<form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" action="admin/semesterManager/addSemester.action" method="post" novalidate="novalidate">
			<div class="pageFormContent" layouth="58" style="height: 203px; overflow: auto;">
				<div class="unit">
					<label>学期名：</label>
					<input type="text" name="sname" class="required">
				</div>
				<div class="unit">
					<label>开学日期：</label>
					<input class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" name="startTime">
					<a class="inputDateButton" href="javascript:;">选择</a>
				</div>
				<div class="unit">
					<label>停课日期：</label>
					<input class="date textInput readonly required" type="text" readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" name="endTime">
					<a class="inputDateButton" href="javascript:;">选择</a>
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

