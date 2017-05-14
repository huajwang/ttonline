<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>添加新地址</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="m.gome.com.cn">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="applicable-device" content="mobile">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<%@ include file="/common/include.rec.ftl"%>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base_new.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/fill_orders.css">

</head>
<body class="address_form_body" style="">

	<nav class="nav" id="wap_address" style="display: block;">
		<div class="toolbar" style="display: block">
			<span class="nav_return nav_ico nav_back" id="nav_return"
				data-icon="0" onclick="history.go(-1);"></span>
			<h2 class="ellipsis">添加新地址</h2>
		</div>
	</nav>

	<article class="content">
		<section class="address_form">
			<div class="div_input">
				<lable>收货人：</lable>
				<p>
					<input type="text" id="name" placeholder="请填写收货人姓名 "
						<c:if test="${not empty address}">value="${address.contactName }"</c:if>
						maxlength="20">
				</p>
			</div>
			<div class="div_input">
				<lable>手机号码：</lable>
				<p>
					<input type="text" id="mobile" placeholder="请填写手机号码  "
						<c:if test="${not empty address}">value="${address.phone }"</c:if>
						maxlength="11">
				</p>
			</div>
			<div class="div_input">
				<lable>邮政编码：</lable>
				<p>
					<input type="text" id="postcode" placeholder="请填写邮政编码  "
						<c:if test="${not empty address}">value="${address.postcode }"</c:if>
						maxlength="6">
				</p>
			</div>
			<div class="div_select">
				<lable>省份：</lable>
				<p>
					<span id="j_province_span">请选择</span> <select id="j_province">
						<option>请选择</option>
						<c:choose>
							<c:when test="${not empty Provinces}">
								<c:forEach items="${Provinces}" var="item" varStatus="vs">
									<option value="${item.provinceId }">${item.province }</option>
								</c:forEach>
							</c:when>
						</c:choose>

					</select>
				</p>
			</div>
			<div class="div_select">
				<lable>城市：</lable>
				<p>
					<span id="j_city_span">请选择</span> <select id="j_city">
						<option>请选择</option>
					</select>
				</p>
			</div>
			<div class="div_select">
				<lable>区/县：</lable>
				<p>
					<span id="j_area_span">请选择</span> <select id="j_area">
						<option>请选择</option>
					</select>
				</p>
			</div>
			<div class="div_textarea">
				<lable>详细地址：</lable>
				<p>
					<textarea id="address" placeholder="收货人详细地址" maxlength="50"><c:if
							test="${not empty address}">${address.streetAddr }</c:if>
					</textarea>
				</p>
			</div>
			<!-- <div class="div_select">
				<lable>是否默认：</lable>
				<p>
					<span id="j_active_span">否</span> <select id="j_active">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</p>
			</div> -->
		</section>
		<section class="wap_btn" id="address_sub" databool="true">
			确认</section>
	</article>
</body>
<script>
	var _id = "${address.id }";
	var _province = "${address.provinceId }";
	var _city = "${address.cityId }";
	var _area = "${address.areaId }";
	var _active = "${address.active }";
	$(function() {
		$('select').on('change', function() {
			var id = $(this).attr("id");

			if (id.indexOf("province") >= 0)
				loadCity();
			if (id.indexOf("city") >= 0)
				loadArea();

			if (id.indexOf("area") >= 0) {
				var text = $("#j_area").find("option:selected").text();
				$("#j_area_span").text(text);
			}
			if (id.indexOf("active") >= 0) {
				var text = $("#j_active").find("option:selected").text();
				$("#j_active_span").text(text);
			}
		});
		//提交
		$("#address_sub").on(
				'click',
				function() {
					var name = $("#name").val();
					if (name == "") {
						alert("收货人不能为空！");
						return;
					}
					var mobile = $("#mobile").val();
					if (mobile == "") {
						alert("联系电话不能为空！");
						return;
					}
					var postcode = $("#postcode").val();
					var j_province = $("#j_province").val();
					var j_city = $("#j_city").val();
					var j_area = $("#j_area").val();
					var address = $("#address").val();
					//var active = $("#j_active").val();
					var active = 0;
					if (j_province == "" || j_city == "请选择" || j_area == "请选择"
							|| address == "") {
						alert("收货地址不能为空！");
						return;
					}
					if(_id=="")
						_id=0;
					$.post(ctx + "/address/ajaxSaveOrUpdateAddress", {
						id : _id,
						active : active,
						contactName : name,
						phone : mobile,
						provinceId : j_province,
						cityId : j_city,
						areaId : j_area,
						streetAddr : address,
						postcode : postcode
					}, function(result) {
						var status = result["status"];
						if (status == "0") {
							//var url = ctx + "/" + "address/";
							//window.location.href = url;
							self.location=document.referrer;
						} else {
							alert("添加失败！");
						}
					}, 'json');
				});
		setTimeout(function() {
			if (_active != "") {
				$("#j_active").val(_active);
				$("#j_active").trigger("change");
			}
			if (_province != "") {
				$("#j_province").val(_province);
				$("#j_province").trigger("change");
			}
		}, 500);
	})

	/**
	 * 加载市数据
	 */
	function loadCity() {
		var value = $("#j_province").val();
		var text = $("#j_province").find("option:selected").text();
		$("#j_province_span").text(text);
		if (text == "请选择") {
			$("#j_city_span").text(text);
			$("#j_area_span").text(text);
			$("#j_city").empty();
			$("#j_area").empty();
			$("#j_city").append("<option>请选择</option>");
			$("#j_area").append("<option>请选择</option>");
			return;
		}
		$.post(ctx + "/sys/code/getCityByProvinceId.api", {
			provinceId : value
		}, function(result) {
			var len = result["data"].length;
			if (len > 0) {
				var json = result["data"];
				$("#j_city").empty();
				for (var i = 0; i < len; i++) {
					$("#j_city").append(
							"<option value='" + json[i]["cityId"] + "'>"
									+ json[i]["city"] + "</option>")
				}

				if (_city != "") {
					$("#j_city").val(_city);
				}
				loadArea();
			}
		}, 'json');
	}
	/**
	 * 加载县区数据
	 */
	function loadArea() {
		var value = $("#j_city").val();
		var text = $("#j_city").find("option:selected").text();
		$("#j_city_span").text(text);
		$.post(ctx + "/sys/code/getAreaByCityId.api", {
			cityId : value
		}, function(result) {
			var len = result["data"].length;
			if (len > 0) {
				var json = result["data"];
				$("#j_area").empty();
				for (var i = 0; i < len; i++) {
					$("#j_area").append(
							"<option value='" + json[i]["areaId"] + "'>"
									+ json[i]["area"] + "</option>")
				}
				if (_area != "") {
					$("#j_area").val(_area);
				}
				var text = $("#j_area").find("option:selected").text();
				$("#j_area_span").text(text);
			}
		}, 'json');
	}
</script>
</html>