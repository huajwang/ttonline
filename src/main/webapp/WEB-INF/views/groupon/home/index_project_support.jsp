<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支持</title>
<%@ include file="/common/public.jsp"%>
</head>
<body class="bgcolor2">
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg"
			alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px; display: inline-block;">支持</span>
	</div>
	<!-- 产品 -->
	<div class="zc_top bgcolor1">
		<h4 class="fontcolor7">产品</h4>
	</div>
	<div id="prolist">
		<c:choose>
			<c:when test="${not empty product}">
				<c:forEach items="${product}" var="item" varStatus="vs">
					<c:if test="${item.Surplus!=0 }">
						<div class="zc_top_project bgcolor1">
							<div class="projectlis">
								<span class="sel" style="width: 6%; display: inline-block;">
									<img style="width: 100%" class="projectlisimg" alt=""
									src="${ctx}/groupon/assets/img/index/indexprojectsupport/gou1.PNG">
									<input id="${item.id }" type="checkbox" value=""
									>
								</span> <img class="projectlisimg1" style="margin-right: 0;" alt=""
									src="${item.Icon }"> <label class="addordet"> <img
									class="an_jian addordetimg" alt=""
									src="${ctx}/groupon/assets/img/index/indexprojectsupport/jian.PNG">
									<span id="num_${item.id }" price="${item.price }"
									unit="${item.unit }" class="addordetspan">1</span> <img
									class="an_jia addordetimg" alt=""
									src="${ctx}/groupon/assets/img/index/indexprojectsupport/jia.PNG">
								</label>
								<div class="fontcolor3 fontsize15 zhichibc" style="">
									<div class="zhichibc1">${item.ProductName }</div>
									<div class="zhichibc2">${item.price }/${item.unit }</div>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>

	<!-- 支付方式 -->
	<%-- <div class="zfselect zf_whole bgcolor1">
		<img class="zf_wholeimg" alt=""
			src="${ctx}/groupon/assets/img/logo/log.PNG"> <span><strong
			class="fontcolor7">余额支付</strong></span> <span class="selzf"> <img
			class="projectlisimg zf_wholebc" alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectsupport/gou1.PNG">
			<input type="checkbox" value="">
		</span>
	</div> --%>

	<div class="zfselect2 zf_whole bgcolor1 zf_wholebc1">
		<img class="zf_wholeimg" alt=""
			src="${ctx}/groupon/assets/img/logo/wxlogo.png"> <span><strong
			class="fontcolor7">微信支付</strong></span> <span class="selzf"> <img
			class="projectlisimg zf_wholebc2" alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectsupport/gou.PNG">
			<input type="checkbox" value="" checked="checked">
		</span>
	</div>

	<!-- 收货地址 -->
	<div class="zf_whole bgcolor1 zf_wholebc1">
		<div class="zf_whole_l">
			<img class="zf_whole_img" alt=""
				src="${ctx}/groupon/assets/img/index/indexprojectsupport/adress.PNG">
		</div>
		<div class="address zf_whole_c">
			<span class="zf_whole_cspan1"><strong class="fontcolor7">收件人：${addrInfor.contactName }</strong></span>
			<span class="zf_whole_cspan2 zf_whole_cspan22">${addrInfor.province }${addrInfor.city }${addrInfor.area }${addrInfor.streetAddr }</span>
		</div>
		<img class="zf_whole_r" alt=""
			src="${ctx}/groupon/assets/img/index/indexprojectsupport/arrow_right.png">
	</div>

	<!-- 备注 -->
	<div class="bz_whole bgcolor1">
		<textarea id="remark" class="bz_whole_textarea" rows="" cols=""
			placeholder="跟小伙伴们说一句话吧（默认：支持）"></textarea>
	</div>

	<!-- 合计 -->
	<div class="hj_whole">
		<span><strong class="fontcolor7">合计:</strong></span> <span><strong
			id="heji" class="fontcolor6">￥20.00</strong></span>
		<div id="topay" class="hj_whole_an ztbgcolor fontcolor1">下一步</div>
	</div>
