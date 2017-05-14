<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>订单提交</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">

<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/icheck.js"></script>
<script>
	$(document).ready(function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-red',
			increaseArea : '20%' // optional
		});
	});
	var id = "${order.id }";//订单Id
	var contact="${address.contactName }";//收货联系人
	function pay() {
		var url = "";
		//判断是否设置地址
		if(contact==""){
			alert("请选择收货地址！");
			return;
		}
		if (isWeiXin()) {
			$.post(ctx + "/orders/WxPay.api", {
				id : id
			}, function(result) {
				url = result["url"];
				window.location.href = url;
			}, 'json');
		} else {
			url = ctx + "/orders/Payment?id=" + id;
			window.location.href = url;
		}
	}
</script>
</head>

<body>

	<div class="head">
		<ul>
			<li><a href="javascript:history.go(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">填写订单</li>
			<li></li>
		</ul>
	</div>
	<div class="main" style="background: none">
		<c:choose>
			<c:when test="${not empty address}">
				<div class="chanp">
					<a href="${ctx}/address/?orderId=${order.id }">
						<div class="cpname">
							收件人：${address.contactName} <br /> 联系电话：${address.phone} <br />
							${address.province}${address.city}${address.area}${address.streetAddr}
						</div>
						<div class="jt"></div>
					</a>
				</div>
			</c:when>
			<c:when test="${empty address}">
				<div class="dz">
					<a href="${ctx}/address/"><i>请先填写收货地址</i><span class="jt"
						style="float: right"></span></a>
				</div>
			</c:when>
		</c:choose>
		<c:set value="0" var="sum" />
		<c:choose>
			<c:when test="${not empty detail}">
				<c:forEach items="${detail}" var="item" varStatus="vs">
					<div class="chanp">
						<a href=""><div class="cppicc">
								<img src="${item.iconUrl }" width="100%">
							</div>
							<div class="cpname">${item.gName }</div>
							<div class="je">
								<span>￥${item.price }</span>x${item.quantity }
							</div>
							<div class="jt"></div></a>
							<div style="color:#C0C0C0; font-size: 10px;float: right;margin-top: -10px;">
								<c:if test="${!empty item.goodsProperty.color}">颜色:${item.goodsProperty.color}
									&nbsp;&nbsp;</c:if>
								<c:if test="${!empty item.goodsProperty.style}">尺码:${item.goodsProperty.style}</c:if>
								<c:if test="${!empty item.goodsProperty.size}">${item.goodsProperty.size}
									&nbsp;&nbsp;</c:if>
							</div>
					</div>
					
					<c:set value="${sum + item.quantity}" var="sum" />
				</c:forEach>
			</c:when>
		</c:choose>

		<div class="zffs">
			<ul>
				<li style="height: 14px"><a href="">
					<div class="zftou">支付方式</div>
						<div class="tounr">在线支付</div>
						<!-- div class="jt"></div--></a>
				</li>
				<li style="height: 38px"><a href="">
					<div class="zftou"
							style="line-height: 38px">配送方式
					</div>
						<div class="tounr">
							普通快递
						</div>
						<!-- div class="jt"></div--></a>
				</li>
				<!-- li style="height: 54px"><a href=""><div class="zftou"
							style="line-height: 54px">发票信息</div>
						<div class="tounr">
							电子发票<br>个人<br>电器类商品-明细
						</div>
						<div class="jt"></div></a></li-->
			</ul>
		</div>

		<!-- div class="zfzz"-->
			<!-- ul>
				<li><a href=""><div class="one">优惠券</div>
						<div class="two">暂无可用优惠券</div>
						<div class="jt" style="float: right"></div></a></li>
				<li><a href=""><div class="one">在线积分</div>
						<div class="two">暂无可用积分</div>
						<div class="jt" style="float: right"></div></a></li>
				<li><a href=""><div class="one">使用推荐号</div>
						<div class="two">无推荐人可不填</div>
						<div class="jt" style="float: right"></div></a></li>
			</ul-->
		<!-- /div-->

		<div class="zfzz">
			<ul>
				<li><div class="one">商品总计</div>
					<div class="three">￥${order.amount }</div>
					<div class="four">${sum }件</div></li>
				<!-- li><div class="one">在线积分</div>
					<div class="three">+￥0.00</div></li-->
			</ul>
		</div>

	</div>

	<div class="foot2">
		<div class="qjs">
			<a href="javascript:pay();">提交订单</a>
		</div>
		<div class="zj">
			应付金额<span>￥${order.amount }</span>
		</div>
	</div>


</body>
</html>
