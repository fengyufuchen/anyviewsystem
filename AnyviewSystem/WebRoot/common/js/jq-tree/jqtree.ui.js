$(function() {// XML解析
	
	jQuery.fn.parseXml = function(externalXmlPath, cid, ptype){
		

			var SingleImg1 = ["minustop.png", "minus.png", "plustop.png",
					"plus.png"], FulColorImg1 = ["minustop_red.png",
					"minus_red.png", "plustop_red.png", "plus_red.png"], FulGreenImg1 = [
					"minustop_gre.png", "minus_gre.png", "plustop_gre.png",
					"plus_gre.png"],

			SingleImg2 = ["plustop.png", "plus.png", "minustop.png",
					"minus.png"], FulColorImg2 = ["plustop_red.png",
					"plus_red.png", "minustop_red.png", "minus_red.png"], FulGreenImg2 = [
					"plustop_gre.png", "plus_gre.png", "minustop_gre.png",
					"minus_gre.png"],

			SingleLineImg = ["line.png", "join.png", "joinbottom.png"], FulColorLine = [
					"line_red.png", "join_red.png", "joinbottom_red.png"], FulGreenLine = [
					"line_gre.png", "join_gre.png", "joinbottom_gre.png"],

			ImgArray1 = [SingleImg1, SingleImg2], ImgArray2 = [FulColorImg1,
					FulColorImg2], ImgArray3 = [FulGreenImg1, FulGreenImg2], LineImgArray1 = SingleLineImg, LineImgArray2 = FulColorLine, LineImgArray3 = FulGreenLine,

			Tree_collapse = function(ImgSelector) { // 树状点击控制,作为XML解析函数的回调参数
				var ImgNamF = -1, ImgArray;
				$("img." + ImgSelector).css("cursor", "pointer").click(
						function(e) {
							e.stopImmediatePropagation();
							if (ImgNamF == -1) {
								ImgNamF = $(this).attr("src").lastIndexOf("/");
							}
							var CurImg = $(this).attr("src").slice(ImgNamF + 1);
							if (CurImg.match("red")) {
								ImgArray = ImgArray2;
							} else {
								if (CurImg.match("gre")) {
									ImgArray = ImgArray3;
								} else {
									ImgArray = ImgArray1;
								}

							}
							for (var i = 0; i < ImgArray[0].length; i++) {
								if (CurImg == ImgArray[0][i]) {
									$(this).attr(
											"src",
											$(this).attr("src").replace(CurImg,
													ImgArray[1][i]));
								}
							}
							$(this).siblings("div").toggle();
						});
			},

			ShowTree = function(path, $obj, chap_ary, ImgUrl) { // 树状图显示(path:xml路径,$obj:树状图目标位置,chapID:所选章节,ImgUrl:组件路径)
				var appendStr = "", chapSelector = "", headImg = "";
				$.get(path, function(data) {
							var IndexObj = new Object();
							IndexObj.globalIndex = 0;
							for (var i = 0; i < chap_ary.length; i++) {
								chapSelector = "chapter[value^='" + chap_ary[i]
										+ "_']";
								appendStr += "<div id='tree_"
										+ chap_ary[i]
										+ "'>"
										+ $(data).find(chapSelector)
												.DeepTravel(headImg, IndexObj,
														"collapse", ImgUrl)
										+ "</div>";
							}
							$obj.append(appendStr);
				});
			},
			// 树状图显示数据初始化
			InitPage = function(teaProStr, fu_teaStr, CustomerColor, ColorMem, PointMem1, PointMem2) {
				var InitAry = [teaProStr, fu_teaStr, PointMem1, PointMem2];
				for (var i = 0; i < InitAry.length; i++) {
					for (var index = 0; index < InitAry[i].length; index++) {
						InitAry[i][index] = InitAry[i][index].slice(0,
								InitAry[i][index].indexOf("_")).replace(/\./g,
								"_");
					}
				}
				for (var i = 0; i < CustomerColor.length; i++) {
					ColorMem.push(CustomerColor[i]);
				}
				$("body").append("<div id='MouseDiv'></div><div id='TreeDiv'></div>");
				
			},

			ImgChange = function($obj, thisIndex, thisImg, thisLine) { // 图标转化
				for (var k = 0; k < 2; k++) {
					if ($obj.attr("src").match(thisImg[0][k])) {
						var CurImg = $obj.attr("src");
						$obj.attr("src", CurImg.replace(thisImg[0][k],
										thisImg[1][k]));
						break;
					}
				}
				if (k >= 2) {
					for (var k = 0; k < 3; k++) {
						if ($obj.attr("src").match(thisLine[0][k])) {
							var CurImg = $obj.attr("src");
							$obj.attr("src", CurImg.replace(thisLine[0][k],
											thisLine[1][k]));
							break;
						}
					}
				}
			},

			DrawTeapro = function(MemAry, $targetSel, tempImgAry, lineImgAry) { // 树状图教学进度显示
				if (MemAry.length > 0) {
					$targetSel.find("#chap_" + MemAry[0].slice(0, 1))
							.children("img").each(function(index) {
								ImgChange($(this), index, tempImgAry,
										lineImgAry);
							});
					for (var i = 0; i < MemAry.length; i++) {
						$targetSel.OutTravel(MemAry[i], "#chap_", "_", 0,
								ImgChange, tempImgAry, lineImgAry);
					}
				}
			},

			TreeControl = function(theChap, mouseTree, wholeTree) { // 导航栏章节Mouse处理
				$(mouseTree).hover(function(e) {
							$(this).clearQueue();
						}, function(e) {
							if ($(this).html() != "") {
								if ($(this).find("[id ^= 'tree_']").size() != 0) {
									$(wholeTree).append($(this).children());
								} else {
									$(wholeTree).find("#tree_"
											+ $(this).find("[id ^= 'chap_']")
													.attr("id").slice(5))
											.append($($(this).children()));
								}
							}
							$(this).hide();
						}).hide();
				$(theChap).hover(function(e) {
					$(mouseTree).clearQueue().mouseleave();
					var $mouseObj;
					if ($(this).attr("id") != "chapter_all") {
						$mouseObj = $(wholeTree).find("#tree_"
								+ $(this).attr("id").slice(8)).children();
					} else {
						$mouseObj = $(wholeTree).children();
					}
					if ($mouseObj.html() != null) {
						$(mouseTree).css({
							"top" : $(this).offset().top
									+ $(this).parent().height()
						});
						if (($(window).width() - $(this).offset().left) < 300) {
							$(mouseTree).css(
									"left",
									$(this).offset().left + $(this).width()
											- 300);
						} else {
							$(mouseTree).css("left", $(this).offset().left);
						}
						$(mouseTree).append($mouseObj).show();
					}
				}, function(e) {
					$thisObj = $(this);
					$(mouseTree).delay(1).queue(function() {
						if ($thisObj.attr("id") != "chapter_all") {
							$(this).children().appendTo($(wholeTree)
									.find("#tree_"
											+ $thisObj.attr("id").slice(8)));
						} else {
							$(this).children().appendTo($(wholeTree));
						}
						$(this).hide().dequeue();
					});
				});
			},

//			xmlPath = "../js/jq-tree/tree.xml", colorState = new Array();
			xmlPath = externalXmlPath, colorState = new Array();

			jQuery.fn.DeepTravel = function(PrefixImg, theIndexObj, claStyle, theImg_url) {
				var tempStr = this.size() ? "<div>" : "", TempImg = "", tempSize = this.size();
				this.each(function(tempIndex) {
							var Collapseflg;
							if ($(this).children().size()) {
								TempImg = PrefixImg + "<img class='tree_img "
										+ claStyle + "' src='" + theImg_url;
								Collapseflg = "class ='" + claStyle + "'";
								if (theIndexObj.globalIndex == 0) {
									TempImg += ImgArray1[0][0] + "'/>";
								} else {
									TempImg += ImgArray1[0][1] + "'/>";
								}
							} else {
								TempImg = PrefixImg
										+ "<img class='tree_img' src='"
										+ theImg_url;
								Collapseflg = "";
								if (tempIndex == tempSize - 1) {
									TempImg += LineImgArray1[2] + "'/>";
								} else {
									TempImg += LineImgArray1[1] + "'/>";
								}
							}
							if ($(this).attr("url") != undefined) {
								tempStr += "<div id='chap_"
										+ $(this).attr("value").slice(
												0,
												$(this).attr("value")
														.indexOf("_")).replace(
												/\./g, "_")
										+ "'"
										+ Collapseflg
										+ ">"
										+ TempImg
										+ "<a href='"
										+ $(this).attr("url")
										+ "' target='iframe'>"
										+ $(this).attr("value").slice(
												0,
												$(this).attr("value")
														.indexOf("_"))
										+ " "
										+ $(this).attr("value").slice($(this)
												.attr("value").indexOf("_")
												+ 1) + "</a>";
								tempStr += "";
							} else {
								tempStr += "<div id='chap_"
										+ $(this).attr("value").slice(
												0,
												$(this).attr("value")
														.indexOf("_")).replace(
												/\./g, "_")
										+ "'"
										+ Collapseflg
										+ ">"
										+ TempImg
										+ $(this).attr("value").slice(
												0,
												$(this).attr("value")
														.indexOf("_"))
										+ " "
										+ $(this).attr("value").slice($(this)
												.attr("value").indexOf("_")
												+ 1);
							}
							theIndexObj.globalIndex++;
							tempStr += $(this).children().DeepTravel(
									PrefixImg + "<img class='tree_img' src='"
											+ theImg_url + LineImgArray1[0]
											+ "'/>", theIndexObj, claStyle,
									theImg_url)
									+ "</div>";
						});
				return tempStr += this.size() ? "</div>" : "";
			}; // 树状图插件(功能:树形深度优先搜索)

			jQuery.fn.OutTravel = function(curSuffix, curPrefix, DelimiterStr,
					Outlevel, controlFun, funImg, funLine) {// 参数::id后缀,id前缀,界符,外翻层数
				// 操作函数, 操作函数的参数(图标及线形图标)
				// 规范限定::界符只能是一位长的字符,前缀、后缀中每隔一位有一个界符
				if ((curSuffix.split(DelimiterStr)).length == 2) {
					if ($.isFunction(controlFun)) {
						if (Outlevel == 0) {
							$(this).find(curPrefix + curSuffix).find("img")
									.each(function(index) {
										controlFun($(this), index, funImg,
												funLine);
									});
						} else {
							$(this).find(curPrefix + curSuffix).children("img")
									.each(function(index) {
										controlFun($(this), index, funImg,
												funLine);
									});
						}
					}
				} else {
					if ($.isFunction(controlFun)) {
						$(this).find(curPrefix + curSuffix).find("img").each(
								function(index) {
									controlFun($(this), index, funImg, funLine);
								}).end().prevAll().find("img").each(
								function(index) {
									controlFun($(this), index, funImg, funLine);
								});
						$(this).OutTravel(curSuffix.slice(0, -2), curPrefix,
								DelimiterStr, ++Outlevel, controlFun, funImg,
								funLine);
					}
				}
			};
			
			var MouseDiv = '#MouseDiv', TreeId = '#TreeDiv', MouseChap = '#navMenu a';
			var tea_pro, chapAry, future_pro, future_chap, page_ary,
				scoreSta1 = [], scoreSta2 = [], pointSta1 = [], pointSta2 = [], scoreSta = [], pointSta = [];
			scoreSta = [ scoreSta1, scoreSta2 ];
			pointSta = [ pointSta1, pointSta2 ];
			
			$.getJSON("TeachPlanAction.action",{'cid':cid, "ptype":ptype},
				function(data) {
					chapAry = data.data[0];
					tea_pro = data.data[1];
					future_pro = data.data[2];
					future_chap = data.data[3];
					page_ary = [ chapAry, tea_pro, future_pro, future_chap ];
					initTree();
			});
			
			function initTree(){
				InitPage(tea_pro, future_pro, ["#ff0000", "#00b300"], colorState,pointSta1, pointSta2);
				for (var i = 1; i < 13; i++) {
					$("#navMenu ul").append("<li><a href='#' id='chapter_"
							+ i + "' ><span>第" + i + "章</span></a></li>");
				}
				
				for (var i = 0; i < future_chap.length; i++) {
					$("#chapter_" + future_chap[i]).parent().addClass("selected");
				}
				ShowTree(xmlPath, $(TreeId), chapAry, "../js/jq-tree/img/");
				$(TreeId).ajaxSuccess(function() {
					Tree_collapse("collapse");
					DrawTeapro(tea_pro, $(this), [SingleImg1, FulColorImg1], [
									SingleLineImg, FulColorLine]);
					DrawTeapro(future_pro, $(this), [SingleImg1, FulGreenImg1], [
									SingleLineImg, FulGreenLine]);
					$(TreeId).find("a").css("color", "#000000").click(function(e) {
								e.preventDefault();
								$this = $(this);
								var title = $this.text();
								var tabid = "treeMenu"+title.substring(0,title.indexOf(" "));
								var url = $this.attr("href");
								navTab.openTab(tabid, url,{title:title, fresh:true, external:true});
							});
					for (var i = 0; i < pointSta.length; i++) {// 0:60以下
						for (var index = 0; index < pointSta[i].length; index++) {
							$(TreeId).find("#chap_" + pointSta[i][index])
									.children("a").attr("title",
											"测试成绩：" + scoreSta[i][index] + "分")
									.css("color", colorState[i]);
						}
					}
				});
				TreeControl(MouseChap, MouseDiv, TreeId);
			};
		};
});
