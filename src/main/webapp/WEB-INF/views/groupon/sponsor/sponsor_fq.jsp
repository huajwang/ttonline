<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>发起</title>
<script type="text/javascript">
	function back(){
		window.location.href=ctx+"/groupon/launch/";
	}
</script>
<%@ include file="/common/public.jsp"%>
</head>
<body id="bk" style="background-color: #fff; margin: 0; padding: 0;">
	<!-- 模态框 -->
	<div class="mt_kuang" id="beijing" style="display: block;"></div>
	<div class="mt_activate" style="display: block;">
		<div class="mt_nickname">发起须知</div>
		<div class="mt_conter">
			<span>根据相关法律法规，项目提交后，须经过网站工作人员审核后才能发布；<br>
					根据项目的内容，工作人员会要求发起人提供相关材料，证明项目的可行性，以及发起人的 执行能力；<br>
					网站对提交项目审核的项目是否拥有上线资格具有最终决定权（具备上线资格的项目的上线 时间由项目发起人自行决定）。</span>
		</div>
		<div class="mt_btn" id="ld">知道了</div>
	</div>

	<!-- 第二个页面 -->
	<div class="presell">
		<!-- 头部 -->
		<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
			<img onclick="back()" class="prodetalieimg"
				alt=""
				src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
			<span style="font-size: 18px; display: inline-block;">发起</span> <img
				style="float: right; margin-top: 1.05em; padding-right: 10px;display:none;"
				alt="" src="${ctx}/groupon/assets/img/pint/tb1.png">
		</div>

		<div class="launched">
			<img class="launched_left_img"
				src="${ctx}/groupon/assets/img/pint/ys1.png" />
			<div class="zzt">
				<span style="font-size: 15px; color: RGB(97, 219, 204);" id="yushou">尝鲜预售</span><br />
				<span style="font-size: 12px;">新品上市速尝鲜</span>
			</div>
			<img class="launched_sj"
				src="${ctx}/groupon/assets/img/pint/jiao1.png" />
		</div>
		<div class="launched_btn"></div>
	</div>

	<!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>

</body>
<!-- 首页的模态框事件 -->
<script type="text/javascript">
	$('.project_launch').click(function() {
		$('#beijing').css("display", "block");
		$('#bk').css("background", "#fff");
		$('.wai_bu').css("display", "none");
		$('.presell').css("display", "block");
		$('.mt_activate').css("display", "block");

	});
	$('#ld').click(function() {
		$('#beijing').css("display", "none");
		$('.mt_activate').css("display", "none");
	});
</script>
<!-- 跳转到发布产品页面的点击事件 -->
<script type="text/javascript">
	//发布产品
	$('.launched').on(
			'click',
			function() {
				window.location.href=ctx+"/groupon/launch/product";
			});
</script>
</html>