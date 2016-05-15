// JavaScript Document
var maxStdCount = 0;
var stdCount = 0;
var startnum = 0;
window.onscroll = function() {
	var top = document.body.scrollTop; // 向下滚动距离
	var height = document.body.scrollHeight; // 正文高度
	var screenHeight = window.screen.availHeight; //
	if (screenHeight + top - 60 >= height) {
		$(".loading").fadeIn();
		setTimeout(function() {
			getStdList();
		}, 800);
	}
};
$(function() {
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 2
		},
		success : function(data) {
			if (data == 4004) {
				swal({
					text : "登陆超时，请重新登陆"
				}, function() {
					window.location.href = "Login.html";
				});
			} else {
				var obj = eval("(" + data + ")");
				$("#teano").html(obj.account);
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#tname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);
				stdCount = obj.teaStdCount;
				maxStdCount = obj.maxSCount;

				getStdList();
			}
		},
		error : function(data) {
			alert("抱歉，获取基本信息出错，请刷新重试...");
		}
	});
});
function getStdList() {
	$.ajax({
		type : 'POST',
		url : "./servlet/StdList",
		data : {
			start : startnum,
		},
		success : function(data) {
			if (data != 1010) {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					if (obj[i].select == false) {
						$("#DlistWarp").append(
								stdList(obj[i].imgURL, obj[i].id, obj[i].name,
										obj[i].major, obj[i].sclass, 'block',
										obj[i].belong, obj[i].date));
					}
					if (obj[i].select == true) {
						$("#DlistWarp").append(
								stdList(obj[i].imgURL, obj[i].id, obj[i].name,
										obj[i].major, obj[i].sclass, 'none',
										obj[i].belong, obj[i].date));
					}
				}
				startnum += obj.length;
			} else {
				$(".loading").fadeOut();
			}
		},
		error : function() {
			alert("抱歉，获取学生列表出错，请刷新重试...");
		}
	});
}

function selectStd(self) {
	if (stdCount < maxStdCount) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		currentMdt = $(self);
		currentMdt.removeAttr("onclick");
		addmdt(e, currentMdt);
	} else {
		swal("学生数量已经达到管理员指定的上限了哦，不能添加了~亲");
	}
}

/* 学生列表信息 */
function stdList(imgSrc, sno, sname, smajor, sclass, btnshow, belong, regdate) {
	var $stdlist = "<div class='list'><div class='ln-left'><div class='ln-left-img'><img src="
			+ imgSrc
			+ " alt='sorry' /> <span>点击查看详情</span></div><div class='ln-left-info'><div id='psno'><span>学号：</span><span id='sno'>"
			+ sno
			+ "</span></div><div id='psname'><span>姓名：</span><span id='sname'>"
			+ sname
			+ "</span></div><div><span>专业：</span><span>"
			+ smajor
			+ "</span><span style='margin-left: 40px;'>班级：</span><span>"
			+ sclass
			+ "</span></div></div></div><div class='ln-right'><div class='lr'><span class='btn-select' style='display: "
			+ btnshow
			+ "' onclick='selectStd(this)';>添加该学生</span></div><div class='lr'><span>所属：</span><span>"
			+ belong
			+ "</span></div><div class='lr'><span>注册日期：</span><span>"
			+ regdate + "</span></div></div></div>";

	return $stdlist;
}

/* 回到顶部 */
function backtoTop() {
	window.scrollTo(0, 0);
}

/* 导师选择学生，获得毕设题目列表 */
var currentMdt;
// 添加列表框
function addmdt(e, self) {
	$.ajax({
		type : 'POST',
		url : "./servlet/TeaSelfDesTitle",
		data : {
			ntype : 2
		},
		success : function(data) {
			if (data == 1016) {
				swal("请求失败");
			} else if (data == 1001) {
				swal("您没有多余的毕业设计题目");
				currentMdt.attr("onclick", "selectStd(this)");
			} else {
				$("#mainBox").append(dtlist());
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					$("#mdtlist").append(dlTitle(obj[i].ID, obj[i].DNAME));
				}
				$("#myDesTitleList").addClass("show");
				$("#myDesTitleList").css({
					"display" : "block",
					"top" : e.pageY - 20,
					"left" : e.pageX + 80
				});
			}
		},
		error : function() {
			alert("请求失败，请检查网络或刷新");
		}
	});
}

// 在屏幕中的坐标
var x;
var y;

function mousePress() {
	$("#myDesTitleList").mousemove(function() {
		mouseMove();
	});
}

function mouseMove(event) {
	// 获得当前鼠标坐标
	var e = event || window.event || arguments.callee.caller.arguments[0];
	x = e.pageX;
	y = e.pageY;
	$("#myDesTitleList").mousemove(function() {
		$("#myDesTitleList").css({
			"top" : y - 22,
			"left" : x - 100
		});
	});
}
function moseUp() {
	// 取消mousemuve监听器
	$("#myDesTitleList").unbind("mousemove");
}
/* 鼠标移出后接触mousemove监听器 */
function moseOut() {
	// 取消mousemuve监听器
	$("#myDesTitleList").unbind("mousemove");
}

// 删除列表框
function removemdt() {
	currentMdt.attr("onclick", "selectStd(this)");
	$("#myDesTitleList").remove();
}
var selID;
function mdtlcelect(self) {
	$(self).css("background-color", "#2CAF60");
	$(self).siblings(".mdtlt").css("background-color", "#4FFBB2");
	selID = $(self).attr("id");
}
/* 毕设id和标题 */
function dlTitle(id, title) {
	var $title = '<div id="d' + id
			+ '" class="mdtlt" onclick="mdtlcelect(this)">' + title + '</div>';
	return $title;
}
/* 选择毕设id和标题 确定按钮 */
function mdtconfirm() {
	var currSno = currentMdt.parent().parent(".ln-right").prev(".ln-left")
			.children(".ln-left-info").children("#psno").children("#sno")
			.text();
	var currSname = currentMdt.parent().parent(".ln-right").prev(".ln-left")
			.children(".ln-left-info").children("#psname").children("#sname")
			.text();
	swal({
		title : "提示",
		text : "您要为 -->" + currSname + "<-- 添加毕设课题吗？",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "确定",
		closeOnConfirm : false
	}, function() {
		confirmStd(currSno);
	});
}
function mdtnew() {
	window.location.href = "teaSetDeses.html";
}
function confirmStd(sid) {
	var id = selID.replace('d', '');
	$.ajax({
		type : 'POST',
		url : "./servlet/TeaSelectStd",
		data : {
			ntype : 2,
			stdID : sid,
			desID : id
		},
		success : function(data) {
			if (data == 1016) {
				alert("请求失败");
			} else if (data == 1017) {
				alert("参数错误");
			} else if (data == 1007) {
				alert("更新失败");
			} else {
				window.location.href = "teaStds.html";
			}
		},
		error : function() {
			alert("提交异常");
		}
	});
}
/* 毕设标题列表框架 */
function dtlist() {
	var $warp = '<div id="myDesTitleList"><div id="mdtTitle" onmousedown="mousePress()" onmouseup="moseUp()">可选毕设列表</div>'
			+ '<span id="mdtTitleClose" onclick="removemdt()">'
			+ '<img class="mdtltclose" src="image/images/btn_close.png" width="20" height="20"/></span>'
			+ '<div id="mdtlist"></div><div id="mdtConWarp"><span id="mdtConfirm" onclick="mdtconfirm()">确定</span>'
			+ '<span id="mdtAddMore" onclick="mdtnew()">新增</span></div></div>';
	return $warp;
}