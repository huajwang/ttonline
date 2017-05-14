<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心</title>
<!-- 帮助_发起项目的首页 - -->
<style type="text/css">
     
   
</style>
</head>
<body style="background-color: #fff;margin:0;padding:0;">
    <div class="project_related_issues project_public " style="text-indent: 0.8em"><strong>发起项目相关问题</strong></div>
    <img id="fan_hui" onclick="window.location.href='${ctx}/groupon/launch/helpcenter'"  style="position: fixed;width:40px;margin-top:-1em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
     <div id="guifan" class="project_related_norm  project_public">
        <span>发起项目有何规范?</span>
        <img  class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
      <div id="save" class="project_related_norm  project_public">
        <span>拼团成功后钱存在哪里？</span>
        <img  class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
      <div id="project_success" class="project_related_norm  project_public">
        <span>什么叫项目成功和项目失败？</span>
        <img class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
      <div id="collection_of" class="project_related_norm  project_public">
        <span>项目成功后会收取费用吗？</span>
        <img  class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
      <div id="displace" class="project_related_norm  project_public">
        <span>项目发布后，是否还能修改编辑？</span>
        <img class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
     
      <div  id="take_account" class="project_related_norm  project_public">
        <span>申请提现后多久可以到账？</span>
        <img class="project_related_norm_img" src="${ctx}/groupon/assets/img/pint/jiao1.png"/>
     </div>
</body>
    <!-- 跳转的点击事件 -->
<script type="text/javascript">
	$('#guifan').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization";
	});
	/* 拼团成功后钱存在哪里。点击 */
	$('#save').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization_prep";
	});
	
	/* 什么叫项目成功和项目失败  。点击 */
	$('#project_success').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization_success_failure";
	});
	
	/* 项目成功后会收取费用吗   。点击 */
	$('#collection_of').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization_feecharging";
	});
	
	/* 项目发布后，是否还能修改编辑？   。点击 */
	$('#displace').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization_edit";
	});
	
	/* 申请提现后多久可以到账 。点击 */
	$('#take_account').on('click',function(){
		window.location.href=ctx+"/groupon/project/standardization_account";
	});
</script>
	
</html>