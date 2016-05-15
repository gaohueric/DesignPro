// JavaScript Document
/* 显示选择对话框 */
function showChoice() {
	$("body").append(choice());
	if ($(".inputTnoWarp").hasClass("hide")) {
		$(".inputTnoWarp").removeClass("hide");
	}
	$(".choiceTeaDialog").css({
		"display" : "block"
	});
	$(".inputTnoWarp").addClass("show");
	teacherList();
};

function hideChoice() {
	if ($(".inputTnoWarp").hasClass("show")) {
		$(".inputTnoWarp").removeClass("show");
	}
	$(".inputTnoWarp").addClass("hide");
	if ($(".inputTnoWarp").hasClass("hide")) {
		setTimeout("$('.choiceTeaDialog').css({'display': 'none'})", 250);
	}
}

function choice() {
	var $obj = "<div class='choiceTeaDialog'><div class='inputTnoWarp'>"
			+ "<div class='ctdtitle'>请输入导师教工号</div>"
			+ "<div class='inwarp'><input type='text' class='inbox' onkeyup='stsearchin(this)' />"
			+ "<span class='btnconf' onclick='choiceConfirm()'>确定</span>"
			+ "<div class='tlsearch'></div></div>"
			+ "<div class='close' onclick='hideChoice()'><img src='image/images/btn_close.png' style=' width: 20px; height: 20px;'/>"
			+ "</div></div></div>";
	return $obj;
}
function tealist(tname, tid) {
	var $obj = "<div class='teawarp' onclick='getTid(this)'>"
			+ "<span class='teaname'>" + tname + "</span>"
			+ "<span class='teano'>" + tid + "</span></div>";
	return $obj;
}
function teacherList() {
	$.ajax({
		type : 'POST',
		url : "./servlet/StdTeaNN",
		data : {
			ntype : 1,
		},
		success : function(data) {
			if (data == 1017) {
				swal("请求参数异常");
			} else if (data == 1001) {
				swal("获得列表失败");
			} else {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					$(".tlsearch").append(
							tealist(obj[i].TEA_NAME, obj[i].TEA_ID));
				}
			}
		},
		error : function() {
			swal("连接异常");
		}
	});
}
// 选择导师匹配
function stsearchin(self) {
	var input = $(self).val();
	var list = $(".teawarp");
	if (input != '') {
		$(".tlsearch").css("display", "block");
		for (i = 0; i < list.length; i++) {
			var l = $(list[i]);
			var tno = l.children(".teano").text();
			if (input != tno.substring(0, input.length)) {
				l.css("display", "none");
			} else {
				l.css("display", "block");
			}
		}
	} else {
		$(".tlsearch").css("display", "none");
		for (i = 0; i < list.length; i++) {
			var l = $(list[i]);
			l.css("display", "block");
		}
	}
}
function getTid(self) {
	var tid = $(self).children(".teano").text();
	$(".inbox").val(tid);
	$(self).parent(".tlsearch").css("display", "none");
}
/* 选择导师确认 */
function choiceConfirm() {
	$(self).parent(".tlsearch").css("display", "none");
	var inbox = $(".inbox").val();
	if (inbox != "") {
		$.ajax({
			type : "POST",
			url : "./servlet/ChoiceOperation",
			data : {
				ntype : 1000,
				tno : inbox
			},
			success : function(data) {
				if (data == 1759) {
					swal("已经有导师了，无需选");
				} else if (data == 1006) {
					swal("选择成功!");
					hideChoice();
				} else {
					swal({
						title : "选择失败!!",
					}, function() {
						showChoice();
					});
					hideChoice();
				}
			},
			error : function() {
				swal("服务器连接异常！");
				hideChoice();
			}
		});
	} else {
		swal({
			title : "输入为空，请重新输入！",
		}, function() {
			showChoice();
		});
		hideChoice();
	}
}

function selectTea() {

}
function stsubmit(tno) {
	$.ajax({
		type : 'POST',
		url : "",
		data : {
			ntype : 1,
			tid : tno
		},
		success : function(data) {

		},
		error : function() {
			swal("连接异常");
		}
	});
}

/* 修改密码 */
function changePasswd() {
	$("body").append(cpbox());
}
function cpbox() {
	var $box = '<div class="changePasswdWarp"><div class="changePasswd show">'
			+ '<div class="cptitle">密码修改</div><table class=cptable cellspacing="5">'
			+ '<tr><td>原始密码：</td><td><input id="oldpass" type="password" /></td></tr>'
			+ '<tr><td>新密码：</td><td><input id="newpass" type="password" /></td></tr>'
			+ '<tr><td>重复密码：</td><td><input id="rnewpass" type="password" /></td></tr>'
			+ '</table><div class="cpwho"><input id="cpstd" type="radio" name="who" value="studtnt" checked="checked"/>'
			+ '<span>学生</span><input id="cptea" type="radio" name="who" value="teacher"/><span>导师</span>'
			+ '</div><div class="cpsubmit" onclick="cpsubmit()">提交</div><span class="cpClose" onclick="closeCPPasswd()">'
			+ '<img class="cpcloseimg" src="image/images/btn_close.png" width="20" height="20"/>'
			+ '</span></div></div>';
	return $box;
}
function closeCPPasswd() {
	$(".changePasswdWarp").remove();
}
function cpsubmit() {
	var oldp = $("#oldpass").val();
	if (oldp != '') {
		var newp = $("#newpass").val();
		var rnewp = $("#rnewpass").val();
		var check = document.getElementsByName("who");
		var who;
		for (i = 0; i < check.length; i++) {
			if (check.item(i).checked) {
				who = check.item(i).value;
				break;
			} else {
				continue;
			}
		}
		if (newp != '') {
			if (rnewp != '') {
				if (newp == rnewp) {
					$.ajax({
						type : 'POST',
						url : "./servlet/ChangePass",
						data : {
							ntype : who,
							oldp : oldp,
							newp : newp
						},
						success : function(data) {
							if (data == 1017) {
								swal("参数错误");
							} else if (data == 1007) {
								swal("更新失败");
							} else if (data == 1006) {
								swal("更新成功");
							}
						},
						error : function() {
							swal("修改异常");
						}
					});
				} else {
					alert("两次输入密码不一致");
				}
			} else {
				alert("请确认新密码");
			}
		} else {
			alert("请输入新密码");
		}
	} else {
		alert("请输入旧密码");
	}
}
/* 退出系统 */
function exitSystem() {
	$.ajax({
		type : 'POST',
		url : "./servlet/ChoiceOperation",
		data : {
			ntype : '-33',
		},
		success : function(data) {
			if (data == "1313") {
				window.location.href = "Login.html";
			}
		},
		error : function() {
			swal("请求异常");
		}
	});
}