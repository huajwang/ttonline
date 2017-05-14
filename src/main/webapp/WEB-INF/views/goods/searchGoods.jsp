<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>商品列表</title>
<%@ include file="/common/include.rec.ftl"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/yly_zjw.css" />
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">
<script type="text/javascript">
	//打开产品详情
	function OpenProduct(id){
		var url=ctx+"/productDetail?productId="+id;
		window.location.href=url;
	}

</script>
</head>
<body class="yly_bg_color1">

	<a style="width: 100%; height: 1px;" name="atop">1</a>
	<div class="lis_top">
		<div class="yl_bc53">
			<img src="${ctx}/resources/img/back.png" height="20"
				onclick="javascript:history.go(-1);" />
		</div>
		<div class="yl_bc54">商品列表</div>
	</div>
	<div class="lis_reco">
		<c:choose>
			<c:when test="${not empty goods}">
				<c:forEach items="${goods}" var="item" varStatus="vs">
					<div class="lis_pro" onclick="OpenProduct(${item.id})">
						<div class="lis_pro_img">
							<img
								src="${item.iconUrl}" />
						</div>
						<div class="lis_pro_name">${item.gName}</div>
						<div class="lis_pro_price">
							<div class="pric_ban yl_bc51">￥${item.price}</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>



	</div>
	<!--
		  	作者：787711932@qq.com
		  	时间：2016-04-28
		  	描述：返回顶部
		  -->
	<a class="yl_bc32" href="#atop">顶部</a>

	<div id="footer">
		<ul>
			<li><a href="${ctx}/" class="nav1"></a></li>
			<li><a href="${ctx}/category/" class="nav2 on"></a></li>
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
			<li><a href="${ctx}/member/" class="nav4"></a></li>
		</ul>
	</div>

</body>
</html>
