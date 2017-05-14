<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/common/public.jsp"%>
<script src="${ctx}/groupon/assets/js/verification.js"></script>
<title>个人验证</title>
</head>
<style type="text/css">

</style>
<body style="margin:0;padding:0;">
  <div  class="basic_a">
       <div class="Id_card"><span class="Id_card_id">法人姓名</span></div>
       <div class="input_shuru"><input id="name" class="input_jihuo"type="text" placeholder=" &nbsp;填写发起人真实姓名"/></div>
  </div>
  
   <div class="basic_b">
       <div class="Id_card"><span class="Id_card_id">身份证号</span></div>
       <div class="input_shuru"><input id="IDCard" class="input_jihuo"type="text" placeholder=" &nbsp;填写发起人身份证号码"/></div>
  </div>
  
   <div class="basic_c">
       <div class="Id_card"><span class="Id_card_id">联系号码</span></div>
       <div class="input_shuru"><input id="phone" class="input_jihuo"type="text" placeholder=" &nbsp;填写发起人的联系号码"/></div>
  </div>
  <div class="handheld1">
       <div class="handheld1_text">手持身份证照片</div>
       <div id="IDCardImg" class="handheld1_img">
           <img  style="width:28%;margin-left:0.82em;" src="${ctx}/groupon/assets/img/pint/shenfen2.jpg" onclick="uploadFile();"/>
           <img style="width:28%;margin-left:1.5em;" src="${ctx}/groupon/assets/img/pint/shenfen1.jpg"/>   
       </div>
       <div class="handheld1_wenzi">身份证上的所有信息必须清晰可见，必须能看清身份证号</div>
       
  </div>
  <div class="entity1">
      <div id="LicenseImg" class="entity_faren">营业执照照片</div>
      <img  style="width:28%;margin-left:0.82em;margin-top: 8px;" src="${ctx}/groupon/assets/img/pint/shenfen2.jpg" onclick="uploadFileBylicense();"/>
      <div class="entity1_wenzi">身份证上的所有信息必须清晰可见，必须能看清身份证号</div>
  </div>
  
  
  <div onclick="submit(1);" class="handheld1_btn">提交</div>
</body>
</html>