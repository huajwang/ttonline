<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>余额提现</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script src="${ctx}/resources/js/wallet.js"></script>
<script type="text/javascript">
	function getRecord() {
		var url = ctx + "/member/getWithdrawalRecord/";
		window.location.href = url;
	}
</script>
</head>

<body class="yly_bg_color1">

	<div class="yly_tixian">
		
		<div class="yly_or_top">
				<div class="or_top_left"><img src="${ctx}/resources/img/back.png" height="20"  onclick="history.go(-1);" /></div>
					<div class="or_top_right">
						<p>账户余额</p>
					</div>
				</div>

		<div class="usereco">
			
			<div class="usereco_zt">
				<div class="usereco_zt_top">
					<div class="reco_zt_top_l">
						<img src="${ctx}/resources/images/bao1.png" height="60%"
							style="margin-top: 0.4rem;" />
					</div>
					<div class="reco_zt_top_r yly_font_size2">账户余额</div>
				</div>

				<div class="usereco_zt_con yly_font_size40 font_w1">
					<div class="yly_float3">￥${total}</div>
				</div>
				
				

				<div class="usereco_zt_bot">
					<div class="yly_float1">可用余额：</div>
					<div class="yly_float1">￥${available}</div>
				</div>
				<div class="clear"></div>
				<div class="usereco_zt_bot">
					<div class="yly_float1">提现处理中：</div>
					<div class="yly_float1">￥${process}</div>
				</div>

			</div>
		</div>

		<div class="tixian_pr">
			<div class="tixian_pr_zt">
				<div class="tixian_pr_zt_top">
					<div class="pr_zt_top">
						<div class="usereco_zt_top">
							<div class="reco_zt_top_l">
								<img src="${ctx}/resources/images/yue.png" height="60%"
									style="margin-top: 0.6rem;" />
							</div>
							<div class="reco_zt_top_r yly_font_size2 yly_color1">余额提现</div>
						</div>
					</div>
					<input class="pr_zt_bot" type="text" value="" id="withdrawalAccount"
						onkeyup="this.value=this.value.replace(/[^\d\.*\d{0,2}]/g,'')"  />
					<div class="pr_zt_top">
						<div class="usereco_zt_top">
							<div class="reco_zt_top_l">
								<img src="${ctx}/resources/images/yue.png" height="60%"
									style="margin-top: 0.6rem;" />
							</div>
							<div class="reco_zt_top_r1 yly_font_size2 yly_color1">提现账户</div>
							<a href="${ctx}/member/setPayMethod/">
								<div class="reco_zt_top_r2">
									<img src="${ctx}/resources/images/tianjia2.png" height="60%"
										style="margin-top: 0.6rem;" />
								</div>
							</a>
						</div>
					</div>
				</div>
				<c:forEach items="${wallets}" var="item" varStatus="vs">
					<c:if test="${not empty item.alipaywallet}">
						<!-- 此处循环罗列支付方式 -->
						<div class="pr_zt_top">
							<div class="usereco_zt_top" onclick="">
								<div class="paymethod_top_l">
								<input name="payment" type="radio" value="zfb" >
								</div>
								<div class="paymethod_top_l1">
									<img src="${ctx}/resources/img/zfb.png" height="60%"
										style="margin-top: 0.1rem;" />
								</div>
								<div class="paymethod_top_l2 yly_font_size2 yly_color1">支付宝账户</div>
								</input>
								<input type="hidden" id="zfb" value="${item.alipaywallet}">
								<div class="paymethod_top_r" onclick="">
									<a href="${ctx}/member/setPayMethod?paymethod=zfb">
										<img src="${ctx}/resources/img/more.png" height="60%"
											style="margin-top: 0.1rem;" />
									</a>
								</div>
								<div class="paymethod_top_r" onclick="delWallet('zfb')">
									<img src="${ctx}/resources/img/sc.png" height="60%"
										style="margin-top: 0.1rem;" />
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty item.wxwallet}">
						<!-- 此处循环罗列支付方式 -->
						<div class="pr_zt_top">
							<div class="usereco_zt_top" onclick="">
								<div class="paymethod_top_l">
								<input name="payment" type="radio" value="wx" >
								</div>
								<div class="paymethod_top_l1">
									<img src="${ctx}/resources/img/wx.png" height="60%"
										style="margin-top: 0.1rem;" />
								</div>
								<div class="paymethod_top_l2 yly_font_size2 yly_color1">微信账户</div>
								</input>
								<input type="hidden" id="wx" value="${item.wxwallet}">
								<div class="paymethod_top_r">
									<a href="${ctx}/member/setPayMethod?paymethod=wx">
										<img src="${ctx}/resources/img/more.png" height="60%"
											style="margin-top: 0.1rem;" />
									</a>
								</div>
								<div class="paymethod_top_r" onclick="delWallet('wx')">
									<img src="${ctx}/resources/img/sc.png" height="60%"
										style="margin-top: 0.1rem;" />
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
				
				<div class="pr_zt_bot_bo yl_bc41 yly_font_size2 indexan" style="cursor:pointer;" onclick="withdrawal();">提现</div>
				<div class="pr_zt_bot_bo yl_bc42 yly_font_size2 "
					onclick="getRecord();">提现记录</div>

			</div>
		</div>

			

		<div class="jiange"></div>
		<div class="tousu">
			<span>1.提现审核后，资金直接打入您提供的支付宝、微信钱包账号;</span><br /> <span>2.根据微信支付要求，单次提现金额最低为1元，我们建议最好50元以上。</span><br />
			<span>&nbsp;&nbsp;共享家商城投诉：0592-2069229</span><br />
		</div>

	</div>

</body>
</html>
