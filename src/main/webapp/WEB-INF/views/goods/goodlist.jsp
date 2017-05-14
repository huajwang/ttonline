<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>商品列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="applicable-device" content="mobile">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<%@ include file="/common/include.rec.ftl"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/goods_list.css">
<script type="text/javascript">
	var goodJson = '${DataJson}';
	$(function() {
		$(".search_btn").click(
				function() {
					var value = $("#search_input").val();
					if (value == "") {
						alert("请输入查询关键字")
						return;
					}
					value = encodeURI(value)
					window.location.href = "${ctx}/goods/search?name="
							+ encodeURI(value);
				});
	});
</script>
<style type="text/css">
.yl_bc32{
	position: fixed;
	width: 3rem;
	height: 3rem;
	background-color: #ccc;
	border-radius: 25px;
	bottom: 6rem;
	right: 1rem;
	filter:alpha(opacity=60);
	-moz-opacity:0.6;
	-khtml-opacity: 0.6;
	opacity: 0.6;
	line-height: 3rem;
	text-align: center;  
	z-index: 1;
}
</style>
<!-- 返回顶部 -->
<script type="text/javascript">
$(document).ready(function(){
  $("#returnTop").click(function(){
    $("body").animate({scrollTop: '0px'}, 200);
  });
});
</script>
</head>
<body>
<!-- 	<a name="atop"></a> -->
	<article class="goods_list">
		<nav class="toolbar">
			<div class="title ">
				<p onclick="window.history.back(-1);"
					class="nav_return nav_ico float_l" id="nav_return" data-icon="0"></p>

				<p id="nav_more"></p>
				<span class="nav_home nav_ico float_r" id="nav_home"
					style="cursor: pointer;"
					onclick="javascript:window.location.href='${ctx}/';" ><img src="${ctx}/resources/images/zhuye2.PNG"
					style="cursor: pointer;margin-top:1.1rem" ></span>

				<div class="list_search">
					<div class="search_module">
						<input type="hidden" id="default_kwd" value=""
							placeholder="请输入你要购买的商品">

						<div class="search_bar">
							<p class="cancle">取消</p>

							<div class="search">
								<div class="search_btn" data-icon="3"></div>
								<div class="search_clear" data-icon="?"></div>
								<div class="search_item" data-icon="C" data-val="0">商品</div>
								<div class="search_input_bar">
									<input id="search_input" type="search" value=""
										placeholder="请输入关键字" input_key="平板电视">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</nav>
		<script type="text/javascript" src="${ctx}/resources/js/wap_nav.js"></script>
		<div class="Tabs_module">
			<a href="javascript:changeState(1);" id="general_show"><span
				class="tab_ckd">综合</span> <span class="general_red_down"></span><span
				class="general_red_up" style="display: none;"></span> </a> <a
				href="javascript:changeState(2);" id="sales_show"><span>销量</span></a>
			<a class="pos_r" href="javascript:changeState(3);" id="price_show">
				<span>价格</span> <span class="sort gray"></span>
			</a> <a class="filter_btn slide_ctrl" id="nav_filter" data-id="filter"></a>

		</div>
		<ul class="general_list" style="display: none;">
			<a>
				<li class="checkin">综合<span class="right_narrow"></span></li>
			</a>
			<a href="#">
				<li style="border-bottom: none;" id="appraise_check">评价<span></span></li>
			</a>

		</ul>
		<div class="con_mask"></div>
		<a id="returnTop" class="yl_bc32">顶部</a>
		<ul class="goods_list_module" id="goods_list">
			<c:choose>
				<c:when test="${not empty goods}">
					<c:forEach items="${goods}" var="item" varStatus="vs">
						<li class="gd_list"><a
							href="${ctx}/productDetail?productId=${item.id }"> <span
								class="gd_img"><img src="${item.iconUrl}" onerror="" /></span>
								<div>
									<strong class="title ellipsis_two"> <span
										class="isBbc_or">自营</span> ${item.gName}
									</strong>

									<div class="price_warp">
										<span class="price"> <b>¥${item.price}</b>
										</span>
									</div>
									<c:choose>
										<c:when test="${item.sales==0 }">
											<span class="cmt">${item.evaluation}人评论 销量：${item.sales}</span>
										</c:when>
										<c:otherwise>
											<span class="cmt">${item.evaluation}人评论 销量：${item.sales+50}</span>
										</c:otherwise>
									</c:choose>
								</div>
						</a></li>
					</c:forEach>
				</c:when>
			</c:choose>


		</ul>
		<p id="page_dom" style="display: none;">
			<span id="currentpage">1</span>/195
		</p>

		<div class="scroll_load" id="loading">
			<span class="load">正在加载</span>
		</div>
		<div class="scroll_load" id="no_more">
			<span class="nomore">没有更多商品了……</span>
		</div>
	</article>
