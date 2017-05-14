<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>收益记录</title>
<%@ include file="/common/include.rec.ftl"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" href="${ctx}/resources/css/gainings.css" />
<style type="text/css">
body {
	padding: 0;
	margin: 0;
}
</style>


</head>
<body>
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20" onclick="history.go(-1);" />
		</div>
		<div class="or_top_right" style="line-height: 1rem">
			<p>收益记录</p>
		</div>
		
	</div>
	<div class="zhuti">
		<div class="zhong">
			<div class="up">
				<div class="shang">
					<div class="shang1">
						<strong class="strong1">我分享所得利润(元)</strong>
					</div>
					<div class="shang2">
						<strong class="h3_detail">${map.cr1 }</strong>
					</div>
				</div>
				<div class="shang_right">
					<div class="shang1">
						<strong class="strong1">我代理产品所得利润(元)</strong>
					</div>
					<div class="shang2">
						<strong class="h3_detail">${map.mcr0 }</strong>
					</div>
				</div>
			</div>
			<div class="xia">
				<div class="xleft">
					<div class="xiaa1">
						<strong>未结算利润(元)</strong>
					</div>
					<div class="xiaa2">
						<strong>${map.cr0 }</strong>
					</div>
				</div>
				<div class="xright">
					<div class="xiab1">
						<strong>未结算利润(元)</strong>
					</div>
					<div class="xiab2">
						<strong>${map.mcr1 }</strong>
					</div>
				</div>
			</div>
		</div>
		<div class="bottom1">
			<div class="bt1">
				<div class="bta_a1"></div>
				<div class="bta_a2">昨日</div>
				<div class="bta_a3">近七天</div>
				<div class="bta_a4">近一个月</div>
			</div>
			<div class="bt2">
				<div class="btb_b1">
					<div class="btb_bb1">
						<strong>分享</strong>
					</div>
					<div class="btb_bb2">成交量</div>
				</div>
				<div class="btb_b2">${map1.count }</div>
				<div class="btb_b3">${map2.count }</div>
				<div class="btb_b4">${map3.count }</div>
			</div>

			<div class="bt3">
				<div class="btc_c1">收益金额</div>
				<div class="btc_c2">${map1.sum }</div>
				<div class="btc_c3">${map2.sum }</div>
				<div class="btc_c4">${map3.sum }</div>
			</div>
			<div class="bt4">
				<div class="btb_b1">
					<div class="btb_bb1">
						<strong>代理</strong>
					</div>
					<div class="btb_bb2">成交量</div>
				</div>
				<div class="btb_b2">${map4.count }</div>
				<div class="btb_b3">${map5.count }</div>
				<div class="btb_b4">${map6.count }</div>
			</div>
			<div class="bt5">
				<div class="btc_c1">收益金额</div>
				<div class="btc_c2">${map4.sum }</div>
				<div class="btc_c3">${map5.sum }</div>
				<div class="btc_c4">${map6.sum }</div>

			</div>
			<div class="bt6">
				<strong style="font-size: 10px;">未结算利润说明:要等到产品客户验收后,没有退货，才能确定成交.</strong>
			</div>
			<div class="bt7">
				<h2>共享家商城投诉：0592-2069229</h2>
			</div>
		</div>

	</div>
</body>
</html>
