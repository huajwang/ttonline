<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>添加提现账户</title>
<script type="text/javascript">
	$(function(){
		//保存
		$(".Add_cash_btn1").click(function(){
			var alipay = $("#alipayAccount").val();
			var wx = $("#wxAccount").val();
			var r = $.ajax({
		        type: "post",
		        url: ctx + "/member/updateWallet",
		        async: false,
		        data: {
		        	"alipay" : alipay,
		        	"wx" : wx
		        }
			 }).responseText;
			var json=eval("("+r+")");
			if(json["result"]=="success"){
				window.history.back(-1);
			}else{
				window.history.back(-1);
			};
		});
		//返回
		$(".Add_cash_btn2").click(function(){
			history.go(-1); 
			location.reload(); 
		});
	});
</script>
<style type="text/css">
</style>
</head>
<body style="background-color: #fff; margin: 0; padding: 0;">
	<div class="Add_cash">
		<img class="Add_cash_img"
			src="${ctx}/groupon/assets/img/projectmanage/zfb.png" />
		<div class="Add_cash_text">支付宝账号:</div>
		<input id="alipayAccount" class="Add_cash_input" type="text" name="" value="${user.alipaywallet }" />
	</div>

	<div class="Add_cash" style="margin-top: -12px;">
		<img class="Add_cash_img"
			src="${ctx}/groupon/assets/img/projectmanage/wx.png" />
		<div class="Add_cash_text">微信账户:</div>
		<input id="wxAccount" class="Add_cash_input" type="text" name="" value="${user.wxwallet }" />
	</div>
	<%-- <div class="Add_cash" style="margin-top: -12px;">
		<img class="Add_cash_img"
			src="${ctx}/groupon/assets/img/projectmanage/yl.png" />
		<div class="Add_cash_text">
			<strong>银行卡账户</strong>
		</div>
	</div>
	<div class="Add_cash_kaihu"
		style="margin-top: 15px; margin-bottom: 8px;">
		<div class="Add_cash_kaihu_left">
			<span>开户银行</span>
		</div>
		<div class="help_jihuo">
			<select name="test2" class="xl_xlkuan">
				<option value="1" class="xia_la_1 ">选择钱包</option>
				<option value="2" class="xia_la_1">qq钱包</option>
				<option value="3" class="xia_la_1">微信钱包</option>
				<option value="4" class="xia_la_1">支付宝</option>
			</select>
			<div class="Add_cash_kaihu_img">
				<img style="width: 90%; margin-top: 6.5px; margin-left: 100%;"
					src="${ctx}/groupon/assets/img/projectmanage/xiabiao1.png" />
			</div>
		</div>
	</div> 

	<div class="Add_cash_a">
		<div class="Add_cash_card">
			<span class="Add_cash_id">开户姓名</span>
		</div>
		<div class="Add_cash_input_shuru">
			<input class="Add_cash_input_jihuo" type="text"
				placeholder=" &nbsp;填写开户人姓名" />
		</div>
	</div>

	<div class="Add_cash_a">
		<div class="Add_cash_card">
			<span class="Add_cash_id">银行卡号</span>
		</div>
		<div class="Add_cash_input_shuru">
			<input class="Add_cash_input_jihuo" type="text"
				placeholder=" &nbsp;填写银行卡号" />
		</div>
	</div>--%>

	<div class="Add_cash_line"></div>

	<div class="Add_cash_btn1">保存</div>
	<div class="Add_cash_btn2">返回</div>


</body>
</html>