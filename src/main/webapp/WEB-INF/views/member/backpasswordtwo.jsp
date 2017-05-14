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
		<meta name="format-detection" content="telephone=yes">
		<title>找回密码</title>
		<%@ include file="/common/include.rec.ftl"%>
		<link rel="stylesheet" href="${ctx}/resources/css/newcs.css" />
		<script src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4/jquery-2.1.4/jquery.min.js" ></script>
		<script type="text/javascript" src="${ctx}/resources/js/resetcode.js" ></script>
		
	</head>
	<body class="bacolor5">
		<!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：头部
        -->
        <form id="resetpwd" action="${ctx}/member/resetPassword">
		<div class="headcs color3 bacolor3">
			<img class="float1 headcs_img" src="${ctx}/resources/img/arrow_right.png" />
			<span>找回密码</span>
			<span class="fontsiez1 float2">关闭</span>
		</div>
		
		<!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：步骤
        -->
        <div class="bacolor2 topbz">
        	<div class="topbz_zt">
        		<div class="topbz_zt_l">
        			<div class="bacolor4 color2 topbz_zt_l_t">1</div>
        			<span class="fontsiez1">验证手机号</span>
        		</div>
        		<div class="topbz_zt_r">
        			<div class="bacolor4 color2 topbz_zt_l_t bzcs">2</div>
        			<span class="fontsiez1 color3">设置新密码</span>
        		</div>
        		<div class="jindu2"></div>
        	</div>
        </div>
        
        <!--
        	作者：787711932@qq.com
        	时间：2016-06-01
        	描述：内容
        -->
        <div class="chonzhi">
        	<div class="tishi">
        		<input class="bacolor5 pos" id="phone" name="phone" value="" placeholder="请输入11位手机号" maxlength="11" />
        		<div class="poss2"></div>
        	</div><div class="tishi">
        		<div class="tishi_l">
	        		<input id="valicode" name='registerCode' class="bacolor5 pos2" value="" placeholder="请输入验证码" />
	        		<div class="poss3"></div>
        		</div>
	    		<div class="tishi_r">
	    			<!-- getVerificationCode(); -->
	    			<div id="sendSMS" class="yanzhen" onclick="getVerificationCode();">获取短信验证码</div>
	    		</div>
        	</div>
        </div>
        
        <div class="subnit bacolor1 color2" onclick="validateCode();">下一步</div>
        
        <div class="tsa">【提示】仅限手机号以验证和手机号注册用户找回密码</div>
        
	</body>
</html>
