<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>添加提现账户</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />

<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/wallet.js"></script>

</head>

<body>
	<div class="head">
		<ul>
			<li><a href="javascript:window.history.back(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">添加提现账户</li>
		</ul>
	</div>
	<div class="main">
		<div class="gwc">
			<div class="tixian_pr">
				<div class="tixian_pr_zt">
				<c:if test="${ empty mymethod or mymethod == 'zfb' }">
					<div class="pr_zt_top">
						<div class="usereco_zt_top">
							<div class="paymethod_top_l1">
								<img src="${ctx}/resources/img/zfb.png" height="60%"
									style="margin-top: 0.6rem;" />
							</div>
							<div class="paymethod_top_l3 yly_font_size2 yly_color1">支付宝账户:</div>
							<div class="paymethod_top_r2">
								<input class="pr_zt_bot1" type="text" id="alipayAccount" value="${alipaywallet}"></input>
							</div>

						</div>
					</div>
				</c:if>
				<c:if test="${ empty mymethod or mymethod == 'wx' }">
					<div class="pr_zt_top">
						<div class="usereco_zt_top">
							<div class="paymethod_top_l1">
								<img src="${ctx}/resources/img/wx.png" height="60%"
									style="margin-top: 0.6rem;" />
							</div>
							<div class="paymethod_top_l3 yly_font_size2 yly_color1">微信账户:</div>
							<div class="paymethod_top_r2">
								<input class="pr_zt_bot1" type="text" id="wxAccount" value="${wxwallet}"></input>
							</div>

						</div>
					</div>
				</c:if>
				<div class="pr_zt_bot_bo yl_bc41 yly_font_size2 indexan" onclick="doSubmit();">确  认</div>
				</div>
			</div>
		</div>

	</div>
	<input type="hidden" id="state" name="state" value="hide" />
	
</body>
</html>
