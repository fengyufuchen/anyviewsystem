var tmpl = {
	newInstance: function(type){
		if(this.checkInface(this[type],["create"])){
			return this[type]
		}else{
			return null;
		}
	},
	checkInface: function (obj,methods){//检查是否实现了后面的接口方法，如果没实现将抛出异常
	　　if(!(typeof obj == 'object')){
	　　　　throw new Error("error");
	　　}
	　　for(var i=0,len=methods.length;i<len;i++){
	　　　　var method = methods[i];
	　　　　if(!obj[method] || typeof obj[method] !== 'function'){
	　　　　　　throw new Error('error');
	　　　　}
	　　}
		return true;
	},
	
	TABLE_TMPL: "#tableTmpl#",
	TABLE_BAR_TOP: "#tableBarTop#",
	TABLE_BAR_BOTTOM: "#tableBarBottom#",
	TABLE: "#table#",
	TABLE_HEAD: "#tableHead#",
	TABLE_BODY: "#tableBody#",
	
	stuList: {
		create: function(data){
			var tableTmpl = DWZ.tmpl["tableTmpl"];
			var tableBarTop = DWZ.tmpl["tableBarTop"];
			var tableBarBottom = DWZ.tmpl["tableBarBottom"];
			var table = DWZ.tmpl["table"];
			var tableHead = DWZ.tmpl["tableHead-stuList"];
			var tableBody = "";
			
			for(var i=0;i<data.length;++i){
				var body = DWZ.tmpl["tableBody-stuList"];
				var obj = data[i];
				tableBody += body.replace("#index#",i+1)
								 .replace("#s_user#",obj["s_user"])
								 .replace("#s_name#",obj["s_name"])
								 .replace("#s_class#",obj["s_class"])
								 .replace("#s_course#",obj["s_course"])
								 .replace("#s_grade#",obj["s_grade"]);
			}
			tableTmpl = tableTmpl
							.replace(tmpl.TABLE_BAR_TOP,tableBarTop)
							.replace(tmpl.TABLE_BAR_BOTTOM,tableBarBottom)
							.replace(tmpl.TABLE,table
												.replace(tmpl.TABLE_HEAD,tableHead)
												.replace(tmpl.TABLE_BODY,tableBody));
			return tableTmpl;
		}
	},
	
	//新建一个名为testScore的list样式
	testScore: {
		create: function(data){
			var form = DWZ.tmpl["form-testScore"];
			var tableTmpl = DWZ.tmpl["tableTmpl"];
			var tableBarTop = "";	//指显示页面上的修改，删除功能按键var tableBarTop = DWZ.tmpl["tableBarTop"]
			var tableBarBottom = DWZ.tmpl["tableBarBottom-testScore"];
			var table = DWZ.tmpl["table-testScore"];
			var tableHead = DWZ.tmpl["tableHead-testScore"];
			var tableBody = "";
			
			for(var i=0;i<(data.length - 2);++i){
				var body = DWZ.tmpl["tableBody-testScore"];
				var obj = data[i];
				tableBody += body.replace("#index#",i+1)	//序号
								 .replace("#pchapter#",obj["pchapter"])	//用obj[]中的pchapter替换模版中的pchapter
								 .replace("#ppoint#",obj["ppoint"])
								 .replace("#trscore#",obj["trscore"])
								 .replace("#classesRanking#",obj["classesRanking"])
								 .replace("#allRanking#",obj["allRanking"]);
			}
			var obj = data[i];
			form = form
					.replace("#numPerPage#", obj["everyPage"]);
			tableBarBottom = tableBarBottom
								.replace("#totalCount#", obj["totalCount"])
								.replace("#totalCount#", obj["totalCount"])
								.replace("#numPerPage#", obj["everyPage"])
								.replace("#currentPage#", obj["currentPage"]);
			var obj = data[++i];
			form = form
					.replace("#trtype#", obj[0]);
			tableTmpl = tableTmpl
							.replace(tmpl.TABLE_BAR_TOP,tableBarTop)
							.replace(tmpl.TABLE_BAR_BOTTOM,tableBarBottom)
							.replace(tmpl.TABLE,table
												.replace(tmpl.TABLE_HEAD,tableHead)
												.replace(tmpl.TABLE_BODY,tableBody));
			tableTmpl = form + tableTmpl;
			return tableTmpl;
		}
	},
	
	/*新建一个名为courseList的list样式，用于管理员端显示课程列表*/
	courseList: {
		create: function(data){
			var tableTmpl = DWZ.tmpl["tableTmpl-courseList"];
			var tableBarTop = DWZ.tmpl["tableBarTop"];	//指显示页面上的修改，删除功能按键var tableBarTop = DWZ.tmpl["tableBarTop"]
			var tableBarBottom = DWZ.tmpl["tableBarBottom-courseList"];
			var table = DWZ.tmpl["table"];
			var tableHead = DWZ.tmpl["tableHead-courseList"];
			var tableBody = "";
			
			for(var i=0, j =1;i<data.length;++i,j++){
				var body = DWZ.tmpl["tableBody-courseList"];
				var obj = data[i];
				tableBody += body.replace("#indexs#",obj["cid"])
								 .replace("#index#", j)
								 .replace("#course#",obj["cname"]);	//用obj替换模版中的course
			}
			tableTmpl = tableTmpl
							.replace(tmpl.TABLE_BAR_TOP,tableBarTop)
							.replace(tmpl.TABLE_BAR_BOTTOM,tableBarBottom)
							.replace(tmpl.TABLE,table
												.replace(tmpl.TABLE_HEAD,tableHead)
												.replace(tmpl.TABLE_BODY,tableBody));
			return tableTmpl;
		}
	},
	
	/*用于管理员端显示意见反馈*/
	suggestList: {
		create: function(data){
			var form = DWZ.tmpl["form-testScore"];
			var tableTmpl = DWZ.tmpl["tableTmpl-suggestList"];
			var tableBarTop = "";	//指显示页面上的修改，删除功能按键var tableBarTop = DWZ.tmpl["tableBarTop"]
			var tableBarBottom = DWZ.tmpl["tableBarBottom"];
			var table = DWZ.tmpl["table"];
			var tableHead = DWZ.tmpl["tableHead-suggestList"];
			var tableBody = "";
			
			for(var i=0;i<data.length-1;++i){
				var body = DWZ.tmpl["tableBody-suggestList"];
				var obj = data[i];
				tableBody += body.replace("#index#",i+1)	//序号
								 .replace("#stid#",obj["stid"])	
								 .replace("#uid#",obj["uid"])	
								 .replace("#stcontent#",obj["stcontent"])
								 .replace("#sttime#",obj["sttime"])
								 .replace("#stip#",obj["stip"])
			}
			tableTmpl = tableTmpl
							.replace(tmpl.TABLE_BAR_TOP,tableBarTop)
							.replace(tmpl.TABLE_BAR_BOTTOM,tableBarBottom)
							.replace(tmpl.TABLE,table
												.replace(tmpl.TABLE_HEAD,tableHead)
												.replace(tmpl.TABLE_BODY,tableBody));
			var obj = data[i];
			
			return tableTmpl;
		}
	},
	
};