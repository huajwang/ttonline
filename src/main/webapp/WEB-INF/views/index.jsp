<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html style="font-size: 10px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>蚂蚁小威</title>
<%@ include file="/common/include.rec.ftl"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/resources/css/base.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/resources/css/base_new.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/resources/css/yly_zjw.css" />
<script src="<%=path%>/resources/js/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/resources/css/index.7e6f785c.css">
<script>
	var width = document.querySelector('html').offsetWidth;
	var delta = (640 - 320) / (20 - 10);
	var fontSize = (width - 320) / delta + 10;
	if (width <= 320) {
		fontSize = 10;
	} else if (width >= 640) {
		fontSize = 20;
	}
	document.querySelector('html').style.fontSize = fontSize + 'px';
	window.addEventListener('resize', function() {
		var width = document.querySelector('html').offsetWidth;
		var delta = (640 - 320) / (20 - 10);
		var fontSize = (width - 320) / delta + 10;
		if (width <= 320) {
			fontSize = 10;
		} else if (width >= 640) {
			fontSize = 20;
		}
		document.querySelector('html').style.fontSize = fontSize + 'px';
	})
</script>
<script>
/**
 * 打开商品详情
 */
function OpenProduct(id) {
	var url ="<%=path%>/" + "productDetail?productId=" + id;
	window.location.href = url;
}

</script>
</head>

