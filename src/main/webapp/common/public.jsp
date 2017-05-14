<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- css -->
<link rel="stylesheet"
	href="${ctx}/groupon/assets/css/amazeui.flat.min.css" />
<link rel="stylesheet" href="${ctx}/groupon/assets/css/app.css" />
<link rel="stylesheet" href="${ctx}/groupon/assets/css/wt.css" />
<link rel="stylesheet" href="${ctx}/groupon/assets/css/zjwupdate.css" />


<link rel="stylesheet"
	href="${ctx}/groupon/assets/css/css1/bootstrap-slider.css" />
<link rel="stylesheet"
	href="${ctx}/groupon/assets/css/css1/bootstrap-slider.min.css" />
<!-- The below link cause loading page extemly slow ? -->
<!-- link rel="stylesheet" href="${ctx}/groupon/assets/css/css1/default.css" / -->
<link rel="stylesheet"
	href="${ctx}/groupon/assets/css/css1/normalize.css" />





<!-- js -->
<script src="${ctx}/groupon/assets/js/jquery.min.js"></script>
<script src="${ctx}/groupon/assets/js/amazeui.min.js"></script>
<script src="${ctx}/groupon/assets/js/amazeui.js"></script>


<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.js"></script>
<script src="${ctx}/groupon/assets/js/js1/bootstrap-slider.min.js"></script>
<script src="${ctx}/groupon/assets/js/JSON-js-master/json2.js"></script>

<script src="${ctx}/resources/js/common.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/qrcode/jquery.qrcode.min.js"></script>
<script>
	var ctx = '${ctx}';
	var aliURL = "http://toyke.oss-cn-hangzhou.aliyuncs.com/";

	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val)
				return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	
	// 判断是否微信浏览器
	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
</script>





