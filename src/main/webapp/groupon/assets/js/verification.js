/**
 * 
 */
var identification = {};// 身份验证信息
// 提交事件
function submit(type) {
	identification.type = type;// 个人验证
	if ($("#name").val() == "") {
		alert("真实姓名不能为空！");
		return;
	}
	identification.name = $("#name").val();
	if ($("#IDCard").val() == "") {
		alert("身份证号不能为空！");
		return;
	}
	identification.IDCard = $("#IDCard").val();
	if ($("#phone").val() == "") {
		alert("联系号码不能为空！");
		return;
	}
	identification.phone = $("#phone").val();
	if (picStr == "") {
		alert("请按要求上传身份证图片！");
		return;
	}
	//企业注册
	if(type==1){
		if (licenseStr == "") {
			alert("请按要求上传企业营业执照图片！");
			return;
		}
	}
	var str = JSON.stringify(identification);// 序列化方法 转成字符串
	$.post(ctx + '/groupon/validate/addIdentification', {
		jsonStr : str
	}, function(result) {
		var json = JSON.parse(result);
		if (json["status"] == "0") {
			alert("身份验证信息成功提交");
			window.location.href = ctx + "/groupon/launch/";
		}
		else{
			alert("提交失败！请与管理员联系");
		}
		// $("span").html(result);
	});
}
var _index = 1;// 标识上传图片文件的数量
var picStr = "";// 身份证图片的字符串
var licenseStr="";//企业营业执照
// 上传身份证图片
function uploadFile() {
	if (_index > 1) {
		alert("只能上传1张身份证图片");
		return;
	}
	var obj = jQuery(
			'<input type="file" id="inputfile" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
			.appendTo('body');
	obj.click();// 点击弹出文件对话框，选择图片文件
	// 当选择完图片后触发
	$('#inputfile').change(
			function() {
				var data = new FormData();
				data.append('picFile', $('#inputfile')[0].files[0]);
				$.ajax({
					url : ctx + '/common/ajaxUploadPic.api',
					type : 'POST',
					data : data,
					cache : false,
					contentType : false, // 不可缺参数
					processData : false, // 不可缺参数
					success : function(data) {
						var json = eval("(" + data + ")");
						picStr = json["data"]["picStr"];
						identification.IDCard_Icon = picStr;// 身份证图片
						$("#IDCardImg").prepend(
								'<img style="width: 25%; margin-top: 15px; margin-left: 20px;" src="'
										+ aliURL + picStr + '"  />');
						obj.remove();
						_index++;
					},
					error : function() {
						obj.remove();
						alert('上传出错');
					}
				});
			});
}

//上传企业营业执照图片
function uploadFileBylicense() {
	var obj = jQuery(
			'<input type="file" id="inputfile" style="height:0;width:0;z-index: -1; position: absolute;left: 10px;top: 5px;"/>')
			.appendTo('body');
	obj.click();// 点击弹出文件对话框，选择图片文件
	// 当选择完图片后触发
	$('#inputfile').change(
			function() {
				var data = new FormData();
				data.append('picFile', $('#inputfile')[0].files[0]);
				$.ajax({
					url : ctx + '/common/ajaxUploadPic.api',
					type : 'POST',
					data : data,
					cache : false,
					contentType : false, // 不可缺参数
					processData : false, // 不可缺参数
					success : function(data) {
						var json = eval("(" + data + ")");
						licenseStr = json["data"]["picStr"];
						identification.license_Icon = licenseStr;// 企业营业执照图片
						$("#LicenseImg").prepend(
								'<img style="width: 25%; margin-top: 15px; margin-left: 20px;" src="'
										+ aliURL + picStr + '"  />');
						obj.remove();
						_index++;
					},
					error : function() {
						obj.remove();
						alert('上传出错');
					}
				});
			});
}