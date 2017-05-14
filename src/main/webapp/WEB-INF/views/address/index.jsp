<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>收货人信息</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />

<link href="${ctx}/resources/css/red.css" rel="stylesheet" />

<style type="text/css">
.login_btn {
	height: 3.15rem;
	line-height: 3.15rem;
	background: #F4CCCC;
	text-align: center;
	font-size: 1.2rem;
	border-radius: 0.2rem;
	margin: 1rem 0 2rem 0;
	width: 80%;
	margin-left: 10%;
}

.login_btn.red {
	background: #ef3030;
}
</style>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" height="20"
				onclick="history.go(-1);" />
		</div>
		<div class="or_top_right">
			<p>收货人信息</p>
		</div>
		<div class="yl_bc35">
			<a href="${ctx}/address/Edit" style="color: white">+</a>
		</div>
	</div>

	<!--
     	作者：787711932@qq.com
     	时间：2016-04-29
     	描述：收获人信息
     -->
	<div id="memreco">
		<c:choose>
			<c:when test="${not empty address}">
				<c:forEach items="${address}" var="item" varStatus="vs">
					<div class="yly_sh_reco">
						<div class="sh_reco_left">
							<div class="sh_reco_left_l">
								<input type="radio" addressId="${item.id}"
									id="radio-1-${vs.index+1 }"
									<c:if test='${item.active==1}'>checked</c:if>
									name="radio-1-set" class="radiozj${vs.index+1 } regular-radio" /><label
									class="yl_bc37 radiozj-${vs.index+1 }"
									for="radio-1-${vs.index+1 }"
									<c:if test='${item.active==1}'> style="border: 1px solid RGB(248,81,76)"</c:if>></label><br />
							</div>
							<div class="sh_reco_left_r">
								<div class="reco_left_top yly_color10">
									<div class="rlt_left yly_font_size2 font_w1">${item.contactName}</div>
									<div class="rlt_right">
										<span class="yl_bc36">${item.phone}</span>
									</div>
								</div>
								<div class="reco_left_bottom">
									<span>${item.province}${item.city}${item.area}${item.streetAddr}</span>
								</div>
							</div>
						</div>
						<div class="sh_reco_right">
							<img src="${ctx}/resources/images/qianbi.png" height="30"
								onclick="EditAddress('${item.id}')" /> 
							<img src="${ctx}/resources/img/sc.png" height="30"
								onclick="delAddress('${item.id}')" />
							<c:choose>
								<c:when test="${item.active==1}">
									<img src="${ctx}/resources/img/default.png" height="30"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx}/resources/img/setdefault.png" height="30" onclick="setDefault('${item.id}')" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>
	</div>
	<div class="login_btn red" id="xuanze">
		<a href="javascript:xuanze()">确 定</a>
	</div>
</body>
<script>
	var orderId;
	var addressId = "";
	$(function() {
		$('input[type=radio]').on(
				'click',
				function() {
					var chek = $(this).prop("checked");
					var id = $(this).attr("id");
					addressId = $(this).attr("addressId");
					var lable = $(this)[0].nextElementSibling;

					$(lable).css('border', '1px solid RGB(248,81,76)');
					var len = $('input[type=radio]').length;
					var data = $('input[type=radio]');
					for (var i = 0; i < len; i++) {
						if (data[i].id != id) {
							$(data[i].nextElementSibling).css('border',
									'1px solid #cacece');
						}
					}
				});

		$(".or_top_left").on('click', function() {
			history.go(-1);
		});

		orderId = Request["orderId"];
		if (orderId == undefined) {
			var groupon = Request["groupon"]//拼团调用
			if (groupon == undefined)
				$("#xuanze").css("display", "none");
		}
	})
	//删除地址
	function delAddress(id) {
		if (window.confirm("您确定删除该数据吗？")) {
			$.post(ctx + "/address/delAddress", {
				id : id
			}, function(result) {
				if (result > 0) {
					alert("删除成功！");
					window.location.reload();
				}else{
					alert("默认地址无法删除！");
				}
			});
		}
	}
	//设置新的默认地址
	function setDefault(id){
		if (window.confirm("确定要将该地址设置为默认地址吗？")) {
			$.post(ctx + "/address/ajaxSetDefaultAddress", {
				id : id
			}, function(result) {
				if (result > 0) {
					alert("设置成功！");
					window.location.reload();
				}else{
					alert("设置失败！");
				}
			});
		}
	}
	
	
	//编辑地址信息
	function EditAddress(id) {
		var url = ctx + "/address/Edit?id=" + id;
		window.location.href = url;
	}
	//选择收货地址
	function xuanze() {
		var groupon = Request["groupon"]//拼团调用
		if (groupon != undefined) {
			var projectId = Request["projectId"]//项目主键
			if (addressId == "") {
				window.location.href = ctx + "/groupon/homeprojectsupport?id="
						+ projectId
			} else {
				window.location.href = ctx + "/groupon/homeprojectsupport?id="
						+ projectId + "&addrId=" + addressId;
			}
			return;
		}
		//以上代码是拼团调用
		var url = ctx + "/orders/subOrder?id=" + orderId;
		if (addressId == "") {
			window.location.href = url
			return;
		}
		if (window.confirm("您确定修改收货地址？")) {
			$.post(ctx + "/address/updOrderAddress", {
				orderId : orderId,
				addressId : addressId
			}, function(result) {
				var status = result["status"]
				if (status == 0) {
					window.location.href = url
				}
			}, 'json');
		}

	}
</script>
</html>
