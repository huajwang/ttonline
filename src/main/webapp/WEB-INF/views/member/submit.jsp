<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>提交成功</title>
<%@ include file="/common/include.rec.ftl"%>
<style type="text/css">
ul{
    -webkit-margin-after: 0;
    -webkit-padding-start: 0;
}
.tiaozhuan > *{
	width:33.3333%;
}
.tiaozhuan li{
	list-style-type:none;
	float:left;
}
.tiaozhuan li a{
	text-decoration : none;
	color:#ef3030;
	font-size:15px;
	border:1px solid #ef3030;
	border-radius:5px;
	padding:5px 10px;
	margin-left:7%;
}


</style>
</head>

<body >
	
	<div style="width:100%">
		<img src="${ctx}/resources/images/submit.png" style="display:block;margin:0 auto;width:200px;height:200px"/>
	</div>
    <div style="text-align:center;">
    	我们将最晚72小时内相应您的请求
    </div>
    <ul class="tiaozhuan">
    	<li><a href="${ctx}"><span>返回首页</span></a></li>
    	<li><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes">联系客服</a></li>
    	<li><a href="${ctx}/member/getReturn/">查看退换货</a></li>
    </ul>
</body>
</html>
