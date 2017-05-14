<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>全部分类</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">

<script src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$(".scbtn").click(function(){
		var value=$(".scinp").val();
		if(value==""){
			alert("请输入查询关键字")
			return;
		}
		value=encodeURI(value)
		window.location.href="${ctx}/goods/search?name="+encodeURI(value);
	});
});
</script>

</head>

<body>

	<div class="head">
		<ul>
			<li><a href="javascript:history.go(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="scn" style="width: 500px;margin: 10px 10px; ">
				<input name="" type="text" class="scinp" style="border-radius:4px;">
				<input name="" type="submit" value=" " class="scbtn">
			</li>
			
		</ul>
	</div>
	<div class="main3">
		<div class="tabcon">
			<div id="sidebar_nav" class="hd">
				<ul>
					<c:choose>
						<c:when test="${not empty category}">
							<c:forEach items="${category}" var="item" varStatus="vs">
								<li <c:if test='${vs.index==0 }'> class="on"</c:if>><a>${item.typeName }</a></li>
							</c:forEach>
						</c:when>
					</c:choose>
				</ul>
			</div>

			<div id="category_main" class="bd">
				<c:choose>
					<c:when test="${not empty category}">
						<c:forEach items="${category}" var="item" varStatus="vs">
							<ul>
								<!-- div class="banpic">
									<img src="${ctx}/resources/images/1186dtwap1231.jpg" width="100%">
								</div-->
								<h3>${item.typeName }</h3>
								<c:choose>
									<c:when test="${not empty item.subcategory}">
										<c:forEach items="${item.subcategory}" var="subcategory"
											varStatus="vs1">
											<li><a href="${ctx}/goodlist?type=1&CId=${subcategory.subcategoryId }"><div class="cppicn"
														style="background-image: url(${subcategory.iconUrl })"></div>
													<div class="namen">${subcategory.typeName }</div></a></li>

										</c:forEach>
									</c:when>
								</c:choose>
							</ul>
						</c:forEach>
					</c:when>
				</c:choose>
			</div>

		</div>
	</div>

	<div id="footer">
		<ul>
			<li><a href="${ctx}/" class="nav1"></a></li>
			<li><a href="${ctx}/category/" class="nav2 on"></a></li>
			<li><a href="${ctx}/groupon/" class="nav5"></a></li>
			<!--  <li><a href="${ctx}/activity/productDetail" class="nav5"></a></li>-->
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

	<script type="text/javascript" src="${ctx}/resources/js/SuperSlide.js"></script>
	<script type="text/javascript">
		jQuery(".tabcon").slide();
	</script>
</body>
</html>
