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
<img id="fan_hui" onclick="self.location=document.referrer;" style="position: fixed;width:40px;margin-top:2em;margin-left:0.5em;" src="${ctx}/resources/img/logo/jiantou.png"/>
     <div class="successfailure_there_save"><strong>什么叫项目成功和项目失败</strong></div>
     <div class="successfailure_there_text">
          <span>按照发起项目时选择二的模板，成功则分为如下</span><br/>
          <span>一、在三天审核时间内，通过我们审核的，视为项目成功；否则视为失败。</span>
     </div>
</body>
</html>