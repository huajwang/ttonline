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
   .money_there_save{
         width:94%;
         margin:auto;
         height:50px;
         border-bottom:1px solid RGB(239,239,239);
         color:RGB(5,5,5);
         line-height:50px;
         font-size:20px;
         font-family:"微软雅黑";
   }
   .money_there_text{
         width:94%;
         margin:auto;
         height:50px;
         color:RGB(5,5,5);
         line-height:50px;
         font-size:14px;
         font-family:"微软雅黑";
   }
</style>
<body style="margin:0;padding:0;background-color: #fff;">
<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
     <div class="money_there_save"><strong>拼团成功后钱存在哪里</strong></div>
     <div class="money_there_text">拼团成功后，款项将会储存在您的欢乐拼账户中</div>
</body>
</html>