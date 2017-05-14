<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title></title>
</head>

<body style="margin:0;padding:0;background-color: #fff">
     <!-- 头部 -->
		<div class="mobindextop ztbgcolor fontcolor1 fontsize25">
			<img onclick="window.location.href='${ctx}/groupon/launch/projectfq';" class="prodetalieimg" alt="" src="${ctx}/groupon/assets/img/index/indexprojectdetail/back.png">
			<span style="font-size: 18px;display: inline-block;">已发起</span>
			
		</div>
		
		<div class="top_img">
		     <img id="img" style="width:18%;margin-left:41%;" src="${ctx}/groupon/assets/img/pint/similer.png"/>
		</div>
		
		<div class="top_text">成功发起项目</div>
		
		<div class="the_identity_authentication"><strong>未身份认证，请您身份认证</strong></div>
		<div class="look_btn">查看项目</div>
		<div class="identity_btn" id="sfzheng">身份验证</div>
		
		<div class="share_firend">
		    <div class="share_firend_left"></div>
		    <div class="share_firend_conter">分享给好友</div>
		    <div class="share_firend_right"></div>
		  
		</div>
		<div class="bom_img">
		     <img id="" style="width:13%;float:left;margin-left:5%;" src="${ctx}/groupon/assets/img/pint/faqi1.png"/>
		     <img id="" style="width:13%;float:left;margin-left:5%;" src="${ctx}/groupon/assets/img/pint/faqi2.png"/>
		     <img id="" style="width:13%;margin-left:5%;" src="${ctx}/groupon/assets/img/pint/faqi3.png"/>
		     <img id="" style="width:13%;margin-left:5%;" src="${ctx}/groupon/assets/img/pint/faqi4.png"/>
		     <img id="" style="width:13%;margin-left:5%;" src="${ctx}/groupon/assets/img/pint/faqi5.png"/>
		</div>
</body>
<script type="text/javascript">
	$('#sfzheng').on('click',function(){
		window.location.href=ctx+"/groupon/launch/authentication";
	});
	//查看项目
	$('.look_btn').on('click',function(){
		window.location.href=ctx+"/groupon/project/projectedit";
	});
</script>
</html>