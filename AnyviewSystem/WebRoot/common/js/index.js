$(function() {
	$("#Lef_View div img").hover(function() {
		$(this).addClass("OverDiv");
	}, function() {
		$(this).removeClass("OverDiv");
	}).click(function() {
		var divId = $(this).parent().eq(0).attr("id");
		var begain = divId.indexOf("_")+1;
		var end = divId.length;
		var id = divId.substring(begain, end);
		$("#Mid_View").children().each(function() {
			var iframe = $(this);
			var path = iframe.attr("class");
			var src = iframe.attr("src");
			if (iframe.attr("id") == id) {
				if(src == ""){
					iframe.attr("src", path);
				}
				iframe.css("display", "");
			} else {
				iframe.css("display", "none");
			}
		});
	});
});