/*学生主机面*/
//全局变量
var tno = 0;
// 页面刷新
window.onload = function() {
	// 请求基本数据
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 1,
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
				$("#stdno").html(obj.account);
				$("#sname").html(obj.name);
				$("#imgHead").attr("src", obj.imgUrl);
				tno = obj.tno;

				baseInfo();
			}
		},
		error : function() {
			alert("error");
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

function baseInfo() {
	$.ajax({
		type : 'POST',
		url : "./servlet/BaseInfo",
		data : {
			ntype : 1,
		},
		success : function(data) {
			if (data == 1017) {
				swal("请求参数错误");
			} else if (data == 1001) {
				swal("查询失败");
			} else {
				var obj = eval("(" + data + ")");
				$("#sid").val(obj.id);
				$("#name").val(obj.name);
				$("#sex").val(obj.sex);
				$("#major").val(obj.major);
				$("#grade").val(obj.grade);
				$("#sclass").val(obj.sclass);
				$("#phone").val(obj.phone);
				$("#QQ").val(obj.QQ);
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

	var id = document.getElementById("sid").value;
	var name = document.getElementById("name").value;
	var sex = document.getElementById("sex").value;
	var major = document.getElementById("major").value;
	var grade = document.getElementById("grade").value;
	var sclass = document.getElementById("sclass").value;
	var phone = document.getElementById("phone").value;
	var qq = document.getElementById("QQ").value;
	var self = document.getElementById("self").value;

	$.ajax({
		type : 'POST',
		url : "./servlet/UpdateInfo",
		data : {
			who : 1,
			name : name,
			sex : sex,
			major : major,
			grade : grade,
			sclass : sclass,
			phone : phone,
			qq : qq,
			self : self
		},
		success : function(data) {
			if (data == 1006) {
				swal({
					title : "更新成功"
				}, function() {
					window.location.href = "stdPerInfo.html";
				});
			} else if (data == 1011)
				swal("更新失败");
		},
		error : function() {
			swal("修改失败");
		}
	});
}

function backmain() {
	window.location.href = "stdMain.html";
}