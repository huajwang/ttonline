<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>我的购物</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" onclick="history.go(-1);"
				height="20" />
		</div>
		<div class="or_top_right">
			<p>全部订单</p>
		</div>
	</div>
	<!--
    	时间：2016-04-27
    	描述：tophead
    -->
	<div class="yly_or_bc">
		<ul class="black">
			<li class="tit">订单详情</li>
		</ul>
	</div>

	<div class="yly_main">
		<c:choose>
			<c:when test="${not empty order}">
				<c:forEach items="${order}" var="item" varStatus="vs">
					<div class="yly_or_lis">
						<div class="or_lis_top">
							<span class="yly_float1 yl_bc1"> <img
								src="${ctx}/resources/images/logo.jpg" width="20px" height="20px" />
							</span> <span class="yly_float1">订单编号：${item.orderId }</span>
						</div>
						<div class="or_lis_bottom">
							<c:forEach items="${item.details}" var="detail" varStatus="vs1">
								<div class="lis_bottom_pro">
									<div class="lis_bottom_pro">
										<div class="lis_bo_pro">
											<div class="bo_pro_lef">
												<img src="${detail.iconUrl }"
													style="width: 4.5rem; height: 4.5rem;" />
											</div>
											<div class="bo_pro_rig" style="text-align: left;" onclick="window.location.href='${ctx}/productDetail?productId= ${detail.id}';">
												商品名称：${detail.gName }</div>
										</div>
									</div>
								</div>
							</c:forEach>
							<div class="lis_bottom_pri">
								<span>订单金额：<span style="color: RGB(41, 41, 41)">￥${item.amount }</span></span><br />
								<span style="font-size:10px">下单时间：<fmt:formatDate type="time" value="${item.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
							</div>
							<div class="lis_bottom_pay"></div>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

		<div class="yly_end">没有更多了</div>

	</div>


</body>
</html>
