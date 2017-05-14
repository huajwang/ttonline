<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1 ,user-scalable=no">
<title>会员购买</title>
<%@ include file="/common/include.rec.ftl"%>
<link rel="stylesheet" href="resources/vtools/css/fre_color.css" />
<link rel="stylesheet" href="resources/vtools/css/amazeui.flat.css" />
<link rel="stylesheet" href="resources/vtools/css/zjw.css" />
<script type="text/javascript" src="resources/vtools/js/jquery.js"></script>
<script type="text/javascript" src="resources/vtools/js/amazeui.js"></script>
<script src="resources/js/layer/layer.js"></script>
</head>
<body class="fre_bg1">
	<div class="fre_w1" style="margin-bottom: 8rem;">
		<!--
	        	作者：787711932@qq.com
	        	时间：2016-09-07
	        	描述：用户信息
	        -->
		<div class="fre_w1 per_top">
			<table class="fre_w1 theme_co">
				<tr>
					<c:choose>
						<c:when test="${not empty user}">
							<td class="fre_tc"><img width="50px" height="50px"
								class="fre_w05"
								src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${user.iconUrl}" />
							</td>
							<td><strong>${user.userName}</strong></br>${user.phone}</td>
						</c:when>
						<c:otherwise>
							<td class="fre_tc"><img class="fre_w05"
								src="resources/vtools/img/personal/my_personal_18.png" /></td>
							<td><strong>未登录</strong></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty vip}">
							<td>${vip.name}</td>
							<td>剩余${vip.day}天</td>
						</c:when>
						<c:otherwise>
							<td>游客</td>
							<td>无vip特权</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</div>
		<!--
            	作者：787711932@qq.com
            	时间：2016-09-08
            	描述：中间内容
            -->
		<div class="mem_p">
			<div class="fre_w1 m_pbc">
				<span>购买产品：</span> <span id="name" class="fre_fw">试用版</span> <span id="price">5元/3天</span>
			</div>
			<div class="fre_w1 per_menutd2 m_pbc2">
				<span id="Explain">说明：每天能转5篇文章，底下的轮播图只能发布1条关于自己产品的广告；帐号可储存广告条数为1条。</span>
			</div>
			<!--
	            	作者：787711932@qq.com
	            	时间：2016-09-08
	            	描述：版本选择
	            -->
			<div class="fre_w1 per_menutd2 fre_fs5" style="padding-bottom: 2%;">
				<div id="1" val="5" class="theme_co fre_bor m_pbc4 mem_p_on">
					<table class="fre_w1">
						<tr>
							<td>试用版</td>
							<td>5元/3天</td>
							<td>每个用户只能购买一次</td>
						</tr>
					</table>
				</div>
				<div id="2" val="30" class="theme_co fre_bor m_pbc4">
					<table class="fre_w1">
						<tr>
							<td style="width: 19%;">普通版</td>
							<td>30元/月</td>
						</tr>
					</table>
				</div>
				<div id="3" val="50" class="theme_co fre_bor m_pbc4">
					<table class="fre_w1">
						<tr>
							<td style="width: 19%;">高级版</td>
							<td>50元/月</td>
						</tr>
					</table>
				</div>
				<div id="4" val="360" class="theme_co fre_bor m_pbc4">
					<table class="fre_w1">
						<tr>
							<td style="width: 19%;">企业版</td>
							<td>360元/月</td>
						</tr>
					</table>
				</div>
			</div>
			<!--
                	作者：787711932@qq.com
                	时间：2016-09-08
                	描述：按钮
                -->
			<div id="pay" class="m_pbc5 thene_bg fre_co1">立即支付</div>
		</div>
	</div>
	<!--
        	作者：787711932@qq.com
        	时间：2016-09-07
        	描述：尾部导航
        -->
	<div class="fre_w1 fre_foo fre_bg2">
		<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc inject">
			<span> <img
				src="resources/vtools/img/personal/my_personal_24.png" />
			</span><br /> <span class="theme_co fre_fs4">广告注入</span>
		</div>
		<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc wgmember">
			<span> <img
				src="resources/vtools/img/personal/my_personal_21.png"
				style="width: 19%" />
			</span><br /> <span class="theme_co fre_fs4">微广会员</span>
		</div>
		<div class="fre_w03-3 fre_fl fre_fo_meu fre_tc personal">
			<span> <img
				src="resources/vtools/img/personal/my_personal_18.png"
				style="width: 16%" />
			</span><br /> <span class="theme_co fre_fs4">我的中心</span>
		</div>
	</div>
	<c:if test="${not empty notice}">
		<input type="hidden" id="notice" value="${notice}">
	</c:if>
</body>
<script>
	$(function() {
		if($("#notice").val()!=null)
			alert($("#notice").val());
		//我的中心
		$('.personal').on('click', function() {
			window.location.href = "vtools/vmember";
		});
		//广告注入
		$('.inject').on('click', function() {
			window.location.href = "vtools/advertise/editPage";
		});
		//微广会员
		$('.wgmember').on('click', function() {
			window.location.href = "vtools/vmember/pay";
		});
		//选择
		$('.m_pbc4').on('click', function() {
			$('.mem_p_on').removeClass('mem_p_on');
			$(this).addClass('mem_p_on');
			var id = $('.mem_p_on').attr("id");
			$("#name").html($(this).find("table tr td")[0].innerHTML);
			$("#price").html($(this).find("table tr td")[1].innerHTML);
			if(id=="1"){
				$("#Explain").html("说明：每天能转5篇文章，底下的轮播图只能发布1条关于自己产品的广告；帐号可储存广告条数为1条。");				
			}
			if(id=="2"){
				$("#Explain").html("说明：每天只能转发5篇文章，底下的轮播图只能发布1条关于自己的产品的广告，账号可储存1条。");				
			}
			if(id=="3"){
				$("#Explain").html("说明：每天只能转发10篇文章，底下的轮播图只能发布三条关于自已的广告，账号可以储存5条。");				
			}
			if(id=="4"){
				$("#Explain").html("说明：每天随便转发文单，底下的轮播图可以三第以上关于自己的广告，账号可以储存10条。");				
			}
			
		});
		//立即支付
		$("#pay").on('click', function() {
			//支付金额
			var val = $('.mem_p_on').attr("val");
			if (val == null || val == undefined) {
				layer.msg("请选择购买产品");
				return;
			}
			//VIP表主键值
			var id = $('.mem_p_on').attr("id");
			callpay(id, val)
		});
	})

	//调用微信JS api 支付
	function jsApiCall(json) {
		WeixinJSBridge.invoke('getBrandWCPayRequest', json, function(res) {
			WeixinJSBridge.log(res.err_msg);
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				alert("支付成功！");
			} else {
				alert("支付失败！");
			}
		});
	}

	function callpay(id, val) {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', jsApiCall,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', jsApiCall);
				document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			}
		} else {
			var r = $.ajax({
				type : "post",
				url : ctx + '/vtools/logwx/GetOrderResult?vipId=' + id
						+ '&Money=' + val,
				async : false
			}).responseText;
			var json = eval('(' + r + ')');
			jsApiCall(json)
		}
	}
</script>
</html>

