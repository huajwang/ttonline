<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>去评价</title>
<%@ include file="/common/include.rec.ftl"%>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/yly_zjw.css" />
<script type="text/javascript"
	src="${ctx}/resources/js/zjw_js/zjw_js.js"></script>


<script src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript">
	var aliURL = "http://toyke.oss-cn-hangzhou.aliyuncs.com/";
	var orderId = Request["orderId"];
	var gid = Request["gid"];
	var detailId = Request["detailId"];
	function indexan() {
		var content = encodeURI($("#content").val());
		if (content == "") {
			alert("请输入评价内容!");
			return;
		}
		if (_taidu == "") {
			alert("请做出评价等级!");
			return;
		}
		var r = $.ajax({
			type : "post",
			url : ctx + "/member/ajaxAddEvaluation.api",
			async : false,
			data : "orderId=" + orderId + "&gId=" + gid + "&rating=" + _taidu
					+ "&detailId=" + detailId + "&content="
					+ encodeURI(content)+"&Icon="+Icon.toString()
		}).responseText;
		var json = eval("(" + r + ")");
		if (json["status"] == "0") {
			alert("评价成功！");
			var url = ctx + "/member/";
			window.location.href = url;
		}
	}
	//上传图片
	var Icon = new Array();// 项目图片集合
	function uploadFile() {
		var obj = jQuery(
				'<input multiple="true" type="file" id="inputfile" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
				.appendTo('body');
		obj.click();// 点击弹出文件对话框，选择图片文件
		$('#inputfile').change(function() {
							var index = layer.load(0, {
								shade : [ 0.2, '#030303' ]//0.1透明度的白色背景
							});
							var data = new FormData();
							var len = $('#inputfile')[0].files.length;
							for (var i = 0; i < len; i++) {
								data.append('picFile',$('#inputfile')[0].files[i]);
							}
							$.ajax({
										url : ctx
												+ '/common/multipleAjaxUploadPic.api',
										type : 'POST',
										data : data,
										cache : false,
										contentType : false, // 不可缺参数
										processData : false, // 不可缺参数
										success : function(data) {
											obj.remove();
											var json = eval("(" + data + ")");
											var len = json["data"].length;
											for (var i = 0; i < len; i++) {
												var picData = json["data"][i];
												var picStr = picData["picStr"];
												Icon.push(picStr);
												$("#projectImg")
														.prepend(
																'<img width="120px" height="120px" style="margin-right: 3px;" src="'
												+ aliURL
												+ picStr
												+ '" />');
											}
											layer.close(index);//关闭
											layer.msg('上传成功！');
										},
										error : function() {
											obj.remove();
											layer.msg('上传出错');
										}
									});
						});
	}
</script>
</head>
<body class="yly_bg_color1">

	<div class="lis_top">
		<div class="yl_bc53">
			<img src="${ctx}/resources/img/back.png" onclick="history.go(-1);"
				height="20" />
		</div>
		<div class="yl_bc54">去评价</div>
	</div>

	<div class="reco_comment">
		<div class="reco_detai">
			<div class="yl_bc55">
				<img src="${iconUrl }" height="85"
					style="margin-top: 1.5rem; margin-left: 2%;" />
			</div>
			<div class="yl_bc56">
				<div class="yl_bc57" style="height:29%">${gName }</div>
				<div class="yl_bc58">
					<c:if test="${not empty size}">
						<div>尺码：${size}</div>
					</c:if>

					<c:if test="${not empty colour}">
						<div class="yl_bc12">颜色：${colour}</div>
					</c:if>

				</div>
			</div>
		</div>
		<div class="yl_bc59" style="border: none; height: 6.5rem;">
			<div class="yl_bc61">
				<textarea id="content" class="yl_bc62" name="" rows="" cols=""
					placeholder="亲！有什么想要对我们说的请告诉我们~"></textarea>
			</div>
		</div>
	</div>
	<div class="jiange" style="height: 10px;"></div>
	<!-- 上传评价图片 -->
	<div id="projectImg" style="margin: 8px;">
		<img width="120px" height="120px"
			src="${ctx}/groupon/assets/img/pint/mm1.png"
			onclick="uploadFile('1')" />
	</div>

	<div class="jiange" style="height: 10px;"></div>
	<div class="fuwuandtaidu" style="height: 5rem; padding-top: 1rem;">

		<div class="taidu">
			<div class="pinfen yly_color4 yl_bc63">
				<div class="" style="float: left; width: auto; height: 100%;">评价</div>
				<div class="" style="float: left; width: 77%;">
					<ul id="taidu" style="list-style-type: none">
						<li id="taidu5"></li>
						<li id="taidu4"></li>
						<li id="taidu3"></li>
						<li id="taidu2"></li>
						<li id="taidu1"></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="jiange" style="height: 10px;"></div>

	<div class="bottom" style="margin-bottom: 5rem;">
		<div class="ly_right" style="width: 90%; margin: auto; float: none;">
			<div class="ly_sn"></div>
			<div onclick="indexan()" class="ly_xn indexan">
				<strong>发表评价</strong>
			</div>
		</div>

	</div>
</body>
</html>