<body>
	<a name="atop"></a>
	<!--顶部浮层-->
	<input type="hidden" value="_" id="float_id">
	<input type="hidden" value="__tan" id="float_id_tan">
	<!--搜索-->
	<header class="idx_header">
		<div class="gome_logo">
			<img src="<%=path%>/resources/img/tyk-logo.png">
		</div>
		<div class="usr_bar">
			<p id="guanzhu">
				<c:choose>
					<c:when test="${flag == 0}">
						<!-- 用户已经登录 -->

					</c:when>
					<c:otherwise>
						<a id="btnLogin" href="<%=path%>/Login/">登录</a>
					</c:otherwise>
				</c:choose>
			</p>
		</div>
		<div class="idx_searech">
			<div class="search_module">
				<input type="hidden" style="font-size: 1.3rem;" id="default_kwd"
					value="请输入你要购买的商品">
				<div class="search_bar">
					<p class="cancle" id="wok">取消</p>
					<div class="search">
						<div class="search_btn1" data-icon="3"></div>
						<div class="search_clear" data-icon="?"></div>
						<div class="search_item" data-icon="C" data-val="0">商品</div>
						<div class="search_input_bar">
							<input id="search_input1" style="font-size: 1.3rem;"
								type="search" placeholder="请输入你要购买的商品">
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>

	<article class="idx_viewpoint">
		<section class="banner swiper swiper-container-horizontal"
			data-swiper="[object Object]">
			<ul id="banner_swiper" class="swiper-wrapper"
				style="transition-duration: 0ms; transform: translate3d(-320px, 0px, 0px);">
				<c:choose>
					<c:when test="${not empty TopPic}">
						<c:forEach items="${TopPic}" var="item" varStatus="vs">
							<c:if test="${vs.index==0 }">
								<li class="swiper-slide swiper-slide-active"
									data-swiper-slide-index="0" style="width: 320px;"><a
									href="#"><img src="${item.pic }"
										style="opacity: 1; transition: opacity 0.3s;"></a></li>
							</c:if>
							<c:if test="${vs.index==1 }">
								<li class="swiper-slide swiper-slide-next"
									data-swiper-slide-index="${vs.index }" style="width: 320px;"><a
									href="#"><img src="${item.pic }"
										style="opacity: 1; transition: opacity 0.3s;"></a></li>
							</c:if>
							<c:if test="${vs.index>1 }">
								<li class="swiper-slide" data-swiper-slide-index="${vs.index }"
									style="width: 320px;"><a href="#"><img
										src="${item.pic }"
										style="opacity: 1; transition: opacity 0.3s;"></a></li>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>

			</ul>
			<div class="swiper-pagination">
				<span
					class="swiper-pagination-bullet swiper-pagination-bullet-active"></span>
				<span class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span> <span
					class="swiper-pagination-bullet"></span>
			</div>
		</section>
		<nav style="margin-bottom: 0;">
			<ul>
				<li><a href="<%=path%>/activity/productDetail"><div class="cat_icon">
					<img src="<%=path%>/resources/images/1179liwugou_wap.png"
								style="opacity: 1; transition: opacity 0.3s;">
					</div>
					<p class="cat_name">活动打折</p></a></li>
				<li><a href="<%=path%>/travel"><div class="cat_icon">
					<img src="<%=path%>/resources/images/1179liwugou_wap.png"
								style="opacity: 1; transition: opacity 0.3s;">
					</div>
					<p class="cat_name">欢乐时光</p></a></li>
				
				<li><a href="https://scratch.mit.edu/"><div class="cat_icon">
					<img src="<%=path%>/resources/images/1179liwugou_wap.png"
								style="opacity: 1; transition: opacity 0.3s;">
					</div>
					<p class="cat_name">教育园地</p></a></li>
				<li><a href="<%=path%>/poem"><div class="cat_icon">
					<img src="<%=path%>/resources/images/1179liwugou_wap.png"
								style="opacity: 1; transition: opacity 0.3s;">
					</div>
					<p class="cat_name">散文游记</p></a></li>
				
				<li><a href="<%=path%>/category/"><div class="cat_icon">
							<img src="<%=path%>/resources/images/1179fenlei@2x.png"
								style="opacity: 1; transition: opacity 0.3s;">
						</div>
						<p class="cat_name">分类</p></a></li>
				<li><a href="<%=path%>/cart/"><div class="cat_icon">
							<img src="<%=path%>/resources/images/1179gouwuche.png"
								style="opacity: 1; transition: opacity 0.3s;">
						</div>
						<p class="cat_name">购物车</p></a></li>
				<li><a href="<%=path%>/groupon/"><div class="cat_icon">
							<img src="<%=path%>/resources/images/huanleping.png"
								style="opacity: 1; transition: opacity 0.3s;">
						</div>
						<p class="cat_name">欢乐拼</p></a></li>
				<li><a href="<%=path%>/member/"><div class="cat_icon">
							<img src="<%=path%>/resources/images/1179my@2x.png"
								style="opacity: 1; transition: opacity 0.3s;">
						</div>
						<p class="cat_name">我的中心</p></a></li>
			</ul>
		</nav>
		<a class="yl_bc32" href="#atop">顶部</a>
		<!-- 推荐商品 -->
		<input type="hidden" id="bighitCount" value="${bighitCount}" />
		<c:if test="${not empty bighit}">
			<div id="bighitdiv" class="yly_tjian">
				<section class="plc_idx_module">
					<h4 class="title" style="border-top: 1px solid #e6e6e6;">
						<span class="line" style="background-color: #e73028"></span> &nbsp<img
							src="${ctx}/resources/images/shangcheng.png" class="imgs" />&nbsp
						<span style="color: #e73028">商城推荐</span> <span class="line"
							style="background-color: #e73028"></span>
					</h4>
				</section>
				<c:forEach items="${bighit}" var="item" varStatus="vs">
					<div class="yl_bc86" onclick="OpenProduct('${item.gId}');">
						<div class="tjian_pro_bighit">
							<div class="pro_img">
								<img src="${item.iconUrl}" width="100%" height="100%" />
							</div>
							<div class="pro_name_bighit yly_color8 yly_font_size3"
								style="margin-top: 5px">${item.gName}</div>
							<div class="pro_pric">
								<div class="prics">
									<span class="yl_bc30 yly_color12 yly_font_size8">￥${item.price}</span>
								</div>
								<div class="prics yl_bc29">
									<span class="yl_bc31 yly_color3 yly_font_size3"
										style="overflow: hidden; text-overflow: ellipsis"><nobr>${item.sales+50}人付款</nobr></span>
								</div>
							</div>
						</div>
					</div>

				</c:forEach>
			</div>
			<h1 id="superaddload" style="width: 100%; text-align: center">数据加载中...</h1>
		</c:if>

		<!-- 防止商品文字信息被底部菜单栏遮挡 -->
		<div class="clear"></div>
		<div class="yl_bc87"></div>
		<div id="footer">
			<ul>
				<li><a href="<%=path%>/" class="nav1 on"></a></li>
				<li><a href="<%=path%>/category/" class="nav2"></a></li>
				<li><a href="<%=path%>/groupon/" class="nav5"></a></li>
				<li><a href="<%=path%>/cart/" class="nav3"> <c:forEach
							items="${carts}" var="item" varStatus="vs" begin="0" end="0">
							<c:if test="${ not empty item.id}">
								<div id="shuzi1">${cartsize}</div>
							</c:if>
							<c:if test="${empty item.id}">
								<div id="shuzi1">0</div>
							</c:if>
						</c:forEach>
				</a></li>
				<li><a href="<%=path%>/member/" class="nav4"></a></li>
			</ul>
		</div>
		<span id="gotop" data-gotop="true" data-icon="0"
			style="display: none;"></span>
	</article>
	<script type="text/javascript">
		var total_page = "2";
		var index_switch = "";
		var delay_endtime = "8990";
		var is_home = 1;
	</script>

	<script type="text/javascript"
		src="<%=path%>/resources/js/zepto.min.js"></script>
	<script type="text/javascript"
		src="<%=path%>/resources/js/zepto.swipe.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/my_index.js"></script>
	<script type="text/javascript"
		src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/s_code.js"></script>
	<script type="text/javascript" src="<%=path%>/resources/js/wap.js"></script>
	<script type="text/javascript">

	$(".search_btn1").click(function(){
		var value=$("#search_input1").val();
		if(value==""){
			alert("请输入查询关键字")
			return;
		}
		value=encodeURI(value)
		window.location.href="<%=path%>/goods/search?name="
						+ encodeURI(value);
	});
	
	var username = unescape(getCookie("username"));
	var password = unescape(getCookie("password"));
	if(username!="")//判断是否存在用户 如果存在 自动登录
	{
		$.post("<%=path%>
		/user/ajaxLogin.api", {
				userName : username,
				password : password
			}, function(result) {
				var status = result["status"];
				if (status != "0") {
					if (isWeiXin()) {
						var OpenId = getCookie("OpenId");
						$.post(ctx + "/WXLoginByOpenId", {
							phone : "",
							OpenId : OpenId
						}, function(result) {

						}, 'json');
					}
				}
				;
			}, 'json');
		}
	</script>
