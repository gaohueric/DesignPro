// JavaScript Document
$(function() {
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
				$("#desNavi").css("background-color", "#353535");

				getThisDes();
				getThisFiles();
			}
		},
		error : function() {
			alert("获得基本信息出错");
		}
	});
});

function getThisDes() {
	$.ajax({
		type : 'POST',
		url : "./GetFileServlet.do",
		data : {
			who : 1,
			type : 1
		},
		success : function(data) {
			var obj = eval("(" + data + ")");
			$("#destable").append(destableItem(obj.name, obj.date, obj.path));
		},
		error : function() {
			alert("抱歉，获取自己的毕业设计信息失败......");
		}
	});
}

function getThisFiles() {
	$.ajax({
		type : 'POST',
		url : "./GetFileServlet.do",
		data : {
			who : 1,
			type : 2
		},
		success : function(data) {
			var obj = eval("(" + data + ")");
			for (i = 0; i < obj.length; i++) {
				$("#datatable").append(dataItem(obj[i].name, obj[i].path));
			}
		},
		error : function() {
			alert("抱歉，获取导师上传文件列表失败......");
		}
	});
}

function desNavi() {
	$(".thesisWarp").css("display", "block");
	$(".materialwarp").css("display", "none");
	$("#desNavi").css("background-color", "#353535");
	$("#dataNavi").css("background-color", "#666666");
}
function dataNavi() {
	$(".thesisWarp").css("display", "none");
	$(".materialwarp").css("display", "block");
	$("#desNavi").css("background-color", "#666666");
	$("#dataNavi").css("background-color", "#353535");
}
function moreInput() {
	$(".fwwarp").append(getInput());
}
function getInput() {
	var str = '<div class="file-warp fw"><div class="filename"></div><div class="addFile">添&nbsp;加</div><input name="files" type="file" class="file" onchange="showfilename(this)"/><div class="filemore" onclick="moreInput()">+</div><div class="filenomore" onclick="removeSelf(this)">-</div></div>';
	return str;
}
function removeSelf(self) {
	var len = $(".fw").length;
	if (len > 1) {
		$(self).parent(".file-warp").remove();
	}
}
// 提交论文
function subDesFile() {
	var form1 = document.getElementById('form1');
	form1.action = "./StdFileUpLoadServlet.do?type=1";
	form1.submit();
	//	
	// alert("提交论文");
	// add_destableitem();
}
function subDataFile() {
	var form2 = document.getElementById('form2');
	form2.action = "./StdFileUpLoadServlet.do?type=2";
	form2.submit();
	// alert("提交资料");
	// add_datatableitem();
}
// 资料新内容
function add_destableitem() {
	$("#destable").append(
			destableItem("bbbbb", "2015-05-21", "http://www.imau.edu.cn"));
}
function add_datatableitem() {
	$("#datatable").append(dataItem("aaaaa", "http://www.imau.edu.cn"));
}
// 毕设表的元组
function destableItem(name, date, ahref) {
	var str = '<tr><td ><div style=" width: 350px; padding: 0 5px 0 5px; overflow: hidden; font-size: 14px;">'
			+ name
			+ '</div></td><td style="text-align:center; font-size: 14px;">'
			+ date
			+ '</td><td style="text-align:center; font-size: 14px;"><a id="aid" href="'
			+ ahref + '">下载</a></td></tr>';
	return str;
}
function dataItem(name, ahref) {
	var str = '<tr ><td ><div style=" width: 500px; padding: 0 5px 0 5px; overflow: hidden;">'
			+ name
			+ '</div></td><td style="text-align:center;"><a id="aid" href="'
			+ ahref + '">下载</a></td></tr>';
	return str;
}
function showfilename(self) {
	$(self).siblings(".filename").html($(self).val());
}