<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>项目结算</title>
<%@ include file="/common/public.jsp"%>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: rgb(245, 246, 250);
}
</style>
</head>
<body>
	<!-- 项目信息 -->
	<div class="prosett_top">
		<div class="prosetttopt">
			<span class="prosetttopt_font">项目实际结账：￥ <fmt:formatNumber
					type="number" value="${data.Actual }" pattern="0.00"
					maxFractionDigits="2" />
			</span>
		</div>
		<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:-1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
		<div class="prosetttopt">
			<span>项目总售：￥${data.Total }</span> <span class="prosetttopt_span">退货：￥${data.Refund }</span>
		</div>
		<div class="prosetttopt">
			<span>平台服务费：￥ <fmt:formatNumber type="number"
					value="${data.service }" pattern="0.00" maxFractionDigits="2" />
			</span> <span class="prosetttopt_span">赔偿买家：￥0.00</span>
		</div>
	</div>

	<!-- 账户信息 -->
	<div class="prosett_user bgcolor1">
		<div class="prosettuser">
			<span>提现账户：</span> <span>${data.realName }</span> <span
				class="prosettuser_jia">+</span>
		</div>
	</div>

	<!-- 提现方式 -->
	<div class="prosett_type bgcolor1">
		<div class="prosetttype">
			<input type="checkbox" class="regular-radio" value="0"> <span
				class="radio_select am-icon-circle-thin fontcolor2"></span> <span>
				<img class="prosetttype_img" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/zfb.png">
			</span> <span>支付宝账户</span>
			<!-- <span class="am-icon-trash prosetttype_bc"></span> -->
			<span> <img class="prosetttype_im2" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/more.png">
			</span>
		</div>
		<div class="prosetttype">
			<input type="checkbox" class="regular-radio" value="1"> <span
				class="radio_select am-icon-circle-thin fontcolor2"></span> <span>
				<img class="prosetttype_img" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/wx.png">
			</span> <span>微信账户</span>
			<!-- <span class="am-icon-trash prosetttype_bc"></span> -->
			<span> <img class="prosetttype_im2" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/more.png">
			</span>
		</div>
		<%-- <div class="prosetttype">
			<input type="checkbox" class="regular-radio"> <span
				class="radio_select am-icon-circle-thin fontcolor2"></span> <span>
				<img class="prosetttype_img" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/yl.png">
			</span> <span>银行卡账户</span> <span class="am-icon-trash prosetttype_bc"></span>
			<span> <img class="prosetttype_im2" alt=""
				src="${ctx}/groupon/assets/img/projectmanage/more.png">
			</span>
		</div> --%>

	</div>

	<!-- 项目提现 -->
	<div class="prosett_price bgcolor1">
		<span> <img class="prosettprice_img" alt=""
			src="${ctx}/groupon/assets/img/projectmanage/icon_dl.png">
		</span> <span class="fontcolor9">项目提现</span> <span> <input
			class="prosettprice_input" readonly="readonly"
			value="<fmt:formatNumber type="number" value="${data.Actual }" pattern="0.00" maxFractionDigits="2"/>">
		</span>
	</div>

	<!-- 提现按钮 -->
	<div class="prosett_an bgcolor1">
		<div id="jiesuan" class="prosettan fontcolor1">提现</div>
	</div>

	<!-- 提示 -->
	<div class="prosett_ts">
		<span>注：项目完结后，无任何买卖纠纷，可获得款项将在三个工作日内打到你所提供的账户上；项目总售额奖扣除总额的2%，该费用为平台服务费。</span>
	</div>
</body>
<script type="text/javascript">
	var projectId = "${projectId}";//项目主键Id
	var Refund = "${data.Refund }";//退款金额
	var Actual = "${data.Actual }";//实际金额
	var Total = "${data.Total}";//销售总额
	var Service = "${data.service}";//服务费用
	var alipay="${user.alipaywallet }";//支付包帐号
	var wx="${user.wxwallet }";//微信帐号
	$(function() {

		$('.radio_select').on('click', function() {
			$('.radio_select').prev().prop("checked", false);
			$(this).prev().prop("checked", true);
			$('.radio_select').removeClass('am-icon-circle');
			$('.radio_select').addClass('am-icon-circle-thin');
			$('.radio_select').removeClass('fontcolor10');
			$(this).removeClass('am-icon-circle-thin');
			$(this).addClass('am-icon-circle');
			$(this).addClass('fontcolor10');
		});

		$('.am-icon-trash').on('click', function() {
			$(this).closest('.prosetttype').css('display', 'none');
		});

		//添加提现账户
		$('.prosettuser_jia').on('click', function() {
			window.location.href = ctx + "/groupon/project/project_account";
		});
		$('.prosetttype_im2').on('click', function() {
			window.location.href = ctx + "/groupon/project/project_account";
		});
		//提现结算
		$("#jiesuan").on('click', function() {
			var type = "";//付款方式
			$(".regular-radio").each(function() {
				if (this.checked == true) {
					type = this.value;
				}
			});
			if (type == "") {
				alert("请选择付款方式！");
				return;
			}
			//判断用户支付宝是否为空
			if(type=="0"){
				if(alipay==""){
					alert("您支付宝帐号为空，请先设置帐号！");
					return;
				}
			}
			//判断用户微信帐号是否为空
			if(type=="1"){
				if(wx==""){
					alert("您微信帐号为空，请先设置帐号！");
					return;
				}
			}

			$.post(ctx + '/groupon/api/project/saveOrUpdateSettlement', {
				projectId : projectId,
				Actual : Actual,
				Total : Total,
				Service : Service,
				type : type,
				Refund : Refund
			}, function(result) {
				var json = JSON.parse(result);
				if (json["status"] == "0") {
					alert("提现申请成功！");
					window.location.href = ctx + "/groupon/launch/withdrawal";
				} else {
					alert("提现申请失败！请与管理员联系");
				}
			});

		});
	})
</script>
</html>