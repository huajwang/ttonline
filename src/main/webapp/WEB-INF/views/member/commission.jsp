<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>推荐所得利润说明</title>
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
		<div class="or_top_right"><p >推荐所得利润说明</p></div>
		<a href="${ctx}/member/Feedback/" style="color: white;"><div class="yl_bc48">反馈</div></a>
	</div>
	
	<div class="zwu_ts yl_bc49">
 		<span>蚂蚁小威推荐所得利润说明：</span>
	</div>
    
    <div class="kongbai">
    	<h5>推荐所得利润说明：<br>
		  1.您在商城购买过的或者看到的满意的产品，记得要和好朋友一起分享。<br>
		  2.朋友根据您推荐的产品链接购买的所有产品，其订单总额的10%将会是您利润。<br>
		  3.商城采用只认链接不认上下家模式，利润只产生于一次性的产品链接。<br><br>
		     举个栗子：<br>
		      ①您从朋友甲推荐的A产品链接进入商城，却购买了BCDE产品，您的朋友甲依然可得到BCDE产品总额的10%利润。<br>
		      ②下次您的朋友乙跟你推荐了Z产品链接，您从这个链接进入商城购买的任何产品与朋友甲无任何关系。
    	</h5>
    </div>
    <div><img src="${ctx}/resources/images/fenxiang.jpg" style="padding:0 20%">
    			<img src="${ctx}/resources/images/yongjin.jpg" style="padding:0 20%"></div>
</body>
</html>
