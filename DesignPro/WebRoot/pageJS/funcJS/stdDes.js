/*学生主机面*/
//全局变量
var startNum = 0;
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
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#stdno").html(obj.account);
				$("#sname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);
				tno = obj.tno;

				$.ajax({
					type : 'POST',
					url : "./servlet/ChoiceOperation",
					data : {
						ntype : 1001,
					},
					success : function(data) {
						// alert(data);
						var obj = eval("(" + data + ")");
						$(".Dtitle").html(obj.DNAME);
						$(".Ddes").html(obj.DESCRIPTION);
						$(".tname").html(obj.TEA_NAME);
						$(".tself").html(obj.TEA_SELF);
						$(".phone").html(obj.TEA_PHONE);
						$(".WX").html(obj.TEA_WX);
						$(".QQ").html(obj.TEA_QQ);

						var dlist = obj[0].desList;
						for (i = 0; i < dlist.length; i++) {
							$(".dlist").append(
									design(dlist[i].name, dlist[i].description,
											dlist[i].std_id));
						}
					},
					error : function() {
						alert("获得导师信息异常");
					}
				});
			}
		},
		error : function() {
			alert("获得基本信息出错");
		}
	});

};
