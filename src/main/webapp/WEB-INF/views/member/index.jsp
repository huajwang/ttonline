<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>个人中心</title>
<%@ include file="/common/include.rec.ftl"%>
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="format-detection" content="telephone=no" />
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<!-- &{ctx}项目全路径引用，配置文件在上面的include文件中配置了 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base_new.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/personal.css">

<!--end-->
</head>
<body>
	<!--start-->
	<!--嵌入导航模板-->
	<div class="toolbar">
		<!-- span class="nav_ico nav_back" data-icon="0"></span-->
		<h2 class="ellipsis">蚂蚁小威</h2>
	</div>
	<!--end-->
	<!--头部用户账户信息-->
	<div id="header">
		<div class="user_info">
			<a href="${ctx}/member/PerfectInfor/">
				<div class="portrait"
					style="background-image: url(${user.iconUrl})"></div>
				<h2 class="name">${user.phone }</h2>
			</a>
			<h4 class="box h_center user_level level1">
				<div class="label" >布衣达人</div>
		</div>

		<ul class="sub_info grid3">
			<li class="icon_btn"><a href="${ctx}/member/myFavorite/"> <i
					class="font_icon icon wait_pay"> ${goodsize} </i> <span class="label">商品收藏</span>
			</a></li>
			<li class="icon_btn" style="text-align:center">
				<img alt="" src="${ctx}/resources/images/touming.png" >
				
			</li>
			<li class="icon_btn" style="text-align:center">
				<img alt="" src="${ctx}/resources/images/qq1.png" style="width: 1.5rem;height: 1.6rem;margin:0.1rem auto 0.55rem auto;">
				<span class="label"><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes">
				联系客服</a></span>
			</li>
			
		</ul>
	</div>


	<div class="content">
		<div class="module orders">
			<a href="${ctx}/member/orderHistory/">
				<h4 class="title">
					<i class="icon">l</i> 全部订单 <span class="desc">查看全部订单</span>
				</h4>
			</a>

			<div class="sub_info grid5">
				<a class="icon_btn" href="${ctx}/member/getWaitPayOrderList/"> <i
					class="font_icon icon wait_pay"> ${order1 } </i> <span
					class="label">待支付</span>
				</a> <a class="icon_btn" href="${ctx}/member/getWaitSendOrder/"> <i
					class="font_icon icon wait_recieve"> ${order2 } </i> <span
					class="label">待发货</span>
				</a> <a class="icon_btn" href="${ctx}/member/getWaitGetOrder/"> <i
					class="font_icon icon wait_transport"> ${order3 } </i> <span
					class="label">待收货</span>
				</a> <a class="icon_btn" href="${ctx}/member/getWaitEvalOrder/"> <i
					class="font_icon icon wait_judge"> ${order4 } </i> <span
					class="label">待评价</span>
				</a> <a class="icon_btn" href="${ctx}/member/getReturn/"> <i
					class="font_icon icon service"> ${order5} </i> <span class="label">退款/售后</span>
				</a>
			</div>
		</div>
		<div class="module fourtune">
			<a href="#">
				<h4 class="title">
					<i class="icon">m</i> 我的钱包
				</h4>
			</a>

			<div class="grid4">
				<a class="icon_btn" href="${ctx}/member/getWithdrawals/"> <i
					class="number_change">${balance }</i> <span class="label">账户余额</span>
				</a> <a class="icon_btn" href="${ctx}/member/EarningsRecord/"> <i
					class="number_change"><img src="${ctx}/resources/images/shouyi.png" style="height:20px;width:20px;padding:10% 38% 10% 38%" /></i> <span class="label">收益记录</span>
				</a> <a class="icon_btn" href="${ctx}/member/PerfectInfor/"> <i
					class="number_change"><img src="${ctx}/resources/images/zhanghu.png" style="height:20px;width:20px;padding:10% 38% 10% 38%" /></i> <span class="label">账户管理</span>
				</a>
			</div>

		</div>
		<div class="simple_module_group">
			<a class="simple_module pre_order" href="${ctx}/member/getTrade/">
				<i class="icon" style="background-color: red;">7</i> 分享交易记录
			</a> <a class="simple_module pre_order"
				href="${ctx}/member/getMakerTrade/"> <i class="icon" style="background-color: blue;">5</i>
				创客交易记录
			</a> <a class="simple_module pre_order" href="${ctx}/address/"> <i
				class="icon">e</i> 收货地址
			</a>
		</div>
		<a class="simple_module help" href="${ctx}/member/ExplainFeedback/"> <i
			class="icon">p</i> 商城说明与反馈
		</a>
		<%-- <a class="simple_module help" href="${ctx}/member/Feedback/"> <i
			class="icon">p</i> 购物说明与反馈
		</a> --%>
	</div>

	<!--start-->
	<!--嵌入底部模板-->
	<div class="plc_footer" id="plc_footer">
		<div class="gome_info">
			<p>copyright © 2000-2016 www.xmtroika.com</p>

			<p>客服热线：0592-2069229</p>
		</div>
	</div>
	<br />
	<div id="footer">
		<ul>
			<li><a href="${ctx}/" class="nav1"></a></li>
			<li><a href="${ctx}/category/" class="nav2"></a></li>
			<li><a href="${ctx}/groupon/" class="nav5"></a></li>
			<li><a href="${ctx}/cart/" class="nav3">
				<c:forEach items="${carts}" var="item" varStatus="vs" begin="0" end="0">
					<c:if test="${ not empty item.id}">
						<div id="shuzi1">${cartsize}</div>
					</c:if>
					<c:if test="${empty item.id}">
						<div id="shuzi1">0</div>
					</c:if>
				</c:forEach>
			</a></li>
			<li><a href="${ctx}/member/" class="nav4 on"></a></li>
		</ul>
	</div>
</body>
</html>