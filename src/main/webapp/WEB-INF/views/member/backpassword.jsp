<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>找回密码</title>
		<%@ include file="/common/include.rec.ftl"%>
		<link rel="stylesheet" href="${ctx}/resources/css/newcs.css" />
		<script src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.min.js" ></script>
	</head>
	<body class="bacolor5">
		<!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：头部
        -->
		<div class="headcs color2 bacolor1">
			<img class="float1 headcs_img" src="${ctx}/resources/img/back.png" />
			<span>找回密码</span>
			<span class="fontsiez1 float2">关闭</span>
		</div>
		
		<!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：步骤
        -->
        <div class="bacolor3 topbz">
        	<div class="topbz_zt">
        		<div class="topbz_zt_l">
        			<div class="bacolor4 color2 topbz_zt_l_t">1</div>
        			<span class="fontsiez1">验证手机号</span>
        		</div>
        		<div class="topbz_zt_r">
        			<div class="bacolor4 color2 topbz_zt_l_t">2</div>
        			<span class="fontsiez1">设置新密码</span>
        		</div>
        		<div class="jindu"></div>
        	</div>
        </div>
        
        <!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：内容
        -->
        <div class="chonzhi">
        	<div class="tishi color1" style="height: 3rem;">您可以使用手机号登录：${phone}</div>
        	<div class="tishi">
        		<input class="bacolor5 pos" id="password" name="password" value="" placeholder="密码" maxlength="20"/>
        		<div class="poss"></div>
        	</div><div class="tishi">
        		<input class="bacolor5 pos" id="repassword" value="" placeholder="请再次输入密码" maxlength="20" />
        		<div class="poss"></div>
        	</div>
        	<span class="fontsiez2 color3">请使用数字和字母或符号的组合，6-20个字符</span>
        </div>
        <input type="hidden" id="phone" name="phone" value="${phone}"/>
        <input type="hidden" id="registerCode" name="registerCode" value="${registerCode}"/>
        <div class="subnit bacolor1 color2" onclick="resetPWD();">提交</div>
        <script type="text/javascript">
        //验证短信验证码
		function resetPWD(){
			var phone = $('#phone').val();
			var registerCode = $('#registerCode').val();
			var password = $('#password').val();
			var repassword = $('#repassword').val();
			if(password!=''&&password!=null&&password==repassword){
				var r=$.ajax({
					type : 'post',
					url : ctx+'/web/code/resetPassword',
					data : {
						'password' : password,
						'registerCode' : registerCode,
						'phone' : phone },
					success : function(result){
						var json = eval("(" + result + ")");
						var status = json["status"];
						if (status == "0") {
							 window.location.href = ctx+'/Login';
						}
					}
				})
			}else {
				alert("密码输入有误");
			}
		}
		</script>
	</body>
	
</html>
