<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/common/public.jsp"%>
<title>退货详情</title>
</head>
<style type="text/css">
		body{
			margin: 0;
			padding: 0;
		}
</style>
<body>
		<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
			<img onclick="self.location=document.referrer;" class="prodetalieimg"
				alt=""
				src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
			<span style="font-size: 18px; display: inline-block;">退货及换货</span> <img
				style="float: right; margin-top: 1.05em; padding-right: 10px;display:none;"
				alt="" src="${ctx}/groupon/assets/img/pint/tb1.png">
		</div>
	<c:forEach items="${list}" var="item">
		<c:forEach items="${item.order}" var="order">
		<div style="width:100%;height:auto;clear:both">
		<div class="back_detail_top" style="width:100%;text-indent: 0.4em;">${order.userName}</div>
		
		<div>
			<c:forEach items="${order.productList}" var="product">
			<div class="back_detail_list" style="border: none ;">
				<div class="list_product" style="border-bottom:1px solid RGB(240,240,240);">
					<img class="list_product_pro" src="${product.Icon}" />
					<span class="list_product_pri">${product.price}元/${product.unit}</span>
					<span class="list_product_num">${product.num}份</span>
				</div>
			</div>
			</c:forEach>
			
		</div>
		
		<div class="back_pric">
			<span class="abckcol" style="float:left;margin-left:45%">订单金额：</span>
			<span class="abckcol1">￥${order.amount}</span>
			<br />
			<span class="abckcol">下单时间：<fmt:formatDate type="time" value="${order.createTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span>
		</div>
		
		<div class="back_mem">
			<span class="back_mem_name">姓名：${order.userName}</span>
			<span class="back_mem_phone">联系电话：${order.phone}</span>
			<span class="back_mem_adress">收货地址：${order.address.province}${order.address.city}${order.address.area}${order.address.streetAddr}</span>
		</div>
		
		<div class="back_mem remcolor yybc">退货原因：${order.reason}</div>
		</div>
		</c:forEach>
	</c:forEach>
</body>
</html>
