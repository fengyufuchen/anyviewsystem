<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>��Ȩ����</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="404" />
		<link href="themes/css/vcds/error.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="error">
			<div id="header"></div>
			<div id="content">
				<div id="imgDiv">
					<img src="imgs/error/404.jpg" />
				</div>
				<div id="infoDiv">
					<p>
						�Բ�����ûȨ���ʸ�ҳ�棬ע�����صǣ�
					</p>
				</div>
				<div id="planDiv">
					<p>
						����������ҳ�����Ժ�.......
					</p>
					<p>
						<input type=text id="chart" size="40" />
						<br />
						<input type=text id="percent" size="26" />
					</p>
				</div>
			</div>
			<div id="footer">
				<p>
					Copyright &copy; 2012-2013 ���ݽṹ����Ӽ����Ŷ�
				</p>
			</div>
		</div>
	</body>

	<script>
	var $ = function(id) {
		return document.getElementById(id);
	}
	var bar = 0;
	var line = "||";
	var amount = "||";
	count();
	function count() {
		bar = bar + 2;
		amount = amount + line;
		$("chart").value = amount;
		$("percent").value = bar + "%";
		if (bar < 99) {
			setTimeout("count()", 100);
		}//����ʱ��
		else {
			window.location = "/StudySystem/login.jsp";
		}
	}
</script>
</html>