<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>${good.gName }详细信息</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/resources/css/red.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/yly_zjw.css">

<link href="${ctx}/resources/css/city-picker.css" rel="stylesheet">
<link href="${ctx}/resources/css/main.css" rel="stylesheet">

<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/icheck.js"></script>
<script src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/touchslider.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/qrcode/jquery.qrcode.min.js"></script>
<script src="${ctx}/resources/js/detail.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/zepto.min.js"></script>
<script type="text/javascript">
	var price = "${good.price}";
	var property = '${propertyJSON}';
	var _type = '${type}';
	var _goodsType = 0;//标识3种属性的组合方式，具体规则见setGoodsType()方法。 by rd
	var _unpickType = _goodsType;//用户未选择的属性组合，采用_goodsType的逆运算。by rd
	var _operation = 1;//判断用户是选择购买还是放入购物车： 0为加入购物车，其他为购买 by rd
	var propertyTableName = "${good.propertyTableName}";

</script>
<!-- 返回顶部 -->
<script type="text/javascript">
$(document).ready(function(){
  $("#returnTop").click(function(){
    $(".main1").animate({scrollTop: '0px'}, 200);
  });
});
</script>
</head>
<body>
	<div class="tachu tchurecord"
		style="display: none; height: 300px; overflow: hidden;">
		<div class="tcreco">
			<div class="tcrecotop">
				<div class="topzt">
					<div class="topztleft">
						<img id="imgProperty"
							src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${good.iconUrl}"
							width="100%" height="100%">
					</div>
					<div class="topztcenter">
						<div class="ztcentertop">${good.gName}</div>
						<div class="ztcenterbottom">
							<span id="spjg"></span><span class="yh">&nbsp;&nbsp;有货</span>
						</div>
					</div>
					<div class="topztright">
						<img class="gbi" src="${ctx}/resources/images/colse.png">
					</div>
				</div>
			</div>
			<div class="tcrecobottom" style="overflow-y: auto; height: 180px;">
				<!-- 颜色 -->
				<div class="tcrecobo">
					<c:choose>
						<c:when test="${type==1}">
							<div class="tcrecoboleft">款式</div>
						</c:when>
						<c:otherwise>
							<div class="tcrecoboleft">颜色</div>
						</c:otherwise>
					</c:choose>
					<div id="goodsColor" class="tcrecoboright"
						style="margin: 10px 0px 10px 0px;"></div>
				</div>
				<div class="divclear"></div>
				<HR width="98%" color=#ccc SIZE=1>
				<!-- 尺寸 -->
				<c:if
					test="${groupType == 2 or groupType == 3 or groupType == 6 or groupType == 7}">
					<div class="tcrecobo">
						<div class="tcrecoboleft">尺寸</div>
						<div id="goodsSize" class="tcrecoboright"
							style="margin: 10px 0px 10px 0px;"></div>
					</div>
					<div class="divclear"></div>
					<HR width="98%" color=#ccc SIZE=1>
				</c:if>
				<!-- 风格 -->
				<c:if
					test="${groupType == 4 or groupType == 5 or groupType == 6 or groupType == 7}">
					<div class="tcrecobo">
						<div class="tcrecoboleft">风格</div>
						<div id="goodsStyle" class="tcrecoboright"
							style="margin: 10px 0px 10px 0px;"></div>
					</div>
					<div class="divclear"></div>
					<HR width="98%" color=#ccc SIZE=1>
				</c:if>
				<!-- 数量 -->
				<div class="tcrecobo" style="border: none">
					<div class="tcrecoboleft" style="height: 46px; line-height: 46px">数量：</div>
					<div class="tcrecoboright" style="padding-top: 6px;"
						style="margin: 10px;">
						<div class="addordel">
							<div class="aodleft">-</div>
							<div class="aodcenter">1</div>
							<div class="aodright">+</div>
						</div>
					</div>
					<!-- 确认键 -->

					<div class="topztright2">
						<!-- <input type="button" class="gbi" value="确认"  style="float:left"/>
						<input type="button" class="gbi" value="取消" style="float:right" onclick="history.go(0);" /> -->
					</div>

				</div>
			</div>
			<div>
				<!-- 			<div id="besure" class="pr_zt_bot_bo_gdetail yl_bc41 yly_font_size2 indexan" style="cursor:pointer;margin-top: 20%;" >确认</div> -->
			</div>
		</div>
	</div>
	<div class="tchu"></div>

	<!-- 上面代码弹出层 -->



	<div class="head">
		<ul>
			<li><a href="javascript:history.go(-1);"><img
					src="${ctx}/resources/img/back.png"></a></li>
			<li class="tit" style="width: 300px;">产品详情</li>
			<li><a><img src="${ctx}/resources/images/zhuye2.PNG"
					style="cursor: pointer; width: 35px; margin-left: 5px"
					onclick="javascript:window.location.href='${ctx}/';"></a></li>
		</ul>
	</div>
	<div class="main1">

		<div class="hdp-box">
			<div id="slider" class="swipe">
				<!-- 				<a name="atop"></a> -->
				<ul class="piclist">
					<c:choose>
						<c:when test="${not empty photos}">
							<c:forEach items="${photos}" var="item" varStatus="vs">
								<li><img id="img${vs.index }" src="${item.photo}"
									width="100%"></li>
							</c:forEach>
						</c:when>
					</c:choose>
				</ul>
			</div>

			<div class="count">
				<b class="bi">1</b>/<b class="bcount">4</b>
			</div>
		</div>

		<div class="cpantou yl_bc21">
			<span>${good.gName } </span>
		</div>

		<div class="cpantou" style="overflow: initial;">

			<div class="pro_pri">
				<div class="chanpwz yly_color8" style="margin-top: 1rem;">价&nbsp;&nbsp;&nbsp;格</div>
				<div id="spjg" class="shuliang yly_color7 yly_font_size6 yl_bc22">
					<c:choose>
						<c:when test="${min==-1}">￥${good.price }</c:when>
						<c:when test="${min!=-1}">
							<c:if test="${min==max}">￥${min }</c:if>
							<c:if test="${min!=max}">￥${min } - ￥${max }</c:if>
						</c:when>
					</c:choose>
				</div>
				<div class="yl_bc34">
					<span class="yl_bc33">包邮</span>
				</div>
				<!-- <div class="yl_bc23">
					<span class="jt"></span>
				</div> -->
			</div>
			<!-- <div class="pro_pri yly_color8">
				<div class="chanpwz" style="margin-top: 1rem;">市场价</div>
				<div class="shuliang  yly_font_size6 yl_bc22 huaxian">￥280.00
				</div>
			</div> -->

			<div class="shuliangwzcao">
				<div class="chanpwz yly_color8">数&nbsp;&nbsp;&nbsp;量</div>
				<div class="shuliang yly_color8 yly_font_size6 yl_bc22">
					<div class="yl_bc24">
						<div class="jian">-</div>
						<input class="injorj" type="text" value="1" />
						<div class="jia">+</div>
					</div>
				</div>
			</div>
			<c:if test="${type > 0 }">
				<div id="pro_pri" class="pro_pri">
					<div class="chanpwz yly_color8" style="margin-top: 1rem;">属&nbsp;&nbsp;&nbsp;性</div>
					<div id="xuanze" class="shuliang yly_color7 yl_bc22">

						<c:out value="${colorList[0].color}"></c:out>
						<c:out value="${sizeList[0].size}"></c:out>
						<c:out value="${styleList[0].style}"></c:out>
					</div>
					<div class="yl_bc23">
						<span class="jt"></span>
					</div>
				</div>
			</c:if>

			<!-- div class="gotoadress">
				<div class="chanpwz yly_color8">送&nbsp;&nbsp;&nbsp;至</div>
				<div style="width: 86%; min-height: 2.5rem; float: right;">
					<div class="docs-methods">
						<form class="form-inline">
							<div id="distpicker">
								<div class="form-group">
									<div style="position: relative;">
										<input id="city-picker3" class="form-control" readonly
											type="text" value="福建省/厦门市/思明区" data-toggle="city-picker">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div-->



			<div class="yunfei">
				<div class="chanpwz">送货：</div>
				<div class="yfsm">
					<a href="">免运费</a>
				</div>
			</div>
			<div class="fuwu">
				<div class="chanpwz">服务：</div>
				<div class="fwsm" style="margin: 0;">
					<span>支持七天无理由退货</span>
				</div>
			</div>
			<div class="getqrcode" id="qrcode">
				<img alt="点击生成二维码" src="${ctx}/resources/img/qrcode/qrcode.png"
					width="30px" height="30px" style="margin-top: -1%; float: left" />
				<p style="float: left; margin-top: 1%"
					onClick="with(document.getElementById('code')){style.display='';tabIndex = 1;focus();}">生成二维码</p>
			</div>
		</div>





		<div class="cpantou yl_bc25" style="margin-bottom: 0;">
			<a href="${ctx}/detail/evalist/?gId=${good.id}"
				style="-webkit-tap-highlight-color: #fff;">
				<div class="baojia">
					<div class="bjle2">评价（${evalSize }）</div>
					<div class="yl_bc23">
						<span class="jt"></span>
					</div>
					<div class="bjri2" style="color: gray;">好评率：${evalHao }%</div>
				</div>
			</a>
		</div>

		<!--
   	作者：787711932@qq.com
   	时间：2016-04-28
   	描述：空白
   -->
		<c:if test="${!empty evalMap}">
			<div class="pinlu">
				<a href="${ctx}/detail/evalist/?gId=${good.id}"
					style="-webkit-tap-highlight-color: #fff;">
					<div class="com_lis_reco_top">

						<div class="yl_bc67">
							<c:choose>
								<c:when test="${!empty evalMap.iconUrl}">
									<img
										src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${evalMap.iconUrl}"
										height="100%" />
								</c:when>
								<c:otherwise>
									<img src="${ctx}/resources/images/touxiang.png" height="100%" />
								</c:otherwise>
							</c:choose>

						</div>
						<div class="yl_bc68" style="width: auto;">${evalMap.userName }</div>
					</div>
					<div class="com_lis_reco_bot" style="height: 3.5rem">
						<div class="yl_bc71" style="font-size: 18px">${evalMap.content }
							<c:choose>
								<c:when test="${not empty evalImg }">
									<c:forEach items="${evalImg}" var="item" varStatus="vs">
										<img
											src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${item.icon}"
											height="40px" width="40px"
											style="position: relative; top: 10px" />
									</c:forEach>
								</c:when>
							</c:choose>
						</div>
						<div class="yl_bc701"
							style="color: #C0C0C0; font-size: 10px; margin-top: 0.5rem">${evalMap.createTime }</div>
					</div>
				</a>
			</div>
		</c:if>
		<!--
   	作者：787711932@qq.com
   	时间：2016-04-28
   	描述：查看分类，进店逛逛
   -->
		<div class="yly_inde">
			<div class="yly_inde_top">
				<div class="yly_tt" style="width: 55%">
					<img src="${ctx}/resources/images/TT2.png" style="width: 100%;" />
				</div>
				<div class="top_inde yly_color8">蚂蚁小威</div>
			</div>
			<div class="yly_inde_bot">
				<a href="${ctx}/goodlist?type=1&CId=${good.subcategoryId}"><div
						class="inde_bot_an yly_bg_color5">查看分类</div></a>
				<div class="inde_bot_an yly_bg_color5"
					onclick="javascript:window.location.href='${ctx}/';"
					style="margin: 0.5rem 0;">返回首页</div>
			</div>
		</div>
		<!--
   	作者：787711932@qq.com
   	时间：2016-04-28
   	描述：推荐产品
   -->

		<!--
   	作者：787711932@qq.com
   	时间：2016-04-28
   	描述：产品详情图
   -->
		<c:choose>
			<c:when test="${not empty good.DetialImg}">
				<c:forEach items="${good.DetialImg}" var="item" varStatus="vs">
					<div class="pro_detai_img">
						<img src="${item.detailPhotoUrl }" />
					</div>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>

	<!--
  	作者：787711932@qq.com
  	时间：2016-04-28
  	描述：返回顶部
  -->
	<a class="yl_bc32" id="returnTop">顶部</a>

	<div class="foot3_up" style="z-index: 999;">
		<div class="shouc">
			<c:choose>
				<c:when test="${good.isFavourite==0}">
					<img src="${ctx}/resources/images/icon_42.png" id="changePic"
						height="30px" />
					<br />
				</c:when>
				<c:when test="${good.isFavourite==1}">
					<img src="${ctx}/resources/images/icon_41.png" id="changePic"
						height="30px" />
					<br />
				</c:when>
				<c:otherwise>
					<img src="${ctx}/resources/images/icon_41.png" id="changePic"
						height="30px" />
					<br />
				</c:otherwise>
			</c:choose>
			<span class="yly_color8">收藏</span>
		</div>
		<div class="ylypay">
			<c:choose>
				<c:when test="${good.upForSale =='0'}">
					<div class="ylypay_an_down yly_bg_color8">该商品已下架</div>
				</c:when>
				<c:otherwise>
					<div id="goodsclick" class="ylypay_an yly_bg_gc">加入购物车</div>
					<div id="purchase" class="ylypay_an indexan">立即购买</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="cart">
			<a href="${ctx}/cart/"> <c:forEach items="${carts}" var="item"
					varStatus="vs" begin="0" end="0">
					<c:if test="${!empty item.id}">
						<div id="shuzi">${cartsize}</div>
					</c:if>
					<c:if test="${empty item.id}">
						<div id="shuzi">0</div>
					</c:if>
				</c:forEach> <img src="${ctx}/resources/images/icon_39-1.png" height="30px" /><br />
				<span class="yly_color8">购物车</span></a>
		</div>
	</div>

	<!-- 	kefu -->
	<div id="div" class="qqke" style="bottom: 23rem;">
		<a target="_blank"
			href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes">
			<img alt="" src="${ctx}/resources/img/kefu/00.png"
			style="width: 100%; height: 100%">
		</a>
	</div>
	<div id="div2" class="qqke" style="bottom: 17rem;">
		<a target="_blank"
			href="http://wpa.qq.com/msgrd?v=3&uin=3447681374&site=qq&menu=yes">
			<img alt="" src="${ctx}/resources/img/kefu/11.png"
			style="width: 100%; height: 100%">
		</a>
	</div>
	
	<div id="ant" class="ant">
		<a target="_blank"
			href="http://mp.weixin.qq.com/s?__biz=MzI0NTU0MTUzOA==&tempkey=Czm706DrhzRjyqXytgDFazKFwuUXuj1zzdA5dCkTz1BAHlrZ3i0XLuNyucqN5dfuKnXoYqJMUHUnDeaoRrzGd5peNb79Ieb13IbDRkhoZCopCUwAJ%2FiFxwAtUCby2nMzTZ%2FyVzq%2BCUaxtv7iHW7D4g%3D%3D&#rd">
			<img alt="" src="${ctx}/resources/img/ant.png"
			style="width: 100%; height: 100%">
		</a>
	</div>
	
	<!-- 	kefu -->
	<!-- 二维码 -->
	<div id="code" class="qrcode" onblur="this.style.display='none';"
		style="display: none;"></div>
	<!-- 二维码 -->
	<input type="hidden" id="gId" value="${good.id}" />
	<script src="${ctx}/resources/js/bootstrap.js"></script>
	<script src="${ctx}/resources/js/city-picker.data.js"></script>
	<script src="${ctx}/resources/js/city-picker.js"></script>
	<script src="${ctx}/resources/js/main.js"></script>
