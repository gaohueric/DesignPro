// JavaScript Document
var loginStatus = 1;
function setStudentStatus() {
	loginStatus = 1;
	$(".stdtitle").css({
		"background-color" : "#CED2F7",
		"transition" : "background-color 0.3s ease"
	});
	$(".stdtitle").siblings().css("background", "#FFFFFF");
	$(".btnRegist").fadeIn(500);
}
function setLecturerStatus() {
	loginStatus = 2;
	$(".teatitle").css({
		"background-color" : "#CED2F7",
		"transition" : "background-color 0.3s ease"
	});
	$(".teatitle").siblings().css("background", "#FFFFFF");
	$(".btnRegist").fadeIn(500);
}
function setAdminStatus() {
	loginStatus = 3;
	$(".admintitle").css({
		"background-color" : "#CED2F7",
		"transition" : "background-color 0.3s ease"
	});
	$(".admintitle").siblings().css("background", "#FFFFFF");
	$(".btnRegist").fadeOut(500);
}
function accfocus(self) {
	if ($(self).val() == "请输入账号")
		$(self).val('');
}
function accblur(self) {
	if ($(self).val() == '')
		$(self).val("请输入账号");
}
/* 登陆 */
function login() {
	var account = document.getElementById("account");
	var passwd = document.getElementById("passwd");
	if (account.value == "请输入账号") {
		swal("账号不能为空");
		account.focus();
	} else if (passwd.value == "") {
		swal("密码不能为空");
		passwd.focus();
	} else {
		verify(loginStatus, account.value, passwd.value);
	}
}
/* 认证与跳转 */
function verify(who, account, passwd) {
	$.ajax({
		type : 'POST',
		url : "./servlet/LoginServlet",
		data : {
			ntype : who,
			acc : account,
			pass : passwd
		},
		success : function(data) {
			if (data == 1003) {
				swal("登陆失败", " ", "error");
			} else {
				swal({
					type : "success",
					title : "登陆成功，2秒后跳转",
					timer : 1750
				});
				if (data == 1012) {
					// 成功跳转界面
					setTimeout(function() {
						window.location.href = "stdMain.html";
					}, 1750);
				} else if (data == 1013) {
					setTimeout(function() {
						window.location.href = "teaMain.html";
					}, 1750);
				} else if (data == 1014) {
					setTimeout(function() {
						window.location.href = "adminMain.html";
					}, 1750);
				}
			}
		},
		error : function() {
			swal("连接异常", "error");
		}
	});
}
/* 注册 */
function regist() {
	if (loginStatus == 1) {
		window.location.href = "regStd.html";
	} else if (loginStatus == 2) {
		window.location.href = "regTea.html";
	} else if (loginStatus == 3) {
		alert(loginStatus);
	}
}
/* 忘记密码 */
function forgetPass() {
	alert("忘记密码");
}
