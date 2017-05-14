<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心</title>
</head>
<style>
body {
	background-color: rgb(245, 246, 250);
	margin: 0;
	padding: 0;
}

.returnup {
	display: none;
}
</style>
<body>
	<div>
		<img src="${ctx}/groupon/assets/img/pint/help.PNG" width="100%" />
	</div>

	<div class="help_menu li1" style="width: 100%;">
		<img class="help_menuimg" style="margin-bottom: 0.2em;"
			src="${ctx}/groupon/assets/img/pint/wenhao.PNG" /> <span
			style="color: rgb(53, 53, 53);">发起项目相关问题</span> <img
			class="helpjiant" src="${ctx}/groupon/assets/img/pint/jt.png" />
	</div>

	<div class="help_menu li2" style="width: 100%;">
		<img class="help_menuimg" style="margin-bottom: 0.2em;"
			src="${ctx}/groupon/assets/img/pint/wenhao.PNG" /> <span
			style="color: rgb(53, 53, 53);">项目管理相关问题</span> <img
			class="helpjiant" src="${ctx}/groupon/assets/img/pint/jt.png" />
	</div>

	<div class="help_menu li3" style="width: 100%;">
		<img class="help_menuimg" style="margin-bottom: 0.2em;"
			src="${ctx}/groupon/assets/img/pint/wenhao.PNG" /> <span
			style="color: rgb(53, 53, 53);">支持项目相关问题</span> <img
			class="helpjiant" src="${ctx}/groupon/assets/img/pint/jt.png" />
	</div>

	<div class="help_menu li4" style="width: 100%;">
		<img class="help_menuimg" style="margin-bottom: 0.2em;"
			src="${ctx}/groupon/assets/img/pint/wenhao.PNG" /> <span
			style="color: rgb(53, 53, 53);">尝鲜预售相关问题</span> <img
			class="helpjiant" src="${ctx}/groupon/assets/img/pint/jt.png" />
	</div>

	<div class="help_menu1" style="width: 100%; margin-bottom: 8rem">
		<img class="help_menuimh" style="margin-bottom: 0.2em;"
			src="${ctx}/groupon/assets/img/pint/pros.PNG" /> <strong>拓意客在线
			投诉 : 0592-2069229</strong>
<!-- 			 <input type="text" -->
<!-- 			style="height: 3.12rem; font-size: 15px; margin-top: -4px;" value="" -->
<!-- 			placeholder="0592-2069229" readonly="readonly"  /> -->
	</div>

	<!-- 尾部 -->
	<%@ include file="/common/footer.jsp"%>
</body>


<script type="text/javascript">
	/*  发起项目.     点击 .. */
	$('.li1')
			.on(
					'click',
					function() {
						var inputs = '';
						// inputs += '<input type="hidden" name="type" value="0" />';
						jQuery(
								'<form action="${ctx}/groupon/project" method="post">'
										+ inputs + '</form>').appendTo('body')
								.submit().remove();
					});
	/*  项目管理.     点击 .. */
	$('.li2').on(
			'click',
			function() {
				var inputs = '';
				// inputs += '<input type="hidden" name="type" value="0" />';
				jQuery(
						'<form action="${ctx}/groupon/project/standardization_management" method="post">'
								+ inputs + '</form>').appendTo('body').submit()
						.remove();
			});

	/*  支持项目.     点击 .. */
	$('.li3').on(
			'click',
			function() {
				var inputs = '';
				// inputs += '<input type="hidden" name="type" value="0" />';
				jQuery(
						'<form action="${ctx}/groupon/project/standardization_support" method="post">'
								+ inputs + '</form>').appendTo('body').submit()
						.remove();
			});

	/*  尝鲜预售.     点击 .. */
	$('.li4').on(
			'click',
			function() {
				var inputs = '';
				// inputs += '<input type="hidden" name="type" value="0" />';
				jQuery(
						'<form action="${ctx}/groupon/project/standardization_presell" method="post">'
								+ inputs + '</form>').appendTo('body').submit()
						.remove();
			});
</script>
</html>

