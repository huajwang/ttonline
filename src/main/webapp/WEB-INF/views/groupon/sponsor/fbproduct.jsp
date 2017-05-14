<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<script src="${ctx}/groupon/assets/js/fbproduct.js"></script>
<title>发布产品</title>
<style type="text/css">
</style>
</head>

<body
	style="padding: 0; margin: 0; background-color: RGB(245, 246, 250);">
	<div class="dialog" id="dia" style="display: none"></div>
	<div class="attempt_activate" style="display: none">
		<div class="attempt_nickname">发布条款</div>
		<div class="attempt_conter">条款</div>
		<div class="xuan_xian">
			<div class="xuan_xian_left" onclick="show()">
				<img id="img"
					style="width: 15px; width: 20px; margin-left: -1px; margin-top: -1px; display: none"
					src="${ctx}/groupon/assets/img/pint/gou.png" />
			</div>
			<div class="xuan_xian_right">我已阅读并同意该条款</div>
		</div>
		<div class="attempt_btn" id="yes">确认</div>
	</div>

	<div class="presell_top">
		<div class="presell_top_left">尝鲜预售</div>
		<div class="presell_top_right">
			<div style="float: left">
				<img style="width: 18px; margin-top: px; margin-left: 28px;"
					src="${ctx}/groupon/assets/img/pint/tb2.png" />
			</div>
			<div style="color: RGB(186, 53, 72); font-size: 13px; float: right">条款说明</div>
		</div>
	</div>
	<img id="fan_hui" onclick="self.location=document.referrer;"
		style="position: fixed; width: 40px; margin-top: -1em; margin-left: 0.5em;"
		src="${ctx}/resources/img/logo/jiantou.png" />
	<div class="basic_a"
		style="background-color: #fff; border-bottom: 1px solid RGB(233, 233, 233)">
		<div class="Id_card" style="text-indent: 0em;">
			<strong class="Id_card_id">拼团名称</strong>
		</div>
		<div class="input_shuru">
			<input id="name" class="input_jihuo" type="text"
				placeholder=" &nbsp;填写拼团名称" />
		</div>
	</div>


	<div class="spell_money" style="background-color: #fff;">
		<div class="spell_money_card">
			<strong class="spell_money_id">拼款总金额</strong>
		</div>
		<div class="spell_money_input_shuru">
			<input id="amount" class="spell_money_input_jihuo" type="text"
				onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;"
				placeholder=" &nbsp;填写拼团金额目标" />
		</div>
		<div class="spell_money_yuan">
			<strong>元</strong>
		</div>
	</div>

	<div class="stop_date" style="background-color: #fff;">
		<div class="stop_wenzi">
			<div class="stop_wenzi_left">
				<strong>截止日期</strong>
			</div>
			<div class="stop_wenzi_right">
				<span id="currenttime"
					style="color: RGB(216, 216, 216); margin-left: 0.9em;">2016-6-15
					16:30</span> <span style="color: RGB(216, 216, 216)">共</span><span
					id="day" style="color: RGB(192, 47, 54);">7</span><span
					style="color: RGB(216, 216, 216)">天</span>
			</div>
		</div>
		<div class="gun_tiao">
			<div class="gun_tiao_left">1天</div>

			<div class='slider-example'>
				<div class="well">
					<input id="ex1" data-slider-id='ex1Slider' type="text"
						data-slider-min="1" data-slider-max="30" data-slider-step="1"
						data-slider-value="0" />
				</div>

			</div>

			<div class="gun_tiao_right">30天</div>

		</div>
	</div>

	<div class="basic_a"
		style="background-color: #fff; border-bottom: 1px solid RGB(233, 233, 233)">
		<div class="Id_card" style="text-indent: -2em;">
			<strong class="Id_card_id">运费</strong>
		</div>
		<div class="input_shuru">
			<input id="EMS" readonly="readonly" value="包邮" class="input_jihuo" type="text"
				placeholder=" &nbsp;请说名运费" />
		</div>
	</div>

	<div class="basic_a"
		style="background-color: #fff; border-bottom: 1px solid RGB(233, 233, 233);">
		<div class="Id_card">
			<strong class="Id_card_id">发货时间</strong>
		</div>
		<div class="input_shuru">
			<input id="sendTime" class="input_jihuo" type="text"
				placeholder=" &nbsp;填写发货时间" />
		</div>
	</div>

	<div class="fill_what" style="height: 5px;">
		<!-- <input style="padding-left: 1.2rem;" class="fill_what_input"
			placeholder=" &nbsp;填写你要预售什么产品" /> -->
	</div>
	<div class="fill_what" style="margin-top: 0;">
		<textarea id="remark" style="resize: none; padding-left: 1.2rem;"
			class="fill_what_input" placeholder=" &nbsp;详细介绍下你的产品内容"></textarea>
	</div>

	<div class="uploading_img fpbc">
		<div style="color: RGB(168, 168, 168); margin-left: 5%">产品详情图</div>
		<div id="projectImg0">
		</div>
		<div class="fpbc1">
			<img class="fpbc2" src="${ctx}/groupon/assets/img/pint/mm1.png"
				onclick="uploadFile('0')" />
		</div>
	</div>

	<div class="uploading_img fpbc">
		<div style="color: RGB(168, 168, 168); margin-left: 5%">产品轮播图</div>
		<div id="projectImg1">
		</div>
		<div class="fpbc1">
			<img class="fpbc2" src="${ctx}/groupon/assets/img/pint/mm3.PNG"
				onclick="uploadFile('1')" />
		</div>
		<div class="uploading_wenzi fpbc" style="width: 95%;">请上传清晰的产品图片，建议图片尺寸大于1080× 980</div>

		<!-- 原样式 -->
		<!-- 		<img style="width: 25%; margin-top: 15px; margin-left: 20px;" -->
		<%-- 			src="${ctx}/groupon/assets/img/pint/mm1.png" onclick="uploadFile()" /> --%>
		<!-- 		<div class="uploading_wenzi" style="width: 87%;">请上传清晰的场频图片，建议图片尺寸大于750 -->
		<!-- 			× 400</div> -->
		<!-- 进度条 -->
		<!-- <div style="width: 100%; background-color: #fff">
			<div class="am-progress am-progress-striped am-progress-sm am-active"
				style="height: 0.5rem; margin-bottom: 0">
				<div class="am-progress-bar am-progress-bar-success"
					style="width: 62%"></div>
			</div>
		</div> -->
	</div>

	<div class="plus_sign">
		<div class="plus_sign_left">＋</div>
		<div class="plus_sign_right" id="spell_group">添加拼团产品</div>
	</div>

	<div class="spell_conter_tiao"></div>
	<div id="productList" class="spell_hu">
		<!-- 此区域添加产品列表数据 -->
	</div>

	<div class="fb_btn">
		<div class="publish_project" id="fabu">发布项目</div>
		<div class="statement1">
			<img style="width: 4%; margin-top: -1px; margin-left: 15px;"
				src="${ctx}/groupon/assets/img/pint/xuanzhong.png" /> <span
				style="font-size: 10px; color: RGB(209, 210, 207);">已阅读并同意</span> <span
				style="font-size: 10px; color: RGB(186, 53, 72);">《欢乐拼项目发起条款》</span>
		</div>
	</div>


	<!-- 发布产品弹出 -->

	<div class="tcdown">
		<div class="tcdown_bc">
			<i class="am-icon-times" style="margin-top: 0.5rem;"></i>
		</div>
		<div class="issued_money" style="background-color: #fff;">
			<div class="issued_money_card">
				<strong class="issued_money_id">产品名称</strong>
			</div>
			<div class="issued_money_input_shuru">
				<input id="ProductName" class="issued_money_input_jihuo" type="text"
					placeholder=" &nbsp;填写产品名称" />
			</div>
		</div>
		<div class="issued_money" style="background-color: #fff;">
			<div class="issued_money_card">
				<strong class="issued_money_id">拼售价格</strong>
			</div>
			<div class="issued_money_input_shuru">
				<input id="price" class="issued_money_input_jihuo" type="text"
					placeholder=" &nbsp;填写金额"
					onkeyup="if(this.value==this.value2)return;if(this.value.search(/^\d*(?:\.\d{0,2})?$/)==-1)this.value=(this.value2)?this.value2:'';else this.value2=this.value;" />
			</div>
			<div class="issued_money_yuan">
				<strong>元</strong>
			</div>
		</div>
		<div class="issued_money" style="background-color: #fff;">
			<div class="issued_money_card">
				<strong class="issued_money_id">计量单位</strong>
			</div>
			<div class="issued_money_input_shuru">
				<input id="unit" class="issued_money_input_jihuo" type="text"
					placeholder=" &nbsp;如：个、双、箱等" />
			</div>
		</div>
		<div class="issued_what" style="">
			<textarea id="content" class="issued_what_input"
				placeholder=" &nbsp;填写产品详情"></textarea>
		</div>
		<div id="productImg" class="uploading_img">
			<img onclick="uploadProductFile()"
				style="width: 25%; margin-top: 15px; margin-left: 20px; margin-bottom: 5px;"
				src="${ctx}/groupon/assets/img/pint/mm2.png" />
		</div>
		<!-- 进度条 -->
		<!-- <div style="width: 100%; background-color: #fff">
			<div class="am-progress am-progress-striped am-progress-sm am-active"
				style="height: 0.5rem; margin-bottom: 0">
				<div class="am-progress-bar am-progress-bar-success"
					style="width: 82%"></div>
			</div>
		</div> -->
		<div class="issued_money" style="background-color: #fff;">
			<div class="issued_money_card">
				<strong class="issued_money_id">售卖数量</strong>
			</div>
			<div class="issued_money_input_shuru">
				<input id="num" class="issued_money_input_jihuo" type="text"
					placeholder=" &nbsp;输入数量"
					onkeyup="this.value=this.value.replace(/\D/g,'')"
					onafterpaste="this.value=this.value.replace(/\D/g,'')" />
			</div>
			<div class="issued_money_yuan">
				<strong>份</strong>
			</div>
		</div>
		<div class="bgcolor1" style="height: 5rem; padding-top: 1rem;">
			<div onclick="addProduct()" class="issued_btn" style="margin-top: 0">添加保存</div>
		</div>

	</div>
	<div class="tachu"></div>
	<!-- 图片正在上传中 -->
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
		id="my-modal-loading">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">图片正在上传中 ...</div>
			<div class="am-modal-bd">
				<span class="am-icon-spinner am-icon-spin"></span>
			</div>
		</div>
	</div>
	<!-- 数据正在保存中，请稍等！ -->
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
		id="my-modal-saving">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">数据正在保存中，请稍等！</div>
			<div class="am-modal-bd">
				<span class="am-icon-spinner am-icon-spin"></span>
			</div>
		</div>
	</div>
