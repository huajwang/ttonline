<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>编辑</title>
<%@ include file="/common/public.jsp"%>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: rgb(245, 246, 250);
}
</style>
<script type="text/javascript">
	function back(){
		window.location.href=ctx+"/groupon/launch/initiate/";
	}
</script>
</head>
<body>

	<div class="dialog" id="ment" style="display: none"></div>
	<div class="management_activate" style="display: none">
		<div class="management_motai">
			<div class="management_motai_top">项目管理</div>
			<div class="management_motai_btn_top">
				<span id="tojiaoyi" class="management_motai_conter"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project11.png" /></span><br>
					<span>拼团交易</span>
				</span> <span id="tobianji" class="management_motai_conter"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project22.png" /></span><br>
					<span>编辑项目</span>
				</span> <span class="management_motai_conter toprojectup_schedule">
					<span><img style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project33.png" /></span><br>
					<span>更新动态</span>
				</span> <span class="management_motai_conter toprojectsettlement"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project44.png" /></span><br>
					<span>项目结算</span>
				</span> <span class="management_motai_conter"> <span id="qun"><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project55.png" /></span><br>
					<span>官方qq群</span>
				</span> <span class="management_motai_conter tohelpcenter"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project66.png" /></span><br>
					<span>帮助中心</span>
				</span> <span class="management_motai_conter touptime" style="display:none"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project77.png" /></span><br>
					<span>修改时间</span>
				</span> <span class="management_motai_conter toupprice" style="display:none"> <span><img
						style="width: 50%;"
						src="${ctx}/groupon/assets/img/projectmanage/project88.png" /></span><br>
					<span>修改金额</span>
				</span>
			</div>
		</div>
		<div class="management_motai_btn">取消</div>
	</div>

	<div class="management_activate2" style="display: none">
		<div class="management_motai_top2">官方qq群</div>
		<div class="management_motai_btn_top2">
			<span class="management_motai_conter2"> <span><img
					style="width: 50%;"
					src="${ctx}/groupon/assets/img/projectmanage/project55.png" /></span><br>
				<span>尝鲜预售1群</span><br /> <span
				class="management_motai_conter_text">88888888</span>
			</span> <span class="management_motai_conter2"> <span><img
					style="width: 50%;"
					src="${ctx}/groupon/assets/img/projectmanage/project55.png" /></span><br>
				<span>尝鲜预售2群</span><br /> <span
				class="management_motai_conter_text">88888888</span>
			</span> <span class="management_motai_conter2"> <span><img
					style="width: 50%;"
					src="${ctx}/groupon/assets/img/projectmanage/project55.png" /></span><br>
				<span>尝鲜预售3群</span><br /> <span
				class="management_motai_conter_text">88888888</span>
			</span>
		</div>

		<div class="management_motai_btn2">关闭</div>
	</div>





	<div class="presell_top">
		<div class="presell_top_left">尝鲜预售</div>
		<div class="presell_top_right">
			<div class="promanage_edit_xg ztcolor" id="management_id">项目管理</div>
		</div>
	</div>
	<img id="fan_hui" onclick="back();" style="position: fixed;width:40px;margin-top:-1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
	<div class="spell_money bgcolor1">
		<div class="spell_money_card">
			<strong class="spell_money_id">筹款金额</strong>
		</div>
		<div class="promanage_edit">${project.amount }</div>
	</div>

	<div class="stop_date bgcolor1">
		<div class="stop_wenzi">
			<div class="stop_wenzi_left">
				<strong>截止日期</strong>
			</div>
			<div class="stop_wenzi_right">
				<span class="fontcolor12 promanage_edit_bc22"><fmt:formatDate type="time" value="${project.endTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
				<span class="fontcolor12">共</span> <span id="day"
					class="fontcolor11">7</span> <span class="fontcolor12">天</span>
			</div>
		</div>
		<div class="gun_tiao">
			<div class="gun_tiao_left">1天</div>
			<div class='slider-example'>
				<div class="well">
					<input id="ex1" data-slider-id='ex1Slider' type="text"
						data-slider-min="1" data-slider-max="30" data-slider-step="1"
						data-slider-value="${project.dayNum }" />
				</div>

			</div>

			<div class="gun_tiao_right">30天</div>

		</div>
	</div>

	<!-- <div class="promanage_edit_bc1">
		<span><strong class="Id_card_id promanage_edit_bc10">设为隐私项目</strong></span>
		<span class="fontsize10 fontcolor2">开启后不会被搜索到</span>
		<span class="kaiguan am-icon-toggle-off fontcolor2 fontsize40 promanage_edit_bc11" data-status="off"></span>
	</div>
	<div class="promanage_edit_bc1">
		<span><strong class="Id_card_id promanage_edit_bc10">需要支持者收件地址</strong></span>
		<span class="kaiguan am-icon-toggle-off fontcolor2 fontsize40 promanage_edit_bc11" data-status="off"></span>
	</div> -->
	<div class="promanage_edit_bc1">
		<span><strong class="Id_card_id promanage_edit_bc10">运费</strong></span>
		<span class="promanage_edit_bc2">${project.EMS }</span>
	</div>
	<div class="promanage_edit_bc1">
		<span><strong class="Id_card_id promanage_edit_bc10">发货时间</strong></span>
		<span class="promanage_edit_bc2">${project.sendTime }</span>
	</div>



	<!-- 产品信息 -->
	<div class="yandfti dtbc promanage_edit_bc9 bgcolor1 fontcolor12"
		style="margin-top: 1rem; border-top: 1px solid #ccc;">
		<strong class="Id_card_id">项目名称</strong> <span
			class="promanage_edit_bc2" style="text-overflow: ellipsis;overflow:hidden;"><nobr>${project.name }</nobr></nobr></span>
	</div>

	<div class="pro_editss bgcolor1">${project.remark }</div>

	<div class="yandfti dtbc promanage_edit_bc9 bgcolor1 fontcolor12"
		style="border: none; border-bottom: 1px solid #ccc;">
		<strong class="Id_card_id">产品信息</strong>
	</div>

	<div class="yunf bgcolor1 promanage_edit_bc17">
		<c:choose>
			<c:when test="${not empty project.product}">
				<c:forEach items="${project.product}" var="item" varStatus="vs">
					<div class="promanage_edit_bc18">
						<img class="promanage_edit_bc19" src="${item.Icon }"> <span
							class="ztcolor promanage_edit_bc20"> <span>名称：${item.ProductName }</span><br>
							<span>价格：${item.price }/${item.unit }</span>
						</span> <span class="promanage_edit_bc21">支持 ${item.orderNum } 份</span>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>

	<!-- 拼团动态 -->
	<div
		class="yandfti coss dtbc promanage_edit_bc9 bgcolor1 promanage_edit_bc16">我的拼团动态</div>
	<div class="dynamic promanage_edit_bc15">
		<c:choose>
			<c:when test="${not empty schedule}">
				<c:forEach items="${schedule}" var="item" varStatus="vs">
					<div class="dtsczt">
						<img class="detail_ztmemimg dynamicbc" alt=""
							src="${item.iconUrl}"> <img class="xiaoimg bgcolor1" alt=""
							src="${ctx}/groupon/assets/img/index/indexprojectdetail/jian.png">
						<span class="dtname fontcolor3">${item.userName}</span> <span
							class="fontcolor5">发布进度更新</span><br> <span
							class="fontcolor3 fontsize13 dttime">发布时间：${item.createTime}</span>
						<img class="plcs" alt=""
							src="${ctx}/groupon/assets/img/index/indexprojectdetail/02_proindex_07.png">
					</div>
					<div class="memztde">
						<div class="memztde_r">
							<div class="memztde_r_top promanage_edit_bc8">
								<span class="ztcolor">${item.content}</span>
							</div>
						</div>
					</div>

				</c:forEach>
			</c:when>
		</c:choose>


	</div>





	<!-- 支持 -->
	<!-- 	<div class="zc_an bgcolor1 promanage_edit_bc12">
		<div class="zc_an_l promanage_edit_bc12 fontcolor2">
			<span class="am-icon-star-o zc_an_lbc promanage_edit_bc23"></span><br>
			<span class="fontsize14">关注</span>
		</div>
		<div class="zc_an_c promanage_edit_bc12">
			<div id="zhichi" class="ancss ztbgcolor fontcolor1 promanage_edit_bc14" >我要支持</div>
		</div>
		<div class="zc_an_r promanage_edit_bc12 fontcolor2">
			<span class="am-icon-share zc_an_lbc promanage_edit_bc23"></span><br>
			<span class="fontsize14">分享</span>
		</div>
	</div> -->

