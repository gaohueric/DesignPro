// JavaScript Document
$(function() {
	$("#r1i").bind("mouseover", function() {
		$("#r1t").show(100);
	}).bind("mouseout", function() {
		$("#r1t").hide(100);
	});
	$("#r2i").bind("mouseover", function() {
		$("#r2t").show(100);
	}).bind("mouseout", function() {
		$("#r2t").hide(100);
	});
	$("#r3i").bind("mouseover", function() {
		$("#r3t").show(100);
	}).bind("mouseout", function() {
		$("#r3t").hide(100);
	});

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
				$("#tname").html(obj.name);
				getInfo();
			}
		},
		error : function(data) {
			alert("抱歉，获取基本信息出错，刷新页面...");
		}
	});
});

function getInfo() {
	$.ajax({
		type : 'POST',
		url : "./TeaSetServlet.do",
		data : {
			type : 1
		},
		success : function(data) {
			var obj = eval("(" + data + ")");
			$("#cycle").val(obj.cycle);
			$("#remind").val(obj.remind);
			$("#date").val(obj.date);
		},
		error : function(data) {
			alert("抱歉，获得设置信息异常");
		}
	});
}

/* 提交设置 */
function modify() {
	alert("修改");
	var cycle = $("#cycle").val();
	var remind = $("#remind").val();
	var date = $("#date").val();
	$.ajax({
		type : 'POST',
		url : "./TeaSetServlet.do",
		data : {
			type : 2,
			cycle : cycle,
			remind : remind,
			date : date
		},
		success : function(data) {
		},
		error : function(data) {
			alert("抱歉，获得设置信息异常");
		}
	});

}

function backMain() {
	window.location.href = "teaMain.html";
}