</body>
<script>
	var colorArr = [],sizeArr = [],styleArr = [];
	var myColor='';mySize='';myStyle='';//存放已选中的属性
	$(function(){
		if(property!=null&&property!=""){
			var json = eval(property);
			var len = json.length;
			for(var i=0;i<len;i++){
				judgeStrArr(colorArr,json[i]['color']);
				judgeStrArr(sizeArr,json[i]['size']);
				judgeStrArr(styleArr,json[i]['style']);
			}
			showAllPropertys();
		}
		
	})
	var spjg=$('#spjg').val();
	if(spjg==''){
		$('#spjg').html('￥'+${good.price}+"元");
	}else{
		$('#spjg').html("元");
	}
	$(function() {
		var num = $('#slider').find('li').size();
		$('.bcount').text(num);
		$('.b_btn').click(function() {
			$(this).toggleClass('b_btn_active');
			$('.intro').toggle();
		});
		$(".jia").click(function() {
			addNum();
		});
		$(".jian").click(function() {
			reduceNum();
		});
		$("#goodsclick").click(function() {
			if(_type!="0"){
				if(_goodsType>0 && propertyId=="0"){
					_operation = 0;
					$('#pro_pri').click();
				}else{
					addCart();
				}
			}
			else{
				addCart();				
			}
			
		});
		$('#pro_pri').on('click', function() {
			$('.tchu').css('display', 'block');
			$('.tachu').css('display', 'block');
			//var len = $("#goodsColor div").length;
			//if (len > 0) {
				//$("#goodsColor div")[0].click();
				//setTimeout(function() {
					//len = $("#goodSize div").length;
					//if (len > 0)
						//$("#goodSize div")[0].click();
				//}, 300);
			//}
		});
		$('.topztright').on('click', function() {
			$('.tchu').css('display', 'none');
			$('.tachu').css('display', 'none');
		});
		$('#besure').on('click',function(){
			if(_unpickType > 0){
				alert("请选择具体单品");
				return;
			}else{
				switch(_operation){
					case 0 : addCart();break;
					default : buy();
				}
			}
		})
		
		//$('.gbi').on('click', function() {
			//$('.tchu').css('display', 'none');
			//$('.tachu').css('display', 'none');
		//});
		if(colorArr.length>0){
			$("#goodsColor div").click(function(){
				var _this = this;//锁定选择的按钮
				if($(_this).attr('class')=='ys unxuanz')
					return;
				pickColor(_this);
			});
		}
		if(sizeArr.length>0){
			$("#goodsSize div").click(function(){
				var _this = this;//锁定选择的按钮
				if($(_this).attr('class')=='ys unxuanz')//不可选按钮无效
					return;
				pickSize(_this);
			});
		}
		if(styleArr.length>0){
			$("#goodsStyle div").click(function(){
				var _this = this;//锁定选择的按钮
				if($(_this).attr('class')=='ys unxuanz')
					return;
				pickStyle(_this);
			});
		}
		//$("#goodsColor div").click(function() {
			//var _this = this;
			//setColor(_this);
		//});

		//if (_type == 3) {
			//$("#goodStyle div").click(function() {
				//$("#goodStyle .ys.xuanz").attr("class", "ys");
				//$(this).attr("class", "ys xuanz");
			//});
		//}
		$(".aodright").click(function() {
			addNum();
		});
		$(".aodleft").click(function() {
			reduceNum();
		});
		$("#purchase").click(function() {
			if(_goodsType>0 && propertyId=="0"){
				_operation = 1;
				$('#pro_pri').click();
			}else{
				buy();
			}
		});
		$(".shouc").click(function() {
			ShareClick();
		});

	})

	var tt = new TouchSlider({
		id : 'slider',
		'auto' : '-1',
		fx : 'ease-out',
		direction : 'left',
		speed : 600,
		timeout : 5000,
		'before' : function(index) {
			var es = document.getElementById('slider').getElementsByTagName(
					'li');
			var it = $(es[index]).index() + 1;
			$('.bi').text(it);
			var tx = $(es[index]).find('p').text();
			$('.title').text(tx);
		}
	});
