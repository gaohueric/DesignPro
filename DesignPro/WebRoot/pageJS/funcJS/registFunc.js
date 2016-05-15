// JavaScript Document
/*界面*/
$(document).ready(function(e) {
	$(".btn").mouseenter(function() {
		$(this).css("background-color", "#6267DB");
		$(this).css("color", "#FFFFFF");
		$(this).css("cursor", "pointer");
	}).mousedown(function() {
		$(this).css("background-color", "#3436A5");
		$(this).css("color", "#E8E5E8");
	}).mouseup(function() {
		$(this).css("background-color", "#6267DB");
		$(this).css("color", "#FFFFFF");
	}).mouseleave(function() {
		$(this).css("background-color", "#8186FC");
		$(this).css("color", "#E8E5E8");
	});
});

/* 功能 */
/* 学生注册 */
function s_submit() {

	// 获得学生信息
	var account = document.getElementsByName("account").item(this);
	var passwd = document.getElementsByName("passwd").item(this);
	var re_passwd = document.getElementsByName("re_passwd").item(this);
	var name = document.getElementsByName("name").item(this);
	var sex = document.getElementsByName("sex").item(this);
	var major = document.getElementsByName("major").item(this);
	var grade = document.getElementsByName("grade").item(this);
	var sclass = document.getElementsByName("sclass").item(this);
	var phone = document.getElementsByName("phone").item(this);
	if (account.value != "" && passwd.value != "" && re_passwd.value != ""
			&& name.value != "" && sex.value != "" && major.value != ""
			&& grade.value != "" && sclass.value != "" && phone.value != "") {

		// 提交数据
		if (isEqual(passwd.value, re_passwd.value)) {
			$.ajax({
				type : 'POST',
				url : "./servlet/RegServlet",
				data : {
					who : 1,
					account : account.value,
					passwd : passwd.value,
					name : name.value,
					sex : sex.value,
					major : major.value,
					grade : grade.value,
					sclass : sclass.value,
					phone : phone.value
				},
				success : function(data) {
					if (data == 1002) {
						swal({
							type : "success",
							title : "注册成功，2秒后跳转",
							timer : 2000
						});
						// 成功跳转界面
						setTimeout(function() {
							window.location.href = "stdMain.html";
						}, 2000);
					} else if (data == 1005) {
						swal({
							title: "账号存在，注册失败",
							type: "warning",
						});
					}
				},
				error : function() {
					swal("数据提交异常", " ", "error");
				}
			});
		} else {
			swal("两次密码不想等");
		}
	} else {
		swal("提交失败", "检查是否有空填", "error");
	}
}

/* 导师注册 */
function t_submit() {
	// 获得教师信息
	var account = document.getElementsByName("account").item(this);
	var passwd = document.getElementsByName("passwd").item(this);
	var re_passwd = document.getElementsByName("re_passwd").item(this);
	var name = document.getElementsByName("name").item(this);
	var sex = document.getElementsByName("sex").item(this);
	var major = document.getElementsByName("major").item(this);
	var title = document.getElementsByName("title").item(this);
	var phone = document.getElementsByName("phone").item(this);

	if (account.value != "" && passwd.value != "" && re_passwd.value != ""
			&& name.value != "" && sex.value != "" && major.value != ""
			&& title.value != "" && phone.value != "") {
		// 提交数据
		if (isEqual(passwd.value, re_passwd.value)) {
			$.ajax({
						type : 'POST',
						url : "./servlet/RegServlet",
						data : {
							who : 2,
							account : account.value,
							passwd : passwd.value,
							name : name.value,
							sex : sex.value,
							major : major.value,
							title : title.value,
							phone : phone.value
						},
						success : function(data) {
							if (data == 1002) {
								swal({
									type : "success",
									title : "注册成功，2秒后跳转",
									timer : 2000
								});
								// 成功跳转界面
								setTimeout(function() {
									window.location.href = "teaMain.html";
								}, 2000);
							} else if (data == 1005) {
								swal({
									title: "账号存在，注册失败",
									type: "warning",
								});
							}
						},
						error : function() {
							swal("数据提交异常", " ", "error");
						}
					});
		} else {
			swal("两次密码不想等");
		}
	} else {
		swal("提交失败", "检查是否有空填", "error");
	}
}
/*检测account是否存在*/
function blurAcc(self){
	if($(self).val() != ''){
		$("#accimg").css("display","block");
		checkAccount($("#acc").val());
	}else{
		$("#accimg").css("display","none");
	}
}
function checkAccount(account){
	$.ajax({
		type: 'POST',
		url: "./servlet/RegCheckServlet",
		data: {
			ntype: 0, //表示没有区别
			acc: account
		},
		success: function(data){
			if(data == 1017){
				alert("参数异常,检测失败");
			}else if(data == 1018){
				$("#accimg").attr("src","image/images/wrong.png");
			}else if(data == 1019){
				$("#accimg").attr("src","image/images/right.png");
			}
		},
		error: function(){
			swal("连接异常");
		}
	});
}
/*输入pass*/
function inPass(self){
	if($(self).val() != ''){
		$("#passimg").css("display","block");
	}else{
		$("#passimg").css("display","none");
	}
}
/*判断输入密码是否一致*/
function checkPass(self){
	if($(self).val() != ''){
		$("#re_passimg").css("display","block");
		if($(self).val() == $("#pass").val()){
			$("#re_passimg").attr("src","image/images/right.png");
		}else{
			$("#re_passimg").attr("src","image/images/wrong.png");
		}
	}else{
		$("#re_passimg").css("display","none");
	}
}
/*检测姓名是否输入*/
function inName(self){
	if($(self).val() != ''){
		$("#nameimg").css("display","block");
	}else{
		$("#nameimg").css("display","none");
	}
}
/*检测电话是否输入*/
function inPhone(self){
	if($(self).val() != ''){
		$("#phoneimg").css("display","block");
	}else{
		$("#phoneimg").css("display","none");
	}
}
/*检测电话是否输入*/
function inClass(self){
	if($(self).val() != ''){
		$("#classimg").css("display","block");
	}else{
		$("#classimg").css("display","none");
	}
}
/*检测电话是否输入*/
function inGrade(self){
	if($(self).val() != ''){
		$("#gradeimg").css("display","block");
	}else{
		$("#gradeimg").css("display","none");
	}
}
function isEqual(pass, repass) {
	if (pass == repass)
		return true;
	else
		return false;
}