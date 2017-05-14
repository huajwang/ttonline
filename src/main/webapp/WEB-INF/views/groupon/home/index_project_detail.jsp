<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${project.name}</title>
<%@ include file="/common/public.jsp"%>
<script src="${ctx}/resources/js/layer/layer.js"></script>
<style type="text/css">
.qrcode {
	width: 1.5rem;
	height: 5rem;
	position: fixed;
	bottom: 20rem;
	z-index: 9999999;
	right: 63%;
	bottom: 50%;
	background-color: #fff;
}
</style>
</head>
<body class="bgcolor2">

	<div id="code" class="qrcode" style="display: none"></div>
	<script type="text/javascript">
		$('.qrcode').focus(function() {
			alert('div focus');
		});
		$('.qrcode').blur(function() {
			alert('div blur');
		});
	</script>

	<!-- 二维码 -->
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<div style="width: 7%; height: 3.5rem; float: left;">
			<img class="prodetalieimg"
				src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		</div>
		<div
			style="text-overflow: ellipsis; overflow: hidden; width: 80%; float: left;">
			<nobr>${project.name}</nobr>
		</div>
		<div class="pro_jb" style="width: 10%; float: right;">举报</div>
	</div>

	<!-- 图片轮播 -->
	<div data-am-widget="slider" class="am-slider am-slider-a1"
		data-am-slider='{&quot;directionNav&quot;:false}'>
		<ul class="am-slides">
			<c:forEach items="${ImgList}" var="item" varStatus="vs">
				<c:if test="${item.IsDisplay=='1'}">
					<li><img class="pro-img" src="${item.Icon}" /></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

	<!-- 详情 -->
	<div class="detail_zt bgcolor1">
		<div class="detail_ztmem">
			<img class="detail_ztmemimg" alt="" src="${project.iconUrl}"> <span
				class="detail_ztmemname">${project.userName}</span> <span
				style="font-size: 11px;"><fmt:formatDate type="time"
					value="${project.createTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span>
		</div>
		<div class="prodeta_pu">
			<!-- 标题 -->
			<div class="recolistzt_titl">${project.name}</div>
		</div>
		<div class="prodeta_pu detamemuco">
			<div class="detamenu">
				<img class="detamenu_img" alt=""
					src="${ctx}/groupon/assets/img/index/indexprojectdetail/pr1.PNG"><br>
				<span class="fontsize14 fontcolor3">目标金额</span><br> <span
					class="deprice ztcolor">${project.amount}元</span>
			</div>
			<div class="detamenu">
				<img class="detamenu_img" alt=""
					src="${ctx}/groupon/assets/img/index/indexprojectdetail/pr2.PNG"><br>
				<span class="fontsize14 fontcolor3">已拼金额</span><br> <span
					class="deprice ztcolor">${project.target }元</span>
			</div>
			<div class="detamenu">
				<img class="detamenu_img" alt=""
					src="${ctx}/groupon/assets/img/index/indexprojectdetail/02_proindex_03.png"><br>
				<span class="fontsize14 fontcolor3">支持次数</span><br> <span
					class="deprice ztcolor">${project.orderNum }次</span>
			</div>
		</div>
		<div class="pintuan bgcolor1 fontcolor3">拼团详情</div>
	</div>

	<!-- 运费和发货时间 -->
	<div class="yunf bgcolor1">
		<div class="yandfti">运费和发货时间</div>
		<div class="yandfprice">
			<span class="fontcolor3">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</span><span>${project.EMS}</span>
		</div>
		<div class="yandfprice">
			<span class="fontcolor3">发货时间：</span><span>${project.sendTime}</span>
		</div>
	</div>

	<!-- 拼团产品 -->
	<div class="yunf bgcolor1">
		<div class="yandfti coss" style="border-bottom: 1px solid #ccc;">拼团产品</div>
		<c:forEach items="${project.product}" var="item">
			<div class="prodetzt">
				<!-- 此处应该有链接 -->
				<div
					style="margin: 15px 0 0 10px; font-size: 1rem; height: 2rem; line-height: 1rem">
					${item.ProductName }</div>
				<img class="prodet_img" src="${item.Icon}"> <span
					class="fontcolor3" style="width: 60%">${item.price}元/每${item.unit}</span>

				<c:if test="${item.surplus==0}">
					<span class="fontcolor3 sycs">已售完</span>
				</c:if>
				<c:if test="${item.surplus!=0}">
					<span class="fontcolor3 sycs">剩余<span class="fontcolor5"
						name="true">${item.surplus}</span>份</span>
				</c:if>
				<div style="height:20px;line-height:20px;margin-left:10px;font-size:10px">${item.content}</div>
			</div>
		</c:forEach>
		<!-- 项目详情图片 -->
		<div>
			<div style="padding:0 3%">${project.remark}</div>
			<c:forEach items="${ImgList}" var="item" varStatus="vs">
				<c:if test="${item.IsDisplay=='0'}">
					<img src="${item.Icon}" width="100%" height="100%">
				</c:if>
			</c:forEach>

		</div>
	</div>

	<!-- 拼团动态 -->
	<div class="dynamic">
		<div class="yandfti coss dtbc">拼团动态</div>

		<c:forEach items="${schedule}" var="item">
			<div class="dtsczt">
				<img class="detail_ztmemimg dynamicbc" alt="" src="${item.iconUrl}">
				<img class="xiaoimg bgcolor1" alt=""
					src="${ctx}/groupon/assets/img/index/indexprojectdetail/jian.png">
				<span class="dtname fontcolor3">${item.userName }</span> <span
					class="fontcolor5">发布进度更新</span><br> <span
					class="fontcolor3 fontsize13 dttime">${item.createTime}</span> <img
					onclick="comments('${item.id}',0)" class="plcs" alt=""
					src="${ctx}/groupon/assets/img/index/indexprojectdetail/02_proindex_07.png">
			</div>
			<div class="memztde">
				<div class="memztde_r">
					<div class="memztde_r_top">${item.content}
						<!-- 项目更新图片 -->
						<div>
							<c:forEach items="${item.imgList}" var="im">
								<img alt="" src="${im.icon}" width="100%" height="100%">
							</c:forEach>
						</div>
					</div>
					<c:if test="${not empty item.image}">
						<div class="memztde_r_top">
							<c:forEach items="${item.image}" var="im">
								<img alt="" src="${ctx}/${im}">
							</c:forEach>
						</div>
					</c:if>
					<div class="xiaosanjiao"></div>
					<div class="pllis">
						<c:forEach items="${item.comment }" var="comItem">
							<div>
								<span class="am-text-primary">${comItem.userName } <c:if
										test="${not empty comItem.parent}">
										<span class="fontcolor3">[回复]</span>${comItem.parent}</c:if>：
								</span> <span class="fontcolor3"
									onclick="comments('${item.id}','${comItem.id}')">${comItem.content }</span>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- 支持者 -->
	<div class="zczhe bgcolor1">
		<div class="zcztitl">Ta的支持者</div>
		<div class="zcztzyop">

			<c:forEach items="${project.OrderList}" var="order">
				<div class="dtsczt">
					<img class="detail_ztmemimg dynamicbc" style="margin-right: 1rem;"
						alt="" src="${order.iconUrl }"> <span
						class="dtname fontcolor3" style="max-width: 45%;">${order.userName }</span>
					<span class="fontcolor3">支持了<span class="ztcolor">${order.amount }</span>元
					</span><br> <span class="fontcolor3 fontsize13 dttime"><fmt:formatDate
							type="time" value="${order.createTime }"
							pattern="yyyy-MM-dd hh:mm:ss" /></span> <img class="plcs"
						onclick="commento('${order.id}',0)" style="margin-top: -1.6rem;"
						alt=""
						src="${ctx}/groupon/assets/img/index/indexprojectdetail/02_proindex_07.png">
				</div>
				<div class="memztde" style="border: none;">
					<div class="memztde_r">
						<div class="memztde_r_top">${order.remark }</div>

						<div class="xiaosanjiao"></div>
						<div class="pllis">
							<c:forEach items="${order.comlist }" var="como">
								<div>
									<span class="am-text-primary">${como.userName } <c:if
											test="${not empty como.parent}">
											<span class="fontcolor3">[回复]</span>${como.parent}</c:if>：
									</span> <span class="fontcolor3"
										onclick="commento('${order.id}','${como.id}')">${como.content }</span>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>

		</div>
	</div>

	<div class="returnup" data-am-smooth-scroll>
		<img alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectdetail/return.png"
			width="100%">
	</div>

	<!-- 支持 -->
	<div class="zc_an bgcolor1 promanage_edit_bc12">
		<div class="zc_an_l promanage_edit_bc12 fontcolor13">
			<c:choose>
				<c:when test="${not empty focus}">
					<span id="focus"
						class="am-icon-heart zc_an_lbc promanage_edit_bc23"></span>
					<br>
				</c:when>
				<c:otherwise>
					<span id="focus"
						class="am-icon-heart-o zc_an_lbc promanage_edit_bc23"></span>
					<br>
				</c:otherwise>
			</c:choose>
			<span class="fontsize14">关注</span>
		</div>
		<div class="zc_an_c promanage_edit_bc12">
			<div id="zhichi"
				class="ancss ztbgcolor fontcolor1 promanage_edit_bc14">我要购买</div>
		</div>
		<div id="fex" class="zc_an_r promanage_edit_bc12 fontcolor14">
			<span class="am-icon-share-alt zc_an_lbc promanage_edit_bc23"></span><br>
			<span class="fontsize14">分享</span>
		</div>
	</div>

	<div class="tshimt">
		<span>请登录后发布评论</span>
	</div>

	<!-- 发表评论 -->
	<div class="review_activate" style="margin-bottom: 15%;">
		<div class="review_name1">发表评论</div>
		<div class="review_text1">
			<textarea class="review_text_textarea" id="textareass" maxlength="80"></textarea>
		</div>
		<div class="review_digital">
			<div class="review_digital_end">80</div>
			<div style="float: right;">/</div>
			<div class="review_digital_start" id="div1">0</div>
		</div>
		<div class="review_btnt">
			<div class="review_btnt_left">取消</div>
			<div class="review_btnt_right">提交</div>
		</div>
	</div>

	<input type="hidden" id="projectId" value="${project.id}" />
	<input type="hidden" id="scheduleId" value="0" />
	<!-- 存储评论提交时的进度id  -->
	<input type="hidden" id="orderId" value="0" />
	<!-- 存储评论提交时的订单id -->
	<input type="hidden" id="parentId" value="0" />
	<!-- 存储评论提交时的父级评论id -->

	<!-- 分享给好友 -->
	<!-- 	<div class="management_activate2" style="display: none"> -->
	<!-- 		<div class="management_motai_top2" -->
	<!-- 			style="height: 40px; line-height: 40px;">分享给好友</div> -->
	<!-- 		<div class="management_motai_btn_top2"> -->
	<!-- 			<span class="management_motai_conter2 ptxqfenxiang"> <span> -->
	<!-- 					<img style="width: 50%;" -->
	<%-- 					src="${ctx}/groupon/assets/img/groupon/project/fenxiang_12.png" /> --%>
	<!-- 			</span><br> <span>微信朋友圈</span> -->
	<!-- 			</span> <span class="management_motai_conter2 ptxqfenxiang"> <span> -->
	<!-- 					<img style="width: 50%;" -->
	<%-- 					src="${ctx}/groupon/assets/img/groupon/project/fenxiang_05-03.png" /> --%>
	<!-- 			</span><br> <span>微信好友</span> -->
	<!-- 			</span> <span class="management_motai_conter2 ptxqfenxiang"> <span> -->
	<!-- 					<img style="width: 50%;" -->
	<%-- 					src="${ctx}/groupon/assets/img/groupon/project/fenxiang_09.png" /> --%>
	<!-- 			</span><br> <span>QQ好友</span> -->
	<!-- 			</span> <span class="management_motai_conter2 ptxqfenxiang"> <span> -->
	<!-- 					<img style="width: 50%;" -->
	<%-- 					src="${ctx}/groupon/assets/img/groupon/project/fenxiang_05.png" /> --%>
	<!-- 			</span><br> <span>QQ空间</span> -->
	<!-- 			</span> -->
	<!-- 		</div> -->
	<!-- 		<div class="management_motai_btn2">取消</div> -->
	<!-- 	</div> -->
