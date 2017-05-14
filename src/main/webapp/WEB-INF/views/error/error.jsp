<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
<!-- jsp:include page="../head.jsp">
	<jsp:param value="网络出错" name="title" />
</jsp:include -->
<style type="text/css">
</style>
</head>
<body style="margin:0px;padding:0px; position:fixed;top: 0;bottom: 0;z-index: 0;overflow-y: auto;right: 0;left: 0;">
	<div style="width: 100%;position: relative;">
		<img alt="" src="${ctx}/resources/img/error/404_03.png" style="width: 60%;margin-top: 20%;margin-left: 20%">
	</div>
	<!-- 底部导航栏 -->
	<footer id="j-ft"></footer>
</body>
<!-- script type="text/javascript">
$('#j-ft').load('${ctx}/mobile/footer.jsp');
</script -->
</html>