<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>收货人信息</title>
<%@ include file="/common/public.jsp"%>
</head>
<body class="bgcolor2">
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg" alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px;display: inline-block;">收货人信息</span>
		<span class="jias">+</span>
	</div>
	
	<div id="memreco">
     	
     	<div class="yly_sh_reco">
	 		<div class="sh_reco_left">
	 			<div class="sh_reco_left_l">
	 				<input type="radio" id="radio-1-1" name="radio-1-set" class="adresssel radiozj1 regular-radio" /><label class="yl_bc37 radiozj-1" for="radio-1-1"></label><br />
	 			</div>
	 			<div class="sh_reco_left_r">
	 				<div class="reco_left_top yly_color10">
	 					<div class="rlt_left yly_font_size2 font_w1">王冬梅</div>
	 					<div class="rlt_right"><span class="yl_bc36">15812345678</span></div>
	 				</div>
	 				<div class="reco_left_bottom">
	 					<span>福建省厦门市思明区滨海街道思明南路</span>
	 				</div>
	 			</div>
	 		</div>
	 		<div class="sh_reco_right">
	 			<img src="${ctx}/groupon/assets/img/index/pens.png" height="30" />
	 			<img class="deteladre" alt="" src="${ctx}/groupon/assets/img/index/sc.png">
	 		</div>
 		</div>
 		
 		<div class="yly_sh_reco">
	 		<div class="sh_reco_left">
	 			<div class="sh_reco_left_l">
	 				<input type="radio" id="radio-1-2" name="radio-1-set" class="adresssel radiozj2 regular-radio" /><label class="yl_bc37 radiozj-2" for="radio-1-2"></label><br />
	 			</div>
	 			<div class="sh_reco_left_r">
	 				<div class="reco_left_top yly_color10">
	 					<div class="rlt_left yly_font_size2 font_w1">王冬梅</div>
	 					<div class="rlt_right"><span class="yl_bc36">15812345678</span></div>
	 				</div>
	 				<div class="reco_left_bottom">
	 					<span>福建省厦门市思明区滨海街道思明南路</span>
	 				</div>
	 			</div>
	 		</div>
	 		<div class="sh_reco_right">
	 			<img src="${ctx}/groupon/assets/img/index/pens.png" height="30" />
	 			<img class="deteladre" alt="" src="${ctx}/groupon/assets/img/index/sc.png">
	 		</div>
 		</div>
 		
 		<div class="yly_sh_reco">
	 		<div class="sh_reco_left">
	 			<div class="sh_reco_left_l">
	 				<input type="radio" id="radio-1-3" name="radio-1-set" class="adresssel radiozj3 regular-radio" /><label class="yl_bc37 radiozj-3" for="radio-1-3"></label><br />
	 			</div>
	 			<div class="sh_reco_left_r">
	 				<div class="reco_left_top yly_color10">
	 					<div class="rlt_left yly_font_size2 font_w1">王冬梅3</div>
	 					<div class="rlt_right"><span class="yl_bc36">15812345678</span></div>
	 				</div>
	 				<div class="reco_left_bottom">
	 					<span>福建省厦门市思明区滨海街道思明南路</span>
	 				</div>
	 			</div>
	 		</div>
	 		<div class="sh_reco_right">
	 			<img src="${ctx}/groupon/assets/img/index/pens.png" height="30" />
	 			<img class="deteladre" alt="" src="${ctx}/groupon/assets/img/index/sc.png">
	 		</div>
 		</div>
     	
     </div>
</body>
<script>
$(function(){
	
	 //删除
	 $('.deteladre').on('click',function(){
		 $(this).parent().parent().css('display','none');
	 });
	 
	 //添加地址
	  $('.jias').on('click',function(){
		window.location.href=ctx+"/groupon/homeprojectaddadress";
	  });
})
</script>
</html>