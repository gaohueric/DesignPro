// JavaScript Document
var startnum = 0;
var tscount = 0;
// 学生导师教工号
var tno = 0;
window.onscroll = function() {
	var top = document.body.scrollTop; // 向下滚动距离
	var height = document.body.scrollHeight; // 正文高度
	var screenHeight = window.screen.availHeight; //
	if (screenHeight + top - 60 >= height) {
		$(".loading").fadeIn();
		setTimeout(function() {
			tealists();
		}, 800);
	}
};
$(function() {
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 1
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
				$("#stdno").html(obj.account);
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#sname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);
				tno = obj.tno;
				tealists();
			}
		},
		error : function(data) {
			alert("抱歉，获取基本信息出错，请刷新重试...");
		}
	});
});

// 获得更多列表
function tealists() {
	$.ajax({
		type : 'POST',
		url : "./servlet/TeaList",
		data : {
			start : startnum,
		},
		success : function(data) {
			if (data != 1010) {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					if (obj[i].full == true) {
						$(
								teaList(obj[i].imgURL, obj[i].id, obj[i].name,
										obj[i].major, obj[i].title, 'none',
										obj[i].stdCount, obj[i].date))
								.appendTo("#DlistWarp");
					} else {
						$(
								teaList(obj[i].imgURL, obj[i].id, obj[i].name,
										obj[i].major, obj[i].title, 'block',
										obj[i].stdCount, obj[i].date))
								.appendTo("#DlistWarp");
					}
				}
				startnum += obj.length;
			} else {
				$(".loading").fadeOut();
			}
		},
		error : function() {
			alert("抱歉，获取导师列表出错，请刷新重试...");
		}
	});
}

/* 导师列表信息 */
function teaList(imgSrc, tno, tname, tmajor, ttitle, btnshow, stdcount, regdate) {
	var $tealist = "<div class='list'><div class='ln-left'><div class='ln-left-img' onclick='tinfo(t"
			+ tno
			+ ")'><span>点击查看详情</span><img src="
			+ imgSrc
			+ " alt='sorry' /></div><div class='ln-left-info'><div><span>教工号：</span><span id='t"
			+ tno
			+ "'>"
			+ tno
			+ "</span></div><div><span>姓名：</span><span>"
			+ tname
			+ "</span></div><div><span>专业：</span><span>"
			+ tmajor
			+ "</span><span style='margin-left: 40px;'>职称：</span><span>"
			+ ttitle
			+ "</span></div></div></div><div class='ln-right'><div class='lr'><span class='btn-select' onclick='teaSelect(t"
			+ tno
			+ ")' style='display: "
			+ btnshow
			+ ";'>选择该导师</span></div><div class='lr'><span>所带人数：</span><span>"
			+ stdcount
			+ "</span></div><div class='lr'><span>注册日期：</span><span>"
			+ regdate
			+ "</span></div></div></div></div>";

	return $tealist;
}

function teaSelect(teanum) {
	var teanum = teanum.innerHTML;
	swal({
		title : "确定吗?",
		text : "提交后不可修改，若操作错误，请联系管理员!",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "确定",
		cancelButtonText : "取消",
		closeOnConfirm : false
	}, function() {
		$.ajax({
			type : 'POST',
			url : "./servlet/ChoiceOperation",
			data : {
				ntype : 1000,
				tno : teanum
			},
			success : function(data) {
				if (data == 1759) {
					swal("已经有导师了，无需选");
				} else if (data == 1006) {
					swal({
						title : "操作成功!",
						text : "可以选择该导师的毕业设计课题了!",
						type : "success"
					}, function() {
						window.location.href = "stdTeas.html";
					});
				} else {
					swal("操作失败!", "请重新选择!", "error");
				}
			},
			error : function() {
				swal("提交失败!", "服务器连接异常!", "error");
			}
		});

	});
}

function tinfo(tno) {
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionTNO",
		data : {
			tno : tno.innerHTML,
		},
		success : function(data) {
			if (data == 1002) {
				window.location.href = "stdSTinfo.html";
			} else {
				alert("data != 1002, the data:" + data);
			}
		},
		error : function() {
			alert("请求异常，请刷新重试");
		}
	});
}
/* 回到顶部 */
function backtoTop() {
	window.scrollTo(0, 0);
}
