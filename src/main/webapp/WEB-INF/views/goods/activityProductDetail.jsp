<%@ page language="java" pageEncoding="UTF-8"%>
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

<link href="${ctx}/resources/css/main.css" rel="stylesheet">

<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/sockjs-client/1.1.2/sockjs.min.js">
	</script>
<script
	src="http://cdn.jsdelivr.net/webjars/org.webjars/stomp-websocket/2.3.3-1/stomp.min.js">
	</script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<script src="${ctx}/resources/js/jquery.js"></script>
<script src="${ctx}/resources/js/icheck.js"></script>
<script src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/touchslider.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/qrcode/jquery.qrcode.min.js"></script>
<script src="${ctx}/resources/js/activityProductDetail.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/zepto.min.js"></script>
<script type="text/javascript">
	var price = "${activity.promotionPrice}";
	var productId = "${good.id}";// 产品Id
	var property = '${propertyJSON}';
	var _type = '${type}';
	var _goodsType = 0;//标识3种属性的组合方式，具体规则见setGoodsType()方法。 by rd
	var _unpickType = _goodsType;//用户未选择的属性组合，采用_goodsType的逆运算。by rd
	var _operation = 1;//判断用户是选择购买还是放入购物车： 0为加入购物车，其他为购买 by rd
	var propertyTableName = "${good.propertyTableName}";
	var endDate = "${activity.endDate}";

</script>
<!-- 返回顶部 -->
<script type="text/javascript">
$(document).ready(function(){
  $("#returnTop").click(function(){
    $(".main1").animate({scrollTop: '0px'}, 200);
  });
//   var subscribe = '${subscribe}',
//   Con = $('#subscribe');
//   if(subscribe!=null &&subscribe!=''&&subscribe==1){
// 	  Con.append('<a href="javascript:void(0);"><img src="${ctx}/resources/img/subscribe/subscribe.png" width="100%" height="50px"></a>');
//   }
});
</script>
</head>
<body>
<script type="text/javascript">
	var sock;
	var stomp;
	function connect() {
	      sock = new SockJS('/ttmall/spittr');
	      stomp = Stomp.over(sock);
	      stomp.connect('guest', 'guest', function(frame) {
	    	  alert("connected")
	        stomp.subscribe("/topic/spittlefeed", handleSpittle);
	        stomp.subscribe("/topic/aaa", handleReply);
	      });
	      function handleSpittle(message) {
	      	  alert('handle spittle: ' + JSON.parse(message.body).message);
	        }
	        
	        function handleReply(message) {
	            alert('handle Notification: ' + JSON.parse(message.body).chatMessage);
	          }
	}
	
    function sendMessage() {
    	var text = $('#msg_input').val()
        stomp.send("/app/spittle", {}, 
            JSON.stringify({ 'text': text }));
    }