</script>

<script>
var dquserId = '${userId}';//当前用户Id
var url = location.href;
var qrUrl = url+'&userId='+dquserId;

$(function() {
    var img = $("#img0").attr("src");
	var title = "${good.gName }";
	var description = "${good.description }";
    $.post("http://121.40.69.138/wx/test/WeiXinJsServer", {url:url}, function(data) {
    	wx.config({                  
    			debug: false,
    			appId: data.appid,
    			timestamp: data.timeStamp,
    			nonceStr: data.noncestr,                  
    			signature: data.signature,                  
    			jsApiList: [ 'onMenuShareTimeline',                    
                             'onMenuShareAppMessage',
                             'onMenuShareQQ',
                             'onMenuShareWeibo',
                             'onMenuShareQZone',]                    
    		
    	});
    		wx.ready(function () {
    			var shareData = {
   					    title: title,
   					    desc: description,
   					    link: url+'&userId='+dquserId,
					    imgUrl: img
   					  };
    			var shareDataTwo = {
   					    title: title,
   					    desc: description,
   					    link: 'http://www.xmtroika.com/ttmall/logwx/index?goPage=productDetail&productId=${good.id}&userId='+dquserId,
   					 	//link: 'http://www.xmtroika.com/ttmall/productDetail?productId=${good.id}&userId='+dquserId,
   					    imgUrl: img
   					  };
   			  wx.onMenuShareAppMessage(shareDataTwo);
   			  wx.onMenuShareTimeline(shareDataTwo);
   			  wx.onMenuShareQQ(shareData);
   			  wx.onMenuShareWeibo(shareData);
   			  wx.onMenuShareQZone(shareData);
   			});
    }, 'json');
});

