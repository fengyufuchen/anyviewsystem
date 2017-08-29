runTest = function(ceId,schemeId,pId,exName,dom,answer) {
	if (!window.ActiveXObject) {
		alert("运行测试只适用于IE浏览器,请用IE浏览器重新打开");
		return;
	}
	var testobj;
	try {
		testobj = new ActiveXObject("RunAnyviewTest.RunTest"); //创建对象
	} catch (e) {
		alert("请下载并安装测试客户端!");
		$("form").attr("action","downloadAnyview.action");
		$("form").submit();
		return;
	}
	testobj.OpenClient(); //打开Anyview客户端
	if (testobj.OpenClientResult != 1) //返回1 表示已经打开
	{
		alert("请重新安装测试客户端！");
		return;
	}
	testobj.PrepareTest(exName,ceId,pId,
			schemeId);//这个函数必须在以下函数的前面 //准备测试:习题名,用户ID,习题ID,作业表ID
	var a = dom.getElementsByTagName("a")[0];
	var b = dom.getElementsByTagName("b")[0];
	var c = dom.getElementsByTagName("c")[0];
	var d = dom.getElementsByTagName("d")[0];
	var e = dom.getElementsByTagName("e")[0];
	testobj.SetMainFile(a.getElementsByTagName("name")[0].childNodes[0].nodeValue,
		a.getElementsByTagName("content")[0].childNodes[0].nodeValue);//设置主文件:文件名和文件内容
	testobj.SetStdFile(b.getElementsByTagName("name")[0].childNodes[0].nodeValue,
		b.getElementsByTagName("content")[0].childNodes[0].nodeValue); //设置标准答案文件:文件名和文件内容
	testobj.SetUserFile(c.getElementsByTagName("name")[0].childNodes[0].nodeValue,
		answer, "");//设置用户答案文件:文件名,文件内容,用户函数头文件(只有函数头,没有实现的那个,可以为空)
	testobj.SetDocFile(e.childNodes[0].nodeValue);//设置说明文档:文档内容
	testobj.AddHeadFile(d.getElementsByTagName("d1")[0].getElementsByTagName("name")[0].childNodes[0].nodeValue,
		d.getElementsByTagName("d1")[0].getElementsByTagName("content")[0].childNodes[0].nodeValue);//添加头文件:文件名和文件内容.如果有多个头文件,则可以多次调用该函数添加头文件
	testobj.StartTest(); //开始测试
}
