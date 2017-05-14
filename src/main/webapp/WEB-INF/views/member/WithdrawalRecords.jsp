<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>提现记录</title>
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
			<img src="${ctx}/resources/img/back.png" height="20"
				onclick="history.go(-1);" />
		</div>
		<div class="or_top_right">
			<p>提现记录</p>
		</div>
	</div>

	<div class="jiange1"></div>
	<div class="yly_bg_color7 yl_bc44">
		<div class="yly_float4 tab7">提现账号</div>
		<div class="yly_float4 tab8">金额</div>
		<div class="yly_float4 tab8">状态</div>
		<div class="yly_float4 tab7">请求时间</div>
	</div>
	
	<div class="yl_bc46">
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="item" varStatus="vs">
					<div class="yl_bc45" style="height:2rem">
						<div class="yly_float4 tab7">${item.accountNo }</div>
						<div class="yly_float4 tab8">
							<span class="yly_color5">${item.amount }</span>元
						</div>
						<div class="yly_float4 tab8">${item.statusText }</div>
						<div class="yly_float4 tab7">
							<fmt:formatDate value="${item.createdTime }" pattern="yyyy-MM-dd"/> 
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>
	<div class="yly_end">没有更多了</div>


</body>
</html>