</script>
<!-- .拖动. -->
<script>
	var div = document.getElementById('div');
	
	div.addEventListener('touchmove', function(event) {
		event.preventDefault();//阻止其他事件
		// 如果这个元素的位置内只有一个手指的话
		if (event.targetTouches.length == 1) {
		 	var touch = event.targetTouches[0];  // 把元素放在手指所在的位置
		 	div.style.left = touch.pageX + 'px';
			div.style.top = touch.pageY + 'px';
			div.style.background = "";
		 }
	}, false);
	
	var div2 = document.getElementById('div2');
	
	div2.addEventListener('touchmove', function(event) {
		event.preventDefault();//阻止其他事件
		// 如果这个元素的位置内只有一个手指的话
		if (event.targetTouches.length == 1) {
		 	var touch = event.targetTouches[0];  // 把元素放在手指所在的位置
		 	div2.style.left = touch.pageX + 'px';
			div2.style.top = touch.pageY + 'px';
			div2.style.background = "";
		 }
	}, false);
	
	var code = document.getElementById('code');
	
	code.addEventListener('touchmove', function(event) {
		event.preventDefault();//阻止其他事件
		// 如果这个元素的位置内只有一个手指的话
		if (event.targetTouches.length == 1) {
		 	var touch = event.targetTouches[0];  // 把元素放在手指所在的位置
		 	code.style.left = touch.pageX-50 + 'px';
		 	code.style.top = touch.pageY-50 + 'px';
		 	code.style.background = "";
		 }
	}, false);
	
$(function(){
	var subscribe = ${subscribe};
	if(subscribe == 1){
		$("#ant").show();
	}else{
		$("#ant").hide();
	}
	
	$("#qrcode").click(function(){
		window.location.href=ctx+"/qrcode?gid=${good.id}"; 
	});
})



</script>
</html>
