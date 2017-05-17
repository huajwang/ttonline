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
<title>用户登录</title>
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
	href="${ctx}/resources/css/css.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base_new.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/login.css">
<script src="${ctx}/resources/js/jquery.md5.js"></script>
<script type="text/javascript">	
	var path="${path}";
	var OpenId="${OpenId}";
	var IsTrue="${IsTrue}";
	$(function(){
		/*if(isWeiXin()){
			var code = Request["code"];
			if(code==undefined){
				$.post(ctx + "/WXLogin",function(result) {
					window.location.href=result["url"];
				}, 'json');
			}
		}*/
		$("#password").click(function(){
			$("#login_sub").addClass("red");
		})
	})
	
	function WXLogin(){
		if(isWeiXin()){
			var username=$("#username").val();
			if(IsTrue=="true"){
				if(username==""){
					alert("您好！你是第一次登录，请提供手机号码！");
					$("#username").attr("placeholder","请输入手机号码")
					return;
				}
			}
			$.post(ctx + "/WXLoginByOpenId", {phone:username,OpenId:OpenId},function(result) {
				var status = result["status"];
				if(status=="0"){
					clearCookie("username");
					clearCookie("password");					
					setCookie("OpenId", OpenId, 30);
					var productId=getCookie("productId");
					var userId=getCookie("userId");
					var url="";
					if(productId!=""&&productId!=undefined&&path==""){
						var url=ctx+"/productDetail?productId="+productId
						if(userId!=""&&userId!="undefined"){
							url+="&userId="+userId;
						}
					}
					else{
						url=ctx+"/member/";
					}
					window.location.href=url;
				}
			}, 'json');
		}
		else{
			alert("请在微信打开该网页！");
			return;
		}
	}
</script>
</head>
<body>
	<div class="head">
		<ul>
			<li><a href="${ctx}"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit">登陆</li>
			<li><a href="${ctx}/register/" class="reg_btn"
				style="width: 10%; line-height: 44px">注册</a></li>
		</ul>
	</div>
	</header>

	<section class="login_wrap">
	<form action="/ttmall/login" method="post" class="form_con" id="login_submit">
		<div class="user">
			<span class="icon"><img src="${ctx}/resources/img/user.png"
				alt=""></span>
			<div class="user_code">
				<input id="username" type="text" name="username"
					placeholder="输入手机号码登陆" value="" subform="login_sub"> <span
					class="del_btn" style="display: none"></span>
			</div>
		</div>
		<div class="pwd">
			<span class="icon"><img src="${ctx}/resources/img/pwd.png"
				alt=""></span>
			<div class="pwd_code">
				<input id="password" type="password" name="password"
					placeholder="密码" value="" subform="login_sub"> <span
					class="del_btn" style="display: none"></span>
			</div>
		</div>


		<div class="clear"></div>
		<div class="login_btn" id="login_sub">
			<input name="submit" type="submit" value="登录"/>
		</div>
		<div class="login_resetpwd" id="login_resetpwd">
			<a href="${ctx}/member/retrievePassword">找回密码</a>
		</div>
		<div>
			商城登陆说明：<br>1、登录名为您注册时使用的手机号码。<br>2、登录密码为注册时设置的密码，可能是数字、字母或数字+字母的组合。<br>
			3、若您忘记密码请点击右下角的'找回密码'按钮。<br>4、如还有问题，请联系我们客服人员：0592-2069229。
		</div>
	</form>


	<div class="other_login" style="display: none">
		<a href="javascript:void(0)"
			onclick="third_login(&quot;qq&quot;,&quot;qq&quot;);" id="qq"><img
			src="${ctx}/resources/img/qq.png"></a> <a href="javascript:void(0)"
			onclick="third_login(&quot;alipay&quot;,&quot;支付宝&quot;);"
			id="alipay"><img src="${ctx}/resources/img/pay.png"></a> <a
			href="javascript:void(0)"
			onclick="third_login(&quot;sina&quot;,&quot;新浪&quot;);" id="sina"><img
			src="${ctx}/resources/img/wibo.png"></a> <a
			href="javascript:WXLogin();" id="more_btn"><img
			src="${ctx}/resources/img/more.png" alt="微信登录"></a>
	</div>
	</section>
</body>
</html>