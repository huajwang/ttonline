<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>待评价</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script type="text/javascript">
	function EvalOrder(orderId,gid,detailId){
		var url=ctx+"/member/Evaluate?orderId="+orderId+"&gid="+gid+"&detailId="+detailId;
		window.location.href=url;
	}
</script>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20"
				onclick="history.go(-1);" />
		</div>
		<div class="or_top_right">
			<p>待评价</p>
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
					<div class="yly_or_lis" style="padding-bottom: 1rem;">
						<div class="or_lis_top">
							<span class="yly_float1 yl_bc1"><img
								src="${ctx}/resources/images/logo.jpg" width="20px" height="20px" /></span>
							<span class="yly_float1">订单编号：${item.orderId }</span>
						</div>
						<div class="or_lis_bottom">
							<c:forEach items="${item.details}" var="detail" varStatus="vs1">
								<div class="lis_bottom_pro">
									<div class="lis_bo_pro">
										<div class="bo_pro_lef">
											<img src="${detail.iconUrl }"
												style="width: 4.5rem; height: 4.5rem;" />
										</div>
										<div class="bo_pro_rig" style="text-align: left;" onclick="window.location.href='${ctx}/productDetail?productId= ${detail.id}';">商品名称：${detail.gName }</div>
										<div class="lis_com indexan"
											onclick="EvalOrder(${item.id },${detail.id },${detail.detailId });">去评价</div>
									</div>
								</div>
							</c:forEach>

						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>
	<div class="yly_end">没有更多了</div>

	</div>
	<!--kefu-->
		<div id="div" class="qqke" style="bottom: 23rem;">
		<a target="_blank"
			href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes">
			<img alt="" src="${ctx}/resources/img/kefu/00.png"
			style="width: 100%; height: 100%">
		</a>
	</div>
	<div id="div2" class="qqke" style="bottom: 17rem;">
		<a target="_blank"
			href="http://wpa.qq.com/msgrd?v=3&uin=3447681374&site=qq&menu=yes">
			<img alt="" src="${ctx}/resources/img/kefu/11.png"
			style="width: 100%; height: 100%">
		</a>
	</div>
	<!--kefu-->

</body>
</html>