</script>
<!-- 	<div id="subscribe" style="z-index: 99999;position: fixed;"></div> -->
	<div class="tachu tchurecord"
		style="display: none; height: 300px; overflow: hidden;">
		<div class="tcreco">
			<div class="tcrecotop">
				<div class="topzt">
					<div class="topztleft">
						<c:if test="${!empty good.iconUrl}">
							<img id="imgProperty" src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${good.iconUrl}" width="100%" height="100%">
						</c:if>
					</div>
					<div class="topztcenter">
						<div class="ztcentertop">${good.gName}</div>
						<div class="ztcenterbottom">
							<span id="spjg">￥${activity.promotionPrice}</span><span class="yh">&nbsp;&nbsp;有货</span>
						</div>
					</div>
					<div class="topztright">
						<img class="gbi" src="${ctx}/resources/images/colse.png">
					</div>
				</div>
			</div>
			<div class="tcrecobottom" style="overflow-y: auto; height: 180px;">
				<!-- 颜色 -->
				<div class="tcrecobo" >
					<c:choose>
						<c:when test="${type==1}">
							<div class="tcrecoboleft">款式</div>
						</c:when>
						<c:otherwise>
							<div class="tcrecoboleft">颜色</div>
						</c:otherwise>
					</c:choose>
					<div id="goodsColor" class="tcrecoboright" style="margin:10px 0px 10px 0px;">
					</div>
				</div>
				<div class="divclear"></div>
				<HR width="98%" color=#ccc SIZE=1>
				<!-- 尺寸 -->
				<c:if test="${type >= 2 }">
					<div class="tcrecobo">
						<div class="tcrecoboleft">尺寸</div>
						<div id="goodsSize" class="tcrecoboright" style="margin:10px 0px 10px 0px;"></div>
					</div>
					<div class="divclear"></div>
					<HR width="98%" color=#ccc SIZE=1>
				</c:if>
				<!-- 风格 -->
				<c:if test="${type == 3 }">
					<div class="tcrecobo">
						<div class="tcrecoboleft">风格</div>
						<div id="goodsStyle" class="tcrecoboright" style="margin:10px 0px 10px 0px;"></div>
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
			<li class="tit">限时抢购</li>
			<li><a><img src="${ctx}/resources/images/zhuye1.png"
					style="cursor: pointer;"
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
								<li><img id="img${vs.index }" src="${item.icon}"
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
		
				<div class="pro-de-bc pro-c pro-c2">
			<div class="pro-de-bc1">
				<div class="bo_pro_lef pro-de-h pro-de-bc2 pro-c4">￥${activity.promotionPrice}</div>
				<div class="bo_pro_lef pro-de-h pro-de-bc3 pro-c3">
					<span class="pro-de-bc4">原价</span><br>
					<span class="pro-de-bc5">￥${good.price}</span>
				</div>
				<div class="bo_pro_lef pro-de-h pro-de-bc3 pro-c3">
					<span class="pro-de-bc4">折扣</span><br>
					<span class="pro-de-bc5"><fmt:formatNumber type="number" value="${(good.price-activity.promotionPrice)/good.price}" pattern="0.00" maxFractionDigits="2"/></span>
				</div>
				<div class="bo_pro_lef pro-de-h pro-de-bc3 pro-c3">
					<span class="pro-de-bc4">节省</span><br>
					<span class="pro-de-bc5">￥ <fmt:formatNumber type="number" value="${good.price-activity.promotionPrice}" pattern="0.00" maxFractionDigits="2"/> </span>
				</div>
			</div>
			<div class="pro-c3" style="border-bottom: 1px dashed;width: 100%"></div>
			<div class="pro-de-bc1">
				<div class="bo_pro_lef pro-de-h pro-de-bc6 pro-de-bc5">18888人已购买</div>
				<div class="bo_pro_lef pro-de-h pro-de-bc5 pro-de-bc7 pro-c6">
					<span id="dd" class="pro-c5">00</span><span> 天 </span>
					<span id="hh" class="pro-c5">00</span><span> 小时 </span>
					<span id="mm" class="pro-c5">00</span><span> 分 </span>
					<span id="ss" class="pro-c5">00</span><span> 秒 </span>
				</div>
			</div>
		</div>
		<div class="pro-c7 pro-de-bc8">
			<div class="pro-de-bc9"></div>
			<div class="pro-c8" style="font-weight: bold;">抢购描述</div>
		</div>
		<div class="pro-de-bc10 pro-c9">
			${activity.describe}
		</div>

		<div class="cpantou" style="overflow: initial;">
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




			<div class="yunfei">
				<div class="chanpwz">送货：</div>
				<div class="yfsm">
					<a href="">免运费</a>
				</div>
			</div>
			<!-- div class="fuwu">
				<div class="chanpwz">服务：</div>
				<div class="fwsm" style="margin: 0;">
					<span>支持七天无理由退货</span>
				</div>
			</div-->
			<div class="input-group">
        	<input id="msg_input" type="text" class="form-control" placeholder="Search Blog..">
        	<span class="input-group-btn">
          		<button class="btn btn-default" type="button" onclick="sendMessage()">
            		<span class="glyphicon glyphicon-search"></span>
          		</button>
        	</span>
    	</div>
		</div>
		
		
		

		
		<!--
   	作者：787711932@qq.com
   	时间：2016-04-28
   	描述：查看分类，进店逛逛
   -->
		<div class="yly_inde">
			<div class="yly_inde_top">
				<div class="yly_tt" style="width: 65%">
					<img src="${ctx}/resources/images/TT2.png" style="width: 100%;" />
				</div>
				<div class="top_inde yly_color8" >共享家商城</div>
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
						<img src="${item.icon }" />
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
	<a class="yl_bc32" id="returnTop" >顶部</a>
	
	<div class="foot3_up" style="z-index: 999;">
		<div id="query" class="shouc" onclick = "connect()">
			<img src="${ctx}/resources/img/activity/inquiry.PNG" id="changePic" height="30px"/>
			<br />
			<span id='spantest' class="yly_color8">咨询</span>
		</div>
		<div class="ylypay">
			<div id="purchase" class="ylypay_an yly_bg_gc" style="width: 100%;background-color: red;">立即抢购</div>
		</div>
		<div class="cart" id="qrcode"  onClick="with(document.getElementById('code')){style.display='';tabIndex = 1;focus();}" >
			 <img src="${ctx}/resources/img/activity/share.PNG" height="30px" /><br />
				<span class="yly_color8">分享</span>
		</div>
	</div>
