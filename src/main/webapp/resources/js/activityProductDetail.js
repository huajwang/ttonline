/**
 * 
 */

var userId = Request["userId"];// 分享用户ID
var propertyId = "0";

/**
 * 减数
 */
function reduceNum() {
	var num = $(".injorj").val();
	num = parseInt(num) - 1;
	if (num >= 1) {
		$(".injorj").val(num);
		$(".aodcenter").text(num);
	}
}
/**
 * 加数
 */
function addNum() {
	var num = $(".injorj").val();
	num = parseInt(num) + 1;
	// $("#ProductNum").text(num);
	$(".injorj").val(num);
	$(".aodcenter").text(num);
}

/**
 * 不重复将属性放入属性容器数组 by rd
 */
function judgeStrArr(_arr, _property) {
	var len = _arr.length;
	for (var i = 0; i < len; i++) {
		if (_arr[i] == _property)
			return;
	}
	if (_property != null && _property != '') {
		_arr.push(_property);
	}
}

/**
 * 显示所有属性
 */
function showAllPropertys() {
	for (var i = 0; i < colorArr.length; i++)
		$("#goodsColor").append(
				"<div id='gcolor" + i + "'class='ys axuan'>" + colorArr[i]
						+ "</div>");
	for (var i = 0; i < sizeArr.length; i++)
		$("#goodsSize").append(
				"<div id='gsize" + i + "'class='ys axuan'>" + sizeArr[i]
						+ "</div>");
	for (var i = 0; i < styleArr.length; i++)
		$("#goodsStyle").append(
				"<div id='gstyle" + i + "'class='ys axuan'>" + styleArr[i]
						+ "</div>");
	setGoodsType();
}

/**
 * 获取商品属性组合方式 设颜色权值为1，尺码权值为2，风格权值为4
 * 则存在7种组合方式：颜色=1；尺码=2；风格=4；颜色+尺码=3；颜色+风格=5；尺码+风格=6；颜色+尺码+风格=7
 */
function setGoodsType() {
	_goodsType = 0;
	if (colorArr.length > 0)
		_goodsType += 1;
	if (sizeArr.length > 0)
		_goodsType += 2;
	if (styleArr.length > 0)
		_goodsType += 4;
	_unpickType = _goodsType;
}

/**
 * 根据已选中的属性设置按钮可否点击
 * 
 */
function ableColor() {
	var json = eval(property);
	$("#goodsColor .axuan").attr("class", "ys unxuanz");
	for (var i = 0; i < json.length; i++) {
		// mySize=''和myStyle=''说明2个属性未选定
		if ((mySize == '' || mySize == json[i]['size'])
				&& (myStyle == '' || myStyle == json[i]['style'])) {
			for (var e = 0; e < colorArr.length; e++) {
				if ($("#gcolor" + e).text() == json[i]['color']
						&& $("#gcolor" + e).attr("class") != 'ys xuanz')
					$("#gcolor" + e).attr("class", "ys axuan");
			}
		}
	}
}

function ableSize() {
	var json = eval(property);
	$("#goodsSize .axuan").attr("class", "ys unxuanz");
	for (var i = 0; i < json.length; i++) {
		if ((myColor == '' || myColor == json[i]['color'])
				&& (myStyle == '' || myStyle == json[i]['style'])) {
			for (var e = 0; e < sizeArr.length; e++) {
				if ($("#gsize" + e).text() == json[i]['size']
						&& $("#gsize" + e).attr("class") != 'ys xuanz')
					$("#gsize" + e).attr("class", "ys axuan");
			}
		}
	}
}

function ableStyle() {
	var json = eval(property);
	$("#goodsStyle .axuan").attr("class", "ys unxuanz");
	for (var i = 0; i < json.length; i++) {
		if ((myColor == '' || myColor == json[i]['color'])
				&& (mySize == '' || mySize == json[i]['size'])) {
			for (var e = 0; e < styleArr.length; e++) {
				if ($("#gstyle" + e).text() == json[i]['style']
						&& $("#gstyle" + e).attr("class") != 'ys xuanz')
					$("#gstyle" + e).attr("class", "ys axuan");
			}
		}
	}
}

// 确认商品
function sureGoods(_index) {
	var json = eval(property);
	propertyId = json[_index]['id'];
	var iconUrl = json[_index]['iconUrl'];
	if (iconUrl != undefined) {
		$("#imgProperty").attr("src",
				"http://toyke.oss-cn-hangzhou.aliyuncs.com/" + iconUrl);
	}
}

