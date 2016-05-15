/*学生主机面*/
//全局变量
var startNum = 0;
var tno = 0;
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
				desList();
			}
		},
		error : function() {
			alert("获得基本信息出错");
		}
	});
};

/**
 * 添加列表
 * 
 * @param sn
 *            开始编号
 */
function desList() { // 获得个人信息成功后，请求前12条记录
	$.ajax({
		type : 'POST',
		url : "./servlet/DesList",
		async : false,
		data : {
			start : startNum,
		},
		success : function(data) {
			if (data != 1001) {
				var obj = eval("(" + data + ")");
				for (i = 0; i < obj.length; i++) {
					// 添加列表
					if (obj[i].isselect == 0) {
						$(
								stdMainList(obj[i].name, obj[i].tea_id,
										obj[i].ID, obj[i].description,
										obj[i].std_id, obj[i].date, 'block'))
								.appendTo("#DlistWarp");
					}
					// 1表示改課已课题选择了
					if (obj[i].isselect == 1) {
						$("#DlistWarp").append(
								stdMainList(obj[i].name, obj[i].tea_id,
										obj[i].ID, obj[i].description,
										obj[i].std_id, obj[i].date, 'none'));
					}
				}
				startNum += obj.length;
			} else {
				$(".loading").fadeOut();
			}
		},
		error : function() {
			alert("获取毕设列表出错,刷新页面");
		}
	});
}

// 毕设列表
function stdMainList(name, tea, id, des, belong, date, display) {
	var $listItems = "<div class='list'>" + "<div class='ln-left'>"
			+ "<div class='ll1'>" + "<span class='tit'>课题：</span><span>"
			+ name
			+ "</span>"
			+ "<span class='tit'>导师：</span><span class='teano'>"
			+ tea
			+ "</span>"
			+ "<span class='tit'>编号：</span><span class='did'>"
			+ id
			+ "</span>"
			+ "</div><div class='ll2'>"
			+ "<span class='tit-intr'>介绍：</span>"
			+ "<div class='intr'>"
			+ des
			+ "</div></div></div><div class='ln-right'>"
			+ "<div class='lr'><span class='btn-select' style='display: "
			+ display
			+ ";' onclick='selectDes(this)'>选择课题</span></div>"
			+ "<div class='lr'><span>所属：</span><span>"
			+ belong
			+ "</span></div>"
			+ "<div class='lr'><span>创建日期：</span><span>"
			+ date + "</span></div></div></div>";
	return $listItems;
}

/* 选择毕业设计 */
function selectDes(self) {
	var did = $(self).parent().parent().prev(".ln-left").children(".ll1")
			.children(".did").text();
	var tno = $(self).parent().parent().prev(".ln-left").children(".ll1")
			.children(".teano").text();
	swal({
		title : "确定吗?",
		text : "一旦选择，则不能修改哦，亲~",
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "确定",
		closeOnConfirm : false
	}, function() {
		sdsubmit(did, tno);
	});
}
function sdsubmit(did, tno) {
	$.ajax({
		type : 'POST',
		url : "./servlet/StdSelects",
		data : {
			ntype : 1,
			what : 100, // 100表示选择毕业设计
			desid : did,
			tno : tno
		},
		success : function(data) {
			if (data == 1017) {
				swal("提交失败，参数异常");
			} else if (data == 1007) {
				swal("更新失败，已经有毕业设计了！");
			} else if (data == 1857) {
				swal("只能选择对应导师的毕业设计！");
			} else if (data == 1759) {
				swal("还没有选择导师，请选择导师");
			} else if (data == 1006) {
				swal({
					title : "更新成功"
				}, function() {
					window.location.href = "stdMain.html";
				});
			}
		},
		error : function() {
			swal("连接异常");
		}
	});
}
/* 回到顶部 */
function backtoTop() {
	window.scrollTo(0, 0);
}
