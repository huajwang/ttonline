<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>商品评价</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css" />
<script src="${ctx}/resources/js/layer/layer.js"></script>
<style type="text/css">
.yl_bc70 ul {
	width: 80%;
}

.yl_bc72 {
	width: 30%;
	height: 3.5rem;
	color: RGB(136, 136, 136);
	font-size: 3px;
	line-height: 3.5rem;
	float: left;
	text-align: center;
}

.yl_bc711 {
	float: left;
	padding-left: 4%;
	font-size: 15px;
	min-height: 1rem;
	line-height: 1.5rem;
	max-height: 100rem;
	width: 92%;
	height: auto;
}

.yl_bc72 {
	width: 92%;
	height: 2rem;
	margin: auto;
	color: RGB(136, 136, 136);
	text-align: left;
	padding-left: 4%;
	line-height: 2rem;
	font-size: 15px;
}
</style>
<script type="text/javascript">
	//点击图片放大
	function openImg(_this) {
		var src = $(_this).attr("src");
		var height = $(document.body).outerHeight(true)*0.6;//计算高度
		var width = $(document.body).outerWidth(true)*0.9;//计算宽度
		layer.open({
			type : 1,
			title : null,
			area : [ width+"px", height+"px" ],
			skin : 'layui-layer-rim', //没有背景色
			shadeClose:true,
			content : '<img src="'+src+'" height="100%" width="100%" />'
		});
	}
</script>
</head>

<body>
	<!--
    	时间：2016-04-27
    	描述：top
    -->
	<div class="yly_or_top" style="position: fixed; top: 0;">
		<div class="or_top_left">
			<img src="${ctx}/resources/img/back.png" onclick="history.go(-1);"
				height="20" />
		</div>
		<div class="or_top_right">
			<p>商品评价</p>
		</div>
	</div>

	<div class="comment_lis"
		style="margin-top: 4.5rem; margin-bottom: 4rem;">
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach items="${list}" var="item" varStatus="vs">
					<div class="com_lis_reco"
						style="min-height: 1rem; max-height: 100rem;">
						<div class="com_lis_reco_top">
							<div class="yl_bc67" style="margin-top: 0">
								<img src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${item.iconUrl}" height="100%" />
							</div>
							<div class="yl_bc68" style="width: 36%">${item.userName}</div>
							<div class="yl_bc70" style="width: 30%">
								<c:if test="${item.rating==5 }">
									<ul>
										<li></li>
										<li></li>
										<li></li>
										<li></li>
										<li></li>
									</ul>
								</c:if>
								<c:if test="${item.rating==4 }">
									<ul>
										<li></li>
										<li></li>
										<li></li>
										<li></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
									</ul>
								</c:if>
								<c:if test="${item.rating==3 }">
									<ul>
										<li></li>
										<li></li>
										<li></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
									</ul>
								</c:if>
								<c:if test="${item.rating==2 }">
									<ul>
										<li></li>
										<li></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
									</ul>
								</c:if>
								<c:if test="${item.rating==1 }">
									<ul>
										<li></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
										<li
											style="background: url('${ctx}/resources/images/xininx.png') no-repeat;background-size: 100% 100%;"></li>
									</ul>
								</c:if>
							</div>
						</div>
						<div class="com_lis_reco_bot"
							style="min-height: 1rem; max-height: 100rem; float: left; padding-top: 0.5rem;">
							<div class="yl_bc711">${item.content }
								<div>
									<c:choose>
										<c:when test="${not empty item.imgList }">
											<c:forEach items="${item.imgList}" var="itemImg"
												varStatus="vs1">
												<img
													src="http://toyke.oss-cn-hangzhou.aliyuncs.com/${itemImg.icon}"
													height="80px" width="80px" onclick="openImg(this)" />
											</c:forEach>
										</c:when>
									</c:choose>
								</div>
							</div>
							<div class="yl_bc72">${item.createTime }</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
		</c:choose>



	</div>

	<div class="yly_end">没有更多了</div>

</body>
</html>
