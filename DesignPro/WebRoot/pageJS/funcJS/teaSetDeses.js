// JavaScript Document
/*毕设最大数量，有管理员决定*/
var desMaxCount = 0;
/* 设置的毕设数量 */
var desCount = 0;
/* 有关设置毕业设计id，不重复 */
var desidtemp = 100000;
/* 开始编辑的数量 */
var editCount = 0;
/* 设置完成的毕设数量 */
var completeCount = 0;
/* 加载 */
$(function() {
	$.ajax({
		type : 'POST',
		url : "./servlet/SessionInfo",
		data : {
			who : 2,
		},
		success : function(data) {
			if(data == 4004){
				swal({
					text: "登陆超时，请重新登陆"
				},function(){
					window.location.href = "Login.html";
				});
			}else{
			var obj = eval("(" + data + ")");
			$("#title_head").attr("src", obj.imgUrl);
			$("#head").attr("src", obj.imgUrl);
			$("#teano").html(obj.account);
			$("#tname").html(obj.name);
			$("#isw-name").html(obj.name);
			$("#isw-pos").html(obj.major);
			desMaxCount = obj.maxSCount;
			var tno = obj.account;
			// 获得列表
			// designList(tno);
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
	$
			.ajax({
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
							var $hr = '<hr style=" border: 1px solid #B1B1B1; float: left; width: 800px; margin: 5px 25px 5px 25px;"/>';
							if (i != 0) {
								$(".DWarp").append($hr);
							}
							var std = obj[i].std_id;
							if (obj[i].canchoice == 0) {
								if (std == 0)
									std = "";
								$(".DWarp").append(
										defDes('disabled="disabled"', 'des'
												+ obj[i].ID, obj[i].name,
												'checked="checked"', std));
								startCache('des' + obj[i].ID, obj[i].name,
										obj[i].description, 'check', std);
							}
							if (obj[i].canchoice == 1) {
								if (std == 0)
									std = "";
								$(".DWarp").append(
										defDes('disabled="disabled"', 'des'
												+ obj[i].ID, obj[i].name, '', std));
								startCache('des' + obj[i].ID, obj[i].name,
										obj[i].description, 'uncheck', std);
							}
							$("#des" + obj[i].ID).children(".desc").val(
									obj[i].description);
							desCount++;
						}
					}
				},
				error : function() {
					swal("抱歉，获取毕设列表异常，请刷新重试...");
				}
			});
}

/* title取消按钮的缓存 */
var startArrayCache = new Array();
function startCache(did, dtitle, ddes, dcheck, dsno) {
	var startCache = new OptCache1(did, dtitle, ddes, dcheck, dsno);
	startArrayCache.push(startCache);
}
/* 要提交的毕业设计列表 */
var subUpdateArray = new Array();
function subUpdate(did, dtitle, ddes, dcheck, dsno, oldtitle) {
	var canUpdate = new OptCache2(did, dtitle, ddes, dcheck, dsno, oldtitle);
	subUpdateArray.push(canUpdate);
}

/* 管理按钮 */
function manage() {
	$(".delete").fadeIn(300);
	$(".add").fadeIn(300);
	$(".checkbox").fadeIn(200);
	$(".do").fadeIn(200);
	$(".Des").css({
		"box-shadow" : "0 0 1px 1px #C0C0C0",
		"border" : "1px solid silver"
	});
	$(".manage").removeAttr("onclick").html("取消").attr("onclick", "tcancel()");
	$(".titlesubmit").fadeIn(200);
}

/* 标题取消按钮 */
function tcancel() {

	$(".delete").fadeOut(300);
	$(".add").fadeOut(300);
	$(".checkbox").fadeOut(200);
	$(".do").fadeOut(200);
	$(".Des").css({
		"box-shadow" : "0 0 0px 0px #C0C0C0",
		"border" : "0px solid silver"
	});
	$(".manage").removeAttr("onclick").html("管理").attr("onclick", "manage()");
	$(".titlesubmit").fadeOut(200);

	if (startArrayCache.length != 0) {
		for (i = 0; i < startArrayCache.length; i++) {
			var currentItem = $("#" + startArrayCache[i].id);
			var dtitle = currentItem.children(".dtitle");
			var ddes = currentItem.children(".desc");
			var dcheck = currentItem.children(".canchoiceWarp").children(
					"input");
			var dsno = currentItem.children(".belongWarp").children("#stdno");
			// 取消边框
			dtitle.attr("disabled", "disabled").css({
				"border" : "none",
			});
			ddes.attr("disabled", "disabled").css({
				"border" : "none",
			});
			dcheck.attr("disabled", "disabled");
			currentItem.children(".belongWarp").removeClass("belongNew")
					.removeAttr("onclick");

			dtitle.val(startArrayCache[i].title);
			ddes.val(startArrayCache[i].des);
			// 设置操作前是否选中
			if (startArrayCache[i].check == 'check') {
				// 这里要这样写，否则会不显示勾选
				dcheck.prop("checked", "checked");
			} else {
				dcheck.removeAttr("checked");
			}
			dsno.html(startArrayCache[i].sno);
		}
		var objs = $(".Des");
		for (y = 0; y < objs.length; y++) {
			var did = $(objs[y]).attr("id");
			for (z = 0; z < startArrayCache.length; z++) {
				if (did == startArrayCache[z].id)
					break;
				else if (z == startArrayCache.length - 1) {
					$(objs[y]).prev().remove();
					$(objs[y]).remove();
					desCount -= 1;
				}
			}
		}
		// 清空要提交的缓存数组
		while (subUpdateArray.length != 0) {
			subUpdateArray.pop();
		}
		// 清空
		editCount = 0;
		completeCount = 0;
	}
}

/* 提交按钮 */
function completesub() {
	if (subUpdateArray.length > 0) {
		if ((completeCount == editCount) > 0) {
			swal(
					{
						title : "确定吗？",
						text : "一经提交，只能修改不能删除，若想删除，请联系管理员...",
						showCancelButton : true,
						cancelButtonText : "取消",
						showConfirmButton : true,
						confirmButtonColor : "#FF0004",
						confirmButtonText : "确定",
					},
					function() {
						// 将obj转换成json字符串
						var jsonStr = JSON.stringify(subUpdateArray);
						$
								.ajax({
									type : 'POST',
									url : "./servlet/TeaSetDeses",
									data : {
										ntype : 2,
										dataStr : jsonStr
									},
									success : function(data) {
										if (data == 1007) {
											swal("更新失败", "请检查填写是否正确！");
										} else {
											/* 操作成功以后，更新id */
											var obj = eval("(" + data + ")");
											for (i = 0; i < obj.length; i++) {
												for (j = 0; j < subUpdateArray.length; j++) {
													if (obj[i].dname == subUpdateArray[j].title) {
														setDesWarpID(
																subUpdateArray[j].id,
																obj[i].did);
														newInfoToStartArray(obj[i].did);
														$("#des" + obj[i].did)
																.children(
																		".checkbox")
																.attr(
																		"disabled",
																		"disabled")
																.remove(
																		"checked");
														break;
													}
												}
											}
											while (subUpdateArray.length != 0) {
												subUpdateArray.pop();
											}
										}
									},
									error : function() {
										swal("提交异常异常，请检查网络或重新登陆");
									}
								});

						$(".delete").fadeOut(300);
						$(".add").fadeOut(300);
						$(".checkbox").fadeOut(200);
						$(".do").fadeOut(200);
						$(".Des").css({
							"box-shadow" : "0 0 0px 0px #C0C0C0",
							"border" : "0px solid silver"
						});
						$(".manage").removeAttr("onclick").html("管理").attr(
								"onclick", "manage()");
						$(".titlesubmit").fadeOut(200);
					});
			$(".cplEdit").fadeOut(200);
		} else {
			swal("请完成所有的操作再提交!");
		}
	} else if (subUpdateArray.length == 0) {
		swal("未有任何操作");
	}
}

/* 替换原有的id */
function setDesWarpID(oldID, newID) {
	$("#" + oldID).attr("id", "des" + newID);
}

/* 将更新后的信息添加到开始时的默认数组 */
function newInfoToStartArray(newID) {

	var id = $("#des" + newID);
	var title = id.children(".dtitle").val();
	var des = id.children(".desc").val();
	var check = id.children(".canchoiceWarp").children("input");
	var sno = id.children(".belongWarp").children("#stdno").text();

	/* check的值 */
	if (check.attr("checked"))
		check = 'check';
	else
		check = 'uncheck';
	/* 这里要注意变量，如果与以上结果用到相同变量（如：i），则会出现异常 */
	for (o = 0; o < startArrayCache.length; o++) {
		// 如果startArrayCache中存在，则更新, 否则则添加新的元素到数组
		if (id.attr("id") == startArrayCache[o].id) {
			var old = new OptCache1(id.attr("id"), title, des, check, sno);
			startArrayCache.splice(o, 1, old);
			return;
		} else if (o == (startArrayCache.length - 1)) {
			startCache(id.attr("id"), title, des, check, sno);
			return;
		}
	}
}

/* 添加按钮 */
function add() {
	var $hr = '<hr style=" border: 1px solid #B1B1B1; float: left; width: 800px; margin: 5px 25px 5px 25px;"/>';
	if (desCount < desMaxCount) {
		if (desCount != 0) {
			$(".DWarp").append($hr);
		}
		$(".DWarp").append(setdes('des' + desidtemp));
		manage();
		desCount += 1;
		desidtemp += 1;
	} else {
		swal("已经达到最大数量，不可继续添加!");
	}
}

/* 删除按钮 */
function del() {
	if (desCount > 0) {
		for (i = 0; i < desArray.length; i++) {
			$("#" + desArray[i]).prev().remove();
			$(".DWarp").children().remove("#" + desArray[i]);
			desCount--;
		}
		/* 清空数组，防止出错 */
		while (desArray.length != 0) {
			desArray.pop();
		}
	} else {
		swal("没有设置任何毕设哟~~");
	}
}

var desArray = new Array();
/* 批量删除checkbox */
function mutilDel(self) {
	var s = $(self).parent().parent().attr("id");
	/* 判断是否选中,必须这样写 */
	if ($(self).attr("checked")) {
		for (i = 0; i < desArray.length; i++) {
			/* 判断是哪一个未被选中 */
			if (desArray[i] == s) {
				// 删除当前位置对象
				desArray.splice(i, 1);
			}
		}
		$(self).removeAttr("checked");
	} else {
		desArray.push(s);
		$(self).attr("checked", 'true');
	}
}

/* 设置毕设 */
function setdes(desid) {
	var $setdes = '<div id="'
			+ desid
			+ '" class="Des"><div style="width: 40px; height: 100px; float:left;">'
			+ '<input class="checkbox" type="checkbox" onclick="mutilDel(this)" style="display: none;"/></div>'
			+ '<span>题目</span><input type="text" class="dtitle" disabled="disabled" />'
			+ '<div id="do" class="do" onclick="edit(this)">编辑</div>'
			+ '<div id="complete" class="cplEdit" onclick="completeEdit(this)">完成</div>'
			+ '<textarea class="desc" disabled="disabled" onfocus="descfocus(this)" onblur="descblur(this)">描述......</textarea>'
			+ '<div class="canchoiceWarp"><input type="checkbox" onclick="checkCanChoice(this)" disabled="disabled" />不可被选</div>'
			+ '<div class="belongWarp"><span style="font-size: 15px; float: left;">所属：</span>'
			+ '<span id="stdno" style="font-size: 15px; float: left;"></span></div></div>';
	return $setdes;
}
/* 加载导师的毕设 */
function defDes(candelete, desid, dtitle, ischeck, dsno) {
	var $des = '<div id="'
			+ desid
			+ '" class="Des"><div style="width: 40px; height: 100px; float:left;">'
			+ '<input class="checkbox" type="checkbox" onclick="mutilDel(this)" style="display: none;" '
			+ candelete
			+ '/></div>'
			+ '<span>题目</span><input type="text" class="dtitle" disabled="disabled" value="'
			+ dtitle
			+ '"/>'
			+ '<div id="do" class="do" onclick="edit(this)">编辑</div>'
			+ '<div id="complete" class="cplEdit" onclick="completeEdit(this)">完成</div>'
			+ '<textarea class="desc" disabled="disabled"></textarea>'
			+ '<div class="canchoiceWarp"><input type="checkbox" onclick="checkCanChoice(this)" disabled="disabled" '
			+ ischeck
			+ '/>不可被选</div>'
			+ '<div class="belongWarp"><span style="font-size: 15px; float: left;">所属：</span>'
			+ '<span id="stdno" style="font-size: 15px; float: left;">' + dsno
			+ '</span></div></div>';
	return $des;
}

/* 操作前缓存 */
function OptCache1(did, dtitle, ddes, dcheck, dsno) {
	this.id = did;
	this.title = dtitle;
	this.des = ddes;
	this.check = dcheck;
	this.sno = dsno;
}
function OptCache2(did, dtitle, ddes, dcheck, dsno, oldtitle) {
	this.id = did;
	this.title = dtitle;
	this.des = ddes;
	this.check = dcheck;
	this.sno = dsno;
	this.oldtitle = oldtitle;
}
/* 操作前缓存数组 */
var optCacheArray = new Array();

/* 编辑按钮 */
function edit(self) {
	var itself = $(self);
	var did = itself.parent().attr("id");
	var dtitle = itself.siblings(".dtitle");
	var ddes = itself.siblings(".desc");
	var dcheck = itself.siblings(".canchoiceWarp").children("input");
	var dsno = itself.siblings(".belongWarp").children("#stdno").text();

	itself.html("取消").attr("onclick", "cancel(this)");
	itself.siblings(".cplEdit").fadeIn(200);
	dtitle.removeAttr("disabled").css({
		"border" : "1px solid silver",
	});
	ddes.removeAttr("disabled").css({
		"border" : "1px solid silver",
	});

	// 如果startArrayCache.length == 0，则说明是新增数据，导师还没有制定
	if(startArrayCache.length != 0){
		/* 遍历初始数组中是否有该元素，若没有，则说明是新增数据，提交学号是可以修改的 */
		for (x = 0; x < startArrayCache.length; x++) {
			if (did == startArrayCache[x].id) {
				if (startArrayCache[x].sno == "") {
					dcheck.removeAttr("disabled");
					itself.siblings(".belongWarp").addClass("belongNew").attr(
							"onclick", "choiceStd(this)");
				}
				break;
			} else if (x == startArrayCache.length - 1) {
				dcheck.removeAttr("disabled");
				itself.siblings(".belongWarp").addClass("belongNew").attr(
						"onclick", "choiceStd(this)");
			}
		}
	}else{
			dcheck.removeAttr("disabled");
			itself.siblings(".belongWarp").addClass("belongNew").attr(
					"onclick", "choiceStd(this)");
	}

	var opt;
	if (dcheck.attr("checked"))
		opt = new OptCache1(did, dtitle.val(), ddes.val(), 'check', dsno);
	else
		opt = new OptCache1(did, dtitle.val(), ddes.val(), 'uncheck', dsno);
	for (i = 0; i < optCacheArray.length; i++) {
		if (did == optCacheArray[i].id) {
			editCount++;
			optCacheArray.splice(i, 1, opt);
			return;
		}
	}
	editCount++;
	optCacheArray.push(opt);
}
/* 取消按钮 */
function cancel(self) {
	var itself = $(self);
	var did = itself.parent().attr("id");
	var dtitle = itself.siblings(".dtitle");
	var ddes = itself.siblings(".desc");
	var dcheck = itself.siblings(".canchoiceWarp").children("input");
	var dsno = itself.siblings(".belongWarp").children("#stdno");

	for (i = 0; i < optCacheArray.length; i++) {
		if (did == optCacheArray[i].id) {
			dtitle.val(optCacheArray[i].title);
			ddes.val(optCacheArray[i].des);
			// 设置操作前是否选中
			if (optCacheArray[i].check == 'check') {
				// 这里要这样写，否则会不显示勾选
				dcheck.prop("checked", "checked");
			} else {
				dcheck.removeAttr("checked");
			}
			dsno.html(optCacheArray[i].sno);
		}
	}

	itself.html("编辑").attr("onclick", "edit(this)");
	itself.siblings(".cplEdit").fadeOut(200);
	dtitle.attr("disabled", "disabled").css({
		"border" : "none",
	});
	ddes.attr("disabled", "disabled").css({
		"border" : "none",
	});
	dcheck.attr("disabled", "disabled");
	itself.siblings(".belongWarp").removeClass("belongNew").removeAttr(
			"onclick");
	editCount--;
}

/* 完成编辑按钮 */
function completeEdit(self) {
	var itself = $(self);
	var did = itself.parent().attr("id");
	var dcheck = itself.siblings(".canchoiceWarp").children("input");
	if (itself.siblings(".dtitle").val() != "") {
		if (itself.siblings(".desc").val() != "描述......") {
			// 如果该毕业设计不存在，则可以提交
			if (dnameIsNotExist(itself)) {
				itself.prev(".do").html("编辑").attr("onclick", "edit(this)");
				itself.fadeOut(200);
				itself.siblings(".dtitle").attr("disabled", "disabled").css({
					"border" : "none",
				});
				itself.siblings(".desc").attr("disabled", "disabled").css({
					"border" : "none",
				});
				itself.siblings(".canchoiceWarp").children("input").attr(
						"disabled", "disabled");
				itself.siblings(".belongWarp").removeClass("belongNew")
						.removeAttr("onclick");
				completeCount++;
				for (i = 0; i < optCacheArray.length; i++) {
					if (did == optCacheArray[i].id) {
						/* 将其添加到即将要提交的列表中 */
						var oldtitle = "";
						for (q = 0; q < startArrayCache.length; q++) {
							if (did == startArrayCache[q].id) {
								oldtitle = startArrayCache[q].title;
								break;
							}
						}
						if (subUpdateArray.length != 0) { // 如果subUpdateArray中有值存在
							for (j = 0; j < subUpdateArray.length; j++) {
								if (did == subUpdateArray[j].id) {
									var obj;
									if (dcheck.attr("checked")) {
										obj = new OptCache2(did, itself
												.siblings(".dtitle").val(),
												itself.siblings(".desc").val(),
												'check', itself.siblings(
														".belongWarp")
														.children("#stdno")
														.text(), oldtitle);
									} else {
										obj = new OptCache2(did, itself
												.siblings(".dtitle").val(),
												itself.siblings(".desc").val(),
												'uncheck', itself.siblings(
														".belongWarp")
														.children("#stdno")
														.text(), oldtitle);
									}
									subUpdateArray.splice(j, 1, obj);
									break;
								} else if (j == subUpdateArray.length - 1) {
									if (dcheck.attr("checked")) {
										subUpdate(did, itself.siblings(
												".dtitle").val(), itself
												.siblings(".desc").val(),
												'check', itself.siblings(
														".belongWarp")
														.children("#stdno")
														.text(), oldtitle);
									} else {
										subUpdate(did, itself.siblings(
												".dtitle").val(), itself
												.siblings(".desc").val(),
												'uncheck', itself.siblings(
														".belongWarp")
														.children("#stdno")
														.text(), oldtitle);
									}
								}
							}
						} else {
							if (dcheck.attr("checked")) {
								subUpdate(did,
										itself.siblings(".dtitle").val(),
										itself.siblings(".desc").val(),
										'check', itself.siblings(".belongWarp")
												.children("#stdno").text(),
										oldtitle);
							} else {
								subUpdate(did,
										itself.siblings(".dtitle").val(),
										itself.siblings(".desc").val(),
										'uncheck', itself.siblings(
												".belongWarp").children(
												"#stdno").text(), oldtitle);
							}
						}
						/* 从缓存列表中删除操作完成的对象 */
						optCacheArray.splice(i, 1);
					}
				}
			}
		} else {
			swal("必须对内容进行描述");
		}
	} else {
		swal("题目不能为空");
	}
}

/* 判断dname不存在 */
function dnameIsNotExist(itself) {
	var did = itself.parent().attr("id");
	var instart = false;
	for (m = 0; m < startArrayCache.length; m++) {
		// 如果修改的是已经存在的毕业设计，则不需要遍历页面加载时的缓存数组
		if (did == startArrayCache[m].id) {
			instart = true;
			break;
		}
	}
	var dname = itself.siblings(".dtitle").val();
	var desc = itself.siblings(".desc").val();
	// 这样做是确保可以修改已经存在的毕业设计
	if (!instart) {
		if(isNameNotChange(did, dname)){
			return true;
		}else{
			if(selectStartTemp(dname)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}
			if(selectUpTemp(dname)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}
			if(selectByNet(dname)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}else{
				return true;
			}
		}
	}else{
		var dtitle = itself.siblings(".dtitle").val();
		var desc = itself.siblings(".desc").val();
		if(isNameNotChange(did, dtitle)){
			return true;
		}else{
			// 如果存在开始缓存中，不查询提交缓存和数据库
			if(selectStartTemp(dtitle)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}
			// 如果存在提交缓存中，不查询数据库
			if(selectUpTemp(dtitle)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}
			// 如过存在数据库中
			if(selectByNet(dtitle)){
				swal("该题目已经存在，无法再次创建！");
				return false;
			}else{
				return true;
			}
		}
	}
}
/* 是否修改题目，若题目改变，则需要查询题目是否存在，若不改变，则不需要查询 */
function isNameNotChange(did, dtitle){
	var tempDesInStart;
	// 给tempDesInStart赋值
	for (m = 0; m < optCacheArray.length; m++) {
		if (did == optCacheArray[m].id) {
			tempDesInStart = optCacheArray[m];
			break;
		}
	}
	if (dtitle == tempDesInStart.title) {
		return true;
	} else if (dtitle != tempDesInStart.title) {
		return false;
	}
}
/* 查询开始缓存中元素是否存在，返回true是存在 */
function selectStartTemp(dtitle){
	// 遍历已经存在的缓存数组
	for (m = 0; m < startArrayCache.length; m++) {
		var title = startArrayCache[m].title;
		// alert("dtitle: "+ dtitle + "___________________
		// startArrayCache[m].title:"+startArrayCache[m].title);
		if (dtitle == title) {
			return true;
		}
	}
	return false;
}
/* 查询将要提交的缓存中元素是否存在，返回true是存在 */
function selectUpTemp(dtitle){
	// 遍历将要提交的缓存数组
	for (m = 0; m < subUpdateArray.length; m++) {
		var title = subUpdateArray[m].title;
		// alert("dtitle: "+ dtitle + "___________________
		// subUpdateArray[m].title:"+subUpdateArray[m].title);
		if (dtitle == title) {
			return true;
		}
	}
	return false;
}
/* 通过网络查询数据库，返回true是存在或网络异常 */
function selectByNet(dname){
	var flag = false;
	$.ajax({
		type : 'POST',
		url : "./servlet/TeaSetDeses",
		async : false,
		data : {
			ntype : 2,
			dtitle : dname,
		},
		success : function(data) {
			if (data == 1018) {
				flag = true;
			} else if (data == 1019) {
				flag = false;
			}
		},
		error : function() {
			swal("网络异常");
			flag = true;
		}
	});
	return flag;
}

/* 每个毕设对象的'是否可选'状态 */
function checkCanChoice(self) {
	var itself = $(self);
	if (itself.attr("checked")) {
		itself.removeAttr("checked");
	} else {
		itself.attr("checked", "checked");
	}
}
/* 获取毕设 */
function deses(title, desc, canchoice, stdno) {

	var check = "";
	if (canchoice == 0) {
		check = 'checked="checked"';
	}
	var $des = '<div class="Des">'
			+ '<div style="width: 40px; height: 100px; float:left;">'
			+ '<input class="checkbox" type="checkbox" style="display: none;"/></div>'
			+ '<span>题目</span>'
			+ '<input type="text" class="dtitle" disabled="disabled" value='
			+ title
			+ '/>'
			+ '<div id="do" class="do" onclick="edit(this)">编辑</div>'
			+ '<div id="complete" class="cplEdit" onclick="completeEdit(this)">完成</div>'
			+ '<textarea class="desc" disabled="disabled">'
			+ desc
			+ '</textarea>'
			+ '<div class="canchoiceWarp"><input type="checkbox" disabled="disabled" '
			+ check
			+ '/>不可被选</div>'
			+ '<div class="belongWarp"><span style="font-size: 15px; float: left;">所属：</span>'
			+ '<span id="stdno" style="font-size: 15px; float: left;">' + stdno
			+ '</span></div>';
	'</div>';
	return $des;
}

/* 确定当前设置那个学生 */
var currentObj;
function setCurrentItem(obj) {
	currentObj = obj;
}
function getCurrentItem() {
	return currentObj;
}

/* 打开对话框 */
function choiceStd(obj) {
	setCurrentItem(obj);
	$(".getStdNo").css("display", "block").children(".boxWarp").css("display",
			"block").addClass("show");
}
/* 关闭对话框 */
function closeChoiceStd() {
	$(".getstdlistBox").hide(100, function() {
		$(".boxWarp").hide(100, function() {
			$(this).removeClass("show");
			$(this).parent().hide();
		});
	});
}
/* 确定 */
function csubmit() {
	$(".getstdlistBox").hide(
			100,
			function() {
				$(".boxWarp").hide(
						100,
						function() {
							$(this).removeClass("show");
							$(this).parent().hide();
							var sno = $("#inputSno").val();
							$(getCurrentItem()).children("#stdno").html(
									$("#inputSno").val());
							if (sno != "") {
								$(getCurrentItem()).prev().children().attr(
										"checked", "checked").attr("disabled",
										"disabled");
							} else {
								$(getCurrentItem()).prev().children()
										.removeAttr("checked").removeAttr(
												"disabled");
							}
							$("#inputSno").val("");
						});
			});
}

/* 显示和隐藏学生列表框 */
function showStdNoList() {
	$.ajax({
		type: 'POST',
		url: "./servlet/TeaStdNameSno",
		data: {
			ntype: 2,
			req: 'name_no'
		},
		success: function(data){
			if(data == 1017){
				swal("参数错误");
			}else if(data == 1001){
				swal("查询失败");
			}else{
				var obj = eval("("+data+")");
				for(i = 0; i < obj.length; i++){
					$(".stdlistwarp").append(snolist(obj[i].STD_NAME, obj[i].STD_ID));
				}
				$(".getstdlistBox").show(200, function() {
					$(this).addClass("show");
				});
			}
		},
		error: function(){
			alert("请求异常");
		}
	});
}
function hideStdNoList() {
	$(".getstdlistBox").hide(200, function() {
		$(this).removeClass("hide");
	});
}
function snolist(sname, sno){
	var $sno = 	'<div class="stdItem" ondblclick="dbcgetSno(this);">'+
						'<div class="stdItemTitle">'+sname+'</div><div class="stdItemSno">'+sno+'</div>'+
						'<div class="stdItemAdd" onclick="getSno(this)">+</div></div>';
	return $sno;
}
/* 获得sno */
function dbcgetSno(curritem) {
	$("#inputSno").val($(curritem).children(".stdItemSno").text());
}
function getSno(curritem) {
	$("#inputSno").val($(curritem).siblings(".stdItemSno").text());
}
// 匹配
function searchin(self){
	var input = $(self).val();
	var list = $(".stdItem");
	if(input != ''){
		for(i = 0; i < list.length; i++){
			var l = $(list[i]);
			var sno = l.children(".stdItemSno").text();
			if(input != sno.substring(0, input.length)){
				l.css("display","none");
			}else{
				l.css("display","block");
			}
		}
	}else{
		for(i = 0; i < list.length; i++){
			var l = $(list[i]);
			l.css("display","block");
		}
	}
}
// 相关清空操作
function isfocus(self){
	var o = $(self);
	if(o.val() == "请输入学号"){
		o.val('');
	}
}
function isblur(self){
	var o = $(self);
	if(o.val() == ''){
		o.val("请输入学号");
	}
}
function descfocus(self){
	var o = $(self);
	if(o.val() == "描述......"){
		o.val('');
	}
}
function descblur(self){
	var o = $(self);
	if(o.val() == ''){
		o.val("描述......");
	}
}