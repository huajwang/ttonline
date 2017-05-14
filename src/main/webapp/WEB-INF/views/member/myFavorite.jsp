<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>商品收藏</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css2.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red2.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />

<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/icheck.js"></script>
<script src="${ctx}/resources/js/collection.js"></script>
</head>

<body>
	<div class="head">
		<ul>
			<li><a href="javascript:window.history.back(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">商品收藏</li>
			<li class="bianji" onclick="showEdit();">编辑</li>
		</ul>
	</div>
	<div class="main">
		<div class="gwc">
			<ul>
				<c:choose>
					<c:when test="${not empty goods}">
						<c:forEach items="${goods}" var="item" varStatus="vs">
							<c:if test="${item.isDel == 0}">
								<li>
									<div class="tplis" >
										<input id="ck_${item.id }" type="checkbox" style="display:none">
										<div class="cppic"
											style="background: url(${item.iconUrl }) no-repeat #FFF; background-size: contain"></div>
										<div class="cpsl">
											${item.gName}
										</div>
										<div class="jg">
											<div id="div_${item.id }" price="${item.price }" class="jgs">￥${item.price }</div>
										</div>
										<a href="javascript:OpenProduct(${item.gId })"></a>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>

			</ul>
		</div>
	</div>

	<div id="footEdit" class="foot" style="display:none">
		<div class="qx">
			<input id="checkAll" type="checkbox">全选
		</div>
		<div class="qjs">
			<a href="javascript:Settlement()">删除</a>
		</div>
	</div>
	
	<input type="hidden" id="state" name="state" value="hide" />

</body>
</html>