</body>
<script type="text/javascript">
	var userId = "${userId}";//当期用户Id
	$(function() {

		var oHeight = $(window).height(); //浏览器当前的高度

		$(window).resize(function() {

			if ($(window).height() < oHeight) {

				$(".zc_an").css("position", "static");
				$(".review_activate").css("bottom", "0.1%");
			} else {
				$(".zc_an").css("position", "fixed");
				$(".review_activate").css("position", "fixed");
				$(".review_activate").css("bottom", "28%");
			}

		});

		 /* $('.plcs').on('click', function() {
			$('.tshimt').css('display', 'block');
			window.setTimeout(tshi, 3000);
		});
		function tshi() {
			$('.tshimt').css('display', 'none');
		}  */

		//举报
		$('.pro_jb').on(
				'click',
				function() {
					window.location.href = ctx
							+ "/groupon/homeprojectreport?id=${project.id}";
				});

		//支持
		$('#zhichi')
				.on(
						'click',
						function() {
							if ($(".fontcolor5[name='true']").length == 0) {
								alert("产品已售完！");
								return;
							}
							if (userId == "") {
								//判断是不是微信浏览器 如果是调转到微信登录鉴权页面上
								if (isWeiXin()) {
									window.location.href = ctx
											+ "/logwx/index?goPage=groupon/homeprojectsupport&projectId=${project.id}";
									return;
								}
							}
							window.location.href = ctx
									+ "/groupon/homeprojectsupport?id=${project.id}";
						});

		//关注
		$('.zc_an_l')
				.on(
						'click',
						function() {
							if ($('#focus').hasClass('am-icon-heart-o')) {
								$
										.post(
												ctx
														+ '/groupon/project/addFollowRecord',
												{
													"projectId" : $(
															'#projectId').val(),
												},
												function(result) {
													var json = JSON
															.parse(result);
													if (json["status"] == "0") {
														$("#focus")
																.attr("class",
																		'am-icon-heart zc_an_lbc promanage_edit_bc23');
													} else {
														alert("关注失败，请确认是否已登陆。");
													}
												});
							}
						});

		//返回
		$('.prodetalieimg').on('click', function() {
			//window.history.back(-1);
			window.location.href = ctx + "/groupon/";
			//var inputs = '';
			// inputs += '<input type="hidden" name="type" value="0" />';
			//jQuery('<form action="${ctx}/home" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
		});

		$('#textareass').keyup(function() {
			TextCount = $(this).val().length;//获取文本框的长度
			$('#div1').html(TextCount);//将长度显示在div中
			if (TextCount >= 80) {
			} else {
				$('#div1').html(TextCount);//将长度显示在div中
			}
		});
		$('#textareass').keydown(function() {
			TextCount = $(this).val().length;//获取文本框的长度
			$('#div1').html(TextCount);//将长度显示在div中
			if (TextCount >= 80) {
			} else {
				$('#div1').html(TextCount);//将长度显示在div中
			}
		});
		$('.plcs').on('click', function() {
			$('.review_activate').css('display', 'block');
		});

		$('.review_btnt_left').on('click', function() {
			$('#textareass').val('');
			$('#div1').html(0);
			$('.review_activate').css('display', 'none');
			$('#scheduleId').val(0);
			$('#orderId').val(0);
			$('#parentId').val(0);
		});

		//评论提交按钮
		$('.review_btnt_right')
				.on(
						'click',
						function() {
							$
									.post(
											ctx + '/groupon/project/addComment',
											{
												"projectid" : $('#projectId')
														.val(),
												"orderid" : $('#orderId').val(),
												"scheduleid" : $("#scheduleId")
														.val(),
												"content" : $("#textareass")
														.val(),
												"parentid" : $("#parentId")
														.val()
											},
											function(result) {
												var json = JSON.parse(result);
												if (json["status"] == "0") {
													$("#focus")
															.attr("class",
																	'am-icon-heart zc_an_lbc promanage_edit_bc23');
													window.location.reload()
													//$('#textareass').val('');
													//$('#div1').html(0);
													//$('.review_activate').css('display','none');
												} else {
													alert("没有足够的权限评论当前栏目");
												}
											});

						});
		var project_id = '${project.id}';
		$('#fex').on('click', function() {
			var url = location.href;
			$("#code").empty();
			$("#code").css("display", "block");
			var str = toUtf8(url);
			$("#code").qrcode({
				width : 140,
				height : 140,
				text : str,
				src : '${ctx}/resources/img/logo/logo.jpg'
			});
		});
		//点击隐藏二维码
		$('#code').click(function() {
			$("#code").css("display", "none");
		});

		var code = document.getElementById('code');

		code.addEventListener('touchmove', function(event) {
			event.preventDefault();//阻止其他事件
			// 如果这个元素的位置内只有一个手指的话
			if (event.targetTouches.length == 1) {
				var touch = event.targetTouches[0]; // 把元素放在手指所在的位置
				code.style.left = touch.pageX - 70 + 'px';
				code.style.top = touch.pageY - 70 + 'px';
				code.style.background = "";
			}
		}, false);
		function toUtf8(str) {
			var out, i, len, c;
			out = "";
			len = str.length;
			for (i = 0; i < len; i++) {
				c = str.charCodeAt(i);
				if ((c >= 0x0001) && (c <= 0x007F)) {
					out += str.charAt(i);
				} else if (c > 0x07FF) {
					out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
					out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
					out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				} else {
					out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
					out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				}
			}
			return out;
		}

		$('.management_motai_btn2').on('click', function() {
			$('.management_activate2').css('display', 'none');
		});
	})
</script>
<script type="text/javascript">
	//回复进度评论
	function comments(scheduleId, parentId) {
		$('.review_activate').css('display', 'block');
		$('#scheduleId').val(scheduleId);
		$('#orderId').val(0);
		$('#parentId').val(parentId);
	}
	//回复订单评论
	function commento(orderId, parentId) {
		$('.review_activate').css('display', 'block');
		$('#orderId').val(orderId);
		$('#scheduleId').val(0);
		$('#parentId').val(parentId);
	}

	//轮播图规定一下大小：当手机是1080P的情况下图片为 高*宽：980*1080
	$(function() {
		var win = $(window).width(); //浏览器当前窗口可视区域宽度
		if (Number(win) == Number(1080)) {
			$('.pro-img').css('width', '1080px');
			$('.pro-img').css('height', '980px');
		}
	});
</script>

</html>