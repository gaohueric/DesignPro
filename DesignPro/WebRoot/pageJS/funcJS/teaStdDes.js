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
				// 获得学生姓名及其对应的毕业设计题目
				getStdList();
			}
		},
		error : function() {
			alert("抱歉，获取个人信息异常，请刷新重试...");
		}
	});
});

function getStdList() {

	$
			.ajax({
				type : 'POST',
				url : "./servlet/TeaStdWithDes",
				data : {
					ntype : 2,
				},
				success : function(data) {
					if (data == 1001) {
						swal("查询异常！");
					} else {
						var obj = eval("(" + data + ")");
						var $hr = '<hr style="width:625px; margin-left: 12px; border: 1px solid silver;" />';
						for (i = 0; i < obj.length; i++) {
							$(".infoWarp").append($hr);
							$(".infoWarp").append(
									stddesList(obj[i].STD_NAME, obj[i].DNAME));
						}
					}
				},
				error : function() {
					alert("抱歉，获取个人信息异常，请刷新重试...");
				}
			});

}

function stddesList(sname, sdtitle) {
	var $obj = '<div class="infoBox"><div class="sname">' + sname
			+ '</div><div class="sdes">' + sdtitle + '</div></div>';
	return $obj;
}