// 判断可选属性按钮
function isUseful() {
	ableColor();
	ableSize();
	ableStyle();
	$("#xuanze").html("");
	$("#xuanze").append(myColor + " " + mySize + " " + myStyle);
	if (_unpickType <= 0) {
		var json = eval(property);
		for (var i = 0; i < json.length; i++) {
			switch (_goodsType) {
			case 1:
				if (json[i]['color'] == myColor) {
					sureGoods(i);
				}
				break;
			case 2:
				if (json[i]['size'] == mySize) {
					sureGoods(i);
				}
				break;
			case 3:
				if (json[i]['color'] == myColor && json[i]['size'] == mySize) {
					sureGoods(i);
				}
				break;
			case 4:
				if (json[i]['style'] == myStyle) {
					sureGoods(i);
				}
				break;
			case 5:
				if (json[i]['color'] == myColor && json[i]['style'] == myStyle) {
					sureGoods(i);
				}
				break;
			case 6:
				if (json[i]['size'] == mySize && json[i]['style'] == myStyle) {
					sureGoods(i);
				}
				break;
			case 7:
				if (json[i]['color'] == myColor && json[i]['size'] == mySize
						&& json[i]['style'] == myStyle) {
					sureGoods(i);
				}
				break;
			}
		}
	}
}

/**
 * 选择颜色
 */
function pickColor(_this) {
	// 第一次选择颜色，则将颜色踢出未选择列表
	if (myColor == '')
		_unpickType -= 1;
	// 点击已经被选中的按钮，则为取消选中
	if (myColor == $(_this).text()) {
		$(_this).attr("class", "ys");
		_unpickType += 1;// 未选中属性加上颜色
		myColor = '';
		isUseful();
	} else {
		myColor = $(_this).text();
		$("#goodsColor .ys.xuanz").attr("class", "ys");// 原本选中的修改为未选中
		$(_this).attr("class", "ys xuanz");// 设置选中的按钮的样式
		$("#xuanze").html("");// 修改商品页面属性内容
		var text = $(_this).text();
		$("#xuanze").append(text);
		isUseful();
	}
}

/**
 * 选择尺码
 */
function pickSize(_this) {
	// 第一次选择尺码，则将尺码踢出未选择列表
	if (mySize == '')
		_unpickType -= 2;
	// 点击已经被选中的按钮，则为取消选中
	if (mySize == $(_this).text()) {
		$(_this).attr("class", "ys");
		_unpickType += 2;// 未选中属性加上尺码
		mySize = '';
		isUseful();
	} else {
		mySize = $(_this).text();
		$("#goodsSize .ys.xuanz").attr("class", "ys");// 原本选中的修改为未选中
		$(_this).attr("class", "ys xuanz");// 设置选中的按钮的样式
		$("#xuanze").html("");// 修改商品页面属性内容
		var text = $(_this).text();
		$("#xuanze").append(text);
		isUseful();
	}
}

/**
 * 选择风格
 */
function pickStyle(_this) {
	// 第一次选择风格，则将风格踢出未选择列表
	if (myStyle == '')
		_unpickType -= 4;
	// 点击已经被选中的按钮，则为取消选中
	if (myStyle == $(_this).text()) {
		$(_this).attr("class", "ys");
		_unpickType += 4;// 未选中属性加上风格
		myStyle = '';
		isUseful();
	} else {
		myStyle = $(_this).text();
		$("#goodsStyle .ys.xuanz").attr("class", "ys");// 原本选中的修改为未选中
		$(_this).attr("class", "ys xuanz");// 设置选中的按钮的样式
		$("#xuanze").html("");// 修改商品页面属性内容
		var text = $(_this).text();
		$("#xuanze").append(text);
		isUseful();
	}
}

/**
 * 选择商品颜色
 */
function setColor(_this) {
	propertyId = "0";
	$("#goodsColor .ys.xuanz").attr("class", "ys");
	$(_this).attr("class", "ys xuanz");
	$("#xuanze").html("");
	var text = $(_this).text();
	$("#xuanze").append(text);
	var json = eval(property);
	if (_type >= 2) {
		setSize(text);
	}
	setPrice();
}
/**
 * 选择商品尺寸
 * 
 * @param color
 */