</body>
<!--滑动 -->

<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.js"></script>
<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.min.js"></script>
<script type='text/javascript'>
	var status = "${project.status }";//项目状态
	var id = "${project.id }";//项目状态
	var amount = "${project.amount }";//目标金额
	var endTime = "${project.endTime }";//截止时间
	var dayNum = "${project.dayNum }";//天数
	var createTime="${project.createTime}";//创建时间
	var SettType="${SettType}";//结算类型
	$(document).ready(function() {
		/* Example 1 */
		$('#ex1').slider({
			formatter : function(value) {
				$('#day').html(value + "");
				return '';
			}
		});
	});
	$('.kaiguan').on('click', function() {
		var status = $(this).data('status');
		if (status == 'off') {
			$(this).data('status', 'on');
			$(this).removeClass('am-icon-toggle-off');
			$(this).removeClass('fontcolor2');
			$(this).addClass('am-icon-toggle-on');
			$(this).addClass('ztcolor');
		} else {
			$(this).data('status', 'off');
			$(this).addClass('am-icon-toggle-off');
			$(this).addClass('fontcolor2');
			$(this).removeClass('am-icon-toggle-on');
			$(this).removeClass('ztcolor');
		}
	});
	$(function() {

		/*菜单跳转**/
		//拼团交易
		$('#tojiaoyi').on(
				'click',
				function() {
					window.location.href = ctx
							+ "/groupon/project/allorderlis?id=" + id;
				});
		//编辑项目
		$('#tobianji').on(
				'click',
				function() {
					window.location.href = ctx
							+ "/groupon/project/projectedit?id=" + id;
				});
		//更新动态
		$('.toprojectup_schedule').on(
				'click',
				function() {
					window.location.href = ctx
							+ "/groupon/project/projectup_schedule?id=" + id;
				});
		//项目结算
		$('.toprojectsettlement').on(
				'click',
				function() {
					//拼团不是结束状态，不能结算
					if(status!="4"){
						alert("该项目还没到可以结算的状态");
						return;
					}
					if(SettType=="1"){
						alert("该项目在结算中或者已经结算，不能重复提交！");
						return;						
					}
					window.location.href = ctx
							+ "/groupon/project/projectsettlement?id=" + id;
				});
		//帮助中心
		$('.tohelpcenter').on('click', function() {
			window.location.href = ctx + "/groupon/launch/helpcenter";
		});
		//修改时间
		$('.touptime').on(
				'click',
				function() {
					if (status != "1") {
						alert("已经审核完成，不能修改！");
						return;
					}
					window.location.href = ctx
							+ "/groupon/project/projectup_modificationtime?id="
							+ id + "&endTime=" + endTime + "&dayNum=" + dayNum+"&createTime="+createTime;
				});
		//修改金额
		$('.toupprice')
				.on(
						'click',
						function() {
							if (status != "1") {
								alert("已经审核完成，不能修改！");
								return;
							}
							window.location.href = ctx
									+ "/groupon/project/projectup_modificationamount?id="
									+ id+ "&amount=" + amount;
						});
		//我要支持
		$('#zhichi').on('click', function() {
			+"/groupon/project/projectsettlement?id=" + id;
		});
	});
</script>
<!-- 模态框 -->
<script>
	$('#management_id').click(function() {
		$('#ment').css("display", "block")
		$('.management_activate').css("display", "block");
	});

	$('.management_motai_btn').click(function() {
		$('#ment').css("display", "none")
		$('.management_activate').css("display", "none");
	});

	$('#qun').click(function() {
		$('.management_activate').css("display", "none")
		$('.management_activate2').css("display", "block");
	});

	$('.management_motai_btn2').click(function() {
		$('.management_activate2').css("display", "none");
		$('#ment').css("display", "none");
	});
</script>
</html>