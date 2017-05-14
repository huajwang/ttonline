<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>我的购物</title>
	<%@ include file="/common/public.jsp"%>
</head>
<style type="text/css">
 
			
</style>
<body style="margin:0;padding:0;background-color: RGB(245,246,250);"> 
  <!-- 模态框 -->
   <div class="delivery_kuang" id="beijing"></div>
		<div class="delivery_activate">
		    <div class="delivery_prompt">提示</div>
		    <div class="delivery_prompt_text">
		        <span>如果未收到货物，请勿点击"确定"按钮。如点击确定收货后将默认您已收到产品</span>
		    </div>
		    <div class="delivery_prompt_btn" id="delivery_determine">确定</div>
		    <div class="delivery_prompt_btn" id="delivery_cancle">取消</div>
		</div>

        <div class="delivery">
			<div class="delivery_zt a">
			<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
				<div class="delivery_zt_pu3  delivery_click waitget" id="daifa" style="height: 4rem;">
					<div class="delivery_zt_pu1 ">待发货</div>
				</div>
				<div class="delivery_zt_pu3 finish" id="yifa" style="height: 4rem;">
					<div class="delivery_zt_pu1">待收货</div>
				</div>
				<div class="delivery_zt_pu3 finish" id="yiwan" style="height: 4rem;">
					<div class="delivery_zt_pu1">已完成</div>
				</div>
			</div>
		</div>
		
		
<!-- 已发货 -->		
<div id="zhi">
	<c:if test="${not empty waitlist}">
	<c:forEach items="${waitlist}" var="order" varStatus="vs">		
		<div class="delivery_top ">
		   <div class="delivery_top_1  delivery_ling1" style="">${order.realName}</div>
		   <div class="delivery_top_1 delivery_ling2" style="">${order.name}</div>
		   <div class="delivery_top_1 delivery_ling3" style="">已付款</div>
		</div>
		
		<div class="delivery_zhuti">
			<c:forEach items="${order.productList}" var="product" varStatus="vsp">
			<c:choose>
			<c:when test="${vsp.index ==0}">
		     <div class="delivery_conter">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:when>
		     <c:otherwise>
		     	<div class="delivery_conter delivery_bating">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:otherwise>
		     </c:choose>
		     </c:forEach>
		     
		     <div class="delivery_amount">
		           <div class="delivery_amount_text1"><span style="color:RGB(155,155,155);font-size:15px;">订单金额：</span> <strong style="color:RGB(54,54,54);font-size:14px;">￥${order.amount}</strong></div>
		           <div class="delivery_amount_text2"><span style="">下单时间：</span> <span>${order.createTime }</span></div>
		     </div>
		   
		</div>
		  <div class="delivery_btn">
		      <!-- <div class="delivery_btn_niu" id="make_sure" onclick="confirm(${order.id});">确定收货</div> -->
		  </div>
	</c:forEach>
	</c:if>
</div>
		
<!-- 已发货 -->		
<div id="fa" style="display:none">
	<c:if test="${not empty list}">
	<c:forEach items="${list}" var="order" varStatus="vs">		
		<div class="delivery_top ">
		   <div class="delivery_top_1  delivery_ling1" style="">${order.realName}</div>
		   <div class="delivery_top_1 delivery_ling2" style="">${order.name}</div>
		   <div class="delivery_top_1 delivery_ling3" style="">已发货</div>
		</div>
		
		<div class="delivery_zhuti">
			<c:forEach items="${order.productList}" var="product" varStatus="vsp">
			<c:choose>
			<c:when test="${vsp.index ==0}">
		     <div class="delivery_conter">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:when>
		     <c:otherwise>
		     	<div class="delivery_conter delivery_bating">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:otherwise>
		     </c:choose>
		     </c:forEach>
		     
		     <div class="delivery_amount">
		           <div class="delivery_amount_text1"><span style="color:RGB(155,155,155);font-size:15px;">订单金额：</span> <strong style="color:RGB(54,54,54);font-size:14px;">￥${order.amount}</strong></div>
		           <div class="delivery_amount_text2"><span style="">下单时间：</span> <span>${order.createTime }</span></div>
		     </div>
		   
		</div>
		  <div class="delivery_btn">
		       <div class="delivery_btn_niu" id="make_sure" onclick="confirm(${order.id});">确定收货</div>
		  </div>
	</c:forEach>
	</c:if>
</div>

