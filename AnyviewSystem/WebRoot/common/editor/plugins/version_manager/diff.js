/** mousewheel 鼠标滚轮事件 */
$.fn.extend({
	mousewheel:function(Func){
	return this.each(function(){
		var _self = this;
		_self.D = 0;//滚动方向
		if($.browser.msie||$.browser.safari){
			_self.onmousewheel=function(){_self.D = event.wheelDelta;event.returnValue = false;Func && Func.call(_self);};
		}else{
			_self.addEventListener("DOMMouseScroll",function(e){
				_self.D = e.detail>0?-1:1;
				e.preventDefault();
				Func && Func.call(_self);
			},false); 
		}
	});
   }
});
/** Curve 曲线
 *  生成对比图的连线 */
var Curve = function() {
	function draw(y1,y2,r) {
		var self = this;
		if (!(self instanceof draw)) {
			return new draw(y1,y2,r);
		}
		self.top = y1>y2 ? y2 : y1;//偏移量
		self.range = Math.abs(y1-y2) / 2;//曲线范围
		self.angle = y1>y2 ? Math.PI/2 : -Math.PI/2;//初始角度
		self.radius = r;//周期半径
		return self.drawCurve();
	}

	draw.prototype = {
		
		drawCurve: function() {
			var self = this;
			var PI = Math.PI;
			var angle = self.angle;
			var x = 0,y = 0;
			for (var i = 0, arr = []; angle < self.angle+PI; ++i) {
			    x += self.radius;//水平分量
			    y = self.range + Math.sin(angle+=.02) * self.range + self.top;//垂直分量
				arr[i] = self.createDot(2, 2, x, y, '#FF0000');
			}
			var curvePanel = document.createElement("div");
			curvePanel.className = "curve";
			curvePanel.innerHTML += arr.join('');
			return curvePanel;
		},

		createDot: function(w, h, x, y, c) {
			return '<span style="width:'+w+'px;height:'+h+
			       'px;font-size:1;background-color:'+c+
			       ';position:absolute;left:'+x+'px;top:'+y+
			       'px;"></span>';
		}
	}

	return {draw: draw}
}();
/** 文本比较区初始化 */
function init(){
	var $textL = $("#text_L");
	var $textR = $("#text_R");
	var $hScroll = $("#hor_scroll");
	var $vScroll = $("#ver_scroll");
	var $graph = $("#graph");
	var $domL = $textL.contents();//iframe内的文档句柄
	var $domR = $textR.contents();//iframe内的文档句柄
	var $domWidth = $domL.width() > $domR.width()? $domL.width() : $domR.width();
	var $domHeight = $domL.height() > $domR.height()? $domL.height() : $domR.height();
	var $iframeHeight = $textL.height();//iframe可视窗口高度
	var $iframeWidth = $textL.width();//iframe可视窗口高度
	var $contentHeight = $domHeight - $iframeHeight;//文档显示高度
	var $contentWidth = $domWidth - $iframeWidth;//文档显示宽度
	var $hBarHeight = ($contentHeight * $iframeHeight / $domHeight) / Math.ceil( $domHeight / $iframeHeight);
	var $vBarWidth = $contentWidth * $iframeWidth / $domWidth;
	var $hScrollSD = 10;//
	
	$textL.add($textR).css({
		width:$domWidth+20,
		height:$domHeight+25
	});//设置iframe的尺寸
	
	var $posArray = [];//记录文本间不同地方的句柄
	var pos_1 = $textL.contents().find(".pos");
	var pos_2 = $textR.contents().find(".pos");
	for(var i=0;i<pos_1.length;++i){
		var pTop1 = pos_1.eq(i).offset().top + pos_1.eq(i).height()/2;
		var pTop2 = pos_2.eq(i).offset().top + pos_2.eq(i).height()/2;
		$posArray.push({
			diffY1 : pTop1,
			diffY2 : pTop2
		})
	}
	//
	$hScroll.css({
		height: $hBarHeight
	}).bind("mousedown",{type:"hor_scroll"},moveScroll);
	$vScroll.css({
		width: $vBarWidth
	}).bind("mousedown",{type:"ver_scroll"},moveScroll);

	$('#hor_scroll_bar').bind("mousedown",function(e){
		var startY = $hScroll.offset().top;
		var endY = e.pageY - $hScroll.height()/2;
		var sd = endY > startY ? $hScrollSD : -$hScrollSD;
		var t = window.setInterval(function(){
			if(Math.abs(startY-endY)<11){
				window.clearInterval(t);
			}else{
				startY += sd;
				setScrollY(startY);
			}
		},10);
		return false;
	});
	$(document).add($textL.contents()).add($textR.contents()).mousewheel(function(e){
		var y = parseInt($hScroll.css("top"));
		if(this.D>0){y -= 5;
		}else{y += 5;};
		setScrollY(y);
	});

	var $parentY = $hScroll.parent().height()-$hScroll.height();//滚动条可移动的垂直区域
	var $parentX = $vScroll.parent().width()-$vScroll.width();//滚动条可移动的垂直区域
	setScrollY(0);

	function moveScroll(e){
		var $target = $(e.target);
		if(e.data.type=="hor_scroll"){
			var pageY = e.pageY ,t = parseInt($target.css("top"));
			$(document).mousemove(function(e2){
				setScrollY(t+ e2.pageY - pageY);//pageY浏览器可视区域鼠标位置，screenY屏幕可视区域鼠标位置
			});
		}else{
			var pageX = e.pageX ,l = parseInt($target.css("left"));
			$(document).mousemove(function(e2){
				setScrollX(l+ e2.pageX - pageX);//pageY浏览器可视区域鼠标位置，screenY屏幕可视区域鼠标位置
			});
		}
		$(document).mouseup(function(){
			$(document).unbind();
		});
		return false;
	}
	function setScrollY(y){
		if(y < 0){y = 0}
		else if(y > $parentY){y = $parentY;}
		var scrollY = y / $parentY * $contentHeight;//计算需要移动iframe内容文档的高度
		if(scrollY < $contentHeight){
			$hScroll.css("top",y);//设置滚动条的位置
			$textL.css({marginTop:-scrollY});//设置文档的位置
			$textR.css({marginTop:-scrollY});
			$graph.empty();
			for(var i in $posArray){//显示文本间不同的连线
				var y1 = $posArray[i]['diffY1']-scrollY;
				var y2 = $posArray[i]['diffY2']-scrollY;
				$graph.append(Curve.draw(y1,y2,0.15));
			}
		}
	}
	function setScrollX(x){
		if(x < 0){x = 0}
		else if(x > $parentX){x = $parentX;}
		var width = 2 * $iframeWidth+$graph.width();
		var scrollX = x / $parentX * width;//计算需要移动iframe内容文档的高度
		if(scrollX < width){
			$vScroll.css("left",x);//设置滚动条的位置
			$textL.css({marginLeft:-scrollX});//设置文档的位置
			$textR.css({marginLeft:-scrollX});
		}
	}
};

var KE = parent.KindEditor;
location.href.match(/\?id=([\w-]+)/i);
var id = RegExp.$1;
var stack = KE.g[id].dialogStack;
var dialog = stack[stack.length - 1];//dialog句柄
$.ajax({
	type: "POST",
	url: KE.g[id].fileManagerJson,
	data: dialog.option,
	success : function(msg) {
		var result = KE.util.parseJson(msg);
		var $textL = $("#text_L").contents()[0];
		$textL.open();
		$textL.write(result.local);
		$textL.close();
		var $textR = $("#text_R").contents()[0];
		$textR.open();
		$textR.write(result.remote);
		$textR.close();
		init();
	}
});
KE.event.ready(function() {
	window.focus();
	KE.util.hideLoadingPage(id);
}, window, document);