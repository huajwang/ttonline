<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>退换货</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css1.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw1.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/webuploader-0.1.5/webuploader.css">
<script type="text/javascript"
	src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/webuploader-0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uploader.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/submitReturn.js"></script>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left" onclick="javascript:window.history.back(-1);">
			<img src="${ctx}/resources/img/back.png" height="20" />
		</div>
		<div class="or_top_right">
			<p style="margin-left: 30%;">退换货的原因</p>
		</div>
	</div>
	<div class="yly_tui_main">
		<label><strong>退换类型</strong></label><br />
		<div id="type1" class="tui_main_sel yly_bg_color1">
    		<span>我要换货</span>
    		<input class="yl_bc86" type="radio"/>
    		<span class="main_sel_in"></span>
    	</div>
		<div id="type2" class="tui_main_sel yly_bg_color1 yl_bc87">
			<span>我要退货</span> <input class="yl_bc86" type="radio"
				name="operaType"  value="0"/> <span class="main_sel_in"></span>
		</div>	
		<label><strong>退换货原因</strong></label><br />
		<textarea class="tui_main_tea" id="reason" value=""
			placeholder="请输入退换货原因"></textarea>

		<div class="tui_main_file">
			<!--用来存放item-->
			<div>
				<div id="filePicker">+</div>
			</div>
			<div id="thelist" class="uploader-list"></div>
			<div class="mian_file_ti">上传照片，最多三张</div>
		</div>
		<div>
		说明：<br><br>
		1.商城支持7天无理由退货<br>
		2.产品未曾使用、无人为损坏，不影响二次销售<br>
		3.换货非质量及发错货问题由买家承担运费
	</div>
	</div>
	
	<div class="tui_submit" id="apply" onclick="doApply();">提交申请</div>
	<input type="hidden" id="orderDetailId" name="orderDetailId" value="${orderDetailId}">
	<!-- <input type="hidden" id="orderId" name="orderId" value="${orderId}">
	<input type="hidden" id="gId" name="gId" value="${gId}">
	<input type="hidden" id="propertyId" name="propertyId" value="${propertyId}"> -->
</body>




<script>
	$(function(){
		$('#type1').on('click',function(){
			var s = $(this).children('input').prop("checked");
			if(s==false){
				$('#type2').children('input').prop("checked",false);
				$('#type2').find('.main_sel_in').removeClass("main_sel_in_check");
				$(this).children('input').prop("checked",true);
				$(this).find('.main_sel_in').addClass("main_sel_in_check");
			}else{
				$(this).children('input').prop("checked",false);
				$(this).find('.main_sel_in').removeClass("main_sel_in_check");
			}
		});
		$('#type2').on('click',function(){
			var s = $(this).children('input').prop("checked");
			if(s==false){
				$('#type1').children('input').prop("checked",false);
				$('#type1').find('.main_sel_in').removeClass("main_sel_in_check");
				$(this).children('input').prop("checked",true);
				$(this).find('.main_sel_in').addClass("main_sel_in_check");
			}else{
				$(this).children('input').prop("checked",false);
				$(this).find('.main_sel_in').removeClass("main_sel_in_check");
			}
		});
	})
</script>
</html>
