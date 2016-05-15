// JavaScript Document
// 创建学生对象
function std(sno, sname) {
	this.sno = sno;
	this.sname = sname;
	return this;
}
// 学生数组，记录学生学号姓名
var stdArray = new Array();
var desCount = 0;
$(function() {

	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 2
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
				$("#teano").html(obj.account);
				$("#title_head").attr("src", obj.imgUrl);
				$("#head").attr("src", obj.imgUrl);
				$("#tname").html(obj.name);
				$("#isw-name").html(obj.name);
				$("#isw-pos").html(obj.major);

				$("#desNavi").css("background-color", "#353535");
				getStdNumName();
				getStdDeses();
				getTeaFiles();
			}
		},
		error : function(data) {
			alert("抱歉，获取基本信息出错，请刷新重试...");
		}
	});

});

function getStdDeses() {
	$.ajax({
		type : 'POST',
		url : "./GetFileServlet.do",
		data : {
			who : 2,
			type : 1
		},
		success : function(data) {
			var obj = eval("(" + data + ")");
			for (i = 0; i < obj.length; i++) {
				$("#destable").append(
						destableitem(obj[i].sname, obj[i].name, obj[i].date,
								obj[i].path));
				$("#datatable").append(tableItem(obj[i].name, obj[i].path));
			}
		},
		error : function() {
			alert("抱歉，获取导师上传文件列表失败......");
		}
	});
}

function getTeaFiles() {
	$.ajax({
		type : 'POST',
		url : "./GetFileServlet.do",
		data : {
			who : 2,
			type : 2
		},
		success : function(data) {
			var obj = eval("(" + data + ")");
			for (i = 0; i < obj.length; i++) {
				$("#datatable").append(tableItem(obj[i].name, obj[i].path));
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

function moreDesInput() {
	desCount++;
	$(".fwdwarp").append(desInput());
}
function desInput() {
	var ops;
	for (i = 0; i < stdArray.length; i++) {
		ops += '<option style="border: 1px solid silver;" value="'
				+ stdArray[i].sno + '" >' + stdArray[i].sname + '</option>';
	}

	var str = '<div class="file-warp fwd">'
			+ '<div class="csno" style="width: 150px; top: 2px; left: 480px; position: absolute; ">'
			+ '<select onchange="getSno(this)" style=" background-color: #F4F4F4; float: left; width:100px; color:#2A2C2E; height: 30px; border:1px solid silver; outline: none; font-size:15px;">'
			+ '<option style="border: 1px solid silver;" value="0" selected="selected">选择学生</option>'
			+ ops
			+ '</select></div>'
			+ '<div class="filename" ></div>'
			+ '<div class="addFile">添&nbsp;加</div>'
			+ '<input name="file" type="file" class="file" onchange="showfilename(this)"/>'
			+ '<div class="filemore" onclick="moreDesInput()">+</div>'
			+ '<div class="filenomore" onclick="removeDesSelf(this)">-</div>'
			+ '</div>';
	return str;
}

function removeDesSelf(self) {
	if (desCount > 1) {
		$(self).parent(".file-warp").remove();
		desCount--;
		// 移出后重置所有name，将其排序
		for (i = 0; i < desCount; i++) {
			$($(".fwd")[i]).children(".csno").children(".sno_num").attr("name",
					"sno" + (i + 1));
		}
	}
}
function getSno(self) {
	$(self).parent().siblings(".file").attr("name", $(self).val());
}

// 提交论文
function subDesFile() {
	var form1 = document.getElementById("form1");
	form1.action = "./FileUploadServlet.do?type=1";
	form1.submit();

	// alert("提交论文");
	// add_destableitem();
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

function subDataFile() {
	var form2 = document.getElementById("form2");
	form2.action = "./FileUploadServlet.do?type=2";
	form2.submit();

	// alert("提交资料");
	// add_datatableitem();
}

// 资料新内容
function add_destableitem() {
	$("#destable")
			.append(
					destableitem("王耿", "bbbbb", "2015-05-23",
							"http://www.imau.edu.cn"));
}
function add_datatableitem() {
	$("#datatable").append(tableItem("aaaaa", "http://www.imau.edu.cn"));
}

function destableitem(sname, fname, data, ahref) {
	var str = '<tr><td ><div style="text-align: center; width: 80px; padding: 0 5px 0 5px;">'
			+ sname
			+ '</div></td>'
			+ '<td ><div style=" width: 320px; padding: 0 5px 0 5px; overflow: hidden;">'
			+ fname
			+ '</div></td>'
			+ '<td ><div style=" text-align: center; width: 100px; padding: 0 5px 0 5px;">'
			+ data
			+ '</div></td>'
			+ '<td style="text-align:center;"><a id="aid" href="'
			+ ahref
			+ '">下载</a></td></tr>';
	return str;
}

function tableItem(name, ahref) {
	var str = '<tr ><td ><div style=" width: 500px; padding: 0 5px 0 5px; overflow: hidden;">'
			+ name
			+ '</div></td><td style="text-align:center;"><a id="aid" href="'
			+ ahref + '">下载</a></td></tr>';
	return str;
}

function getStdNumName() {
	$.ajax({
		type : 'POST',
		url : "./TeaStdNums.do",
		success : function(data) {
			var obj = eval("(" + data + ")");
			for (i = 0; i < obj.length; i++) {
				stdArray.push(new std(obj[i].num, obj[i].name));
			}
			moreDesInput(); // 默认加载一条
		},
		error : function() {
			alert("抱歉，获取导师上传文件列表失败......");
		}
	});
}

function showfilename(self) {
	$(self).siblings(".filename").html($(self).val());
}