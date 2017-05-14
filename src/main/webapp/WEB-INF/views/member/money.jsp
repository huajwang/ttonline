<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>提现说明</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
</head>

<body class="yly_bg_color1">
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left"><img src="${ctx}/resources/img/back.png" onclick="history.go(-1);" height="20"/></div>
		<div class="or_top_right"><p >提现说明</p></div>
		<a href="${ctx}/member/Feedback/" style="color: white;"><div class="yl_bc48">反馈</div></a>
	</div>
	
	<div class="zwu_ts yl_bc49">
 		<span>蚂蚁小威提现说明：</span>
	</div>
    
    <div class="kongbai" style="height:12rem">
    	<h5>提现说明：<br>
    		1.当您确认收货并且没有进行退货申请时，您的账户余额才会增加，这时方能进行余额提现操作。<br>
		    2.提现审核成功后，资金会在24小时内打入您提供的支付宝、微信钱包账号。<br>
			3.根据微信支付要求，单次提现金额最低为1元，我们建议最好50元以上。
    	</h5>
    </div>
    <div><img src="${ctx}/resources/images/200.JPG" style="padding:0 20%"></div>
</body>
</html>
