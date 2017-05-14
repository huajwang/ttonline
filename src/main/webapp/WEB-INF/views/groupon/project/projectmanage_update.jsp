<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>更新项目进展</title>
<%@ include file="/common/public.jsp"%>
<script type="text/javascript">
	var projectId = "${projectId}";//项目主键Id
	Icon = new Array();// 项目图片集合
	$(function() {
		//更新
		$("#uppro").click(
				function() {
					var content = $("#content").val();
					if (content == "") {
						alert("内容不能为空！");
						return;
					}
					var str = JSON.stringify(Icon);// 序列化方法 转成字符串
					$.post(ctx + '/groupon/api/project/addSchedule', {
						projectId : projectId,
						content : content,
						Icon : str
					}, function(result) {
						var json = JSON.parse(result);
						if (json["status"] == "0") {
							alert("添加一条项目动态成功！");
							window.location.href = ctx
									+ "/groupon/project/projectedit?id="
									+ projectId;
						} else {
							alert("修改失败！请与管理员联系");
						}
					});
				});
		//返回
		$("#goBack").click(
				function() {
					window.location.href = ctx
							+ "/groupon/project/projectedit?id=" + projectId;
				});
	});

	function uploadFile() {
		// 限制上传图片数量
		var obj = jQuery(
				'<input type="file" id="inputfile" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
				.appendTo('body');
		obj.click();// 点击弹出文件对话框，选择图片文件
		// 当选择完图片后触发
		$('#inputfile').change(function() {
							var $modal = $('#my-modal-loading');
							var data = new FormData();
							data.append('picFile', $('#inputfile')[0].files[0]);
							$modal.modal();// 打开遮罩层 图片正在上传中
							$.ajax({
										url : ctx + '/common/ajaxUploadPic.api',
										type : 'POST',
										data : data,
										cache : false,
										contentType : false, // 不可缺参数
										processData : false, // 不可缺参数
										success : function(data) {
											var json = eval("(" + data + ")");
											var picStr = json["data"]["picStr"];
											var data = {
												"Icon" : picStr
											};
											Icon.push(data);
											$("#projectImg").prepend('<div id="'+ picStr+ '" class="fpbc1"><span onclick="RemoveIcon(\''
																	+ picStr+ '\')" class="am-icon-times-circle fpbc3 shanchu"></span><img height="95px" class="fpbc2" src="'
																	+ aliURL
																	+ picStr
																	+ '" /></div>');
											obj.remove();
											$modal.modal('close');
										},
										error : function() {
											obj.remove();
											alert('上传出错');
										}
									});
						});
	}

	/**
	 * 移除图片
	 */
	function RemoveIcon(id) {
		if (window.confirm("确定移除此图片？")) {
			for (var i = 0; i < Icon.length; i++) {
				if (Icon[i].Icon == id) {
					Icon.splice(i, 1);
				}
			}
			$("#" + id).remove();
		}
	}
</script>
</head>
<body>
	<!-- 内容 -->
	<div class="updaereco bgcolor1">
		<textarea id="content" class="updatetexta" rows="" cols=""
			placeholder="填写更新内容"></textarea>
	</div>

	<!-- 图片 -->
	<div class="upimages bgcolor1">
		<div class="upimg_an">
			<div id="projectImg"></div>
			<div class="upimg_an_div">
				<img class="upimg_an_div_img"
					src="${ctx}/groupon/assets/img/pint/mm1.png" onclick="uploadFile()" />
			</div>
		</div>
		<div class="upimg_ts ztcolor">请勿在更新内容中上传支付二维码或引导用户发微信、支付宝红包、私人帐号汇款等信息,一经发现,将严肃处理。</div>
	</div>

	<!-- 按钮 -->

	<div id="uppro" class="uppro_an ztbgcolor fontcolor1">更新</div>
	<div id="goBack" class="uppro_an bgcolor4 fontcolor1"
		style="margin-bottom: 4rem">返回</div>
	<!-- 图片正在上传中 -->
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
		id="my-modal-loading">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">图片正在上传中 ...</div>
			<div class="am-modal-bd">
				<span class="am-icon-spinner am-icon-spin"></span>
			</div>
		</div>
	</div>
</body>
</html>