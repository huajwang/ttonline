<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<meta http-equiv="X-UA-Compatible" content="chrome=1"> 
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>下载二维码</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">

<script src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.js"></script>
<script src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/qrcode/jquery.qrcode.min.js"></script>

</head>
<body>

	<div class="head">
		<ul>
			<li><a href="javascript:history.go(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">长按下载二维码</li>
			<li></li>
		</ul>
	</div>
	<div class="main" style="background: none">
			<!-- 二维码 -->
			<!-- <div id="code" class="qrcode"></div>  -->
			<div id="qrrcode" class="qrcode">
				<img id="test" src="${ctx}/getQrcode?gid=${gid}"/>
				</br>
				<p style="color:red;">${notice}</p>
			</div>
			<!-- 二维码 -->
			
	</div>
	<input type="hidden" id="qrurl" value="${qrurl}"/>
	

</body>

<script>
//将jqrcode生成的二维码canvas放入img
//	$(function() {
//		$("#code").empty();
//		$("#code").qrcode({
	//		text : $("#qrurl").val(),
	//		src : '${ctx}/resources/img/logo/logo.jpg'
	//	});
	//	var myCanvas = document.getElementById("qrcanvas");
		//var imag = document.getElementById("test");
	//	imag.src = myCanvas.toDataURL("image/png");
		//$("#code").hide();
	//})

</script>


</html>