<!-- 	kefu -->
<!-- 		<div id="div" class="qqke"> -->
<!-- 			 <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2088151366&site=qq&menu=yes"> -->
<%-- 				<img alt="" src="${ctx}/resources/img/qqkf2.png" style="width: 100%;height: 100%"> --%>
<!-- 			 </a>  -->
<!-- 		</div> -->
<!-- 	kefu -->
	<!-- 二维码 -->
	<div id="code" class="qrcode" onblur="this.style.display='none';" style="display: none;"></div>
	<!-- 二维码 -->

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
$(function() {
	timer();//倒计时功能启动
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
//     			var shareDataTwo = {
//    					    title: title,
//    					    desc: description,
//    					    link: 'http://1ywg.6655.la/wx/logwx?goPage=productDetail&product_id=${good.id}&user_id='+userId,
// 					    imgUrl: img
//    					  };
   			  wx.onMenuShareAppMessage(shareData);
   			  wx.onMenuShareTimeline(shareData);
   			  wx.onMenuShareQQ(shareData);
   			  wx.onMenuShareWeibo(shareData);
   			  wx.onMenuShareQZone(shareData);
   			});
    }, 'json');
});
</script>
<!-- .拖动. -->
<script>
	
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
// 	var str = toUtf8("TT商城");
// 	$('#code').qrcode(str);
	
	$("#qrcode").click(function(){
		$("#code").empty();
		var display = document.getElementById('code').style.display;
// 		if(display=="none"){
// 			$('#code').css("display","none");
// 		}else{
// 			$('#code').css("display","block");
// 		}
		var str = "";
		if(dquserId==null||dquserId==""){
			str = toUtf8(url);
		}else{
			str = toUtf8(url+'&userId='+dquserId);
		}
		$("#code").qrcode({
			width: 140,
			height:140,
			text: str,
			src: '${ctx}/resources/img/logo/logo.jpg'
		});
	});
})
function toUtf8(str) {   
    var out, i, len, c;   
    out = "";   
    len = str.length;   
    for(i = 0; i < len; i++) {   
    	c = str.charCodeAt(i);   
    	if ((c >= 0x0001) && (c <= 0x007F)) {   
        	out += str.charAt(i);   
    	} else if (c > 0x07FF) {   
        	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
        	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	} else {   
        	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	}   
    }   
    return out;   
} 
</script>
</html>
