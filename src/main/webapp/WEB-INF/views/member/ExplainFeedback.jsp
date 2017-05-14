<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>商城说明与反馈</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left"><img src="${ctx}/resources/img/back.png" onclick="history.go(-1);" height="20"/></div>
		<div class="or_top_right"><p >商城说明与反馈</p></div>
	</div>
	
	<div class="gouzt">
		<a href="${ctx}/member/explain/"><div class="gouzt_one">1、购物与退货说明</div></a>
		<a href="${ctx}/member/commission/"><div class="gouzt_one">2、推荐所得利润说明</div></a>
		<a href="${ctx}/member/maker/"><div class="gouzt_one">3、创客代理说明</div></a>
		<a href="${ctx}/member/factory/"><div class="gouzt_one">4、厂家免费入驻说明</div></a>
		<a href="${ctx}/member/money/"><div class="gouzt_one">5、提现说明</div></a>
		<a href="${ctx}/member/Feedback/"><div class="gouzt_one">6、反馈</div></a>
		<div class="zwu_ts yl_bc47">
	 		<span>蚂蚁小威</span>
	 		<span>投诉：0592-2069229</span>
 		</div>
	</div>
    
 
</body>
</html>