var KE = parent.KindEditor;
location.href.match(/\?id=([\w-]+)/i);
var id = RegExp.$1;
KE.event.ready(function() {
	var html = KE.html(id);
	html = "<div class='contain'>" + html + "</div>"
	document.body.innerHTML = html;
	KE.util.pluginLang('preview', document);
	KE.util.hideLoadingPage(id);
}, window, document);

$(document).ready(function(){
	stretch();
	imagePreview();
	flashPreview(); 
})
/**图片浏览*/
this.imagePreview = function(){	
	xOffset = -10;
	yOffset = -100;
	$("img.point").hover(function(e){
		$("body").append("<p id='preview'><img src='"+ this.src +"' /></p>");
		$("#preview")
		.css("top",(e.pageY - xOffset) + "px","left",
			(e.pageX + yOffset) + "px")
			.fadeIn("fast");						
	},
	function(){
		$("#preview").remove();
	});	
	$("img.point").mousemove(function(e){
		$("#preview")
		.css("top",(e.pageY - xOffset) + "px")
		.css("left",(e.pageX + yOffset) + "px");
	});			
};
/**内容伸缩*/
this.stretch = function(){
	var stretch;//点击伸缩按钮句柄
	if($.browser.msie){//ie
		$(".stretch > pre").each(function(){ //不知为什么ie下pre是dd的子元素
			$(this).css("visibility","hidden");
		});
		stretch = $(".stretch .point");
	}else{ //其他
		$(".stretch ~ pre").each(function(){ //dd的同辈元素
			$(this).css("visibility","hidden");
		});
		stretch = $(".stretch");
	}
	stretch.click(function(){
		var pre = $(this).nextAll("pre:first");//第一个pre元素
		var img = $(this).find(".point img");
		var src = img.attr("src");
		if(pre.css("visibility")=="hidden"){
			pre.css("visibility","inherit");
			src = src.replace("images/expand.gif","images/collapse.gif");
			img.attr("src",src);
		}else{
			pre.css("visibility","hidden");
			src = src.replace("images/collapse.gif","images/expand.gif");
			img.attr("src",src);
		}
		return false;
	});
};
/**动画浏览*/
this.flashPreview = function(){
	$("a.flash").click(function(e){
		if($("#flash").length>0){$("#flash").remove()};//flash存在则去掉
		var win = $(window.parent).length>0 ? $(window.parent) : $(window);//判断是否在iframe窗口内
		var sTop,sLeft;
		if(window.parent.document!=document){
			var pdoc = $(window.parent.document);//取父窗口document
			var iframeTop = $("#iframe",pdoc).position().top;
			var iframeLeft = $("#iframe",pdoc).position().left;
			if(pdoc.scrollLeft()>iframeLeft){
				sLeft = pdoc.scrollLeft() - $(document).scrollLeft() - iframeLeft;
			}else{
				sLeft = - 2*iframeLeft/3;
			}
			if(pdoc.scrollTop()>iframeTop){
				sTop = pdoc.scrollTop() - $(document).scrollTop() - iframeTop;
			}else{
				sTop = - iframeTop/2;
			}
		}else{
			sLeft = $(document).scrollLeft();
			sTop = $(document).scrollTop();
		}
		//加入到body内
		$("body").append("<div id='flash'>"+
			             "<a href='#' class='closeView'></a>"+
		                 "<div id='flashView'></div></div>");
		$("#flash").css({//显示flash位置
			position:'absolute',
			left: (win.width()/2 - $(this).outerWidth())/2 + sLeft, 
			top: (win.height()/2 - $(this).outerHeight())/2 + sTop,
			display:'block'
		});
		//flash大小
		if($(this).attr("alt")=="game"){
			$("#flash").css({
				width: win.width()/2,
				height:4*win.height()/5
			});
		}else{
			if($.browser.msie){//ie
				$("#flash").css({
					width:5*win.width()/9,
					height:2*win.height()/3
				});
			}else{
				$("#flash").css({
					width:12*win.width()/25,
					height:win.height()/2
				});
			}
		}
		//flash路径
		var src = $(this).attr("href");							 
		$("#flashView").flash(
			{ src: src,
				width: '100%',
				height: '100%' }
		);
		//e.preventDefault();
		//e.stopPropagation();
		$(".closeView").click(function(e){//关闭按钮
			$("#flash").remove();
			e.preventDefault();
			return false;
		});
		return false;					
	});
};