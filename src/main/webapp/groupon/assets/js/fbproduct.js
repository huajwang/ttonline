/**
 * 
 */
var _index = 1;// 标识上传图片文件的数量
var projectItme = {};// 项目所有参数集合
projectItme.Icon = new Array();// 项目图片集合
// 上传项目图片数据
function uploadFile(type) {
	//判断控件是否存在
	if($('#inputfile').length>0){
		$('#inputfile').remove()
	}
	// 限制上传图片数量
	var len = projectItme.Icon.length;
	var _index = 0
	for (var i = 0; i < len; i++) {
		if (projectItme.Icon[i].IsDisplay == type) {
			_index++;
		}

	}
	var obj = jQuery(
			'<input multiple="true" type="file" id="inputfile" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
			.appendTo('body');
	obj.click();// 点击弹出文件对话框，选择图片文件
	// 当选择完图片后触发
	$('#inputfile')
			.change(
					function() {
						var $modal = $('#my-modal-loading');
						var data = new FormData();
						var len = $('#inputfile')[0].files.length;
						if (type == 0) {// 产品详情图
							if (len + _index > 8) {
								alert("产品详情图只能上传8张图片");
								return;
							}
						}
						if (type == 1) {// 产品轮播图
							if (len + _index > 4) {
								alert("产品轮播图只能上传4张图片");
								return;
							}
						}
						for (var i = 0; i < len; i++) {
							data.append('picFile', $('#inputfile')[0].files[i]);
						}
						$modal.modal();// 打开遮罩层 图片正在上传中
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
											var icon = {
												"Icon" : picStr,
												"IsDisplay" : type
											};
											projectItme.Icon.push(icon);
											$("#projectImg" + type)
													.prepend(
															'<div id="'
																	+ picStr
																	+ '" class="fpbc1"><span onclick="RemoveIcon(\''
																	+ picStr
																	+ '\')" class="am-icon-times-circle fpbc3 shanchu"></span><img height="95px" class="fpbc2" src="'
																	+ aliURL
																	+ picStr
																	+ '" /></div>');
										}
										$modal.modal('close');
									},
									error : function() {
										obj.remove();
										alert('上传出错');
										$modal.modal('close');
									}
								});
					});
}
// 删除产品数据
function delProuduct(_this) {
	if (window.confirm("您确定移除该产品？")) {
		$(_this).parent().parent().remove();
	}
}
// 添加产品
function addProduct() {
	var ProductName = $("#ProductName").val();// 产品名称
	var price = $("#price").val();// 拼团价格
	var content = $("#content").val();// 产品详情
	var num = $("#num").val();// 数量
	var unit = $("#unit").val();// 拼团单位
	if (ProductName == "" || price == "" || content == "" || num == ""
			|| productImgStr == "") {
		alert("数据不能为空！");
		return;
	}
	var html = '<div ProductName="' + ProductName + '" price="' + price
			+ '" content="' + content + '" num="' + num + '" productImgStr="'
			+ productImgStr + '" unit="' + unit + '" class="spell_conter11">';
	html += '<img class="spell_conter_img" src="' + aliURL + productImgStr
			+ '" />';
	html += '<div class="spell_conter_left">' + ProductName + '</div>'
	html += '<div class="spell_conter_left">' + price + '元/' + unit + '</div>';
	html += '<div class="spell_conter_right"><img class="spell_conter_right_img" src="'
			+ ctx
			+ '/groupon/assets/img/pint/toilite.png" onclick="delProuduct(this)" /></div>';
	html += '</div>';
	$("#productList").prepend(html);
	removePorduct();
}
var productImgStr = "";// 产品图片字符
// 上传产品图片
function uploadProductFile() {
	if ($("#Img1").length != 0) {
		alert("产品图片只能添加一张！");
		return;
	}
	var obj = jQuery(
			'<input type="file" id="inputfilebyProduct" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
			.appendTo('body');
	obj.click();// 点击弹出文件对话框，选择图片文件
	// 当选择完图片后触发
	$('#inputfilebyProduct')
			.change(
					function() {
						var $modal = $('#my-modal-loading');
						$modal.modal();// 打开遮罩层 图片正在上传中
						var data = new FormData();
						data.append('picFile',
								$('#inputfilebyProduct')[0].files[0]);
						$
								.ajax({
									url : ctx + '/common/ajaxUploadPic.api',
									type : 'POST',
									data : data,
									cache : false,
									contentType : false, // 不可缺参数
									processData : false, // 不可缺参数
									success : function(data) {
										var json = eval("(" + data + ")");
										var picStr = json["data"]["picStr"];
										productImgStr = picStr;
										$("#productImg")
												.prepend(
														'<img id="Img1" height="80px" style="width: 25%; margin-top: 15px; margin-left: 20px;" src="'
																+ aliURL
																+ picStr
																+ '"  />');
										obj.remove();
										$modal.modal('close');
									},
									error : function() {
										productImgStr = "";
										obj.remove();
										alert('上传出错');
									}
								});
					});
}
// 移除产品相关信息
function removePorduct() {
	$('.tcdown').css('display', 'none');
	$('.tachu').css('display', 'none');
	if ($("#Img1").length != 0) {
		$("#Img1").remove();// 移除上传的图片数据
	}
	$("#ProductName").val("");// 产品名称
	$("#price").val("");// 拼团价格
	$("#content").val("");// 产品详情
	$("#num").val("");// 数量
}
// 发布项目
function fabuProject() {
	projectItme.name = $("#name").val();// 项目名称
	projectItme.amount = $("#amount").val();// 拼款金额
	projectItme.endTime = $("#currenttime").html();// 截止日期
	projectItme.EMS = $("#EMS").val();// 运费说明
	projectItme.sendTime = $("#sendTime").val();// 发货说明
	projectItme.remark = $("#remark").val();// 项目说明
	projectItme.product = new Array();// 产品数据结合
	$('#productList .spell_conter11').each(function(i) {
		var ProductName = $(this).attr("ProductName");
		var price = $(this).attr("price");
		var content = $(this).attr("content");
		var num = $(this).attr("num");
		var Icon = $(this).attr("productImgStr");
		var unit = $(this).attr("unit");
		var item = {
			"ProductName" : ProductName,// 产品名称
			"price" : price,// 产品价格
			"content" : content,// 产品介绍
			"num" : num,// 产品数量
			"unit" : unit,// 单位
			"Icon" : Icon
		// 产品图片
		};
		projectItme.product.push(item);
	});
	if (projectItme.product.length == 0) {
		alert("产品不能为空！");
		return;
	}
	var str = JSON.stringify(projectItme);// 序列化方法 转成字符串

	var $modalSaving = $('#my-modal-saving');
	$modalSaving.modal();// 打开遮罩层
	$.post(ctx + '/groupon/launch/addProject', {
		jsonStr : str
	}, function(json) {
		if (json["status"] == "0") {
			alert("拼团发布成功！进入审批环节");
			if (IsTrue != "true")// 进入身份验证
				window.location.href = ctx + "/groupon/launch/start";
			else
				window.location.href = ctx + "/groupon/launch/initiate";// 项目审批页面
		} else {
			alert(json["data"]["err_msg"]);
		}
		$modalSaving.modal('close');
		// $("span").html(result);
	});
}
/**
 * 移除图片
 */
function RemoveIcon(id) {
	if (window.confirm("确定移除此图片？")) {
		projectItme.Icon.remove(id);
		$("#" + id).remove();
	}
}