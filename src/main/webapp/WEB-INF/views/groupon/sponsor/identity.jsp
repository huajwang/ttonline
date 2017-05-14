<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>身份验证</title>
</head>
<body>
	<div class="status_top1">
		<div class="status_top_left1">
			<strong>发起人</strong>
		</div>
		<div class="statusl_top_right1">
			<div style="float: left">
				<img style="width: 18px; margin-top: 1px; margin-left: 46px;"
					src="${ctx}/groupon/assets/img/pint/tb2.png" />
			</div>
			<div style="color: RGB(186, 53, 72); font-size: 14px; float: left;">帮助</div>
		</div>
	</div>
	<div id="geyan" class="personage">
		<div class="personage_left">
			<div class="personage_left_shang">
				<strong>个人名义发起</strong>
			</div>
			<div class="personage_left_xia">需上传个人手持身份证照片</div>
		</div>
		<img class="personage_right"
			src="${ctx}/groupon/assets/img/pint/jiao1.png" />
	</div>

	<div id="qiyan" class="company">
		<div class="company_left">
			<div class="company_left_shang">
				<strong>个体/企业名义发起</strong>
			</div>
			<div class="company_left_xia">需上传营业执照</div>
		</div>
		<img class="company_right"
			src="${ctx}/groupon/assets/img/pint/jiao1.png" />
	</div>

</body>
<script type="text/javascript">
	//个人发起
	$('#geyan').on('click', function() {
		window.location.href = ctx + "/groupon/launch/identifiable";
	});
	//企业发起
	$('#qiyan').on('click', function() {
		window.location.href = ctx + "/groupon/launch/legalentity";
	});
</script>
</html>