function setSize(color) {
	propertyId = "0";
	var json = eval(property);
	var len = json.length;
	$("#goodSize").html("");
	for (var i = 0; i < len; i++) {
		if (json[i]["color"] == color) {
			$("#goodSize").append(
					"<div class='ys'>" + json[i]["size"] + "</div>");
		}
	}
	$("#goodSize div").click(function() {
		$("#goodSize .ys.xuanz").attr("class", "ys");
		$(this).attr("class", "ys xuanz");
		var text = $(this).text();
		$("#xuanze").html("");
		$("#xuanze").append(color);
		$("#xuanze").append(" " + text);
		if (_type == "3") {
			SetStyle(color, text);
		}
		setPrice();
	});
}
/**
 * 设置商品风格
 * 
 * @param style
 */
function SetStyle(color, size) {
	propertyId = "0";
	var json = eval(property);
	var len = json.length;
	$("#goodStyle").html("");
	for (var i = 0; i < len; i++) {
		if (json[i]["color"] == color && json[i]["size"] == size) {
			$("#goodStyle").append(
					"<div class='ys'>" + json[i]["style"] + "</div>");
		}
	}
	$("#goodStyle div").click(function() {
		$("#goodStyle .ys.xuanz").attr("class", "ys");
		$(this).attr("class", "ys xuanz");
		var text = $(this).text();
		$("#xuanze").html("");
		$("#xuanze").append(color);
		$("#xuanze").append(" " + size);
		$("#xuanze").append(" " + text);
		setPrice();
	});
}
/**
 * 设置价格
 */
function setPrice() {
	var json = eval(property);
	var len = json.length;
	if (_type == 1) {
		var color = $("#goodsColor .ys.xuanz").text();
		for (var i = 0; i < len; i++) {
			if (json[i]["color"] == color) {
				propertyId = json[i]["id"];
			}
		}
	}
	if (_type == 2) {
		var color = $("#goodsColor .ys.xuanz").text();
		var size = $("#goodSize .ys.xuanz").text();
		for (var i = 0; i < len; i++) {
			if (json[i]["color"] == color && json[i]["size"] == size) {
				propertyId = json[i]["id"];
			}
		}
	}
	if (_type == 3) {
		var color = $("#goodsColor .ys.xuanz").text();
		var size = $("#goodSize .ys.xuanz").text();
		var style = $("#goodStyle .ys.xuanz").text();
		for (var i = 0; i < len; i++) {
			if (json[i]["color"] == color && json[i]["size"] == size
					&& json[i]["style"] == style) {
				propertyId = json[i]["id"];
			}
		}
	}
}
/**
 * 立即购买
 */
function buy() {
	if (_type != "0") {
		if (propertyId == "0") {
			alert("请选择商品属性！");
			return;
		}
	}
	var num = $(".injorj").val();
	var total = parseFloat(price) * parseFloat(num);
	var isOut = "0";
	var cartDetail = '{"total" : "' + total + '","gId" : "' + productId
			+ '","gQuantity" : "' + num + '","propertyTableName" : "'
			+ propertyTableName + '","propertyId" : "' + propertyId
			+ '","price" : "' + price + '"}'
	$.post(ctx + "/detail/SaveOrder", {
		cartDetail : cartDetail,
		total : total,
		isOut : isOut,
		introducer : userId
	}, function(result) {
		var status = result["status"];
		if (status == "99") {
			alert("您尚未登录，请先登录！");
			setCookie("productId", productId, 0.5);
			setCookie("userId", userId, 0.5);
			window.location.href = ctx + "/Login";
			return;
		} else if (status == "1") {
			var msg = result["data"]["msg"];
			alert(msg);
		} else {
			var id = result["data"]["id"];
			var url = ctx + "/orders/" + "subOrder?id=" + id;
			window.location.href = url;
		}
	}, 'json');
}

// 时间倒计时功能
function timer() {
	var ts = (new Date(endDate)) - (new Date());// 计算剩余的毫秒数
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);// 计算剩余的天数
	var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);// 计算剩余的小时数
	var mm = parseInt(ts / 1000 / 60 % 60, 10);// 计算剩余的分钟数
	var ss = parseInt(ts / 1000 % 60, 10);// 计算剩余的秒数
	dd = checkTime(dd);
	hh = checkTime(hh);
	mm = checkTime(mm);
	ss = checkTime(ss);
	$("#dd").html(dd);
	$("#hh").html(hh);
	$("#mm").html(mm);
	$("#ss").html(ss);
	setInterval("timer()", 1000);
}
function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
