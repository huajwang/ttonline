<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>全部订单</title>
<%@ include file="/common/public.jsp"%>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: rgb(245, 246, 250);
}
</style>
</head>
<body>
	<!-- 顶部 -->
	<div class="all_list_top">
		<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
		<div class="all_list_top_menu">
			<div class="list_top_menu">
				<span class="allfon">${count }</span> <span>次</span><br /> <span
					class="allcor">支持次数</span>
			</div>
			<div class="list_top_menu list_top_menu1">
				<span class="allfon">${target }</span> <span>元</span><br /> <span
					class="allcor">支持金额</span>
			</div>
			<div class="list_top_menu">
				<span class="allfon">${RecordCount }</span> <span>次</span><br /> <span
					class="allcor">关注数</span>
			</div>
		</div>
	</div>

	<!-- 菜单切换 -->
	<div class="alllismenu">
		<div class="alllismenu1 allsele" data-menuid="quanbu">全部订单</div>
		<div class="alllismenu1" data-menuid="daifahuo">待发货</div>
		<div class="alllismenu1" data-menuid="yifahuo">已发货</div>
		<div class="alllismenu1" data-menuid="yishouhuo">已收货</div>
	</div>

	<!-- 菜单内容 -->

	<!-- 全部订单 -->
	<div id="quanbu" class="allisstatu">
		<c:choose>
			<c:when test="${not empty orderList}">
				<c:forEach items="${orderList}" var="item" varStatus="vs">
					<!-- 用户信息 -->
					<div class="delivery_top ">
						<div class="delivery_top_1  delivery_ling1">${item.realName }</div>
						<div class="delivery_top_1 delivery_ling2">${item.phone }</div>
					</div>

					<!-- 产品详情 -->
					<div class="bgcolor1 weizhi1" style="width: 100%;">
						<c:if test="${not empty item.productList}">
							<c:forEach items="${item.productList}" var="product"
								varStatus="vs1">
								<div class="back_detail_list" style="border: none;">
									<div class="list_product"
										style="border-top: 1px solid RGB(240, 240, 240);">
										<img class="list_product_pro" src="${product.Icon }" /> <span
											class="list_product_pri">${product.ProductName }
											${product.price }/${product.unit }</span> <span
											class="list_product_num">${product.num }</span>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>

					<!-- 合计 -->
					<div class="allanbot">
						<div class="back_pric">
							<span class="abckcol" style="float: left; margin-left: 54%">订单金额：</span>
							<span class="abckcol1">￥${item.amount }</span> <br /> <span
								class="abckcol">下单时间：<fmt:formatDate type="time" value="${item.createTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
						</div>
					</div>
					<!-- <div class="an_allorder weizhi1"></div>
					<div class="jiangeall"></div> -->
				</c:forEach>
			</c:when>
		</c:choose>
	</div>

	<!-- 待发货 -->
	<div id="daifahuo" class="disdown allisstatu">
		<c:choose>
			<c:when test="${not empty orderList}">
				<c:forEach items="${orderList}" var="item" varStatus="vs">
					<c:if test='${item.status=="1"}'>
						<!-- 用户信息 -->
						<div id="user${item.id}" class="delivery_top ">
							<div class="delivery_top_1  delivery_ling1">${item.realName }</div>
							<div class="delivery_top_1 delivery_ling2">${item.phone }</div>
							<div class="delivery_top_1 delivery_ling3">已付款</div>
						</div>

						<!-- 产品详情 -->
						<div id="product${item.id}" class="bgcolor1 weizhi1" style="width: 100%;">
							<c:if test="${not empty item.productList}">
								<c:forEach items="${item.productList}" var="product"
									varStatus="vs1">
									<div class="back_detail_list" style="border: none;">
										<div class="list_product"
											style="border-top: 1px solid RGB(240, 240, 240);">
											<img class="list_product_pro" src="${product.Icon }" /> <span
												class="list_product_pri">${product.ProductName }
												${product.price }/${product.unit }</span> <span
												class="list_product_num">${product.num }</span>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>

						<!-- 合计 -->
						<div id="Sum${item.id}" class="allanbot">
							<div class="back_pric">
								<span class="abckcol" style="float: left; margin-left: 54%">订单金额：</span>
								<span class="abckcol1">￥${item.amount }</span> <br /> <span
									class="abckcol">下单时间：${item.createTime }</span>
							</div>
						</div>

						<!-- 收货信息 -->
						<div id="Addr${item.id}" class="back_mem weizhi1 bgcolor1">
							<span class="back_mem_name">姓名：${item["address"]["contactName"] }</span>
							<span class="back_mem_phone">联系电话：${item["address"]["phone"] }</span>
							<span class="back_mem_adress">收货地址：${item["address"]["province"] }${item["address"]["city"] }${item["address"]["area"] }${item["address"]["streetAddr"] }</span>
						</div>

						<!-- 按钮 -->
						<div id="btn${item.id}" onclick="SendGoods(${item.id});" class="an_allorder weizhi1">
							<div class="ztbgcolor fontcolor1 an_fh weizhi2">发货</div>
						</div>
						<div id="btn1${item.id}" class="jiangeall"></div>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>

	<!-- 已发货 -->
	<div id="yifahuo" class="disdown allisstatu">
		<c:choose>
			<c:when test="${not empty orderList}">
				<c:forEach items="${orderList}" var="item" varStatus="vs">
					<c:if test='${item.status=="2"}'>
						<!-- 用户信息 -->
						<div class="delivery_top ">
							<div class="delivery_top_1  delivery_ling1">${item.realName }</div>
							<div class="delivery_top_1 delivery_ling2">${item.phone }</div>
							<div class="delivery_top_1 delivery_ling3">待收货</div>
						</div>

						<!-- 产品详情 -->
						<div class="bgcolor1 weizhi1" style="width: 100%;">
							<c:if test="${not empty item.productList}">
								<c:forEach items="${item.productList}" var="product"
									varStatus="vs1">
									<div class="back_detail_list" style="border: none;">
										<div class="list_product"
											style="border-top: 1px solid RGB(240, 240, 240);">
											<img class="list_product_pro" src="${product.Icon }" /> <span
												class="list_product_pri">${product.ProductName }
												${product.price }/${product.unit }</span> <span
												class="list_product_num">${product.num }</span>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>

						<!-- 合计 -->
						<div class="allanbot">
							<div class="back_pric">
								<span class="abckcol" style="float: left; margin-left: 54%">订单金额：</span>
								<span class="abckcol1">￥${item.amount }</span> <br /> <span
									class="abckcol">下单时间：${item.createTime }</span>
							</div>
						</div>

						<!-- 收货信息 -->
						<div class="back_mem weizhi1 bgcolor1">
							<span class="back_mem_name">姓名：${item["address"]["contactName"] }</span>
							<span class="back_mem_phone">联系电话：${item["address"]["phone"] }</span>
							<span class="back_mem_adress">收货地址：${item["address"]["province"] }${item["address"]["city"] }${item["address"]["area"] }${item["address"]["streetAddr"] }</span>
						</div>
						<div class="jiangeall"></div>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>

	<!-- 已收货 -->
	<div id="yishouhuo" class="disdown allisstatu">
		<c:choose>
			<c:when test="${not empty orderList}">
				<c:forEach items="${orderList}" var="item" varStatus="vs">
					<c:if test='${item.status>2}'>
						<!-- 用户信息 -->
						<div class="delivery_top ">
							<div class="delivery_top_1  delivery_ling1">${item.realName }</div>
							<div class="delivery_top_1 delivery_ling2">${item.phone }</div>
							<div class="delivery_top_1 delivery_ling3">已收货</div>
						</div>

						<!-- 产品详情 -->
						<div class="bgcolor1 weizhi1" style="width: 100%;">
							<c:if test="${not empty item.productList}">
								<c:forEach items="${item.productList}" var="product"
									varStatus="vs1">
									<div class="back_detail_list" style="border: none;">
										<div class="list_product"
											style="border-top: 1px solid RGB(240, 240, 240);">
											<img class="list_product_pro" src="${product.Icon }" /> <span
												class="list_product_pri">${product.ProductName }
												${product.price }/${product.unit }</span> <span
												class="list_product_num">${product.num }</span>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>

						<!-- 合计 -->
						<div class="allanbot">
							<div class="back_pric">
								<span class="abckcol" style="float: left; margin-left: 54%">订单金额：</span>
								<span class="abckcol1">￥${item.amount }</span> <br /> <span
									class="abckcol">下单时间：${item.createTime }</span>
							</div>
						</div>

						<!-- 收货信息 -->
						<div class="back_mem weizhi1 bgcolor1">
							<span class="back_mem_name">姓名：${item["address"]["contactName"] }</span>
							<span class="back_mem_phone">联系电话：${item["address"]["phone"] }</span>
							<span class="back_mem_adress">收货地址：${item["address"]["province"] }${item["address"]["city"] }${item["address"]["area"] }${item["address"]["streetAddr"] }</span>
						</div>
						<div class="jiangeall"></div>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>


</body>
<script>
	$(function() {
		$('.alllismenu1').on('click', function() {
			$('.alllismenu1').removeClass('allsele');
			$(this).addClass('allsele');
			var menuid = $(this).data('menuid');
			$('.allisstatu').css('display', 'none');
			$("#" + menuid + "").css('display', 'block');
		});
	});
	//发货
	function SendGoods(id){
		var _this=this;
		if(window.confirm('您确定发货？')){
			$.post(ctx + '/groupon/api/project/updOrderStatus', {
				Id : id,
				status:2
			}, function(result) {
				var json=JSON.parse(result);
				if (json["status"] == "0") {
					alert("发货成功！");
					$("#user"+id).remove();
					$("#product"+id).remove();
					$("#Sum"+id).remove();
					$("#Addr"+id).remove();
					$("#btn"+id).remove();
					$("#btn1"+id).remove();
					//window.location.href = ctx + "/groupon/launch/start";
				}
				else{
					alert("发货失败！请与管理员联系");
				}
			});
		}
	}
</script>
</html>