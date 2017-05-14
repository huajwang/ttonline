<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>待收货</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script type="text/javascript">
	function getDetail(id){
		if(confirm("你确信要确认收货？"))
		{
			var r = $.ajax({
	            type: "post",
	            url: ctx+"/orders/updateOrderDetailState",
	            async: false,
	            data: "id=" + id 
	         }).responseText;
			var json=eval("("+r+")");
			if(json["status"]==0){
				alert("确认成功！");
				window.location.reload();
			}
		}else{
			alert("取消确认订单！")
		}
	}
</script>
<style type="text/css">
	.st_bottom{
	    width: 94%;
	    padding: 0.5rem 3%;
	    font-size: 10px;
	    line-height: 1.3rem;
	    background: rgba(0,0,0,0.4);
	    position: fixed;
	    bottom: 0;
    	color: #fff;
	}
	.ck_an{
		background-color: #fff;
		color: #ef3030;
		margin-right: 1rem;
	}
</style>
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
			<p>待收货</p>
		</div>
		<div class="yl_bc4" style="display:none">物流信息</div>
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
							<span class="yly_float1 yl_bc1"><img
								src="${ctx}/resources/images/logo.jpg" width="20px" height="20px" /></span>
							<span class="yly_float1">订单编号：${item.orderId }</span>
						</div>
						<div class="or_lis_bottom">
							<c:forEach items="${item.details}" var="detail" varStatus="vs1">
								<div class="lis_bottom_pro" style="height: 14rem;">
									<div class="lis_bo_pro">
										<div class="bo_pro_lef">
											<img src="${detail.iconUrl }"
												style="width: 4.5rem; height: 4.5rem;" />
										</div>
										<div class="bo_pro_rig" style="text-align: left;font-size: 13px;margin-top: 1px;" onclick="window.location.href='${ctx}/productDetail?productId= ${detail.id}';">
											${detail.gName }</div>
									</div>
									<div style="width:100%;color:#C0C0C0;font-size: 13px;float: left;margin:2px 0 10px 4px;">
											<c:if test="${!empty detail.goodsProperty.color}">
												<span>${detail.goodsProperty.color}&nbsp;&nbsp;</span>
											</c:if>
											<c:if test="${!empty detail.goodsProperty.style}">
												<span>${detail.goodsProperty.style}</span>
											</c:if>
											<c:if test="${!empty detail.goodsProperty.size}">
												<span>${detail.goodsProperty.size}&nbsp;&nbsp;</span>
											</c:if>
											<c:if test="${!empty detail.goodsProperty.price}">
												<span>价格：${detail.goodsProperty.price}元&nbsp;&nbsp;</span>
											</c:if>
											<c:if test="${!empty detail.quantity}">
												<span>数量：${detail.quantity}&nbsp;&nbsp;</span>
											</c:if>
										</div>
										<div style="font-size: 13px;margin-top:10px;">
											
											<c:if test="${!empty detail.contactName}">
												<span>收货人：${detail.contactName}&nbsp;&nbsp;</span>
											</c:if>
											<c:if test="${!empty detail.phone}">
												<span>联系方式：${detail.phone}&nbsp;&nbsp;<br></span>
											</c:if>
											<c:if test="${!empty detail.streetAddr}">
												<span style="text-overflow: ellipsis;overflow:hidden;width:80%;float:left;"><nobr>地址：${detail.streetAddr}&nbsp;&nbsp;</nobr></span>
											</c:if>
										</div>
									<div class="lis_bottom_pay">
										<div class="bottom_pay indexan"
											onclick="getDetail(${detail.detailId})">确认收货</div>
											<a href="javascript:;" onClick="javascript:window.open('http://m.kuaidi100.com/index_all.html?postid=${detail.logisticsNo}','','width=600,height=500,left=500, top=200,toolbar=no, status=no, menubar=no, resizable=yes, scrollbars=yes');return false;">
												<div class="bottom_pay indexan ck_an">查看物流</div>
											</a>
											<a href="javascript:;" onClick="location='${ctx}/member/applyReturn?detailId=${detail.detailId}'">
												<div class="bottom_pay indexan ck_an" style="width: 5.5rem;">申请退换货</div>
											</a>
									</div>
								</div>
							</c:forEach>
							<div class="lis_bottom_pri">
							
								<span>订单金额：<span style="color: RGB(41, 41, 41)">￥${item.amount }</span></span><br />
								<span style="font-size:10px">下单时间：<fmt:formatDate type="time" value="${item.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
							</div>
							<%-- <div class="lis_bottom_pay">
								<div class="bottom_pay indexan" onclick="getGood(${item.id})">确认收货</div>
							</div> --%>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>
	<div class="yly_end">没有更多了</div>

	<div class="st_bottom">
		<span>【我们支持7天无理由退换货，如果在申请过程中有任何问题，请联系我们售后客服QQ或拨打客服联系电话：0592-2069229】</span>
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