</body>
<script type="text/javascript">
	var project = {};//项目
	var userId = "${userId}";//当前用户Id
	project.id = "${projectId}";//项目主键
	project.addrId = "${addrId}";//收货地址
	project.amount = 0;//金额
	project.status = 0;//支付状态  0 未支付
	$(function() {
		//产品选择
		$('#prolist .sel')
				.on(
						'click',
						function() {
							var status = $(this).children('input').prop(
									"checked");
							if (status == true) {
								$(this).children('input')
										.prop("checked", false);
								$(this).children('input')
								.attr("checked", false);
								$(this)
										.children('img')
										.attr("src",
												"${ctx}/groupon/assets/img/index/indexprojectsupport/gou1.PNG")
								heji();
							} else {
								$(this).children('input').prop("checked",
										true);
								$(this).children('input').attr("checked",
								"checked");
								$(this)
										.children('img')
										.attr("src",
												"${ctx}/groupon/assets/img/index/indexprojectsupport/gou.PNG")
								heji();
							}
						});
		heji();
		//余额支付
		$('.zfselect')
				.on(
						'click',
						function() {
							$(this).find('.selzf').children('input').prop(
									"checked", true);
							$(this)
									.find('.selzf')
									.children('img')
									.attr("src",
											"${ctx}/groupon/assets/img/index/indexprojectsupport/gou.PNG");
							$(this).addClass('zf_wholebc1');
							$(this).find('.selzf').children('img').addClass(
									'zf_wholebc2');
							$('.zfselect2').find('.selzf').children('input')
									.prop("checked", false);
							$('.zfselect2')
									.find('.selzf')
									.children('img')
									.attr("src",
											"${ctx}/groupon/assets/img/index/indexprojectsupport/gou1.PNG");
							$('.zfselect2').removeClass('zf_wholebc1');
							$('.zfselect2').find('.selzf').children('img')
									.removeClass('zf_wholebc2');
							$('.zfselect2').find('.selzf').children('img')
									.addClass('zf_wholebc');
						});
		//微信支付
		$('.zfselect2')
				.on(
						'click',
						function() {
							$(this).find('.selzf').children('input').prop(
									"checked", true);
							$(this)
									.find('.selzf')
									.children('img')
									.attr("src",
											"${ctx}/groupon/assets/img/index/indexprojectsupport/gou.PNG");
							$(this).addClass('zf_wholebc1');
							$(this).find('.selzf').children('img').addClass(
									'zf_wholebc2');
							$('.zfselect').find('.selzf').children('input')
									.prop("checked", false);
							$('.zfselect')
									.find('.selzf')
									.children('img')
									.attr("src",
											"${ctx}/groupon/assets/img/index/indexprojectsupport/gou1.PNG")
							$('.zfselect').removeClass('zf_wholebc1');
							$('.zfselect').find('.selzf').children('img')
									.removeClass('zf_wholebc2');
							$('.zfselect').find('.selzf').children('img')
									.addClass('zf_wholebc');
						});

		//减
		$('.an_jian').on('click', function() {
			var num = $(this).next().html();
			if (num > 0) {
				num--;
				$(this).next().html(num);
				heji();
			}
		});
		//加
		$('.an_jia').on('click', function() {
			var num = $(this).prev().html();
			num++;
			$(this).prev().html(num);
			heji();
		});

		//添加收获地址
		$('.address').on(
				'click',
				function() {
					//window.location.href=ctx+"/groupon/homeprojectadress";
					if (userId == "") {
						alert("您尚未登录，请先登录");
						window.location.href = ctx + "/Login";
						return;
					}
					window.location.href = ctx
							+ "/address?groupon=1&projectId=" + project.id;
				});
		//支付
		$('#topay').on('click', function() {
			if (userId == "") {
				alert("您尚未登录，请先登录");
				window.location.href = ctx + "/Login";
				return;
			}
			if (project.addrId == "0") {
				alert("您尚未选择收货地址，请先选择收货地址");
				return;
			}
			var remark = $("#remark").val();
			if (remark == "")
				remark = "支持";
			project.product = new Array();//购买产品列表
			$("input[checked='checked']").each(function() {
				var id = $(this).attr("id");
				var price = $("#num_" + id).attr("price");//价格
				var unit = $("#num_" + id).attr("unit");//单位
				var num = $("#num_" + id).html();//数量
				if (num != undefined && num != "0") {
					var item = {
						"productId" : id,//产品Id
						"num" : num,// 产品名称
						"price" : price,// 产品价格
						"unit" : unit
					// 单位
					};
					project.product.push(item);
					//project.amount =parseFloat(project.amount)+ parseFloat(price) * parseFloat(num);
				}
			});
			if(project.product.length==0){
				alert("请选择购买产品");
				return;
			}
			var str = JSON.stringify(project.product);// 序列化方法 转成字符串
			$.post(ctx + '/groupon/api/project/addOrder', {
				"projectId" : project.id,//项目主键
				"amount" : project.amount,//订单金额
				"remark" : remark,//备注
				"Details" : str, //产品列表
				"addrId" : project.addrId
			//收货地址
			}, function(json) {
				if (json["status"] == "0") {
					var orderId = json["data"]["id"];
					payPage(orderId);//打开支付页面
					//window.location.href = ctx + "/groupon/homeprojectorderpay?orderId="+orderId;
				} else {
					alert(json["data"]["msg"]);
				}
			});
		});
	});
	//合计购买产品金额
	function heji() {
		project.amount = 0;
		$("input[checked='checked']").each(
				function() {
					var id = $(this).attr("id");
					var price = $("#num_" + id).attr("price");//价格
					var unit = $("#num_" + id).attr("unit");//单位
					var num = $("#num_" + id).html();//数量
					if (num != undefined && num != "0") {
						project.amount = (parseFloat(project.amount)
								+ parseFloat(price) * parseFloat(num)).toFixed(2);
					}
				});
		$("#heji").html("￥" + project.amount);
	}

	function payPage(orderId) {
		var url = "";
		if (isWeiXin()) {//判断是否微信
			$.post(ctx + "/orders/WxPay.api", {
				groupon : "groupon",
				id : orderId
			}, function(result) {
				url = result["url"];
				window.location.href = url;
			}, 'json');
		} else {
			window.location.href = ctx
					+ "/groupon/homeprojectorderpay?orderId=" + orderId;
		}
	}
</script>
</html>