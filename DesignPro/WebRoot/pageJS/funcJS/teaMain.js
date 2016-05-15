/*导师主机面*/
//全局变量
var startNum = 0;
window.onscroll = function() {
	var top = document.body.scrollTop; // 向下滚动距离
	var height = document.body.scrollHeight; // 正文高度
	var screenHeight = window.screen.availHeight; //
	if (screenHeight + top - 60 >= height) {
		$(".loading").fadeIn();
		setTimeout(function() {
			desList();
		}, 800);
	}
};
// 页面刷新
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
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#teano").html(obj.account);
				$("#tname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);

				// 请求前12条记录
				desList();
			}
		},
		error : function() {
			alert("抱歉，获取基本信息出错，请刷新重试...");
		}
	});
};

/**
 * 添加列表
 * 
 * @param sn
 *            开始编号
 */
function desList() {
	$.ajax({
		type : 'POST',
		url : "./servlet/DesList",
		data : {
			start : startNum,
		},
		success : function(data) {
			if (data != 1001) {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					// 添加列表
					if (obj[i].isselect == 0) {
						$("#DlistWarp").append(
								teaMainList(obj[i].name, obj[i].tea_id,
										obj[i].ID, obj[i].description,
										obj[i].std_id, obj[i].date));
					}
					// 1表示改課已课题选择了
					if (obj[i].isselect == 1) {
						$("#DlistWarp").append(
								teaMainList(obj[i].name, obj[i].tea_id,
										obj[i].ID, obj[i].description,
										obj[i].std_id, obj[i].date));
					}
				}
				startNum += obj.length;
			} else {
				$(".loading").fadeOut();
			}
		},
		error : function() {
			alert("加载信息出错，请刷新重试");
		}
	});

}
/* 回到顶部 */
function backtoTop() {
	window.scrollTo(0, 0);
}

// 毕设列表
function teaMainList(name, tea, id, des, belong, date, display) {
	var bel = belong;
	if (belong == 0) {
		bel = '无';
	}
	var $listItems = "<div class='list'>" + "<div class='ln-left'>"
			+ "<div class='ll1'>" + "<span class='tit'>课题：</span><span>" + name
			+ "</span>" + "<span class='tit'>导师：</span><span>" + tea
			+ "</span>" + "<span class='tit'>编号：</span><span>" + id + "</span>"
			+ "</div><div class='ll2'>" + "<span class='tit-intr'>介绍：</span>"
			+ "<div class='intr'>" + des
			+ "</div></div></div><div class='ln-right'>"
			+ "<div class='lr'></div>"
			+ "<div class='lr'><span>所属：</span><span>" + bel + "</span></div>"
			+ "<div class='lr'><span>创建日期：</span><span>" + date
			+ "</span></div></div></div>";
	return $listItems;
}