</body>

<style type='text/css'>
/* Space out content a bit */
body {
	/*padding-top: 20px;*/
	/*   padding-bottom: 20px; */
	
}
</style>
<!-- 模态框 -->
<script>
	$('#yes').click(function() {
		$('#dia').css("display", "none")
		$('.attempt_activate').css("display", "none");

	});
</script>

<!-- 打钩的事件 -->
<script type="text/javascript">
	function show() {
		var tupian = document.getElementById('img');
		if (tupian.style.display == "none") {

			tupian.style.display = "block";

		} else if (tupian.style.display == "block") {

			tupian.style.display = "none";
		}

	}
</script>
<!--滑动 -->


<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.js"></script>
<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.min.js"></script>
<script type='text/javascript'>
	var IsTrue = "${Identification}"//判断用户是否已经验证过
	$(document).ready(function() {
		/* Example 1 */
		$("#currenttime").html(CurentTime());
		$('#ex1').slider({
			formatter : function(value) {
				$('#day').html(value + "");
				$("#currenttime").html(CurentTime(value));
				return '';
			}
		});
		//删除产品数据
		$(".spell_conter_right_img").click(function() {
			delProuduct(this);
		});

	});

	function CurentTime(i) {
		var now = new Date();
		if (i != null)
			now.setDate(now.getDate() + i);
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate();
		var hh = now.getHours(); //时
		var mm = now.getMinutes(); //分
		var clock = year + "-";
		if (month < 10)
			clock += "0";
		clock += month + "-";
		if (day < 10)
			clock += "0";
		clock += day + " ";
		if (hh < 10)
			clock += "0";
		clock += hh + ":";
		if (mm < 10)
			clock += '0';
		clock += mm;
		return (clock);
	}
	// 	$(function(){
	// 		//删除
	// 		$('.shanchu').on('click',function(){
	// 			$(this).parent().css('display','none');
	// 		})
	// 	})
</script>


<!-- 跳转的点击事件 -->
<script type="text/javascript">
	$('#fabu').on('click', function() {
		fabuProject();
	});

	//添加拼团
	$('#spell_group').on('click', function() {
		// 		var inputs = '';
		// 		   // inputs += '<input type="hidden" name="type" value="0" />';
		// 		jQuery('<form action="${ctx}/launch/internetproduct" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
		$('.tcdown').css('display', 'block');
		$('.tachu').css('display', 'block');
	});
	$('.tachu').on('click', function() {
		$('.tcdown').css('display', 'none');
		$('.tachu').css('display', 'none');
	});
	$('.am-icon-times').on('click', function() {
		removePorduct();
	});
</script>
</html>