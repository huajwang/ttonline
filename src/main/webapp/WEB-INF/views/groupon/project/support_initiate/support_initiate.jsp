<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/public.jsp"%>
<title>帮助中心-欢乐拼</title>
</head>
<style type="text/css">
  
</style>
<body style="margin:0;padding:0;background-color: #fff;">
<img id="fan_hui" onclick="window.location.href='${ctx}/groupon/launch/helpcenter'"  style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
     <div class="supported_project_there_save"><strong>项目支持相关问题</strong></div>
     <div class="supported_project_there_text">
          <span style="font-size:17px;">如何申请退款？</span><br/>
          <span style="font-size:14px;">再欢乐拼页面，点击底端"我的拼团",之后点击该页面"我的购物"早该页面内已完成页面点击申请。</span><br/>
          
     </div>
</body>
</html>