</body>
<script type="text/javascript">
	var _num = 1;//单数为降序排序  双数为升序排序
	//切换 综合 销量 价格
	function changeState(type) {
		var span1 = $("#general_show").children(":first");
		var span2 = $("#sales_show").children(":first");
		var span3 = $("#price_show").children(":first");
		switch (type) {
		case 1://综合
			$(span1).attr("class", "tab_ckd");//选中状态
			$(span2).attr("class", "");
			$(span3).attr("class", "");
			sortData(1);
			break;
		case 2://销量
			$(span1).attr("class", "");
			$(span2).attr("class", "tab_ckd");//选中状态
			$(span3).attr("class", "");
			sortData(2);
			break;
		case 3://价格
			$(span1).attr("class", "");
			$(span2).attr("class", "");
			$(span3).attr("class", "tab_ckd");//选中状态
			sortData(3);
			break;
		}
		_num++;
	}
	//数据排序 重新加载
	function sortData(type) {
		try {
			var json = eval(goodJson);
			var data = null;
			switch (type) {
			case 1://综合
				data = json;
				break;
			case 2://销量
				if (_num % 2 == 0) {
					data=json.sort( function(a, b){   
					    return parseInt(a["sales" ]) > parseInt(b["sales" ]) ? 1 : parseInt(a[ "sales"]) == parseInt(b[ "sales" ]) ? 0 : -1;   
					});  
				} else {
					data = json.sort(function(a, b) {
						return parseInt(a["sales"]) < parseInt(b["sales"]) ? 1 : parseInt(a["sales"]) == parseInt(b["sales"]) ? 0 : -1;
							});
				}
				break;
			case 3://价格
				if (_num % 2 == 0) {
					data=json.sort( function(a, b){   
					    return parseInt(a["price" ]) > parseInt(b["price" ]) ? 1 : parseInt(a[ "price"]) == parseInt(b[ "price" ]) ? 0 : -1;   
					});
				}else{
					data = json.sort(function(a, b) {
						return parseInt(a["price"]) < parseInt(b["price"]) ? 1
								: parseInt(a["price"]) == parseInt(b["price"]) ? 0
										: -1;
					});
				}
				break;
			}
			$("#goods_list").empty();
			for (var i = 0; i < data.length; i++) {
				var html = '<li class="gd_list">';
				html += '<a	href="' + ctx + '/productDetail?productId='
						+ data[i]["id"] + '">';
				html += '<span class="gd_img"><img src="'+data[i]["iconUrl"]+'" onerror="" /></span>';
				html += '<div>';
				html += '<strong class="title ellipsis_two"> <span class="isBbc_or">自营</span> '
						+ data[i]["gName"] + '</strong>';
				html += '<div class="price_warp"><span class="price"> <b>¥'
						+ data[i]["price"] + '</b></span></div>';
				html += '<span class="cmt">' + data[i]["evaluation"]
						+ '人评论 销量：' + data[i]["sales"] + '</span>';
				html += '</div></a></li>';
				$("#goods_list").append(html);
			}
		} catch (e) {
		}
	}
</script>
</html>