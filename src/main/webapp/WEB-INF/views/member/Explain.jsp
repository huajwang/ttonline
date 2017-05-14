<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>购物说明</title>
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
		<div class="or_top_right"><p >购物与退货说明</p></div>
		<a href="${ctx}/member/Feedback/" style="color: white;"><div class="yl_bc48">反馈</div></a>
	</div>
	
	<div class="zwu_ts yl_bc49">
 		<span>蚂蚁小威购物与退货说明：</span>
	</div>
    
    <div class="kongbai" style="height:40rem">
    	<h5>壹、注册与登陆说明：<br>
		  1.建议使用个人手机号进行注册，便于找回密码及存储推荐佣金和代理费，设置密码建议使用字母+数字的组合进行设置，以提高账户安全性。<br>
		  2.退出登录或换号登录请直接点击首页右上角“登录”键。<br>
		  3.如若忘记密码请直接点击首页右上角“登录”键，跳转至登录界面后请点击页面右下角的'找回密码'按钮，方可修改密码。<br>
		  4.已经注册过商城账号的，只需直接登陆即可，无需重复注册，请节约公共资源，共创美好社会。<br><br>
		  <div><img src="${ctx}/resources/images/zhuce.jpg" style="padding:0 20%"> 
		  		<img src="${ctx}/resources/images/denglu.jpg" style="padding:5% 20%"></div>
			贰、购买说明:<br>
		  1.本商城须登录后方能购买产品。<br>
		  2.网页商品图片、介绍、包装情况、零售价等所有信息仅供参考，因页面上商品的的图片为扫描或拍照制成，如发现实物与网页介绍稍有不符，请您以实物为准。<br>
		  3.由于厂家商品资料信息变更而造成的与网页参数的差异，请以实物为准。<br><br>
			叁、退货说明:<br>
		  1.错发、漏发或质量问题直接确认收货，然后到“退款/售后”点击申请退货，填写退货单（记得一定要上传照片喔）；将商品寄回后重新下单；如果在退换货有疑问的请拔打客服电话：0592-2069229或发送至EMAIL：2088151366@qq.com。我们会在24小时进行处理。<br>
		  2.买家申请退货时，若先行评价商品，将导致无法线上退货，如遇该情况请拨打客服电话：0592-2069229，我们的客服人员将为您进行线下退货。<br>
		  3.厂家同意退货后，会自动发送地址到您的账号，请根据地址将商品寄回。<br>
		  4.买家请将商品寄到指定地址，厂家会在48小时内处理退货申请，厂家同意后，退款金额及快递费将打到您的账号。如超过时间没有退换货可拔打客服电话：0592-2069229投诉厂家，我们会在24小时内进行处理。
		  <div><img src="${ctx}/resources/images/322.JPG" style="padding:0 20%"></div>
    	</h5>
    </div>
    
</body>
</html>