</body>

<script>
	$(function() {
		if (isWeiXin()) {
			if ($("#btnLogin") != null)//微信浏览器  隐藏登录按钮
				$("#btnLogin").hide();
			var subscribe = '${subscribe}';
			if (subscribe != "" && subscribe != null && subscribe == 1) {
				$('#guanzhu')
						.html(
								'<a href="http://mp.weixin.qq.com/s?__biz=MzI0NTU0MTUzOA==&tempkey=Czm706DrhzRjyqXytgDFazKFwuUXuj1zzdA5dCkTz1BAHlrZ3i0XLuNyucqN5dfuKnXoYqJMUHUnDeaoRrzGd5peNb79Ieb13IbDRkhoZCopCUwAJ%2FiFxwAtUCby2nMzTZ%2FyVzq%2BCUaxtv7iHW7D4g%3D%3D&#rd">关注</a>');
			}
		}
		var url = location.href;
		$
				.post(
						"http://121.40.69.138/wx/test/WeiXinJsServer",
						{
							url : url
						},
						function(data) {
							wx.config({
								debug : false,
								appId : data.appid,
								timestamp : data.timeStamp,
								nonceStr : data.noncestr,
								signature : data.signature,
								jsApiList : [ 'onMenuShareTimeline',
										'onMenuShareAppMessage',
										'onMenuShareQQ', 'onMenuShareWeibo',
										'onMenuShareQZone', ]
							});

							wx
									.ready(function() {
										var shareData = {
											title : '拓意客在线： 好东西，要和好朋友一起分享！',
											desc : '零库存、零成本，10%佣金等你拿！',
											link : 'http://www.xmtroika.com/ttmall/?shareCode=${shareCode}',
											imgUrl : 'http://www.xmtroika.com/ttmall/resources/images/troika_logo.jpg'
										};
										wx.onMenuShareAppMessage(shareData);
										wx.onMenuShareTimeline(shareData);
										wx.onMenuShareQQ(shareData);
										wx.onMenuShareWeibo(shareData);
										wx.onMenuShareQZone(shareData);
									});
						}, 'json');
	});
</script>

<script>
	var stop = true;
	//防止history.go(-1) 或者回退键,页面不刷新
	if ($('#bighitCount').val() != 8) {
		$('#bighitCount').val("8");
	}
	$(window)
			.scroll(
					function() {
						//$(window).height()浏览器可视界面高度
						//$(window).scrollTop()浏览器可视窗口顶端距离网页顶端的高度（垂直偏移）
						//$(document).height()整个网页的文档高度
						totalheight = parseFloat($(window).height())
								+ parseFloat($(window).scrollTop());
						if ($(document).height() <= totalheight) {
							if (stop == true) {
								stop = false;
								$
										.post(
												ctx + '/superaddhit',
												{
													"current" : $(
															'#bighitCount')
															.val(),
												},
												function(result) {
													var json = JSON
															.parse(result);
													if (json.length == 0) {
														$('#superaddload').val(
																'没有更多内容了！');
														return;
													}
													var temp = parseInt($(
															'#bighitCount')
															.val())
															+ json.length;
													$('#bighitCount').val(temp);
													for (var i = 0; i < json.length; i++) {
														var hitdiv = "<div class=\"yl_bc86\" onclick=\"OpenProduct('"
																+ json[i]['gId']
																+ "');\">"
																+ "<div class=\"tjian_pro_bighit\"><div class=\"pro_img\">"
																+ "<img src=\""
																+ json[i]['iconUrl']
																+ "\" width=\"100%\" height=\"100%\" /></div>"
																+ "<div class=\"pro_name_bighit yly_color8 yly_font_size3\">"
																+ json[i]['gName']
																+ "</div>"
																+ "<div class=\"pro_pric\"><div class=\"prics\">"
																+ "<span class=\"yl_bc30 yly_color12 yly_font_size8\">￥"
																+ json[i]['price']
																+ "</span>"
																+ "</div><div class=\"prics yl_bc29\">"
																+ "<span class=\"yl_bc31 yly_color3 yly_font_size3\">"
																+ json[i]['sales']
																+ "人付款</span>"
																+ "</div></div></div></div>";
														$('#bighitdiv').append(
																hitdiv);
													}
													stop = true;
												});
							}
						}
					});
</script>

</html>