<!-- 已完成 -->		
<div id="shou" style="display:none">
	<c:if test="${not empty finishlist}">
	<c:forEach items="${finishlist}" var="order" varStatus="vs">		
		<div class="delivery_top ">
		   <div class="delivery_top_1  delivery_ling1" style="">${order.realName}</div>
		   <div class="delivery_top_1 delivery_ling2" style="">${order.name}</div>
		   <div class="delivery_top_1 delivery_ling3" style="">已收货</div>
		</div>
		
		<div class="delivery_zhuti">
			<c:forEach items="${order.productList}" var="product" varStatus="vsp">
		     <c:choose>
			<c:when test="${vsp.index ==0}">
		     <div class="delivery_conter">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:when>
		     <c:otherwise>
		     	<div class="delivery_conter delivery_bating">
		         <a href="${product.Icon}"><img class="delivery_conter_img" style="height:100%;" src="${product.Icon}"/></a>
		         <div class="delivery_conter_left"> ${product.price}元/${product.unit}</div>
		         <div class="delivery_conter_right">${product.num}份</div>
		     </div>
		     </c:otherwise>
		     </c:choose>
		     </c:forEach>
		     
		     <div class="delivery_amount">
		           <div class="delivery_amount_text1"><span style="color:RGB(155,155,155);font-size:15px;">订单金额：</span> <strong style="color:RGB(54,54,54);font-size:14px;">￥${order.amount}</strong></div>
		           <div class="delivery_amount_text2"><span style="">下单时间：</span> <span>${order.createTime }</span></div>
		     </div>
		   
		</div>
		  <div class="delivery_btn">
		       <div class="delivery_btn_niu" id="apply_refund" onclick="applyReturn(${order.id})">申请退货</div>
		  </div>
	</c:forEach>
	</c:if>
</div>				

<input type="hidden" id="curOrderId" /><!-- 当前要确认收货的订单id -->
		
</body>
<script>
		//顶部标签样式切换
		$(function(){
			$('.delivery_zt_pu3').on('click',function(){
				$(this).siblings().removeClass('delivery_click');
				$(this).addClass('delivery_click');
			});
		});
</script>
<!-- 点击切换的js -->
<script type="text/javascript">
function confirm(id){
    $('#beijing').css("display","block");
    $('.delivery_activate').css("display","block");
    $('#curOrderId').val(id);
};

/* 点击确定触发的事件*/
$('#delivery_determine').click(function(){
	$.ajax({
		type : 'post',
		url : ctx + "/groupon/personal/confirm",
		data : {
			'id' : $('#curOrderId').val(),
		},
		success : function(result) {
			var json = eval("(" + result + ")");
			if(json['status']=='0')	{
				window.location.reload();
				$('#beijing').css("display","none");
			    $('.delivery_activate').css("display","none");
			    $('.finish').addClass("delivery_click");
			    $('.waitget').removeClass("delivery_click");
			    $('#shou').css("display","block");
			    $('#fa').css("display","none");
			}		
		}
	});
});

/* 点击取消触发的时间*/
$('#delivery_cancle').click(function(){
    $('#beijing').css("display","none");
    $('.delivery_activate').css("display","none");
    $('.finish').addClass("delivery_click");
    $('.waitget').removeClass("delivery_click");
    $('#shou').css("display","block");
    $('#fa').css("display","none");
});
/* 点击待发货触发的事件 */
$('#daifa').click(function(){
	$('#zhi').css("display","block");
	$('#shou').css("display","none");
    $('#fa').css("display","none");
});
/* 点击待收货触发的事件 */
$('#yifa').click(function(){
    $('#zhi').css("display","none");
	$('#shou').css("display","none");
    $('#fa').css("display","block");
});
/* 点击已完成触发的事件 */
$('#yiwan').click(function(){
    $('#zhi').css("display","none");
	$('#shou').css("display","block");
    $('#fa').css("display","none");
});

</script>

<script type="text/javascript">
	function applyReturn(id){
		var inputs = '<input type="hidden" name="orderid" value="'+id+'"/>';
		   //inputs += '<input type="hidden" name="type" value="0" />';
		   //jQuery('<form action="${ctx}/groupon/personal/apply?id='+'$("#curOrderId").val()'+'" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
		jQuery('<form action="${ctx}/groupon/personal/apply" method="post">' + inputs + '</form>').appendTo('body').submit().remove();
	};
</script>
	
</html>