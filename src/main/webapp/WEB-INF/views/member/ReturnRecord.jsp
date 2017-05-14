<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>退换货记录</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script src="${ctx}/resources/js/goods.js"></script>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20" onclick="history.go(-1);" />
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
			<div class="yly_color1 yl_th"
				style="background-color: #fff; border-right: 0px;">
				<a href="${ctx}/member/getReturn/" class="yly_color13">申请退换货</a>
			</div>
			<div class="yly_color2 yl_thr"
				style="background-color: #ef3030;">
				<a href="${ctx}/member/getReturnRecord/" class="yly_color2">退换货记录</a>
			</div>
		</div>
	</div>
	<if test="${not empty list}">
		<c:forEach items="${list}" var="item" varStatus="vs">
		<div class="yly_main_th yl_bc7">
			<div class="main_th_top yly_color3 yl_bc8">
				<span class="yly_float1">售后类型：</span> <span class="yly_color5">退货单</span>
				<span class="yly_float2 yl_bc16 yly_color5"><span
					class="yly_color3">状态：</span>${item.status}</span>
			</div>
			<div class="main_th_bot yl_bc9">
				<div class="yly_or_lis">
					<div class="or_lis_bottom">
						<div class="lis_bottom_pro yl_bc11">
							<div class="lis_bo_pro yl_bc12">
								<div class="bo_pro_lef">
									<img class="yl_bc13" src="${item.iconUrl }" />
								</div>
								<div class="bo_pro_rig yly_color4" onclick="OpenProduct(${item.gId})"
									style="margin: 0; /* margin-right: 1rem; */text-align: left;">${item.gName}</div>
								<div class="bo_pro_rig yly_color3 yly_font_size08" onclick="OpenProduct(${item.gId})"
									style="margin: 0; margin-right: 1rem;text-align: left;line-height:3rem;">
									<c:if test="${not empty item.goodsProperty.style}">${item.goodsProperty.style}&nbsp;</c:if>
									<c:if test="${not empty item.goodsProperty.color}">${item.goodsProperty.color}&nbsp;</c:if>
									<c:if test="${not empty item.goodsProperty.size}">${item.goodsProperty.size}&nbsp;</c:if>
								</div>
								<!-- <div style="margin-left: 0.1rem;">
									<img src="${ctx}/resources/images/right_morebtn.png"
										style="margin-top: 1.5rem; height: 20px;">
								</div> -->
							</div>
						</div>
						<div class="lis_bottom_pri_return yl_bc14 yly_font_size08">
							<br/>
							<span>售后编号：${item.id }</span><br/>
							<span>申请时间：<fmt:formatDate type="time" value="${item.requestTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
							<c:if test="${item.status != '退货申请中'}">
								<br/><span>反馈详情：${item.resultExplain }</span>
							</c:if>
						</div>
					</div>
				</div>
			</div>
	
		</div>
		</c:forEach>
	</if>


</body>
</html>
