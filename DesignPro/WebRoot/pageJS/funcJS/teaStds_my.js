// JavaScript Document
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
			start : -1000,
		},
		success : function(data) {
			if (data != 1010) {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					$("#DlistWarp").append(
							stdList(obj[i].imgURL, obj[i].id, obj[i].name,
									obj[i].major, obj[i].sclass, 'none',
									obj[i].belong, obj[i].date));
				}
			} else {
				alert("没有更多了");
			}
		},
		error : function() {
			alert("抱歉，获取学生列表出错，请刷新重试...");
		}
	});
}
/* 学生列表信息 */
function stdList(imgSrc, sno, sname, smajor, sclass, btnshow, belong, regdate) {
	var $stdlist = "<div class='list'><div class='ln-left'><div class='ln-left-img'><img src="
			+ imgSrc
			+ " alt='sorry' /> <span>点击查看详情</span></div><div class='ln-left-info'><div><span>学号：</span><span>"
			+ sno
			+ "</span></div><div><span>姓名：</span><span>"
			+ sname
			+ "</span></div><div><span>专业：</span><span>"
			+ smajor
			+ "</span><span style='margin-left: 40px;'>班级：</span><span>"
			+ sclass
			+ "</span></div></div></div><div class='ln-right'><div class='lr'><span class='btn-select' style='display: "
			+ btnshow
			+ "'>添加该学生</span></div><div class='lr'><span>所属：</span><span>"
			+ belong
			+ "</span></div><div class='lr'><span>注册日期：</span><span>"
			+ regdate + "</span></div></div></div>";

	return $stdlist;
}
