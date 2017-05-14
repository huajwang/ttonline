<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>用户注册</title>
<%@ include file="/common/include.rec.ftl"%>
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="format-detection" content="telephone=no" />
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base_new.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/login.css">
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<script src="${ctx}/resources/js/icheck.js"></script>
<link href="${ctx}/resources/css/red.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-red',
			increaseArea : '20%' // optional
		});
	});
	//获取验证码
	function getCode() {
		var phone = $("#mobile").val();
		if (phone == "") {
			alert("请输入手机号码！");
			return;
		}
		$.post(ctx + "/register/smsCode", {
			phone : phone
		}, function(result) {
			var status = result["status"];
			if (status == "0") {
				alert("注册码已发送，请注意查收！");
			} else {
				alert(result["data"]["msg"]);
			}
		}, 'json');
	}
	//注册功能
	function register() {
		var phone = $("#mobile").val();
		if (phone == "") {
			alert("请输入手机号码！");
			return;
		}
		var code = $("#code").val();
		if (code == "") {
			alert("请输入注册码！");
			return;
		}
		var password = $("#password").val();
		var password1 = $("#password1").val();
		if (password == "") {
			alert("请输入密码！");
			return;
		}
		if (password1 != password) {
			alert("密码不一致，请重新输入！");
			return;
		}
		$.post(ctx + "/register/userRegister", {
			registerCode : code,
			phone : phone,
			password : password
		}, function(result) {
			var status = result["status"];
			if (status == "0") {
				alert("注册成功！");
				var url = ctx + "/";
				window.location.href = url;
			} else {
				var msg = result["data"]["msg"];
				alert(msg);
			}
		}, 'json');
	}
	//校验注册码
	function checkSmsCode() {
		var code = $("#code").val();
		var len = code.length;
		if (len == 6) {
			$.post(ctx + "/register/checkSmsCode", {
				smsCode : code
			}, function(result) {
				var status = result["status"];
				if (status != "0") {
					alert("注册码不存在或已过期，请重新获取！");
					$("#code").val("");
				}
			}, 'json');
		}
	}
</script>
</head>
<body>
	<header>
	<div class="head">
		<ul>
			<li><a href="${ctx}/Login/"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">注册</li>
			<li><a></a></li>
		</ul>
	</div>
	</header>
	<section class="login_wrap">
	<form action="" method="post" class="form_con" id="login_submit">
		<div class="user">
			<span class="icon"><img src="${ctx}/resources/img/phone.png"
				alt=""></span>
			<div class="user_code">
				<input id="mobile" type="text" name="username" placeholder="请输入手机号码"
					value="" subform="login_sub">
				<div class="lis_com indexan" style="width: 100px;"
					onclick="getCode()">获取验证码</div>
			</div>
			<div class="code_con">
				<span class="icon"><img
					src="${ctx}/resources/images/code.png" alt=""></span>
				<div class="code">
					<input id="code" type="text" name="code" placeholder="输入验证码"
						value="" maxlength="6" onkeyup="checkSmsCode()"> <span
						class="del_btn" style="display: none"></span>
				</div>
			</div>
			<div class="pwd">
				<span class="icon"><img src="${ctx}/resources/images/pwd.png"
					alt=""></span>
				<div class="pwd_code">
					<input id="password" type="password" name="password"
						placeholder="输入密码" value="" subform="login_sub"> <span
						class="del_btn" style="display: none"></span>
				</div>
			</div>
			<div class="pwd">
				<span class="icon"><img src="${ctx}/resources/images/pwd.png"
					alt=""></span>
				<div class="pwd_code">
					<input id="password1" type="password" name="password1"
						placeholder="再次输入密码" value="" subform="login_sub"> <span
						class="del_btn" style="display: none"></span>
				</div>
			</div>
		</div>

		<div class="clear"></div>
		<br />
		<div class="login_btn red" id="register">
			<a href="javascript:register()">注册新用户</a>
		</div>
	</form>
	<div class="gome_vip">
		<input type="checkbox" checked="checked">
		<p>我已阅读并同意《蚂蚁小威服务协议》</p>
	</div>
	<div style="margin-top:20px">商城注册说明：<br>1、建议使用个人手机号进行注册，便于找回密码及存储推荐佣金和代理费。<br>2、密码可以为数字、字母或数字+字母的组合。<br>
											3、如您已经注册过商城账号，只需直接登陆即可，无需重复注册。<br>4、其他问题，请联系我们客服人员：0592-2069229。</div>
	</section>

</body>
</html>