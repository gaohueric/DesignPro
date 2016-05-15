// JavaScript Document
/*加载*/
$(function() {
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
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#teano").html(obj.account);
				$("#tname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);
				var tno = obj.account;

				// 获得列表
				designList();
			}
		},
		error : function() {
			alert("抱歉，获取个人信息异常，请刷新重试...");
		}
	});
});

/* 获得导师毕业设计列表信息 */
function designList() {
	$.ajax({
		type : 'POST',
		url : "./servlet/TeaMyDeses",
		data : {
			ntype : 2,
		},
		success : function(data) {
			if (data == 1017) {
				swal("请求参数错误");
			} else if (data == 1001) {
				swal("快制定毕业设计吧！");
			} else {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					if (obj[i].canchoice == 0) {
						$("#DesesWarp").append(
								des(obj[i].name, obj[i].ID, '否',
										obj[i].description, obj[i].date,
										obj[i].std_id));
					}
					if (obj[i].canchoice == 1) {
						$("#DesesWarp").append(
								des(obj[i].name, obj[i].ID, '是',
										obj[i].description, obj[i].date,
										obj[i].std_id));
					}
				}
			}
		},
		error : function() {
			swal("抱歉，获取毕设列表异常，请刷新重试...");
		}
	});
}

function des(title, desno, CanChoice, description, date, stdno) {
	var $des = '<div class="DWarp"><div class="DesTitle">'
			+ title
			+ '</div><span class="DesNoWarp"><span>编号：</span><span class="DesNO">'
			+ desno
			+ '</span></span> <span class="DesCanChoiceWarp"><span>是否可选：</span><span class="DesCanChoice">'
			+ CanChoice
			+ '</span></span><div class="DDesc">'
			+ description
			+ '</div><span class="DDateWarp"><span>日期：</span><span class="date">'
			+ date
			+ '</span></span> <span class="DStdWarp"><span>所属：</span><span>'
			+ stdno
			+ '</span></span><hr style=" border: 1px solid #B1B1B1; float: left; width: 850px; margin: 10px 0 10px 25px;"/></div>';
	return $des;
}