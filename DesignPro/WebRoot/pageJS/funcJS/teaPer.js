/*学生主机面*/
//全局变量
// 页面刷星
window.onload = function() {
	// 请求基本数据
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 2,
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
				$("#titleimg").attr("src", obj.imgUrl);
				$("#teano").html(obj.account);
				$("#tname").html(obj.name);
				$("#imgHead").attr("src", obj.imgUrl);
				baseInfo();
			}
		},
		error : function() {
			alert("页面加载基本数据出错....");
		}
	});

	$("#imgfile").change(function() {
		// 预览
		var url = getFileUrl("imgfile");
		var img = document.getElementById("imgHead");
		img.src = url;

	});
};

/**
 * 从 file 域获取 本地图片 url
 */
function getFileUrl(sourceId) {
	var url;
	if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
		url = window.URL
				.createObjectURL(document.getElementById(sourceId).files
						.item(0));
	} else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
		url = window.URL
				.createObjectURL(document.getElementById(sourceId).files
						.item(0));
	} else {
		// IE
		url = document.getElementById(sourceId).value;
	}
	return url;
}

/**
 * 
 * 设施个人信息内容
 * 
 * @param id
 * @param name
 * @param sex
 * @param major
 * @param grade
 * @param sclass
 * @param phone
 * @param qq
 * @param self
 */
function getInfor(id, name, sex, major, title, phone, qq, wx, self) {
	$("#tid").attr("value", id);
	$("#name").attr("value", name);
	$("#sex").attr("value", sex);
	$("#major").attr("value", major);
	$("#title").attr("value", title);
	$("#phone").attr("value", phone);
	$("#qq").attr("value", qq);
	$("#wx").attr("value", wx);
	$("#self").html(self);
}

function baseInfo() {
	$.ajax({
		type : 'POST',
		url : "./servlet/BaseInfo",
		data : {
			ntype : 2
		},
		success : function(data) {
			if (data == 1017) {
				swal("请求参数错误");
			} else if (data == 1001) {
				swal("查询失败");
			} else {
				var obj = eval("(" + data + ")");
				$("#tid").val(obj.id);
				$("#name").val(obj.name);
				$("#sex").val(obj.sex);
				$("#major").val(obj.major);
				$("#title").val(obj.title);
				$("#phone").val(obj.phone);
				$("#QQ").val(obj.QQ);
				$("#wx").val(obj.WX);
				$("#self").val(obj.self);
			}
		},
		error : function() {
			swal("请求异常");
		}
	});
}

/**
 * 提交信息
 */
function subInfo() {

	var id = document.getElementById("tid").value;
	var name = document.getElementById("name").value;
	var sex = document.getElementById("sex").value;
	var major = document.getElementById("major").value;
	var title = document.getElementById("title").value;
	var phone = document.getElementById("phone").value;
	var qq = document.getElementById("QQ").value;
	var wx = document.getElementById("wx").value;
	var self = document.getElementById("self").value;
	alert(title);
	$.ajax({
		type : 'POST',
		url : "./servlet/UpdateInfo",
		data : {
			who : 2,
			name : name,
			sex : sex,
			major : major,
			title : title,
			phone : phone,
			qq : qq,
			wx : wx,
			self : self
		},
		success : function(data) {
			if (data == 1006) {
				swal({
					title : "更新成功"
				}, function() {
					window.location.href = "teaPerInfo.html";
				});
			} else if (data == 1011)
				swal("更新失败");
		},
		error : function() {
			swal("更新异常");
		}
	});
}

function backmain() {
	window.location.href = "teaMain.html";
}