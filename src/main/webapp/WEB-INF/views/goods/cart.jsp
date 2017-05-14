<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>购物车</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />

<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/icheck.js"></script>
<script src="${ctx}/resources/js/car.js"></script>
<script>
	var addrId = "${address.id}";
	var jsonStr = '${json}';
	$(document).ready(function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-red',
			increaseArea : '20%' // optional
		});
		$('input').on("ifChanged", function(event) {
			var check = event.currentTarget.checked;
			var _this = this;
			setCheck(check, _this)
		});
	});
</script>
</head>

<body>

	<div class="head">
		<ul>
			<li><a href="javascript:window.history.back(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">购物车</li>
			<li><a href="${ctx}/"><img src="${ctx}/resources/images/zhuye1.png"></a></li>
		</ul>
	</div>
	<div class="main">	
		<div class="gwc">
			<ul>
				<c:choose>
					<c:when test="${not empty goods}">
						<c:forEach items="${goods}" var="item" varStatus="vs">
							<c:if test="${ not empty item.id}">
							<li>
								<div class="tplis" >
									<input id="ck_${item.id }${item.propertyId}" gid="${item.id }" type="checkbox">
									<div class="cppic"
										style="background: url(${item.iconUrl }) no-repeat #FFF; background-size: contain" ></div>
									<div class="cpsl">
										<div class="cpsm">${item.gName }
											<c:choose>
												<c:when test="${not empty item.goodsProperty}">
												【
												<c:forEach items="${item.goodsProperty}" var="gproperty"
														varStatus="vs1">
														<c:if test="${gproperty.key=='color' }">
														${gproperty.value }
													</c:if>
														<c:if test="${gproperty.key=='size' }">
														${gproperty.value }
													</c:if>
														<c:if test="${gproperty.key=='style' }">
														${gproperty.value }
													</c:if>
													</c:forEach>
												】
											</c:when>
											</c:choose>
										</div>
										<%-- <input id="num_${item.id }" name="Num" type="text"
											value="${item.gQuantity }" style="width: 40px;"
											disabled="disabled" /> --%>
										<div class="shuliangwzcao">
											<div class="chanpwz yly_color8">数量</div>
											<div
												class="shuliang yly_color8 yly_font_size6 yl_bc22 yl_bc74">
												<div class="yl_bc24 yl_bc75">
													<div id="jian" class="jian yl_bc76" onclick="reduceNum(${item.id }${item.propertyId})">-</div>
													<input id="num_${item.id }${item.propertyId}" class="injorj" type="text" value="${item.gQuantity }" />
													<div id="jia" class="jia yl_bc76" onclick="addNum(${item.id }${item.propertyId})">+</div>
												</div>
											</div>
										</div>
									</div>
									<div class="jg">
										<c:choose>
											<c:when test="${empty item.goodsProperty}">
												<div id="div_${item.id }${item.propertyId}" price="${item.price }" class="jgs">￥${item.price }</div>
											</c:when>
											<c:when test="${not empty item.goodsProperty}">
												<c:forEach items="${item.goodsProperty}" var="gproperty"
													varStatus="vs1">
													<c:if test="${gproperty.key=='price' }">
														<div id="div_${item.id }${item.propertyId}" price="${gproperty.value }"
															class="jgs">￥${gproperty.value }</div>
													</c:if>
												</c:forEach>
											</c:when>
										</c:choose>
										<div class="scsc">
											<i><img
												src="${ctx}/resources/img/sc.png" width="100%" onclick="delCart(${item.cartDetailId })" ></i>
										</div>
									</div>
									<a href="javascript:OpenProduct(${item.id })"></a>
								</div>
							</li>
							</c:if>
							<c:if test="${empty item.id}">
								<div class="kongkong">
									<img src="${ctx}/resources/images/gouwuche3.png" onclick="javascript:window.location.href='${ctx}/';">
								</div>	
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>

			</ul>
		</div>
	</div>

	<div class="foot">
		<div class="qx">
			<input id="checkAll" type="checkbox">全选
		</div>
		<div class="zj">
			总计<span id="total">￥0.00</span>
		</div>
		<div class="qjs">
			<a href="javascript:Settlement()">去结算</a>
		</div>
	</div>

</body>
</html>
