<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>订单支付</title>
<%@ include file="/common/public.jsp"%>
<script src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript">
	var amount = "${order.amount }";//支付金额
	var OpenId = "${OpenId }";//微信的OpenId
	var orderId = "${order.id }";//订单主键
	
	function WXPay(){
		if (!isWeiXin()) {
			layer.msg("请在微信浏览器支付");
			return;
		}
	}
	
	//支付宝支付
	function alipay(){
		document.forms['alipaysubmit'].submit();
	}
</script>
</head>
<body class="bgcolor2">
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg" alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px;display: inline-block;">订单支付</span>
		<span id="quren" class="pro_jb" style="margin-left: -17%;">关闭</span>
	</div>
	
	<div class="main2">
    <div class="gonggao">请在24小时内完成支付，部分商品支付时效为15或30分</div>
    
    <div class="oreder">
     <div class="orderico"><img src="${ctx}/groupon/assets/img/index/oreder.png" width="100%" /></div>
     <div class="oredernr">订单提交成功，请尽快付款！<span>应付金额：<i class="ztcolor">￥${order.amount }</i></span></div>
    </div>
    
    <div class="zflist">
	     <ul>
	       <li>
	      	<a href="javascript:alipay();">
		       <div class="zfico"><img src="${ctx}/groupon/assets/img/index/zfb.png" width="100%" /></div>
		       <div class="zfnr">支付宝<span class="ztcolor">推荐 ! 支持花呗支付</span>
			       <div class="jt"></div>
		       </div>
	      	</a>
      	  </li> 
	      
	      <li>
	        <a href="javascript:WXPay();" >
		       <div class="zfico"><img src="${ctx}/groupon/assets/img/index/wx.png" width="100%" /></div>
		       <div class="zfnr">微信支付<span>建议已安装微信的用户使用</span>
		       	<div class="jt"></div>
		       </div>
	        </a>
	      </li>
	      
	      <!-- <li>
	      	 <a href="">
	      		<div class="zfico"><img src="${ctx}/groupon/assets/img/index/yl.png" width="100%" /></div>
	       		<div class="zfnr">银联在线支付<span>支持银联储蓄卡，信用卡支付</span>
	       			<div class="jt"></div>
	       		</div>
	      	 </a>
      	  </li>
	      
	      <li>
	      	  <a href="">
		        <div class="zfico"><img src="${ctx}/groupon/assets/img/index/hy.png" width="100%" /></div>
		        <div class="zfnr">微信大夫<span>好友代付，退款时将退给代付方</span>
		        	<div class="jt"></div>
		        </div>
	      	  </a>
      	  </li> -->
	      
	      
	     </ul>
    </div>
    
${sHtmlText}
 </div>
</body>
</html>