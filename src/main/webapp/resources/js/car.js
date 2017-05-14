/**
 * 设置勾选状态
 */
var total = 0.00;// 总计
var arrayObj = new Array();// 选中商品的id集合
function setCheck(check, _this) {
	var id = $(_this).attr("id");
	if (id == "checkAll") {
		if (check)
			$('input').iCheck('check');
		else
			$('input').iCheck('uncheck');
	} else {
		// var gid=$(_this).attr("gid");//商品id
		var ck_id = id.split('ck_')[1];// 获取勾选按钮的id值
		var num = $("#num_" + ck_id).val();
		var price = $("#div_" + ck_id).attr("price");
		if (check) {
			total = (parseFloat(num) * parseFloat(price) + parseFloat(total))
					.toFixed(2);
			arrayObj.push(ck_id);
		} else {
			total = (parseFloat(total) - parseFloat(num) * parseFloat(price))
					.toFixed(2);
			arrayObj.remove(ck_id);
		}
		$("#total").text("￥" + total);
	}
}
/**
 * 打开商品详情
 */
function OpenProduct(id) {
	var url = ctx + "/" + "productDetail?productId=" + id;
	window.location.href = url;
}
/**
 * 结算
 */
function Settlement() {
	var json = eval(jsonStr);
	var len = arrayObj.length
	if (len == 0) {
		alert("请选择商品");
		return;
	}
	var cartId = "0";
	var amount = total;
	var orderSource = 0;
	var orderDetail = '[';
	for (var i = 0; i < len; i++) {
		var datalen = json.length;
		for (var r = 0; r < datalen; r++) {
			var ck_id = arrayObj[i];// 勾选的数据
			if (ck_id == json[r]["id"] + '' + json[r]["propertyId"]) {
				cartId = json[r]["cartId"];
				var num = $("#num_" + ck_id).val();
				var propertyTableName = json[r]["propertyTableName"] == undefined ? ""
						: json[r]["propertyTableName"]
				orderDetail += '{"gId":"' + json[r]["id"] + '","gQuantity":"'
						+ num + '","propertyTableName":"' + propertyTableName
						+ '","propertyId":' + json[r]["propertyId"] + '},'
				break;
			}

		}
	}
	orderDetail = orderDetail.substring(0, orderDetail.length - 1);
	orderDetail += ']';
	var userId = getCookie("userId");
	if (userId == "undefined")
		userId = "0";
	$.post(ctx + "/cart/addOrder", {
		addrId : addrId == "" ? 0 : addrId,
		amount : amount,
		orderSource : orderSource,
		orderDetail : orderDetail,
		cartId : cartId
	}, function(result) {
		var status = result["status"];
		if (status == "1") {
			var data = result["data"]["result"];
			var len = result["data"]["result"].length;

			for (var i = 0; i < len; i++) {
				var gName = data[i]["gName"];
				var msg = data[i]["msg"];
				alert(gName + " " + msg);
			}
		}
		if (status == "0") {
			var id = result["data"]["id"];
			var url = ctx + "/orders/" + "subOrder?id=" + id;
			window.location.href = url;
		}

	}, 'json');
}

/**
 * 减数
 */
function reduceNum(index) {
	var num = $("#num_" + index).val();
	num = parseInt(num) - 1;
	if (num >= 1) {
		$("#num_" + index).val(num);
		if ($("#ck_" + index).attr("checked") == undefined)
			return;
		if (total != 0) {
			var price = $("#div_" + index).attr("price");
			total = (parseFloat(total) - parseFloat(price)).toFixed(2);
			$("#total").text("￥" + total);
		}
	}
}
/**
 * 加数
 */
function addNum(index) {
	var num = $("#num_" + index).val();
	num = parseInt(num) + 1;
	$("#num_" + index).val(num);
	if ($("#ck_" + index).attr("checked") == undefined)
		return;
	if (total != 0) {
		var price = $("#div_" + index).attr("price");
		total = (parseFloat(price) + parseFloat(total)).toFixed(2);
		$("#total").text("￥" + total);
	}
}
/**
 * 删除购物车详情数据
 * 
 * @param id
 */
function delCart(id) {
	if (window.confirm("您确定删除此数据？")) {
		$.post(ctx + "/cart/deleteCart", {
			id : id
		}, function(result) {
			var status = result["status"];
			if (status == "0") {
				window.location.reload();
			}
		}, 'json');
	}
}
