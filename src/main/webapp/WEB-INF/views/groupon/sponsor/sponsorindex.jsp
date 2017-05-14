<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>发起拼团</title>
	<%@ include file="/common/public.jsp"%>
<style type="text/css">
.returnup{
	display: none;
}
</style>
</head>
<body id="bk" style="background-color: RGB(229, 229, 229); margin: 0; padding: 0;">
	<div class="wai_bu">
		<div class="project_launch">
			<img class="project_launch_left_img"
				src="${ctx}/groupon/assets/img/pint/fq1.png" /> <span>项目发起</span>
			<img class="project_launch_sj" id="portraitid"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

		<div class="Myproject">
			<img class="Myproject_left_img"
				src="${ctx}/groupon/assets/img/pint/fq2.png" /> <span>我的项目</span>
			<img class="Myproject_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

		<div class="About">
			<img class="About_left_img"
				src="${ctx}/groupon/assets/img/pint/fq3.png" /> <span>关于欢乐拼
			</span> <img id="together_id" class="About_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

		<div class="withdrawal">
			<img class="withdrawal_left_img"
				src="${ctx}/groupon/assets/img/pint/fq4.png" /> <span>提现记录</span>
			<img id="withdrawal_id" class="withdrawal_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

		<div class="exchange">
			<img class="exchange_left_img"
				src="${ctx}/groupon/assets/img/pint/fq5.png" /> <span>退货及换货</span>
			<img id="exchange_id" class="exchange_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

		<div class="helpfeedback">
			<img class="helpfeedback_left_img"
				src="${ctx}/groupon/assets/img/pint/fq6.png" /> <span>帮助与反馈</span>
			<img id="helpfeedback_id" class="helpfeedback_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>

	</div>

	<!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>

</body>
<!-- 跳转到发布产品页面的点击事件 -->
<script type="text/javascript">
	//项目发起
	$('.project_launch').on('click',function(){
		window.location.href=ctx+"/groupon/launch/projectfq";
	});

	$('.About').on('click',function(){
		window.location.href=ctx+"/groupon/launch/regard";
	});
	
	$('.withdrawal').on('click',function(){
		window.location.href=ctx+"/groupon/launch/withdrawal";
	});
	
	$('.exchange').on('click',function(){
		window.location.href=ctx+"/groupon/launch/salesreturn";
	});
	
	$('.helpfeedback').on('click',function(){
		window.location.href=ctx+"/groupon/launch/helpcenter";
	});
	
	//我的项目
	$('.Myproject').on('click',function(){
		window.location.href=ctx+"/groupon/launch/initiate";
	});
</script>
</html>