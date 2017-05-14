/**
 * 
 */

var productId = Request["productId"];// 产品Id
var userId = Request["userId"];// 分享用户ID
var propertyId = "0";
/**
 * 加入购物车
 */
function addCart() {
	if (userId == undefined)
		userId = 0;
	if (_type != "0") {
		if (propertyId == "0") {
			alert("请选择商品属性！");
			return;
		}
	}
	var num = $(".injorj").val();
	var total = parseFloat(price) * parseFloat(num);
	var isOut = "0";
	var cartDetail = '{"total" : ' + total + ',"gId" : "' + productId
			+ '","gQuantity" : ' + num + ',"propertyTableName" : "'
			+ propertyTableName + '","propertyId" : ' + propertyId
			+ ',"price" : "' + price + '","introducer" : "' + userId
			+ '","isOut":"' + isOut + '"}';
	/*//判断当前用户是否为空
	if(dquserId==""){
		// 判断是不是微信浏览器 如果是调转到微信登录鉴权页面上
		if (isWeiXin()) {
			window.location.href = ctx
					+ "/logwx/index?goPage=detail/SaveOrderByPage&order="
					+ escape(cartDetail) + "&introducer=" + userId;
			return;
		}
	}*/
	$.post(ctx + "/detail/addCart", {
		cartDetail : cartDetail,
		total : total,
		isOut : isOut
	}, function(result) {
		var status = result["status"];
		if (status == "99") {
			alert("您尚未登录，请先登录！");
			setCookie("productId", productId, 0.5);
			setCookie("userId", userId, 0.5);
			window.location.href = ctx + "/Login";
			return;
		} else {
			layer.msg("添加购物车成功！");
			$('.tchu').css('display', 'none');
			$('.tachu').css('display', 'none');
			// var url = ctx + "/cart";
			//window.location.reload();// 重载页面
			history.go(0);
		}
	}, 'json');
}
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
 * 收藏点击
 */
function ShareClick() {
	$.post(ctx + "/detail/ajaxIsFavorite", {
		gId : productId
	}, function(result) {
		var status = result["status"];
		if (status == "99") {
			alert("您尚未登录，请先登录！");
			setCookie("productId", productId, 0.5);
			setCookie("userId", userId, 0.5);
			window.location.href = ctx + "/Login";
			return;
		} else {
			if (result["data"]["isFavourite"] == "true") {
				// if (window.confirm("您确定取消收藏？"))
				favourite(0);
			} else {
				// if (window.confirm("您确定收藏该商品？"))
				favourite(1);
			}
		}
	}, 'json');
}
/**
 * 收藏
 * 
 * @param type
 *            0 取消收藏 1 收藏
 */
function favourite(type) {
	$.post(ctx + "/detail/favourite", {
		type : type,
		gId : productId
	}, function(result) {
		var status = result["status"];
		if (status == "99") {
			setCookie("productId", productId, 0.5);
			setCookie("userId", userId, 0.5);
			window.location.href = ctx + "/Login";
			return;
		} else {
			if (type == 1) {
				$('#changePic').attr('src',
						ctx + '/resources/images/icon_42.png');
				layer.msg("恭喜，产品收藏成功!");
			} else {
				$('#changePic').attr('src',
						ctx + '/resources/images/icon_41.png');
				layer.msg("取消产品收藏成功!");
			}
		}
	}, 'json');
}

/**
 * 加载市数据
 */
function loadCity() {
	var value = $("#province").val();
	$.post(ctx + "/sys/code/getCityByProvinceId.api", {
		provinceId : value
	}, function(result) {
		var len = result["data"].length;
		if (len > 0) {
			var json = result["data"];
			$("#city").empty();
			for (var i = 0; i < len; i++) {
				$("#city").append(
						"<option value='" + json[i]["cityId"] + "'>"
								+ json[i]["city"] + "</option>")
			}
			loadArea();
		}
	}, 'json');
}
/**
 * 加载县区数据
 */
function loadArea() {
	var value = $("#city").val();
	$.post(ctx + "/sys/code/getAreaByCityId.api", {
		cityId : value
	}, function(result) {
		var len = result["data"].length;
		if (len > 0) {
			var json = result["data"];
			$("#area").empty();
			for (var i = 0; i < len; i++) {
				$("#area").append(
						"<option value='" + json[i]["areaId"] + "'>"
								+ json[i]["area"] + "</option>")
			}
		}
	}, 'json');
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
	for (var i = 0; i < colorArr.length; i++) {
		$("#goodsColor").append(
				"<div id='gcolor" + i + "'class='ys axuan'>" + colorArr[i]
						+ "</div>");
	}
	for (var i = 0; i < sizeArr.length; i++)
		$("#goodsSize").append(
				"<div id='gsize" + i + "'class='ys axuan'>" + sizeArr[i]
						+ "</div>");
	for (var i = 0; i < styleArr.length; i++)
		$("#goodsStyle").append(
				"<div id='gstyle" + i + "'class='ys axuan'>" + styleArr[i]
						+ "</div>");
	setGoodsType();
	// 延迟1秒执行 默认选中第一个属性
	setTimeout(function() {
		if (colorArr.length == 1)
			$("#gcolor0").click();
		if (sizeArr.length == 1)
			$("#gsize0").click();
		if (styleArr.length == 1)
			$("#gstyle0").click();
	}, 1000);
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
	$("#spjg").html("");
	$("#spjg").append('￥' + json[_index]['price'] + '元');
	propertyId = json[_index]['id'];
	var iconUrl = json[_index]['iconUrl'];
	if (iconUrl != undefined) {
		$("#imgProperty").attr("src",
				"http://toyke.oss-cn-hangzhou.aliyuncs.com/" + iconUrl);
	}
	price = json[_index]['price'];
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
				$("#price").text("￥" + json[i]["price"]);
				price = json[i]["price"];
				$("#spjg").html("");
				$("#spjg").append("￥" + json[i]["price"]);
				propertyId = json[i]["id"];
			}
		}
	}
	if (_type == 2) {
		var color = $("#goodsColor .ys.xuanz").text();
		var size = $("#goodSize .ys.xuanz").text();
		for (var i = 0; i < len; i++) {
			if (json[i]["color"] == color && json[i]["size"] == size) {
				$("#price").text("￥" + json[i]["price"]);
				price = json[i]["price"];
				$("#spjg").html("");
				$("#spjg").append("￥" + json[i]["price"]);
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
				$("#price").text("￥" + json[i]["price"]);
				price = json[i]["price"];
				$(".bjle").text("￥" + json[i]["price"]);
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
	if (userId == undefined)
		userId = "0";
	var isOut = "0";
	var cartDetail = '{"total" : "' + total + '","gId" : "' + productId
			+ '","gQuantity" : "' + num + '","propertyTableName" : "'
			+ propertyTableName + '","propertyId" : "' + propertyId
			+ '","price" : "' + price + '"}'
	$.post(ctx + "/detail/SaveOrder", {
		cartDetail : cartDetail,
		total : total,
		introducer : userId
	}, function(result) {
		var status = result["status"];
		if (status == "99") {
			/*// 判断是不是微信浏览器 如果是调转到微信登录鉴权页面上
			if (isWeiXin()) {
				window.location.href = ctx
						+ "/logwx/index?goPage=detail/SaveOrderByPage&order="
						+ escape(cartDetail) + "&introducer=" + userId;
				return;
			}*/
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
