<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>订单支付</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<script src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript">
	var amount = "${order.amount }";
	var OpenId = "${OpenId }";
	var code = Request["code"];
	var orderId = "${orderId }";
	var id = "${order.id }";
	var groupon = "${groupon }";//判断是否由拼团发起支付
	$(function() {
		if (isWeiXin()) {
			$("#zfb").css("display", "none");
			$("#ylzx").css("display", "none");
			$("#wxdf").css("display", "none");
		}else{
			$("#wx").css("display", "none");
			$("#ylzx").css("display", "none");
			$("#wxdf").css("display", "none");
		}
	})
	//微信支付
	function wxPay() {
		if (!isWeiXin()) {
			layer.msg("请在微信浏览器支付");
			return;
		}
		callpay();
	}

	//调用微信JS api 支付
	function jsApiCall(json) {
		WeixinJSBridge.invoke('getBrandWCPayRequest', json, function(res) {
			WeixinJSBridge.log(res.err_msg);
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				//判断是拼团支付还是商城支付
				/*if (groupon == "") {
					var r = $.ajax({
						type : "post",
						url : ctx + '/orders/updateOrder?id=' + id,
						async : false
					}).responseText;
					alert("支付成功！");
					var url = ctx + "/member/";
					window.location.href = url;
				} else {
					var r = $.ajax({
						type : "post",
						url : ctx + '/groupon/updateOrder?orderId=' + id,
						async : false
					}).responseText;
					alert("支付成功！");
					var url = ctx + "/groupon/";
					window.location.href = url;
				}*/
				alert("支付成功！");
			} else {
				alert("支付失败！");
			}
		});
	}

	function callpay() {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', jsApiCall,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', jsApiCall);
				document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			}
		} else {
			var r = $.ajax({
				type : "post",
				url : ctx + '/orders/OrderResult?orderId=' + orderId
						+ '&OpenId=' + OpenId + '&Money=' + amount,
				async : false
			}).responseText;
			var json = eval('(' + r + ')');
			jsApiCall(json)

		}
	}
	//支付宝支付
	function alipay(){
		document.forms['alipaysubmit'].submit();
	}
</script>
</head>

<body>

	<div class="head">
		<ul class="black">
			<li></li>
			<li class="tit">订单支付</li>
			<li><a href="javascript:history.go(-1);">关闭</a></li>
		</ul>
	</div>

	<div class="main2">
		<div class="gonggao">请在24小时内完成支付，部分商品支付时效为15或30分</div>

		<div class="oreder">
			<div class="orderico">
				<img src="${ctx}/resources/img/oreder.png" width="100%" />
			</div>
			<div class="oredernr">
				订单提交成功，请尽快付款！<span>应付金额：<i>￥${order.amount }</i></span>
			</div>
		</div>

		<div class="zflist">
			<ul>
				<li id="zfb"><a href="javascript:alipay();">
						<div class="zfico">
							<img src="${ctx}/resources/img/zfb.png" width="100%" />
						</div>
						<div class="zfnr">
							支付宝<span style="color: #b14e48"></span>
						</div>
						<div class="jt"></div>
				</a></li>

				<li id="wx"><a href="javascript:wxPay();">
						<div class="zfico">
							<img src="${ctx}/resources/img/wx.png" width="100%" />
						</div>
						<div class="zfnr">
							微信支付<span>建议已安装微信的用户使用</span>
						</div>
						<div class="jt"></div>
				</a></li>

				<li id="ylzx"><a href="">
						<div class="zfico">
							<img src="${ctx}/resources/img/yl.png" width="100%" />
						</div>
						<div class="zfnr">
							银联在线支付<span>支持银联储蓄卡，信用卡支付</span>
						</div>
						<div class="jt"></div>
				</a></li>

				<li id="wxdf"><a href="">
						<div class="zfico">
							<img src="${ctx}/resources/img/hy.png" width="100%" />
						</div>
						<div class="zfnr">
							微信大夫<span>好友代付，退款时将退给代付方</span>
						</div>
						<div class="jt"></div>
				</a></li>


			</ul>
		</div>

	</div>
${sHtmlText}
</body>
</html>
