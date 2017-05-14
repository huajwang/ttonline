<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=yes">
<title>完善资料</title>
<%@ include file="/common/include.rec.ftl"%>
<link href="${ctx}/resources/css/css.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/yly_zjw.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/webuploader-portrait/webuploader.css">
<script type="text/javascript" src="${ctx}/resources/js/webuploader-portrait/uploader.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/webuploader-portrait/webuploader.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.js"></script>
<script type="text/javascript">
	var _sex = "${user.sex}";
	$(function() {
		if (_sex != "") {
			$("#sex").val(_sex);
		}
		$("#saveUser").click(function() {
			var realName = $("#realName").val();
			if (realName == "") {
				alert("真实姓名不能为空！");
				return;
			}
			var userName = $("#userName").val();
			if (userName == "") {
				alert("登录名不能为空！");
				return;
			}
			$.ajax({
				type : 'post',
				url : ctx + "/member/saveOrUpdateUser",
				data : $("form").serialize(),
				success : function(result) {
					var json = eval("(" + result + ")");
					var status = json["status"];
					if (status == "0") {
						alert("成功修改个人用户信息！");
						history.go(-1);
					}else{
						var msg = json["data"]["msg"];
						alert(msg);
					}
				}
			});
		});
	});
</script>
</head>

<body>
	<form id="user" action="">
		<div class="memreco">
			<div class="yly_or_top">
				<div class="or_top_left"></div>
					<div class="or_top_right">
						<p>账户管理</p>
					</div>
				</div>
			<div class="portrait_all">
				<div id="filePicker"><img id="userPortrait" class="portrait_util"
					src="${user.iconUrl}"/></div>	
				<div class="memname_pic">头像：<font size='1' color='red'>(点右圈操作头像)</font></div>
			</div>
			<div>
				<div class="memname">真实姓名：</div>
				<input type="text" id="realName" name="realName"
					class="memnamein yly_bg_color1" value="${user.realName}" />
			</div>

			<div>
				<div class="memname">性别：</div>
				<select id="sex" name="sex" class="memnamein yly_bg_color1">
					<option value="1" selected="selected">男</option>
					<option value="0">女</option>
				</select>
			</div>

			<%-- <div>
				<div class="memname">登录名：</div>
				<input type="text" id="userName" name="userName"
					class="memnamein yly_bg_color1" value="${user.userName}" />
			</div> --%>
			
			<div>
				<div class="memname">手机号码：</div>
				<input type="text" id="phone" name="phone"
					class="memnamein yly_bg_color1" value="${user.phone}" />
			</div>

			<div>
				<div class="memname">电子邮件：</div>
				<input type="text" name="email" class="memnamein yly_bg_color1"
					value="${user.email}" />
			</div>

			<div id="saveUser" class="queren indexan">确认</div>
			<div id="fanhui" class="queren fanhui" onclick="history.go(-1);">返回</div>
			<div class="queren tuichu"><a href="${ctx}/Login" style="color: #ef3030;">退出登录</a></div>
		</div>
	</form>

	<div class="zwu_ts yl_bc50">
		<span>蚂蚁小威</span> <span>投诉：0592-2069229</span>
	</div>

</body>
</html>
