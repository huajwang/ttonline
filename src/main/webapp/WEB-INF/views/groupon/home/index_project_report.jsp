<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>填写举报信息</title>
<%@ include file="/common/public.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/webuploader-0.1.5/webuploader-group.css">
<script type="text/javascript" src="${ctx}/resources/js/webuploader-0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/webuploader-0.1.5/uploader-groupreport.js"></script>
</head>
<body class="bgcolor1">
	<!-- 头部 -->
	<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
		<img onclick="self.location=document.referrer;" class="prodetalieimg" alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
		<span style="font-size: 18px;display: inline-block;">项目举报</span>
		<span id="quren" class="pro_jb" style="margin-left: -17%;">确认举报</span>
	</div>
	
	<div class="in_roc">
		<input type="text" id="realName" value="" placeholder="真实姓名">
	</div>
	<div class="in_roc">
		<input type="text" id="identification" value="" placeholder="您的身份证号码">
		<div class="xmjbtx">请填写正确的身份证号！</div>
	</div>
	<div class="in_roc_te">
		<textarea rows="" cols="3" id="reason" placeholder="填写您举报该项目的理由"></textarea>
	</div>
	<input type="hidden" id="projectId" value="${projectId}"/>
	<div class="imgup">
		<div class="imgup_ts fontcolor2">上传举报相关图片</div>
		<div class="imgup_updi">
			<div style="width:100%">
				<div class="tui_main_file">
				<!--用来存放item-->
					<div>
						<div id="filePicker">+</div>
					</div>
					<div id="thelist" class="uploader-list"></div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 弹出 -->
	<div class="tachu"></div>
	<div class="jbts">
		<div class="jbts_titl"><h3>举报提示</h3></div>
		<div class="jbts_reco">
			<p>举报提交将在1~3个工作日内处理，请留意账户消息！</p>
		</div>
		<div class="jbts_an ztbgcolor fontcolor1">知道了</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$('#quren').on('click',function(){
		$('.tachu').css('display','block');
		$('.jbts').css('display','block');
	});
	$('.jbts_an').on('click',function(){
		$('.tachu').css('display','none');
		$('.jbts').css('display','none');
		report();
	});

	window.setInterval(function(){
		var ss = $("#identification").val().length;
		$("#identification").blur(function(){
			if(ss<18&&ss>0){
				$('.xmjbtx').css('display','block');
			}else{
				$('.xmjbtx').css('display','none');
			}
		});
		if(ss==18){
			$('.xmjbtx').css('display','none');
		}
	},100);//实时执行
});

function report(){
	var images = '';
	for(var i=0;i<picArr.length;i++){
		images += ' ';
		images += picArr[i];
	}
	$.post(ctx + '/groupon/submitprojectreport', {
		"projectId" : $('#projectId').val(),
		"images": images,
		"realName":$('#realName').val(),
		"IDCare":$('#identification').val(),
		"content" : $('#reason').val()
	}, function(result) {
		var json = JSON.parse(result);
		if (json["status"] == "0") {
			window.history.go(-1);
		}
		else{
			alert("提交失败，请检查账户是否已经登陆，填写内容格式是否正确。");
		}
	});
}
</script>
</html>