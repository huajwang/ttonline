<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>意见反馈</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script type="text/javascript">
	$(function() {
		$("#Feed").click(function() {
			var opinion = $("#opinion").val();
			if (opinion == "") {
				alert("请输入您的意见！");
				return;
			}
			var email = $("#email").val();
			if (email == "") {
				alert("请输入您的邮箱！");
				return;
			}
			$.post(ctx + "/member/saveOrUpdateFeedback", {
				opinion : opinion,
				email : email
			}, function(result) {
				var status = result["status"];
				if (status == "0") {
					alert("成功提交您宝贵的意见");
					history.go(-1);
				}

			}, 'json');
		});
	});
</script>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20"
				onclick="history.go(-1);" />
		</div>
		<div class="or_top_right">
			<p >意见反馈</p>
		</div>
		<div id="Feed" class="yl_bc48">提交</div>
	</div>

	<div class="fankuirecord">
		<textarea id="opinion" placeholder="写下您的建议吧" maxlength="70"
			onkeyup="checkWordNum(this)" value=""></textarea>

		<div class="fankuinum">
			<span id="word_count">0</span> <span>/70</span>
		</div>
	</div>

	<div class="youxiang">
		<span>您的联络邮箱：</span> <input id="email" class="youxiangin" value=""
			 />
	</div>
</body>
<script>
	function checkWordNum(obj) {
		var maxLength = 70;
		if (obj.value.length > maxLength) {
			obj.value = obj.value.substring(0, maxLength);
		} else {
			var leftwords = maxLength - obj.value.length;
			document.getElementById("word_count").innerHTML = obj.value.length;
			document.getElementById("word").innerHTML = leftwords;
		}
	}
</script>
</html>
