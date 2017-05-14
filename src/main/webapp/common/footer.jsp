<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="public.jsp"%>
</head>
<body>

	<div class="returnup" data-am-smooth-scroll >
		<img alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/return.png" width="100%">
	</div>
	<div class="foo bgcolor2">
		<div id="back" class="foopu">
			<img alt="" src="${ctx}/groupon/assets/img/index/01.png"><br>
			<span>返回商城</span>
		</div>
		<div id="index" class="foopu">
			<img alt="" src="${ctx}/groupon/assets/img/index/02.png"><br>
			<span>拼团首页</span>
		</div>
		<div id="news" class="foopu">
			<img alt="" src="${ctx}/groupon/assets/img/index/03.png"><br>
			<span>消息</span>
		</div>
		<div id="mysepell" class="foopu">
			<img alt="" src="${ctx}/groupon/assets/img/index/04.png"><br>
			<span>我的拼团</span>
		</div>
	</div>
</body>
<script type="text/javascript">
	//返回商城
	$('#back').on('click',function(){
		window.location.href=ctx+"/";
	});
	//拼团首页
	$('#index').on('click',function(){
		window.location.href=ctx+"/groupon/";
	});
	//消息
	$('#news').on('click',function(){
		window.location.href=ctx+"/groupon/news";
	});
	//我的拼团
	$('#mysepell').on('click',function(){
		window.location.href=ctx+"/groupon/personal";
	});
</script>
</html>