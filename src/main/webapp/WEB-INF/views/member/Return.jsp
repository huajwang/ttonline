<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>退换货</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<style type="text/css">
.xg_return{
	float: left;
	display: inline-block;
}
.xg_return1{
	display: inline-block;
	width: 50%;
	text-overflow: ellipsis;
	overflow: hidden;
}
.xg_return2{
	overflow: hidden;
	text-overflow: ellipsis;
	display: block;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
	width: 50%;
}
.xg_return3{
	display: inline-block;
	width: 100%;
}
.xg_return4{
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
	float: left;
	width: 64%;
}
.xg_return1{}
.xg_return1{}
.xg_return1{}
</style>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20" onclick="history.go(-1);"/>
		</div>
		<div class="or_top_right">
			<p>退换货</p>
		</div>
	</div>
	<!--
    	时间：2016-04-27
    	描述：tophead
    -->
	<div class="yly_or_bc yly_bg_color2 yl_bc5">
		<div class="yl_bc6">
			<div class="yly_color2 yl_th " style="background-color: #ef3030;">
				<a href="${ctx}/member/getReturn/" class="yly_color2">申请退换货</a>
			</div>
			<div class="yly_color1 yl_thr" style="background-color: #fff; border-right: 0px;">
				<a href="${ctx}/member/getReturnRecord/" class="yly_color13">退换货记录</a>
			</div>
		</div>
	</div>
	<c:if test="${not empty list}">
		<c:forEach items="${list}" var="order" varStatus="vs">
			<div class="yly_main_th yl_bc7">
				<c:if test="${not empty order.details}">
				<div class="main_th_top yly_color3 yl_bc8">
					<span class="yly_float1 yl_bc1"><img
								src="${ctx}/resources/images/logo.jpg" width="20px" height="20px" /></span>
					<span class="xg_return">订单编号：</span> <span class="yly_color4 xg_return1">${order.orderId}</span>
				</div>
				<c:forEach items="${order.details}" var="detail" varStatus="vs2">
					<div class="main_th_bot yl_bc9">
						<div class="yly_or_lis">
							<div class="or_lis_top yl_bc10">
								<span class="yly_float1 yly_color3">配送单编号：</span> <span class="xg_return2"
									class="yly_color4">${detail.logisticsNo}</span>
							</div>
							<div class="or_lis_bottom">
								<div class="lis_bottom_pro yl_bc11">
									<div class="lis_bo_pro yl_bc12">
										<div class="bo_pro_lef">
											<img class="yl_bc13" src="${detail.iconUrl}" />
										</div>
										<div class="bo_pro_rig yly_color4" style="margin: 0;text-align: left;">${detail.gName}</div>
										<a href="${ctx}/member/applyReturn?detailId=${detail.detailId}">
										<!-- &&orderId=${order.id}&&propertyId=${detail.propertyId}&&gId=${detail.id} -->
										<span class="yl_bc15 indexan" >申请退换货</span></a>
									</div>
								</div>
								<div class="lis_bottom_pri yl_bc14">
									<span class="xg_return3">
										<span>配送单金额：</span>
										<span class="yly_color5">￥${detail.price}</span>
									</span><br /> 
									<span class="xg_return3">
										<span style="float: left;">下单时间：</span>
										<span class="xg_return4">${order.createTime}</span>
									</span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				</c:if>
			</div>
			</br>
		</c:forEach>
	</c:if>
	<!--kefu-->
		<div id="div" class="qqke">
			 <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes">
				<img alt="" src="${ctx}/resources/img/qqke3.PNG" style="width: 100%;height: 100%">
			 </a> 
		</div>
	<!--kefu-->

</body>
</html>
